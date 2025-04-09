package app.strategy.chart;

import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;

import java.util.*;
import java.awt.*;


public class HistogramChart extends Chart {
    public HistogramChart(String title, List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        super(title);
        createAndDisplayChart(title, headers, data, xAxis, yAxis);
    }

    @Override
    public void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int dummy, int yAxis) {
        SimpleHistogramDataset dataset = new SimpleHistogramDataset("Frecuencia");
        List<Double> values = new ArrayList<>();

        for (List<String> row : data) {
            if (row.size() > yAxis) {
                try {
                    double value = Double.parseDouble(row.get(yAxis).trim());
                    values.add(value);
                } catch (NumberFormatException ignored) {}
            }
        }

        if (values.isEmpty()) {
            System.out.println("No hay datos numéricos válidos para el histograma.");
            return;
        }

        // Obtener valores únicos ordenados (para tratar datos discretos como meses)
        Set<Double> uniqueValues = new TreeSet<>(values);

        for (double val : uniqueValues) {
            // Cada bin va de (val - 0.5) a (val + 0.5) → centra la barra en el número exacto
            dataset.addBin(new SimpleHistogramBin(val - 0.5, val + 0.5, true, false));
        }

        // Añadir los valores
        for (double val : values) {
            dataset.addObservation(val);
        }

        JFreeChart histogram = ChartFactory.createHistogram(
                chartTitle,
                headers.get(yAxis),
                "frequency",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        XYPlot plot = histogram.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.getRenderer().setSeriesPaint(0, new Color(70, 130, 180));

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);
        renderer.setMargin(0.025);

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel chartPanel = new ChartPanel(histogram);
        chartPanel.setPreferredSize(new Dimension(1000, 600));
        setContentPane(chartPanel);
    }

}

