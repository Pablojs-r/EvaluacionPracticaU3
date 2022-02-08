/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.grafo;

import Controlador.exepcion.AdyacenciaExepcion;
import java.io.Serializable;
import lista.controaldor.Lista;
import lista.controaldor.PilaCola;

/**
 *
 * @author pablo
 */
public abstract class Grafo implements Serializable {

    public abstract Integer numVertices();

    /**
     * Es el numero de vertices del grafo
     *
     * @return Integer numero de vertices
     */
    public abstract Integer numAristas();

    /**
     *
     * Es el numero de aristas del grafo
     *
     * @return Integer numero de aristas
     */
    public abstract Boolean existeArista(Integer i, Integer j) throws Exception;

    public abstract Double pesoArista(Integer i, Integer j);

    public abstract void InsertarArista(Integer i, Integer j);

    /**
     *
     * Es el numero de aristas del grafo
     *
     * @return Integer numero de aristas
     */
    public abstract void InsertarArista(Integer i, Integer j, Double peso);

    public abstract Lista<Adyacencia> adyacentes(Integer i);

    @Override
    public String toString() {
        String grafo = "";
        for (int i = 1; i <= numVertices(); i++) {
            grafo += "Vertice " + i;
            Lista<Adyacencia> lista = adyacentes(i);
            for (int j = 0; j < lista.tamanio(); j++) {
                Adyacencia aux = lista.consultarDatoPosicion(j);
                if (aux.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN))) {
                    grafo += " --Vertice destino " + aux.getDestino() + "-- SP";
                } else {
                    grafo += " --Vertice destino " + aux.getDestino() + "-- Peso" + aux.getPeso() + "--";
                }
            }
            grafo += "\n";
        }
        return grafo;
    }

    public Lista caminoMinimo(Integer verticeI, Integer verticeF) throws AdyacenciaExepcion {
        Lista<Integer> caminos = new Lista<>();

        Lista<Double> listaPesos = new Lista<>();
        Integer inicial = verticeI;
        caminos.insertarNodo(inicial);
        Boolean finalizar = false;
        while (!finalizar) {
            Lista<Adyacencia> adyacencias = adyacentes(inicial);
            if (adyacencias == null) {
                throw new AdyacenciaExepcion("No existe Adyacencias");
            }
            Double peso = 100000000.0;
            Integer T = -1;
            for (int i = 0; i < adyacencias.tamanio(); i++) {
                Adyacencia ad = adyacencias.consultarDatoPosicion(i);
                if (!estaEnCamino(caminos, ad.getDestino())) {
                    Double pesoArista = ad.getPeso();
                    if (verticeF.intValue() == ad.getDestino().intValue()) {
                        T = ad.getDestino();
                        peso = pesoArista;
                        break;
                    } else if (pesoArista < peso) {
                        T = ad.getDestino();
                        peso = pesoArista;
                    }
                }
            }
            listaPesos.insertarNodo(peso);
            caminos.insertarNodo(T);
            inicial = T;
            if (verticeF.intValue() == inicial.intValue()) {
                finalizar = true;
            }
        }
        return caminos;
    }

    private Boolean estaEnCamino(Lista<Integer> lista, Integer vertice) {
        Boolean band = false;
        for (int i = 0; i < lista.tamanio(); i++) {
            if (lista.consultarDatoPosicion(i).intValue() == vertice.intValue()) {
                band = true;
                break;
            }
        }
        return false;
    }

    //Metoos de recorrido 
    private Integer ordenVisita;
    private Integer[] visitados;
    private PilaCola<Integer> q;

    public Integer[] BPP(Integer origen) {
        Integer recorrido[] = new Integer[numVertices() + 1];
        visitados = new Integer[numVertices() + 1];
        ordenVisita = 1;
        for (int i = 0; i <= numVertices(); i++) {
            visitados[i] = 0;
        }
        for (int i = origen; i <= numVertices(); i++) {
            if (visitados[i] == 0) {
                BPP(i, recorrido);
            }
        }
        return recorrido;
    }

    private Integer[] BPP(Integer origen, Integer recorrido[]) {
        recorrido[ordenVisita] = origen;
        visitados[origen] = ordenVisita++;
        Lista<Adyacencia> adyacencias = adyacentes(origen);
        for (int i = 0; i < adyacencias.tamanio(); i++) {
            Adyacencia aux = adyacencias.consultarDatoPosicion(i);
            if (visitados[aux.getDestino()] == 0) {
                BPP(aux.getDestino(), recorrido);
            }
        }
        return recorrido;
    }

    public Integer[] BPA(Integer origen) {
        Integer recorrido[] = new Integer[numVertices() + 1];
        visitados = new Integer[numVertices() + 1];
        ordenVisita = 1;
        q = new PilaCola(numVertices() + 1, "COLA");
        for (int i = 1; i <= numVertices(); i++) {
            visitados[i] = 0;
        }
        for (int i = origen; i <= numVertices(); i++) {
            if (visitados[i] == 0) {
                BPA(i, recorrido);
            }
        }
        return recorrido;
    }

    private void BPA(Integer origen, Integer[] recorrido) {
        recorrido[ordenVisita] = origen;
        visitados[origen] = ordenVisita++;
        q.queue(origen);
        while (!q.estaVacia()) {
            Integer u = q.extraer();
            Lista<Adyacencia> adyacencias = adyacentes(u);
            for (int i = 0; i < adyacencias.tamanio(); i++) {
                Adyacencia aux = adyacencias.consultarDatoPosicion(i);
                if (visitados[aux.getDestino()] == 0) {
                    recorrido[ordenVisita] = aux.getDestino();
                    visitados[aux.getDestino()] = ordenVisita++;
                    q.queue(aux.getDestino());
                }
            }
        }
    }

    //Floyd
    private Double[][] matrizAdya;
    private Integer[][] caminos;

    public String floyd(Integer NI, Integer NF) {
        //Llenar Matriz Adyacencias
        matrizAdya = new Double[numVertices()][numVertices()];
        caminos = new Integer[numVertices()][numVertices()];
        for (int i = 0; i < numVertices(); i++) {
            Lista<Adyacencia> adyacencias = adyacentes((i + 1));
            for (int j = 0; j < adyacencias.tamanio(); j++) {
                Adyacencia aux = adyacencias.consultarDatoPosicion(j);
                matrizAdya[i][aux.getDestino() - 1] = aux.getPeso();
            }
        }
        for (int i = 0; i < numVertices(); i++) {
            for (int j = 0; j < numVertices(); j++) {
                if (matrizAdya[i][j] == null) {
                    matrizAdya[i][j] = -1.0;
                }
            }
        }
        imprimirMatrizAdya();

        //Llenar matriz caminos 
        for (int i = 0; i < numVertices(); i++) {
            int k = 1;
            for (int j = 0; j < numVertices(); j++) {
                if (i == j) {
                    caminos[i][j] = 0;
                } else {
                    caminos[i][j] = k;
                }
                k++;
            }
        }
        imprimirCaminos();

        Double anterior, ik, kj, actual, minimo = 0.0;
        for (int k = 0; k < numVertices(); k++) {
            for (int i = 0; i < numVertices(); i++) {
                for (int j = 0; j < numVertices(); j++) {
                    if (i != j && k != i && k != j) {
                        anterior = matrizAdya[i][j];
                        ik = matrizAdya[i][k];
                        kj = matrizAdya[k][j];
                        if (ik == -1 || kj == -1) {
                            actual = -1.0;
                        } else {
                            actual = ik + kj;
                        }
                        if (actual != -1 && (actual < anterior || anterior == - 1)) {
                            minimo = actual;
                            caminos[i][j] = (k + 1);
                        } else {
                            minimo = anterior;
                        }
                        matrizAdya[i][j] = minimo;
                    }
                }
            }
        }

        //Llenar matriz caminso con 0 en su diagonal
        for (int i = 0; i < numVertices(); i++) {
            for (int j = 0; j < numVertices(); j++) {
                if (caminos[i][j] == null) {
                    caminos[i][j] = 0;
                }
            }
        }

        imprimirMatrizAdya();
        imprimirCaminos();

        //CAMINOS
        Integer ant = -1;
        String caminoOp = "";
        System.out.println("CAMINOOP");
        Integer ni = NI - 1;
        Integer nf = NF - 1;
        while (caminos[ni][nf] != ni && ant != nf) {
            caminoOp = caminos[ni][nf] + "---->" + caminoOp;
            ant = nf;
            nf = caminos[ni][nf];
        }
        System.out.println(caminoOp);
        return caminoOp;
    }
    
    
    public void imprimirMatrizAdya() {
        System.out.println("MATRIZ ADYACENCIAS");
        for (int i = 0; i < numVertices(); i++) {
            for (int j = 0; j < numVertices(); j++) {
                System.out.print(matrizAdya[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    private void imprimirCaminos() {
        System.out.println("MATRIZ CAMINOS");
        for (int i = 0; i < numVertices(); i++) {
            for (int j = 0; j < numVertices(); j++) {
                System.out.print(caminos[i][j] + "\t");
            }
            System.out.println("");
        }
    }

}
