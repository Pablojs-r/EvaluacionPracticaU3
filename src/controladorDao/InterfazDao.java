/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorDao;

import Controlador.grafo.GrafoEtiquetaND;
import lista.controaldor.Lista;

/**
 *
 * @author pablo
 */
public interface InterfazDao <T> {
    public boolean guardar(T dato);
    public boolean modificar(T dato);
    public GrafoEtiquetaND<T> listarGrafo();
//    public T buscaId(Long id, T dato);
}
