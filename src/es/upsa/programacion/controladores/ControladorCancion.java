package es.upsa.programacion.controladores;

import java.util.List;

import es.upsa.programacion.modelos.Cancion;
import es.upsa.programacion.servicios.PersistenciaJSON;

public class ControladorCancion extends ControladorMultimedia<Cancion> {
    private PersistenciaJSON persistencia;

    public ControladorCancion(PersistenciaJSON persistencia) {
        super(persistencia.cargarCanciones());
        this.persistencia = persistencia;
    }

    public boolean crearCancion(int id, String titulo, String autor, String album, String genero,
            int anno, String rutaArchivo) {
        if (buscarPorId(id) == null) {
            items.add(new Cancion(id, titulo, autor, album, genero, anno, rutaArchivo));
            persistencia.guardarCanciones(items);
            return true;
        }
        return false;
    }

    public boolean modificarCancion(int id, String titulo, String autor, String album, String genero,
            int anno, String rutaArchivo) {
        Cancion cancion = buscarPorId(id);
        if (cancion != null) {
            cancion.setTitulo(titulo);
            cancion.setAutor(autor);
            cancion.setAlbum(album);
            cancion.setGenero(genero);
            cancion.setAnno(anno);
            cancion.setRutaArchivo(rutaArchivo);
            persistencia.guardarCanciones(items);
            return true;
        }
        return false;
    }

    public boolean eliminarCancion(int id) {
        Cancion cancion = buscarPorId(id);
        if (cancion != null) {
            items.remove(cancion);
            persistencia.guardarCanciones(items);
            return true;
        }
        return false;
    }
}
