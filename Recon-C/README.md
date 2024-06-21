# ReconC

## Configurar tu repo


```
$ git clone https://gitlab.keylabcloud.com/keylab/machinelearning/ReconC.git

```
## Modificaciones por medio de merge request

Si queremos hacer alguna modificacion correccion de bug o un fix o un feacture seguimos 
los siguientes pasos: 

Actualizamos a la ultima version del main creamos un branch nuevo hacemos los cambios 
y seguimos el ciclo  add -> commit -> push -> merge request 

```
$ git pull
$ git checkout -b fix-N main
$ git add .
$ git commit -m "Mensaje"
$ git push --set-upstream origin fix-N

```
y podemos realizar el pedido del merge request desde la web o el link que nos da git 


## Intalación de OPENCV LIB en Linux

En Otra carpeta que no se la del repo de ReconC por ejemplo en ~ 

```
$ cd ~
$ sudo apt install -y g++ cmake make git libgtk2.0-dev pkg-config
$ git clone https://github.com/opencv/opencv.git
$ git clone https://github.com/opencv/opencv_contrib.git
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

```
$ cd src
$ cmake . 
$ make 
$ ./Recon
```

