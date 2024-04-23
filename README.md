# Ludex

## Descripción
Ludex es una aplicación Android desarrollada utilizando Kotlin que permite a los usuarios gestionar una lista de juegos, marcándolos como favoritos, actualizando su estado y buscando títulos específicos. Este proyecto implementa Clean Architecture y utiliza MVVM para el diseño de la arquitectura de la interfaz de usuario. Además, incorpora Room para la persistencia de datos y Hilt para la inyección de dependencias.

## Características
- **Gestión de juegos:** Crear, leer, actualizar y eliminar información sobre juegos.
- **Favoritos:** Marcar juegos como favoritos para un acceso rápido.
- **Actualización de estados:** Cambiar el estado de los juegos, como "Jugando", "Completado", etc.
- **Búsqueda de juegos:** Filtrar juegos por título.
- **Persistencia de datos local:** Los datos de los usuarios se almacenan localmente utilizando Room.
- **Diseño responsivo:** Interfaz adaptada a diferentes tamaños de pantalla y orientaciones.

## Capturas de Pantalla
![Google Pixel 4 XL (3) (1)](https://github.com/cvalera18/Ludex/assets/57680708/4c20495b-d76f-493e-85c4-dfecd47dbb45) ![Google Pixel 4 XL (2)](https://github.com/cvalera18/Ludex/assets/57680708/62fdfd11-9ea1-4798-9556-a4541b8cc2d5) ![Google Pixel 4 XL (4)](https://github.com/cvalera18/Ludex/assets/57680708/313f30bb-219e-4b1f-8fc0-d1413bfaca47)

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
Para ejecutar este proyecto y asegurar su funcionamiento correcto, sigue estos pasos:

1. **Clonar este repositorio**
   - Usa el comando `git clone https://github.com/cvalera18/Ludex/` para obtener una copia local del proyecto.
2. **Abrir el proyecto en Android Studio**
   - Inicia Android Studio y selecciona 'Open an existing Android Studio project', luego navega hasta la carpeta donde clonaste el proyecto.

3. **Obtener una API Key de IGDB**
   - Visita el sitio web de [IGDB](https://api.igdb.com/) y regístrate para obtener una cuenta de desarrollador si aún no tienes una.
   - Sigue las instrucciones para solicitar una API Key. Normalmente, esto implica aceptar los términos de uso y completar un formulario con la descripción de cómo planeas usar los datos de IGDB.
   - Una vez que tengas la API Key, necesitarás configurarla en tu proyecto.

4. **Configurar la API Key en tu proyecto**
   - Localiza el archivo de configuración en tu proyecto donde las claves API son almacenadas (`local.properties`).
   - Añade tu API Key a este archivo de configuración. Asegúrate de no subir este archivo a repositorios públicos para mantener segura tu clave.
   - Alternativamente, puedes configurar tu clave en el código en un lugar seguro y referenciarla desde allí.

5. **Ejecutar el proyecto en un emulador o dispositivo Android**
   - Asegúrate de que tu entorno de desarrollo está configurado para ejecutar aplicaciones Android, incluyendo un emulador configurado o un dispositivo Android conectado.
   - Ejecuta la aplicación desde Android Studio usando 'Run > Run 'app''.

---

Para más información, puedes contactar a Carlos Valera en ing.cvalera@gmail.com

