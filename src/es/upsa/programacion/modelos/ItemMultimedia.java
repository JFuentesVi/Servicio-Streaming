package es.upsa.programacion.modelos;

public abstract class ItemMultimedia implements Reproducible, Comparable<ItemMultimedia> {
    private final int id;
    private String titulo;
    private String autor;
    private String album;
    private String genero;
    private int anno;
    private String rutaArchivo;

    protected ItemMultimedia(int id, String titulo, String autor, String album, String genero, int anno,
            String rutaArchivo) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.album = album;
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

    public String getAutor() {
        return autor;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnno() {
        return anno;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public int compareTo(ItemMultimedia otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }
}
