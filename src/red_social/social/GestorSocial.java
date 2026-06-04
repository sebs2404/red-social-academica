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

import red_social.usuarios.Perfil;

public class GestorSocial {

    private ArrayList<Publicacion> publicaciones;

    public GestorSocial() {

        publicaciones = new ArrayList<>();
    }

    public void agregarPublicacion(
            Publicacion publicacion) {

        publicaciones.add(publicacion);
    }

    public void eliminarPublicacion(
            Perfil usuario,
            Publicacion publicacion) {

        if(!usuario.puedeEliminarPublicaciones()) {

            throw new IllegalArgumentException(
                    "No tiene permisos");
        }

        publicaciones.remove(publicacion);
    }

    public ArrayList<Publicacion> getPublicaciones() {

        return publicaciones;
    }
}