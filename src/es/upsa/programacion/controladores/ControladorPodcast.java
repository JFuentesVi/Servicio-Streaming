package es.upsa.programacion.controladores;

import java.util.ArrayList;
import java.util.List;

import es.upsa.programacion.modelos.Podcast;
import es.upsa.programacion.servicios.PersistenciaJSON;

public class ControladorPodcast {
    private final List<Podcast> podcasts;
    private PersistenciaJSON persistencia;

    public ControladorPodcast(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.podcasts = new ArrayList<>(persistencia.cargarPodcasts());
    }

    // Métodos para crear, buscar, modificar y eliminar podcasts

    public boolean crearPodcast(int id, String titulo, String anfitrion, String categoria, String descripcion,
            int duracionSeg, String genero, int anno, String rutaArchivo) {
        if (buscarPorId(id) == null) {
            podcasts.add(new Podcast(id, titulo, anfitrion, categoria, descripcion, duracionSeg, genero, anno, rutaArchivo));
            persistencia.guardarPodcasts(podcasts);
            return true;
        }
        return false;
    }

    public Podcast buscarPorId(int id) {
        for (Podcast podcast : podcasts) {
            if (podcast.getId() == id) {
                return podcast;
            }
        }
        return null;
    }

    public List<Podcast> buscarPorTitulo(String filtro) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            if (podcast.getTitulo().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(podcast);
            }
        }
        return resultado;
    }

    public List<Podcast> buscarPorAnfitrion(String filtro) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            if (podcast.getAnfitrion().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(podcast);
            }
        }
        return resultado;
    }

    public List<Podcast> buscarPorCategoria(String filtro) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            if (podcast.getCategoria().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(podcast);
            }
        }
        return resultado;
    }

    public List<Podcast> buscarPorGenero(String filtro) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            if (podcast.getGenero().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(podcast);
            }
        }
        return resultado;
    }

    public List<Podcast> buscarPorAnno(int anno) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            if (podcast.getAnno() == anno) {
                resultado.add(podcast);
            }
        }
        return resultado;
    }

    public int modificarPodcast(int id, String titulo, String anfitrion, String categoria, String descripcion,
            int duracionSeg, String genero, int anno, String rutaArchivo) {
        Podcast podcast = buscarPorId(id);
        if (podcast == null) {
            return 0;
        }
        podcast.setTitulo(titulo);
        podcast.setAnfitrion(anfitrion);
        podcast.setCategoria(categoria);
        podcast.setDescripcion(descripcion);
        podcast.setDuracionSeg(duracionSeg);
        podcast.setGenero(genero);
        podcast.setAnno(anno);
        podcast.setRutaArchivo(rutaArchivo);
        persistencia.guardarPodcasts(podcasts);
        return 1;
    }

    public int eliminarPodcast(int id) {
        Podcast podcast = buscarPorId(id);
        if (podcast != null) {
            podcasts.remove(podcast);
            persistencia.guardarPodcasts(podcasts);
            return 1;
        }
        return 0;
    }

    public List<Podcast> getTodos() {
        return new ArrayList<>(podcasts);
    }
}
