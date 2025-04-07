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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.awt.Color;


import java.awt.*;
import java.util.List;

public class LineChart extends Chart {
    // Constructor
    public LineChart(String title, List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        super(title);
        createAndDisplayChart(title, headers, data, xAxis, yAxis);
    }

    public LineChart(String title, List<String> headers, List<List<String>> data, int xAxis, int yAxis, int groupByAxis) {
        super(title);
        createAndDisplayChart(title, headers, data, xAxis, yAxis, groupByAxis);
    }

    // Método para crear y mostrar la gráfica
    public void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        DefaultCategoryDataset dataset = createDataset(headers, data, xAxis, yAxis);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                headers.get(xAxis),
                headers.get(yAxis),
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        CategoryPlot plot = lineChart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(70, 130, 180));
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.5f));

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(1000, 600));
        setContentPane(chartPanel);
    }

    public void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int xAxis, int yAxis, int groupByAxis) {
        DefaultCategoryDataset dataset = createDataset(headers, data, xAxis, yAxis, groupByAxis);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                headers.get(xAxis),
                headers.get(yAxis),
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        Color[] colors = {
                new Color(70, 130, 180),
                new Color(100, 149, 237),
                new Color(135, 206, 235),
                new Color(176, 196, 222)
        };

        for (int i = 0; i < dataset.getRowCount(); i++) {
            renderer.setSeriesPaint(i, colors[i % colors.length]);
            renderer.setSeriesStroke(i, new BasicStroke(2.5f));
        }

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(1000, 600));
        setContentPane(chartPanel);
    }
}


