package es.upsa.programacion.modelos;

/**
 * Base común para canciones y podcasts para ilustrar herencia.
 */
public abstract class MediaItem implements Reproducible {
    private final int id;
    private String titulo;
    private int duracionSeg;
    private String genero;
    private int anno;

    protected MediaItem(int id, String titulo, int duracionSeg, String genero, int anno) {
        this.id = id;
        this.titulo = titulo;
        this.duracionSeg = duracionSeg;
        this.genero = genero;
        this.anno = anno;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracionSeg() {
        return duracionSeg;
    }

    public void setDuracionSeg(int duracionSeg) {
        this.duracionSeg = duracionSeg;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }
}
