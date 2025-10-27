package uni.aed.TareaLab;

import uni.aed.TareaLab.*;

public class Laberinto {
    private int[][] lab;
    private int filas;
    private int columnas;

    public Laberinto(int[][] laberinto) {
        this.lab = laberinto;
        this.filas = laberinto.length;
        this.columnas = laberinto[0].length;
    }

    public boolean esValido(int f, int c) {
        return f >= 0 && f < filas && c >= 0 && c < columnas && lab[f][c] == 0;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
    
    public int[][] getMapa() {
    return lab;
}

    public void imprimir() {
        for (int[] fila : lab) {
            for (int celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}