package inti.recon;

import inti.recon.backend.BillSearch;
import inti.recon.backend.Billete;
import inti.recon.backend.SimpleBillSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera.Size;
import android.os.Bundle;
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
    public static final int Ancho = 800;
    public static final int Alto = 600;
    
    
    
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
    		R.raw.cienpesos
    		};
    
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
    	mOpenCvCameraView.setFlashOn();
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

    	Mat rgba=new Mat();
    	org.opencv.core.Size dzise=new org.opencv.core.Size(Ancho,Alto);
    	Imgproc.resize(inputFrame.gray(),rgba,dzise);
         if ( touched ) {
        	 Escena_actual = new Billete(ReconActivity.this, rgba, rgba, ID_Denominacion[0]);
        	 
        	 BillSearch bs = new SimpleBillSearch();
        	 bs.search(Escena_actual, billetes);
        	         	 

        	 touched = false;
         }
         return inputFrame.rgba();
         
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
    public List<Billete> getBilletes(){
    	return billetes;
    }
}
