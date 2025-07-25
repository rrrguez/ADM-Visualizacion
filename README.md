# Visualización de Datos

Esta aplicación Java utiliza las librerías Swing y JFreeChart para la generación de gráficos a partir de un archivo de datos de entrada mediante una interfaz intuitiva.

Se admiten dos formatos de archivo: CSV y Excel. El programa cuenta con dos *parsers*, uno para cada formato, y el patrón *Strategy* permite cambiar de un *parser* a otro fácilmente.

Asimismo, se admiten archivos de entrada válidos que estén almacenados tanto de forma local como a partir de una URL, lo que demuestra la versatilidad de la herramienta.

En esta iteración, se pueden generar los siguientes tipos de gráficos (para lo que también se utiliza el patrón *Strategy*):
* Gráfico de barras.
* Graáfico de líneas.
* Nube de puntos.
