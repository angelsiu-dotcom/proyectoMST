// Código Estudiante: XXXXXXXX
// Apellidos y Nombres: TU_APELLIDO TU_NOMBRE
// Clase: Reporte.java
package uni.aed.tda.treeTDA.vocabulario;

import java.io.BufferedWriter;
import java.io.IOException;

import uni.aed.tda.treeTDA.BstNodeTDA;

/**
 * Clase responsable de "imprimir" (reportar) el contenido
 * del árbol de vocabulario en orden alfabético (inorder).
 */
public class Reporte {

    /**
     * Recorre el árbol en inorder y escribe cada entrada (nodo) en una línea
     * del archivo de salida.
     */
    public void escribirInorder(BstNodeTDA<Vocabulario> root, BufferedWriter bw) throws IOException {
        inorderRec(root, bw);
    }

    private void inorderRec(BstNodeTDA<Vocabulario> node, BufferedWriter bw) throws IOException {
        if (node == null) return;

        inorderRec(node.getLeft(), bw);
        // visit
        Vocabulario v = node.getKey();
        bw.write(v.toString());
        bw.newLine();
        inorderRec(node.getRight(), bw);
    }
}
