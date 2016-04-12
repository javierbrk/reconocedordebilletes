package inti.recon.test;

import inti.recon.Billete;
import inti.recon.ReconActivity;

import java.io.IOException;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import inti.recon.test.R;

import android.test.ActivityInstrumentationTestCase2;

public class ReconocerValidacion extends ActivityInstrumentationTestCase2<ReconActivity> {

	
	public ReconocerValidacion() {
		super(ReconActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		
	}
	
	public void testCargaBilletes() {
		
	    // MyClass is tested
		ReconActivity mainActivity = getActivity();
		//Verifico si se cargaron los ocho tipos de billetes como templates
	    assertEquals(8,mainActivity.getbilletes());
	  }
/*Test cases para dos pesos de frente*/
	
public void testReconocerdosp_1() {
		
	    // MyClass is tested
		ReconActivity mainActivity = getActivity();
		
	    Mat srcRGBA = new Mat(); //RGBA format
        try {
			Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        Mat rgba=new Mat();
        org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
    	Imgproc.resize(srcRGBA,rgba,dzise);
	    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
	    
	    // assert statements
	    assertEquals("0 ",mainActivity.reconocerFrentes());
	  }
public void testReconocerdosp_10() {
		
	    // MyClass is tested
		ReconActivity mainActivity = getActivity();
		
	    Mat srcRGBA = new Mat(); //RGBA format
        try {
			Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        Mat rgba=new Mat();
        org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
    	Imgproc.resize(srcRGBA,rgba,dzise);
	    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
	    
	    // assert statements
	    assertEquals("0 ",mainActivity.reconocerFrentes());
	  }
public void testReconocerdosp_20() {
		
	    // MyClass is tested
		ReconActivity mainActivity = getActivity();
		
	    Mat srcRGBA = new Mat(); //RGBA format
        try {
			Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        Mat rgba=new Mat();
        org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
    	Imgproc.resize(srcRGBA,rgba,dzise);
	    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
	    
	    // assert statements
	    assertEquals("0 ",mainActivity.reconocerFrentes());
	  }
public void testReconocerdosp_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerFrentes());
  }
public void testReconocerdosp_40() {
		
	    // MyClass is tested
		ReconActivity mainActivity = getActivity();
		
	    Mat srcRGBA = new Mat(); //RGBA format
        try {
			Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        Mat rgba=new Mat();
        org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
    	Imgproc.resize(srcRGBA,rgba,dzise);
	    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
	    
	    // assert statements
	    assertEquals("0 ",mainActivity.reconocerFrentes());
	  }
public void testReconocerdosp_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerFrentes());
  }
public void testReconocerdosp_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerFrentes());
  }
public void testReconocerdosp_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerFrentes());
  }
public void testReconocerdosp_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerFrentes());
  }
public void testReconocerdosp_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerFrentes());
  }
public void testReconocerdosp_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dosp_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerFrentes());
  }
/*Test cases para dos pesos de dorso*/

public void testReconocerdospd_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }
public void testReconocerdospd_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.dospd_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("0 ",mainActivity.reconocerDorsos());
  }



/*Test cases para cinco pesos de frente*/

public void testReconocercincop_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }
public void testReconocercincop_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincop_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerFrentes());
  }



/*Test cases para cinco pesos de dorso */

public void testReconocercincopd_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }
public void testReconocercincopd_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cincopd_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("1 ",mainActivity.reconocerDorsos());
  }


/*Test cases para diez pesos de frente*/

public void testReconocerdiezp_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }
public void testReconocerdiezp_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

public void testReconocerdiezp_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezp_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerFrentes());
  }

/*Test cases para diez pesos de dorso*/

public void testReconocerdiezpd_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }

public void testReconocerdiezpd_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.diezpd_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("2 ",mainActivity.reconocerDorsos());
  }


/*Test case para veinte pesos de frente*/	


public void testReconocerveintep_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

public void testReconocerveintep_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintep_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerFrentes());
  }

/*Test case para veinte pesos de dorso*/

public void testReconocerveintepd_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintepd_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.veintepd_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

public void testReconocerveintepd_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.veintepd_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("3 ",mainActivity.reconocerDorsos());
  }

/*Test case para cincuenta pesos de frente*/

public void testReconocercincuentap_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }

public void testReconocercincuentap_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentap_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerFrentes());
  }
/*Test case para cincuenta pesos de dorso*/

public void testReconocercincuentapd_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

public void testReconocercincuentapd_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cincuentapd_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("4 ",mainActivity.reconocerDorsos());
  }

/*Test case para cien pesos de frente*/

public void testReconocercienp_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cienp_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cienp_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

public void testReconocercienp_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienp_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerFrentes());
  }

/*Test case para cien pesos de dorso*/

public void testReconocercienpd_1() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cienpd_1), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_10() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(Utils.loadResource(getInstrumentation().getContext(), R.raw.cienpd_10), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_20() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_20), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_30() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_30), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_40() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_40), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_50() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_50), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_60() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_60), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_70() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_70), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_80() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_80), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_90() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_90), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

public void testReconocercienpd_100() {
	
    // MyClass is tested
	ReconActivity mainActivity = getActivity();
	
    Mat srcRGBA = new Mat(); //RGBA format
    try {
		Imgproc.cvtColor(
				Utils.loadResource(
					getInstrumentation().getContext(), R.raw.cienpd_100), srcRGBA, Imgproc.COLOR_BGR2GRAY);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    Mat rgba=new Mat();
    org.opencv.core.Size dzise=new org.opencv.core.Size(ReconActivity.Ancho,ReconActivity.Alto);
	Imgproc.resize(srcRGBA,rgba,dzise);
    mainActivity.Escena_actual=new Billete(getInstrumentation().getContext(), rgba, rgba, R.raw.cienpesos);
    
    // assert statements
    assertEquals("6 ",mainActivity.reconocerDorsos());
  }

}
