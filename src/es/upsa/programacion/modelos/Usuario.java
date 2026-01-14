package es.upsa.programacion.modelos;

/**
 * Usuario básico con rol para control de acceso en el CLI.
 */
public class Usuario {
    private final int id;
    private String nombre;
    private String email;
    private String passwordPlano;
    private Rol rol;

    public Usuario(int id, String nombre, String email, String passwordPlano, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
