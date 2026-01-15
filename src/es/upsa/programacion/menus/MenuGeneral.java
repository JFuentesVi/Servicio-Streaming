package es.upsa.programacion.menus;

import java.util.Scanner;

import es.upsa.programacion.controladores.ControladorUsuario;
import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.modelos.Usuario;

public class MenuGeneral {
    private final Scanner scanner;
    private final ControladorUsuario controlUsuario;
    public boolean terminar = false;

    public MenuGeneral(Scanner scanner, ControladorUsuario controlUsuario) {
        this.scanner = scanner;
        this.controlUsuario = controlUsuario;
    }

    public Usuario mostrar() {
        System.out.println("\n1) Crear cuenta");
        System.out.println("2) Iniciar sesión");
        System.out.println("0) Salir");
        String opcion = scanner.nextLine().trim();
        switch (opcion) {
            case "1":
                return registrarUsuario();
            case "2":
                return login();
            case "0":
                System.out.println("\nSaliendo...");
                terminar = true;
                return null;
            default:
                System.out.println("\nOpción no válida");
                return null;
        }
    }

    private Usuario registrarUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();
        Usuario u = controlUsuario.registrar(nombre, nombreUsuario, pass, Rol.USER);
        if (u == null) {
            System.out.println("\nEse nombre de usuario ya existe.");
        } else {
            System.out.println("\nCuenta creada. Ya puedes iniciar sesión.");
        }
        return null;
    }

    private Usuario login() {
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String contrasenna = scanner.nextLine().trim();
        Usuario u = controlUsuario.login(nombreUsuario, contrasenna);
        if (u == null) {
            System.out.println("\nCredenciales incorrectas");
        } else {
            System.out.println("\nBienvenido " + u.getNombre() + " (" + u.getRol() + ")");
        }
        return u;
    }
}
