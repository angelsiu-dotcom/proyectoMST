/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.historialweb;
import java.util.Scanner; 
import uni.aed.tda.listTDA.ListTDA; 
import uni.aed.tda.linkedlistTDA.LinkedListTDA; 
import java.time.LocalDateTime; 

public class HistoriaWebMain {
    
    private static ListTDA<PaginaWeb> historial;
    
    public HistoriaWebMain() {
         historial = new LinkedListTDA<>(); 
        
    }
    public static void main(String[] args){
       Scanner sc = new Scanner(System.in);
       String Rpta= "S"; 
       HistoriaWebMain app= new HistoriaWebMain(); 
       String SEPARADOR="\n";
       int opcion; 
        try{
            do{
                System.out.print("Algoritmos Maximo-Minimo"+SEPARADOR+
                        "1.- Agregar pagina"+SEPARADOR+
                        "2.- Eliminar pagina "+SEPARADOR+
                        "3.- Buscar pagina"+SEPARADOR+
                        "4.- Agregar antes o despues"+SEPARADOR+
                        "5.- Visualizar el historial"+SEPARADOR+ 
                        "Elija una opcion:");
                opcion=sc.nextInt();
                sc.nextLine(); 
                switch(opcion)
                {
                    case 1->{app.agregarPagina(sc);}
                    case 2->{app.eliminarPagina(sc);}
                    case 3->{app.buscarPagina(sc);}
                    case 4->{app.agregarAntesDespues(sc);}
                    case 5->{app.visualizar();}
                    default-> {break;}                
                }
                System.out.print("Para continuar con la operaciones pulse S, para finalizar pulse N: ");
                Rpta=sc.next().toUpperCase();            
            }while(Rpta.equals("S")==true);
            sc.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    private static void agregarPagina(Scanner sc){
        System.out.println("Ingresar la URL: "); 
        String url= sc.nextLine(); 
        PaginaWeb p = new PaginaWeb(url); 
        historial.add(p); 
        System.out.println("Pagina agregada: " + p);
    }
    
    private static void eliminarPagina(Scanner sc){
        System.out.println("Eliminar url: "); 
        String url= sc.nextLine(); 
        PaginaWeb p= new PaginaWeb(url);
        historial.delete(p); 
        
    }
    
    
    private static void buscarPagina(Scanner sc){
        System.out.println("Ingrese la URL a buscar: ");
        String url= sc.nextLine(); 
        PaginaWeb p= new PaginaWeb(url); 
        if(historial.contain(p))
            System.out.println("La URL ya fue visitada antes"); 
        else{
            System.out.println("La url no se encuentra en el historial"); 
        }
        
    }
    
    private static void agregarAntesDespues(Scanner sc){
        System.out.println("Ingrese la url existente"); 
        String Existente= sc.nextLine(); 
        System.out.println("Ingrese la nueva url"); 
        String nuevaURL= sc.nextLine(); 
        System.out.println("Agregar antes(A) o despues (D) de la url existente");
        String opcion= sc.nextLine(); 
        
        PaginaWeb nueva= new PaginaWeb(nuevaURL); 
        PaginaWeb ref= new PaginaWeb(Existente); 
        
        int index= historial.indexOf(ref);
        
        if(index==-1)
            return; 
        if(opcion.equals("A")){
            historial.add(index+1, nueva); 
            
        } else if(opcion.equals("D")){
            historial.add(index,nueva);
        }else{
            System.out.println("Opcion no valida"); 
            return; 
        }
    }
    
    private static void visualizar(){
        if(historial.isEmpty())
            return; 
        System.out.println("Historial (Antiguo-> reciente");
        for(int i=0; i<historial.size(); i++){
            System.out.println((i+1)+". " + historial.get(i)); 
        }
    }
}
