# Visualización de Datos (I)

Esta aplicación Java se ha desarrollado para la asignatura Análisis de Datos Masivos. Utiliza las librerías Swing y JFreeChart para la generación de gráficos a partir de un archivo de datos de entrada mediante una interfaz intuitiva.

Se admiten dos formatos de archivo: CSV y Excel. El programa cuenta con dos *parsers*, uno para cada formato, y el patrón *Strategy* permite cambiar de un *parser* a otro fácilmente.

Asimismo, se admiten archivos de entrada válidos que estén almacenados tanto de forma local como a partir de una URL, lo que demuestra la versatilidad de la herramienta.

En esta iteración, se pueden generar los siguientes tipos de gráficos (para lo que también se utiliza el patrón *Strategy*):
* Gráfico de barras.
* Graáfico de líneas.
* Nube de puntos.

Se proporciona un archivo `traffic_accidents.csv` de muestra, disponible abiertamente en Kaggle ([fuente](https://www.kaggle.com/datasets/oktayrdeki/traffic-accidents)).

Se ha utilizado el *plugin* PlantUML para la generación del Diagrama de Clases del programa.
