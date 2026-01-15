package es.upsa.programacion.controladores;

import java.util.List;

import es.upsa.programacion.modelos.Podcast;
import es.upsa.programacion.servicios.PersistenciaJSON;

public class ControladorPodcast extends ControladorMultimedia<Podcast> {
    private PersistenciaJSON persistencia;

    public ControladorPodcast(PersistenciaJSON persistencia) {
        super(persistencia.cargarPodcasts());
        this.persistencia = persistencia;
    }

    public boolean crearPodcast(int id, String titulo, String autor, String temporada, String descripcion,
            String genero, int anno, String rutaArchivo) {
        if (buscarPorId(id) == null) {
            items.add(new Podcast(id, titulo, autor, temporada, descripcion, genero, anno, rutaArchivo));
            persistencia.guardarPodcasts(items);
            return true;
        }
        return false;
    }

    public boolean modificarPodcast(int id, String titulo, String autor, String temporada, String descripcion,
            String genero, int anno, String rutaArchivo) {
        Podcast podcast = buscarPorId(id);
        if (podcast != null) {
            podcast.setTitulo(titulo);
            podcast.setAutor(autor);
            podcast.setAlbum(temporada);
            podcast.setDescripcion(descripcion);
            podcast.setGenero(genero);
            podcast.setAnno(anno);
            podcast.setRutaArchivo(rutaArchivo);
            persistencia.guardarPodcasts(items);
            return true;
        }
        return false;
    }

    public boolean eliminarPodcast(int id) {
        Podcast podcast = buscarPorId(id);
        if (podcast != null) {
            items.remove(podcast);
            persistencia.guardarPodcasts(items);
            return true;
        }
        return false;
    }
}
