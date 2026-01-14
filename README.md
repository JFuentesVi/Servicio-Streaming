# 🎵 Aplicación de Streaming de Música y Podcasts

## 📋 Descripción

Aplicación CLI en Java para gestionar y reproducir música y podcasts. Permite crear usuarios, organizar contenido multimedia, crear listas de reproducción y reproducir audio local.

## ✨ Funcionalidades

### 🎵 Gestión de Contenido

- Catálogo de canciones (título, artista, álbum, duración, género, año, ruta)
- Catálogo de podcasts (título, anfitrión, categoría, descripción, ruta)
- Búsqueda por título, artista o género
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

## Casos de Uso

**Autenticación y Gestión de Usuarios:**

- CU1: Registrarse como nuevo usuario (nombre, nombre de usuario, contraseña).
- CU2: Iniciar sesión con credenciales (nombre de usuario y contraseña).
- C🏗️ Arquitectura

**Patrón MVC:**

- **Modelos**: `Usuario`, `Cancion`, `Podcast`, `ListaReproduccion`
- **Controladores**: lógica de negocio para cada entidad
- **Vista**: interfaz CLI (`App.java`)
- **Servicios**: persistencia JSON y reproducción de audio

**Estructura:**

```
src/
├── App.java                    # CLI principal
├── modelos/                    # Entidades de dominio
├── controladores/              # Lógica de negocio
└── servicios/                  # Persistencia y audio

datos/                          # Archivos JSON
```

## 🛠️ Tecnologías

- **Java SE 8+**
- **org.json** - Persistencia JSON
- **Java Sound API** - Reproducción de audio

## 📖 Casos de Uso

**Usuarios (3):** Registro, Login, Logout  
**Canciones (5):** Añadir, Buscar, Listar, Modificar, Eliminar  
**Podcasts (5):** Añadir, Buscar, Listar, Modificar, Eliminar  
**Listas (5):** Crear, Ver, Añadir items, Ver contenido, Eliminar  
**Reproducción (4):** Reproducir, Controles, Ver progreso, Detener: clases de dominio que representan usuarios, medios, listas.

- **Controladores** (`es.upsa.programacion.controladores`): gestionan la lógica de negocio y operaciones.
- 📚 Diseño Técnico

**Conceptos aplicados:**

- **Herencia**: `Cancion` y `Podcast` extienden `MediaItem`
- **Interfaces**: `Reproducible` para polimorfismo
- **Comparable**: ordenamiento natural por título
- **Iterable**: iteración sobre items de listas
- **Genéricos**: `ordenarYMostrar(List<T extends MediaItem>)`

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
