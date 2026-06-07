# 🎵 Aplicación de Streaming de Música y Podcasts

## 📋 Descripción

Esta es una aplicación CLI en Java para gestionar y reproducir música y podcasts. Permite crear usuarios, organizar contenido multimedia, crear listas de reproducción y reproducir audio local.
No incluye reproducción automática por lo que tras acabar una canción se deberá de pasar manualmente a la siguiente.

## ✨ Funcionalidades

### 🎵 Gestión de Contenido

- Catálogo de canciones (título, artista, álbum, género, año, ruta)
- Catálogo de podcasts (título, host, temporada, descripción, género, año, ruta)
- Búsquedas unificadas por metadatos: título, autor, álbum/temporada, género, año
- Búsqueda global en todos los campos de texto
- Ordenamiento alfabético automático

### 👥 Sistema de Usuarios

- Registro con nombre de usuario y contraseña
- Autenticación con roles (ADMIN y USER)
- Persistencia de datos en JSON

### 📂 Listas de Reproducción

- Crear listas privadas por usuario
- Añadir canciones y podcasts sin duplicados
- Gestionar contenido de listas

### ▶️ Reproductor

- Formatos: WAV, AU, AIFF (Java Sound API)
- Controles: play, pausa, siguiente, anterior
- Cola de reproducción con navegación

## 🏗️ Arquitectura

**Patrón MVC:**

- **Modelos**: `Usuario`, `ItemMultimedia` (abstracta), `Cancion`, `Podcast`, `ListaReproduccion`
- **Controladores**: `ControladorMultimedia` (base genérica), `ControladorCancion`, `ControladorPodcast`, `ControladorUsuario`, `ControladorLista`, `ControladorReproductor`
- **Vista**: menús CLI modulares (`MenuGeneral`, `MenuUsuario`, `MenuAdmin`)
- **Servicios**: `PersistenciaJSON`, `ReproductorAudio`

**Estructura:**

```
src/
└── es/
    └── upsa/
        └── programacion/
            ├── App.java                    # CLI principal
            ├── modelos/                    # Entidades de dominio
            │   ├── ItemMultimedia.java     # Clase abstracta base
            │   ├── Cancion.java
            │   ├── Podcast.java
            │   ├── Usuario.java
            │   ├── ListaReproduccion.java
            │   ├── Rol.java
            │   └── Reproducible.java
            ├── controladores/              # Lógica de negocio
            │   ├── ControladorMultimedia.java  # Controlador genérico base
            │   ├── ControladorCancion.java
            │   ├── ControladorPodcast.java
            │   ├── ControladorUsuario.java
            │   ├── ControladorLista.java
            │   └── ControladorReproductor.java
            ├── menus/                      # Interfaz CLI modular
            │   ├── MenuGeneral.java
            │   ├── MenuUsuario.java
            │   └── MenuAdmin.java
            └── servicios/                  # Persistencia y audio
                ├── PersistenciaJSON.java
                └── ReproductorAudio.java

datos/                          # Archivos JSON
```

## 🛠️ Tecnologías

- **Java SE 8+**
- **org.json** - Persistencia JSON
- **Java Sound API** - Reproducción de audio

## 📖 Casos de Uso

**Usuarios (3):** Registro, Login, Logout  
**Canciones (6):** Añadir, Buscar (por id/título/artista/álbum/género/año), Listar, Modificar, Eliminar  
**Podcasts (6):** Añadir, Buscar (por id/título/host/temporada/género/año), Listar, Modificar, Eliminar  
**Listas (5):** Crear, Ver, Añadir items, Ver contenido, Eliminar  
**Reproducción (5):** Reproducir, Pausar, Reanudar, Anterior, Siguiente, Detener

## 📚 Diseño Técnico

**Conceptos aplicados:**

- **Herencia**: `Cancion` y `Podcast` extienden `ItemMultimedia` (clase abstracta)
- **Generics**: `ControladorMultimedia<T extends ItemMultimedia>` proporciona búsquedas genéricas
- **Interfaces**: `Reproducible` define `play()`, `pause()`, `stop()` permitiendo tratar canciones y podcasts de forma uniforme
- **Comparable**: ordenamiento natural por título
- **Iterable**: iteración sobre items de listas
- **Modularización**: menus separados en clases (`MenuGeneral`, `MenuUsuario`, `MenuAdmin`)

**Modelo unificado:**

- `ItemMultimedia` contiene campos comunes: `id`, `titulo`, `autor`, `album`, `genero`, `anno`, `rutaArchivo`
- En `Cancion`: `autor` = artista, `album` = álbum
- En `Podcast`: `autor` = host, `album` = temporada

**Colecciones:**

- `ArrayList` para catálogos
- `HashMap` para usuarios y listas
- `LinkedList` para cola de reproducción
- `HashSet` para prevenir duplicados

## 🚀 Ejecución

```bash
# Compilar
javac -cp lib/json-20220320.jar -d bin $(find src -name "*.java")

# Ejecutar
java -cp "bin:lib/json-20220320.jar" es.upsa.programacion.App
```

**Usuario admin por defecto:**

- Usuario: `admin`
- Contraseña: `admin`

## ℹ️ Información Adicional

**Sobre este proyecto:**

- README y commits generados con IA
- Trabajo para Programación - [Universidad Pontificia de Salamanca](https://upsa.es/)

## 🔗 Recursos

**Tutoriales utilizados:**

- [Java Sound API - Tutorial 1](https://www.youtube.com/watch?v=kSmwtbRgoDs)
- [Java CLI Applications - Tutorial 2](https://www.youtube.com/watch?v=PmOgruSPy3s&t=640s)
