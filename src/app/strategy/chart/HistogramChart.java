package app.strategy.chart;

import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import java.util.ArrayList;
import java.awt.Color;


public class HistogramChart extends Chart {
    public HistogramChart(String title, List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        super(title);
        createAndDisplayChart(title, headers, data, xAxis, yAxis);
    }

    public void createAndDisplayChart(String chartTitle, List<String> headers, List<List<String>> data, int xAxis, int yAxis) {
        HistogramDataset dataset = new HistogramDataset();

        List<Double> values = new ArrayList<>();
        for (List<String> row : data) {
            if (row.size() > yAxis) {
                try {
                    double value = Double.parseDouble(row.get(yAxis).trim());
                    values.add(value);
                } catch (NumberFormatException ignored) {}
            }
        }

        double[] valuesArray = values.stream().mapToDouble(Double::doubleValue).toArray();
        dataset.addSeries(headers.get(yAxis), valuesArray, 10); // 10 bins por defecto

        JFreeChart histogram = ChartFactory.createHistogram(
                chartTitle,
                headers.get(yAxis),
                "Frecuencia",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = histogram.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.getRenderer().setSeriesPaint(0, new Color(70, 130, 180));

        ChartPanel chartPanel = new ChartPanel(histogram);
        setContentPane(chartPanel);
    }
}

