/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.stackTDA.recolector;

public class Laberinto {
    private char[][] lab;
    private int filas;
    private int columnas;

    public Laberinto(char[][] laberinto) {
        this.lab = laberinto;
        this.filas = laberinto.length;
        this.columnas = laberinto[0].length;
    }

    public boolean esValido(int f, int c) {
        return f >= 0 && f < filas && c >= 0 && c < columnas && lab[f][c] == '*'||lab[f][c]=='P'||lab[f][c]=='B';
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
    
    public char[][] getMapa() {
    return lab;
}

    public void imprimir() {
        for (char[] fila : lab) {
            for (char celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}