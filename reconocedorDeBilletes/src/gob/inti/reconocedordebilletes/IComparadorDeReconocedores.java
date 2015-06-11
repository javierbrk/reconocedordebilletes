package gob.inti.reconocedordebilletes;
import org.opencv.core.Mat;
/**
 * @author jjorge
 * Interfaz a implementar por todos los métodos de detección para ser probados. 
 */
public interface IComparadorDeReconocedores {
	/**
	 * Pone a cero todos los contadores de la clase para comenzar la verificación
	 */
	public void Inicializar(int config);	
	/**
	 * Realiza una comparación con los templates del método y verifica si la imagen contiene el objeto que se encuentra en los templates
	 * @param imagen imagen donde buscar
	 * @return true si se encontró el template en la foto
	 */
	public boolean Probar(Mat imagen,EDenominacionBilletes ExpectedValue, boolean imagenCorrespondeConExpectedValue);
	
	/**
	 * Estadísticas de las corridas.
	 * @return el tiempo promedio de detecciones, el tiempo máximo, mínimo, cantidad de detecciones correctas y cantidad de detecciones incorrectas.
	 */
	public ContenedorEstadistico estadisticas();
	
}

