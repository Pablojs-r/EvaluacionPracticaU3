/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.grafo;

import java.io.Serializable;
import lista.controaldor.Lista;
import lista.controaldor.PilaCola;

/**
 *
 * @author pablo
 */
public class GrafoEtiquetaND<E> extends GrafoEtiquetadoD<E> implements Serializable {

//    private Lista<Integer> visitados;

    public GrafoEtiquetaND(Integer numV) {
        super(numV);
    }

    @Override
    public void InsertarArista(Integer i, Integer j, Double peso) {
        try {
            if (i.intValue() <= numVertices() && j.intValue() <= numVertices()) {
                if (!existeArista(i, j)) {
                    numA++;
                    listaAdyacente[i].insertarNodo(new Adyacencia(j, peso));
                    listaAdyacente[j].insertarNodo(new Adyacencia(i, peso));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en insertar GND");
        }
    }

    @Override
    public void insertarAristaE(E i, E j, Double peso) {
        try {
            InsertarArista(obtenerCodigo(i), obtenerCodigo(j), peso);
            InsertarArista(obtenerCodigo(j), obtenerCodigo(i), peso);
        } catch (Exception e) {
        }
    }

}
