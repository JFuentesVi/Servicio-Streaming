package es.upsa.programacion.modelos;

public abstract class ItemMultimedia implements Reproducible, Comparable<ItemMultimedia> {
    private final int id;
    private String titulo;
    private int duracionSeg;
    private String genero;
    private int anno;
    private String rutaArchivo; // Ruta relativa desde el proyecto (ej: datos/canciones/cancion.extension)

    protected ItemMultimedia(int id, String titulo, int duracionSeg, String genero, int anno, String rutaArchivo) {
        this.id = id;
        this.titulo = titulo;
        this.duracionSeg = duracionSeg;
        this.genero = genero;
        this.anno = anno;
        this.rutaArchivo = rutaArchivo;
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

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public int compareTo(ItemMultimedia otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }
}
