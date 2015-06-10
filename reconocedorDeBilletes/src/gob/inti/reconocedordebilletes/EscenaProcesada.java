package gob.inti.reconocedordebilletes;

import org.opencv.core.Mat;

public class EscenaProcesada {
	public Mat imagenDelMacheo;
	public boolean correspondencia;
	public Billete Contraparete;
	
	public void finalize()
	{
		if(imagenDelMacheo != null)
		{
			imagenDelMacheo.release();
		}
	}
	
	
}
