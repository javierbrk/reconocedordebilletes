package gob.inti.reconocedordebilletes;

/**
 * Clase que contiene las estadísitcas de las ejecuciones de los comparadores
 * @author jjorge
 *
 */
public class ContenedorEstadistico {

	long correctos, tiempototal, contador,tiempomaximo,tiempominimo, fp, fn;

	
	public ContenedorEstadistico()
	{
		tiempomaximo = 0;
		correctos = 0;
		tiempototal = 0;
		contador = 0;
		tiempominimo = 999999999;
		fp = 0;
		fn = 0;
	
	}
	
	/**
	 * Aumenta en uno la cantidad de incorrectos y registra el tiempo que tardo.
	 * @param timpodeunaimagen
	 */
	public void incCorrectos(long timpodeunaimagen)
	{
		tiempototal(timpodeunaimagen);
		correctos ++;
		contador ++;
	}
	/**
	 * Incrementar el contador de errores en particular fp
	 * @param timpodeunaimagen
	 */
	public void incFalsoPositivo(long timpodeunaimagen)
	{
		fp++;
		incIncorrectos(timpodeunaimagen);
	}
	
	public void incFalsoNegativo(long timpodeunaimagen)
	{
		fn++;
		incIncorrectos(timpodeunaimagen);
	}
	
	private void incIncorrectos(long timpodeunaimagen)
	{
		tiempototal(timpodeunaimagen);
		
		contador ++;
	}
	
	/**
	 * registra el tiempo total de las imágenes el mínimo y el máximo.
	 * @param timpodeunaimagen tiempo que tardo en procesar una imagen
	 */
	private void tiempototal (long timpodeunaimagen)
	{
		tiempototal = tiempototal + timpodeunaimagen;
		if (timpodeunaimagen<tiempominimo)
		{
			tiempominimo = timpodeunaimagen;
		}
		if (timpodeunaimagen>tiempomaximo)
		{
			tiempomaximo = timpodeunaimagen;
		}
		
	}
	
	public long tiempoTotal()
	{
		return tiempototal;
	}
	
	public double tiempomedio()
	{
		return ((double)tiempototal)/((double)contador);
	}
	public long tiempoMinimo()
	{
		return tiempominimo;
	}
	public long timpoMaximo()
	{
		return tiempomaximo;
	}
	public long correctos()
	{
		return correctos;
	}
	public long incorrectos()
	{
		return fp+fn;
	}
	
	public String toString()
	{
		return "correctos: " + correctos + " incorrectos: " + incorrectos() + "Falsos positivos: " +fp+ "Falsos Negativos" + fn+ " tiempomedio: " + tiempomedio();
		
	}
}
