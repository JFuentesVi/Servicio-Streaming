package es.upsa.programacion.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import es.upsa.programacion.controladores.ControladorCancion;
import es.upsa.programacion.controladores.ControladorLista;
import es.upsa.programacion.controladores.ControladorPodcast;
import es.upsa.programacion.controladores.ControladorReproductor;
import es.upsa.programacion.modelos.Cancion;
import es.upsa.programacion.modelos.ItemMultimedia;
import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.Podcast;
import es.upsa.programacion.modelos.Usuario;

public class MenuUsuario {
    private final Scanner scanner;
    private final ControladorCancion controlCancion;
    private final ControladorPodcast controlPodcast;
    private final ControladorLista controlLista;
    private final ControladorReproductor controlReproductor;

    public MenuUsuario(Scanner scanner, ControladorCancion controlCancion, ControladorPodcast controlPodcast,
            ControladorLista controlLista, ControladorReproductor controlReproductor) {
        this.scanner = scanner;
        this.controlCancion = controlCancion;
        this.controlPodcast = controlPodcast;
        this.controlLista = controlLista;
        this.controlReproductor = controlReproductor;
    }

    public Usuario mostrar(Usuario u) {
        System.out.println("\n-- MENÚ USUARIO --");
        System.out.println("1) Buscar canción");
        System.out.println("2) Buscar podcast");
        System.out.println("3) Listar canciones");
        System.out.println("4) Listar podcasts");
        System.out.println("5) Crear lista de reproducción");
        System.out.println("6) Ver mis listas de reproducción");
        System.out.println("7) Añadir item a lista de reproducción");
        System.out.println("8) Reproducir lista de reproducción");
        System.out.println("9) Controles (siguiente/anterior/pausa)");
        System.out.println("0) Cerrar sesión");
        String opcion = scanner.nextLine().trim();
        switch (opcion) {
            case "1":
                buscarCancion();
                break;
            case "2":
                buscarPodcast();
                break;
            case "3":
                ordenarYMostrar(controlCancion.buscarPorTitulo(""));
                break;
            case "4":
                ordenarYMostrar(controlPodcast.buscarPorTitulo(""));
                break;
            case "5":
                crearListaReproduccion(u);
                break;
            case "6":
                verListasReproduccion(u);
                break;
            case "7":
                anadirItemLista(u);
                break;
            case "8":
                reproducirLista(u);
                break;
            case "9":
                controlesReproductor();
                break;
            case "0":
                return null;
            default:
                System.out.println("Opción no válida");
        }
        return u;
    }

    private void buscarCancion() {
        System.out.println("\nBuscar por:");
        System.out.println("1) Id");
        System.out.println("2) Título");
        System.out.println("3) Autor");
        System.out.println("4) Álbum");
        System.out.println("5) Género");
        System.out.println("6) Año");
        String opcion = scanner.nextLine().trim();
        switch (opcion) {
            case "1":
                System.out.print("Id: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Cancion cancion = controlCancion.buscarPorId(id);
                if (cancion == null) {
                    System.out.println("No encontrado");
                } else {
                    System.out.println(cancion);
                }
                break;
            case "2":
                System.out.print("Título: ");
                String titulo = scanner.nextLine().trim();
                ordenarYMostrar(controlCancion.buscarPorTitulo(titulo));
                break;
            case "3":
                System.out.print("Autor: ");
                String autor = scanner.nextLine().trim();
                ordenarYMostrar(controlCancion.buscarPorAutor(autor));
                break;
            case "4":
                System.out.print("Álbum: ");
                String album = scanner.nextLine().trim();
                ordenarYMostrar(controlCancion.buscarPorAlbum(album));
                break;
            case "5":
                System.out.print("Género: ");
                String genero = scanner.nextLine().trim();
                ordenarYMostrar(controlCancion.buscarPorGenero(genero));
                break;
            case "6":
                System.out.print("Año: ");
                int anno = Integer.parseInt(scanner.nextLine().trim());
                ordenarYMostrar(controlCancion.buscarPorAnno(anno));
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private void buscarPodcast() {
        System.out.println("\nBuscar por:");
        System.out.println("1) Id");
        System.out.println("2) Título");
        System.out.println("3) Host");
        System.out.println("4) Temporada");
        System.out.println("5) Género");
        System.out.println("6) Año");
        String opcion = scanner.nextLine().trim();
        switch (opcion) {
            case "1":
                System.out.print("Id: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Podcast podcast = controlPodcast.buscarPorId(id);
                if (podcast == null) {
                    System.out.println("No encontrado");
                } else {
                    System.out.println(podcast);
                }
                break;
            case "2":
                System.out.print("Título: ");
                String titulo = scanner.nextLine().trim();
                ordenarYMostrar(controlPodcast.buscarPorTitulo(titulo));
                break;
            case "3":
                System.out.print("Host: ");
                String autor = scanner.nextLine().trim();
                ordenarYMostrar(controlPodcast.buscarPorAutor(autor));
                break;
            case "4":
                System.out.print("Temporada: ");
                String temporada = scanner.nextLine().trim();
                ordenarYMostrar(controlPodcast.buscarPorAlbum(temporada));
                break;
            case "5":
                System.out.print("Género: ");
                String genero = scanner.nextLine().trim();
                ordenarYMostrar(controlPodcast.buscarPorGenero(genero));
                break;
            case "6":
                System.out.print("Año: ");
                int anno = Integer.parseInt(scanner.nextLine().trim());
                ordenarYMostrar(controlPodcast.buscarPorAnno(anno));
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private <T extends ItemMultimedia> void ordenarYMostrar(List<T> originales) {
        if (originales.isEmpty()) {
            System.out.println("Sin resultados");
            return;
        }

        List<T> copia = new ArrayList<>(originales);
        Collections.sort(copia);

        for (T item : copia) {
            System.out.println(item);
        }
    }

    private void crearListaReproduccion(Usuario u) {
        System.out.print("Nombre lista: ");
        String nombre = scanner.nextLine().trim();
        ListaReproduccion lista = controlLista.crearLista(nombre, u.getId());
        System.out.println("Lista creada con id " + lista.getId());
    }

    private void verListasReproduccion(Usuario u) {
        List<ListaReproduccion> listas = controlLista.obtenerPorUsuario(u.getId());
        if (listas.isEmpty()) {
            System.out.println("No tienes listas");
            return;
        }
        for (ListaReproduccion l : listas) {
            System.out.println(l.getId() + " - " + l.getNombre());
        }
    }

    private void anadirItemLista(Usuario u) {
        System.out.print("Id lista: ");
        int listaId = Integer.parseInt(scanner.nextLine().trim());
        ListaReproduccion lista = controlLista.obtener(listaId);
        if (lista == null || lista.getOwnerId() != u.getId()) {
            System.out.println("Lista no válida");
            return;
        }
        System.out.print("Tipo (C para canción, P para podcast): ");
        String tipo = scanner.nextLine().trim();
        System.out.print("Id item: ");
        int itemId = Integer.parseInt(scanner.nextLine().trim());
        ListaReproduccion.TipoItem t;
        if ("C".equalsIgnoreCase(tipo)) {
            t = ListaReproduccion.TipoItem.CANCION;
        } else {
            t = ListaReproduccion.TipoItem.PODCAST;
        }

        if (t == ListaReproduccion.TipoItem.CANCION && controlCancion.buscarPorId(itemId) == null) {
            System.out.println("Canción no existe");
            return;
        }
        if (t == ListaReproduccion.TipoItem.PODCAST && controlPodcast.buscarPorId(itemId) == null) {
            System.out.println("Podcast no existe");
            return;
        }

        if (lista.addItem(t, itemId)) {
            controlLista.guardarCambios();
            System.out.println("Añadido");
        } else {
            System.out.println("Duplicado");
        }
    }

    private void reproducirLista(Usuario u) {
        System.out.print("Id lista: ");
        int listaId = Integer.parseInt(scanner.nextLine().trim());
        ListaReproduccion lista = controlLista.obtener(listaId);
        if (lista == null || lista.getOwnerId() != u.getId()) {
            System.out.println("Sin acceso");
            return;
        }
        String count = controlReproductor.reproducirLista(lista.getItems());
        System.out.println(count);
    }

    private void controlesReproductor() {
        System.out.println("1) Pausa");
        System.out.println("2) Reanudar");
        System.out.println("3) Siguiente");
        System.out.println("4) Anterior");
        System.out.println("5) Detener");
        String opcion = scanner.nextLine().trim();
        String mensaje;
        switch (opcion) {
            case "1":
                mensaje = controlReproductor.pausar();
                break;
            case "2":
                mensaje = controlReproductor.reanudar();
                break;
            case "3":
                mensaje = controlReproductor.siguiente();
                break;
            case "4":
                mensaje = controlReproductor.anterior();
                break;
            case "5":
                mensaje = controlReproductor.detener();
                break;
            default:
                mensaje = "Opción no válida";
        }
        System.out.println(mensaje);
    }
}
