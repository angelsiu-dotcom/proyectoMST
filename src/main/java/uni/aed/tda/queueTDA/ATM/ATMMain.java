/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.queueTDA.ATM;


import uni.aed.tda.queueTDA.LinkedQueueTDA;
import uni.aed.tda.queueTDA.QueueTDA;
import java.util.Random;
import java.util.Scanner;



public class ATMMain {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese minutos a simular (M): ");
        int M = sc.nextInt();
        System.out.print("Ingrese probabilidad de llegada de cliente (P, 0-1): ");
        double P = sc.nextDouble();
        System.out.print("Ingrese número de cajeros automáticos (N): ");
        int N = sc.nextInt();

        SimuladorATM simulador = new SimuladorATM(M, P, N);
        simulador.simular();

        System.out.println("Simulación finalizada:");
        System.out.println("Tiempo promedio de espera: " + simulador.tiempoPromedioEspera());
        System.out.println("Tiempo máximo de espera: " + simulador.tiempoMaximoEspera());
    }
}

