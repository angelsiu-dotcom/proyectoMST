/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.PC2;
import uni.aed.PC2.Contraseña;
import java.util.Scanner; 

public class ContraseñaMain {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); 
    Contraseña c= new Contraseña(); 
    int p; 
    
    do{
        String texto=""; 
        
        
        
        
        p= sc.nextInt(); 
        sc.nextLine();
        
        switch(p){
            case 1: 
                System.out.println("iNGRESE CONTRASEÑA: "); 
                String t= sc.nextLine(); 
                c.registrar(t); 
                System.out.println("contraseña registrada"); 
                break; 
            case 2: 
                System.out.println(c.validar()); 
                break; 
            case 3: 
                System.out.println("Contraseña: "+ c.visualizar()); 
                break; 
            case 4: 
                System.out.println("Saliendo"); 
                break; 
                
        }
     }while(p!=4); 
    }
}
