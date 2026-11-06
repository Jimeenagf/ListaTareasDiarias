
# COSAS QUE HACER EN EL DÍA

## Descripción

COSAS QUE HACER EN EL DÍA es una aplicación de escritorio desarrollada en Java que permite organizar y gestionar las tareas diarias de una forma sencilla e intuitiva.

La aplicación permite crear una lista de tareas al comienzo del día y marcar aquellas que se van completando mediante casillas de verificación.

Su objetivo es facilitar la organización personal y el seguimiento de las actividades pendientes.

## Funcionalidades

- Añadir nuevas tareas a la lista diaria.
- Marcar las tareas completadas mediante casillas de verificación.
- Consultar el progreso diario y el número de tareas terminadas.
- Guardar automáticamente las tareas y su estado.
- Conservar las tareas pendientes para el día siguiente.
- Eliminar las tareas que ya han sido completadas.
- Mantener los datos guardados aunque se cierre la aplicación.

## Tecnologías utilizadas

- Java
- Java Swing
- Eclipse IDE
- Git
- GitHub

## Persistencia de datos

La aplicación utiliza un archivo local llamado `tareas.txt` para almacenar las tareas y su estado de finalización.

Al comenzar un nuevo día, las tareas que no se han completado se mantienen en la lista, mientras que las tareas terminadas se eliminan automáticamente.

## Ejecución

El proyecto puede ejecutarse desde Eclipse mediante la clase principal:

`ListaTareasDiarias.java`

La aplicación utiliza Java Swing para construir su interfaz gráfica.

## Autor

Proyecto personal desarrollado como práctica de programación en Java y gestión de versiones mediante Git.
