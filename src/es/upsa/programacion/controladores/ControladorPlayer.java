package es.upsa.programacion.controladores;

import java.util.LinkedList;
import java.util.List;

import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.Reproducible;

/**
 * Control sencillo de reproducción con una cola y un índice actual.
 */
public class ControladorPlayer {
    public enum EstadoPlayer {
        STOPPED, PLAYING, PAUSED
    }

    private final ControladorCancion ctrlCanciones;
    private final ControladorPodcast ctrlPodcasts;
    private final LinkedList<ListaReproduccion.ItemRef> cola;
    private int indiceActual;
    private EstadoPlayer estado;
    private ListaReproduccion.ItemRef transmisionActual;

    public ControladorPlayer(ControladorCancion ctrlCanciones, ControladorPodcast ctrlPodcasts) {
        this.ctrlCanciones = ctrlCanciones;
        this.ctrlPodcasts = ctrlPodcasts;
        this.cola = new LinkedList<>();
        this.indiceActual = -1;
        this.estado = EstadoPlayer.STOPPED;
    }

    public String reproducirItem(ListaReproduccion.ItemRef ref) {
        cola.clear();
        cola.add(ref);
        indiceActual = 0;
        estado = EstadoPlayer.PLAYING;
        return playActual();
    }

    public String reproducirLista(List<ListaReproduccion.ItemRef> items) {
        cola.clear();
        cola.addAll(items);
        indiceActual = items.isEmpty() ? -1 : 0;
        estado = items.isEmpty() ? EstadoPlayer.STOPPED : EstadoPlayer.PLAYING;
        return playActual();
    }

    public String siguiente() {
        if (indiceActual + 1 < cola.size()) {
            indiceActual++;
            estado = EstadoPlayer.PLAYING;
            return playActual();
        }
        return "Fin de la cola";
    }

    public String anterior() {
        if (indiceActual - 1 >= 0) {
            indiceActual--;
            estado = EstadoPlayer.PLAYING;
            return playActual();
        }
        return "No hay anterior";
    }

    public String pausar() {
        if (estado == EstadoPlayer.PLAYING) {
            estado = EstadoPlayer.PAUSED;
            Reproducible rep = obtenerActual();
            return rep == null ? "Nada en reproducción" : rep.pause();
        }
        return "No se puede pausar";
    }

    public String reanudar() {
        if (estado == EstadoPlayer.PAUSED) {
            estado = EstadoPlayer.PLAYING;
            return playActual();
        }
        return "No hay nada pausado";
    }

    public String playActual() {
        Reproducible rep = obtenerActual();
        if (rep == null) {
            return "Cola vacía";
        }
        estado = EstadoPlayer.PLAYING;
        return rep.play();
    }

    private Reproducible obtenerActual() {
        if (indiceActual < 0 || indiceActual >= cola.size()) {
            return null;
        }
        ListaReproduccion.ItemRef ref = cola.get(indiceActual);
        switch (ref.getTipo()) {
            case CANCION:
                return ctrlCanciones.buscarCancion(ref.getRefId());
            case PODCAST:
                return ctrlPodcasts.buscarPodcast(ref.getRefId());
            default:
                return null;
        }
    }

    public void transmitir(ListaReproduccion.ItemRef ref) {
        this.transmisionActual = ref;
    }

    public ListaReproduccion.ItemRef obtenerTransmision() {
        return transmisionActual;
    }
}
