package es.upsa.programacion.modelos;

public class Usuario {
    private final int id;
    private String nombre;
    private String nombreUsuario;
    private String passwordPlano;
    private Rol rol;

    public Usuario(int id, String nombre, String nombreUsuario, String passwordPlano, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.passwordPlano = passwordPlano;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return passwordPlano;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean validarPassword(String password) {
        return this.passwordPlano.equals(password);
    }
}
