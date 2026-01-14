package es.upsa.programacion.controladores;

import java.util.HashMap;
import java.util.Map;

import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.modelos.Usuario;
import es.upsa.programacion.servicios.PersistenciaJSON;

public class ControladorUsuario {
    private final Map<String, Usuario> usuariosPorNombre;
    private int secuenciaId = 1;
    private PersistenciaJSON persistencia;

    public ControladorUsuario(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.usuariosPorNombre = new HashMap<>(persistencia.cargarUsuarios());
        for (Usuario u : usuariosPorNombre.values()) {
            if (u.getId() >= secuenciaId) {
                secuenciaId = u.getId() + 1;
            }
        }
        if (!usuariosPorNombre.containsKey("admin")) {
            registrar("admin", "admin", "admin", Rol.ADMIN);
        }
    }

    // Métodos para registrar y autenticar usuarios

    public Usuario registrar(String nombre, String nombreUsuario, String password, Rol rol) {
        if (usuariosPorNombre.containsKey(nombreUsuario)) {
            return null;
        }
        Usuario u = new Usuario(secuenciaId++, nombre, nombreUsuario, password, rol);
        usuariosPorNombre.put(nombreUsuario, u);
        persistencia.guardarUsuarios(usuariosPorNombre);
        return u;
    }

    public Usuario login(String nombreUsuario, String password) {
        Usuario u = usuariosPorNombre.get(nombreUsuario);
        if (u != null && u.validarPassword(password)) {
            return u;
        }
        return null;
    }
}
