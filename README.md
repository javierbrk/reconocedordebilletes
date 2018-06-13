# reconocedordebilletes
Reconocedor de denominación de billetes para personas en situacion de discapaciad visual. Concretamente un lector de billetes para ciegos.

Creemos importante que este proyecto sea un proyecto de código abierto y disponible para todos, es por ello que nos interesa de sobremanera que se puedan sumar todos los que quieran participar.
 
Actualmente contamos con un prototipo funcionando en PC (Ubuntu) y en dispositivos moviles (android 4.0.3)
http://44jaiio.sadio.org.ar/sites/default/files/sts252-263.pdf

De acuerdo a las pruebas realizadas con billetes para ciertos billetes funciona muy bien pero para otros no tanto ($2 reverso en mal estado).

De acuerdo a las ultimas pruebas hemos notado que es necesario que cada dispositivo realice una calibración previa. El principal factor que afecta a la deteccion es la resolucion de la camara, por ahora es importante configurar la camara a una resolucion de 800*600.

Dado que el prototipo funciona podemos afirmar que la metodología utilizada es correcta. Pero dada la complejidad de la misma será necesario realizar ajustes finos que involucran pruebas exhaustivas y documentadas de laboratorio.

Ya contamos con un banco de pruebas para los diferentes para diferentes billetes.


Los pasos a seguir son:
======================

     Realizar pruebas con diferentes configuraciones del algoritmo de detección para encontrar el mejor.
     Realizar mejoras de rendimiento para hacer que funcione mas rápido en dispositivos de alta gama.
     Pruebas con sujetos en condiciones de laboratorio.
     Pruebas con sujetos en condiciones reales.

Informacion para desarrolladores
================================

En la Wiki del proyecto encontraran toda la informacion necesaria para compilar y ejecutar la aplicación

https://github.com/javierbrk/reconocedordebilletes/wiki


##Publicaciones
Se realizarán publicaciones de tipo técnicas con el fin de recibir realimentación y sugerencias sobre la metodología utilizada
https://www.inti.gob.ar/tecnointi/pdf/TecnoINTI2015.pdf pg 396#

De acuerdo a lo discutido y expresado en múltiples oportunidades no se realizaran publicaciones de tipo difusión/divulgación hasta no contar con pruebas reales de personas con problemas de visión.

##Referencias

Procesamiento de imagenes en general http://szeliski.org/Book/drafts/SzeliskiBook_20100903_draft.pdf

Carlos Miguel Correia da Costa, (2014) "Multiview banknote recognition with component and shape analysis" http://www.kjer.in/archives/vol2/issue1/61.%20Currency%20Recognition%20in%20Mobile%20Application%20for%20Visually%20Challenged.pdf

Ilya Toytman Jonathan Thambidurai, "Banknote recognition on Android platform" https://stacks.stanford.edu/file/druid:my512gb2187/Toytman_Thambidurai_Coin_counting_with_Android.pdf

Euro money bill recognition vgg.fiit.stuba.sk/wordpress/2013-07/euro-money-bill-recognition/
--------------------------------
