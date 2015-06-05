package gob.inti.reconocedordebilletes;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;

public class HomographyMatcher {

	public FeatureDetector detector;
	public DescriptorExtractor extractor;
	public DescriptorMatcher matcher;
	public List<Billete> billetes;
	
	public void ProcesarTemplate(List<Billete> lb) {
		
	}
	public List<EscenaProcesada> ProcesarImagen(Mat imagen) {
		List<EscenaProcesada> res=null;
		return res;
	}
	
	public void Inicializar(int config) {
		
	}
	
	private void Preprocess(Escena esc){
		
	}
	
	private void Detectar(Escena esc) {
		
	}
	private void Detectar(Billete bil) {
		
	}
	private void Extraer(Escena esc) {
		
	}
	private void Macheo(Escena esc,Template temp) {
		
	}
	private MatOfDMatch Seleccion (List<MatOfDMatch> matches) {
		MatOfDMatch res=null;
		return res;
	}
}
