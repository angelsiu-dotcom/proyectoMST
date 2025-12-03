/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.queueTDA.hospital;

import java.util.Random;
import uni.aed.tda.arraylistTDA.ArrayListTDA;
import uni.aed.tda.listTDA.ListTDA;
import uni.aed.tda.queueTDA.ArrayQueueTDA;
import uni.aed.tda.queueTDA.PriorityQueueTDA;
import uni.aed.tda.queueTDA.QueueTDA;
import uni.aed.tda.queueTDA.simulador.Estadistica;
import uni.aed.tda.queueTDA.simulador.Trabajo;

public class SimuladorEmergencias {
public static final int TAMANIO_INICIAL_COLA=10;
    //Definimos constantes para la simulacion
    public static final int MIN_PRIORIDAD=1;
    public static final int MAX_PRIORIDAD=5;
    public static final int MIN_TIEMPO_EJECUCION=1;
    public static final int MAX_TIEMPO_EJECUCION=10;
    public static final String SEPARADOR="\n";
    public static final String TITULO_REPORTE="---VISUALIZACION DE AGENDA DE PACIENTE---";
    
    private final QueueTDA<Paciente> colaEspera;
    private final QueueTDA<Paciente> pacientesEjecutando;
    private final ListTDA<Estadistica> estadisticas;
    private final Random generador;
    private int tiempoSimulacion;
    private int tiempoSimulacionExtra=0;
    private int maxPacientesSimultaneos;
    private int contadorPacientes;
    private boolean simulacionActiva;
    StringBuilder reporte;

    public SimuladorEmergencias() {
        this.colaEspera =new PriorityQueueTDA<>(TAMANIO_INICIAL_COLA);
        this.pacientesEjecutando= new ArrayQueueTDA<>(TAMANIO_INICIAL_COLA);
        this.generador=new Random();
        this.contadorPacientes=0;
        this.simulacionActiva=false;
        this.reporte= new StringBuilder();
        
        this.estadisticas= new ArrayListTDA<>(MAX_PRIORIDAD);
        for(int i=MIN_PRIORIDAD;i<=MAX_PRIORIDAD;i++)
            this.estadisticas.add(i-1, new Estadistica(i));
        this.reporte.append(TITULO_REPORTE+SEPARADOR);
    }
    
    public void cargarAgenda(int minutos,int maxTrabajos){
        if(minutos<=0 || maxTrabajos<=0)
            throw new IllegalArgumentException("Los parametros deben ser positivos");
        //Reiniciar estado de la simulacion
        reiniciarSimulacion();
        this.tiempoSimulacion=minutos;              //M
        this.maxPacientesSimultaneos=maxTrabajos;    //N
        this.simulacionActiva=true;
        //Ejecutar la simulacion minuto a minuto
        for(int minutoActual=1;minutoActual<=tiempoSimulacion;minutoActual++){
            procesarMinuto(minutoActual);
            setReporte();
        }
        //procesar trabajos restantes en la cola, al final de la simulacion
        procesarPacientesRestantes();
        this.reporte.append(SEPARADOR+"Simulacion completada: ")
                .append(minutos).append(" minutos,")
                .append(maxTrabajos)
                .append(" pacientes simultaneos maximo"+SEPARADOR);        
    }
    //Procesamos los eventos de un minuto especifico
    private void procesarMinuto(int minutoActual){
        //1.- Llega un nuevo paciente cada minuto
        generarNuevoPaciente(minutoActual);
        //2.- Ejecutar pacientes actualmente en proceso
        ejecutarPacientesActivos(minutoActual);
        //3.- Asignar nuevos pacientes si hay capacidad disponible
        asignarNuevosPacientes();
    }
    // Generamos un nuevo trabajo aleatorio que llega en el minuto actual
    private void generarNuevoPaciente(int minutoLlegada) {
        // Generar prioridad(urgencia) aleatoria (1-5)
        int prioridad = generador.nextInt(MAX_PRIORIDAD - MIN_PRIORIDAD + 1) + MIN_PRIORIDAD;        
        
        // Generar tiempo de atencion aleatorio (1-10 minutos)
        int tiempoEjecucion = generador.nextInt(MAX_TIEMPO_EJECUCION - MIN_TIEMPO_EJECUCION + 1) + MIN_TIEMPO_EJECUCION;        
        
        // Crear y agregar el nuevo paciente a la cola de espera
        Paciente nuevoPaciente = new Paciente(contadorPacientes++, prioridad, 
                                          tiempoEjecucion, minutoLlegada);
        colaEspera.enqueue(nuevoPaciente);                
    }
    
    // Ejectuamos todos los pacientes actualmente en proceso por un minuto
    private void ejecutarPacientesActivos(int minutoActual) {
        ArrayQueueTDA<Paciente> pacientesTemporales = new ArrayQueueTDA<>(TAMANIO_INICIAL_COLA);
        
        // Procesar cada trabajo en ejecucion
        while (!pacientesEjecutando.isEmpty()) {
            Paciente paciente = pacientesEjecutando.dequeue();
            boolean completado = paciente.atenderUnMinuto();
            
            if (completado) {
                // Trabajo completado: registrar estadisticas
                int tiempoEspera = paciente.calcularTiempoEspera(minutoActual);                
                estadisticas.get(paciente.getPrioridad()-1).registrarTiempoEspera(tiempoEspera);
            } else
                // Trabajo no completado: mantener en ejecución
                pacientesTemporales.enqueue(paciente);            
        }
        
        // Restaurar trabajos no completados
        while (!pacientesTemporales.isEmpty()) {
            pacientesEjecutando.enqueue(pacientesTemporales.dequeue());
        }
    }
    
    // Asignamos nuevos trabajos de la cola de espera si hay capacidad disponible
    private void asignarNuevosPacientes() {
        // Mientras haya capacidad y trabajos en espera
        while (pacientesEjecutando.size() < maxPacientesSimultaneos && !colaEspera.isEmpty()) {
            Paciente paciente = colaEspera.dequeue();
            pacientesEjecutando.enqueue(paciente);
        }
    }
    
    // Procesamos todos los trabajos restantes en la cola al final de la simulación
    private void procesarPacientesRestantes() {
        //int minutoExtra = tiempoSimulacion + 1;
        tiempoSimulacionExtra= tiempoSimulacion + 1;
        
        // Continuar procesando hasta que no queden trabajos
        while (!colaEspera.isEmpty() || !pacientesEjecutando.isEmpty()) {
            // Ejecutar trabajos activos
            ejecutarPacientesActivos(tiempoSimulacionExtra);
            
            // Asignar nuevos trabajos si hay capacidad
            asignarNuevosPacientes();
            setTiempoSimulacionExtra(tiempoSimulacionExtra);
            setReporte(); 
            tiempoSimulacionExtra++;            
        }
    }

    public void setTiempoSimulacionExtra(int tiempoSimulacionExtra) {
        this.tiempoSimulacionExtra = tiempoSimulacionExtra;
    }

    public int getTiempoSimulacionExtra() {
        return tiempoSimulacionExtra;
    }
    
    
    // Obtenemos el tiempo de espera promedio de todos los trabajos procesados
    public double obtenerTiempoEsperaPromedio() {
        if (!simulacionActiva) {
            return 0.0;
        }
        
        int totalTrabajos = 0;
        int tiempoTotal = 0;
        
        for (int i = MIN_PRIORIDAD; i <= MAX_PRIORIDAD; i++) {
            Estadistica stats = estadisticas.get(i-1);
            totalTrabajos += stats.getTotalTrabajos();
            tiempoTotal += stats.getTiempoEsperaTotal();
        }        
        
        return totalTrabajos > 0 ? (double) tiempoTotal / totalTrabajos : 0.0;
    }

    public ListTDA<Estadistica> getEstadistica() {
        return estadisticas;
    }
    
    // Eliminamos la agenda de trabajo actual y reinicia el simulador
    public void eliminarAgenda() {
        reiniciarSimulacion();
        System.out.println("Agenda de PACIENTES eliminada y simulador reiniciado");
    }
    
    // Reiniciamos el estado completo del simulador
    private void reiniciarSimulacion() {
        // Limpiar colas
        colaEspera.clear();
        pacientesEjecutando.clear();
        
        // Reiniciar estadisticas                
        for (int i = MIN_PRIORIDAD; i <= MAX_PRIORIDAD; i++)
            estadisticas.get(i-1).reiniciar();        
        
        // Reiniciar contadores
        contadorPacientes = 1;
        simulacionActiva = false;
        tiempoSimulacion = 0;
        maxPacientesSimultaneos = 0;
        
        tiempoSimulacionExtra = 0;
        reporte.setLength(0);        
    }
    
    // Visualizamos el estado actual de la agenda de trabajo
    public String visualizarAgenda() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ESTADO ACTUAL DE LA AGENDA DE PACIENTES ---\n");
        
        if (!simulacionActiva) {
            sb.append("No hay simulación activa. Cargue una agenda primero.\n");
            return sb.toString();
        }
        
        // Información general
        sb.append(String.format("Simulación: %d minutos, maximo %d pacientes simultaneos\n", 
                               tiempoSimulacion, maxPacientesSimultaneos));
        sb.append(String.format("Tiempo de espera promedio general: %.2f minutos\n\n", 
                               obtenerTiempoEsperaPromedio()));
        
        // Estadísticas por prioridad
        sb.append("ESTADISTICAS POR NIVEL DE PRIORIDAD:\n");
        for (int i = MIN_PRIORIDAD; i <= MAX_PRIORIDAD; i++) {
            sb.append(estadisticas.get(i-1).toString()).append("\n");
        }
        
        // Estado de las colas
        sb.append(String.format("\nPACIENTES EN ESPERA: %d\n", colaEspera.size()));
        sb.append(String.format("PACIENTES ATENDIENDOSE: %d\n", pacientesEjecutando.size()));
        
        return sb.toString();
    }    

    public int getTiempoSimulacion() {
        return tiempoSimulacion;
    }

    public void getEstadisticaPorPrioridad() {     
        reporte.append(SEPARADOR).append("Traza Estadistica Pacientes atendidos o en atencion \n(No incluye los Pacientes en Espera): ").append(SEPARADOR);
        for (int i = 0; i < estadisticas.size(); i++)
            reporte.append(estadisticas.get(i).toString()).append(SEPARADOR);                            
    }
    
    
    private void setReporte(){        
        reporte.append("\nPacientes COLA DE ESPERA:\n").append(colaEspera.toString(SEPARADOR));
        reporte.append("\nPACIENTES EN ATENCION:\n").append(pacientesEjecutando.toString(SEPARADOR));
        if (getTiempoSimulacionExtra()> getTiempoSimulacion()){
            reporte.append("\nTiempo de Simuluacion Extra:\n").append(getTiempoSimulacionExtra());
            getEstadisticaPorPrioridad();
        }
    }

    @Override
    public String toString() {
        return reporte.toString();
    }
    
    // Verificamos si hay una simulacion activa
    public boolean isSimulacionActiva() {
        return simulacionActiva;
    }
    
}
