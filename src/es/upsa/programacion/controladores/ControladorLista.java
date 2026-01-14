package es.upsa.programacion.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.servicios.PersistenciaJSON;

public class ControladorLista {
    private final Map<Integer, ListaReproduccion> listas;
    private int secuenciaId = 1;
    private PersistenciaJSON persistencia;

    public ControladorLista(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.listas = new HashMap<>(persistencia.cargarListas());
        for (int id : listas.keySet()) {
            if (id >= secuenciaId) {
                secuenciaId = id + 1;
            }
        }
    }

    // Métodos para crear, obtener, modificar y eliminar listas

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
        List<ListaReproduccion> listas = new ArrayList<>();
        for (ListaReproduccion lista : this.listas.values()) {
            if (lista.getOwnerId() == ownerId) {
                listas.add(lista);
            }
        }
        return listas;
    }

    public boolean renombrar(int id, int usuarioId, Rol rol, String nuevoNombre) {
        ListaReproduccion lista = listas.get(id);
        if (lista == null) {
            return false;
        }
        if (rol == Rol.ADMIN || lista.getOwnerId() == usuarioId) {
            lista.setNombre(nuevoNombre);
            persistencia.guardarListas(listas);
            return true;
        }
        return false;
    }

    public boolean eliminar(int id, int usuarioId, Rol rol) {
        ListaReproduccion lista = listas.get(id);
        if (lista == null) {
            return false;
        }
        if (rol == Rol.ADMIN || lista.getOwnerId() == usuarioId) {
            listas.remove(id);
            persistencia.guardarListas(listas);
            return true;
        }
        return false;
    }
}
