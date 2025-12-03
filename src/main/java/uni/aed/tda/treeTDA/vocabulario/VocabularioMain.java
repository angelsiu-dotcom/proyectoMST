
package uni.aed.tda.treeTDA.vocabulario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase principal con el menú del programa.
 */
public class VocabularioMain {

    public static void main(String[] args) {
        VocabularioManager manager = new VocabularioManager();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("a) Generar palabras en ingles (orden alfabetico)");
            System.out.println("b) Visualizar archivo ingles.txt");
            System.out.println("c) Salir");
            System.out.println("d)  Visualizar arbol en memoria");
            System.out.print("Opción: ");

            String opcion;
            try {
                opcion = br.readLine();
            } catch (IOException e) {
                System.out.println("Error de entrada. Saliendo...");
                return;
            }

            if (opcion == null) {
                System.out.println("Opcion nula. Saliendo...");
                return;
            }

            opcion = opcion.trim().toLowerCase();

            try {
                switch (opcion) {
                    case "a":
                        manager.generarVocabularioIngles();
                        System.out.println("Se genero el archivo ingles.txt correctamente.");
                        break;
                    case "b":
                        manager.mostrarArchivoInglesEnConsola();
                        break;
                    case "c":
                        System.out.println("Saliendo del programa...");
                        return;
                    case "d":
                        manager.mostrarArbolEnConsola();
                        break;
                    default:
                        System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (IOException ex) {
                System.out.println("Error de E/S: " + ex.getMessage());
            }
        }
    }
}
