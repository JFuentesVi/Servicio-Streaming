package es.upsa.programacion.menus;

import java.util.Scanner;

import es.upsa.programacion.controladores.ControladorCancion;
import es.upsa.programacion.controladores.ControladorPodcast;
import es.upsa.programacion.modelos.Usuario;

public class MenuAdmin {
    private final Scanner scanner;
    private final ControladorCancion controlCancion;
    private final ControladorPodcast controlPodcast;

    public MenuAdmin(Scanner scanner, ControladorCancion controlCancion, ControladorPodcast controlPodcast) {
        this.scanner = scanner;
        this.controlCancion = controlCancion;
        this.controlPodcast = controlPodcast;
    }

    public Usuario mostrar(Usuario u, MenuUsuario menuUsuario) {
        System.out.println("\n-- MENU ADMIN --");
        System.out.println("1) Añadir canción");
        System.out.println("2) Modificar canción");
        System.out.println("3) Eliminar canción");
        System.out.println("4) Añadir podcast");
        System.out.println("5) Modificar podcast");
        System.out.println("6) Eliminar podcast");
        System.out.println("7) Cambiar al menú de usuario");
        System.out.println("8) Cerrar sesión");
        String opcion = scanner.nextLine().trim();
        switch (opcion) {
            case "1":
                annadirCancion();
                break;
            case "2":
                modificarCancion();
                break;
            case "3":
                eliminarCancion();
                break;
            case "4":
                annadirPodcast();
                break;
            case "5":
                modificarPodcast();
                break;
            case "6":
                eliminarPodcast();
                break;
            case "7":
                menuUsuario.mostrar(u);
                break;
            case "8":
                return null;
            default:
                System.out.println("Opción no válida");
        }
        return u;
    }

    private void annadirCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Artista: ");
        String artista = scanner.nextLine().trim();
        System.out.print("Álbum: ");
        String album = scanner.nextLine().trim();
        System.out.print("Género: ");
        String genero = scanner.nextLine().trim();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine().trim());
        String rutaFinal = leerRuta();
        if (controlCancion.crearCancion(id, titulo, artista, album, genero, anno, rutaFinal)) {
            System.out.println("Canción creada");
        } else {
            System.out.println("Ya existe una canción con ese Id");
        }
    }

    private void modificarCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Artista: ");
        String artista = scanner.nextLine().trim();
        System.out.print("Álbum: ");
        String album = scanner.nextLine().trim();
        System.out.print("Género: ");
        String genero = scanner.nextLine().trim();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine().trim());
        String rutaFinal = leerRuta();
        if (controlCancion.modificarCancion(id, titulo, artista, album, genero, anno, rutaFinal)) {
            System.out.println("Canción modificada");
        } else {
            System.out.println("No existe una canción con ese Id");
        }
    }

    private void eliminarCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (controlCancion.eliminarCancion(id)) {
            System.out.println("Canción eliminada");
        } else {
            System.out.println("No existe una canción con ese Id");
        }
    }

    private void annadirPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Host: ");
        String autor = scanner.nextLine().trim();
        System.out.print("Temporada: ");
        String temporada = scanner.nextLine().trim();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();
        System.out.print("Género: ");
        String genero = scanner.nextLine().trim();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine().trim());
        String rutaFinal = leerRuta();
        if (controlPodcast.crearPodcast(id, titulo, autor, temporada, descripcion, genero,
                anno, rutaFinal)) {
            System.out.println("Podcast creado");
        } else {
            System.out.println("Ya existe un podcast con ese Id");
        }
    }

    private void modificarPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Host: ");
        String autor = scanner.nextLine().trim();
        System.out.print("Temporada: ");
        String temporada = scanner.nextLine().trim();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();
        System.out.print("Género: ");
        String genero = scanner.nextLine().trim();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine().trim());
        String rutaFinal = leerRuta();
        if (controlPodcast.modificarPodcast(id, titulo, autor, temporada, descripcion, genero,
                anno, rutaFinal)) {
            System.out.println("Podcast modificado");
        } else {
            System.out.println("No existe un podcast con ese Id");
        }
    }

    private void eliminarPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (controlPodcast.eliminarPodcast(id)) {
            System.out.println("Podcast eliminado");
        } else {
            System.out.println("No existe un podcast con ese Id");
        }
    }

    private String leerRuta() {
        System.out.print("Ruta del archivo (vacío si no hay): ");
        String ruta = scanner.nextLine().trim();
        if (ruta.isEmpty()) {
            return null;
        }
        return ruta;
    }
}
