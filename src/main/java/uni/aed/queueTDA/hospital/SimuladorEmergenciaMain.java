/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.queueTDA.hospital;

import java.util.InputMismatchException;
import java.util.Scanner;
import uni.aed.tda.listTDA.ListTDA;
import uni.aed.tda.queueTDA.simulador.Simulador;
import uni.aed.tda.queueTDA.simulador.SimuladorMain;


public class SimuladorEmergenciaMain {
    private final SimuladorEmergencias simulador = new SimuladorEmergencias();                // Instancia del simulador de agenda
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");      // Scanner para entrada de usuario        
    public static void main(String[] args) {
        SimuladorEmergenciaMain simuladorMain=new SimuladorEmergenciaMain();
        simuladorMain.menu();
    }
    
    private void menu() {
        String SEPARADOR = "\n";
        int opcion = 5;
        try {            
            do {                
                System.out.println("--- SIMULADOR DE AGENDA DE TRABAJO ---");
                System.out.print("--- MENU DE OPCIONES ---" + SEPARADOR +
                        "1.- Simular " + SEPARADOR +                        
                        "2.- Obtener el tiempo de espera promedio" + SEPARADOR +
                        "3.- Obtener el tiempo de espera maximo por nivel de prioridad" + SEPARADOR +                        
                        "4.- Eliminar simulacion medica " + SEPARADOR +
                        "5.- Visualizar Simular" + SEPARADOR +                        
                        "6.- Salir " + SEPARADOR + "Elija una opcion: ");
                opcion = scanner.nextInt();  // Leer la opción del usuario

                // Acciones según la opción seleccionada
                switch (opcion) {
                    case 1 -> { cargarAgendaPaciente(); visualizarAgendaPaciente();}                    
                    case 2 -> { obtenerTiempoEsperaPromedio(); }
                    case 3 -> { obtenerTiempoEsperaMaximoPorPrioridad(); }                    
                    case 4 -> { eliminarAgendaPaciente(); }
                    case 5 -> { visualizarAgendaPaciente(); }                    
                    case 6 -> { System.out.println("Saliendo del programa..."); return; }  
                    default -> System.out.println("Opcion no valida. Por favor, elija una opcion correcta(1-6).");
                }
            } while (opcion != 6);  
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar obligatoriamente un numero entero como opcion elegida." + e.toString());
        } catch (Exception e) {
            System.out.println("Error presentado: " + e.getMessage());
        }
    }
    // Opcion a) Cargar Agenda de Trabajo
    private void cargarAgendaPaciente() {
        System.out.println("--- CARGAR AGENDA DE PACIENTES ---");
        
        try {
            // Solicitar minutos a simular
            System.out.print("Ingrese el numero de minutos a simular (M): ");
            int minutos = leerEnteroPositivo();
            
            
            
            int maxPacientes = 1; 
            
            // Confirmar parametros
            System.out.printf("\nConfirmacion de parametros:\n");
            System.out.printf("- Minutos a simular: %d\n", minutos);
            System.out.printf("- Pacientes simultaneos maximo: %d\n", maxPacientes);
            System.out.print("¿Proceder con la simulacion? (s/n): ");
            
            String confirmacion = scanner.next().trim().toLowerCase();
            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                System.out.println("\nEjecutando simulacion...");                
                // Ejecutar la simulacion
                long tiempoInicio = System.currentTimeMillis();
                simulador.cargarAgenda(minutos, maxPacientes);
                long tiempoFin = System.currentTimeMillis();                
                System.out.printf("Simulacion completada en %d ms\n", (tiempoFin - tiempoInicio));                
            } else
                System.out.println("Simulacion cancelada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al cargar la agenda: " + e.getMessage());
        }
    }
    
    // Opcion b) Obtener el tiempo de espera promedio
    private void obtenerTiempoEsperaPromedio() {
        System.out.println("--- TIEMPO DE ESPERA PROMEDIO ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa. Cargue una agenda primero.");
            return;
        }        
        double tiempoPromedio = simulador.obtenerTiempoEsperaPromedio();
        System.out.printf("Tiempo de espera promedio general: %.2f minutos\n", tiempoPromedio);
    }
    
    // Opcion c) Obtener el tiempo de espera maximo por nivel de prioridad
    private void obtenerTiempoEsperaMaximoPorPrioridad() {
        System.out.println("--- TIEMPO DE ESPERA MAXIMO POR NIVEL DE PRIORIDAD ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa. Cargue una agenda primero.");
            return;
        }
        
        ListTDA<uni.aed.tda.queueTDA.simulador.Estadistica> estadisticas = simulador.getEstadistica();
        boolean hayEstadistica = false;        
        for (int i = 0; i < estadisticas.size(); i++) {
            if (estadisticas.get(i).tieneTrabajos()) {
                System.out.println(estadisticas.get(i).toString());
                hayEstadistica = true;
            }
        }
        
        if (!hayEstadistica)
            System.out.println("No hay estadisticas disponibles para ningun nivel de prioridad.");        
    }
    
    // Opción d) Eliminar actual Agenda de Trabajo
    private void eliminarAgendaPaciente() {
        System.out.println("--- ELIMINAR AGENDA DE Pacientes ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa para eliminar.");
            return;
        }        
        System.out.print("¿Esta seguro de que desea eliminar la agenda actual? (s/n): ");
        String confirmacion = scanner.next().trim().toLowerCase();        
        if (confirmacion.equals("s") || confirmacion.equals("si"))
            simulador.eliminarAgenda();
        else
            System.out.println("Eliminacion cancelada.");        
    }
    
    // Opcion e) Visualizar Agenda de Trabajo
    private void visualizarAgendaPaciente() {        
        System.out.println(simulador.toString());        
    }
    
    // Leemos un numero entero positivo del usuario
    private int leerEnteroPositivo() {
        while (true) {
            try {
                int numero = scanner.nextInt();                
                if (numero <= 0)
                    throw new IllegalArgumentException("El numero debe ser positivo");                
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un numero entero valido.");                
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.print("Intente nuevamente: ");
        }
    }    
    
}
