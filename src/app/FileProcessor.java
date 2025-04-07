/**
 * Rebeca Rodríguez Rodríguez (alu0101394763)
 * Universidad de La Laguna
 * Escuela de Doctorado y Estudios de Posgrado
 * Máster en Ingeniería Informática
 * Análisis de Datos Masivos
 * Práctica Visualización de Datos I
 */

package app;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FileProcessor {
    protected String url;
    protected String type;

    public FileProcessor(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public ArrayList<String> ProcessFile() throws IOException {
        // Array que contiene las líneas del fichero
        ArrayList<String> lines = new ArrayList<>();

        if (type.equals("url")) {
            lines = ProcessURLFile();
        } else if (type.equals("local")) {
            lines = ProcessLocalFile();
        }

        return lines;
    }

    public static byte[] downloadBinaryFile(String urlString) throws IOException {
        URL url = new URL(urlString);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (InputStream is = url.openStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        }
        return baos.toByteArray();
    }

    private ArrayList<String> ProcessURLFile() throws IOException {
        // Array que contiene las líneas del fichero
        ArrayList<String> lines = new ArrayList<>();
        // URL
        URL file_url = new URL(url);
        // Nos conectamos a la url proporcionada para descargar el fichero
        HttpURLConnection connection = (HttpURLConnection) file_url.openConnection();
        // Comprobamos que la URL es válida
        try (BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())))) {
            System.out.print("Downloading from " + url + "...\n");
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al procesar la URL proporcionada: " + HttpURLConnection.class + ")\n", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            connection.disconnect();
        }
        return lines;
    }

    private ArrayList<String> ProcessLocalFile() throws IOException {
        File localFile = new File(url);
        ArrayList<String> lines = new ArrayList<>();

        if (localFile.exists() && localFile.isFile()) {
            // Leer el archivo local
            FileReader fileReader = new FileReader(localFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            // Leer cada línea del archivo
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();
        } else {
            JOptionPane.showMessageDialog(null, "El archivo local no existe o es inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return lines;
    }
}
