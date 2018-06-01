package inti.recon.backend;

import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.KeyPoint;

public class BillImage {
	Mat image;
	Mat corners;
	MatOfKeyPoint keypoints;
	Mat descriptors;

	public Mat getImage() {
		return image;
	}

	public void setImage(Mat image) {
		this.image = image;
	}

	// TODO read from database
	public MatOfKeyPoint getKeypoints() {
		return keypoints;
	}
	
	public int getCols() {
		return image.cols();
	}

	// TODO read from database
	public List<KeyPoint> getListOfKeyPoints() {
		return keypoints.toList();
	}
	
	public void setKeypoints(MatOfKeyPoint keypoints) {
		this.keypoints = keypoints;
	}

	// TODO read from database
	public Mat getDescriptors() {
		return descriptors;
	}

	public void setDescriptors(Mat descriptors) {
		this.descriptors = descriptors;
	}

	// TODO read from database
	public Mat getCorners() {
		corners = new Mat(4, 1, CvType.CV_32FC2);
		corners.put(0, 0, new double[] { 0, 0 });
		corners.put(1, 0, new double[] { image.cols(), 0 });
		corners.put(2, 0, new double[] { image.cols(), image.rows()});
		corners.put(3, 0, new double[] { 0,	image.rows() });

		return corners;
	}
}
