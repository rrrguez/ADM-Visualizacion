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
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.ArrayList;

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

    protected DefaultCategoryDataset createDataset(List<String> headers, List<List<String>> data, int xAxis, int yAxis, int groupByAxis) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Map<String, Double>> groupedData = new LinkedHashMap<>(); // Conserva orden de inserción
        Set<String> allCategories = new HashSet<>();

        boolean isNumericCategory = isColumnNumeric(data, xAxis);

        for (List<String> rowData : data) {
            if (rowData.size() > Math.max(xAxis, Math.max(yAxis, groupByAxis))) {
                String category = rowData.get(xAxis).trim();
                String group = rowData.get(groupByAxis).trim();
                String yValueStr = rowData.get(yAxis).trim();

                if (!category.isEmpty() && !yValueStr.isEmpty() && !group.isEmpty()) {
                    try {
                        double value = Double.parseDouble(yValueStr);
                        allCategories.add(category);

                        Map<String, Double> groupMap = groupedData.computeIfAbsent(group, k -> new HashMap<>());
                        groupMap.put(category, groupMap.getOrDefault(category, 0.0) + value);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor Y no numérico: " + yValueStr);
                    }
                }
            }
        }

        // Orden global del eje X
        List<String> orderedCategories = new ArrayList<>(allCategories);
        orderedCategories.sort((a, b) -> {
            if (isNumericCategory) {
                try {
                    return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
                } catch (NumberFormatException e) {
                    return a.compareTo(b);
                }
            } else {
                return a.compareTo(b);
            }
        });

        // Añadir valores al dataset siguiendo orden global del eje X
        for (Map.Entry<String, Map<String, Double>> groupEntry : groupedData.entrySet()) {
            String groupName = groupEntry.getKey();
            Map<String, Double> values = groupEntry.getValue();

            for (String category : orderedCategories) {
                Double val = values.get(category);
                if (val != null) {
                    dataset.addValue(val, groupName, category);
                } else {
                    dataset.addValue(null, groupName, category); // Añade hueco para mantener el orden
                }
            }
        }

        return dataset;
    }

    protected XYDataset createXYDataset(List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(headers.get(yAxis)); // puedes nombrarlo como quieras

        for (List<String> row : data) {
            if (row.size() > Math.max(xAxis, yAxis)) {
                try {
                    double x = Double.parseDouble(row.get(xAxis).trim());
                    double y = Double.parseDouble(row.get(yAxis).trim());
                    series.add(x, y);
                } catch (NumberFormatException ignored) {
                    // Puedes registrar si quieres saber qué valores fallan
                }
            }
        }

        dataset.addSeries(series);
        return dataset;
    }

    protected XYDataset createXYDataset(List<String> headers, List<List<String>> data, int xAxis, int yAxis, int groupByAxis) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        Map<String, XYSeries> seriesMap = new LinkedHashMap<>();

        for (List<String> row : data) {
            if (row.size() > Math.max(xAxis, Math.max(yAxis, groupByAxis))) {
                try {
                    double x = Double.parseDouble(row.get(xAxis).trim());
                    double y = Double.parseDouble(row.get(yAxis).trim());
                    String group = row.get(groupByAxis).trim();

                    XYSeries series = seriesMap.computeIfAbsent(group, k -> new XYSeries(k));
                    series.add(x, y);
                } catch (NumberFormatException ignored) {}
            }
        }

        for (XYSeries series : seriesMap.values()) {
            dataset.addSeries(series);
        }

        return dataset;
    }

    private boolean isColumnNumeric(List<List<String>> data, int columnIndex) {
        int numericCount = 0;
        int totalCount = 0;

        for (List<String> row : data) {
            if (row.size() > columnIndex) {
                String value = row.get(columnIndex).trim();
                if (!value.isEmpty()) {
                    totalCount++;
                    try {
                        Integer.parseInt(value);
                        numericCount++;
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        // Consideramos numérico si más del 70% de los valores lo son
        return totalCount > 0 && (numericCount * 1.0 / totalCount) > 0.7;
    }
}

