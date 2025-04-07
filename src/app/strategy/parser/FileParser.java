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
import java.util.List;

public abstract class FileParser {
    protected ArrayList<String> columnNames;
    protected List<List<String>> data;

    public FileParser() {}

    public abstract void Parse(ArrayList<String> file);

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }
    public List<List<String>> getData() {
        return data;
    }
}
