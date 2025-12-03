/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.queueTDA.ATM;
import uni.aed.tda.queueTDA.LinkedQueueTDA;
import uni.aed.tda.queueTDA.QueueTDA;
import java.util.Random;
import java.util.Scanner;

class SimuladorATM {
    private final ATM[] cajeros;
    private final int M; // minutos a simular
    private final double P; // probabilidad de llegada de cliente
    private final Random rand;
    private int clientesAtendidos;
    private int tiempoEsperaTotal;
    private int tiempoEsperaMax;

    public SimuladorATM(int M, double P, int N) {
        this.M = M;
        this.P = P;
        this.cajeros = new ATM[N];
        for(int i=0; i<N; i++) cajeros[i] = new ATM();
        this.rand = new Random();
        this.clientesAtendidos = 0;
        this.tiempoEsperaTotal = 0;
        this.tiempoEsperaMax = 0;
    }

    private Cliente generarClienteAleatorio(int minutoActual) {
        int tiempoTransaccion = rand.nextInt(5) + 1; // 1-5 minutos
        return new Cliente(minutoActual, tiempoTransaccion);
    }

    private int encontrarColaMasCorta() {
        int idx = 0;
        int min = cajeros[0].getCola().size();
        for(int i=1; i<cajeros.length; i++) {
            if(cajeros[i].getCola().size() < min) {
                min = cajeros[i].getCola().size();
                idx = i;
            }
        }
        return idx;
    }

    public void simular() throws Exception {
        for(int min=0; min<M; min++) {
            // Llegada de cliente
            if(rand.nextDouble() < P) {
                Cliente c = generarClienteAleatorio(min);
                int idxCola = encontrarColaMasCorta();
                cajeros[idxCola].agregarClienteCola(c);
            }

            // Procesar cada cajero
            for(ATM atm : cajeros) {
                atm.procesarUnMinuto(min);
                Cliente cli = atm.getClienteActual();
                if(cli != null) {
                    clientesAtendidos++;
                    tiempoEsperaTotal += cli.getTiempoEspera();
                    if(cli.getTiempoEspera() > tiempoEsperaMax)
                        tiempoEsperaMax = cli.getTiempoEspera();
                }
            }
        }

        // Procesar clientes restantes hasta vaciar colas
        boolean colasVacias;
        do {
            colasVacias = true;
            for(ATM atm : cajeros) {
                if(!atm.getCola().isEmpty() || atm.getClienteActual() != null) {
                    atm.procesarUnMinuto(M); // usar M como referencia para tiempoActual
                    colasVacias = false;
                }
            }
        } while(!colasVacias);
    }

    public double tiempoPromedioEspera() {
        return clientesAtendidos == 0 ? 0 : (double)tiempoEsperaTotal / clientesAtendidos;
    }

    public int tiempoMaximoEspera() { return tiempoEsperaMax; }
}
