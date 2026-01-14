package es.upsa.programacion.servicios;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Reproduce archivos de audio (WAV, AU, AIFF) usando Java Sound API.
 * Usa AudioSystem y AudioInputStream para cargar y reproducir archivos.
 */

public class ReproductorAudio {
    private Clip clip;
    private String archivoActual;
    private Long pausaEnPosicion;
    private volatile boolean estaReproduciendo = false;

    public void reproducir(String rutaArchivo) {
        detener();
        this.archivoActual = rutaArchivo;
        this.pausaEnPosicion = null;

        try {
            File audioFile = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    estaReproduciendo = false;
                }
            });
            
            clip.start();
            estaReproduciendo = true;
            System.out.println("▶ Reproduciendo: " + rutaArchivo);
            
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Formato de audio no soportado: " + e.getMessage());
            System.err.println("Soportados: WAV, AU, AIFF");
        } catch (IOException e) {
            System.err.println("Error de lectura del archivo: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Línea de audio no disponible: " + e.getMessage());
        }
    }

    public void pausar() {
        if (clip != null && clip.isRunning()) {
            pausaEnPosicion = clip.getMicrosecondPosition();
            clip.stop();
            estaReproduciendo = false;
            System.out.println("⏸ Pausado en: " + (pausaEnPosicion / 1000000) + "s");
        }
    }

    public void reanudar() {
        if (clip != null && pausaEnPosicion != null) {
            clip.setMicrosecondPosition(pausaEnPosicion);
            clip.start();
            estaReproduciendo = true;
            System.out.println("▶ Reanudado desde: " + (pausaEnPosicion / 1000000) + "s");
            pausaEnPosicion = null;
        }
    }

    public void detener() {
        if (clip != null) {
            clip.stop();
            clip.close();
            estaReproduciendo = false;
        }
        pausaEnPosicion = null;
    }

    public boolean estaReproduciendo() {
        return clip != null && estaReproduciendo;
    }

    public boolean estaPausado() {
        return pausaEnPosicion != null;
    }

    public String getArchivoActual() {
        return archivoActual;
    }

    public long getPosicionActual() {
        if (clip != null) {
            return clip.getMicrosecondPosition() / 1000000; // Convertir a segundos
        }
        return 0;
    }

    public long getDuracionTotal() {
        if (clip != null) {
            return clip.getMicrosecondLength() / 1000000; // Convertir a segundos
        }
        return 0;
    }
}
