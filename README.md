# Red Social Académica para Proyectos y Colaboración

Universidad Distrital Francisco José de Caldas  
Ingeniería de Sistemas — Programación Orientada a Objetos  
Docente: Fernando Martínez Rodríguez

## Integrantes
- Jhon Sebastian Avendaño Gutierrez — Módulo 2: Proyectos y Equipos
- ___________________________ — Módulo 1: Usuarios y Perfiles
- ___________________________ — Módulo 3: Social, Eventos y Reportes

## Descripción
Red social orientada a la colaboración académica donde estudiantes y profesores
crean perfiles, publican proyectos, forman equipos y solicitan colaboradores.

## Estructura del proyecto
src/red_social/
├── usuarios/       → Perfil, Estudiante, Profesor, Administrador
├── proyectos/      → Proyecto, Equipo, Miembro, Avance, Invitacion
├── social/         → Feed, Publicacion, Comentario, Reaccion
├── eventos/        → Evento, Calendario
├── persistencia/   → Lectura y escritura de archivos .txt
└── RedSocialMain.java
datos/              → Archivos .txt generados por el programa

## Ramas de trabajo
- main → código estable integrado
- modulo-usuarios → Módulo 1
- modulo-proyectos → Módulo 2
- modulo-social → Módulo 3

## Reglas del repositorio
- Nunca trabajar directamente en main
- Siempre hacer git pull antes de empezar
- Commits con mensajes descriptivos
- Revisar el Pull Request antes de hacer merge