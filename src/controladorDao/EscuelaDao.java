/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorDao;

import modelo.Escuela;

/**
 *
 * @author pablo
 */
public class EscuelaDao extends AdaptadorDao<Escuela> {

    Escuela escuela = new Escuela();

    public EscuelaDao() {
        super(Escuela.class);
        listarGrafo();
    }

    public Escuela getEscuela() {
        if(escuela == null){
            escuela = new Escuela();
        }
        return escuela;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    public Boolean guardar() {
        Integer aux = (getGrafo() != null) ? getGrafo().numVertices() + 1 : 1;
        escuela.setId(new Long(aux));
        return guardar(escuela);
    }

    public Boolean modificar() {
        return modificar(escuela);
    }

}
