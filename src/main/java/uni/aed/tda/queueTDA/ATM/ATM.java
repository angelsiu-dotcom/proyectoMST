/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.queueTDA.ATM;
import uni.aed.tda.queueTDA.LinkedQueueTDA;
import uni.aed.tda.queueTDA.QueueTDA;
import java.util.Random;
import java.util.Scanner;

class ATM {
    private boolean disponible;
    private Cliente clienteActual;
    private QueueTDA<Cliente> cola;

    public ATM() {
        this.disponible = true;
        this.clienteActual = null;
        this.cola = new LinkedQueueTDA<>();
    }

    public boolean estaDisponible() { return disponible; }

    public void agregarClienteCola(Cliente c) {
        cola.enqueue(c);
    }

    public void procesarUnMinuto(int minutoActual) throws Exception {
        // Si hay cliente en uso
        if(clienteActual != null) {
            clienteActual.setTiempoEspera(clienteActual.getTiempoEspera() + 1); // aumentar tiempo de espera real
            int nuevoTiempo = clienteActual.getTiempoTransaccion() - 1;
            if(nuevoTiempo <= 0) {
                clienteActual = null;
                disponible = true;
            } else {
                clienteActual = new Cliente(clienteActual.getTiempoLlegada(), nuevoTiempo);
                disponible = false;
            }
        }

        // Si ATM está libre y hay clientes en cola
        if(disponible && !cola.isEmpty()) {
            clienteActual = cola.dequeue();
            clienteActual.setTiempoEspera(minutoActual - clienteActual.getTiempoLlegada());
            disponible = false;
        }
    }

    public QueueTDA<Cliente> getCola() { return cola; }
    public Cliente getClienteActual() { return clienteActual; }
}
