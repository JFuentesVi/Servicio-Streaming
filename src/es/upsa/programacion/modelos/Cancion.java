package es.upsa.programacion.modelos;

/**
 * Cancion hereda de MediaItem e implementa Reproducible para demostrar
 * herencia e interfaces en el dominio.
 */
public class Cancion extends MediaItem {
    private String artista;
    private String album;

    public Cancion(int id, String titulo, String artista, String album, int duracionSeg, String genero, int anno) {
        super(id, titulo, duracionSeg, genero, anno);
        this.artista = artista;
        this.album = album;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String play() {
        return "Reproduciendo canción: " + getTitulo() + " de " + artista;
    }

    @Override
    public String pause() {
        return "Pausa canción: " + getTitulo();
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", artista='" + artista + '\'' +
                ", album='" + album + '\'' +
                ", duracionSeg=" + getDuracionSeg() +
                ", genero='" + getGenero() + '\'' +
                ", anno=" + getAnno() +
                '}';
    }
}
