package es.upsa.programacion.modelos;

public class Usuario {
    private final int id;
    private String nombre;
    private String nombreUsuario;
    private String contrasenna;
    private Rol rol;

    public Usuario(int id, String nombre, String nombreUsuario, String contrasenna, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contrasenna = contrasenna;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public Rol getRol() {
        return rol;
    }

    public boolean validarContrasenna(String contrasenna) {
        return this.contrasenna.equals(contrasenna);
    }
}
