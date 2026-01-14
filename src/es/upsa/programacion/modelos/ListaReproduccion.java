package es.upsa.programacion.modelos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lista de reproducción que puede mezclar canciones y podcasts.
 */
public class ListaReproduccion {
    public enum TipoItem {
        CANCION, PODCAST
    }

    public static class ItemRef {
        private final TipoItem tipo;
        private final int refId;

        public ItemRef(TipoItem tipo, int refId) {
            this.tipo = tipo;
            this.refId = refId;
        }

        public TipoItem getTipo() {
            return tipo;
        }

        public int getRefId() {
            return refId;
        }
    }

    private final int id;
    private String nombre;
    private final int ownerId;
    private final List<ItemRef> items;
    private final Set<String> indiceUnico;

    public ListaReproduccion(int id, String nombre, int ownerId) {
        this.id = id;
        this.nombre = nombre;
        this.ownerId = ownerId;
        this.items = new ArrayList<>();
        this.indiceUnico = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public List<ItemRef> getItems() {
        return new ArrayList<>(items);
    }

    public boolean addItem(TipoItem tipo, int refId) {
        String clave = tipo + "-" + refId;
        if (indiceUnico.contains(clave)) {
            return false;
        }
        items.add(new ItemRef(tipo, refId));
        indiceUnico.add(clave);
        return true;
    }

    public boolean removeItem(TipoItem tipo, int refId) {
        String clave = tipo + "-" + refId;
        for (int i = 0; i < items.size(); i++) {
            ItemRef ref = items.get(i);
            if (ref.getTipo() == tipo && ref.getRefId() == refId) {
                items.remove(i);
                indiceUnico.remove(clave);
                return true;
            }
        }
        return false;
    }
}
