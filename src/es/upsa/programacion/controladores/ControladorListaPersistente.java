package es.upsa.programacion.controladores;

import java.util.HashMap;
import java.util.Map;

import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.servicios.PersistenciaJSON;

/**
 * Wrapper para garantizar que toda modificación de lista persiste automáticamente.
 */
public class ControladorListaPersistente {
    private final Map<Integer, ListaWrapper> listas;
    private int secuenciaId = 1;
    private PersistenciaJSON persistencia;

    private class ListaWrapper {
        private final ListaReproduccion lista;

        ListaWrapper(ListaReproduccion lista) {
            this.lista = lista;
        }

        ListaReproduccion get() {
            return lista;
        }

        void guardar() {
            Map<Integer, ListaReproduccion> raw = new HashMap<>();
            for (Map.Entry<Integer, ListaWrapper> entry : listas.entrySet()) {
                raw.put(entry.getKey(), entry.getValue().get());
            }
            persistencia.guardarListas(raw);
        }
    }

    public ControladorListaPersistente(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.listas = new HashMap<>();
        Map<Integer, ListaReproduccion> cargadas = persistencia.cargarListas();
        for (Map.Entry<Integer, ListaReproduccion> entry : cargadas.entrySet()) {
            listas.put(entry.getKey(), new ListaWrapper(entry.getValue()));
            if (entry.getKey() >= secuenciaId) {
                secuenciaId = entry.getKey() + 1;
            }
        }
    }

    public ListaReproduccion crearLista(String nombre, int ownerId) {
        ListaReproduccion nueva = new ListaReproduccion(secuenciaId++, nombre, ownerId);
        ListaWrapper wrapper = new ListaWrapper(nueva);
        listas.put(nueva.getId(), wrapper);
        wrapper.guardar();
        return nueva;
    }

    public ListaReproduccion obtener(int id) {
        ListaWrapper w = listas.get(id);
        return w == null ? null : w.get();
    }

    public void guardarLista(int id) {
        ListaWrapper w = listas.get(id);
        if (w != null) {
            w.guardar();
        }
    }
}
