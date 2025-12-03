// Código Estudiante: XXXXXXXX
// Apellidos y Nombres: TU_APELLIDO TU_NOMBRE
// Clase: Vocabulario.java
package uni.aed.tda.treeTDA.vocabulario;

/**
 * Representa una entrada en el vocabulario:
 * Palabra en inglés + lista de equivalentes en latín.
 * Se usa como "key" en el BstTDA, por eso implementa Comparable.
 */
public class Vocabulario implements Comparable<Vocabulario> {
    private String english;          // palabra en inglés (clave)
    private NodoLatin latinesHead;   // lista enlazada simple de palabras latinas

    // Nodo de lista enlazada para palabras latinas
    private static class NodoLatin {
        String latin;
        NodoLatin next;
        NodoLatin(String latin) { this.latin = latin; }
    }

    public Vocabulario(String english) {
        this.english = english.trim();
        this.latinesHead = null;
    }

    public String getEnglish() {
        return english;
    }

    /**
     * Agrega una palabra latina a la lista enlazada, evitando duplicados.
     */
    public void addLatin(String latin) {
        if (latin == null) return;
        latin = latin.trim();
        if (latin.isEmpty()) return;

        if (latinesHead == null) {
            latinesHead = new NodoLatin(latin);
            return;
        }
        // buscar si ya existe
        NodoLatin p = latinesHead;
        NodoLatin prev = null;
        while (p != null) {
            if (p.latin.equalsIgnoreCase(latin)) {
                return; // ya existe, no se agrega
            }
            prev = p;
            p = p.next;
        }
        prev.next = new NodoLatin(latin);
    }

    /**
     * Devuelve la lista de latines como: "lat1, lat2, lat3"
     */
    public String latinesToString() {
        StringBuilder sb = new StringBuilder();
        NodoLatin p = latinesHead;
        boolean first = true;
        while (p != null) {
            if (!first) sb.append(", ");
            sb.append(p.latin);
            first = false;
            p = p.next;
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Vocabulario o) {
        if (o == null) return 1;
        // Comparación alfabética ignorando mayúsculas/minúsculas
        return this.english.compareToIgnoreCase(o.english);
    }

    @Override
    public String toString() {
        // Formato: "palabraEnInglés : lat1, lat2, ..."
        return english + " : " + latinesToString();
    }
}
