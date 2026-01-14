package es.upsa.programacion.controladores;

import java.util.ArrayList;
import java.util.List;

import es.upsa.programacion.modelos.Cancion;
import es.upsa.programacion.servicios.PersistenciaJSON;

/**
 * CRUD y búsquedas avanzadas para canciones con persistencia.
 */
public class ControladorCancion {
    private ArrayList<Cancion> canciones;
    private PersistenciaJSON persistencia;

    public ControladorCancion(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.canciones = new ArrayList<>(persistencia.cargarCanciones());
    }

    // Métodos para crear, buscar, modificar y eliminar canciones
    public boolean crearCancion(int id, String titulo, String artista, String album, int duracionSeg, String genero,
            int anno) {
        if (buscarCancion(id) == null) {
            Cancion nuevaCancion = new Cancion(id, titulo, artista, album, duracionSeg, genero, anno);
            canciones.add(nuevaCancion);
            persistencia.guardarCanciones(canciones);
            return true;
        }
        return false;
    }

    public Cancion buscarCancion(int id) {
        for (Cancion cancion : canciones) {
            if (cancion.getId() == id) {
                return cancion;
            }
        }
        return null;
    }

    public int modificarCancion(int id, String titulo, String artista, String album, int duracionSeg, String genero,
            int anno) {
        Cancion cancion = buscarCancion(id);
        if (cancion != null) {
            cancion.setTitulo(titulo);
            cancion.setArtista(artista);
            cancion.setAlbum(album);
            cancion.setDuracionSeg(duracionSeg);
            cancion.setGenero(genero);
            cancion.setAnno(anno);
            persistencia.guardarCanciones(canciones);
            return 1; // Modificación exitosa
        }
        return 0; // Canción no encontrada
    }

    public int eliminarCancion(int id) {
        Cancion cancion = buscarCancion(id);
        if (cancion != null) {
            canciones.remove(cancion);
            persistencia.guardarCanciones(canciones);
            return 1; // Eliminación exitosa
        }
        return 0; // Canción no encontrada
    }

    // Búsquedas avanzadas: devuelve lista de coincidencias parciales case-insensitive
    public List<Cancion> buscarPorTitulo(String filtro) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion c : canciones) {
            if (c.getTitulo().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Cancion> buscarPorArtista(String filtro) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion c : canciones) {
            if (c.getArtista().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Cancion> buscarPorAlbum(String filtro) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion c : canciones) {
            if (c.getAlbum().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Cancion> buscarPorGenero(String filtro) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion c : canciones) {
            if (c.getGenero().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Cancion> buscarPorAnno(int anno) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion c : canciones) {
            if (c.getAnno() == anno) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Cancion> getTodas() {
        return new ArrayList<>(canciones);
    }
}
