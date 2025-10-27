
package uni.aed.tda.historialweb;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 


public class PaginaWeb {
    private String url;
    private LocalDateTime timestamp;

    public PaginaWeb(String url) {
        this.url = url;
        this.timestamp = LocalDateTime.now();
    }
    
    
    public String getUrl() {
        return url;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Igualdad basada en URL: útil para operaciones de búsqueda/eliminación
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof PaginaWeb)) return false;
        PaginaWeb other = (PaginaWeb) obj;
        return this.url != null && this.url.equals(other.url);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s  (visitado: %s)", url, timestamp.format(fmt));
    }
}


