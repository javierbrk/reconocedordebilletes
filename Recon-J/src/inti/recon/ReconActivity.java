package inti.recon;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
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
import inti.recon.R;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Toast;

public class ReconActivity extends Activity implements CvCameraViewListener2, OnTouchListener {
    private static final String TAG = "OCVSample::Activity";
    private static final double Pdistancia = 0.7;
    private static final int Ngoodmatches = 3;
    public static final int Ancho = 800;
    public static final int Alto = 600;
    private static final int Metodo = 8;//0 usa todos los goodmatches, 8 RANSAC usa solo los que cumplen la reprojecccion a una distancia menor de una cota
    private static final double CotaRansac = 10;//cota de RANSAC desde 1 a 10 pixels generalmente
    private static final boolean Debug = false;
    
    
    
    private ReconView mOpenCvCameraView;
    private List<Size> mResolutionList;
    private MenuItem[] mEffectMenuItems;
    private SubMenu mColorEffectsMenu;
    private MenuItem[] mResolutionMenuItems;
    private SubMenu mResolutionMenu;
    public Billete Escena_actual;
    public List<Billete> billetes;
    private int[] ID_Templates={
    		R.raw.dosp,
    		R.raw.dospd,
    		R.raw.cincop,
    		R.raw.cincopd,
    		R.raw.diezp,
    		R.raw.diezpd,
    		R.raw.veintep,
    		R.raw.veintepd,
    		R.raw.cincuentap,
    		R.raw.cincuentapd,
    		R.raw.cincuentamalp,
    		R.raw.cincuentamalpd,
    		R.raw.cienp,
    		R.raw.cienpd,
    		R.raw.cienevp,
    		R.raw.cienevpd};
    private int[] ID_Denominacion={
    		R.raw.dospesos,
    		R.raw.dospesos,
    		R.raw.cincopesos,
    		R.raw.cincopesos,
    		R.raw.diezpesos,
    		R.raw.diezpesos,
    		R.raw.veintepesos,
    		R.raw.veintepesos,
    		R.raw.cincuentapesos,
    		R.raw.cincuentapesos,
    		R.raw.cincuentapesos,
    		R.raw.cincuentapesos,
    		R.raw.cienpesos,
    		R.raw.cienpesos,
    		R.raw.cienpesos,
    		R.raw.cienpesos,
    		R.raw.cienpesos};
    
    private boolean touched=false;
    private Mat srcRGBA;
    private Mat srcRGBA2;
    
    	
    @SuppressLint("ClickableViewAccessibility") private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(ReconActivity.this);
                    billetes=new ArrayList<Billete>();
                    llenar_lista_billetes();
                           
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }

		private void llenar_lista_billetes() {
			for (int i=0;i<ID_Templates.length;i=i+2){
				try {
	            	srcRGBA = new Mat(); //RGBA format
                    Imgproc.cvtColor(Utils.loadResource(ReconActivity.this, ID_Templates[i]), srcRGBA, Imgproc.COLOR_BGR2GRAY);
                    srcRGBA2 = new Mat(); //RGBA format
                    Imgproc.cvtColor(Utils.loadResource(ReconActivity.this, ID_Templates[i+1]), srcRGBA2, Imgproc.COLOR_BGR2GRAY);
                    billetes.add(new Billete(ReconActivity.this,srcRGBA,srcRGBA2,ID_Denominacion[i]));
                    
	                
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
		}
    };

    public ReconActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.tutorial3_surface_view);

        mOpenCvCameraView = (ReconView) findViewById(R.id.tutorial3_activity_java_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

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

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	 //Mat rgba = inputFrame.gray();
    	String resf="";
    	String resd="";
    	Mat rgba=new Mat();
    	org.opencv.core.Size dzise=new org.opencv.core.Size(Ancho,Alto);
    	Imgproc.resize(inputFrame.gray(),rgba,dzise);
         if ( touched ) {
        	 Escena_actual=new Billete(ReconActivity.this, rgba, rgba, ID_Denominacion[0]);
        	        	 
        	 resf=reconocerFrentes();
        	 resd=reconocerDorsos();
        	         	 
        	 touched = false;
         }
         return inputFrame.rgba();
         
    }
    @SuppressLint("SimpleDateFormat") public String reconocerDorsos() {
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
		
		Log.d("Frente", "billetes:"+billetes.size());
		for(int j=0; j<billetes.size();j++){
			matches = new ArrayList<MatOfDMatch>();
			matcher.knnMatch(billetes.get(j).getDDorso(), Escena_actual.getDFrente(), matches, 2);
		   
			good_matches=new ArrayList<DMatch>();
			for(int i=0;i<matches.size();i++){
						
				if(matches.get(i).toList().get(0).distance < Pdistancia*matches.get(i).toList().get(1).distance)
					good_matches.add(matches.get(i).toList().get(0));
			}
			Log.d("Frente", "goog_matches:"+good_matches.size());			
			goodMatches = new MatOfDMatch();
			goodMatches.fromList(good_matches);
			
			if(good_matches.size()>Ngoodmatches){
				
				
				keypoints_objectList = new ArrayList<KeyPoint>();
				keypoints_sceneList = new ArrayList<KeyPoint>();
				keypoints_objectList = billetes.get(j).getKDorso().toList();
				keypoints_sceneList = Escena_actual.getKFrente().toList();
		
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
				obj_corners.put(1, 0, new double[] {billetes.get(j).getDorso().cols(),0});
				obj_corners.put(2, 0, new double[] {billetes.get(j).getDorso().cols(),billetes.get(j).getDorso().rows()});
				obj_corners.put(3, 0, new double[] {0,billetes.get(j).getDorso().rows()});
				
				scene_corners = new Mat(4,1,CvType.CV_32FC2);
				Core.perspectiveTransform(obj_corners,scene_corners, hg);
	
				
				scene_corners.put(0,0,new double[] {scene_corners.get(0,0)[0]+billetes.get(j).getDorso().cols(),scene_corners.get(0,0)[1]});
		    	scene_corners.put(1,0,new double[] {scene_corners.get(1,0)[0]+billetes.get(j).getDorso().cols(),scene_corners.get(1,0)[1]});
		    	scene_corners.put(2,0,new double[] {scene_corners.get(2,0)[0]+billetes.get(j).getDorso().cols(),scene_corners.get(2,0)[1]});
		    	scene_corners.put(3,0,new double[] {scene_corners.get(3,0)[0]+billetes.get(j).getDorso().cols(),scene_corners.get(3,0)[1]});
		    					
				
				Point punto_A= new Point(scene_corners.get(0,0));
				Point punto_B= new Point(scene_corners.get(1,0));
				Point punto_C= new Point(scene_corners.get(2,0));
				Point punto_D= new Point(scene_corners.get(3,0));
				
				Mat outImg=new Mat();
				MatOfByte drawnMatches = new MatOfByte();
				Features2d.drawMatches(billetes.get(j).getDorso(), billetes.get(j).getKDorso(), Escena_actual.getFrente(), Escena_actual.getKFrente(), goodMatches, outImg,Scalar.all(-1),Scalar.all(-1),drawnMatches,Features2d.NOT_DRAW_SINGLE_POINTS); 
				
				Core.line(outImg, punto_A, punto_B, new Scalar(0, 255, 0),4);
				Core.line(outImg, punto_B, punto_C, new Scalar(0, 255, 0),4);
				Core.line(outImg, punto_C, punto_D, new Scalar(0, 255, 0),4);
				Core.line(outImg, punto_D, punto_A, new Scalar(0, 255, 0),4);
		    					
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		        String currentDateandTime = sdf.format(new Date());
		        String fileName = good_matches.size()+"_"+currentDateandTime + ".png";
				
				SaveImage(outImg,fileName);
				
					    	    
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
					billetes.get(j).play();
					res=res+j+" ";
				}
					
						
			}else{
				Mat outImg=new Mat();
				MatOfByte drawnMatches = new MatOfByte();
				Features2d.drawMatches(billetes.get(j).getDorso(), billetes.get(j).getKDorso(), Escena_actual.getFrente(), Escena_actual.getKFrente(), goodMatches, outImg,Scalar.all(-1),Scalar.all(-1),drawnMatches,Features2d.NOT_DRAW_SINGLE_POINTS);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		        String currentDateandTime = sdf.format(new Date());
		        String fileName = good_matches.size()+"_"+currentDateandTime + ".png";
				SaveImage(outImg,fileName);
			}	
		}
		return res;
	}
	@SuppressLint("SimpleDateFormat") public String reconocerFrentes() {
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
		
		Log.d("Frente", "billetes:"+billetes.size());
		for(int j=0; j<billetes.size();j++){
			matches = new ArrayList<MatOfDMatch>();
			matcher.knnMatch(billetes.get(j).getDFrente(), Escena_actual.getDFrente(), matches, 2);
		   
			good_matches=new ArrayList<DMatch>();
			for(int i=0;i<matches.size();i++){
						
				if(matches.get(i).toList().get(0).distance < Pdistancia*matches.get(i).toList().get(1).distance)
					good_matches.add(matches.get(i).toList().get(0));
			}
			Log.d("Frente", "goog_matches:"+good_matches.size());			
			goodMatches = new MatOfDMatch();
			goodMatches.fromList(good_matches);
			
			if(good_matches.size()>Ngoodmatches){
				
				
				keypoints_objectList = new ArrayList<KeyPoint>();
				keypoints_sceneList = new ArrayList<KeyPoint>();
				keypoints_objectList = billetes.get(j).getKFrente().toList();
				keypoints_sceneList = Escena_actual.getKFrente().toList();
		
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
				obj_corners.put(1, 0, new double[] {billetes.get(j).getFrente().cols(),0});
				obj_corners.put(2, 0, new double[] {billetes.get(j).getFrente().cols(),billetes.get(j).getFrente().rows()});
				obj_corners.put(3, 0, new double[] {0,billetes.get(j).getFrente().rows()});
				
				scene_corners = new Mat(4,1,CvType.CV_32FC2);
				Core.perspectiveTransform(obj_corners,scene_corners, hg);
	
				
				scene_corners.put(0,0,new double[] {scene_corners.get(0,0)[0]+billetes.get(j).getFrente().cols(),scene_corners.get(0,0)[1]});
		    	scene_corners.put(1,0,new double[] {scene_corners.get(1,0)[0]+billetes.get(j).getFrente().cols(),scene_corners.get(1,0)[1]});
		    	scene_corners.put(2,0,new double[] {scene_corners.get(2,0)[0]+billetes.get(j).getFrente().cols(),scene_corners.get(2,0)[1]});
		    	scene_corners.put(3,0,new double[] {scene_corners.get(3,0)[0]+billetes.get(j).getFrente().cols(),scene_corners.get(3,0)[1]});
		    					
				
				Point punto_A= new Point(scene_corners.get(0,0));
				Point punto_B= new Point(scene_corners.get(1,0));
				Point punto_C= new Point(scene_corners.get(2,0));
				Point punto_D= new Point(scene_corners.get(3,0));
				
				Mat outImg=new Mat();
				MatOfByte drawnMatches = new MatOfByte();
				Features2d.drawMatches(billetes.get(j).getFrente(), billetes.get(j).getKFrente(), Escena_actual.getFrente(), Escena_actual.getKFrente(), goodMatches, outImg,Scalar.all(-1),Scalar.all(-1),drawnMatches,Features2d.NOT_DRAW_SINGLE_POINTS); 
				
				Core.line(outImg, punto_A, punto_B, new Scalar(0, 255, 0),4);
				Core.line(outImg, punto_B, punto_C, new Scalar(0, 255, 0),4);
				Core.line(outImg, punto_C, punto_D, new Scalar(0, 255, 0),4);
				Core.line(outImg, punto_D, punto_A, new Scalar(0, 255, 0),4);
		    					
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		        String currentDateandTime = sdf.format(new Date());
		        String fileName = good_matches.size()+"_"+currentDateandTime + ".png";
				
				SaveImage(outImg,fileName);
				
					    	    
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
					billetes.get(j).play();
					res=res+j+" ";
				}
					
						
			}else{
				Mat outImg=new Mat();
				MatOfByte drawnMatches = new MatOfByte();
				Features2d.drawMatches(billetes.get(j).getFrente(), billetes.get(j).getKFrente(), Escena_actual.getFrente(), Escena_actual.getKFrente(), goodMatches, outImg,Scalar.all(-1),Scalar.all(-1),drawnMatches,Features2d.NOT_DRAW_SINGLE_POINTS);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		        String currentDateandTime = sdf.format(new Date());
		        String fileName = good_matches.size()+"_"+currentDateandTime + ".png";
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
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        List<String> effects = mOpenCvCameraView.getEffectList();

        if (effects == null) {
            Log.e(TAG, "Color effects are not supported by device!");
            return true;
        }

        mColorEffectsMenu = menu.addSubMenu("Color Effect");
        mEffectMenuItems = new MenuItem[effects.size()];

        int idx = 0;
        ListIterator<String> effectItr = effects.listIterator();
        while(effectItr.hasNext()) {
           String element = effectItr.next();
           mEffectMenuItems[idx] = mColorEffectsMenu.add(1, idx, Menu.NONE, element);
           idx++;
        }

        mResolutionMenu = menu.addSubMenu("Resolution");
        mResolutionList = mOpenCvCameraView.getResolutionList();
        mResolutionMenuItems = new MenuItem[mResolutionList.size()];

        ListIterator<Size> resolutionItr = mResolutionList.listIterator();
        idx = 0;
        while(resolutionItr.hasNext()) {
            Size element = resolutionItr.next();
            mResolutionMenuItems[idx] = mResolutionMenu.add(2, idx, Menu.NONE,
                    Integer.valueOf(element.width).toString() + "x" + Integer.valueOf(element.height).toString());
            idx++;
         }

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item.getGroupId() == 1)
        {
            mOpenCvCameraView.setEffect((String) item.getTitle());
            Toast.makeText(this, mOpenCvCameraView.getEffect(), Toast.LENGTH_SHORT).show();
        }
        else if (item.getGroupId() == 2)
        {
            int id = item.getItemId();
            Size resolution = mResolutionList.get(id);
            mOpenCvCameraView.setResolution(resolution);
            resolution = mOpenCvCameraView.getResolution();
            String caption = Integer.valueOf(resolution.width).toString() + "x" + Integer.valueOf(resolution.height).toString();
            Toast.makeText(this, caption, Toast.LENGTH_SHORT).show();
        }

        return true;
    }
    @SuppressLint({ "SimpleDateFormat", "ClickableViewAccessibility" })
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG,"onTouch event");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        //String currentDateandTime = sdf.format(new Date());
        //String fileName = Environment.getExternalStorageDirectory().getPath() +
        // "/sample_picture_" + currentDateandTime + ".jpg";
        //mOpenCvCameraView.takePicture(fileName);
        Toast.makeText(this, "Calculando...", Toast.LENGTH_SHORT).show();
        
        touched = true;
        return false;
    }
    public int getbilletes(){
    	return billetes.size();
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
