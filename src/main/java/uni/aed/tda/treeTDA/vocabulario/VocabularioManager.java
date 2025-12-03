// Código Estudiante: XXXXXXXX
// Apellidos y Nombres: TU_APELLIDO TU_NOMBRE
// Clase: VocabularioManager.java
package uni.aed.tda.treeTDA.vocabulario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import uni.aed.tda.treeTDA.BstTDA;
import uni.aed.tda.treeTDA.BstNodeTDA;


public class VocabularioManager {

    // Rutas de archivo (ajusta si tu estructura es distinta)
    private String inputLatinPath  = "src/main/java/uni/aed/tda/treeTDA/vocabulario/latin.txt";
    private String outputEnglishPath = "src/main/java/uni/aed/tda/treeTDA/vocabulario/ingles.txt";

    private Reporte reporte;

  
    private BstTDA<Vocabulario> currentTree;
    private String currentUnit;

    public VocabularioManager() {
        this.reporte = new Reporte();
        this.currentTree = null;
        this.currentUnit = null;
    }

    public void setInputLatinPath(String path) {
        this.inputLatinPath = path;
    }

    public void setOutputEnglishPath(String path) {
        this.outputEnglishPath = path;
    }


    public void generarVocabularioIngles() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputLatinPath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputEnglishPath))) {

            String line;
            currentUnit = null;
            currentTree = null;   // reiniciamos el árbol actual

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("%")) {
                    // Nueva unidad
                    if (currentTree != null && currentUnit != null) {
                        // volcamos la unidad anterior
                        escribirUnidad(bw, currentUnit, currentTree);
                    }
                    // inicializamos nueva unidad
                    currentUnit = line;               // ej: %Unit 5
                    currentTree = new BstTDA<>();     // nuevo árbol para esta unidad
                } else {
                    // Línea de vocabulario: "latin : eng1, eng2, ..."
                    if (currentTree == null) continue; // seguridad
                    procesarLineaVocabulario(line, currentTree);
                }
            }

            // Última unidad al finalizar el archivo
            if (currentTree != null && currentUnit != null) {
                escribirUnidad(bw, currentUnit, currentTree);
            }
        }
    }

  
    private void procesarLineaVocabulario(String line, BstTDA<Vocabulario> tree) {
        int colonIndex = line.indexOf(':');
        if (colonIndex <= 0) return; // formato raro, se ignora

        String latinWord = line.substring(0, colonIndex).trim();
        String englishPart = line.substring(colonIndex + 1).trim();

        // Separar equivalentes en inglés por coma
        String[] englishWords = englishPart.split(",");

        for (String engRaw : englishWords) {
            String eng = engRaw.trim();
            if (eng.isEmpty()) continue;

            // Buscar en el árbol si ya existe esa palabra en inglés
            Vocabulario buscado = new Vocabulario(eng);
            BstNodeTDA<Vocabulario> nodoEncontrado = tree.search(buscado);

            if (nodoEncontrado == null) {
                // No existe: crear Vocabulario y agregarlo al árbol
                Vocabulario nuevo = new Vocabulario(eng);
                nuevo.addLatin(latinWord);
                tree.add(nuevo);
            } else {
                // Ya existe: actualizar su lista de latines
                Vocabulario existente = nodoEncontrado.getKey();
                existente.addLatin(latinWord);
            }
        }
    }


    private void escribirUnidad(BufferedWriter bw, String unidad, BstTDA<Vocabulario> tree) throws IOException {
        bw.write(unidad);
        bw.newLine();

        // Recorremos el árbol en inorder para que salgan en orden alfabético
        StringBuilder sb = new StringBuilder();
        tree.inorder(sb, "\n");   // usa el inorder(BstTDA) con patrón de salto de línea
        bw.write(sb.toString());
        bw.newLine();
        bw.newLine(); // línea en blanco entre unidades (opcional)
    }

 
    public void mostrarArchivoInglesEnConsola() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(outputEnglishPath))) {
            String line;
            System.out.println("=== Contenido de ingles.txt ===");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }


    public void mostrarArbolEnConsola() {
        if (currentTree == null) {
            System.out.println("Aún no se ha generado el árbol. Use primero la opción 'a'.");
            return;
        }
        System.out.println("===== Árbol BST de vocabulario (última unidad leída: "
                           + (currentUnit != null ? currentUnit : "?") + ") =====");
        System.out.println(currentTree.toString());
        
    }
}
