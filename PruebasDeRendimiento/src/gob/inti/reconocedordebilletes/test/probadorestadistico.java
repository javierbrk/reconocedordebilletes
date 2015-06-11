/**
 * 
 */
package gob.inti.reconocedordebilletes.test;

import junit.framework.TestCase;
import gob.inti.reconocedordebilletes.ContenedorEstadistico;

/**
 * @author jjorge
 *
 */
public class probadorestadistico extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	
	ContenedorEstadistico ce;
	
	protected void setUp() throws Exception {
		super.setUp();
		ce = new ContenedorEstadistico();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.example.aplicacion.ContenedorEstadistico#incCorrectos(int)}.
	 */
	public void testIncCorrectos() {
		ce.incCorrectos(100);
		ce.incCorrectos(200);
		assertEquals(ce.correctos(), 2);
		assertEquals(ce.tiempomedio(),150.0);
		assertEquals(ce.tiempoMinimo(),100);
		assertEquals(ce.timpoMaximo(),200);
		assertEquals(ce.incorrectos(),0);
		assertEquals(ce.tiempoTotal(), 300);
	}

	/**
	 * Test method for {@link com.example.aplicacion.ContenedorEstadistico#incIncorrectos(int)}.
	 */
	public void testIncIncorrectos() {
		ce.incFalsoNegativo(100);
		ce.incFalsoNegativo(200);
		assertEquals(ce.correctos(), 0);
		assertEquals(ce.tiempomedio(),150.0);
		assertEquals(ce.tiempoMinimo(),100);
		assertEquals(ce.timpoMaximo(),200);
		assertEquals(ce.incorrectos(),2);
		assertEquals(ce.tiempoTotal(), 300);
	}
}
