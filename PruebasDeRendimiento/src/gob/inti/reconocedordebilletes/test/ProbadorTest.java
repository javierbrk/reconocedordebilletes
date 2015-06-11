/**
 * 
 */
package gob.inti.reconocedordebilletes.test;

import gob.inti.reconocedordebilletes.EDenominacionBilletes;
import gob.inti.reconocedordebilletes.HomographyMatcher;
import gob.inti.reconocedordebilletes.IReconocedores;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import android.test.InstrumentationTestCase;
import android.util.Log;
import junit.framework.TestCase;
import android.app.Activity;
import android.os.Bundle;


/**
 * @author jjorge
 *
 */
public class ProbadorTest extends InstrumentationTestCase {


	static {
	    if (!OpenCVLoader.initDebug()) {
	    	System.out.println("la puta madre ...");
	    }
	}
	
	private static final String TAG = "TEST";
	Probador p;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		 if (!OpenCVLoader.initDebug()) {
		    	System.out.println("la puta madre ...");
		    	throw new Exception();
		    }
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
	      
		IReconocedores c = new HomographyMatcher();
		p.agregarComparador(c);
		p.agregarCarpetaDeImagenesCorrectas("/buenas");
		p.agregarCarpetaDeImagenesIncorrectas("/malas");
		p.probarlosmetodos();
		System.out.println(java.lang.Runtime.getRuntime().freeMemory()); 

	}
	
	

}
