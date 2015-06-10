package gob.inti.reconocedordebilletes;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

public class Escena {

	public Mat ImagenOriginal;
	public Mat ImagenPrePocesada;
	public Mat Descriptores;
	public MatOfKeyPoint keypoints;
	
	public Escena () {
		ImagenOriginal = new Mat();
		ImagenPrePocesada = new Mat();
		Descriptores = new Mat();
		keypoints = new MatOfKeyPoint();
	}
	
	/**
	 * Libera los recursos reservados
	 */
	public void Destruir() {
		ImagenOriginal.release();
		ImagenPrePocesada.release();
		Descriptores.release();
		keypoints.release();
	}
	
	public void finalize()
	{
		if(ImagenOriginal!=null)
		{
			Destruir();
		}
	}
}
