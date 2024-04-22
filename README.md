# GameListApp

## Descripción
GameListApp es una aplicación Android desarrollada utilizando Kotlin que permite a los usuarios gestionar una lista de juegos, marcándolos como favoritos, actualizando su estado y buscando títulos específicos. Este proyecto implementa Clean Architecture y utiliza MVVM para el diseño de la arquitectura de la interfaz de usuario. Además, incorpora Room para la persistencia de datos y Hilt para la inyección de dependencias.

## Características
- **Gestión de juegos:** Crear, leer, actualizar y eliminar información sobre juegos.
- **Favoritos:** Marcar juegos como favoritos para un acceso rápido.
- **Actualización de estados:** Cambiar el estado de los juegos, como "Jugando", "Completado", etc.
- **Búsqueda de juegos:** Filtrar juegos por título.
- **Persistencia de datos local:** Los datos de los usuarios se almacenan localmente utilizando Room.
- **Diseño responsivo:** Interfaz adaptada a diferentes tamaños de pantalla y orientaciones.

## Tecnologías y Herramientas
- **Lenguaje de Programación:** Kotlin
- **Arquitectura de la Aplicación:** Clean Architecture, MVVM
- **Gestión de Dependencias:** Hilt
- **Base de Datos Local:** Room
- **Testing:** JUnit para pruebas unitarias

## Estructura del Proyecto
El proyecto se organiza en las siguientes capas principales:

- **domain:** Contiene las entidades y casos de uso.
- **data:** Define las interfaces de los repositorios y sus implementaciones, además de las fuentes de datos.
- **presentation:** Incluye las actividades, fragmentos y ViewModels.
- **di:** Módulos para la configuración de la inyección de dependencias con Hilt.

## Cómo Ejecutar
Para ejecutar este proyecto, sigue estos pasos:

1. Clona este repositorio.
2. Abre el proyecto en Android Studio.
3. Ejecuta el proyecto en un emulador o dispositivo Android.

## Capturas de Pantalla


## Contribución
Las contribuciones son bienvenidas. Si deseas contribuir, puedes seguir estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tus cambios (`git checkout -b feature/nueva-caracteristica`).
3. Haz commit de tus cambios (`git commit -am 'Añade alguna característica'`).
4. Realiza un push a la rama (`git push origin feature/nueva-caracteristica`).
5. Crea un Pull Request.

---

Para más información, puedes contactar a Carlos Valera en ing.cvalera@gmail.com

