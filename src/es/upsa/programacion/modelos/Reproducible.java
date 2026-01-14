package es.upsa.programacion.modelos;

/**
 * Contrato mínimo para elementos que pueden reproducirse en el CLI.
 */
public interface Reproducible {
    String play();
    String pause();
}
