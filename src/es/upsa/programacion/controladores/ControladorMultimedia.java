package es.upsa.programacion.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import es.upsa.programacion.modelos.ItemMultimedia;

// Controlador base con métodos de búsqueda genéricos para ItemMultimedia

public abstract class ControladorMultimedia<T extends ItemMultimedia> {
    protected final List<T> items;

    protected ControladorMultimedia() {
        this.items = new ArrayList<>();
    }

    protected ControladorMultimedia(List<T> itemsIniciales) {
        this.items = new ArrayList<>(itemsIniciales);
    }

    // Búsquedas genéricas

    public T buscarPorId(int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<T> buscarPorTitulo(String filtro) {
        return buscarPorCampo(filtro, ItemMultimedia::getTitulo);
    }

    public List<T> buscarPorAutor(String filtro) {
        return buscarPorCampo(filtro, ItemMultimedia::getAutor);
    }

    public List<T> buscarPorAlbum(String filtro) {
        return buscarPorCampo(filtro, ItemMultimedia::getAlbum);
    }

    public List<T> buscarPorGenero(String filtro) {
        return buscarPorCampo(filtro, ItemMultimedia::getGenero);
    }

    public List<T> buscarPorAnno(int anno) {
        List<T> resultado = new ArrayList<>();
        for (T item : items) {
            if (item.getAnno() == anno) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    // Búsqueda general en todos los campos de texto
    public List<T> buscar(String filtro) {
        List<T> resultado = new ArrayList<>();
        String filtroLower = filtro.toLowerCase();
        for (T item : items) {
            if (item.getTitulo().toLowerCase().contains(filtroLower) ||
                    item.getAutor().toLowerCase().contains(filtroLower) ||
                    item.getAlbum().toLowerCase().contains(filtroLower) ||
                    item.getGenero().toLowerCase().contains(filtroLower)) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    public List<T> getTodos() {
        return new ArrayList<>(items);
    }

    // Método auxiliar para búsquedas por campo
    private List<T> buscarPorCampo(String filtro, java.util.function.Function<T, String> getter) {
        List<T> resultado = new ArrayList<>();
        String filtroLower = filtro.toLowerCase();
        for (T item : items) {
            if (getter.apply(item).toLowerCase().contains(filtroLower)) {
                resultado.add(item);
            }
        }
        return resultado;
    }
}
