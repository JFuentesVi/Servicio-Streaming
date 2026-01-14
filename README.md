# Aplicación de Streaming de Música y Podcasts

## Descripción General

Esta es una aplicación de línea de comandos (CLI) en Java que permite a los usuarios gestionar, organizar y reproducir contenido multimedia. Combina funcionalidades de un streaming de música tradicional con la capacidad de gestionar podcasts, crear listas de reproducción personalizadas y reproducir audio local.

### Características Principales

**Gestión de Contenido Multimedia:**

- Catálogo de canciones con información detallada: título, artista, álbum, duración, género, año y ruta del archivo.
- Catálogo de podcasts con título, anfitrión, categoría, descripción y enlace de audio.
- Búsqueda avanzada por título, artista o género con coincidencias parciales.
- Ordenamiento flexible: por título natural (alfabético) o por duración.

**Sistema de Usuarios y Autenticación:**

- Registro de nuevos usuarios con nombre, nombre de usuario único y contraseña.
- Login basado en nombre de usuario (migrado de email para mayor usabilidad).
- Roles de usuario: ADMIN y USER con funcionalidades diferenciadas.
- Persistencia de datos entre sesiones mediante JSON.

**Listas de Reproducción Personalizadas:**

- Crear listas de reproducción privadas por usuario.
- Añadir canciones y podcasts a las listas sin limitación.
- Evitar duplicados de ítems mediante estructura de referencias.
- Duplicar listas existentes (clonación superficial).

**Reproductor de Audio Integrado:**

- Reproducción de archivos de audio en formatos WAV, AU, AIFF (Java Sound API).
- Controles interactivos: play, pausa, siguiente, anterior.
- Gestión de cola de reproducción con soporte para navegación eficiente.
- Visualización en tiempo real del estado de reproducción.

## Casos de Uso

**Autenticación y Gestión de Usuarios:**

- CU1: Registrarse como nuevo usuario (nombre, nombre de usuario, contraseña).
- CU2: Iniciar sesión con credenciales (nombre de usuario y contraseña).
- CU3: Cerrar sesión y volver al menú principal.

**Gestión del Catálogo de Canciones:**

- CU4: Añadir nueva canción al catálogo (id, título, artista, álbum, duración, género, año, ruta).
- CU5: Buscar canciones por título, artista o género (búsqueda parcial).
- CU6: Ver listado de canciones ordenadas (natural por título o por duración).
- CU7: Modificar información de una canción existente.
- CU8: Eliminar canción del catálogo.

**Gestión del Catálogo de Podcasts:**

- CU9: Añadir nuevo podcast al catálogo (id, título, anfitrión, categoría, descripción, duración, género, año, ruta).
- CU10: Buscar podcasts por título o categoría (búsqueda parcial).
- CU11: Ver listado de podcasts ordenados (natural por título o por duración).
- CU12: Modificar información de un podcast existente.
- CU13: Eliminar podcast del catálogo.

**Listas de Reproducción Personalizadas:**

- CU14: Crear nueva lista de reproducción privada (nombre, usuario propietario).
- CU15: Ver mis listas de reproducción.
- CU16: Añadir canción o podcast a una lista existente.
- CU17: Ver contenido de una lista (canciones y podcasts añadidos).
- CU18: Duplicar lista de reproducción existente.
- CU19: Eliminar lista de reproducción.

**Reproducción de Contenido:**

- CU20: Reproducir lista de reproducción completa.
- CU21: Controlar reproducción: play, pausa, siguiente, anterior.
- CU22: Ver progreso de reproducción en tiempo real.
- CU23: Detener reproducción y volver al menú.

### Diseño y Arquitectura

La aplicación sigue un patrón **Modelo-Controlador-Vista (MCV)** con separación clara de responsabilidades:

- **Modelos** (`es.upsa.programacion.modelos`): clases de dominio que representan usuarios, medios, listas.
- **Controladores** (`es.upsa.programacion.controladores`): gestionan la lógica de negocio y operaciones.
- **Servicios** (`es.upsa.programacion.servicios`): persistencia en JSON y reproducción de audio.
- **App.java**: interfaz de usuario en CLI con menus interactivos línea por línea.

### Tecnologías y Dependencias

- **Java SE**: Java 8+ (compatible con versiones LTS).
- **org.json**: serialización/deserialización JSON para persistencia.
- **Java Sound API**: reproducción nativa de audio sin dependencias externas.

### Estructura del Proyecto

```
src/
├── App.java                    # Punto de entrada y menus CLI
├── modelos/                    # Clases de dominio
│   ├── MediaItem, Cancion, Podcast, Usuario, ListaReproduccion
│   ├── Comparable, Iterable, Cloneable implementados
│   └── Rol enum para permisos
├── controladores/              # Lógica de negocio
│   ├── ControladorCancion, ControladorPodcast
│   ├── ControladorUsuario, ControladorLista
│   ├── ControladorReproductor, ControladorListaPersistente
└── servicios/                  # Persistencia y audio
    ├── PersistenciaJSON        # I/O de datos
    └── ReproductorAudio        # Reproducción multimedia

datos/                          # Almacenamiento local (JSON)
├── canciones.json
├── podcasts.json
├── usuarios.json
└── listas.json
```

## Diseño Técnico (Temas 1-4)

- **Clases y objetos**: modelos como `Usuario`, `Cancion`, `Podcast`, `ListaReproduccion`, y controladores (`ControladorUsuario`, `ControladorCancion`, `ControladorPodcast`, `ControladorLista`, `ControladorReproductor`) se instancian en `App` para gestionar la lógica.
- **Herencia**: `Cancion` y `Podcast` extienden de la clase abstracta `MediaItem`, reutilizando metadatos comunes (id, título, duración, género, año, ruta de archivo).
- **Interfaz**: ambos (`Cancion` y `Podcast`) implementan la interfaz `Reproducible`, que define las operaciones mínimas de reproducción (`play()` y `pause()`), permitiendo tratarlos de forma polimórfica en el reproductor.
- **Colecciones usadas**:
  - `ArrayList` para catálogos en memoria de canciones y podcasts.
  - `HashMap` para usuarios por nombre de usuario (`nombreUsuario`) y para listas por id (accesos rápidos por clave).
  - `LinkedList` en `ControladorReproductor` para la cola de reproducción (siguiente/anterior eficientes).
  - `HashSet` en `ListaReproduccion` para evitar duplicados de referencias a items.

## Características de Diseño Avanzadas

- **Comparable/Comparator**: `MediaItem` implementa `Comparable` (ordena por título) y `MediaItemDuracionComparator` permite ordenar por duración (Tema 3). Los listados preguntan el criterio antes de mostrar.
- **Iterable y Cloneable**: `ListaReproduccion` implementa `Iterable<ItemRef>` (for-each) y `Cloneable` con clonación superficial para duplicar listas (Tema 3).
- **Patrón Wrapper**: `ListaPersistente` encapsula listas de reproducción con sincronización a disco automática.
- **Genéricos**: método `ordenarYMostrar(List<T>)` con tipo parametrizado constrained a `MediaItem` para evitar duplicación de código.
