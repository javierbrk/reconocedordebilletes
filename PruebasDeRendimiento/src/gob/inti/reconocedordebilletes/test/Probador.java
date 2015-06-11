package gob.inti.reconocedordebilletes.test;

import gob.inti.reconocedordebilletes.EDenominacionBilletes;
import gob.inti.reconocedordebilletes.IComparadorDeReconocedores;

import java.util.ArrayList;

import org.opencv.android.Utils;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;

import org.opencv.core.Mat;

import android.os.Environment;


public class Probador {
	private static final String TAG = "PROBADOR";
	ArrayList<IComparadorDeReconocedores> listadecomparadores;
	ArrayList<String> listadecarpetasconimagenescorrectas;
	ArrayList<String> listadecarpetasconimagenesincorrectas;
	private EDenominacionBilletes denominacionAprobar;
	
	public Probador (EDenominacionBilletes denominacionAprobar)
	{
		listadecomparadores = new ArrayList<IComparadorDeReconocedores>() ;
		listadecarpetasconimagenescorrectas = new ArrayList<String>() ;
		listadecarpetasconimagenesincorrectas  = new ArrayList<String>() ;
		this.denominacionAprobar = denominacionAprobar;
	}
	
	public void agregarComparador(IComparadorDeReconocedores c)
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
			for (int i = 0; i < listadecomparadores.size(); i++) {
				listadecomparadores.get(i).Inicializar(0); 
				for (int j = 0; j < listadecarpetasconimagenescorrectas.size(); j++) {
					
					path = Environment.getExternalStorageDirectory().toString()+"/Pictures" +listadecarpetasconimagenescorrectas.get(j);
					probarUnaCarpeta(path, i,true);
					
				}
				for (int k = 0; k < listadecarpetasconimagenesincorrectas.size(); k++) {
					path = Environment.getExternalStorageDirectory().toString()+"/Pictures" +listadecarpetasconimagenesincorrectas.get(k);
					probarUnaCarpeta(path, i,false);
				}
				Log.d(TAG+"  -   estadisticas - ",listadecomparadores.get(i).estadisticas().toString());
			}
			
		}
	}

	private void probarUnaCarpeta(String path, int i, boolean expectedfoldervalue) {
		Log.d("Files", "Path: " + path);
		File f = new File(path);        
		File file[] = f.listFiles();
		Log.d("Files", "Size: "+ file.length);
		for (int k=0; k < file.length; k++)
		{
			boolean b = true;
			Mat imagen =  new Mat();
		    Log.d(TAG+"Files", "FileName:" + file[k].getPath());
		    Bitmap bMap=BitmapFactory.decodeFile(file[k].getPath());
		    if(bMap!=null)
			{
		    	Utils.bitmapToMat(bMap, imagen);
			    Log.d(TAG+"llenar_templates", "paso 3");
			    Imgproc.cvtColor(imagen, imagen, Imgproc.COLOR_BGR2GRAY);
			    listadecomparadores.get(i).Probar(imagen,this.denominacionAprobar, expectedfoldervalue);
		    }
		}
	}
	
}
