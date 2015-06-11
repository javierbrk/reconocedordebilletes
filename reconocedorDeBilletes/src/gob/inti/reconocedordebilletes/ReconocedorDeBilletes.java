package gob.inti.reconocedordebilletes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

public class ReconocedorDeBilletes extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";


    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean              mIsJavaCamera = true;
    private MenuItem             mItemSwitchCamera = null;
    //private HomographyMatcher    hm;
    private ThresholdHomographyMatcher    hm;
    private int					mCount=0;
    

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public ReconocedorDeBilletes() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_reconocedor_de_billetes);

        if (mIsJavaCamera)
            mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.java_surface_view);
        else
            mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.native_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);

    }

    private void cargartemplates() throws NotEnougthKeypoints {
		// TODO Auto-generated method stub
    	ArrayList<Billete> lb= new ArrayList<Billete>();
    	for (EDenominacionBilletes denominacion : EDenominacionBilletes.values()) 
    	{
			
			Billete b = new Billete(this,sound(denominacion.value())); 
			Mat mask = new Mat(),mask_gray = new Mat();
			
			Bitmap bMap0=BitmapFactory.decodeResource(getResources(),templateimg(denominacion.value()));
			Utils.bitmapToMat(bMap0, b.bTemplate.ImagenOriginal);
			
			Bitmap bMap2=BitmapFactory.decodeResource(getResources(),maskimg(denominacion.value()));
		    Utils.bitmapToMat(bMap2, mask);
		    Imgproc.cvtColor(mask, mask_gray, Imgproc.COLOR_BGR2GRAY,CvType.CV_8UC1);
			b.bTemplate.Mascara = mask_gray;
		    
			b.denominacion = denominacion;
			
			lb.add(b);
		}
		hm.ProcesarTemplate(lb);
	}

	@Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
        	
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemSwitchCamera = menu.add("Toggle Native/Java camera");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String toastMesage = new String();
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);

        if (item == mItemSwitchCamera) {
            mOpenCvCameraView.setVisibility(SurfaceView.GONE);
            mIsJavaCamera = !mIsJavaCamera;

            if (mIsJavaCamera) {
                mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.java_surface_view);
                toastMesage = "Java Camera";
            } else {
                mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.native_surface_view);
                toastMesage = "Native Camera";
            }

            mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
            mOpenCvCameraView.setCvCameraViewListener(this);
            mOpenCvCameraView.enableView();
            Toast toast = Toast.makeText(this, toastMesage, Toast.LENGTH_LONG);
            toast.show();
        }

        return true;
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        
        ////software
    	
    	mCount++;
    	if(mCount == 5)
    	{
	        //hm = new HomographyMatcher();
    		hm = new ThresholdHomographyMatcher();
	        //Solo funcionan las configuraciones 0 y 3 para inicializar	        
	        hm.Inicializar(0);
	        try {
				cargartemplates();
			} catch (NotEnougthKeypoints e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if(mCount>27 && mCount<457){
    		List<EscenaProcesada> lesc;
			try {
				lesc = hm.ProcesarImagen(inputFrame.rgba());
				for (EscenaProcesada escenaProcesada : lesc) {
					if (escenaProcesada.correspondencia)
					{
						escenaProcesada.Contraparete.anunciate();
					}
				}
			} catch (NotEnougthKeypoints e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
			
	        return inputFrame.rgba();
    	}else{
    		return inputFrame.gray();
    	}
        
    }
//    private boolean reconocerp(int idpesos, Mat gray) {
//		Mat scene_gray=new Mat(),template= new Mat(),template_gray= new Mat(),mask= new Mat(),mask_gray= new Mat();
//		
//		
//		
//		Bitmap bMap0=BitmapFactory.decodeResource(getResources(),templateimg(idpesos));
//        Utils.bitmapToMat(bMap0, template);
//        Imgproc.cvtColor(template, template_gray, Imgproc.COLOR_BGR2GRAY,CvType.CV_8UC1);
//          
//        Bitmap bMap2=BitmapFactory.decodeResource(getResources(),maskimg(idpesos));
//	    Utils.bitmapToMat(bMap2, mask);
//	    Imgproc.cvtColor(mask, mask_gray, Imgproc.COLOR_BGR2GRAY,CvType.CV_8UC1);
//	    
//	    scene_gray=gray;
//	    
//	    
//	    MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
//	    MatOfKeyPoint keypoints_scene = new MatOfKeyPoint();
//	    Mat descriptors_object = new Mat();
//	    Mat descriptors_scene = new Mat();
//	    
//	    //ORB es el numero 5 para detector de features
//	    FeatureDetector fd = FeatureDetector.create(FeatureDetector.ORB);
//	    
//	    fd.detect(scene_gray, keypoints_scene);
//	    fd.detect(template_gray, keypoints_object,mask_gray);
//	    
//	    
//	    
//	    int cke=keypoints_scene.toList().size(),ckt=keypoints_object.toList().size();
//	    
//	    
//	    	    
//	    if(cke>50 && ckt>50){
//	    	//sigo trabajando
//			    
//		    DescriptorExtractor extractor= DescriptorExtractor.create(DescriptorExtractor.ORB);
//		    
//		    extractor.compute(scene_gray, keypoints_scene, descriptors_scene);
//		    extractor.compute(template_gray, keypoints_object, descriptors_object);
//		
//		    DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
//		    
//		    MatOfDMatch good_matches = new MatOfDMatch();
//		    		    
//		    List<MatOfDMatch> matches=new ArrayList<MatOfDMatch>();
//		    matcher.knnMatch( descriptors_object, descriptors_scene, matches, 2 );
//		    
//		    List<DMatch> goodmatcheslist = new ArrayList<DMatch>();
//		    for (int i = 0; i < matches.size(); ++i)
//		    { 
//		    	if(matches.get(i).toArray().length > 1){
//		    	
//		    		DMatch m1=new DMatch();
//		    		DMatch m2=new DMatch();
//		    		m1=matches.get(i).toArray()[0];
//		    		m2=matches.get(i).toArray()[1];
//				    if(m1.distance>m2.distance*0.6)goodmatcheslist.add(m1);
//		    	}
//		    }
//		    		    		    
//		    good_matches.fromList(goodmatcheslist);
//		   	   	    
//		    Mat img_matches = new Mat();
//		    Features2d.drawMatches(template_gray, keypoints_object,scene_gray, keypoints_scene, good_matches, img_matches);
//			   
//		    LinkedList<Point> objList = new LinkedList<Point>();
//		    LinkedList<Point> sceneList = new LinkedList<Point>();
//		    List<DMatch> good_matches_list = good_matches.toList();
//	
//		    List<KeyPoint> keypoints_objectList = keypoints_object.toList();
//		    List<KeyPoint> keypoints_sceneList = keypoints_scene.toList();
//	
//		    for(int i = 0; i<good_matches_list.size(); i++)
//		    {
//		        objList.addLast(keypoints_objectList.get(good_matches_list.get(i).queryIdx).pt);
//		        sceneList.addLast(keypoints_sceneList.get(good_matches_list.get(i).trainIdx).pt);
//		    }
//	
//		    MatOfPoint2f obj = new MatOfPoint2f();
//		    obj.fromList(objList);
//	
//		    MatOfPoint2f scene = new MatOfPoint2f();
//		    scene.fromList(sceneList);
//	
//		    Mat hg = Calib3d.findHomography(obj, scene, Calib3d.RANSAC, 5);
//	
//		    Mat obj_corners = new Mat(4,1,CvType.CV_32FC2);
//		    Mat scene_corners = new Mat(4,1,CvType.CV_32FC2);
//	
//		    obj_corners.put(0, 0, new double[] {0,0});
//		    obj_corners.put(1, 0, new double[] {template_gray.cols(),0});
//		    obj_corners.put(2, 0, new double[] {template_gray.cols(),template_gray.rows()});
//		    obj_corners.put(3, 0, new double[] {0,template_gray.rows()});
//	
//		    Core.perspectiveTransform(obj_corners,scene_corners, hg);
//		    scene_corners.put(0,0,new double[] {scene_corners.get(0,0)[0]+template_gray.cols(),scene_corners.get(0,0)[1]});
//		    scene_corners.put(1,0,new double[] {scene_corners.get(1,0)[0]+template_gray.cols(),scene_corners.get(1,0)[1]});
//		    scene_corners.put(2,0,new double[] {scene_corners.get(2,0)[0]+template_gray.cols(),scene_corners.get(2,0)[1]});
//		    scene_corners.put(3,0,new double[] {scene_corners.get(3,0)[0]+template_gray.cols(),scene_corners.get(3,0)[1]});
//	    
//		    Point punto_A= new Point(scene_corners.get(0,0));
//		    Point punto_B= new Point(scene_corners.get(1,0));
//		    Point punto_C= new Point(scene_corners.get(2,0));
//		    Point punto_D= new Point(scene_corners.get(3,0));
//		   
//		    Core.line(img_matches, punto_A, punto_B, new Scalar(0, 255, 0),4);
//		    Core.line(img_matches, punto_B, punto_C, new Scalar(0, 255, 0),4);
//		    Core.line(img_matches, punto_C, punto_D, new Scalar(0, 255, 0),4);
//		    Core.line(img_matches, punto_D, punto_A, new Scalar(0, 255, 0),4);
//		    
//		    double distancia_AB,distancia_AC,distancia_AD;
//		    double angulo_A,angulo_B,angulo_C,angulo_D;
//		    	    	    
//		    distancia_AB=distancia(punto_A,punto_B);
//		    distancia_AC=distancia(punto_A,punto_C);
//		    distancia_AD=distancia(punto_A,punto_D);
//	
//		    angulo_A=angulo(punto_A, punto_D,punto_B);
//		    angulo_B=angulo(punto_B, punto_A,punto_C);
//		    angulo_C=angulo(punto_C, punto_B,punto_D);
//		    angulo_D=angulo(punto_D, punto_C,punto_A);
//		    
//		    String debug= " A_A="+angulo_A+" A_B="+angulo_B+" A_C="+angulo_C+" A_D="+angulo_D;
//		    
//		    Core.putText(img_matches, debug, new Point(10, 100), 3, 0.5, new Scalar(0, 0, 255, 255),1);
//		    
//		    if((angulo_A < (3.1415 - 0.35)) && (angulo_A > 0.35) && 
//		    		(angulo_B < (3.1415 - 0.35)) && (angulo_B > 0.35) &&
//		    		(angulo_C < (3.1415 - 0.35)) && (angulo_C > 0.35) &&
//		    		(angulo_D < (3.1415 - 0.35)) && (angulo_D > 0.35) && 
//		    		distancia_AB > 100 && distancia_AC > 120 && distancia_AD > 50
//		    		){
//		    					
//		    				SaveImage(img_matches, "verdaderos");
//		    				scene_gray.release();
//		    		    	template.release();
//		    		    	template_gray.release();
//		    		    	mask.release();
//		    		    	mask_gray.release();
//		    				return true;
//		    	
//		    	
//		    }
//		    
//		    SaveImage(img_matches, "falsos");
//		    
//		    scene_gray.release();
//	    	template.release();
//	    	template_gray.release();
//	    	mask.release();
//	    	mask_gray.release();
//			
//	    	
//	    	return false;
//	    
//	    }else{
//	    	return false;
//	    }
//	}
    
    private void paper_dolar(Mat scene,Mat template){
    	
    	Mat g_b_scene=new Mat(), g_b_template=new Mat();
    	Imgproc.GaussianBlur(scene, g_b_scene, new Size(3,3), 0.2, 0.2, Imgproc.BORDER_DEFAULT);
	    Imgproc.GaussianBlur(template, g_b_template, new Size(3,3), 0.2, 0.2, Imgproc.BORDER_DEFAULT);
	    
	    Mat canny_scene=new Mat(), canny_template=new Mat();
	    Imgproc.Canny(g_b_scene, canny_scene, 80, 100);
	    Imgproc.Canny(g_b_template, canny_template, 80, 100);
	    
	    Mat element3= new Mat(3,3,CvType.CV_8U,new Scalar(1));
	    
	    Imgproc.morphologyEx(canny_scene,scene,Imgproc.MORPH_CLOSE,element3);
	    Imgproc.morphologyEx(canny_template,template,Imgproc.MORPH_CLOSE,element3);
    	    
    }
    private void paper_euro(Mat scene, Mat template){
    	
    	Mat b_f_scene=new Mat(), b_f_template=new Mat();
    	Imgproc.bilateralFilter(scene, b_f_scene, 3, 75, 75);
	    Imgproc.bilateralFilter(template, b_f_template, 3, 75, 75);
    	
	    Imgproc.equalizeHist(b_f_scene,scene);
	    Imgproc.equalizeHist(b_f_template,template);
	    
    }
    
//    private double distancia(Point pt1,Point pt2){
//    	double res;
//    	res=Math.sqrt(Math.pow(pt1.x - pt2.x, 2) + Math.pow(pt1.y - pt2.y, 2) );
//    	return res;
//    }
//    /**
//    Funcion que calcula la diferencia de angulos entre dos rectas formadas
//    por tres puntos a->b y a->c , es decir el angulo formado por las
//    dos rectas (ang(a->c)-ang(a->b)), pero siempre positivo.
//    Por lo tanto, obtenemos el angulo en sentido antihorario.
//    @param uno : Primer punto.
//    @param dos : Segundo punto.
//    @param tres : Tercer punto.
//    @return : Angulo en radianes [0-2PI] con la diferencia entre las
//    rectas uno-tres y uno-dos.
//    */
//
//    public static double angulo(Point uno,Point dos,Point tres){
//
//    //transladamos al origen de coordenadas los tres puntos
//    Point pi=new Point(dos.x-uno.x,dos.y-uno.y);
//    Point pj=new Point(tres.x-uno.x,tres.y-uno.y);
//    //calculamos su angulo de coordenada polar
//    double ang_pi=Math.atan2((double)pi.x,(double)pi.y);
//    double ang_pj=Math.atan2((double)pj.x,(double)pj.y);
//
//    //hallamos la diferencia
//    double ang=ang_pj-ang_pi;
//
//    //Si el angulo es negativo le sumamos 2PI para obtener el
//    //angulo en el intervalo [0-2PI]; 
//    //siempre obtenemos ángulos positivos (en sentido antihorario)
//    if (ang<0.0)
//    	return ang+(2.0*Math.PI);
//    else
//    	return ang;
//    }//fin angulo
//    
//
//    @SuppressLint("SimpleDateFormat")
//	private void SaveImage (Mat mat,String name ) {
//  	  
//  	  File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//  	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//  	  Date now = new Date();
//  	  
//  	  String filename = formatter.format(now) + name +".jpg";
//  	  File file = new File(path, filename);
//
//  	  Boolean bool = null;
//  	  filename = file.toString();
//  	  bool = Highgui.imwrite(filename,mat);
//  	  
//  	  if (bool!=true) throw new AssertionError("No guardo la imagen");
//  	 }
    private int templateimg(int idpesos){
    	int res=0;
    	switch (idpesos) {
	   	 case 0:
	   		 res=R.raw.dosp;
	   		 break;
	   	 case 1: 
	   		res=R.raw.dospd;
	   		break;
	   	 case 2:
	   		res= R.raw.cincop;
	   		break;
	   	 case 3:
	   		res= R.raw.cincopd;
	   	 break;
	   	 case 4:
	   		res= R.raw.diezp;
	   		break;
	   	 case 5:
	   		res= R.raw.diezpd;
	   		break;
	   	 case 6:
	   		res= R.raw.veintep;
	   		break;
	   	 case 7:
	   		res= R.raw.veintepd;
	   		 break;
	   	 case 8:
	   		res= R.raw.cincuentap;
	   		break;
	   	 case 9:
	   		res= R.raw.cincuentapd;
	   	 break;
	   	 case 10:
	   		res= R.raw.cienp;
	   		break;
	   	 case 11:
	   		res= R.raw.cienpd;
	   		break;
	   	 case 12:
	   		 res= R.raw.cienevp;
	   		break;
	   	 case 13:
	   		res= R.raw.cienevpd;
	   		 break;
	   	 default: 
	        break;
    	}
    	return res;
    }
    private int maskimg(int idpesos){
    	int res=0;
    	switch (idpesos) {
	   	 case 0:
	   		 res=R.raw.dosp_mask;
	   		 break;
	   	 case 1: 
	   		res=R.raw.dospd_mask;
	   		break;
	   	 case 2:
	   		res= R.raw.cincop_mask;
	   		break;
	   	 case 3:
	   		res= R.raw.cincopd_mask;
	   	 break;
	   	 case 4:
	   		res= R.raw.diezp_mask;
	   		break;
	   	 case 5:
	   		res= R.raw.diezpd_mask;
	   		break;
	   	 case 6:
	   		res= R.raw.veintep_mask;
	   		break;
	   	 case 7:
	   		res= R.raw.veintepd_mask;
	   		 break;
	   	 case 8:
	   		res= R.raw.cincuentap_mask;
	   		break;
	   	 case 9:
	   		res= R.raw.cincuentapd_mask;
	   	 break;
	   	 case 10:
	   		res= R.raw.cienp_mask;
	   		break;
	   	 case 11:
	   		res= R.raw.cienpd_mask;
	   		break;
	   	 case 12:
	   		 res= R.raw.cienevp_mask;
	   		break;
	   	 case 13:
	   		res= R.raw.cienevpd_mask;
	   		 break;
	   	 default: 
	        break;
    	}
    	return res;
    }
    
	private int sound(int idpesos){
    	switch (idpesos) {
    	 case 0:
    	 case 1: 
    		 return(R.raw.dospesos);
    	  
    	 case 2:
    	 case 3:
    		 return(R.raw.cincopesos);

    	 case 4:
    	 case 5:
    		 return(R.raw.diezpesos);
    		 
    	 case 6:
    	 case 7:
    		 return( R.raw.veintepesos);


    	 case 8:
    	 case 9:
    		 return(R.raw.cincuentapesos);

    	 case 10:
    	 case 11:
    	 case 12:
    	 case 13:
    		 return(R.raw.cienpesos);
    	 default: 
         return 0;
    	}
    	
    }
	
	static Comparator<DMatch> ascOrder = new Comparator<DMatch>() {

	    public int compare(DMatch arg0, DMatch arg1) {
	        
	    	return Double.compare(arg1.distance, arg0.distance);

	    }
	};
	
    
}