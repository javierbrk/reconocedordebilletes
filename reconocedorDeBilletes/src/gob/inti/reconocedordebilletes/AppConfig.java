package gob.inti.reconocedordebilletes;

public class AppConfig {
    /*
     * Debug true consume aproximadamente 500ms mas por cada template ya que ademas de hacer la comparación realiza un dibujo de los good matches y lo guarda en una foto.
     * esto implica q para reconocer cada billete se tome aproximadamente 500*13 = 6500ms extra
     */
	public static final boolean DEBUG = true;
    
}