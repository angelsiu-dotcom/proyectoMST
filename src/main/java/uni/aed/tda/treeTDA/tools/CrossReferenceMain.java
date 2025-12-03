// ESTUDIANTE: 2025XXXXXXXX
// Apellidos: TU_APELLIDO
// Nombres: TU_NOMBRES
package uni.aed.tda.treeTDA.tools;

import java.io.IOException;
import java.util.Scanner;

import uni.aed.tda.treeTDA.BstTDA;

public class CrossReferenceMain {

    private static final String DEFAULT_PATH = "src/input.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BstTDA<WordEntry> tree = null;

        String path = DEFAULT_PATH;
        // Si quieres, puedes permitir cambiar path con args
        if (args != null && args.length > 0) {
            path = args[0];
        }

        char opcion;
        do {
            System.out.println("======================================");
            System.out.println("   MENÚ - REFERENCIA CRUZADA (BST)    ");
            System.out.println("======================================");
            System.out.println("a) Construir índice desde archivo");
            System.out.println("b) Mostrar palabras ordenadas (inorder)");
            System.out.println("c) Mostrar estructura del árbol (toString)");
            System.out.println("d) Salir");
            System.out.print("Opción: ");
            opcion = sc.next().toLowerCase().charAt(0);
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 'a':
                    try {
                        System.out.println("Leyendo archivo: " + path);
                        tree = CrossReference.buildIndex(path);
                        System.out.println("Índice construido correctamente.");
                    } catch (IOException e) {
                        System.err.println("Error E/S: " + e.getMessage());
                    }
                    break;
                case 'b':
                    if (tree == null) {
                        System.out.println("Primero construya el índice (opción a).");
                    } else {
                        System.out.println("=== Palabras ordenadas con líneas (inorder) ===");
                        StringBuilder sb = new StringBuilder();
                        tree.inorder(sb, "\n");
                        System.out.println(sb.toString());
                    }
                    break;
                case 'c':
                    if (tree == null) {
                        System.out.println("Primero construya el índice (opción a).");
                    } else {
                        System.out.println("=== Estructura del árbol (BST) ===");
                        System.out.println(tree.toString());
                    }
                    break;
                case 'd':
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 'd');

        sc.close();
    }
}
