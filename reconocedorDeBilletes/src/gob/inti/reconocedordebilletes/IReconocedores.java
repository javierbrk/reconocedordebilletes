package gob.inti.reconocedordebilletes;
import java.util.List;

import org.opencv.core.Mat;
/**
 * @author jjorge
 * Interfaz a implementar por todos los métodos de detección para ser probados. 
 */
public interface IReconocedores {
	/**
	 * Pone a cero todos los contadores de la clase para comenzar la verificación
	 */
	public void Inicializar(int config);	
	/**
	 * Realiza una comparación con los templates del método y verifica si la imagen contiene el objeto que se encuentra en los templates
	 * @param imagen imagen donde buscar
	 * @return true si se encontró el template en la foto
	 */
	public List<EscenaProcesada> ProcesarImagen(Mat imagen) throws NotEnougthKeypoints;
	
}

