# LineaIII-AgendaBD

# Descripción

## Agenda Base de Datos

    Es una aplicación de gestión de usuarios con base de datos desarrollada en Android Studio utilizando Jetpack Compose y Room.
    Creada por el estudiante Cain David Martinez del núcleo temático de Línea de Profundización III 901N.
    Permite a los usuarios agregar, editar, listar y eliminar usuarios en una interfaz intuitiva y responsiva.

# Funcionalidades Principales
## MainActivity.kt

    Punto de Entrada: La actividad principal inicializa la interfaz de usuario.
    Navegación: Usa NavHost para manejar la navegación entre las diferentes pantallas de la aplicación.

## UserApp.kt

    Pantalla Principal: Contiene un formulario que permite a los usuarios ingresar nombre, apellido y edad, con opciones para agregar, editar o eliminar usuarios.
    Funcionalidades:
        Agregar Usuario: Permite registrar nuevos usuarios.
        Editar Usuario: Ofrece la opción de editar un usuario existente proporcionando el ID.
        Eliminar Usuario: Posibilidad de eliminar usuarios por su ID.
        Listar Usuarios: Muestra una lista de todos los usuarios registrados.

## User.kt (Modelo)

    Clase User: Define los campos del usuario, incluyendo ID, nombre, apellido y edad.

## Room Database

    Persistencia de Datos: La aplicación utiliza Room para gestionar la base de datos local, permitiendo la manipulación de usuarios de manera eficiente y segura.

## Diseño de Interfaz

    Interfaz Responsiva: Diseñada para funcionar correctamente tanto en modo vertical como horizontal, utilizando scroll en caso de ser necesario para evitar que botones o campos queden ocultos.
    Formulario: Los campos están distribuidos de manera accesible, con espaciado adecuado para mejorar la legibilidad.

## IDE y Lenguaje Utilizados

    Android Studio
    Kotlin

- ¡Gestiona tus usuarios de manera fácil con esta aplicación!