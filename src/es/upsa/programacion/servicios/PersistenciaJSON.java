package es.upsa.programacion.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import es.upsa.programacion.modelos.Cancion;
import es.upsa.programacion.modelos.ListaReproduccion;
import es.upsa.programacion.modelos.Podcast;
import es.upsa.programacion.modelos.Rol;
import es.upsa.programacion.modelos.Usuario;

// Guarda y carga datos en formato JSON

public class PersistenciaJSON {
    private static final String DIR_DATOS = "datos";
    private static final String CANCIONES_FILE = DIR_DATOS + "/canciones.json";
    private static final String PODCASTS_FILE = DIR_DATOS + "/podcasts.json";
    private static final String USUARIOS_FILE = DIR_DATOS + "/usuarios.json";
    private static final String LISTAS_FILE = DIR_DATOS + "/listas.json";

    public PersistenciaJSON() {
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
        try {
            JSONArray arr = new JSONArray();
            for (Cancion cancion : canciones) {
                JSONObject obj = new JSONObject();
                obj.put("id", cancion.getId());
                obj.put("titulo", cancion.getTitulo());
                obj.put("artista", cancion.getArtista());
                obj.put("album", cancion.getAlbum());
                obj.put("duracionSeg", cancion.getDuracionSeg());
                obj.put("genero", cancion.getGenero());
                obj.put("anno", cancion.getAnno());
                obj.put("rutaArchivo", cancion.getRutaArchivo());
                arr.put(obj);
            }
            escribirArchivo(CANCIONES_FILE, arr.toString(2));
        } catch (IOException e) {
            System.err.println("Error guardando canciones: " + e.getMessage());
        }
    }

    public List<Cancion> cargarCanciones() {
        List<Cancion> resultado = new ArrayList<>();
        String contenido = leerArchivo(CANCIONES_FILE);
        if (contenido == null) {
            return resultado;
        }
        try {
            JSONArray arr = new JSONArray(contenido);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Cancion cancion = new Cancion(
                    obj.getInt("id"),
                    obj.getString("titulo"),
                    obj.getString("artista"),
                    obj.getString("album"),
                    obj.getInt("duracionSeg"),
                    obj.getString("genero"),
                    obj.getInt("anno"),
                    obj.optString("rutaArchivo", null)
                );
                resultado.add(cancion);
            }
        } catch (Exception e) {
            System.err.println("Error cargando canciones: " + e.getMessage());
        }
        return resultado;
    }

    // Podcasts

    public void guardarPodcasts(List<Podcast> podcasts) {
        try {
            JSONArray arr = new JSONArray();
            for (Podcast podcast : podcasts) {
                JSONObject obj = new JSONObject();
                obj.put("id", podcast.getId());
                obj.put("titulo", podcast.getTitulo());
                obj.put("anfitrion", podcast.getAnfitrion());
                obj.put("categoria", podcast.getCategoria());
                obj.put("descripcion", podcast.getDescripcion());
                obj.put("duracionSeg", podcast.getDuracionSeg());
                obj.put("genero", podcast.getGenero());
                obj.put("anno", podcast.getAnno());
                obj.put("rutaArchivo", podcast.getRutaArchivo());
                arr.put(obj);
            }
            escribirArchivo(PODCASTS_FILE, arr.toString(2));
        } catch (IOException e) {
            System.err.println("Error guardando podcasts: " + e.getMessage());
        }
    }

    public List<Podcast> cargarPodcasts() {
        List<Podcast> resultado = new ArrayList<>();
        String contenido = leerArchivo(PODCASTS_FILE);
        if (contenido == null) {
            return resultado;
        }
        try {
            JSONArray arr = new JSONArray(contenido);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Podcast p = new Podcast(
                    obj.getInt("id"),
                    obj.getString("titulo"),
                    obj.getString("anfitrion"),
                    obj.getString("categoria"),
                    obj.getString("descripcion"),
                    obj.getInt("duracionSeg"),
                    obj.getString("genero"),
                    obj.getInt("anno"),
                    obj.optString("rutaArchivo", null)
                );
                resultado.add(p);
            }
        } catch (Exception e) {
            System.err.println("Error cargando podcasts: " + e.getMessage());
        }
        return resultado;
    }

    // Usuarios

    public void guardarUsuarios(Map<String, Usuario> usuarios) {
        try {
            JSONObject obj = new JSONObject();
            for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
                Usuario usuario = entry.getValue();
                JSONObject usuarioObj = new JSONObject();
                usuarioObj.put("id", usuario.getId());
                usuarioObj.put("nombre", usuario.getNombre());
                usuarioObj.put("nombreUsuario", usuario.getNombreUsuario());
                usuarioObj.put("passwordPlano", usuario.getPassword());
                usuarioObj.put("rol", usuario.getRol().toString());
                obj.put(entry.getKey(), usuarioObj);
            }
            escribirArchivo(USUARIOS_FILE, obj.toString(2));
        } catch (IOException e) {
            System.err.println("Error guardando usuarios: " + e.getMessage());
        }
    }

    public Map<String, Usuario> cargarUsuarios() {
        Map<String, Usuario> resultado = new HashMap<>();
        String contenido = leerArchivo(USUARIOS_FILE);
        if (contenido == null) {
            return resultado;
        }
        try {
            JSONObject obj = new JSONObject(contenido);
            for (String nombreUsuario : obj.keySet()) {
                JSONObject usuarioObj = obj.getJSONObject(nombreUsuario);
                Usuario usuario = new Usuario(
                    usuarioObj.getInt("id"),
                    usuarioObj.getString("nombre"),
                    usuarioObj.getString("nombreUsuario"),
                    usuarioObj.getString("passwordPlano"),
                    Rol.valueOf(usuarioObj.getString("rol"))
                );
                resultado.put(nombreUsuario, usuario);
            }
        } catch (Exception e) {
            System.err.println("Error cargando usuarios: " + e.getMessage());
        }
        return resultado;
    }

    // Listas

    public void guardarListas(Map<Integer, ListaReproduccion> listas) {
        try {
            JSONObject obj = new JSONObject();
            for (Map.Entry<Integer, ListaReproduccion> entry : listas.entrySet()) {
                ListaReproduccion lista = entry.getValue();
                JSONObject listaObj = new JSONObject();
                listaObj.put("id", lista.getId());
                listaObj.put("nombre", lista.getNombre());
                listaObj.put("ownerId", lista.getOwnerId());
                
                JSONArray itemsArr = new JSONArray();
                for (ListaReproduccion.ItemRef referencia : lista.getItems()) {
                    JSONObject refObj = new JSONObject();
                    refObj.put("tipo", referencia.getTipo().toString());
                    refObj.put("refId", referencia.getRefId());
                    itemsArr.put(refObj);
                }
                listaObj.put("items", itemsArr);
                obj.put(String.valueOf(entry.getKey()), listaObj);
            }
            escribirArchivo(LISTAS_FILE, obj.toString(2));
        } catch (IOException e) {
            System.err.println("Error guardando listas: " + e.getMessage());
        }
    }

    public Map<Integer, ListaReproduccion> cargarListas() {
        Map<Integer, ListaReproduccion> resultado = new HashMap<>();
        String contenido = leerArchivo(LISTAS_FILE);
        if (contenido == null) {
            return resultado;
        }
        try {
            JSONObject obj = new JSONObject(contenido);
            for (String clave : obj.keySet()) {
                JSONObject listaObj = obj.getJSONObject(clave);
                int id = listaObj.getInt("id");
                String nombre = listaObj.getString("nombre");
                int ownerId = listaObj.getInt("ownerId");
                
                ListaReproduccion lista = new ListaReproduccion(id, nombre, ownerId);
                
                JSONArray itemsArr = listaObj.getJSONArray("items");
                for (int i = 0; i < itemsArr.length(); i++) {
                    JSONObject refObj = itemsArr.getJSONObject(i);
                    ListaReproduccion.TipoItem tipo = ListaReproduccion.TipoItem.valueOf(refObj.getString("tipo"));
                    int refId = refObj.getInt("refId");
                    lista.addItem(tipo, refId);
                }
                
                resultado.put(id, lista);
            }
        } catch (Exception e) {
            System.err.println("Error cargando listas: " + e.getMessage());
        }
        return resultado;
    }

    // Metodos de lectura/escritura
    private String leerArchivo(String ruta) {
        File file = new File(ruta);
        if (!file.exists()) {
            return null;
        }
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, len);
            }
            return sb.toString();
        } catch (IOException e) {
            System.err.println("Error leyendo archivo " + ruta + ": " + e.getMessage());
            return null;
        }
    }

    private void escribirArchivo(String ruta, String contenido) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(ruta), StandardCharsets.UTF_8)) {
            writer.write(contenido);
            writer.flush();
        }
    }
}
