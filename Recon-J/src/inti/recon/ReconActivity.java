package inti.recon;

import inti.recon.backend.BillSearch;
import inti.recon.backend.Billete;
import inti.recon.backend.SimpleBillSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

@SuppressLint("ClickableViewAccessibility")
public class ReconActivity extends Activity implements CvCameraViewListener2, OnTouchListener {
    private static final String TAG = "OCVSample::Activity";
    public static final int Ancho = 640;
    public static final int Alto = 480;
    public boolean bienvenida=false;
    public long timestart=0;

    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }
    
    private ReconView mOpenCvCameraView;
    private List<Size> mResolutionList;
    private MenuItem[] mEffectMenuItems;
    private MenuItem[] mVelocidadMenuItems;
    private SubMenu mColorEffectsMenu;
    private SubMenu mVelocidadMenu;
    
    
    
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
    		R.raw.cienevpd,
    		R.raw.quinientosp,
    		R.raw.quinientospd,
    		R.raw.cinconp,
    		R.raw.cinconpd,
    		R.raw.dieznp,
    		R.raw.dieznpd
    		};
        
    private boolean touched=false;
    private Mat srcRGBA;
    private Mat srcRGBA2;
    
    TextToSpeech t1;
    	
	private void llenar_lista_billetes() {
		for (int i=0;i<ID_Templates.length;i=i+2){
			try {
            	srcRGBA = new Mat(); //RGBA format
                Imgproc.cvtColor(Utils.loadResource(ReconActivity.this, ID_Templates[i]), srcRGBA, Imgproc.COLOR_BGR2GRAY);
                srcRGBA2 = new Mat(); //RGBA format
                Imgproc.cvtColor(Utils.loadResource(ReconActivity.this, ID_Templates[i+1]), srcRGBA2, Imgproc.COLOR_BGR2GRAY);
                billetes.add(new Billete(ReconActivity.this,srcRGBA,srcRGBA2));
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		
	}


	
	
	
	
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

        billetes=new ArrayList<Billete>();
        llenar_lista_billetes();
        
        mOpenCvCameraView = (ReconView) findViewById(R.id.tutorial3_activity_java_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setOnTouchListener(ReconActivity.this);
        mOpenCvCameraView.enableView();
        
        
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
               if(status != TextToSpeech.ERROR) {
            	   Locale locSpanish = new Locale("spa", "ESP");
            	   t1.setLanguage(locSpanish);
               }
            }
         });
        
        
        
    }

    @Override
    public void onPause()
    {
    	if(t1 !=null){
            t1.stop();
            t1.shutdown();
         }
    	super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        billetes=new ArrayList<Billete>();
        llenar_lista_billetes();
        mOpenCvCameraView.enableView();
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
               if(status != TextToSpeech.ERROR) {
            	   Locale locSpanish = new Locale("spa", "ESP");
            	   t1.setLanguage(locSpanish);
               }
            }
         });
        
        //Prendo flash
        
        
        
        //mOpenCvCameraView.setOnTouchListener(ReconActivity.this);
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
        
    }

    public void onCameraViewStarted(int width, int height) {
    	mOpenCvCameraView.turnLightOn();
    	
    }

    public void onCameraViewStopped() {
    	mOpenCvCameraView.turnLightOff();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	
    	Mat rgba=new Mat();
    	
    	if(bienvenida){
    		t1.speak("Bienvenido a Recon. Toque la pantalla para comenzar el reconocimiento.", TextToSpeech.QUEUE_FLUSH, null);
    		bienvenida=false;
    	}
    	
    	org.opencv.core.Size dzise=new org.opencv.core.Size(Ancho,Alto);
    	Imgproc.resize(inputFrame.gray(),rgba,dzise);
        if ( touched ) {
        	 
        	 
        	 Escena_actual = new Billete(ReconActivity.this, rgba, rgba );
        	 
        	 BillSearch bs = new SimpleBillSearch();
        	 long startTime = System.nanoTime();
        	 String toSpeak=texto(bs.search(Escena_actual, billetes));
        	 timestart=(System.nanoTime() - startTime) / 1000000;
        	 //Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show();
        	
        	 t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);        	 
        	 touched = false;
         }
         return inputFrame.rgba();
         
    }
    
    
    



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        List<String> velocidad=new ArrayList<String>();
        velocidad.add("1.0x");
        velocidad.add("2.0x");
        mVelocidadMenu = menu.addSubMenu("Velocidad Vos");
        mVelocidadMenuItems = new MenuItem[velocidad.size()];

        int idx = 0;
        ListIterator<String> veloItr = velocidad.listIterator();
        while(veloItr.hasNext()) {
           String element = veloItr.next();
           mVelocidadMenuItems[idx] = mVelocidadMenu.add(1, idx, Menu.NONE, element);
           idx++;
        }
        
        
		
        List<String> effects = mOpenCvCameraView.getEffectList();

        if (effects == null) {
            Log.e(TAG, "Color effects are not supported by device!");
            return true;
        }

        mColorEffectsMenu = menu.addSubMenu("Color Effect");
        mEffectMenuItems = new MenuItem[effects.size()];

        idx = 0;
        ListIterator<String> effectItr = effects.listIterator();
        while(effectItr.hasNext()) {
           String element = effectItr.next();
           mEffectMenuItems[idx] = mColorEffectsMenu.add(2, idx, Menu.NONE, element);
           idx++;
        }

        mResolutionMenu = menu.addSubMenu("Resolution");
        mResolutionList = mOpenCvCameraView.getResolutionList();
        mResolutionMenuItems = new MenuItem[mResolutionList.size()];

        ListIterator<Size> resolutionItr = mResolutionList.listIterator();
        idx = 0;
        while(resolutionItr.hasNext()) {
            Size element = resolutionItr.next();
            mResolutionMenuItems[idx] = mResolutionMenu.add(3, idx, Menu.NONE,
                    Integer.valueOf(element.width).toString() + "x" + Integer.valueOf(element.height).toString());
            idx++;
         }
        
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item.getGroupId() == 1)
        {
        	if(item.getItemId()==0)
        	{
        		t1.setSpeechRate(1);
        	}else if(item.getItemId()==1){
        		t1.setSpeechRate(2);
        	}	
        		
        	
            //Toast.makeText(this, mOpenCvCameraView.getEffect(), Toast.LENGTH_SHORT).show();
        }
        else if (item.getGroupId() == 2)
        {
            mOpenCvCameraView.setEffect((String) item.getTitle());
            Toast.makeText(this, mOpenCvCameraView.getEffect(), Toast.LENGTH_SHORT).show();
        }
        else if (item.getGroupId() == 3)
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
        t1.speak("Calculando...", TextToSpeech.QUEUE_FLUSH, null);
        Toast.makeText(this, timestart + " ms", Toast.LENGTH_SHORT).show();
                   
       
        touched = true;
        return false;
    }
    public List<Billete> getBilletes(){
    	return billetes;
    }
    public String texto(String in){
    	String out="";
    	if(in.equalsIgnoreCase("0 ")){
    		out ="Dos pesos.";
    	}
    	else if(in.equalsIgnoreCase("1 ")|| in.equalsIgnoreCase("9 ")){
    		out ="Cinco pesos.";
    	}
       	else if(in.equalsIgnoreCase("2 ")|| in.equalsIgnoreCase("10 ")){
    		out ="Diez pesos.";
    	}
      	else if(in.equalsIgnoreCase("3 ")){
    		out ="Veinte pesos.";
    	}
      	else if(in.equalsIgnoreCase("4 ") || in.equalsIgnoreCase("5 ")){
    		out ="Cincuenta pesos.";
    	}
      	else if(in.equalsIgnoreCase("6 ") || in.equalsIgnoreCase("7 ")){
    		out ="Cien pesos.";
    	}
      	else if(in.equalsIgnoreCase("8 ")){
    		out ="Quinientos pesos.";
    	}      	
      	else{
    		out ="Intente nuevamente.";
    	}
      	return out;
    }
    
}
