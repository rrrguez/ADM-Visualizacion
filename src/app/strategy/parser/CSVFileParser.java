/**
 * Rebeca Rodríguez Rodríguez (alu0101394763)
 * Universidad de La Laguna
 * Escuela de Doctorado y Estudios de Posgrado
 * Máster en Ingeniería Informática
 * Análisis de Datos Masivos
 * Práctica Visualización de Datos I
 */

package app.strategy.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVFileParser extends FileParser {
    // Constructor por defecto
    public CSVFileParser() {
        this.columnNames = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    // Método para parsear el fichero
    public void Parse(ArrayList<String> csv_lines) {
        // Guardamos el título de las columnas en columnNames
        String header = csv_lines.get(0);
        if (header != null) {
            String[] columns = header.split(",");
            columnNames.addAll(Arrays.asList(columns));
            for (int i = 0; i < columnNames.size(); ++i) {
                System.out.println(columnNames.get(i));

            }
        }
        // Guardamos los datos en data
        for (int i = 1; i < csv_lines.size(); ++i) {
            String line = csv_lines.get(i);
            String[] values = parseLine(line);  // Usamos parseLine también para las filas de datos
            List<String> rowData = new ArrayList<>(Arrays.asList(values));
            data.add(rowData);
        }
    }

    // Método para parsear una línea y manejar comas dentro de comillas
    private String[] parseLine(String line) {
        // Expresión regular para dividir correctamente, considerando las comas dentro de comillas
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return line.split(regex);
    }
}

