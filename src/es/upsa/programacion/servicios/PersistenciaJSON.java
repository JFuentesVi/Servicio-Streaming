package es.upsa.programacion.servicios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import es.upsa.programacion.modelos.Cancion;
import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.Podcast;
import es.upsa.programacion.modelos.Usuario;

/**
 * Guarda/carga datos en JSON usando Gson para que no se pierdan entre ejecuciones.
 */
public class PersistenciaJSON {
    private static final String DIR_DATOS = "datos";
    private static final String CANCIONES_FILE = DIR_DATOS + "/canciones.json";
    private static final String PODCASTS_FILE = DIR_DATOS + "/podcasts.json";
    private static final String USUARIOS_FILE = DIR_DATOS + "/usuarios.json";
    private static final String LISTAS_FILE = DIR_DATOS + "/listas.json";

    private final Gson gson;

    public PersistenciaJSON() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        crearDirectorio();
    }

    private void crearDirectorio() {
        File dir = new File(DIR_DATOS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Canciones
    public void guardarCanciones(List<Cancion> canciones) {
        try (FileWriter writer = new FileWriter(CANCIONES_FILE)) {
            gson.toJson(canciones, writer);
        } catch (IOException e) {
            System.err.println("Error guardando canciones: " + e.getMessage());
        }
    }

    public List<Cancion> cargarCanciones() {
        File file = new File(CANCIONES_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<List<Cancion>>() {}.getType());
        } catch (IOException e) {
            System.err.println("Error cargando canciones: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Podcasts
    public void guardarPodcasts(List<Podcast> podcasts) {
        try (FileWriter writer = new FileWriter(PODCASTS_FILE)) {
            gson.toJson(podcasts, writer);
        } catch (IOException e) {
            System.err.println("Error guardando podcasts: " + e.getMessage());
        }
    }

    public List<Podcast> cargarPodcasts() {
        File file = new File(PODCASTS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<List<Podcast>>() {}.getType());
        } catch (IOException e) {
            System.err.println("Error cargando podcasts: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Usuarios
    public void guardarUsuarios(Map<String, Usuario> usuarios) {
        try (FileWriter writer = new FileWriter(USUARIOS_FILE)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.err.println("Error guardando usuarios: " + e.getMessage());
        }
    }

    public Map<String, Usuario> cargarUsuarios() {
        File file = new File(USUARIOS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<Map<String, Usuario>>() {}.getType());
        } catch (IOException e) {
            System.err.println("Error cargando usuarios: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Listas
    public void guardarListas(Map<Integer, ListaReproduccion> listas) {
        try (FileWriter writer = new FileWriter(LISTAS_FILE)) {
            gson.toJson(listas, writer);
        } catch (IOException e) {
            System.err.println("Error guardando listas: " + e.getMessage());
        }
    }

    public Map<Integer, ListaReproduccion> cargarListas() {
        File file = new File(LISTAS_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<Map<Integer, ListaReproduccion>>() {}.getType());
        } catch (IOException e) {
            System.err.println("Error cargando listas: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
