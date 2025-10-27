/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.EditorTexto;
import uni.aed.tda.listTDA.ListTDA;
import uni.aed.tda.linkedlistTDA.LinkedListTDA;
import java.io.*;

public class Editor {



    private ListTDA<Linea> lineas;
    private int lineaActual;
    private String archivo;

    public Editor(String archivo) {
        this.archivo = archivo;
        this.lineas = new LinkedListTDA<>();
        this.lineaActual = 1;
    }

    //Inserta una línea antes de la línea n 
    public void insertar(String texto, int n) {
        Linea nueva = new Linea(texto);
        if (n <= 0 || n > lineas.size()) {
            lineas.add(lineaActual - 1, nueva);
        } else {
            lineas.add(n - 1, nueva);
        }
    }

    // Elimina una o varias líneas */
    public void eliminar(int n, int m) {
        if (n == 0 && m == 0) {
            // eliminar línea actual
            lineas.delete(lineaActual - 1);
        } else if (m == 0) {
            // eliminar solo línea n
            lineas.delete(n - 1);
        } else {
            // eliminar desde n hasta m
            for (int i = n; i <= m && n <= lineas.size(); i++) {
                lineas.delete(n - 1); // cada vez se borra la posición n
            }
        }
        if (lineaActual > lineas.size()) lineaActual = lineas.size();
    }

    // Lista líneas 
    public void listar(int n, int m) {
        if (lineas.size() == 0) {
            System.out.println("(Archivo vacío)");
            return;
        }

        if (n == 0 && m == 0) {
            for (int i = 0; i < lineas.size(); i++) {
                System.out.println((i + 1) + "> " + lineas.get(i));
            }
        } else if (m == 0) {
            System.out.println(n + "> " + lineas.get(n - 1));
        } else {
            for (int i = n - 1; i < m && i < lineas.size(); i++) {
                System.out.println((i + 1) + "> " + lineas.get(i));
            }
        }
    }


    public void addFinal(String texto) {
        lineas.add(new Linea(texto));
    }

    
    public void guardar() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (int i = 0; i < lineas.size(); i++) {
                bw.write(lineas.get(i).toString());
                bw.newLine();
            }
            System.out.println("Archivo guardado en " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }
}

