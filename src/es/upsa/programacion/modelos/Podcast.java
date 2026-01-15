package es.upsa.programacion.modelos;

public class Podcast extends ItemMultimedia {
    private String descripcion;

    public Podcast(int id, String titulo, String autor, String temporada, String descripcion,
            String genero, int anno, String rutaArchivo) {
        super(id, titulo, autor, temporada, genero, anno, rutaArchivo);
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String play() {
        return "Reproduciendo podcast: " + getTitulo() + " con " + getAutor();
    }

    @Override
    public String pause() {
        return "Pausa podcast: " + getTitulo();
    }

    @Override
    public String stop() {
        return "Detenido podcast: " + getTitulo();
    }

    @Override
    public String toString() {
        return "🎙️ " + getTitulo() + " | Host: " + getAutor() + " | Temporada: " + getAlbum() +
                " | " + getGenero() + " (" + getAnno() + ") [ID: " + getId() + "]" +
                "\n   📝 " + descripcion;
    }
}
