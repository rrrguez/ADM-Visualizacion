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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.plot.DefaultDrawingSupplier;


import java.awt.Color;

import java.awt.*;
import java.util.List;

public class BarChart extends Chart {
    // Constructor
    public BarChart(String title, List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        super(title);
        createAndDisplayChart(title, headers, data, xAxis, yAxis);
    }

    public BarChart(String title, List<String> headers, List<List<String>> data, int xAxis, int yAxis, int groupByAxis) {
        super(title);
        createAndDisplayChart(title, headers, data, xAxis, yAxis, groupByAxis);
    }

    public void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        DefaultCategoryDataset dataset = createDataset(headers, data, xAxis, yAxis);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                headers.get(xAxis),
                headers.get(yAxis),
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(70, 130, 180));
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setShadowVisible(false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int xAxis, int yAxis, int groupByAxis) {
        DefaultCategoryDataset dataset = createDataset(headers, data, xAxis, yAxis, groupByAxis);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                headers.get(xAxis),
                headers.get(yAxis),
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDrawingSupplier(getDefaultDrawingSupplier());

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setShadowVisible(false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private static DefaultDrawingSupplier getDefaultDrawingSupplier() {
        Paint[] customPaints = new Paint[] {
                new Color(70, 130, 180),
                new Color(100, 149, 237),
                new Color(135, 206, 235),
                new Color(176, 196, 222),
                new Color(30, 144, 255)
        };

        DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(
                customPaints,
                DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE
        );
        return drawingSupplier;
    }
}