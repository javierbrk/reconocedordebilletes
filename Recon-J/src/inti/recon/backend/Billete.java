package inti.recon.backend;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;


import android.content.Context;

public class Billete {

	private Mat frente;
	private Mat dorso;
	private MatOfKeyPoint keypointsf=new MatOfKeyPoint();
	private MatOfKeyPoint keypointsd = new MatOfKeyPoint();
	private Mat descriptorsf = new Mat();
	private Mat descriptorsd = new Mat();
	
	
	public Billete(Context context,Mat f,Mat d){
		DescriptorExtractor descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
		
		frente=f.clone();
		dorso=d.clone();
			
		detector.detect(frente, keypointsf);
		descriptor.compute(frente, keypointsf, descriptorsf);
		
		detector.detect(dorso, keypointsd);
		descriptor.compute(dorso, keypointsd, descriptorsd);
			
	}
	public Mat getFrente(){
		return frente;
	}
	public Mat getDorso(){
		return dorso;
	}
	public MatOfKeyPoint getKFrente(){
		return keypointsf;
	}
	public MatOfKeyPoint getKDorso(){
		return keypointsd;
	}
	public Mat getDFrente(){
		return descriptorsf;
	}
	public Mat getDDorso(){
		return descriptorsd;
	}
	
	
}
