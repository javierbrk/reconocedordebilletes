package inti.recon.backend;

import android.annotation.SuppressLint;
import android.util.Log;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static inti.recon.backend.BillSearchConstants.*;

class ReconocedorDorso implements Runnable {
    private final Billete needle;
    private final List<Billete> haystack;
    private final AtomicBoolean billeteEncontrado;
    private final AtomicReference<String> resultado;

    ReconocedorDorso(Billete needle, List<Billete> haystack, AtomicBoolean billeteEncontrado, AtomicReference<String> resultado) {
        this.needle = needle;
        this.haystack = haystack;
        this.billeteEncontrado = billeteEncontrado;
        this.resultado = resultado;
    }

    @SuppressLint({"SimpleDateFormat", "LongLogTag"})
    @Override
    public void run() {
        Log.d("Frente", "billetes:" + haystack.size());
        if (needle.getKFrente().toList().size() > Nfeatures) {
            DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
            List<MatOfDMatch> matches;
            List<DMatch> good_matches;
            MatOfDMatch goodMatches;
            LinkedList<Point> objList;
            LinkedList<Point> sceneList;
            MatOfPoint2f obj;
            MatOfPoint2f sce;
            Mat obj_corners;
            Mat scene_corners;
            List<KeyPoint> keypoints_objectList;
            List<KeyPoint> keypoints_sceneList;
            double distancia_AB, distancia_AC, distancia_AD;
            double angulo_A, angulo_B, angulo_C, angulo_D;

            for (int j = 0; j < haystack.size(); j++) {
                if(this.billeteEncontrado.get()){break;}
                Billete billeteActual = haystack.get(j);

                matches = new ArrayList<MatOfDMatch>();
                matcher.knnMatch(billeteActual.getDDorso(), needle.getDDorso(), matches, 2);

                good_matches = new ArrayList<DMatch>();
                for (int i = 0; i < matches.size(); i++) {

                    if (matches.get(i).toList().get(0).distance < Pdistancia * matches.get(i).toList().get(1).distance)
                        good_matches.add(matches.get(i).toList().get(0));
                }
                Log.d("Frente", "goog_matches:" + good_matches.size());
                goodMatches = new MatOfDMatch();
                goodMatches.fromList(good_matches);

                Mat outImg = new Mat();
                String fileName;

                if (good_matches.size() > Ngoodmatches) {
                    keypoints_objectList = billeteActual.getKDorso().toList();
                    keypoints_sceneList = needle.getKFrente().toList();

                    objList = new LinkedList<Point>();
                    sceneList = new LinkedList<Point>();
                    for (int i = 0; i < good_matches.size(); i++) {
                        objList.addLast(keypoints_objectList.get(good_matches.get(i).queryIdx).pt);
                        sceneList.addLast(keypoints_sceneList.get(good_matches.get(i).trainIdx).pt);
                    }
                    obj = new MatOfPoint2f();
                    sce = new MatOfPoint2f();
                    obj.fromList(objList);
                    sce.fromList(sceneList);

                    Mat hg = Calib3d.findHomography(obj, sce, Metodo, CotaRansac);

                    obj_corners = new Mat(4, 1, CvType.CV_32FC2);
                    obj_corners.put(0, 0, 0, 0);
                    obj_corners.put(1, 0, billeteActual.getDorso().cols(), 0);
                    obj_corners.put(2, 0, billeteActual.getDorso().cols(), billeteActual.getDorso().rows());
                    obj_corners.put(3, 0, 0, billeteActual.getDorso().rows());

                    scene_corners = new Mat(4, 1, CvType.CV_32FC2);
                    Core.perspectiveTransform(obj_corners, scene_corners, hg);


                    scene_corners.put(0, 0, scene_corners.get(0, 0)[0] + billeteActual.getDorso().cols(), scene_corners.get(0, 0)[1]);
                    scene_corners.put(1, 0, scene_corners.get(1, 0)[0] + billeteActual.getDorso().cols(), scene_corners.get(1, 0)[1]);
                    scene_corners.put(2, 0, scene_corners.get(2, 0)[0] + billeteActual.getDorso().cols(), scene_corners.get(2, 0)[1]);
                    scene_corners.put(3, 0, scene_corners.get(3, 0)[0] + billeteActual.getDorso().cols(), scene_corners.get(3, 0)[1]);


                    Point punto_A = new Point(scene_corners.get(0, 0));
                    Point punto_B = new Point(scene_corners.get(1, 0));
                    Point punto_C = new Point(scene_corners.get(2, 0));
                    Point punto_D = new Point(scene_corners.get(3, 0));

                    if(Debug){
                        MatOfByte drawnMatches = new MatOfByte();
                        Features2d.drawMatches(billeteActual.getFrente(), billeteActual.getKFrente(), needle.getFrente(), needle.getKFrente(), goodMatches, outImg, Scalar.all(-1), Scalar.all(-1), drawnMatches, Features2d.NOT_DRAW_SINGLE_POINTS);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        String currentDateandTime = sdf.format(new Date());
                        fileName = good_matches.size() + "_" + currentDateandTime + ".png";

                        Core.line(outImg, punto_A, punto_B, new Scalar(0, 255, 0), 4);
                        Core.line(outImg, punto_B, punto_C, new Scalar(0, 255, 0), 4);
                        Core.line(outImg, punto_C, punto_D, new Scalar(0, 255, 0), 4);
                        Core.line(outImg, punto_D, punto_A, new Scalar(0, 255, 0), 4);
                    }


                    distancia_AB = distancia(punto_A, punto_B);
                    distancia_AC = distancia(punto_A, punto_C);
                    distancia_AD = distancia(punto_A, punto_D);

                    angulo_A = angulo(punto_A, punto_D, punto_B);
                    angulo_B = angulo(punto_B, punto_A, punto_C);
                    angulo_C = angulo(punto_C, punto_B, punto_D);
                    angulo_D = angulo(punto_D, punto_C, punto_A);

                    final float pi = (float) Math.PI;
                    if ((angulo_A < (pi - min_ang)) && (angulo_A > min_ang) &&
                            (angulo_B < (pi - min_ang)) && (angulo_B > min_ang) &&
                            (angulo_C < (pi - min_ang)) && (angulo_C > min_ang) &&
                            (angulo_D < (pi - min_ang)) && (angulo_D > min_ang) &&
                            distancia_AB > dist_AB_min && distancia_AC > dist_AC_min && distancia_AD > dist_AD_min
                            ) {
                        // distancia_AB > dist_AB_min && distancia_AC > dist_AC_min && distancia_AD > dist_AD_min
                        // distancia_AB > 100 && distancia_AC > 120 && distancia_AD > 50
                        //Encontre un billete
                        // res = res + j + " ";
                        // Log.d("Billete OK", String.format("distancia_AB > %.1f && distancia_AC > %.1f && distancia_AD > %.1f", distancia_AB, distancia_AC, distancia_AD));
                        this.resultado.set(j + " ");
                        this.billeteEncontrado.set(true);
                        Log.d("Direccion billete encontrado", String.format("%d", billeteActual.getDDorso().getNativeObjAddr()));
                        break;
                    }
                }
                if(Debug){SaveImage(outImg, fileName);}
            }
        }
    }
}
