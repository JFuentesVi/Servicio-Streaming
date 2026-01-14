## Diseño solicitado (clases, herencia, interfaz, colecciones)

- **Clases y objetos**: modelos como `Usuario`, `Cancion`, `Podcast`, `ListaReproduccion`, y controladores (`ControladorUsuario`, `ControladorCancion`, `ControladorPodcast`, `ControladorLista`, `ControladorReproductor`) se instancian en `App` para gestionar la lógica.
- **Herencia**: `Cancion` y `Podcast` extienden de la clase abstracta `MediaItem`, reutilizando metadatos comunes (id, título, duración, género, año, ruta de archivo).
- **Interfaz**: ambos (`Cancion` y `Podcast`) implementan la interfaz `Reproducible`, que define las operaciones mínimas de reproducción (`play()` y `pause()`), permitiendo tratarlos de forma polimórfica en el reproductor.
- **Colecciones usadas**:
  - `ArrayList` para catálogos en memoria de canciones y podcasts.
  - `HashMap` para usuarios por nombre de usuario (`nombreUsuario`) y para listas por id (accesos rápidos por clave).
  - `LinkedList` en `ControladorReproductor` para la cola de reproducción (siguiente/anterior eficientes).
  - `HashSet` en `ListaReproduccion` para evitar duplicados de referencias a items.

## Ajustes alineados con los Temas 1-4

- **Orden natural y comparator**: `MediaItem` implementa `Comparable` (ordena por título) y `MediaItemDuracionComparator` permite ordenar por duración (Tema 3 Comparable/Comparator). Los listados preguntan el criterio antes de mostrar.
- **Iterable y Cloneable**: `ListaReproduccion` implementa `Iterable<ItemRef>` (for-each) y `Cloneable` con clonación superficial para duplicar listas (Tema 3).
- **Herencia + interfaz**: `Cancion` y `Podcast` siguen extendiendo `MediaItem` e implementan `Reproducible`, ejemplificando herencia e interfaz (Tema 3).
- **Sintaxis básica**: uso de tipos explícitos, arrays/listas y control de flujo siguiendo la sintaxis mostrada en los Temas 1-2.
- **Fuera de los PDF**: solo org.json para persistencia y Java Sound (AudioSystem/Clip) para reproducir WAV/AU/AIFF; se mantienen por funcionalidad práctica.
