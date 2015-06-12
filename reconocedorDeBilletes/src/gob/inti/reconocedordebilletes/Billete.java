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
	public EDenominacionBilletes denominacion;
	
	private MediaPlayer player;
	
	public Billete(Context context,int audio)
	{
		bTemplate = new Template();
		player = MediaPlayer.create(context,audio);
		
	}
	
	public Billete()
	{
		bTemplate = new Template();
	}
	
	public void anunciate()
	{
		if (player!= null)
		{
			player.start();
		}
	}
}
