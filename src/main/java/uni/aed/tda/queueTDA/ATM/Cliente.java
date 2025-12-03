/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.queueTDA.ATM;

public class Cliente {
    private final int tiempoLlegada;
    private final int tiempoTransaccion;
    private int tiempoEspera;

    public Cliente(int tiempoLlegada, int tiempoTransaccion) {
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoTransaccion = tiempoTransaccion;
        this.tiempoEspera = 0;
    }

    public int getTiempoLlegada() { return tiempoLlegada; }
    public int getTiempoTransaccion() { return tiempoTransaccion; }
    public int getTiempoEspera() { return tiempoEspera; }
    public void setTiempoEspera(int tiempoEspera) { this.tiempoEspera = tiempoEspera; }

    @Override
    public String toString() {
        return String.format("Cliente[TLlegada:%d, TTransaccion:%d, TEspera:%d]",
                tiempoLlegada, tiempoTransaccion, tiempoEspera);
    }
}
