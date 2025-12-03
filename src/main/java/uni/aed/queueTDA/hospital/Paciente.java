/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.queueTDA.hospital;

import uni.aed.tda.queueTDA.LinkedQueueTDA;
import uni.aed.tda.queueTDA.QueueTDA;
import uni.aed.tda.stackTDA.LinkedStackTDA;
import uni.aed.tda.stackTDA.StackTDA;
import java.util.Random;
import java.util.Scanner;
public class Paciente implements Comparable<Paciente> {
    
    private final int id;
    private final int prioridad; // 1 = mayor prioridad(mas urgente)
    private final int tiempoAtencion;
    private final int tiempoLlegada;
    private int tiempoRestante;
    private int tiempoEspera = 0;


    public Paciente(int id, int prioridad, int tiempoAtencion, int tiempoLlegada) {
        this.id = id;
        this.prioridad = prioridad;
        this.tiempoAtencion = tiempoAtencion;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoRestante = tiempoAtencion;
    }

    public int getId() { return id; }
    public int getPrioridad() { return prioridad; }
    public int getTiempoAtencion() { return tiempoAtencion; }
    public int getTiempoRestante() { return tiempoRestante; }
    public int getTiempoLlegada() { return tiempoLlegada; }
    public int getTiempoEspera() { return tiempoEspera; }

    public void disminuirTiempo() { tiempoRestante--; }
    public void setTiempoEspera(int tiempoEspera) { this.tiempoEspera = tiempoEspera; }

    @Override
    public int compareTo(Paciente o) {
        // Menor prioridad numérica = más urgente
        int cmp = Integer.compare(this.prioridad, o.prioridad);
        if (cmp != 0) return cmp;
        return Integer.compare(this.tiempoLlegada, o.tiempoLlegada);
    }
    
    public Boolean atenderUnMinuto() {
        if(tiempoRestante<=0)
            throw new IllegalArgumentException("El paciente excedio su tiempo de atencion");      
        tiempoRestante--;
        return tiempoRestante<=0;   
    }
    
    public int calcularTiempoEspera(int tiempoActual) {
        if (tiempoActual < tiempoLlegada)
            throw new IllegalArgumentException("Tiempo actual no puede ser menor que tiempo de llegada");        
        return tiempoActual - tiempoLlegada;
    }


    @Override
    public String toString() {
        return String.format("%s [P:%d, TAt:%d, TLleg:%d, TEspera:%d]",
                id, prioridad, tiempoAtencion, tiempoLlegada, tiempoEspera);
    }
}

