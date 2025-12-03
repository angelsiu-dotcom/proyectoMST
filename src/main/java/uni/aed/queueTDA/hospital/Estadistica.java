/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.queueTDA.hospital;

public class Estadistica {
    private final int prioridad;    //Nivel de prioridad: 1-5
    private int totalPacientes;      //Total trabajos procesados
    private int tiempoEsperaTotal;  //Sumarizar todos los tiempos de espera para cada prioridad
    private int tiempoEsperaMaximo; //Determinar el tiempo de espera maximo para cada prioridad

    public Estadistica(int prioridad) {
        //validar parametros de entrada
        if(prioridad<1 || prioridad>5)
            throw new IllegalArgumentException("Prioridad debe ser entre 1 y 5");
        
        this.prioridad = prioridad;
        this.totalPacientes=0;
        this.tiempoEsperaTotal=0;
        this.tiempoEsperaMaximo=0;
    }

    public int getPrioridad() {return prioridad;}
    public int getTotalPacientes() {return totalPacientes;}
    public int getTiempoEsperaTotal() {return tiempoEsperaTotal;}
    public int getTiempoEsperaMaximo() {return tiempoEsperaMaximo;}
    
    //Registramos el tiempo de espera de un paciente que esta siendo atendido 
    public void registrarTiempoEspera(int tiempoEspera){
        if(tiempoEspera<0)
            throw new IllegalArgumentException("tiempo de espera no puede ser negativo");        
        tiempoEsperaTotal+=tiempoEspera;//acumulamos el tiempo de espera
        totalPacientes++;    //incrementamos el numero de trabajos procesado
        //Actualizar el tiempo de espera maximo, si hubiere
        if(tiempoEspera>tiempoEsperaMaximo)
            tiempoEsperaMaximo=tiempoEspera;
    }
    //Calculo del tiempo de Espera promedio
    public double calcularTiempoEsperaPromedio(){
        if(totalPacientes==0)
            return 0.0;
        return (double)tiempoEsperaTotal/totalPacientes;
    }
    
    //verificar si existen pacientes registrados para esta prioridad
    public boolean tienePacientes(){
        return totalPacientes>0;
    }
    //Reiniciar parametros estadisticos
    public void reiniciar(){
        this.totalPacientes=0;
        this.tiempoEsperaTotal=0;
        this.tiempoEsperaMaximo=0;     
    }

    @Override
    public String toString() {
        if(!tienePacientes())
            return String.format("Prioridad %d: Sin Pacientes ", prioridad);
        return String.format("Prioridad %d: %d Pacientes, Promedio: %.2f min, Maximo: %d min"
                , prioridad, totalPacientes,calcularTiempoEsperaPromedio(),tiempoEsperaMaximo);
    }
    
    
    
}