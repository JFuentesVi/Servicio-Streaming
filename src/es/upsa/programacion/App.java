package es.upsa.programacion;

import java.util.Scanner;

import es.upsa.programacion.controladores.ControladorCancion;
import es.upsa.programacion.controladores.ControladorLista;
import es.upsa.programacion.controladores.ControladorReproductor;
import es.upsa.programacion.controladores.ControladorPodcast;
import es.upsa.programacion.controladores.ControladorUsuario;
import es.upsa.programacion.menus.MenuAdmin;
import es.upsa.programacion.menus.MenuGeneral;
import es.upsa.programacion.menus.MenuUsuario;
import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.modelos.Usuario;
import es.upsa.programacion.servicios.PersistenciaJSON;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersistenciaJSON persistencia = new PersistenciaJSON();
        ControladorCancion controlCancion = new ControladorCancion(persistencia);
        ControladorPodcast controlPodcast = new ControladorPodcast(persistencia);
        ControladorUsuario controlUsuario = new ControladorUsuario(persistencia);
        ControladorLista controlLista = new ControladorLista(persistencia);
        ControladorReproductor controlReproductor = new ControladorReproductor(controlCancion, controlPodcast);
        MenuGeneral menuGeneral = new MenuGeneral(scanner, controlUsuario);
        MenuUsuario menuUsuario = new MenuUsuario(scanner, controlCancion, controlPodcast, controlLista,
                controlReproductor);
        MenuAdmin menuAdmin = new MenuAdmin(scanner, controlCancion, controlPodcast);

        System.out.println("App de Streaming de Música y Podcasts");
        Usuario sesion = null;
        while (!menuGeneral.terminar) {
            if (sesion == null) {
                sesion = menuGeneral.mostrar();
            } else {
                if (sesion.getRol() == Rol.ADMIN) {
                    sesion = menuAdmin.mostrar(sesion, menuUsuario);
                } else {
                    sesion = menuUsuario.mostrar(sesion);
                }
            }
        }
        scanner.close();
    }
}
