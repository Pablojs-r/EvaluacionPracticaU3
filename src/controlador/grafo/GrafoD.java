/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.grafo;

import lista.controaldor.Lista;

/**
 *
 * @author pablo
 */
public class GrafoD extends Grafo {

    private Integer numV;
    protected Integer numA;
    protected Lista<Adyacencia> listaAdyacente[];

    public GrafoD(Integer numV) {
        this.numV = numV;
        numA = 0;
        listaAdyacente = new Lista[this.numV + 1];
        for (int i = 1; i <= this.numV; i++) {
            listaAdyacente[i] = new Lista<>();
        }
    }

    @Override
    public Integer numVertices() {
        return this.numV;
    }

    @Override
    public Integer numAristas() {
        return this.numA;
    }

    @Override
    public Boolean existeArista(Integer i, Integer j) throws Exception {
        Boolean esta = false;
        if (i.intValue() <= numV && j.intValue() <= numV) {
            Lista<Adyacencia> lista_adyacentes = listaAdyacente[i];
            for (int k = 0; k < lista_adyacentes.tamanio(); k++) {
                Adyacencia aux = lista_adyacentes.consultarDatoPosicion(k);
                if (aux.getDestino().intValue() == j.intValue()) {
                    esta = true;
                    break;
                }
            }
        }
        return esta;
    }

    @Override
    public Double pesoArista(Integer i, Integer j) {
        Double peso = Double.NaN;
        try {
            if (existeArista(i, j)) {
                Lista<Adyacencia> adyacentes = listaAdyacente[i];
                for (int k = 0; k < adyacentes.tamanio(); k++) {
                    Adyacencia aux = adyacentes.consultarDatoPosicion(k);
                    if (aux.getDestino().intValue() == j.intValue()) {
                        peso = aux.getPeso();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo encontrar el dato");
        }
        return peso;
    }

    @Override
    public void InsertarArista(Integer i, Integer j) {
        InsertarArista(i, j, Double.NaN);
    }

    @Override
    public void InsertarArista(Integer i, Integer j, Double peso) {
        try {
            if (i.intValue() <= numV && j.intValue() <= numV) {
                if (!existeArista(i, j)) {
                    numA++;
                    listaAdyacente[i].insertarNodo(new Adyacencia(j, peso));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en insertar arista" + e);
        }
    }

    @Override
    public Lista<Adyacencia> adyacentes(Integer i) {
        try {
            return listaAdyacente[i];
        } catch (Exception e) {
            return null;
        }
    }


}
