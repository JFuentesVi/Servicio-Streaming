package es.upsa.programacion.controladores;

import java.util.ArrayList;
import java.util.List;

import es.upsa.programacion.modelos.Podcast;
import es.upsa.programacion.servicios.PersistenciaJSON;

/**
 * CRUD y búsquedas en memoria para podcasts con persistencia.
 */
public class ControladorPodcast {
    private final List<Podcast> podcasts;
    private PersistenciaJSON persistencia;

    public ControladorPodcast(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.podcasts = new ArrayList<>(persistencia.cargarPodcasts());
    }

    public boolean crearPodcast(int id, String titulo, String anfitrion, String categoria, String descripcion,
            int duracionSeg, String genero, int anno) {
        if (buscarPodcast(id) == null) {
            podcasts.add(new Podcast(id, titulo, anfitrion, categoria, descripcion, duracionSeg, genero, anno));
            persistencia.guardarPodcasts(podcasts);
            return true;
        }
        return false;
    }

    public Podcast buscarPodcast(int id) {
        for (Podcast podcast : podcasts) {
            if (podcast.getId() == id) {
                return podcast;
            }
        }
        return null;
    }

    // Búsquedas avanzadas
    public List<Podcast> buscarPorTitulo(String filtro) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast p : podcasts) {
            if (p.getTitulo().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Podcast> buscarPorAnfitrion(String filtro) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast p : podcasts) {
            if (p.getAnfitrion().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Podcast> buscarPorCategoria(String filtro) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast p : podcasts) {
            if (p.getCategoria().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Podcast> buscarPorGenero(String filtro) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast p : podcasts) {
            if (p.getGenero().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Podcast> buscarPorAnno(int anno) {
        List<Podcast> resultado = new ArrayList<>();
        for (Podcast p : podcasts) {
            if (p.getAnno() == anno) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public int modificarPodcast(int id, String titulo, String anfitrion, String categoria, String descripcion,
            int duracionSeg, String genero, int anno) {
        Podcast p = buscarPodcast(id);
        if (p == null) {
            return 0;
        }
        p.setTitulo(titulo);
        p.setAnfitrion(anfitrion);
        p.setCategoria(categoria);
        p.setDescripcion(descripcion);
        p.setDuracionSeg(duracionSeg);
        p.setGenero(genero);
        p.setAnno(anno);
        persistencia.guardarPodcasts(podcasts);
        return 1;
    }

    public int eliminarPodcast(int id) {
        Podcast p = buscarPodcast(id);
        if (p != null) {
            podcasts.remove(p);
            persistencia.guardarPodcasts(podcasts);
            return 1;
        }
        return 0;
    }

    public List<Podcast> getTodos() {
        return new ArrayList<>(podcasts);
    }
}
