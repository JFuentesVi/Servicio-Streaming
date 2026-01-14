package es.upsa.programacion.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.servicios.PersistenciaJSON;

/**
 * CRUD de listas de reproducción con control de propietario y persistencia.
 */
public class ControladorLista {
    private final Map<Integer, ListaReproduccion> listas;
    private int secuenciaId = 1;
    private PersistenciaJSON persistencia;

    public ControladorLista(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.listas = new HashMap<>(persistencia.cargarListas());
        // Calcular secuencia desde datos cargados
        for (int id : listas.keySet()) {
            if (id >= secuenciaId) {
                secuenciaId = id + 1;
            }
        }
    }

    public ListaReproduccion crearLista(String nombre, int ownerId) {
        ListaReproduccion lista = new ListaReproduccion(secuenciaId++, nombre, ownerId);
        listas.put(lista.getId(), lista);
        persistencia.guardarListas(listas);
        return lista;
    }

    public ListaReproduccion obtener(int id) {
        return listas.get(id);
    }

    public void guardarCambios() {
        persistencia.guardarListas(listas);
    }

    public List<ListaReproduccion> obtenerPorUsuario(int ownerId) {
        List<ListaReproduccion> res = new ArrayList<>();
        for (ListaReproduccion l : listas.values()) {
            if (l.getOwnerId() == ownerId) {
                res.add(l);
            }
        }
        return res;
    }

    public boolean renombrar(int id, int usuarioId, Rol rol, String nuevoNombre) {
        ListaReproduccion l = listas.get(id);
        if (l == null) {
            return false;
        }
        if (rol == Rol.ADMIN || l.getOwnerId() == usuarioId) {
            l.setNombre(nuevoNombre);
            persistencia.guardarListas(listas);
            return true;
        }
        return false;
    }

    public boolean eliminar(int id, int usuarioId, Rol rol) {
        ListaReproduccion l = listas.get(id);
        if (l == null) {
            return false;
        }
        if (rol == Rol.ADMIN || l.getOwnerId() == usuarioId) {
            listas.remove(id);
            persistencia.guardarListas(listas);
            return true;
        }
        return false;
    }
}
