package gob.inti.reconocedordebilletes;

import android.content.Context;
import android.media.MediaPlayer;

/***
 * Representa un billete que contendrá una denominación un template y un audio
 * @author jjorge
 *
 */
public class Billete {
	public Template bTemplate;
	public int audio; // the raw resource id 
	EDenominacionBilletes denominacion;
	
	private MediaPlayer player;
	
	public Billete()
	{
		bTemplate = new Template();
	}
	
	public void anunciate(Context context)
	{
		player = MediaPlayer.create(context,audio);
	}
}
