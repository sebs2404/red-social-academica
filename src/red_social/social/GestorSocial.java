/*
 * NOMBRE DEL PROGRAMA: Red Social Academica
 * MODULO: Social.
 * CLASE: gestor social
 * AUTOR: juan esteban silva
 * FECHA:
 */
//se gestiona la creacion de publicaciones dentro del sistema
package red_social.social;

import java.util.ArrayList;
import red_social.usuarios.GestorUsuarios;
import red_social.usuarios.Perfil;

/**
 * Controla e interactúa con el muro de publicaciones de la red social.
 */
public class GestorSocial {
    
    private Feed feed;
    private GestorUsuarios gestorUsuarios;

    /**
     * Constructor principal del gestor social.
     * @param gestorUsuarios Instancia del sistema de control de usuarios.
     * @param feed Instancia del contenedor de publicaciones del muro.
     */
    public GestorSocial(GestorUsuarios gestorUsuarios, Feed feed) {
        this.gestorUsuarios = gestorUsuarios;
        this.feed = feed;
    }

    /**
     * Crea una nueva publicación inyectando los valores de forma directa en los atributos de la clase.
     * @param contenido Texto del mensaje ingresado por el usuario.
     * @param correoAutor Correo electronico de la persona que publica.
     */
    public void crearPublicacion(String contenido, String correoAutor) {
        if (feed != null && contenido != null && !contenido.trim().isEmpty()) {
            Perfil autor = gestorUsuarios.buscarPorCorreo(correoAutor);
            
            if (autor != null) {
                try {
                    String textoLimpio = contenido.replace(";", "[SC]");
                    
                    // Buscamos el primer constructor que tenga la clase de tu compañero
                    java.lang.reflect.Constructor<?>[] constructores = Publicacion.class.getDeclaredConstructors();
                    java.lang.reflect.Constructor<?> constructor = constructores[0];
                    constructor.setAccessible(true);
                    
                    // Creamos el objeto rellenando sus parámetros automáticamente con datos por defecto
                    Object[] parametros = new Object[constructor.getParameterCount()];
                    for (int i = 0; i < parametros.length; i++) {
                        Class<?> tipo = constructor.getParameterTypes()[i];
                        if (tipo == String.class) parametros[i] = "";
                        else if (tipo == int.class || tipo == Integer.class) parametros[i] = 0;
                        else parametros[i] = null;
                    }
                    
                    Publicacion nueva = (Publicacion) constructor.newInstance(parametros);
                    
                    // Inyectamos el contenido y el autor directamente en las variables de la clase
                    for (java.lang.reflect.Field field : Publicacion.class.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.getType() == String.class) {
                            field.set(nueva, textoLimpio);
                        } else if (field.getType() == Perfil.class || field.getType().getName().contains("Perfil") || field.getType().getName().contains("Usuario")) {
                            field.set(nueva, autor);
                        }
                    }
                    
                    feed.agregarPublicacion(nueva);
                } catch (Exception e) {
                    System.out.println("Error en la inyección de datos de la publicación.");
                }
            }
        }
    }

    /**
     * Agrega un comentario en una publicacion especifica inyectando los valores de forma directa.
     * @param publicacion Objeto de la publicacion original que recibe la respuesta.
     * @param textoContenido Mensaje escrito en el comentario.
     * @param correoAutor Correo electronico del usuario que comenta.
     */
    public void agregarComentarioAPublicacion(Publicacion publicacion, String textoContenido, String correoAutor) {
        if (publicacion != null && textoContenido != null && !textoContenido.trim().isEmpty()) {
            Perfil autor = gestorUsuarios.buscarPorCorreo(correoAutor);
            
            if (autor != null) {
                try {
                    String textoLimpio = textoContenido.replace(";", "[SC]");
                    
                    java.lang.reflect.Constructor<?>[] constructores = Comentario.class.getDeclaredConstructors();
                    java.lang.reflect.Constructor<?> constructor = constructores[0];
                    constructor.setAccessible(true);
                    
                    Object[] parametros = new Object[constructor.getParameterCount()];
                    for (int i = 0; i < parametros.length; i++) {
                        Class<?> tipo = constructor.getParameterTypes()[i];
                        if (tipo == String.class) parametros[i] = "";
                        else if (tipo == int.class || tipo == Integer.class) parametros[i] = 0;
                        else parametros[i] = null;
                    }
                    
                    Comentario nuevoComentario = (Comentario) constructor.newInstance(parametros);
                    
                    for (java.lang.reflect.Field field : Comentario.class.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.getType() == String.class) {
                            field.set(nuevoComentario, textoLimpio);
                        } else if (field.getType() == Perfil.class || field.getType().getName().contains("Perfil") || field.getType().getName().contains("Usuario")) {
                            field.set(nuevoComentario, autor);
                        }
                    }
                    
                    publicacion.agregarComentario(nuevoComentario);
                } catch (Exception e) {
                    System.out.println("Error en la inyección de datos del comentario.");
                }
            }
        }
    }

    /**
     * Obtiene la lista completa de publicaciones registradas en el feed.
     * @return ArrayList con los objetos de tipo Publicacion existentes.
     */
    public ArrayList<Publicacion> obtenerTodasLasPublicaciones() {
        if (feed != null) {
            return feed.getPublicaciones();
        }
        return new ArrayList<>();
    }
}
