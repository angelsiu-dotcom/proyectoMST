
package uni.aed.PC2;

import uni.aed.tda.listTDA.ListTDA;
import java.util.Scanner; 
import uni.aed.tda.linkedlistTDA.LinkedListTDA; 
public class Contraseña{
    private LinkedListTDA<Character> caracteres; 
//character es objeto 
    public Contraseña() {
        caracteres= new LinkedListTDA<>(); 
    }
    
    public void registrar(String texto) {
        caracteres.clear(); 
        for(int i=0; i<texto.length(); i++){
            caracteres.add(texto.charAt(i)); //charAt retorna el indice de un string en a posicion i
        }
    }
    
    public String validar(){
        String simbolos= "!@#$%^&*()_-+="; 
        boolean minus=false, mayus=false, simbolo=false, digito=false; 
        String texto=""; 
        for(int i=0; i<caracteres.size(); i++)
            texto += caracteres.get(i); 
    
        if(caracteres.size()<12)
            return texto + "   1   "  + "Al menos 12 caracteres"; 
        
        for(int i = 0; i< caracteres.size(); i++){
            char c = caracteres.get(i); 
            
            if(Character.isUpperCase(c)) mayus= true; 
            else if(Character.isLowerCase(c)) minus= true; 
            else if(Character.isDigit(c)) digito= true; 
            else if(simbolos.indexOf(c)!=-1) simbolo= true; // ret0rna el indice d0nde c0incide 
            else if(Character.isWhitespace(c))
                return "Contiene espacios en blanco"; 
            else 
                return "Simbolo no permitido"; 
        }
        
        
        if(!mayus || !minus|| !digito || !simbolo)
            return texto + "   1   " +  "Falta mayuscula, minuscula, numero o simbolo"; 
        
        for(int i=0; i<caracteres.size()+1; i++)
            if(caracteres.get(i).equals(caracteres.get(i-1)))
                return texto + "   " + i+ "   "  + "Dos simbolos repetidos"; 
        return "Cumple todas las reglas"; 
    }
    
    public String visualizar(){
        String texto=""; 
        for(int i=0; i<caracteres.size(); i++)
            texto += caracteres.get(i); 
        return texto;
    }
}
