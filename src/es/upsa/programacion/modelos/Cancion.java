package es.upsa.programacion.modelos;

public class Cancion extends ItemMultimedia {

    public Cancion(int id, String titulo, String autor, String album, String genero, int anno,
            String rutaArchivo) {
        super(id, titulo, autor, album, genero, anno, rutaArchivo);
    }

    @Override
    public String play() {
        return "Reproduciendo canción: " + getTitulo() + " de " + getAutor();
    }

    @Override
    public String pause() {
        return "Pausa canción: " + getTitulo();
    }

    @Override
    public String stop() {
        return "Detenida canción: " + getTitulo();
    }

    @Override
    public String toString() {
        return "🎵 " + getTitulo() + " - " + getAutor() + " | Álbum: " + getAlbum() +
                " | " + getGenero() + " (" + getAnno() + ") [ID: " + getId() + "]";
    }
}
