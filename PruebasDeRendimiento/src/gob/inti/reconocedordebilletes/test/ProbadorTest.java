/**
 * 
 */
package gob.inti.reconocedordebilletes.test;

import java.util.ArrayList;

import gob.inti.reconocedordebilletes.Billete;
import gob.inti.reconocedordebilletes.EDenominacionBilletes;
import gob.inti.reconocedordebilletes.HomographyMatcher;
import gob.inti.reconocedordebilletes.IReconocedores;
import gob.inti.reconocedordebilletes.NotEnougthKeypoints;
import gob.inti.reconocedordebilletes.R;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;
import android.util.Log;


/**
 * @author jjorge
 *
 */
public class ProbadorTest extends InstrumentationTestCase {

	static {
	    if (!OpenCVLoader.initDebug()) {
	    	System.out.println("ERRORRRRRRRRRRRRR no carga opencv...");
	    }
	}
	
	private static final String TAG = "TEST";
	Probador p;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		p=new Probador(EDenominacionBilletes.dosp);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		
	}

	/**
	 * Test method for {@link org.opencv.samples.tutorial1.Probador#agregarComparador(org.opencv.IComparadorDeReconocedores.tutorial1.Comparador)}.
	 */
	public void testAgregarComparador() {
		assertEquals(0, p.numerodeComparadores());
		IReconocedores c = new HomographyMatcher();
		p.agregarComparador(c);
		assertEquals(1, p.numerodeComparadores());
		IReconocedores c2 = new HomographyMatcher();
		p.agregarComparador(c2);
		assertEquals(2, p.numerodeComparadores());
	}

	/**
	 * Test method for {@link org.opencv.samples.tutorial1.Probador#agregarCarpetaDeImagenesCorrectas(java.lang.String)}.
	 */
	public void testAgregarCarpetaDeImagenesCorrectas() {
		assertEquals(0, p.numerodeCarpetasDeImagenesIncorrectas());
		p.agregarCarpetaDeImagenesIncorrectas("/home");
		assertEquals(1,p.numerodeCarpetasDeImagenesIncorrectas());
		p.agregarCarpetaDeImagenesIncorrectas("/home/2");
		assertEquals(2, p.numerodeCarpetasDeImagenesIncorrectas());
	}

	/**
	 * Test method for {@link org.opencv.samples.tutorial1.Probador#agregarCarpetaDeImagenesIncorrectas(java.lang.String)}.
	 */
	public void testAgregarCarpetaDeImagenesIncorrectas() {
		assertEquals(0, p.numerodeCarpetasDeImagenesCorrectas());
		p.agregarCarpetaDeImagenesCorrectas("/home");
		assertEquals(1,p.numerodeCarpetasDeImagenesCorrectas());
		p.agregarCarpetaDeImagenesCorrectas("/home/2");
		assertEquals(2, p.numerodeCarpetasDeImagenesCorrectas());
	}
	 
	/**
	 * Test method for {@link org.opencv.samples.tutorial1.Probador#probarlosmetodos()}.
	 */
	public void testProbarlosmetodos() {
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		HomographyMatcher c = new HomographyMatcher();
		try {
			c.Inicializar(0);
			cargartemplates(c);
		} catch (NotEnougthKeypoints e) {
			Log.e(TAG+"excepcion", "excepcion al Inicializar");
			Log.e(TAG,e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			Log.e(TAG+"excepcion", "excepcion al Inicializar");
			Log.e(TAG,e.toString());
			e.printStackTrace();
		}
		p.agregarComparador(c);
		p.agregarCarpetaDeImagenesCorrectas("/buenas2p");
		p.agregarCarpetaDeImagenesIncorrectas("/malas");
		//p.agregarCarpetaDeImagenesIncorrectas("/buenas5p");
		try {
			p.probarlosmetodos();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG+"excepcion", "excepcion al tratar de probar los metodos");
			Log.e(TAG,e.toString());
		}
		Log.d(TAG+"FINDELAPRUEBAAAAAAAAAAAAAAAAAAAAAAAAA", "fin de la aplicacion");
		assertEquals(0,0);
	}
	
	
    public void cargartemplates(HomographyMatcher hm2) throws Exception {

    	ArrayList<Billete> lb= new ArrayList<Billete>();
    	for (EDenominacionBilletes denominacion : EDenominacionBilletes.values()) 
    	{
			
			Billete b = new Billete(); 
			Mat mask = new Mat(),mask_gray = new Mat();
			
			Bitmap bMap0=BitmapFactory.decodeResource(this.getInstrumentation().getTargetContext().getResources(),templateimg(denominacion.value()));
			Utils.bitmapToMat(bMap0, b.bTemplate.ImagenOriginal);
	    	bMap0.recycle();

			Bitmap bMap2=BitmapFactory.decodeResource(this.getInstrumentation().getTargetContext().getResources(),maskimg(denominacion.value()));
		    Utils.bitmapToMat(bMap2, mask);
	    	bMap2.recycle();

		    Imgproc.cvtColor(mask, mask_gray, Imgproc.COLOR_BGR2GRAY,CvType.CV_8UC1);
			b.bTemplate.Mascara = mask_gray;
		    
			b.denominacion = denominacion;
			
			lb.add(b);
		}
		hm2.ProcesarTemplate(lb);
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

}
