package gob.inti.reconocedordebilletes;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.*;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

public class ReconocedorDeBilletes extends Activity implements CvCameraViewListener2 {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reconocedor_de_billetes);
//    }
    private static final String TAG = "billete";

    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean              mIsJavaCamera = true;
    private MenuItem             mItemSwitchCamera = null;
    private int counter=0;
    private MediaPlayer player;
    private Mat template_imagen[]=new Mat[14];
    private Mat template_descriptors[]=new Mat[14];
    private MatOfKeyPoint template_keypoints[]=new MatOfKeyPoint[14];
    private boolean[] templates_llenos = new boolean[14];
       
    
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

        setContentView(gob.inti.reconocedordebilletes.R.layout.activity_reconocedor_de_billetes);
        if (mIsJavaCamera)
            mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.java_surface_view);
        else
            mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.native_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.SetCaptureFormat(Highgui.CV_CAP_ANDROID_GREY_FRAME);

        mOpenCvCameraView.setCvCameraViewListener(this);
              
        
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

    int i=-1;
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
            mOpenCvCameraView.SetCaptureFormat(Highgui.CV_CAP_ANDROID_GREY_FRAME);

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
    public void llenado(int idpesos){
    	this.templates_llenos[idpesos]=true;
   	
    }
     
    private class TareaProcesadorDeBilletes extends AsyncTask<Arguments, Void, Void> {

		@Override
		protected Void doInBackground(Arguments... params) {
			if(templates_llenos[0]==false){
				Log.i(TAG + "background", "lleno 1 esta en false");
				llenar_templates(0);
				llenado(0);
			}
			else
			{
				readmoney(params[0].imagen,0);
				Log.i(TAG + "background", "lleno 0 esta en true");
			}
			
			if(templates_llenos[1]==false){
				Log.i(TAG + "background", "lleno 1 esta en false");
				llenar_templates(1);
				llenado(1);
			}
			else
			{
				readmoney(params[0].imagen,1);
				Log.i(TAG + "background", "lleno 1 esta en true");
			}
		//////////////////////////////////////////
			/*
			if(templates_llenos[2]==false){
				Log.i(TAG + "background", "lleno 2 esta en false");
				llenar_templates(2);
				llenado(2);
			}
			else
			{
				readmoney(params[0].imagen,2);
				Log.i(TAG + "background", "lleno 2 esta en true");
			}
			if(templates_llenos[3]==false){
				Log.i(TAG + "background", "lleno 3 esta en false");
				llenar_templates(3);
				llenado(3);
			}
			else
			{
				readmoney(params[0].imagen,3);
				Log.i(TAG + "background", "lleno 3 esta en true");
			}
			*/
			
			
			
			return null;
		} 
    		
    }
    
    private class Arguments{
    	public Mat imagen;
    	public int idpesos;
    	public boolean lleno;
    	public Arguments(){
    		
    	}
    	
    }
   
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	this.counter++;
    	    	    	
    	if(counter%120==1){
    		Arguments params=new Arguments();
    		params.idpesos=0;
    		params.imagen=inputFrame.gray();
    		params.lleno=this.templates_llenos[0];
    		new TareaProcesadorDeBilletes().execute(params);
    	}
    	  	
    	
    	
       	return inputFrame.gray();
    }
    
    private void playsound(int idpesos){
    	switch (idpesos) {
    	 case 0:
    	 case 1: 
    		 this.player = MediaPlayer.create(this, R.raw.dospesos);
    		 this.player.start();
         break;
    	 case 2:
    	 case 3:
    		 this.player = MediaPlayer.create(this, R.raw.cincopesos);
    		 this.player.start();
    	 break;
    	 case 4:
    	 case 5:
    		 this.player = MediaPlayer.create(this, R.raw.diezpesos);
    		 this.player.start();
    	 break;
    	 case 6:
    	 case 7:
    		 this.player = MediaPlayer.create(this, R.raw.veintepesos);
    		 this.player.start();
    	 break;
    	 case 8:
    	 case 9:
    		 this.player = MediaPlayer.create(this, R.raw.cincuentapesos);
    		 this.player.start();
    	 break;
    	 case 10:
    	 case 11:
    	 case 12:
    	 case 13:
    		 this.player = MediaPlayer.create(this, R.raw.cienpesos);
    		 this.player.start();
    	 break;
    	 
    	 default: 
         break;
    	}
    	
    }
    
    /**
     * 
     */
    private void readmoney(Mat camara, int idpesos){
    	Mat template_imagen=this.template_imagen[idpesos];
		Mat scene=new Mat();
		scene=camara;
    	
    	Imgproc.medianBlur(scene, scene, 3);
    	
    	//estructuras para buscar keypoints
    	FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
    	DescriptorExtractor descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
    	DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
    	
    	try{
    	//para el template
    	Mat descriptors1 = this.template_descriptors[idpesos];
    	MatOfKeyPoint keypoints1 = this.template_keypoints[idpesos];

    	detector.detect(template_imagen, keypoints1);
    	descriptor.compute(template_imagen, keypoints1, descriptors1);
    	
    	//para la imagen de la camara
    	Mat descriptors2 = new Mat();
    	MatOfKeyPoint keypoints2 = new MatOfKeyPoint();

    	detector.detect(scene, keypoints2);
    	descriptor.compute(scene, keypoints2, descriptors2);
    	
    	//para buscar los matches
    	MatOfDMatch  matches = new MatOfDMatch();             
    	matcher.match(descriptors1,descriptors2,matches);
    	
    	
    	//otra forma de goodmatches
    	LinkedList<MatOfDMatch> matches2= new LinkedList<MatOfDMatch>(); 
    	matcher.knnMatch(descriptors1, descriptors2, matches2,2);
    	
    	Log.d(TAG+"knn", "cantidad:"+matches2.size());
    	
    	LinkedList<DMatch> good_matches2 = new LinkedList<DMatch>();
       	MatOfDMatch gm2 = new MatOfDMatch();
    	
    	for(int i=0;i< Math.min(descriptors2.rows()-1, matches2.size());i++){
    		List<DMatch> matchesList2 = matches2.get(i).toList();
    		if((matchesList2.get(0).distance< 0.8*matchesList2.get(1).distance) && matchesList2.size()<=2 && matchesList2.size()>0){
    			good_matches2.push(matchesList2.get(0));
    		}
    	}
    	
       	gm2.fromList(good_matches2);
    	
    	Mat img_matches = new Mat();
    	Features2d.drawMatches(
    	        template_imagen,
    	        keypoints1, 
    	        scene,
    	        keypoints2, 
    	        gm2, 
    	        img_matches, 
    	        new Scalar(255,0,0), 
    	        new Scalar(0,0,255), 
    	        new MatOfByte(), 
    	        2);
    	
    	
    	
    	//dibujo los corners
    	LinkedList<Point> objList = new LinkedList<Point>();
    	LinkedList<Point> sceneList = new LinkedList<Point>();

    	List<KeyPoint> keypoints_objectList = keypoints1.toList();
    	List<KeyPoint> keypoints_sceneList = keypoints2.toList();

    	for(int i = 0; i<good_matches2.size(); i++){
    	    objList.addLast(keypoints_objectList.get(good_matches2.get(i).queryIdx).pt);
    	    sceneList.addLast(keypoints_sceneList.get(good_matches2.get(i).trainIdx).pt);
    	}

    	MatOfPoint2f obj = new MatOfPoint2f();
    	obj.fromList(objList);

    	MatOfPoint2f scene_p = new MatOfPoint2f();
    	scene_p.fromList(sceneList);
        	
    	Mat hg = Calib3d.findHomography(obj, scene_p,8,5);

    	Mat obj_corners = new Mat(4,1,CvType.CV_32FC2);
    	Mat scene_corners = new Mat(4,1,CvType.CV_32FC2);

    	obj_corners.put(0, 0, new double[] {0,0});
    	obj_corners.put(1, 0, new double[] {template_imagen.cols(),0});
    	obj_corners.put(2, 0, new double[] {template_imagen.cols(),template_imagen.rows()});
    	obj_corners.put(3, 0, new double[] {0,template_imagen.rows()});

    	Core.perspectiveTransform(obj_corners,scene_corners, hg);
    	scene_corners.put(0,0,new double[] {scene_corners.get(0,0)[0]+template_imagen.cols(),scene_corners.get(0,0)[1]});
    	scene_corners.put(1,0,new double[] {scene_corners.get(1,0)[0]+template_imagen.cols(),scene_corners.get(1,0)[1]});
    	scene_corners.put(2,0,new double[] {scene_corners.get(2,0)[0]+template_imagen.cols(),scene_corners.get(2,0)[1]});
    	scene_corners.put(3,0,new double[] {scene_corners.get(3,0)[0]+template_imagen.cols(),scene_corners.get(3,0)[1]});
    	

    	
    	Core.line(img_matches, new Point(scene_corners.get(0,0)), new Point(scene_corners.get(1,0)), new Scalar(0, 255, 0),4);
    	Core.line(img_matches, new Point(scene_corners.get(1,0)), new Point(scene_corners.get(2,0)), new Scalar(0, 255, 0),4);
    	Core.line(img_matches, new Point(scene_corners.get(2,0)), new Point(scene_corners.get(3,0)), new Scalar(0, 255, 0),4);
    	Core.line(img_matches, new Point(scene_corners.get(3,0)), new Point(scene_corners.get(0,0)), new Scalar(0, 255, 0),4);
    	
    	
    	//ver si los corners forman un rectangulo
    	double d01=distancia(new Point(scene_corners.get(0,0)),new Point(scene_corners.get(1,0))),
    			d02=distancia(new Point(scene_corners.get(0,0)),new Point(scene_corners.get(2,0))),
    			d03=distancia(new Point(scene_corners.get(0,0)),new Point(scene_corners.get(3,0)));
    	
    	if(d01>200 && d02>250 && d03>100 && d03*2<d01){
    		playsound(idpesos);
    		SaveImage(img_matches,this.counter +"verdadero.png");
    	}else{
    		SaveImage(img_matches,this.counter +"falso.png");
    	}
    	}
    	catch (Exception ex)
    	{
    		ex.printStackTrace();
    		Log.d(TAG+"readmoney",ex.getMessage()+"    " );
    	}
    }
    
    /**
     * 
     * @param idpesos
     */
    private void llenar_templates(int idpesos){
    	Log.d(TAG+"llenar_templates", "paso 0");
    	Mat template_imagen=new Mat(),template_descriptors=new Mat();
    	FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
    	DescriptorExtractor descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
    	MatOfKeyPoint template_keypoints= new MatOfKeyPoint();
    	Log.d(TAG+"llenar_templates", "paso 1");
    	int  idrecursos[]=new int[14];
    	idrecursos[0]=R.raw.dosp;
    	idrecursos[1]=R.raw.dospd;
    	idrecursos[2]=R.raw.cincop;
    	idrecursos[3]=R.raw.cincopd;
    	idrecursos[4]=R.raw.diezp;
    	idrecursos[5]=R.raw.diezpd;
    	idrecursos[6]=R.raw.veintep;
    	idrecursos[7]=R.raw.veintepd;
    	idrecursos[8]=R.raw.cincuentap;
    	idrecursos[9]=R.raw.cincuentapd;
    	idrecursos[10]=R.raw.cienp;
    	idrecursos[11]=R.raw.cienpd;
    	idrecursos[12]=R.raw.cienevp;
    	idrecursos[13]=R.raw.cienevpd;
    	Log.d(TAG+"llenar_templates", "paso 2");
    	
    	Bitmap bMap=BitmapFactory.decodeResource(getResources(),idrecursos[idpesos]);
        Utils.bitmapToMat(bMap, template_imagen);
        Log.d(TAG+"llenar_templates", "paso 3");
        Imgproc.cvtColor(template_imagen, template_imagen, Imgproc.COLOR_BGR2GRAY);
        //Imgproc.threshold(template_imagen, template_imagen, 100, 255, Imgproc.THRESH_BINARY);
        Imgproc.medianBlur(template_imagen, template_imagen,3);
        SaveImage(template_imagen,this.counter+"blur.png");
        detector.detect(template_imagen, template_keypoints);
        descriptor.compute(template_imagen, template_keypoints, template_descriptors);
        
        Log.d(TAG+"llenar_templates", "paso 4");
        this.template_imagen[idpesos]=template_imagen;
        this.template_descriptors[idpesos]=template_descriptors;
        this.template_keypoints[idpesos]=template_keypoints;
        Log.d(TAG+"llenar_templates", "paso 5");  
    }
    
    /**
     * 
     * @param pt1
     * @param pt2
     * @return
     */
    private double distancia(Point pt1,Point pt2){
    	double res;
    	res=Math.sqrt(Math.pow(pt1.x - pt2.x, 2) + Math.pow(pt1.y - pt2.y, 2) );
    	return res;
    }
    
    /**
     * 
     * @param mat
     * @param name
     */
    public void SaveImage (Mat mat, String name) {
    	 
    	  File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    	  String filename = name;
    	  File file = new File(path, filename);

    	  Boolean bool = null;
    	  filename = file.toString();
    	  bool = Highgui.imwrite(filename, mat);

    	  if (bool == true)
    	    Log.d(TAG, "SUCCESS writing image to external storage");
    	  else
    	    Log.d(TAG, "Fail writing image to external storage");
    }
    
}

