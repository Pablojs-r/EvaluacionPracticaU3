/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.grafo;

/**
 *
 * @author pablo
 */
public class GrafoND extends GrafoD {

    public GrafoND(Integer numV) {
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
            System.out.println("Error en insertar GND");
        }
    }

}
