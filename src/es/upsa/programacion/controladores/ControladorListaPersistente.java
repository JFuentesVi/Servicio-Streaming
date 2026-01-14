package es.upsa.programacion.controladores;

import java.util.HashMap;
import java.util.Map;

import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.servicios.PersistenciaJSON;

public class ControladorListaPersistente {
    private final Map<Integer, ListaPersistente> listas;
    private int secuenciaId = 1;
    private PersistenciaJSON persistencia;

    private class ListaPersistente {
        private final ListaReproduccion lista;

        ListaPersistente(ListaReproduccion lista) {
            this.lista = lista;
        }

        ListaReproduccion get() {
            return lista;
        }

        void guardar() {
            Map<Integer, ListaReproduccion> raw = new HashMap<>();
            for (Map.Entry<Integer, ListaPersistente> entrada : listas.entrySet()) {
                raw.put(entrada.getKey(), entrada.getValue().get());
            }
            persistencia.guardarListas(raw);
        }
    }

    public ControladorListaPersistente(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.listas = new HashMap<>();
        Map<Integer, ListaReproduccion> cargadas = persistencia.cargarListas();
        for (Map.Entry<Integer, ListaReproduccion> entrada : cargadas.entrySet()) {
            listas.put(entrada.getKey(), new ListaPersistente(entrada.getValue()));
            if (entrada.getKey() >= secuenciaId) {
                secuenciaId = entrada.getKey() + 1;
            }
        }
    }

    public ListaReproduccion crearLista(String nombre, int ownerId) {
        ListaReproduccion nueva = new ListaReproduccion(secuenciaId++, nombre, ownerId);
        ListaPersistente envoltorio = new ListaPersistente(nueva);
        listas.put(nueva.getId(), envoltorio);
        envoltorio.guardar();
        return nueva;
    }

    public ListaReproduccion obtener(int id) {
        ListaPersistente envoltorio = listas.get(id);
        if (envoltorio == null) {
            return null;
        } else {
            return envoltorio.get();
        }
    }

    public void guardarLista(int id) {
        ListaPersistente envoltorio = listas.get(id);
        if (envoltorio != null) {
            envoltorio.guardar();
        }
    }
}
