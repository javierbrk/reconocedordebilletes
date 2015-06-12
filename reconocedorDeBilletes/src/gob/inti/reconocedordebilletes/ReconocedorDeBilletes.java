package gob.inti.reconocedordebilletes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DMatch;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

    public void cargartemplates(HomographyMatcher hm2) throws NotEnougthKeypoints {
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
		hm2.ProcesarTemplate(lb);
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
				cargartemplates(hm);
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
    

    public static int templateimg(int idpesos){
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
    public static int maskimg(int idpesos){
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
    
	public static int sound(int idpesos){
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