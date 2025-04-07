/**
 * Rebeca Rodríguez Rodríguez (alu0101394763)
 * Universidad de La Laguna
 * Escuela de Doctorado y Estudios de Posgrado
 * Máster en Ingeniería Informática
 * Análisis de Datos Masivos
 * Práctica Visualización de Datos I
 */

package app.strategy.parser;

import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;

public class XLSXFileParser extends FileParser {
    // Constructor por defecto
    public XLSXFileParser() {
        this.columnNames = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    @Override
    public void Parse(ArrayList<String> lines) {
        try {
            // Suponemos que el archivo viene en una única línea codificada en Base64
            String base64 = String.join("", lines);
            byte[] bytes = Base64.getDecoder().decode(base64);

            try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();

                // Leer encabezados
                if (rowIterator.hasNext()) {
                    Row headerRow = rowIterator.next();
                    for (Cell cell : headerRow) {
                        columnNames.add(getCellValueAsString(cell));
                    }
                }

                // Leer filas
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    List<String> rowData = new ArrayList<>();
                    for (int i = 0; i < columnNames.size(); i++) {
                        Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        rowData.add(getCellValueAsString(cell));
                    }
                    data.add(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }
}

