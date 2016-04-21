package inti.recon.backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.KeyPoint;

public class PowerfulBillSearch implements BillSearch {
	private static final double Pdistancia = 0.7;
	private static final int NGOOD_MATCHES = 3;
	private static final int RANSAC = 8;
	private static final double RANSAC_BOUND = 10;

	DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
	int max_best_matches = 2;
	private ImageTestFactory imageTestFactory;
	
	public PowerfulBillSearch(ImageTestFactory imageTestFactory) {
		this.imageTestFactory = imageTestFactory;
	}

	@Override
	public String search(Billete needle, List<Billete> haystack) {
		return null;
	}

	@Override
	public String search(BillImage needle, List<Bill> haystack) {
		String res = "";

		for (Bill candidateBill : haystack) {

			for (BillImage candidateImage : candidateBill.getImages()) {
				boolean goodCandidate = false;
				ImageTest test = imageTestFactory.newTest(needle, candidateImage); 

				List<DMatch> good_matches = findBestMatches(needle, candidateImage);
				test.inspectMatches(good_matches); 

				if (good_matches.size() > NGOOD_MATCHES) {

					MatOfPoint2f candidatePoints = findCandidateMatchingPoints(candidateImage, good_matches);
					MatOfPoint2f needlePoints = findNeedleMatchingPoints(needle, good_matches);
					Mat hg = Calib3d.findHomography(candidatePoints, needlePoints, RANSAC, RANSAC_BOUND);
					test.inspectHomography(candidatePoints, needlePoints, hg);
					
					Mat obj_corners = candidateImage.getCorners(); 
					Mat scene_corners = new Mat(4, 1, CvType.CV_32FC2);
					Core.perspectiveTransform(obj_corners, scene_corners, hg);
					test.inspectPerspective(scene_corners);

					scene_corners.put(0, 0, new double[] { scene_corners.get(0, 0)[0] + candidateImage.getCols(), scene_corners.get(0, 0)[1] });
					scene_corners.put(1, 0,	new double[] { scene_corners.get(1, 0)[0] + candidateImage.getCols(), scene_corners.get(1, 0)[1] });
					scene_corners.put(2, 0,	new double[] { scene_corners.get(2, 0)[0] + candidateImage.getCols(), scene_corners.get(2, 0)[1] });
					scene_corners.put(3, 0, new double[] { scene_corners.get(3, 0)[0] + candidateImage.getCols(), scene_corners.get(3, 0)[1] });

					Point punto_A = new Point(scene_corners.get(0, 0));
					Point punto_B = new Point(scene_corners.get(1, 0));
					Point punto_C = new Point(scene_corners.get(2, 0));
					Point punto_D = new Point(scene_corners.get(3, 0));

					Quadrilateral quadrilateral = new Quadrilateral(punto_A, punto_B, punto_C, punto_D);
					test.inspectQuadrilateral(quadrilateral);

					if (quadrilateral.isConvex() && quadrilateral.isLargeEnough()) {
						res = res + candidateBill.getId() + " ";
						goodCandidate = true;
					}
				}
				test.finish(goodCandidate);
			}
		}
		return res;
	}

	// TODO Can we use Guava to remove duplicate?
	// TODO Can we use Guava transform 
	private MatOfPoint2f findNeedleMatchingPoints(BillImage needle, List<DMatch> good_matches) {
		List<KeyPoint> keypoints_sceneList = needle.getListOfKeyPoints();
		List<Point> needlePoints = new LinkedList<Point>();
		for (DMatch dMatch : good_matches) {
			needlePoints.add(keypoints_sceneList.get(dMatch.trainIdx).pt);
		}
		MatOfPoint2f needlePointsMat = new MatOfPoint2f();
		needlePointsMat.fromList(needlePoints);
		return needlePointsMat;
	}

	// TODO Can we use Guava to remove duplicate?
	// TODO Can we use Guava transform 
	private MatOfPoint2f findCandidateMatchingPoints(BillImage candidateImage, List<DMatch> good_matches) {
		List<KeyPoint> keypoints_objectList = candidateImage.getListOfKeyPoints();
		List<Point> candidatePoints = new LinkedList<Point>();
		for (DMatch dMatch : good_matches) {
			candidatePoints.add(keypoints_objectList.get(dMatch.queryIdx).pt);
		}
		MatOfPoint2f candidatePointsMat = new MatOfPoint2f();
		candidatePointsMat.fromList(candidatePoints);
		return candidatePointsMat;
	}

	private List<DMatch> findBestMatches(BillImage needle,
			BillImage candidateImage) {
		List<MatOfDMatch> foundMatches = new ArrayList<MatOfDMatch>(max_best_matches);
		matcher.knnMatch(candidateImage.getDescriptors(),
				needle.getDescriptors(), foundMatches, max_best_matches);

		List<DMatch> good_matches = filterCloseMatches(foundMatches);
		return good_matches;
	}

	private List<DMatch> filterCloseMatches(List<MatOfDMatch> matches) {
		List<DMatch> good_matches = new ArrayList<DMatch>();

		for (MatOfDMatch match : matches) {
			if (match.toList().get(0).distance < match.toList().get(1).distance * Pdistancia)
				good_matches.add(match.toList().get(0));
		}

		return good_matches;
	}
}
