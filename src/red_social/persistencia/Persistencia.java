/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Persistencia
 * CLASE: Persistencia
 * AUTOR: Jhon Sebastian Avendaño Gutierrez
 * FECHA:
 */

package red_social.persistencia;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Clase base de persistencia para leer y escribir archivos .txt
public class Persistencia {

    private String archivo;

    // Constructor
    public Persistencia(String archivo) {
        this.archivo = archivo;
    }

    // Getter
    public String getArchivo() { return this.archivo; }

    // Guarda una lista de lineas en el archivo (sobreescribe)
    public void guardar(ArrayList<String> lineas) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.archivo));
            for (int i = 0; i < lineas.size(); i++) {
                writer.write(lineas.get(i));
                writer.newLine();
            }
            writer.close();
            System.out.println("Datos guardados en: " + this.archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar en " + this.archivo + ": " + e.getMessage());
        }
    }

    // Carga todas las lineas del archivo
    public ArrayList<String> cargar() {
        ArrayList<String> lineas = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.archivo));
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.isEmpty()) {
                    lineas.add(linea);
                }
            }
            reader.close();
            System.out.println("Datos cargados desde: " + this.archivo);
        } catch (IOException e) {
            System.out.println("Archivo " + this.archivo + " no encontrado. Se creara al guardar.");
        }
        return lineas;
    }
}
