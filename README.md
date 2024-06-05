![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white) ![Firebase](https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase)
# Ludex
## Descripción
Ludex es una aplicación Android desarrollada utilizando Kotlin que permite a los usuarios gestionar una lista de juegos, marcándolos como favoritos, actualizando su estado y buscando títulos específicos. Este proyecto implementa Clean Architecture y utiliza MVVM para el diseño de la arquitectura de la interfaz de usuario. Además, incorpora Room para la persistencia de datos local, Firebase Realtime Database para la persistencia de datos remota, Hilt para la inyección de dependencias y Firebase Auth para gestión de usuarios.

## Características
- 🎮 **Gestión de juegos:** Clasificar tus juegos favoritos por estado tales como: "Pendiente", Completado", "Jugando", "Abandonado", etc.
- ⭐ **Favoritos:** Marcar juegos como favoritos para un acceso rápido.
- 🔎 **Búsqueda de juegos:** Filtrar juegos por título.
- 📦 **Persistencia de datos local:** Permite al usuario consultar sus listas sin conexióon a internet.
- ☁️ **Persistencia de datos remota:** Guarda tus listas de juegos en la nube asignados a tu usuario.
- 🎨 **Diseño responsivo:** Interfaz adaptada a diferentes tamaños de pantalla y orientaciones.

## Capturas de Pantalla
![Google Pixel 4 XL](https://github.com/cvalera18/Ludex/assets/57680708/d4fdafc2-99a3-4a95-8f13-16c9bf92f205)   ..     ![Google Pixel 4 XL (1)](https://github.com/cvalera18/Ludex/assets/57680708/2943d13a-2279-4838-9048-32831df02513)![Google Pixel 4 XL (2)](https://github.com/cvalera18/Ludex/assets/57680708/7015b6ec-6bce-43f2-b382-0d59976c6d38)
![Google Pixel 4 XL (4)](https://github.com/cvalera18/Ludex/assets/57680708/7048bc54-2513-4cf9-a774-a9e4c351288b)![Google Pixel 4 XL (3)](https://github.com/cvalera18/Ludex/assets/57680708/2ca02c60-47e5-4aa7-8346-aa1c22495820)







## Tecnologías y Herramientas
- **Lenguaje de Programación:** ![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
- **Arquitectura de la Aplicación:** Clean Architecture, MVVM
- **Gestión de Dependencias:** Hilt
- **Base de Datos Local:** Room
- **Respaldo en la Nube:** Firebase Realtime Database
- **Autenticación:** Firebase Auth, Google Sign-In
- **Analíticas:** Firebase Analytics
- **Monitoreo de Errores:** Firebase Crashlytics
- **Testing:** JUnit y Mockk para pruebas unitarias

## Estructura del Proyecto
El proyecto se organiza en las siguientes capas principales:

- **core:** Contiene utilidades comunes, como proveedores de contexto, eventos, diálogos y extensiones.
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
  
5. **Configurar Firebase**
   - Ve a la consola de Firebase y añade tu proyecto de Android.
   - Sigue las instrucciones para registrar tu aplicación y descargar el archivo 'google-services.json'.
   - Añade el archivo 'google-services.json' a la carpeta 'app' de tu proyecto.

6. **Ejecutar el proyecto en un emulador o dispositivo Android**
   - Asegúrate de que tu entorno de desarrollo está configurado para ejecutar aplicaciones Android, incluyendo un emulador configurado o un dispositivo Android conectado.
   - Ejecuta la aplicación desde Android Studio usando 'Run > Run 'app''.

---

Para más información, puedes contactar a Carlos Valera en ing.cvalera@gmail.com

