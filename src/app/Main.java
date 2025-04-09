/**
 * Rebeca Rodríguez Rodríguez (alu0101394763)
 * Universidad de La Laguna
 * Escuela de Doctorado y Estudios de Posgrado
 * Máster en Ingeniería Informática
 * Análisis de Datos Masivos
 * Práctica Visualización de Datos I
 */

package app;

import app.gui.ChartGUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Se crea la ventana interactiva
        ChartGUI gui = new ChartGUI();
        gui.setVisible(true);
    }
}