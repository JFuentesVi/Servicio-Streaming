package es.upsa.programacion;

import java.util.List;
import java.util.Scanner;

import es.upsa.programacion.controladores.ControladorCancion;
import es.upsa.programacion.controladores.ControladorLista;
import es.upsa.programacion.controladores.ControladorPlayer;
import es.upsa.programacion.controladores.ControladorPodcast;
import es.upsa.programacion.controladores.ControladorUsuario;
import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.modelos.Usuario;
import es.upsa.programacion.servicios.PersistenciaJSON;

/**
 * CLI de streaming simplificado: gestiona usuarios, catálogos, listas y una
 * cola de reproducción con persistencia JSON. Se prioriza claridad sobre robustez.
 */
public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final PersistenciaJSON persistencia = new PersistenciaJSON();
    private final ControladorCancion ctrlCancion = new ControladorCancion(persistencia);
    private final ControladorPodcast ctrlPodcast = new ControladorPodcast(persistencia);
    private final ControladorUsuario ctrlUsuario = new ControladorUsuario(persistencia);
    private final ControladorLista ctrlLista = new ControladorLista(persistencia);
    private final ControladorPlayer ctrlPlayer = new ControladorPlayer(ctrlCancion, ctrlPodcast);

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("=== CLI Streaming Música/Podcast ===");
        Usuario sesion = null;
        while (true) {
            if (sesion == null) {
                sesion = menuPublico();
            } else {
                if (sesion.getRol() == Rol.ADMIN) {
                    sesion = menuAdmin(sesion);
                } else {
                    sesion = menuUsuario(sesion);
                }
            }
        }
    }

    private Usuario menuPublico() {
        System.out.println("1) Crear cuenta  2) Iniciar sesión  0) Salir");
        String opt = scanner.nextLine();
        switch (opt) {
            case "1":
                return registrarUsuario();
            case "2":
                return login();
            case "0":
                System.out.println("Adiós");
                System.exit(0);
            default:
                System.out.println("Opción no válida");
                return null;
        }
    }

    private Usuario registrarUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        Usuario u = ctrlUsuario.registrar(nombre, email, pass, Rol.USER);
        if (u == null) {
            System.out.println("Email ya registrado");
        } else {
            System.out.println("Cuenta creada. Inicia sesión.");
        }
        return null;
    }

    private Usuario login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        Usuario u = ctrlUsuario.login(email, pass);
        if (u == null) {
            System.out.println("Credenciales incorrectas");
        } else {
            System.out.println("Bienvenido " + u.getNombre() + " (" + u.getRol() + ")");
        }
        return u;
    }

    private Usuario menuUsuario(Usuario u) {
        System.out.println("\n-- Menú Usuario --");
        System.out.println("1) Buscar canción  2) Buscar podcast  3) Listar catálogos  4) Crear lista");
        System.out.println("5) Ver mis listas  6) Añadir item a lista  7) Reproducir lista");
        System.out.println("8) Controles (siguiente/anterior/pausa)  9) Escuchar transmisión  0) Cerrar sesión");
        String opt = scanner.nextLine();
        switch (opt) {
            case "1":
                buscarCancionAvanzada();
                break;
            case "2":
                buscarPodcastAvanzada();
                break;
            case "3":
                listarCatalogos();
                break;
            case "4":
                crearLista(u);
                break;
            case "5":
                verListas(u);
                break;
            case "6":
                anadirItemLista(u);
                break;
            case "7":
                reproducirLista(u);
                break;
            case "8":
                controlesPlayer();
                break;
            case "9":
                escucharTransmision();
                break;
            case "0":
                return null;
            default:
                System.out.println("Opción no válida");
        }
        return u;
    }

    private Usuario menuAdmin(Usuario u) {
        System.out.println("\n-- Menú Admin --");
        System.out.println("1) Alta canción  2) Alta podcast  3) Mod canción  4) Del canción");
        System.out.println("5) Mod podcast  6) Del podcast  7) Transmitir por id  8) Menú usuario  9) Cerrar sesión");
        String opt = scanner.nextLine();
        switch (opt) {
            case "1":
                altaCancion();
                break;
            case "2":
                altaPodcast();
                break;
            case "3":
                modCancion();
                break;
            case "4":
                delCancion();
                break;
            case "5":
                modPodcast();
                break;
            case "6":
                delPodcast();
                break;
            case "7":
                transmitir();
                break;
            case "8":
                menuUsuario(u);
                break;
            case "9":
                return null;
            default:
                System.out.println("Opción no válida");
        }
        return u;
    }

    private void altaCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Título: ");
        String t = scanner.nextLine();
        System.out.print("Artista: ");
        String art = scanner.nextLine();
        System.out.print("Álbum: ");
        String alb = scanner.nextLine();
        System.out.print("Duración seg: ");
        int dur = Integer.parseInt(scanner.nextLine());
        System.out.print("Género: ");
        String gen = scanner.nextLine();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine());
        boolean ok = ctrlCancion.crearCancion(id, t, art, alb, dur, gen, anno);
        System.out.println(ok ? "Canción creada" : "Id duplicado");
    }

    private void altaPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Título: ");
        String t = scanner.nextLine();
        System.out.print("Anfitrión: ");
        String a = scanner.nextLine();
        System.out.print("Categoría: ");
        String c = scanner.nextLine();
        System.out.print("Descripción: ");
        String d = scanner.nextLine();
        System.out.print("Duración seg: ");
        int dur = Integer.parseInt(scanner.nextLine());
        System.out.print("Género: ");
        String g = scanner.nextLine();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine());
        boolean ok = ctrlPodcast.crearPodcast(id, t, a, c, d, dur, g, anno);
        System.out.println(ok ? "Podcast creado" : "Id duplicado");
    }

    private void modCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nuevo título: ");
        String t = scanner.nextLine();
        System.out.print("Nuevo artista: ");
        String art = scanner.nextLine();
        System.out.print("Nuevo álbum: ");
        String alb = scanner.nextLine();
        System.out.print("Duración seg: ");
        int dur = Integer.parseInt(scanner.nextLine());
        System.out.print("Género: ");
        String gen = scanner.nextLine();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine());
        int res = ctrlCancion.modificarCancion(id, t, art, alb, dur, gen, anno);
        System.out.println(res == 1 ? "Actualizada" : "No encontrada");
    }

    private void delCancion() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine());
        int res = ctrlCancion.eliminarCancion(id);
        System.out.println(res == 1 ? "Eliminada" : "No encontrada");
    }

    private void modPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nuevo título: ");
        String t = scanner.nextLine();
        System.out.print("Nuevo anfitrión: ");
        String a = scanner.nextLine();
        System.out.print("Categoría: ");
        String c = scanner.nextLine();
        System.out.print("Descripción: ");
        String d = scanner.nextLine();
        System.out.print("Duración seg: ");
        int dur = Integer.parseInt(scanner.nextLine());
        System.out.print("Género: ");
        String g = scanner.nextLine();
        System.out.print("Año: ");
        int anno = Integer.parseInt(scanner.nextLine());
        int res = ctrlPodcast.modificarPodcast(id, t, a, c, d, dur, g, anno);
        System.out.println(res == 1 ? "Actualizado" : "No encontrado");
    }

    private void delPodcast() {
        System.out.print("Id: ");
        int id = Integer.parseInt(scanner.nextLine());
        int res = ctrlPodcast.eliminarPodcast(id);
        System.out.println(res == 1 ? "Eliminado" : "No encontrado");
    }

    private void buscarCancionAvanzada() {
        System.out.println("Buscar por: 1) Id  2) Título  3) Artista  4) Álbum  5) Género  6) Año");
        String opt = scanner.nextLine();
        switch (opt) {
            case "1":
                System.out.print("Id: ");
                int id = Integer.parseInt(scanner.nextLine());
                var c = ctrlCancion.buscarCancion(id);
                System.out.println(c == null ? "No encontrada" : c);
                break;
            case "2":
                System.out.print("Título: ");
                String t = scanner.nextLine();
                mostrarListaCanciones(ctrlCancion.buscarPorTitulo(t));
                break;
            case "3":
                System.out.print("Artista: ");
                String a = scanner.nextLine();
                mostrarListaCanciones(ctrlCancion.buscarPorArtista(a));
                break;
            case "4":
                System.out.print("Álbum: ");
                String alb = scanner.nextLine();
                mostrarListaCanciones(ctrlCancion.buscarPorAlbum(alb));
                break;
            case "5":
                System.out.print("Género: ");
                String g = scanner.nextLine();
                mostrarListaCanciones(ctrlCancion.buscarPorGenero(g));
                break;
            case "6":
                System.out.print("Año: ");
                int anno = Integer.parseInt(scanner.nextLine());
                mostrarListaCanciones(ctrlCancion.buscarPorAnno(anno));
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private void buscarPodcastAvanzada() {
        System.out.println("Buscar por: 1) Id  2) Título  3) Anfitrión  4) Categoría  5) Género  6) Año");
        String opt = scanner.nextLine();
        switch (opt) {
            case "1":
                System.out.print("Id: ");
                int id = Integer.parseInt(scanner.nextLine());
                var p = ctrlPodcast.buscarPodcast(id);
                System.out.println(p == null ? "No encontrado" : p);
                break;
            case "2":
                System.out.print("Título: ");
                String t = scanner.nextLine();
                mostrarListaPodcasts(ctrlPodcast.buscarPorTitulo(t));
                break;
            case "3":
                System.out.print("Anfitrión: ");
                String a = scanner.nextLine();
                mostrarListaPodcasts(ctrlPodcast.buscarPorAnfitrion(a));
                break;
            case "4":
                System.out.print("Categoría: ");
                String cat = scanner.nextLine();
                mostrarListaPodcasts(ctrlPodcast.buscarPorCategoria(cat));
                break;
            case "5":
                System.out.print("Género: ");
                String g = scanner.nextLine();
                mostrarListaPodcasts(ctrlPodcast.buscarPorGenero(g));
                break;
            case "6":
                System.out.print("Año: ");
                int anno = Integer.parseInt(scanner.nextLine());
                mostrarListaPodcasts(ctrlPodcast.buscarPorAnno(anno));
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private void listarCatalogos() {
        System.out.println("1) Listar canciones  2) Listar podcasts");
        String opt = scanner.nextLine();
        if ("1".equals(opt)) {
            mostrarListaCanciones(ctrlCancion.getTodas());
        } else if ("2".equals(opt)) {
            mostrarListaPodcasts(ctrlPodcast.getTodos());
        }
    }

    private void mostrarListaCanciones(List<es.upsa.programacion.modelos.Cancion> canciones) {
        if (canciones.isEmpty()) {
            System.out.println("Sin resultados");
            return;
        }
        for (var c : canciones) {
            System.out.println(c);
        }
    }

    private void mostrarListaPodcasts(List<es.upsa.programacion.modelos.Podcast> podcasts) {
        if (podcasts.isEmpty()) {
            System.out.println("Sin resultados");
            return;
        }
        for (var p : podcasts) {
            System.out.println(p);
        }
    }

    private void crearLista(Usuario u) {
        System.out.print("Nombre lista: ");
        String nombre = scanner.nextLine();
        var lista = ctrlLista.crearLista(nombre, u.getId());
        System.out.println("Lista creada con id " + lista.getId());
    }

    private void verListas(Usuario u) {
        List<ListaReproduccion> listas = ctrlLista.obtenerPorUsuario(u.getId());
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
        int listaId = Integer.parseInt(scanner.nextLine());
        var lista = ctrlLista.obtener(listaId);
        if (lista == null || lista.getOwnerId() != u.getId()) {
            System.out.println("Lista no válida");
            return;
        }
        System.out.print("Tipo (C para canción, P para podcast): ");
        String tipo = scanner.nextLine();
        System.out.print("Id item: ");
        int itemId = Integer.parseInt(scanner.nextLine());
        ListaReproduccion.TipoItem t = "C".equalsIgnoreCase(tipo)
                ? ListaReproduccion.TipoItem.CANCION
                : ListaReproduccion.TipoItem.PODCAST;
        
        // Validar existencia antes de añadir
        if (t == ListaReproduccion.TipoItem.CANCION && ctrlCancion.buscarCancion(itemId) == null) {
            System.out.println("Canción no existe");
            return;
        }
        if (t == ListaReproduccion.TipoItem.PODCAST && ctrlPodcast.buscarPodcast(itemId) == null) {
            System.out.println("Podcast no existe");
            return;
        }
        
        boolean ok = lista.addItem(t, itemId);
        if (ok) {
            ctrlLista.guardarCambios();
        }
        System.out.println(ok ? "Añadido" : "Duplicado");
    }

    private void reproducirLista(Usuario u) {
        System.out.print("Id lista: ");
        int listaId = Integer.parseInt(scanner.nextLine());
        var lista = ctrlLista.obtener(listaId);
        if (lista == null || (lista.getOwnerId() != u.getId() && u.getRol() != Rol.ADMIN)) {
            System.out.println("Sin acceso");
            return;
        }
        String res = ctrlPlayer.reproducirLista(lista.getItems());
        System.out.println(res);
    }

    private void controlesPlayer() {
        System.out.println("a) Pausa  b) Reanudar  c) Siguiente  d) Anterior");
        String opt = scanner.nextLine();
        String msg;
        switch (opt.toLowerCase()) {
            case "a":
                msg = ctrlPlayer.pausar();
                break;
            case "b":
                msg = ctrlPlayer.reanudar();
                break;
            case "c":
                msg = ctrlPlayer.siguiente();
                break;
            case "d":
                msg = ctrlPlayer.anterior();
                break;
            default:
                msg = "Opción no válida";
        }
        System.out.println(msg);
    }

    private void transmitir() {
        System.out.print("Tipo (C/P): ");
        String tipo = scanner.nextLine();
        System.out.print("Id item: ");
        int id = Integer.parseInt(scanner.nextLine());
        ListaReproduccion.TipoItem t = "C".equalsIgnoreCase(tipo)
                ? ListaReproduccion.TipoItem.CANCION
                : ListaReproduccion.TipoItem.PODCAST;
        
        // Validar existencia antes de transmitir
        if (t == ListaReproduccion.TipoItem.CANCION && ctrlCancion.buscarCancion(id) == null) {
            System.out.println("Canción no existe");
            return;
        }
        if (t == ListaReproduccion.TipoItem.PODCAST && ctrlPodcast.buscarPodcast(id) == null) {
            System.out.println("Podcast no existe");
            return;
        }
        
        ctrlPlayer.transmitir(new ListaReproduccion.ItemRef(t, id));
        System.out.println("Transmisión preparada");
    }

    private void escucharTransmision() {
        var ref = ctrlPlayer.obtenerTransmision();
        if (ref == null) {
            System.out.println("No hay transmisión activa");
            return;
        }
        String res = ctrlPlayer.reproducirItem(ref);
        System.out.println("Escuchando emisión: " + res);
    }
}
