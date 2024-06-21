# ReconC

## Intalación de OPENCV LIB en Linux version 4.8.0

En Otra carpeta que no se la del repo de ReconC por ejemplo en ~ 

```
$ cd ~
$ sudo apt install -y g++ cmake make git libgtk2.0-dev pkg-config
$ git clone https://github.com/opencv/opencv.git
$ git clone https://github.com/opencv/opencv_contrib.git
$ cd opencv
$ git checkout tags/4.8.0
$ cd ..
$ cd opencv_contrib
$ git checkout tags/4.8.0
$ cd ..
$ mkdir -p build && cd build
$ cmake -D CMAKE_BUILD_TYPE=RELEASE \
    		-D CMAKE_INSTALL_PREFIX=/usr/local \
    		-D INSTALL_C_EXAMPLES=ON \
    		-D INSTALL_PYTHON_EXAMPLES=ON \
    		-D OPENCV_GENERATE_PKGCONFIG=ON \
    		-D OPENCV_ENABLE_NONFREE=ON \
    		-D OPENCV_EXTRA_MODULES_PATH=../opencv_contrib/modules \
    		-D BUILD_EXAMPLES=ON ../opencv
$ make -j4
$ sudo make install
```

## Instalamos text to speech

```
$ sudo apt-get install festival festvox-ellpc11k
```

## Prueba de text to speech

```
$ echo "quinientos pesos argentinos." | festival --language spanish --tts
```

## Compilación y Ejecución de Recon 

Ahora si en la Carpeta Recon-C del Repo del reconocedor de billetes

```
$ cd src
$ cmake . 
$ make 
$ ./Recon
```

