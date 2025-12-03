// ESTUDIANTE: 2025XXXXXXXX
// Apellidos: TU_APELLIDO
// Nombres: TU_NOMBRES
package uni.aed.tda.treeTDA.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import uni.aed.tda.treeTDA.BstTDA;
import uni.aed.tda.treeTDA.BstNodeTDA;

/**
 * Lee un archivo de texto, por cada palabra registra la linea donde aparece.
 * Al final imprime las palabras ordenadas (inorder) con la lista de lineas.
 *
 * Uso:
 *   java ... CrossReference <ruta_archivo>
 *
 * Tokenización: quita signos de puntuación básicos y convierte a minúsculas.
 */
public class CrossReference {

    public static void main(String[] args) {
        String path = (args != null && args.length>0) ? args[0] : "src/input.txt";
        try {
            BstTDA<WordEntry> tree = buildIndex(path);
            // imprimir en orden (usamos inorder con patron salto de linea si BstTDA tiene ese metodo)
            StringBuilder sb = new StringBuilder();
            tree.inorder(sb, "\n");
            System.out.println(sb.toString());
        } catch (IOException e) {
            System.err.println("Error E/S: " + e.getMessage());
        }
    }

    public static BstTDA<WordEntry> buildIndex(String path) throws IOException {
        BstTDA<WordEntry> tree = new BstTDA<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                // normalizar: reemplazar puntuacion por espacios, conservar apos/apóstrofe opcional
                String cleaned = line.replaceAll("[^\\p{L}\\p{Nd}'-]+", " "); // letras, digitos, ' y -
                StringTokenizer st = new StringTokenizer(cleaned);
                while (st.hasMoreTokens()) {
                    String token = st.nextToken().toLowerCase();
                    if (token.isEmpty()) continue;
                    WordEntry probe = new WordEntry(token);
                    BstNodeTDA<WordEntry> found = tree.search(probe);
                    if (found == null) {
                        WordEntry we = new WordEntry(token);
                        we.addLine(lineNum);
                        tree.add(we);
                    } else {
                        WordEntry we = found.getKey();
                        we.addLine(lineNum);
                    }
                }
            }
        }
        return tree;
    }
}
