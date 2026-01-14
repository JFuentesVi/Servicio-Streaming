package es.upsa.programacion.controladores;

import java.util.ArrayList;
import java.util.List;

import es.upsa.programacion.modelos.Cancion;
import es.upsa.programacion.servicios.PersistenciaJSON;

public class ControladorCancion {
    private ArrayList<Cancion> canciones;
    private PersistenciaJSON persistencia;

    public ControladorCancion(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.canciones = new ArrayList<>(persistencia.cargarCanciones());
    }

    // Métodos para crear, buscar, modificar y eliminar canciones

    public boolean crearCancion(int id, String titulo, String artista, String album, int duracionSeg, String genero,
            int anno, String rutaArchivo) {
        if (buscarPorId(id) == null) {
            Cancion nuevaCancion = new Cancion(id, titulo, artista, album, duracionSeg, genero, anno, rutaArchivo);
            canciones.add(nuevaCancion);
            persistencia.guardarCanciones(canciones);
            return true;
        }
        return false;
    }

    public int modificarCancion(int id, String titulo, String artista, String album, int duracionSeg, String genero,
            int anno, String rutaArchivo) {
        Cancion cancion = buscarPorId(id);
        if (cancion != null) {
            cancion.setTitulo(titulo);
            cancion.setArtista(artista);
            cancion.setAlbum(album);
            cancion.setDuracionSeg(duracionSeg);
            cancion.setGenero(genero);
            cancion.setAnno(anno);
            cancion.setRutaArchivo(rutaArchivo);
            persistencia.guardarCanciones(canciones);
            return 1; 
        }
        return 0;
    }

    public int eliminarCancion(int id) {
        Cancion cancion = buscarPorId(id);
        if (cancion != null) {
            canciones.remove(cancion);
            persistencia.guardarCanciones(canciones);
            return 1;
        }
        return 0; 
    }

        public Cancion buscarPorId(int id) {
        for (Cancion cancion : canciones) {
            if (cancion.getId() == id) {
                return cancion;
            }
        }
        return null;
    }

    public List<Cancion> buscarPorTitulo(String filtro) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion cancion : canciones) {
            if (cancion.getTitulo().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(cancion);
            }
        }
        return resultado;
    }

    public List<Cancion> buscarPorArtista(String filtro) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion cancion : canciones) {
            if (cancion.getArtista().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(cancion);
            }
        }
        return resultado;
    }

    public List<Cancion> buscarPorAlbum(String filtro) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion cancion : canciones) {
            if (cancion.getAlbum().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(cancion);
            }
        }
        return resultado;
    }

    public List<Cancion> buscarPorGenero(String filtro) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion cancion : canciones) {
            if (cancion.getGenero().toLowerCase().contains(filtro.toLowerCase())) {
                resultado.add(cancion);
            }
        }
        return resultado;
    }

    public List<Cancion> buscarPorAnno(int anno) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion cancion : canciones) {
            if (cancion.getAnno() == anno) {
                resultado.add(cancion);
            }
        }
        return resultado;
    }

    public List<Cancion> getTodas() {
        return new ArrayList<>(canciones);
    }
}
