package gob.inti.reconocedordebilletes;

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
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

public class HomographyMatcher implements IReconocedores {

	public FeatureDetector detector;
	public DescriptorExtractor extractor;
	public DescriptorMatcher matcher;
	public List<Billete> billetes;
	private int findHomographymethod;
	
	public void ProcesarTemplate(List<Billete> lb) throws NotEnougthKeypoints {
		billetes = lb;
		for (Billete billete : billetes) {
			ExtraerDescriptores(billete.bTemplate);
		}
	}
	

	/*
	 * procesa una imagen con un posible billete
	 */
	public List<EscenaProcesada> ProcesarImagen(Mat imagen) throws NotEnougthKeypoints {
		List<EscenaProcesada> res= new ArrayList<EscenaProcesada>();
		Escena esc = new Escena();
		long partialtime = 0;
		esc.ImagenOriginal = imagen;
		long startTime = System.currentTimeMillis();
		ExtraerDescriptores(esc);
		long endTime = System.currentTimeMillis();
		partialtime = endTime - startTime;
		Log.d("TIEMPOS", "extraccion de descriptores " + Long.toString(partialtime));
		for (Billete billete : billetes) {

			long startmacheo = System.currentTimeMillis();
			MatOfDMatch good_matches = SeleccionDeGoodMatches(esc,billete);
			EscenaProcesada e = determinarCorrespondencia(esc, billete,	good_matches);		
			long endmacheo = System.currentTimeMillis();
			Log.d("TIEMPOS", "macheo " + Long.toString(endmacheo - startmacheo));
			e.tiempoDeProcesamiento = partialtime + endmacheo - startmacheo; 
			Log.d("TIEMPOS", "t total de procesamiento " + Long.toString(e.tiempoDeProcesamiento));
			res.add(e);
		}
		esc.Destruir();
		return res;
	}
	
	protected EscenaProcesada determinarCorrespondencia(Escena esc,
			Billete billete, MatOfDMatch good_matches) {
		
		Mat img_matches = new Mat();
		if (AppConfig.DEBUG) {
			Features2d.drawMatches(billete.bTemplate.ImagenPrePocesada, billete.bTemplate.keypoints,esc.ImagenPrePocesada, esc.keypoints, good_matches, img_matches);
		}  
		
		LinkedList<Point> objList = new LinkedList<Point>();
		LinkedList<Point> sceneList = new LinkedList<Point>();
		List<DMatch> good_matches_list = good_matches.toList();
		
		List<KeyPoint> keypoints_objectList = billete.bTemplate.keypoints.toList();
		List<KeyPoint> keypoints_sceneList = esc.keypoints.toList();

		for(int i = 0; i<good_matches_list.size(); i++)
		{
		    objList.addLast(keypoints_objectList.get(good_matches_list.get(i).queryIdx).pt);
		    sceneList.addLast(keypoints_sceneList.get(good_matches_list.get(i).trainIdx).pt);
		}

		MatOfPoint2f obj = new MatOfPoint2f();
		obj.fromList(objList);

		MatOfPoint2f scene = new MatOfPoint2f();
		scene.fromList(sceneList);
		
		//TODO: VER DE PROBAR OTROS ALGORITMOS aparte del Calib3d.RANSAC
		Mat hg = Calib3d.findHomography(obj, scene, findHomographymethod, 5);

		Mat obj_corners = new Mat(4,1,CvType.CV_32FC2);
		Mat scene_corners = new Mat(4,1,CvType.CV_32FC2);

		obj_corners.put(0, 0, new double[] {0,0});
		obj_corners.put(1, 0, new double[] {billete.bTemplate.ImagenPrePocesada.cols(),0});
		obj_corners.put(2, 0, new double[] {billete.bTemplate.ImagenPrePocesada.cols(),billete.bTemplate.ImagenPrePocesada.rows()});
		obj_corners.put(3, 0, new double[] {0,billete.bTemplate.ImagenPrePocesada.rows()});

		Core.perspectiveTransform(obj_corners,scene_corners, hg);
		scene_corners.put(0,0,new double[] {scene_corners.get(0,0)[0]+billete.bTemplate.ImagenPrePocesada.cols(),scene_corners.get(0,0)[1]});
		scene_corners.put(1,0,new double[] {scene_corners.get(1,0)[0]+billete.bTemplate.ImagenPrePocesada.cols(),scene_corners.get(1,0)[1]});
		scene_corners.put(2,0,new double[] {scene_corners.get(2,0)[0]+billete.bTemplate.ImagenPrePocesada.cols(),scene_corners.get(2,0)[1]});
		scene_corners.put(3,0,new double[] {scene_corners.get(3,0)[0]+billete.bTemplate.ImagenPrePocesada.cols(),scene_corners.get(3,0)[1]});
   
		Point punto_A= new Point(scene_corners.get(0,0));
		Point punto_B= new Point(scene_corners.get(1,0));
		Point punto_C= new Point(scene_corners.get(2,0));
		Point punto_D= new Point(scene_corners.get(3,0));
		
		if (AppConfig.DEBUG) {
			Core.line(img_matches, punto_A, punto_B, new Scalar(0, 255, 0),4);
			Core.line(img_matches, punto_B, punto_C, new Scalar(0, 255, 0),4);
			Core.line(img_matches, punto_C, punto_D, new Scalar(0, 255, 0),4);
			Core.line(img_matches, punto_D, punto_A, new Scalar(0, 255, 0),4);
		}
		
		double distancia_AB,distancia_AC,distancia_AD;
		double angulo_A,angulo_B,angulo_C,angulo_D;
			    	    
		distancia_AB=distancia(punto_A,punto_B);
		distancia_AC=distancia(punto_A,punto_C);
		distancia_AD=distancia(punto_A,punto_D);

		angulo_A=angulo(punto_A, punto_D,punto_B);
		angulo_B=angulo(punto_B, punto_A,punto_C);
		angulo_C=angulo(punto_C, punto_B,punto_D);
		angulo_D=angulo(punto_D, punto_C,punto_A);
		
		String debug= " A_A="+angulo_A+" A_B="+angulo_B+" A_C="+angulo_C+" A_D="+angulo_D;
		
		if (AppConfig.DEBUG) {
		Core.putText(img_matches, debug, new Point(10, 100), 3, 0.5, new Scalar(0, 0, 255, 255),1);
		}
		
		EscenaProcesada ep = new EscenaProcesada();
		ep.Contraparete = billete;
		if (AppConfig.DEBUG) {
		ep.imagenDelMacheo = img_matches;
		}
		if((angulo_A < (3.1415 - 0.35)) && (angulo_A > 0.35) && 
				(angulo_B < (3.1415 - 0.35)) && (angulo_B > 0.35) &&
				(angulo_C < (3.1415 - 0.35)) && (angulo_C > 0.35) &&
				(angulo_D < (3.1415 - 0.35)) && (angulo_D > 0.35) && 
				distancia_AB > 100 && distancia_AC > 120 && distancia_AD > 50
				)
		{
			ep.correspondencia = true;
			
			SaveImage(img_matches, "verdaderos");
		}
		else
		{
		    ep.correspondencia = false;
		    SaveImage(img_matches, "falsos");
		}
		return ep;
	}
	
    @SuppressLint("SimpleDateFormat")
	private void SaveImage (Mat mat,String name ) {
    	if (AppConfig.DEBUG) {
	  	  File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	  	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	  	  Date now = new Date();
	  	  
	  	  String filename = formatter.format(now) + name +".jpg";
	  	  File file = new File(path, filename);
	
	  	  Boolean bool = null;
	  	  filename = file.toString();
	  	  bool = Highgui.imwrite(filename,mat);
	  	  
	  	  if (bool!=true) throw new AssertionError("No guardo la imagen");
    	}
  	 }
	
	private double distancia(Point pt1,Point pt2){
    	double res;
    	res=Math.sqrt(Math.pow(pt1.x - pt2.x, 2) + Math.pow(pt1.y - pt2.y, 2) );
    	return res;
    }
	
	/**
    Funcion que calcula la diferencia de angulos entre dos rectas formadas
    por tres puntos a->b y a->c , es decir el angulo formado por las
    dos rectas (ang(a->c)-ang(a->b)), pero siempre positivo.
    Por lo tanto, obtenemos el angulo en sentido antihorario.
    @param uno : Primer punto.
    @param dos : Segundo punto.
    @param tres : Tercer punto.
    @return : Angulo en radianes [0-2PI] con la diferencia entre las
    rectas uno-tres y uno-dos.
    */

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
    }//fin angulo
	 
	/*
	 * (non-Javadoc)
	 * @see gob.inti.reconocedordebilletes.IReconocedores#Inicializar(int)
	 */
	public void Inicializar(int config) {
		
		//TODO:Probar con otros metodos...
		findHomographymethod = Calib3d.RANSAC;
		
		switch (config) {
		//ORB,ORB,BRUTEFORCE anda
		case 0:
			detector = FeatureDetector.create(FeatureDetector.ORB);
			extractor= DescriptorExtractor.create(DescriptorExtractor.ORB);
			matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
			break;

		//FAST,ORB,BRUTEFORCE no anda
		case 1:
			detector = FeatureDetector.create(FeatureDetector.FAST);
			extractor= DescriptorExtractor.create(DescriptorExtractor.ORB);
			matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
			break;
		
		//FAST,FREAK,BRUTEFORCE no anda
		case 2:
			detector = FeatureDetector.create(FeatureDetector.FAST);
			extractor= DescriptorExtractor.create(DescriptorExtractor.FREAK);
			matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
			break;
		
		//ORB,BRIEF,BRUTEFORCE anda
		case 3:
			detector = FeatureDetector.create(FeatureDetector.ORB);
			extractor= DescriptorExtractor.create(DescriptorExtractor.BRIEF);
			matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
			break;
						
		//FAST,BRIEF,BRUTEFORCE no anda
		case 4:
			detector = FeatureDetector.create(FeatureDetector.FAST);
			extractor= DescriptorExtractor.create(DescriptorExtractor.BRIEF);
			matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
			break;
			
		default:
			break;
		}

	}
	
	private void Preprocess(Escena esc){
		
		Imgproc.cvtColor(esc.ImagenOriginal, esc.ImagenPrePocesada, Imgproc.COLOR_BGR2GRAY,CvType.CV_8UC1);
		
	}
	
	private void Detectar(Escena esc) {
		
		detector.detect(esc.ImagenPrePocesada, esc.keypoints);
	}
	private void Detectar(Template temp) {
		detector.detect(temp.ImagenPrePocesada, temp.keypoints,temp.Mascara);

	}
	protected void ExtraerDescriptores(Escena esc) throws NotEnougthKeypoints {
		Preprocess(esc);
		//TODO hacer que sea consciente del tipo de dato;
		/*if (esc instanceof Billete ) {
			Detectar((Billete)esc);
		}
		else
		{*/
		Detectar(esc);
		int cke=esc.keypoints.toList().size();
		if (cke > 50)
		{
			extractor.compute(esc.ImagenPrePocesada,esc.keypoints,esc.Descriptores);
		}
		else
		{
			throw new NotEnougthKeypoints();
		}
	
	}
	private void ExtraerDescriptores(Template esc) throws NotEnougthKeypoints {
		Preprocess(esc);
		//TODO hacer que sea consciente del tipo de dato;
		/*if (esc instanceof Billete ) {
			Detectar((Billete)esc);
		}
		else
		{*/
		Detectar(esc);
		int cke=esc.keypoints.toList().size();
		if (cke > 50)
		{
			extractor.compute(esc.ImagenPrePocesada,esc.keypoints,esc.Descriptores);
		}
		else
		{
			throw new NotEnougthKeypoints();
		}
	
	}
	
	protected MatOfDMatch SeleccionDeGoodMatches (Escena esc,Billete b) {
		MatOfDMatch good_matches = new MatOfDMatch();
		//knnMatch
		
			
			List<MatOfDMatch> matches = new ArrayList<MatOfDMatch>();
		    matcher.knnMatch( b.bTemplate.Descriptores, esc.Descriptores, matches, 2 );
		    
			List<DMatch> goodmatcheslist = new ArrayList<DMatch>();
		    for (int i = 0; i < matches.size(); ++i)
		    { 
		    	if(matches.get(i).toArray().length > 1){
		    	
		    		DMatch m1=new DMatch();
		    		DMatch m2=new DMatch();
		    		m1=matches.get(i).toArray()[0];
		    		m2=matches.get(i).toArray()[1];
				    if(m1.distance>m2.distance*0.6)goodmatcheslist.add(m1);
		    	}
		    }
		    good_matches.fromList(goodmatcheslist);
						    		    
	    return good_matches;
	}

	

}
