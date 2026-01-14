package es.upsa.programacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import es.upsa.programacion.controladores.ControladorCancion;
import es.upsa.programacion.controladores.ControladorLista;
import es.upsa.programacion.controladores.ControladorReproductor;
import es.upsa.programacion.controladores.ControladorPodcast;
import es.upsa.programacion.controladores.ControladorUsuario;
import es.upsa.programacion.modelos.Cancion;
import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.ItemMultimedia;
import es.upsa.programacion.modelos.Podcast;
import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.modelos.Usuario;
import es.upsa.programacion.servicios.PersistenciaJSON;

 // Aplicación de consola para gestión y reproducción de música y podcasts.
 // Usuario administrador por defecto es "admin" con contraseña "admin".

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final PersistenciaJSON persistencia = new PersistenciaJSON();
    private final ControladorCancion controlCancion = new ControladorCancion(persistencia);
    private final ControladorPodcast controlPodcast = new ControladorPodcast(persistencia);
    private final ControladorUsuario controlUsuario = new ControladorUsuario(persistencia);
    private final ControladorLista controlLista = new ControladorLista(persistencia);
    private final ControladorReproductor controlReproductor = new ControladorReproductor(controlCancion, controlPodcast);

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("App de Streaming de Música y Podcasts");
        Usuario sesion = null;
        while (true) {
            if (sesion == null) {
                sesion = menuGeneral();
            } else {
                if (sesion.getRol() == Rol.ADMIN) {
                    sesion = menuAdmin(sesion);
                } else {
                    sesion = menuUsuario(sesion);
                }
            }
        }
    }

    // Menú principal

    private Usuario menuGeneral() {
        System.out.println("\n1) Crear cuenta");
        System.out.println("2) Iniciar sesión");
        System.out.println("0) Salir");
        String opt = scanner.nextLine().trim();
        switch (opt) {
            case "1":
                return registrarUsuario();
            case "2":
                return login();
            case "0":
                System.out.println("\nSaliendo...");
                System.exit(0);
            default:
                System.out.println("\nOpción no válida");
                return null;
        }
    }

    // Menú para registrar

    private Usuario registrarUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();
        Usuario u = controlUsuario.registrar(nombre, nombreUsuario, pass, Rol.USER);
        if (u == null) {
            System.out.println("\nEse nombre de usuario ya existe.");
        } else {
            System.out.println("\nCuenta creada. Ya puedes iniciar sesión.");
        }
        return null;
    }

    // Menú para iniciar sesión

    private Usuario login() {
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasenna = scanner.nextLine();
        Usuario u = controlUsuario.login(nombreUsuario, contrasenna);
        if (u == null) {
            System.out.println("\nCredenciales incorrectas");
        } else {
            System.out.println("\nBienvenido " + u.getNombre() + " (" + u.getRol() + ")");
        }
        return u;
    }

    // Menú usuario

    private Usuario menuUsuario(Usuario u) {
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
        String opt = scanner.nextLine().trim();
        switch (opt) {
            case "1":
                buscarCancion();
                break;
            case "2":
                buscarPodcast();
                break;
            case "3":
                mostrarListaCanciones(controlCancion.getTodas());
                break;
            case "4":
                mostrarListaPodcasts(controlPodcast.getTodos());
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

    // Menú admin

    private Usuario menuAdmin(Usuario u) {
        System.out.println("\n-- MENU ADMIN --");
        System.out.println("1) Añadir canción");
        System.out.println("2) Modificar canción");
        System.out.println("3) Eliminar canción");
        System.out.println("4) Añadir podcast");
        System.out.println("5) Modificar podcast");
        System.out.println("6) Eliminar podcast");
        System.out.println("7) Cambiar al menú de usuario");
        System.out.println("8) Cerrar sesión");
        String opt = scanner.nextLine().trim();
        switch (opt) {
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
                menuUsuario(u);
                break;
            case "8":
                return null;
            default:
                System.out.println("Opción no válida");
        }
        return u;
    }

    // Añadir canción

    private void annadirCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Artista: ");
        String artista = scanner.nextLine();
        System.out.print("Álbum: ");
        String album = scanner.nextLine();
        System.out.print("Duración en segundos: ");
        int duracion = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Ruta del archivo (ej: datos/canciones/song.extension, vacío si no hay): ");
        String ruta = scanner.nextLine();
        String rutaFinal = null;
        if (!ruta.isEmpty()) {
            rutaFinal = ruta;
        }
        boolean exito = controlCancion.crearCancion(id, titulo, artista, album, duracion, genero, anno, rutaFinal);
        if (exito) {
            System.out.println("Canción creada");
        } else {
            System.out.println("Ya existe una canción con ese Id");
        }
    }

    // Añadir podcast

    private void annadirPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Anfitrión: ");
        String anfitrion = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Duración en segundos: ");
        int duracion = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Ruta del archivo (ej: datos/podcast/episodio.extension, vacío si no hay): ");
        String ruta = scanner.nextLine();
        String rutaFinal = null;
        if (!ruta.isEmpty()) {
            rutaFinal = ruta;
        }
        boolean exito = controlPodcast.crearPodcast(id, titulo, anfitrion, categoria, descripcion, duracion, genero, anno, rutaFinal);
        if (exito) {
            System.out.println("Podcast creado");
        } else {
            System.out.println("Ya existe un podcast con ese Id");
        }
    }

    // Modificar canción

    private void modificarCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Artista: ");
        String artista = scanner.nextLine();
        System.out.print("Álbum: ");
        String album = scanner.nextLine();
        System.out.print("Duración en segundos: ");
        int duracion = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Ruta del archivo (vacío para mantener actual): ");
        String ruta = scanner.nextLine();
        String rutaFinal = null;
        if (!ruta.isEmpty()) {
            rutaFinal = ruta;
        }
        int count = controlCancion.modificarCancion(id, titulo, artista, album, duracion, genero, anno, rutaFinal);
        if (count == 1) {
            System.out.println("Canción modificada");
        } else {
            System.out.println("No existe una canción con ese Id");
        }
    }

    // Eliminar canción

    private void eliminarCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        int count = controlCancion.eliminarCancion(id);
        if (count == 1) {
            System.out.println("Canción eliminada");
        } else {
            System.out.println("No existe una canción con ese Id");
        }
    }

    // Modificar podcast

    private void modificarPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Anfitrión: ");
        String anfitrion = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Duración en segundos: ");
        int duracion = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Ruta del archivo (vacío para mantener actual): ");
        String ruta = scanner.nextLine();
        String rutaFinal = null;
        if (!ruta.isEmpty()) {
            rutaFinal = ruta;
        }
        int count = controlPodcast.modificarPodcast(id, titulo, anfitrion, categoria, descripcion, duracion, genero, anno, rutaFinal);
        if (count == 1) {
            System.out.println("Podcast modificado");
        } else {
            System.out.println("No existe un podcast con ese Id");
        }
    }

    // Eliminar podcast

    private void eliminarPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        int count = controlPodcast.eliminarPodcast(id);
        if (count == 1) {
            System.out.println("Podcast eliminado");
        } else {
            System.out.println("No existe un podcast con ese Id");
        }
    }

    // Buscar canción

    private void buscarCancion() {
        System.out.println("\nBuscar por:");
        System.out.println("1) Id");
        System.out.println("2) Título");
        System.out.println("3) Artista");
        System.out.println("4) Álbum");
        System.out.println("5) Género");
        System.out.println("6) Año");
        String opt = scanner.nextLine().trim();
        switch (opt) {
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
                String titulo = scanner.nextLine();
                mostrarListaCanciones(controlCancion.buscarPorTitulo(titulo));
                break;
            case "3":
                System.out.print("Artista: ");
                String artista = scanner.nextLine();
                mostrarListaCanciones(controlCancion.buscarPorArtista(artista));
                break;
            case "4":
                System.out.print("Álbum: ");
                String album = scanner.nextLine();
                mostrarListaCanciones(controlCancion.buscarPorAlbum(album));
                break;
            case "5":
                System.out.print("Género: ");
                String genero = scanner.nextLine();
                mostrarListaCanciones(controlCancion.buscarPorGenero(genero));
                break;
            case "6":
                System.out.print("Año: ");
                int anno = Integer.parseInt(scanner.nextLine().trim());
                mostrarListaCanciones(controlCancion.buscarPorAnno(anno));
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    // Buscar podcast

    private void buscarPodcast() {
        System.out.println("\nBuscar por:");
        System.out.println("1) Id");
        System.out.println("2) Título");
        System.out.println("3) Anfitrión");
        System.out.println("4) Categoría");
        System.out.println("5) Género");
        System.out.println("6) Año");
        String opt = scanner.nextLine().trim();
        switch (opt) {
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
                String titulo = scanner.nextLine();
                mostrarListaPodcasts(controlPodcast.buscarPorTitulo(titulo));
                break;
            case "3":
                System.out.print("Anfitrión: ");
                String anfitrion = scanner.nextLine();
                mostrarListaPodcasts(controlPodcast.buscarPorAnfitrion(anfitrion));
                break;
            case "4":
                System.out.print("Categoría: ");
                String categoria = scanner.nextLine();
                mostrarListaPodcasts(controlPodcast.buscarPorCategoria(categoria));
                break;
            case "5":
                System.out.print("Género: ");
                String genero = scanner.nextLine();
                mostrarListaPodcasts(controlPodcast.buscarPorGenero(genero));
                break;
            case "6":
                System.out.print("Año: ");
                int anno = Integer.parseInt(scanner.nextLine().trim());
                mostrarListaPodcasts(controlPodcast.buscarPorAnno(anno));
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    // Mostrar lista de canciones ordenada por título

    private void mostrarListaCanciones(List<Cancion> canciones) {
        ordenarYMostrar(canciones);
    }

    // Mostrar lista de podcasts ordenada por título

    private void mostrarListaPodcasts(List<Podcast> podcasts) {
        ordenarYMostrar(podcasts);
    }

    // Método para ordenar y mostrar listas

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

    // Crear lista de reproducción

    private void crearListaReproduccion(Usuario u) {
        System.out.print("Nombre lista: ");
        String nombre = scanner.nextLine();
        ListaReproduccion lista = controlLista.crearLista(nombre, u.getId());
        System.out.println("Lista creada con id " + lista.getId());
    }

    // Ver listas de reproducción del usuario

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

    // Añadir item a lista de reproducción

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
        
        // Comprobar que el item existe antes de añadir

        if (t == ListaReproduccion.TipoItem.CANCION && controlCancion.buscarPorId(itemId) == null) {
            System.out.println("Canción no existe");
            return;
        }
        if (t == ListaReproduccion.TipoItem.PODCAST && controlPodcast.buscarPorId(itemId) == null) {
            System.out.println("Podcast no existe");
            return;
        }
        
        boolean exito = lista.addItem(t, itemId);
        if (exito) {
            controlLista.guardarCambios();
            System.out.println("Añadido");
        } else {
            System.out.println("Duplicado");
        }
    }

    // Reproducir lista de reproducción

    private void reproducirLista(Usuario u) {
        System.out.print("Id lista: ");
        int listaId = Integer.parseInt(scanner.nextLine().trim());
        ListaReproduccion lista = controlLista.obtener(listaId);
        if (lista == null || (lista.getOwnerId() != u.getId() && u.getRol() != Rol.ADMIN)) {
            System.out.println("Sin acceso");
            return;
        }
        String count = controlReproductor.reproducirLista(lista.getItems());
        System.out.println(count);
    }

    // Controles del reproductor
    
    private void controlesReproductor() {
        System.out.println("1) Pausa");
        System.out.println("2) Reanudar");
        System.out.println("3) Siguiente");
        System.out.println("4) Anterior");
        String opt = scanner.nextLine().trim();
        String mensaje;
        switch (opt) {
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
            default:
                mensaje = "Opción no válida";
        }
        System.out.println(mensaje);
    }


}
