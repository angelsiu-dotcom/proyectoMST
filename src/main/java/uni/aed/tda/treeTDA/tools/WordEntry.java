// ESTUDIANTE: 2025XXXXXXXX
// Apellidos: TU_APELLIDO
// Nombres: TU_NOMBRES
package uni.aed.tda.treeTDA.tools;

/**
 * Guarda una palabra (clave) y una lista ligada de números de línea.
 * Comparable por la palabra (ignoreCase).
 */
public class WordEntry implements Comparable<WordEntry> {
    private String word;
    private LineNode head; // lista ligada de números de linea

    private static class LineNode {
        int line;
        LineNode next;
        LineNode(int line){ this.line = line; }
    }

    public WordEntry(String word){
        this.word = word.trim();
        this.head = null;
    }

    public String getWord(){ return word; }

    /** agrega numero de linea si no existe ya */
    public void addLine(int line){
        if(head == null){ head = new LineNode(line); return; }
        LineNode p = head;
        while(p != null){
            if(p.line == line) return; // ya existe
            if(p.next == null) break;
            p = p.next;
        }
        p.next = new LineNode(line);
    }

    public String linesToString(){
        StringBuilder sb = new StringBuilder();
        LineNode p = head;
        boolean first = true;
        while(p != null){
            if(!first) sb.append(", ");
            sb.append(p.line);
            first = false;
            p = p.next;
        }
        return sb.toString();
    }

    @Override
    public int compareTo(WordEntry o){
        if(o == null) return 1;
        return this.word.compareToIgnoreCase(o.word);
    }

    @Override
    public String toString(){
        return word + " : " + linesToString();
    }
}
