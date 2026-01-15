package es.upsa.programacion.controladores;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.Reproducible;
import es.upsa.programacion.servicios.ReproductorAudio;

public class ControladorReproductor {
    public enum EstadoReproductor {
        STOPPED, PLAYING, PAUSED
    }

    private final ControladorCancion ctrlCanciones;
    private final ControladorPodcast ctrlPodcasts;
    private final LinkedList<ListaReproduccion.ItemRef> cola;
    private int indiceActual;
    private EstadoReproductor estado;
    private final ReproductorAudio audioPlayer;

    public ControladorReproductor(ControladorCancion ctrlCanciones, ControladorPodcast ctrlPodcasts) {
        this.ctrlCanciones = ctrlCanciones;
        this.ctrlPodcasts = ctrlPodcasts;
        this.cola = new LinkedList<>();
        this.indiceActual = -1;
        this.estado = EstadoReproductor.STOPPED;
        this.audioPlayer = new ReproductorAudio();
    }

    // Métodos para controlar la reproducción: play, pause, next, previous, cargar
    // lista, etc.

    public String reproducirItem(ListaReproduccion.ItemRef ref) {
        cola.clear();
        cola.add(ref);
        indiceActual = 0;
        estado = EstadoReproductor.PLAYING;
        return playActual();
    }

    public String reproducirLista(List<ListaReproduccion.ItemRef> items) {
        cola.clear();
        cola.addAll(items);
        if (items.isEmpty()) {
            indiceActual = -1;
            estado = EstadoReproductor.STOPPED;
        } else {
            indiceActual = 0;
            estado = EstadoReproductor.PLAYING;
        }
        return playActual();
    }

    public String siguiente() {
        if (indiceActual + 1 < cola.size()) {
            indiceActual++;
            estado = EstadoReproductor.PLAYING;
            return playActual();
        }
        return "Fin de la cola";
    }

    public String anterior() {
        if (indiceActual - 1 >= 0) {
            indiceActual--;
            estado = EstadoReproductor.PLAYING;
            return playActual();
        }
        return "No hay anterior";
    }

    public String pausar() {
        if (estado == EstadoReproductor.PLAYING) {
            estado = EstadoReproductor.PAUSED;
            audioPlayer.pausar();
            Reproducible rep = obtenerActual();
            if (rep == null) {
                return "Nada en reproducción";
            } else {
                return rep.pause();
            }
        }
        return "No se puede pausar";
    }

    public String reanudar() {
        if (estado == EstadoReproductor.PAUSED) {
            estado = EstadoReproductor.PLAYING;
            if (audioPlayer.estaPausado()) {
                audioPlayer.reanudar();
                return "▶ Reanudado";
            } else {
                return playActual();
            }
        }
        return "No hay nada pausado";
    }

    public String detener() {
        if (estado == EstadoReproductor.PLAYING || estado == EstadoReproductor.PAUSED) {
            audioPlayer.detener();
            estado = EstadoReproductor.STOPPED;
            cola.clear();
            indiceActual = -1;
            return "Reproducción detenida";
        }
        return "No hay nada en reproducción";
    }

    public String playActual() {
        Reproducible rep = obtenerActual();
        if (rep == null) {
            return "Cola vacía";
        }
        estado = EstadoReproductor.PLAYING;

        if (rep instanceof es.upsa.programacion.modelos.ItemMultimedia) {
            es.upsa.programacion.modelos.ItemMultimedia media = (es.upsa.programacion.modelos.ItemMultimedia) rep;
            String rutaArchivo = media.getRutaArchivo();
            if (rutaArchivo != null && !rutaArchivo.isEmpty()) {
                File archivo = new File(rutaArchivo);
                if (archivo.exists()) {
                    audioPlayer.reproducir(rutaArchivo);
                } else {
                    System.out.println("⚠ Archivo no encontrado: " + rutaArchivo);
                }
            }
        }

        return rep.play();
    }

    private Reproducible obtenerActual() {
        if (indiceActual < 0 || indiceActual >= cola.size()) {
            return null;
        }
        ListaReproduccion.ItemRef ref = cola.get(indiceActual);
        switch (ref.getTipo()) {
            case CANCION:
                return ctrlCanciones.buscarPorId(ref.getRefId());
            case PODCAST:
                return ctrlPodcasts.buscarPorId(ref.getRefId());
            default:
                return null;
        }
    }

}
