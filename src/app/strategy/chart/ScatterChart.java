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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.Color;


import java.util.List;

public class ScatterChart extends Chart {
    public ScatterChart(String title) {
        super(title);
    }

    public ScatterChart(String title, List<String> headers, List<List<String>> data, int xAxis, int yAxis, int groupByAxis) {
        super(title);
        createAndDisplayChart(title, headers, data, xAxis, yAxis, groupByAxis);
    }

    public void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        XYDataset dataset = super.createXYDataset(headers, data, xAxis, yAxis);
        JFreeChart scatterChart = ChartFactory.createScatterPlot(
                chartTitle,
                headers.get(xAxis),
                headers.get(yAxis),
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = scatterChart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE); // Fondo blanco

        /**
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setDefaultPaint(new Color(70, 130, 180));
*/
        ChartPanel chartPanel = new ChartPanel(scatterChart);
        setContentPane(chartPanel);
    }

    public void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int xAxis, int yAxis, int groupByAxis) {
        XYDataset dataset = createXYDataset(headers, data, xAxis, yAxis, groupByAxis);
        JFreeChart scatterChart = ChartFactory.createScatterPlot(
                chartTitle,
                headers.get(xAxis),
                headers.get(yAxis),
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = scatterChart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE); // Fondo blanco


        ChartPanel chartPanel = new ChartPanel(scatterChart);
        setContentPane(chartPanel);
    }
}
