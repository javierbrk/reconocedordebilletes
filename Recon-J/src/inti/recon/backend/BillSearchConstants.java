package inti.recon.backend;

import android.os.Environment;
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.File;

final class BillSearchConstants {
    private static final String TAG = "OCVSample::Activity";
    static final double Pdistancia = 0.7;
    static final int Ngoodmatches = 3, Nfeatures = 3;
    static final int Metodo = 8;//0 usa todos los goodmatches, 8 RANSAC usa solo los que cumplen la reprojecccion a una distancia menor de una cota
    static final double CotaRansac = 10;//cota de RANSAC desde 1 a 10 pixels generalmente
    static final boolean Debug = false;
    static final float min_ang = (float) 0.35;
    static final int dist_AB_min = 150;
    static final int dist_AC_min = 160;
    static final int dist_AD_min = 60;
    static final long SEARCH_TIMEOUT = 5;
    static final int MAX_ACTIVE_THREADS = 2;

    static double distancia(Point pt1, Point pt2) {
        return Math.sqrt(Math.pow(pt1.x - pt2.x, 2) + Math.pow(pt1.y - pt2.y, 2));
    }

    static double angulo(Point uno, Point dos, Point tres) {

        //transladamos al origen de coordenadas los tres puntos
        Point pi = new Point(dos.x - uno.x, dos.y - uno.y);
        Point pj = new Point(tres.x - uno.x, tres.y - uno.y);
        //calculamos su angulo de coordenada polar
        double ang_pi = Math.atan2(pi.x, pi.y);
        double ang_pj = Math.atan2(pj.x, pj.y);

        //hallamos la diferencia
        double ang = ang_pj - ang_pi;

        //Si el angulo es negativo le sumamos 2PI para obtener el
        //angulo en el intervalo [0-2PI];
        //siempre obtenemos ángulos positivos (en sentido antihorario)
        if (ang < 0.0)
            return ang + (2.0 * Math.PI);
        else
            return ang;
    }


    static void SaveImage(Mat mat, String filename) {
        Mat mIntermediateMat = new Mat();

        Imgproc.cvtColor(mat, mIntermediateMat, Imgproc.COLOR_RGBA2BGR, 3);

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(path, filename);

        Boolean bool;
        filename = file.toString();
        bool = Highgui.imwrite(filename, mIntermediateMat);

        if (bool)
            Log.i(TAG, "SUCCESS writing image to external storage");
        else
            Log.i(TAG, "Fail writing image to external storage");
    }

}
