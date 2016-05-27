/*
 * DisplayVideoWebcam2.cpp
 *
 *  Created on: 10/02/2014
 *      Author: ignacio
 *
 * 2° Prototipo lector de billetes para personas en situación de discapacidad visual,
 * reconoce la denominación de los billetes argentinos y comunica su valor por medios sonoros
 */
#include <unistd.h>
#include <math.h>
#include <stdio.h>
#include <iostream>
#include <stdlib.h>
#include <string>
#include <cv.h>
#include <highgui.h>
#include <cstdlib>
#include <pthread.h>
#include "opencv2/core/core.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/nonfree/features2d.hpp"
#include <ctime>
#include <time.h>
#include <sys/time.h>



using namespace cv;
using namespace std;

/*Devuelve verdadero si reconoce algun billete*/
bool reconocerp(Mat img,Mat img_object,std::vector<KeyPoint> keypoints_object,Mat descriptors_object,int idpesos);

/*Calcula la distancia del rectangulo encontrado*/
float distancia(Point2f pt1,Point2f pt2);

/*Funcion para que ejecuten los threads*/
void *reconocerp_threads(void *threadarg);

/*Estructura para pasar argumentos a las funciones que ejecutan los distintos threads*/
struct thread_data{
   Mat  img;
   Mat  img_object;
   std::vector<KeyPoint> keypoints_object;
   Mat descriptors_object;
   int idpesos;
};

struct thread_data thread_data_array[14];


int main(int argc, char* argv[])
{
	VideoCapture cap(0); // abre el video de la camara numero 0 que este en el equipo

    if (!cap.isOpened())  //si no puede termina el programa
    {
        cout << "No se pudo abrir el video de la camara" << endl;
        return -1;
    }

    double dWidth = cap.get(CV_CAP_PROP_FRAME_WIDTH); //toma el ancho del video
    double dHeight = cap.get(CV_CAP_PROP_FRAME_HEIGHT); //toma el alto del video

    cout << "Frame size : " << dWidth << " x " << dHeight << endl;

    namedWindow("MyVideo",CV_WINDOW_AUTOSIZE); //Creo una ventana para la muestra del video de la camara abierta
    int contador=0,ejec=0;

    /*Creo los templates de cada billete y su dorso como modelos para reconocerlos*/
    Mat vector_imagenes[14],vector_descriptores[14];
	std::vector<KeyPoint> vector_keypoints[14];

	/*Cargo las distintas imagenes el el vector de imagenes*/
	vector_imagenes[0]=imread( "../images/templates/2p.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[1]=imread( "../images/templates/2pd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[2]=imread( "../images/templates/5p.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[3]=imread( "../images/templates/5pd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[4]=imread( "../images/templates/10p.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[5]=imread( "../images/templates/10pd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[6]=imread( "../images/templates/20p.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[7]=imread( "../images/templates/20pd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[8]=imread( "../images/templates/50p.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[9]=imread( "../images/templates/50pd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[10]=imread( "../images/templates/100p.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[11]=imread( "../images/templates/100pd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[12]=imread( "../images/templates/101p.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[13]=imread( "../images/templates/101pd.jpg", IMREAD_GRAYSCALE );

	/*Paso 1: Detecto los keypoints usando SURF Detector de cada template*/
	int minHessian = 400;
	SurfFeatureDetector detector_templates(minHessian);
	for(int i=0;i<14;i++){
		detector_templates.detect( vector_imagenes[i],vector_keypoints[i]);
	}

	/*Paso 2: Calculamos los descriptores usando SURF Extractor*/
	SurfDescriptorExtractor extractor;
	for(int i=0;i<14;i++){
		extractor.compute( vector_imagenes[i], vector_keypoints[i], vector_descriptores[i] );
	}

	/*Comienzo de monitoreo de la camara y reconocimiento de billetes*/
    while (1)
    {
    	contador++;
        Mat frame,framerec;

        bool bSuccess = cap.read(frame); // leemos un nuevo frame de la camara

         if (!bSuccess) //si ocurrio algun error finalizamos el programa
        {
             cout << "No se pudo leer el nuevo frame de la camara" << endl;
             break;
        }

        imshow("MyVideo", frame);//mostramos el frame en la ventana "MyVideo"

        /*
         * Cada 90 frames tomo una muestra y lanzo los threads para comparar
         * con los templates de los billetes que creamos anteriormente
         * */

        if(contador % 60 == 0){
        	framerec=frame;
        	pthread_t threads[14];
			int rc;
			for(int t=0;t<14;t++){
				thread_data_array[t].img = framerec;
				thread_data_array[t].img_object = vector_imagenes[t];
				thread_data_array[t].keypoints_object = vector_keypoints[t];
				thread_data_array[t].descriptors_object = vector_descriptores[t];
				thread_data_array[t].idpesos = t;
				cout << "main() : creando thread, " << t << endl;
				rc = pthread_create(&threads[t], NULL, reconocerp_threads,(void *) &thread_data_array[t]);
				if (rc){
					cout << "Error:no se pudo crear thread," << rc << endl;
					//exit(-1);
				}
			}
			//pthread_exit(NULL);

        	ejec++;
        }


        if (waitKey(30) == 27) //espero por la tecla 'esc' por 30ms. Si 'esc' se preciono termina el programa.
       {
            cout << "Game Over " << ejec << endl;
            break;
       }
    }
    return 0;

}

//Devuelve verdadero si reconoce un billete de pesos segun denominacion
bool reconocerp(Mat img,Mat img_object,std::vector<KeyPoint> keypoints_object,Mat descriptors_object,int idpesos){
	bool res=false;
	Mat img_scene;

	cvtColor( img, img_scene, CV_BGR2GRAY );//transformo imagen el blanco y negro

	if(!img_scene.data )
	  { std::cout<< " --(!) Error leyendo imagen " << std::endl; return -1; }

	  //Paso 1: Detecto los keypoints usando SURF Detector
	  int minHessian = 400;

	  SurfFeatureDetector detector( minHessian );

	  std::vector<KeyPoint> keypoints_scene;

	  detector.detect( img_scene, keypoints_scene );

	  //Paso 2: Calculo los descriptors usando SURF Extractor
	  SurfDescriptorExtractor extractor;

	  Mat descriptors_scene;

	  extractor.compute( img_scene, keypoints_scene, descriptors_scene );

	  //Paso 3: Comparo los vectores de descriptores usando FLANN matcher
	  FlannBasedMatcher matcher;
	  std::vector< DMatch > matches;

	  if (descriptors_scene.empty()){
		  return false;
	  }
	  matcher.match( descriptors_object, descriptors_scene, matches );

	  double max_dist = 0; double min_dist = 100;

	  //Calculo la distancia minima y maxima entre los keypoints
	  for( int i = 0; i < descriptors_object.rows; i++ )
	  {
		  double dist = matches[i].distance;
		  if( dist < min_dist ) min_dist = dist;
		  if( dist > max_dist ) max_dist = dist;
	  }

	  //Dibujo solo los que estan a una distancia menor que 3*min_dist
	  std::vector< DMatch > good_matches;

	  for( int i = 0; i < descriptors_object.rows; i++ )
	  {
		  if( matches[i].distance < 3*min_dist )
		  {
			  good_matches.push_back( matches[i]);
		  }
	  }

	  Mat img_matches;
	  drawMatches( img_object, keypoints_object, img_scene, keypoints_scene,
	               good_matches, img_matches, Scalar::all(-1), Scalar::all(-1),
	               vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS );


	  //Localizamos la imagen template en el frame
	  std::vector<Point2f> obj;
	  std::vector<Point2f> scene;

	  for( size_t i = 0; i < good_matches.size(); i++ )
	  {
	    //Tomamos los keypoints del los good_matches
	    obj.push_back( keypoints_object[ good_matches[i].queryIdx ].pt );
	    scene.push_back( keypoints_scene[ good_matches[i].trainIdx ].pt );
	  }

	  Mat H = findHomography( obj, scene, RANSAC );

	  //Tomamos las esquinas de la imagen detectada en el frame
	  std::vector<Point2f> obj_corners(4);
	  obj_corners[0] = Point(0,0); obj_corners[1] = Point( img_object.cols, 0 );
	  obj_corners[2] = Point( img_object.cols, img_object.rows ); obj_corners[3] = Point( 0, img_object.rows );
	  std::vector<Point2f> scene_corners(4);

	  perspectiveTransform( obj_corners, scene_corners, H);


	  //dibujamos las lineas entre las esquinas
	  Point2f offset( (float)img_object.cols, 0);
	  line( img_matches, scene_corners[0] + offset, scene_corners[1] + offset, Scalar(0, 255, 0), 4 );
	  line( img_matches, scene_corners[1] + offset, scene_corners[2] + offset, Scalar( 0, 255, 0), 4 );
	  line( img_matches, scene_corners[2] + offset, scene_corners[3] + offset, Scalar( 0, 255, 0), 4 );
	  line( img_matches, scene_corners[3] + offset, scene_corners[0] + offset, Scalar( 0, 255, 0), 4 );

	  //calculamos la distancia entre los puntos de las esquinas
	  Point2f punto0,punto1,punto2,punto3;
	  float d01,d02,d03;
	  punto0=scene_corners[0] + offset;
	  punto1=scene_corners[1] + offset;
	  punto2=scene_corners[2] + offset;
	  punto3=scene_corners[3] + offset;

	  d01=distancia(punto0,punto1);
	  d02=distancia(punto0,punto2);
	  d03=distancia(punto0,punto3);


	  std::string name = "Image.jpg", rutav="images/verdaderos/",rutaf="images/falsos/";
	  std::string result;

	  time_t now = time(0);
	  char* dt = ctime(&now);


	  if(d01>200.0 && d02>250 && d03>100 && d03*2<d01){
		  res=true;
		  //imshow( "Good Matches & Object detection", img_matches );//mostramos la la imagen detectada y el template correspondiente
		  if(idpesos<2){
			  system ("play ../sound/2p.wav");
		  }else if(idpesos<4){
			  system ("play ../sound/5p.wav");
		  }else if(idpesos<6){
			  system ("play ../sound/10p.wav");
		  }else if(idpesos<8){
			  system ("play ../sound/20p.wav");
		  }else if(idpesos<10){
			  system ("play ../sound/50p.wav");
		  }else if(idpesos<14){
			  system ("play ../sound/100p.wav");
		  }
		  //Casos verdaderos

		  result = rutav + dt + name;
		  imwrite(result, img_matches);
	  }else{
		  //Casos falsos
		  result = rutaf + dt + name;
		  imwrite(result, img_matches);
	  }
	  return res;
}

/*Funcion que calcula la distancia entre dos puntos en un plano 2D*/
float distancia(Point2f pt1,Point2f pt2){
	float res;
	res=sqrt(pow(pt1.x-pt2.x,2)+pow(pt1.y-pt2.y,2));
	return res;
}


/*Funcion para que ejecuten los threads*/
void *reconocerp_threads(void *threadarg)
{
	Mat  aimg,aimg_object,adescriptors_object;
	std::vector<KeyPoint> akeypoints_object;
	int aidpesos;
	struct thread_data *my_data;

	my_data = (struct thread_data *) threadarg;
	aimg=my_data->img;
	aimg_object=my_data->img_object;
	akeypoints_object=my_data->keypoints_object;
	adescriptors_object=my_data->descriptors_object;
	aidpesos=my_data->idpesos;
    long            ms; // Milliseconds
    long 	    ms2;
    time_t          s;  // Seconds
    time_t	    ss;
    struct timespec spec;
    struct timespec spec2;
   
    struct timeval start, end;

    long mtime, seconds, useconds;    gettimeofday(&start, NULL);
    usleep(2000);
    gettimeofday(&end, NULL);

    seconds  = end.tv_sec  - start.tv_sec;
    useconds = end.tv_usec - start.tv_usec;

    mtime = ((seconds) * 1000 + useconds/1000.0) + 0.5;

    cout << "Elapsed time: onds\n"<< mtime<< endl;

    
clock_gettime(CLOCK_REALTIME, &spec);

    s  = spec.tv_sec;
    ms = round(spec.tv_nsec / 1.0e6);
	reconocerp(aimg,aimg_object,akeypoints_object,adescriptors_object,aidpesos);
	clock_gettime(CLOCK_REALTIME, &spec2);

    ss  = spec2.tv_sec;

    ms2 = round(spec2.tv_nsec / 1.0e6);

	cout<<"tiempo insumido "<< ms2-ms <<endl;
	pthread_exit(NULL);
}
