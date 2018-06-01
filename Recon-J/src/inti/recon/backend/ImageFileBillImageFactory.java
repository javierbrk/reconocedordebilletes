package inti.recon.backend;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.imgproc.Imgproc;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.Highgui;;

public class ImageFileBillImageFactory implements BillImageFactory {
	DescriptorExtractor descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
	FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);

	@Override
	public BillImage createFromImageFile(File file) {
		BillImage image = new BillImage();

		Mat imageMat = getImageMat(file);
		image.setImage(imageMat);

		MatOfKeyPoint keypoints = getKeyPoints(imageMat);
		image.setKeypoints(keypoints);

		Mat descriptors = getDescriptors(imageMat, keypoints);
		image.setDescriptors(descriptors);

		return image;
	}

	protected Mat getImageMat(File file) {
		Mat imageMat = new Mat();
		Imgproc.cvtColor(Highgui.imread(file.getAbsolutePath()), imageMat, Imgproc.COLOR_BGR2GRAY);
		return imageMat;
	}

	protected MatOfKeyPoint getKeyPoints(Mat imageMat) {
		MatOfKeyPoint keypoints = new MatOfKeyPoint();
		detector.detect(imageMat, keypoints);
		return keypoints;
	}

	protected Mat getDescriptors(Mat imageMat, MatOfKeyPoint keypoints) {
		Mat descriptors = new Mat();
		descriptor.compute(imageMat, keypoints, descriptors);
		return descriptors;
	}

}
