package inti.recon.backend;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.features2d.DMatch;

public interface ImageTest {

	void inspectMatches(List<DMatch> good_matches);

	void inspectHomography(MatOfPoint2f candidatePoints,
			MatOfPoint2f needlePoints, Mat hg);

	void inspectPerspective(Mat scene_corners);

	void inspectQuadrilateral(Quadrilateral quadrilateral);

	void finish(boolean goodCandidate);

}
