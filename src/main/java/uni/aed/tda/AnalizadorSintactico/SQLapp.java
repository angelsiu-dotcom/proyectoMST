/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.AnalizadorSintactico;
import java.util.Scanner;
import uni.aed.tda.listTDA.ListTDA;
import uni.aed.tda.linkedlistTDA.LinkedListTDA;

public class SQLapp {




    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String consulta = "";
        ListTDA<Token> tokens = new LinkedListTDA<>();
        AnalizadorLexico lex = new AnalizadorLexico();
        AnalizadorSintactico sint = new AnalizadorSintactico();
//incorporamos un menu 
        int opc;
        do {
            System.out.println("\nMENÚ");
            System.out.println("1. Registrar Consulta");
            System.out.println("2. Tokenizar Consulta");
            System.out.println("3. Realizar Analisis Sintactico");
            System.out.println("4. Visualizar Tokens y Resultado");
            System.out.println("5. Salir");
            System.out.print("Opcion: ");
            opc = sc.nextInt(); sc.nextLine();

            switch (opc) {
                case 1 -> {
                    System.out.println("Ingrese consulta SQL:");
                    consulta = sc.nextLine();
                }
                case 2 -> {
                    tokens = lex.tokenizar(consulta);
                    System.out.println("Tokenización completada.");
                }
                case 3 -> {
                    String resultado = sint.analizar(tokens);
                    System.out.println(resultado);
                }
                case 4 -> {
                    int i = 1;
                    var it = tokens.iterador();
                    while (it.hasNext()) {
                        System.out.println(i++ + ". " + it.next());
                    }
                }
            }

        } while (opc != 5);
    }

}
