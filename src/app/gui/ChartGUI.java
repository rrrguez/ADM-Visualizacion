/**
 * Rebeca Rodríguez Rodríguez (alu0101394763)
 * Universidad de La Laguna
 * Escuela de Doctorado y Estudios de Posgrado
 * Máster en Ingeniería Informática
 * Análisis de Datos Masivos
 * Práctica Visualización de Datos I
 */

package app.gui;

import app.FileProcessor;
import app.strategy.chart.*;
import org.jfree.ui.RefineryUtilities;
import app.strategy.parser.CSVFileParser;
import app.strategy.parser.FileParser;
import app.strategy.parser.XLSXFileParser;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

public class ChartGUI extends JFrame {
    // Declaración de los campos de input del usuario
    private JRadioButton url, local;
    private JTextField fileSource;
    private JComboBox<String> fileTypeComboBox;
    private JComboBox<String> chartTypeComboBox;
    private JComboBox<String> xAxisField;
    private JComboBox<String> yAxisField;
    private JComboBox<String> groupByAxisComboBox;

    private JButton processFileButton;
    private JButton generateChartButton;
    private FileParser data = null;
    private String fileName;

    // Constructor
    public ChartGUI() {
        // Ventanita principal
        setTitle("ADM - Práctica Visualización de Datos I");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Se crean los componentes declarados previamente
        url = new JRadioButton("URL");
        local = new JRadioButton("Local");
        fileSource = new JTextField();
        JLabel fileTypeLabel = new JLabel("Tipo de fichero:");
        String[] fileTypeOptions = {"CSV", "XLSX"};
        fileTypeComboBox = new JComboBox<>(fileTypeOptions);
        JLabel chartTypeLabel = new JLabel("Tipo de gráfico");
        String[] chartTypeOptions = {"Gráfico de barras", "Gráfico de líneas", "Histograma", "Nube de puntos"};
        chartTypeComboBox = new JComboBox<>(chartTypeOptions);
        JLabel xAxisLabel = new JLabel("Eje X");
        xAxisField = new JComboBox<>();
        JLabel yAxisLabel = new JLabel("Eje Y");
        yAxisField = new JComboBox<>();
        JLabel groupAxisLabel = new JLabel("Agrupar por...");
        groupByAxisComboBox = new JComboBox<>();

        processFileButton = new JButton("Procesar fichero de datos");
        generateChartButton = new JButton("Generar gráfico");

        setLayout(new GridLayout(4, 1));

        ButtonGroup group = new ButtonGroup();
        group.add(url);
        group.add(local);

        JPanel fileSourcePanel = new JPanel();
        fileSourcePanel.setLayout(new FlowLayout());
        fileSourcePanel.add(local);
        fileSourcePanel.add(url);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(4, 2));

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new GridLayout(2, 2));
        filePanel.add(fileSourcePanel);
        filePanel.add(fileSource);
        filePanel.add(fileTypeLabel);
        filePanel.add(fileTypeComboBox);
        add(filePanel);

        JPanel processFileButtonPanel = new JPanel();
        processFileButtonPanel.add(processFileButton);
        add(processFileButtonPanel);

        processFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(chartPanel);
                remove(generateChartButton);

                try {
                    String fileSourceText = fileSource.getText();
                    String fileType = (String) fileTypeComboBox.getSelectedItem();

                    // Verificar qué JRadioButton está seleccionado
                    if (url.isSelected()) {
                        URL url = new URL(fileSourceText);
                        String path = url.getPath();
                        String[] pathParts = path.split("/");
                        fileName = pathParts[pathParts.length - 1];

                        // Descargar el archivo y almacenarlo en fileLines
                        FileProcessor file = new FileProcessor(fileSourceText, "url");
                        ArrayList<String> fileLines = file.ProcessFile();

                        // Procesar el archivo
                        if (fileType.equals("CSV")) {
                            data = new CSVFileParser();
                        } else if (fileType.equals("XLSX")) {
                            // Descargar archivo binario
                            byte[] binaryContent = FileProcessor.downloadBinaryFile(fileSourceText);
                            String base64 = Base64.getEncoder().encodeToString(binaryContent);
                            fileLines = new ArrayList<>();
                            fileLines.add(base64);

                            data = new XLSXFileParser();
                        }
                        assert data != null;
                        data.Parse(fileLines);

                        updateAxisComboBoxes(data.getColumnNames());

                        chartPanel.add(chartTypeLabel);
                        chartPanel.add(chartTypeComboBox);
                        chartPanel.add(xAxisLabel);
                        chartPanel.add(xAxisField);
                        chartPanel.add(yAxisLabel);
                        chartPanel.add(yAxisField);
                        chartPanel.add(groupAxisLabel);
                        chartPanel.add(groupByAxisComboBox);

                        add(chartPanel);

                        JPanel generateChartButtonPanel = new JPanel();
                        generateChartButtonPanel.add(generateChartButton);
                        add(generateChartButtonPanel);

                        revalidate();
                        repaint();

                    } else if (local.isSelected()) {
                        FileProcessor file = new FileProcessor(fileSourceText, "local");
                        ArrayList<String> lines = file.ProcessFile();

                        // Procesar el archivo
                        if (fileType.equals("CSV")) {
                            data = new CSVFileParser();
                        } else if (fileType.equals("XLSX")) {
                            File localFile = new File(fileSourceText);
                            byte[] fileBytes = Files.readAllBytes(localFile.toPath());
                            String base64 = Base64.getEncoder().encodeToString(fileBytes);
                            lines = new ArrayList<>();
                            lines.add(base64);

                            data = new XLSXFileParser();
                        }
                        assert data != null;
                        data.Parse(lines);

                        updateAxisComboBoxes(data.getColumnNames());

                        chartPanel.add(chartTypeLabel);
                        chartPanel.add(chartTypeComboBox);
                        chartPanel.add(xAxisLabel);
                        chartPanel.add(xAxisField);
                        chartPanel.add(yAxisLabel);
                        chartPanel.add(yAxisField);
                        chartPanel.add(groupAxisLabel);
                        chartPanel.add(groupByAxisComboBox);

                        add(chartPanel);

                        JPanel generateChartButtonPanel = new JPanel();
                        generateChartButtonPanel.add(generateChartButton);
                        add(generateChartButtonPanel);

                        revalidate();
                        repaint();

                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor seleccione una fuente de archivo (URL o Local)", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        generateChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chartType = (String) chartTypeComboBox.getSelectedItem();
                int xAxis = xAxisField.getSelectedIndex();
                int yAxis = yAxisField.getSelectedIndex();

                assert chartType != null;

                if(groupByAxisComboBox.getSelectedItem().equals("None")) {
                    createChart(data, fileName, chartType, xAxis, yAxis);
                } else {
                    createChart(data, fileName, chartType, xAxis, yAxis, groupByAxisComboBox.getSelectedIndex() -1);
                }
            }
        });
    }

    private void updateAxisComboBoxes(ArrayList<String> columnNames) {
        // Limpiar los ComboBox antes de añadir nuevos elementos
        xAxisField.removeAllItems();
        yAxisField.removeAllItems();
        groupByAxisComboBox.removeAllItems();
        groupByAxisComboBox.addItem("None");

        // Añadir los nombres de las columnas a los ComboBox
        for (String columnName : columnNames) {
            xAxisField.addItem(columnName);
            yAxisField.addItem(columnName);
            groupByAxisComboBox.addItem(columnName);
        }
    }

    // Método auxiliar para generar la gráfica (por ejemplo, gráfico de barras, nube de puntos, etc.)
    private void createChart(FileParser data, String fileName, String chartType, int xAxis, int yAxis) {
        Chart chart = null;
        if (chartType.equals("Gráfico de barras")) {
            chart = new BarChart(fileName, data.getColumnNames(), data.getData(), xAxis, yAxis);

        } else if (chartType.equals("Gráfico de líneas")) {
            chart = new LineChart(fileName, data.getColumnNames(), data.getData(), xAxis, yAxis);
        } else if (chartType.equals("Histograma")) {
            chart = new HistogramChart(fileName, data.getColumnNames(), data.getData(), xAxis, yAxis);
        }
        assert chart != null;
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    private void createChart(FileParser data, String fileName, String chartType, int xAxis, int yAxis, int groupByAxis) {
        Chart chart = null;
        if (chartType.equals("Gráfico de barras")) {
            chart = new BarChart(fileName, data.getColumnNames(), data.getData(), xAxis, yAxis, groupByAxis);
        } else if (chartType.equals("Gráfico de líneas")) {
            chart = new LineChart(fileName, data.getColumnNames(), data.getData(), xAxis, yAxis, groupByAxis);
        } else if (chartType.equals("Nube de puntos")) {
            chart = new ScatterChart(fileName, data.getColumnNames(), data.getData(), xAxis, yAxis, groupByAxis);
        } else if (chartType.equals("Histograma")) {
            chart = new HistogramChart(fileName, data.getColumnNames(), data.getData(), xAxis, yAxis);
        }
        assert chart != null;
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
