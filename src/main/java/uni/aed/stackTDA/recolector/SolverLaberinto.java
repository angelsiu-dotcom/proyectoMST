/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.stackTDA.recolector;

import uni.aed.tda.queueTDA.ArrayQueueTDA; 
import uni.aed.tda.stackTDA.ArrayStackTDA; 
import uni.aed.tda.stackTDA.StackTDA;
public class SolverLaberinto {

    public static void main(String[] args) {
        char[][] mapa = {
            {'.','.', '.', '#','.'},
            {'.', '#', 'P','.','.'},
            {'.','.','.', '#', 'P'},
            {'#', '.', '.', '.', '.'},
            {'.', '.', '#', '.', 'B'}
        };

        Laberinto lab = new Laberinto(mapa);
        System.out.println("Laberinto:");
        lab.imprimir();

        if (ResolverLaberinto(lab, 0, 0, 4, 4))
            System.out.println(" Se encontró una salida!");
        else
            System.out.println(" No hay camino posible.");
    }

    public static boolean ResolverLaberinto(Laberinto lab, int inicioFila, int inicioCol, int finFila, int finCol) {
        StackTDA<CeldaDelLaberinto> pila = new ArrayStackTDA<>();
        pila.push(new CeldaDelLaberinto(inicioFila, inicioCol));
        char[][] mapa = lab.getMapa();
        while (!pila.isEmpty()) {
            CeldaDelLaberinto actual = pila.peek();

            if (actual.getFila() == finFila && actual.getColumna() == finCol) {
                System.out.println("Camino encontrado: ");
                StackTDA<CeldaDelLaberinto> camino = new ArrayStackTDA<>();
            while (!pila.isEmpty()) {
                camino.push(pila.pop());
            }

            // Imprimir desde el inicio hasta la meta
            while (!camino.isEmpty()) {
                System.out.println(camino.pop());
            }

            return true;
            }

            int f = actual.getFila();
            int c = actual.getColumna();
            lab.getFilas();

            // Marcar celda como visitada
            mapa[f][c] = 2;

            // Explorar vecinos
            if (lab.esValido(f - 1, c)) pila.push(new CeldaDelLaberinto(f - 1, c));
            else if (lab.esValido(f, c + 1)) pila.push(new CeldaDelLaberinto(f, c + 1));
            else if (lab.esValido(f + 1, c)) pila.push(new CeldaDelLaberinto(f + 1, c));
            else if (lab.esValido(f, c - 1)) pila.push(new CeldaDelLaberinto(f, c - 1));
            else pila.pop();
        }

        return false;
    }
}