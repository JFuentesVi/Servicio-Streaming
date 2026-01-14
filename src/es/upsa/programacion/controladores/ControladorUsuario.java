package es.upsa.programacion.controladores;

import java.util.HashMap;
import java.util.Map;

import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.modelos.Usuario;
import es.upsa.programacion.servicios.PersistenciaJSON;

/**
 * Gestión básica de usuarios en memoria con autenticación simple y persistencia.
 */
public class ControladorUsuario {
    private final Map<String, Usuario> usuariosPorEmail;
    private int secuenciaId = 1;
    private PersistenciaJSON persistencia;

    public ControladorUsuario(PersistenciaJSON persistencia) {
        this.persistencia = persistencia;
        this.usuariosPorEmail = new HashMap<>(persistencia.cargarUsuarios());
        // Calcular secuencia desde datos cargados
        for (Usuario u : usuariosPorEmail.values()) {
            if (u.getId() >= secuenciaId) {
                secuenciaId = u.getId() + 1;
            }
        }
        // Se crea un admin por defecto si no existe
        if (!usuariosPorEmail.containsKey("admin@cli")) {
            registrar("admin", "admin@cli", "admin", Rol.ADMIN);
        }
    }

    public Usuario registrar(String nombre, String email, String password, Rol rol) {
        if (usuariosPorEmail.containsKey(email)) {
            return null;
        }
        Usuario u = new Usuario(secuenciaId++, nombre, email, password, rol);
        usuariosPorEmail.put(email, u);
        persistencia.guardarUsuarios(usuariosPorEmail);
        return u;
    }

    public Usuario login(String email, String password) {
        Usuario u = usuariosPorEmail.get(email);
        if (u != null && u.validarPassword(password)) {
            return u;
        }
        return null;
    }
}
