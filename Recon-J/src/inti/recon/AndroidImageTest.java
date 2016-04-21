package inti.recon;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.os.Environment;
import android.util.Log;
import inti.recon.backend.BillImage;
import inti.recon.backend.ImageTest;
import inti.recon.backend.Quadrilateral;

public class AndroidImageTest implements ImageTest {
	private static final String TAG = "AndroidInspector";
	private static final boolean Debug = false;
	private BillImage needle;
	private BillImage candidateImage;
	private Mat outImg;

	public AndroidImageTest(BillImage needle, BillImage candidateImage) {
		this.needle = needle;
		this.candidateImage = candidateImage;
		this.outImg = new Mat(); 
	}

	@Override
	public void inspectMatches(List<DMatch> matches) {
		Scalar randomColor = Scalar.all(-1);
		Log.d(TAG, "good matches: " + matches.size());
		MatOfDMatch matchesMatrix = new MatOfDMatch();
		matchesMatrix.fromList(matches);
		MatOfByte drawnMatches = new MatOfByte();
		Features2d.drawMatches(candidateImage.getImage(), candidateImage.getKeypoints(), 
				               needle.getImage(), needle.getKeypoints(), 
				               matchesMatrix, outImg, randomColor, randomColor, drawnMatches, Features2d.NOT_DRAW_SINGLE_POINTS);
	}

	@Override
	public void inspectHomography(MatOfPoint2f candidatePoints,
			MatOfPoint2f needlePoints, Mat hg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void inspectPerspective(Mat scene_corners) {
		// TODO Auto-generated method stub
	}

	@Override
	public void inspectQuadrilateral(Quadrilateral quadrilateral) {
		Scalar GREEN = new Scalar(0, 255, 0);
		int THICKNESS = 4;
		Core.line(outImg, quadrilateral.getA(), quadrilateral.getB(), GREEN, THICKNESS);
		Core.line(outImg, quadrilateral.getB(), quadrilateral.getC(), GREEN, THICKNESS);
		Core.line(outImg, quadrilateral.getC(), quadrilateral.getD(), GREEN, THICKNESS);
		Core.line(outImg, quadrilateral.getD(), quadrilateral.getA(), GREEN, THICKNESS);
	}

	@Override
	public void finish(boolean goodCandidate) {
		if (Debug) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			String currentDateandTime = sdf.format(new Date());
			String fileNameTemplate = currentDateandTime + "_" + (goodCandidate ? "PASSED" : "FAILED") + ".png";
			File outputFile = new File(getPicturesPath(), fileNameTemplate);

			Mat convertedColorImage = new Mat();
			Imgproc.cvtColor(outImg, convertedColorImage, Imgproc.COLOR_RGBA2BGR, 3);
			Boolean bool = Highgui.imwrite(outputFile.toString(), convertedColorImage);

//			if (bool == true)
//				Log.i(TAG, "SUCCESS writing image to external storage");
//			else
//				Log.i(TAG, "Fail writing image to external storage");
		}

	}

	protected File getPicturesPath() {
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	}
}
