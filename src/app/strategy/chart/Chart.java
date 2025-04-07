/**
 * Rebeca Rodríguez Rodríguez (alu0101394763)
 * Universidad de La Laguna
 * Escuela de Doctorado y Estudios de Posgrado
 * Máster en Ingeniería Informática
 * Análisis de Datos Masivos
 * Práctica Visualización de Datos I
 */

package app.strategy.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class Chart extends ApplicationFrame {
    public Chart(String title) {
        super(title);
    }

    public abstract void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int xAxis, int yAxis);

    // Método para crear el set de datos a representar
    protected DefaultCategoryDataset createDataset(List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series = headers.get(yAxis);

        // Mapa ordenado: intenta ordenar numéricamente si es posible
        Map<String, Double> categoryMap = new TreeMap<>((a, b) -> {
            try {
                return Integer.compare(Integer.parseInt(a.trim()), Integer.parseInt(b.trim()));
            } catch (NumberFormatException e) {
                return a.compareTo(b);
            }
        });

        for (List<String> rowData : data) {
            if (rowData.size() > xAxis && rowData.size() > yAxis) {
                String category = rowData.get(xAxis).trim();
                String yValueStr = rowData.get(yAxis).trim();

                if (!category.isEmpty() && !yValueStr.isEmpty()) {
                    try {
                        double value = Double.parseDouble(yValueStr);
                        categoryMap.put(category, categoryMap.getOrDefault(category, 0.0) + value);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor Y no numérico: " + yValueStr);
                    }
                }
            }
        }

        // Debug: mostrar lo que se va a añadir al gráfico
        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            dataset.addValue(entry.getValue(), series, entry.getKey());
        }

        return dataset;
    }
}

