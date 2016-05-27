package inti.recon.backend;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

public class SimpleBillSearch implements BillSearch {
    private static final String TAG = "OCVSample::Activity";
    private static final double Pdistancia = 0.7;
    private static final int Ngoodmatches = 3,Nfeatures=3;
    private static final int Metodo = 8;//0 usa todos los goodmatches, 8 RANSAC usa solo los que cumplen la reprojecccion a una distancia menor de una cota
    private static final double CotaRansac = 10;//cota de RANSAC desde 1 a 10 pixels generalmente
    private static final boolean Debug = false;
	
    
    
	@Override
	public String search(Billete needle, List<Billete> haystack) {
   	 String resf=reconocerFrentes(needle, haystack);
   	 String resd=reconocerDorsos(needle, haystack);
   	 
   	 return resf + resd;
	}
	
	
	@SuppressLint("SimpleDateFormat")
	private String reconocerDorsos(Billete needle, List<Billete> haystack) {
		String res="";
    	DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
		List<MatOfDMatch> matches;
		List<DMatch> good_matches;
		MatOfDMatch goodMatches;
		LinkedList<Point> objList;
		LinkedList<Point> sceneList;
		MatOfPoint2f obj;
		MatOfPoint2f sce;
		Mat obj_corners;
		Mat scene_corners;
		List<KeyPoint> keypoints_objectList;
		List<KeyPoint> keypoints_sceneList;
		double distancia_AB,distancia_AC,distancia_AD;
		double angulo_A,angulo_B,angulo_C,angulo_D;
		
		Log.d("Frente", "billetes:"+haystack.size());
		if(needle.getKFrente().toList().size()>Nfeatures){
			
			for(int j=0; j<haystack.size();j++){
				
				matches = new ArrayList<MatOfDMatch>();
				matcher.knnMatch(haystack.get(j).getDDorso(), needle.getDFrente(), matches, 2);
			   
				good_matches=new ArrayList<DMatch>();
				for(int i=0;i<matches.size();i++){
							
					if(matches.get(i).toList().get(0).distance < Pdistancia*matches.get(i).toList().get(1).distance)
						good_matches.add(matches.get(i).toList().get(0));
				}
				Log.d("Frente", "goog_matches:"+good_matches.size());			
				goodMatches = new MatOfDMatch();
				goodMatches.fromList(good_matches);
	
				Mat outImg=new Mat();
				MatOfByte drawnMatches = new MatOfByte();
				Features2d.drawMatches(haystack.get(j).getDorso(), haystack.get(j).getKDorso(), needle.getFrente(), needle.getKFrente(), goodMatches, outImg,Scalar.all(-1),Scalar.all(-1),drawnMatches,Features2d.NOT_DRAW_SINGLE_POINTS); 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		        String currentDateandTime = sdf.format(new Date());
		        String fileName = good_matches.size()+"_"+currentDateandTime + ".png";
				
		        if(good_matches.size()>Ngoodmatches){
					
					
					keypoints_objectList = new ArrayList<KeyPoint>();
					keypoints_sceneList = new ArrayList<KeyPoint>();
					keypoints_objectList = haystack.get(j).getKDorso().toList();
					keypoints_sceneList = needle.getKFrente().toList();
			
					objList = new LinkedList<Point>();
					sceneList = new LinkedList<Point>();
					for(int i = 0; i<good_matches.size(); i++){
					    objList.addLast(keypoints_objectList.get(good_matches.get(i).queryIdx).pt);
					    sceneList.addLast(keypoints_sceneList.get(good_matches.get(i).trainIdx).pt);
					}
					obj = new MatOfPoint2f();
					sce = new MatOfPoint2f();
					obj.fromList(objList);
					sce.fromList(sceneList);
					
					Mat hg = Calib3d.findHomography(obj, sce,Metodo,CotaRansac);
			
					obj_corners = new Mat(4,1,CvType.CV_32FC2);
					obj_corners.put(0, 0, new double[] {0,0});
					obj_corners.put(1, 0, new double[] {haystack.get(j).getDorso().cols(),0});
					obj_corners.put(2, 0, new double[] {haystack.get(j).getDorso().cols(),haystack.get(j).getDorso().rows()});
					obj_corners.put(3, 0, new double[] {0,haystack.get(j).getDorso().rows()});
					
					scene_corners = new Mat(4,1,CvType.CV_32FC2);
					Core.perspectiveTransform(obj_corners,scene_corners, hg);
		
					
					scene_corners.put(0,0,new double[] {scene_corners.get(0,0)[0]+haystack.get(j).getDorso().cols(),scene_corners.get(0,0)[1]});
			    	scene_corners.put(1,0,new double[] {scene_corners.get(1,0)[0]+haystack.get(j).getDorso().cols(),scene_corners.get(1,0)[1]});
			    	scene_corners.put(2,0,new double[] {scene_corners.get(2,0)[0]+haystack.get(j).getDorso().cols(),scene_corners.get(2,0)[1]});
			    	scene_corners.put(3,0,new double[] {scene_corners.get(3,0)[0]+haystack.get(j).getDorso().cols(),scene_corners.get(3,0)[1]});
			    					
					
					Point punto_A= new Point(scene_corners.get(0,0));
					Point punto_B= new Point(scene_corners.get(1,0));
					Point punto_C= new Point(scene_corners.get(2,0));
					Point punto_D= new Point(scene_corners.get(3,0));
	
					Core.line(outImg, punto_A, punto_B, new Scalar(0, 255, 0),4);
					Core.line(outImg, punto_B, punto_C, new Scalar(0, 255, 0),4);
					Core.line(outImg, punto_C, punto_D, new Scalar(0, 255, 0),4);
					Core.line(outImg, punto_D, punto_A, new Scalar(0, 255, 0),4);
	
						    	    
					distancia_AB=distancia(punto_A,punto_B);
					distancia_AC=distancia(punto_A,punto_C);
					distancia_AD=distancia(punto_A,punto_D);
			
					angulo_A=angulo(punto_A, punto_D,punto_B);
					angulo_B=angulo(punto_B, punto_A,punto_C);
					angulo_C=angulo(punto_C, punto_B,punto_D);
					angulo_D=angulo(punto_D, punto_C,punto_A);
					
					if((angulo_A < (3.1415 - 0.35)) && (angulo_A > 0.35) && 
							(angulo_B < (3.1415 - 0.35)) && (angulo_B > 0.35) &&
							(angulo_C < (3.1415 - 0.35)) && (angulo_C > 0.35) &&
							(angulo_D < (3.1415 - 0.35)) && (angulo_D > 0.35) && 
							distancia_AB > 100 && distancia_AC > 120 && distancia_AD > 50
							)
					{
						//Encontre un billete
						
						res=res+j+" ";
					}
				}
				SaveImage(outImg,fileName);
			}
		}
		return res;
	}

	
	@SuppressLint("SimpleDateFormat") public String reconocerFrentes(Billete needle, List<Billete> haystack) {
    	String res="";    	
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
		List<MatOfDMatch> matches;
		List<DMatch> good_matches;
		MatOfDMatch goodMatches;
		LinkedList<Point> objList;
		LinkedList<Point> sceneList;
		MatOfPoint2f obj;
		MatOfPoint2f sce;
		Mat obj_corners;
		Mat scene_corners;
		List<KeyPoint> keypoints_objectList;
		List<KeyPoint> keypoints_sceneList;
		double distancia_AB,distancia_AC,distancia_AD;
		double angulo_A,angulo_B,angulo_C,angulo_D;
		
		Log.d("Frente", "billetes:"+haystack.size());
		if(needle.getKFrente().toList().size()>Nfeatures){
			for(int j=0; j<haystack.size();j++){
				matches = new ArrayList<MatOfDMatch>();
				matcher.knnMatch(haystack.get(j).getDFrente(), needle.getDFrente(), matches, 2);
			   
				good_matches=new ArrayList<DMatch>();
				for(int i=0;i<matches.size();i++){
							
					if(matches.get(i).toList().get(0).distance < Pdistancia*matches.get(i).toList().get(1).distance)
						good_matches.add(matches.get(i).toList().get(0));
				}
				Log.d("Frente", "goog_matches:"+good_matches.size());			
				goodMatches = new MatOfDMatch();
				goodMatches.fromList(good_matches);
	
				Mat outImg=new Mat();
				MatOfByte drawnMatches = new MatOfByte();
				Features2d.drawMatches(haystack.get(j).getFrente(), haystack.get(j).getKFrente(), needle.getFrente(), needle.getKFrente(), goodMatches, outImg,Scalar.all(-1),Scalar.all(-1),drawnMatches,Features2d.NOT_DRAW_SINGLE_POINTS); 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		        String currentDateandTime = sdf.format(new Date());
		        String fileName = good_matches.size()+"_"+currentDateandTime + ".png";			
				
				if(good_matches.size()>Ngoodmatches){
					
					
					keypoints_objectList = new ArrayList<KeyPoint>();
					keypoints_sceneList = new ArrayList<KeyPoint>();
					keypoints_objectList = haystack.get(j).getKFrente().toList();
					keypoints_sceneList = needle.getKFrente().toList();
			
					objList = new LinkedList<Point>();
					sceneList = new LinkedList<Point>();
					for(int i = 0; i<good_matches.size(); i++){
					    objList.addLast(keypoints_objectList.get(good_matches.get(i).queryIdx).pt);
					    sceneList.addLast(keypoints_sceneList.get(good_matches.get(i).trainIdx).pt);
					}
					obj = new MatOfPoint2f();
					sce = new MatOfPoint2f();
					obj.fromList(objList);
					sce.fromList(sceneList);
					
					Mat hg = Calib3d.findHomography(obj, sce,Metodo,CotaRansac);
			
					obj_corners = new Mat(4,1,CvType.CV_32FC2);
					obj_corners.put(0, 0, new double[] {0,0});
					obj_corners.put(1, 0, new double[] {haystack.get(j).getFrente().cols(),0});
					obj_corners.put(2, 0, new double[] {haystack.get(j).getFrente().cols(),haystack.get(j).getFrente().rows()});
					obj_corners.put(3, 0, new double[] {0,haystack.get(j).getFrente().rows()});
					
					scene_corners = new Mat(4,1,CvType.CV_32FC2);
					Core.perspectiveTransform(obj_corners,scene_corners, hg);
		
					
					scene_corners.put(0,0,new double[] {scene_corners.get(0,0)[0]+haystack.get(j).getFrente().cols(),scene_corners.get(0,0)[1]});
			    	scene_corners.put(1,0,new double[] {scene_corners.get(1,0)[0]+haystack.get(j).getFrente().cols(),scene_corners.get(1,0)[1]});
			    	scene_corners.put(2,0,new double[] {scene_corners.get(2,0)[0]+haystack.get(j).getFrente().cols(),scene_corners.get(2,0)[1]});
			    	scene_corners.put(3,0,new double[] {scene_corners.get(3,0)[0]+haystack.get(j).getFrente().cols(),scene_corners.get(3,0)[1]});
			    					
					
					Point punto_A= new Point(scene_corners.get(0,0));
					Point punto_B= new Point(scene_corners.get(1,0));
					Point punto_C= new Point(scene_corners.get(2,0));
					Point punto_D= new Point(scene_corners.get(3,0));
					
					Core.line(outImg, punto_A, punto_B, new Scalar(0, 255, 0),4);
					Core.line(outImg, punto_B, punto_C, new Scalar(0, 255, 0),4);
					Core.line(outImg, punto_C, punto_D, new Scalar(0, 255, 0),4);
					Core.line(outImg, punto_D, punto_A, new Scalar(0, 255, 0),4);
			    					
					distancia_AB=distancia(punto_A,punto_B);
					distancia_AC=distancia(punto_A,punto_C);
					distancia_AD=distancia(punto_A,punto_D);
			
					angulo_A=angulo(punto_A, punto_D,punto_B);
					angulo_B=angulo(punto_B, punto_A,punto_C);
					angulo_C=angulo(punto_C, punto_B,punto_D);
					angulo_D=angulo(punto_D, punto_C,punto_A);
					
					if((angulo_A < (3.1415 - 0.35)) && (angulo_A > 0.35) && 
							(angulo_B < (3.1415 - 0.35)) && (angulo_B > 0.35) &&
							(angulo_C < (3.1415 - 0.35)) && (angulo_C > 0.35) &&
							(angulo_D < (3.1415 - 0.35)) && (angulo_D > 0.35) && 
							distancia_AB > 50 && distancia_AC > 60 && distancia_AD > 20
							)
					{
						//Encontre un billete
						res=res+j+" ";
					}
				}
				SaveImage(outImg,fileName);
			}
		}
		return res;
	}

	public double distancia(Point pt1,Point pt2) {
		double res;
		res=Math.sqrt(Math.pow(pt1.x - pt2.x, 2) + Math.pow(pt1.y - pt2.y, 2) );
		return res;
	}
	public static double angulo(Point uno,Point dos,Point tres){

		//transladamos al origen de coordenadas los tres puntos
		Point pi=new Point(dos.x-uno.x,dos.y-uno.y);
		Point pj=new Point(tres.x-uno.x,tres.y-uno.y);
		//calculamos su angulo de coordenada polar
		double ang_pi=Math.atan2((double)pi.x,(double)pi.y);
		double ang_pj=Math.atan2((double)pj.x,(double)pj.y);
		
		//hallamos la diferencia
		double ang=ang_pj-ang_pi;
		
		//Si el angulo es negativo le sumamos 2PI para obtener el
		//angulo en el intervalo [0-2PI]; 
		//siempre obtenemos ángulos positivos (en sentido antihorario)
		if (ang<0.0)
			return ang+(2.0*Math.PI);
		else
			return ang;
	}	
	

	
	
	
    
    public void SaveImage (Mat mat,String filename) {
    	  if(Debug){
	    	  Mat mIntermediateMat = new Mat();
	
	    	  Imgproc.cvtColor(mat, mIntermediateMat, Imgproc.COLOR_RGBA2BGR, 3);
	
	    	  File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    	  File file = new File(path, filename);
	
	    	  Boolean bool = null;
	    	  filename = file.toString();
	    	  bool = Highgui.imwrite(filename, mIntermediateMat);
	
	    	  if (bool == true)
	    	    Log.i(TAG, "SUCCESS writing image to external storage");
	    	  else
	    	    Log.i(TAG, "Fail writing image to external storage");
	    	  }
    	  }

}
