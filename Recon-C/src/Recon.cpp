/*
 * Recon.cpp
 *
 *  Created on: 10/02/2014
 *      Author: ignacio
 *
 * 2° Prototipo lector de billetes para personas en situación de discapacidad visual,
 * reconoce la denominación de los billetes argentinos y comunica su valor por medios sonoros
 */
#include <stdio.h>
#include <unistd.h>
#include <sys/time.h>
#include <opencv2/opencv.hpp>
#include <opencv2/xfeatures2d.hpp>

#define NFRAMES 60
#define NTEMPLATES 26

using namespace cv;
using namespace cv::xfeatures2d;
using namespace std;

/*Devuelve verdadero si reconoce algun billete*/
bool reconocerp(Mat img,Mat img_object,std::vector<KeyPoint> keypoints_object,Mat descriptors_object,int idpesos);

/*Calcula la distancia del rectangulo encontrado*/
float distancia(Point2f pt1,Point2f pt2);

/*Calcula angulo*/
float angulo(Point2f pt1, Point2f pt2, Point2f pt3);

/*Calcula si 2 distancias son parecidas 10% de tolerancia*/
bool sonDistanciasParecidas(double distancia1, double distancia2);

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

struct thread_data thread_data_array[NTEMPLATES];

int main(int argc, char* argv[])
{
	int contador=0,ejec=0;

	VideoCapture cap(0); // abre el video de la camara numero 0 que este en el equipo

    if (!cap.isOpened()){  //si no puede termina el programa
        cout << "No se pudo abrir el video de la camara" << endl;
        return -1;
    }

    double dWidth = cap.get(CAP_PROP_FRAME_WIDTH); //toma el ancho del video
    double dHeight = cap.get(CAP_PROP_FRAME_HEIGHT); //toma el alto del video

    cout << "Frame size : " << dWidth << " x " << dHeight << endl;

    namedWindow("Recon",WINDOW_AUTOSIZE); //Creo una ventana para la muestra del video de la camara abierta
    

    /*Creo los templates de cada billete y su dorso como modelos para reconocerlos*/
    Mat vector_imagenes[NTEMPLATES],vector_descriptores[NTEMPLATES];
	std::vector<KeyPoint> vector_keypoints[NTEMPLATES];

	/*Cargo las distintas imagenes el el vector de imagenes*/
	vector_imagenes[0]=imread( "../images/templates/milp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[1]=imread( "../images/templates/milpd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[2]=imread( "../images/templates/diezp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[3]=imread( "../images/templates/diezpd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[4]=imread( "../images/templates/dieznp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[5]=imread( "../images/templates/dieznpd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[6]=imread( "../images/templates/veintep.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[7]=imread( "../images/templates/veintepd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[8]=imread( "../images/templates/cincuentap.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[9]=imread( "../images/templates/cincuentapd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[10]=imread( "../images/templates/cienp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[11]=imread( "../images/templates/cienpd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[12]=imread( "../images/templates/cienevp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[13]=imread( "../images/templates/cienevpd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[14]=imread( "../images/templates/quinientosp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[15]=imread( "../images/templates/quinientospd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[16]=imread( "../images/templates/doscientosp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[17]=imread( "../images/templates/doscientospd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[18]=imread( "../images/templates/smmilp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[19]=imread( "../images/templates/smmilpd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[20]=imread( "../images/templates/dosmilp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[21]=imread( "../images/templates/dosmilpd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[22]=imread( "../images/templates/cincuentamalp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[23]=imread( "../images/templates/cincuentamalpd.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[24]=imread( "../images/templates/diezmilp.jpg", IMREAD_GRAYSCALE );
	vector_imagenes[25]=imread( "../images/templates/diezmilpd.jpg", IMREAD_GRAYSCALE );

	/*Paso 1 y 2: Detecto los keypoints usando SURF Detector de cada template y descriptores de los templates(billetes)*/
	int minHessian = 400;
	Ptr<SURF> detector = SURF::create( minHessian );
	for(int i=0;i < NTEMPLATES;i++){
		detector->detectAndCompute( vector_imagenes[i], noArray(), vector_keypoints[i], vector_descriptores[i]);
	}
	
	/*Comienzo de monitoreo de la camara y reconocimiento de billetes*/
    while (true){
    	contador++;
        Mat frame,framerec;

        bool bSuccess = cap.read(frame); // leemos un nuevo frame de la camara

        if (!bSuccess){ //si ocurrio algun error finalizamos el programa
            cout << "No se pudo leer el nuevo frame de la camara" << endl;
            break;
        }

        imshow("Recon", frame);//mostramos el frame en la ventana "Recon"

        /*
         * Cada NFRAMES frames tomo una muestra y lanzo los threads para comparar
         * con los templates de los billetes que creamos anteriormente
         * */

        if(contador % NFRAMES == 0){
        	framerec=frame;
        	pthread_t threads[NTEMPLATES];
			int rc;
			for(int t=0;t<NTEMPLATES;t++){
				thread_data_array[t].img = framerec;
				thread_data_array[t].img_object = vector_imagenes[t];
				thread_data_array[t].keypoints_object = vector_keypoints[t];
				thread_data_array[t].descriptors_object = vector_descriptores[t];
				thread_data_array[t].idpesos = t;
				cout << "main() : creando thread, " << t << endl;
				rc = pthread_create(&threads[t], NULL, reconocerp_threads,(void *) &thread_data_array[t]);
				if (rc){
					cout << "Error:no se pudo crear thread," << rc << endl;
				}
			}
			ejec++;
        }


        if (waitKey(30) == 27){ //espero por la tecla 'esc' por 30ms. Si 'esc' se preciono termina el programa.
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

	cvtColor( img, img_scene,  COLOR_RGB2GRAY);//transformo imagen el blanco y negro
	
	if(!img_scene.data ){
		std::cout<< " --(!) Error leyendo imagen " << std::endl; 
		return -1; 
	}

	//Paso 1: Detecto los keypoints usando SURF Detector
	int minHessian = 400;

	//SurfFeatureDetector detector( minHessian );
	Ptr<SURF> detector = SURF::create( minHessian );
	  		
	std::vector<KeyPoint> keypoints_scene;

	//Paso 2: Calculo los descriptors usando SURF Extractor
	Mat descriptors_scene;
	detector->detectAndCompute( img_scene, noArray(), keypoints_scene, descriptors_scene );	
	//Paso 3: Comparo los vectores de descriptores usando FLANN matcher
	if (descriptors_scene.empty()){
		return false;
	}
	  
	Ptr<DescriptorMatcher> matcher = DescriptorMatcher::create(DescriptorMatcher::FLANNBASED);
 	std::vector< std::vector<DMatch> > knn_matches;
 	matcher->knnMatch( descriptors_object, descriptors_scene, knn_matches, 2 );
	
	//-- Filter matches using the Lowe's ratio test
	const float ratio_thresh = 0.75f;
 	std::vector<DMatch> good_matches;
 	for (size_t i = 0; i < knn_matches.size(); i++){
 		if (knn_matches[i][0].distance < ratio_thresh * knn_matches[i][1].distance){
 			good_matches.push_back(knn_matches[i][0]);
 		}
 	}
	
	if(good_matches.size()< 4) {
		cout << "No hay minimo de good matches \n" << idpesos << "\n" <<endl;
		return res;
	}
	
	Mat img_matches;
	drawMatches( img_object, keypoints_object, img_scene, keypoints_scene,
	            good_matches, img_matches, Scalar::all(-1), Scalar::all(-1),
	            vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS );


	//Localizamos la imagen template en el frame
	std::vector<Point2f> obj;
	std::vector<Point2f> scene;

	for( size_t i = 0; i < good_matches.size(); i++ ){
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

    std::string name = "Image.jpg", rutav="../images/verdaderos/",rutaf="../images/falsos/";
	std::string result;

	time_t now = time(0);
	char* dt = ctime(&now);
	
	//dibujamos las lineas entre las esquinas
	Point2f offset( (float)img_object.cols, 0);
	line( img_matches, scene_corners[0] + offset, scene_corners[1] + offset, Scalar(0, 255, 0), 4 );
	line( img_matches, scene_corners[1] + offset, scene_corners[2] + offset, Scalar( 0, 255, 0), 4 );
	line( img_matches, scene_corners[2] + offset, scene_corners[3] + offset, Scalar( 0, 255, 0), 4 );
	line( img_matches, scene_corners[3] + offset, scene_corners[0] + offset, Scalar( 0, 255, 0), 4 );

	//calculamos la distancia entre los puntos de las esquinas para calcular si se encuentra o no un billete
	Point2f punto0,punto1,punto2,punto3;
	float d01,d02,d03,d12,d23,a0,a1,a2,a3;
	punto0=scene_corners[0] + offset;
	punto1=scene_corners[1] + offset;
	punto2=scene_corners[2] + offset;
	punto3=scene_corners[3] + offset;

	d01=distancia(punto0,punto1);
	d02=distancia(punto0,punto2);
	d03=distancia(punto0,punto3);//diagonal
	d12=distancia(punto1,punto2);
	d23=distancia(punto2,punto3);	


	a0=angulo(punto0,punto3,punto1);	
	a1=angulo(punto1,punto0,punto2);	
  	a2=angulo(punto2,punto1,punto3);	
  	a3=angulo(punto3,punto2,punto0);	


    std::string a0Str = std::to_string(a0);	
	std::string a1Str = std::to_string(a1);	
	std::string a2Str = std::to_string(a2);	
	std::string a3Str = std::to_string(a3);	
	std::string d01Str = std::to_string(d01);	
	std::string d02Str = std::to_string(d02);
	std::string d03Str = std::to_string(d03);
	std::string d12Str = std::to_string(d12);
	std::string d23Str = std::to_string(d23);
	  	  

	if((a0<(3.1415-0.35)) && (a0 > 0.35) &&
		(a1<(3.1415-0.35)) && (a1 > 0.35) &&
		(a2<(3.1415-0.35)) && (a2 > 0.35) &&
		(a3<(3.1415-0.35)) && (a3 > 0.35) &&
		d01>50 && d02>60 && d03 > 20 &&
		sonDistanciasParecidas((double)d01,(double)d23) && sonDistanciasParecidas((double)d12,(double)d03)
	){
		res=true;
		if(idpesos==0 || idpesos==1){
			system ("echo 'mil pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==2 || idpesos==3){
			system ("echo 'diez pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==4 || idpesos==5){
			system ("echo 'diez pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==6 || idpesos==7){
			system ("echo 'veinte pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==8 || idpesos==9){
			system ("echo 'cincuenta pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==10 || idpesos==11 || idpesos==12 || idpesos==13){
			system ("echo 'cien pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==14 || idpesos==15){
			system ("echo 'quinintos pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==16 || idpesos==17){
			system ("echo 'doscientos pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==18 || idpesos==19){
			system ("echo 'mil pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==20 || idpesos==21){
			system ("echo 'dos mil pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==22 || idpesos==23){
			system ("echo 'cincuenta pesos argentinos.' | festival --language spanish --tts");
		}else if(idpesos==24 || idpesos==25){
			system ("echo 'diez mil pesos argentinos.' | festival --language spanish --tts");
		} 
		//Guardo casos positivos y falsos positivos
	  	result = rutav + dt +"_"+ a0Str +"_"+ a1Str+"_"+ a2Str +"_"+ a3Str+"_"+ d01Str +"_"+ d02Str +"_"+ d03Str +"_"+ d12Str+"_"+ d23Str+"_"+ name;
		imwrite(result, img_matches);
	}else{
		//Guardo casos negativos y falsos negativos 
		result = rutaf + dt +"_"+ a0Str +"_"+ a1Str+"_"+ a2Str +"_"+ a3Str+"_"+ d01Str +"_"+ d02Str +"_"+ d03Str +"_"+ d12Str+"_"+ d23Str+"_"+ name;
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

/*Funcion que calcula el angulo */
float angulo(Point2f uno,Point2f dos,Point2f tres){
	float ang,ang_pi,ang_pj;
	//transladamos al origen de coordenadas los tres puntos
	Point2f pi = Point(dos.x-uno.x,dos.y-uno.y);
	Point2f pj = Point(tres.x-uno.x,tres.y-uno.y);
	//calculamos su angulo de coordenada polar
	ang_pi = atan2((double)pi.x,(double)pi.y);
	ang_pj = atan2((double)pj.x,(double)pj.y);
	//hallamos la diferencia
	ang = ang_pj- ang_pi;
	//Si el angulo es negativo le sumamos 2PI para obtener el
	//angulo en el intervalo [0-2PI]; 
	//siempre obtenemos ángulos positivos (en sentido antihorario)
	if (ang<0.0){
		return ang+(2.0*(4.0 * atan(1.0)));
	} else {
		return ang;
	}
}

//Funcion que me dice si 2 distancias son parecidas
bool sonDistanciasParecidas(double distancia1, double distancia2) {
    // Calcula la diferencia absoluta entre las dos distancias
    double diferencia = std::abs(distancia1 - distancia2);

    // Calcula el 10% de la distancia1
    double tolerancia = 0.15 * distancia1;

    // Comprueba si la diferencia es menor o igual a la tolerancia
    if (diferencia <= tolerancia) {
        return true; // Las distancias son parecidas
    } else {
        return false; // Las distancias no son parecidas
    }
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
    long ms; // Milliseconds
    long ms2;
    time_t s;  // Seconds
    time_t ss;
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
	try {
		reconocerp(aimg,aimg_object,akeypoints_object,adescriptors_object,aidpesos);
	}catch(const std::exception& e){
		cout << "Fallo en el reconocerp!!! " << e.what() << endl;
	}
	clock_gettime(CLOCK_REALTIME, &spec2);

    ss  = spec2.tv_sec;

    ms2 = round(spec2.tv_nsec / 1.0e6);

	cout<<"tiempo insumido "<< ms2-ms <<endl;
	pthread_exit(NULL);
}
