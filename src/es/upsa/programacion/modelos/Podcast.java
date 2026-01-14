package es.upsa.programacion.modelos;

public class Podcast extends ItemMultimedia {
    private String anfitrion;
    private String categoria;
    private String descripcion;

    public Podcast(int id, String titulo, String anfitrion, String categoria, String descripcion, int duracionSeg,
            String genero, int anno, String rutaArchivo) {
        super(id, titulo, duracionSeg, genero, anno, rutaArchivo);
        this.anfitrion = anfitrion;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public String getAnfitrion() {
        return anfitrion;
    }

    public void setAnfitrion(String anfitrion) {
        this.anfitrion = anfitrion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String play() {
        return "Reproduciendo podcast: " + getTitulo() + " por " + anfitrion;
    }

    @Override
    public String pause() {
        return "Pausa podcast: " + getTitulo();
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", anfitrion='" + anfitrion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", duracionSeg=" + getDuracionSeg() +
                ", genero='" + getGenero() + '\'' +
                ", anno=" + getAnno() +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
