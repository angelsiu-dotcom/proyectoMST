/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.AnalizadorSintactico;
import uni.aed.tda.linkedlistTDA.LinkedListTDA;
import uni.aed.tda.listTDA.ListTDA;
import java.util.regex.*;
public class Token {
    private TokenType tipo; 
    private String Valor; 

    public Token(TokenType tipo, String Valor) {
        this.tipo = tipo;
        this.Valor = Valor;
    }

    public TokenType getTipo() {
        return tipo;
    }

    public void setTipo(TokenType tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    @Override
    public String toString() {
        return "Token{" + "tipo=" + tipo + ", Valor=" + Valor + '}';
    }
    
    

}

