package gob.inti.reconocedordebilletes.test;

import gob.inti.reconocedordebilletes.ContenedorEstadistico;
import gob.inti.reconocedordebilletes.EDenominacionBilletes;
import gob.inti.reconocedordebilletes.EscenaProcesada;
import gob.inti.reconocedordebilletes.IReconocedores;
import gob.inti.reconocedordebilletes.NotEnougthKeypoints;

import java.util.ArrayList;
import java.util.List;

import org.opencv.android.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;

import org.opencv.core.Mat;

import android.os.Environment;


public class Probador {
	private static final String TAG = "PROBADOR";
	ArrayList<IReconocedores> listadecomparadores;
	ArrayList<String> listadecarpetasconimagenescorrectas;
	ArrayList<String> listadecarpetasconimagenesincorrectas;
	private EDenominacionBilletes denominacionAprobar;
	
	public Probador (EDenominacionBilletes denominacionAprobar)
	{
		listadecomparadores = new ArrayList<IReconocedores>() ;
		listadecarpetasconimagenescorrectas = new ArrayList<String>() ;
		listadecarpetasconimagenesincorrectas  = new ArrayList<String>() ;
		this.denominacionAprobar = denominacionAprobar;
	}
	
	public void agregarComparador(IReconocedores c)
	{
		listadecomparadores.add(c);
	}
	
	public void agregarCarpetaDeImagenesCorrectas(String folderurl)
	{
		listadecarpetasconimagenescorrectas.add(folderurl);
	}
	public void agregarCarpetaDeImagenesIncorrectas(String folderurl)
	{
		listadecarpetasconimagenesincorrectas.add(folderurl);
	}
	
	public int numerodeComparadores()
	{
		return listadecomparadores.size();
	}
	
	public int numerodeCarpetasDeImagenesCorrectas()
	{
		return listadecarpetasconimagenescorrectas.size();
	}
	public int numerodeCarpetasDeImagenesIncorrectas()
	{
		return listadecarpetasconimagenesincorrectas.size();
	}
	
	public void probarlosmetodos ()
	{
		String path ;
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        
		if(listadecomparadores.size()!= 0 && listadecarpetasconimagenescorrectas.size()!= 0 && listadecarpetasconimagenesincorrectas.size() != 0)
		{
			ContenedorEstadistico c = new ContenedorEstadistico();
			for (int i = 0; i < listadecomparadores.size(); i++) {
				listadecomparadores.get(i).Inicializar(0); 
				for (int j = 0; j < listadecarpetasconimagenescorrectas.size(); j++) {
					
					path = Environment.getExternalStorageDirectory().toString()+"/Pictures" +listadecarpetasconimagenescorrectas.get(j);
					probarUnaCarpeta(path, i,true,c);
					
				}
				for (int k = 0; k < listadecarpetasconimagenesincorrectas.size(); k++) {
					path = Environment.getExternalStorageDirectory().toString()+"/Pictures" +listadecarpetasconimagenesincorrectas.get(k);
					probarUnaCarpeta(path, i,false,c);
				}
				Log.d(TAG+"  -   estadisticas - ",c.toString());
			}
			
		}
	}

	private void probarUnaCarpeta(String path, int i, boolean expectedfoldervalue, ContenedorEstadistico c) {
		Log.d("Files", "Path: " + path);
		File f = new File(path);        
		File file[] = f.listFiles();
		Log.d("Files", "Size: "+ file.length);
		for (int k=0; k < file.length; k++)
		{
			Mat imagen =  new Mat();
		    Log.d(TAG+"Files", "FileName:" + file[k].getPath());
		    Bitmap bMap=BitmapFactory.decodeFile(file[k].getPath());
		    if(bMap!=null)
			{
		    	Utils.bitmapToMat(bMap, imagen);
			    Log.d(TAG+"llenar_templates", "paso 3");
			    Probar(imagen,this.denominacionAprobar, expectedfoldervalue,listadecomparadores.get(i),c);
		    }
		}
	}
	
	public boolean Probar(Mat imagen, EDenominacionBilletes ExpectedValue,boolean imagenCorrespondeConExpectedValue,IReconocedores matcher,ContenedorEstadistico c ) 
	{
		
		List<EscenaProcesada> lista;
		try {
			lista = matcher.ProcesarImagen(imagen);
			for (EscenaProcesada escenaProcesada : lista) {
				if(escenaProcesada.Contraparete.denominacion==ExpectedValue)
				{
					if(escenaProcesada.correspondencia)
					{
						c.incCorrectos(escenaProcesada.tiempoDeProcesamiento);
					}
					else
					{
						c.incFalsoNegativo(escenaProcesada.tiempoDeProcesamiento);
					}
				}
				else
				{
					if(escenaProcesada.correspondencia)
					{
						c.incFalsoPositivo(escenaProcesada.tiempoDeProcesamiento);
					}
					else
					{
						c.incCorrectos(escenaProcesada.tiempoDeProcesamiento);
					}
				}
			}
		} 
		catch (NotEnougthKeypoints e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage() + e.getStackTrace());
		}
		catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			Log.e(TAG, ex.getMessage() + ex.getStackTrace());
		}
		
		return false;
	}
	
}
