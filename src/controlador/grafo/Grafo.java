/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.grafo;

import Controlador.exepcion.AdyacenciaExepcion;
import java.io.Serializable;
import java.util.Objects;
import lista.controaldor.Lista;
import lista.controaldor.PilaCola;

/**
 *
 * @author pablo
 */
public abstract class Grafo implements Serializable {

    private Integer[][] matrizAdy;
    private Integer[] camino;
    private Integer[] D;
    private Boolean[] F;

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

    private Double[] distancia;
    private Boolean [] v;
    
    public Integer[] Dijkstra(Integer origen, Integer fin) {
        v = new Boolean[matrizAdy.length];
        distancia = new Double[matrizAdy.length];
        for (int i = 0; i < matrizAdy.length; i++) {
            distancia[i] = Double.MAX_VALUE;
            v[i] = false;
        }
        distancia[origen] = 0.0;
        Integer camino[] = new Integer[matrizAdy.length];
        camino[origen] = Integer.MIN_VALUE;
        for (int i = 0; i < matrizAdy.length; i++) {
            Integer na = caminoMin(distancia, v);
        }
        Integer camcorto[] = new Integer[this.camino.length];
        Integer i = 0;
        while (!Objects.equals(origen, fin)) {
            camcorto[i] = fin;
            fin = this.camino[fin];
            i++;
        }
        camcorto[i] = origen;
        return camcorto;
    }

    public Integer caminoMin(Double d[], Boolean m[]) {
        double min = Integer.MAX_VALUE;
        int aux = -1;
        for (int i = 0; i < matrizAdy.length; i++) {
            if (m[i] == false && d[i] <= min) {
                min = d[i];
                aux = i;
            }
        }
        return aux;
    }
    
    private Double[][] Pesos;
    private int[] ultimo;
    private Double[] d;
    private Boolean[] f;
    private Integer s, n; //Vertice origen y numero de vertices
    
     public void DijkstraCam(int origen) {
         s = origen;
         n =numVertices();
        for (int i = 0; i < n; i++) {
            f[i] = false;
            d[i] = Pesos[s][i];
            ultimo[i] = s;
        }
        D[s] = 0;
        F[s] = true;
        for (int i = 0; i < n; i++) {
            int v = minimos();
            F[v] = true;
            for (int w = 0; w < n; w++) {
                if (!F[w]) {
                    if ((D[v] + Pesos[v][w]) < D[w]) {
                        d[w] = d[v] + Pesos[v][w];
                        ultimo[w] = v;
                    }
                }
            }
        }
    }

    public int minimos() {
        double mx = 1000000.0;
        int v = 1;
        for (int i = 0; i < n; i++) {
            if (!F[i] && D[i] <= mx) {
                mx = D[i];
                v = i;
            }
        }
        return v;
    }

    public int minimo(double dist[], boolean b[]) {
        double min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < 5; i++) {
            if (b[i] == false && dist[i] <= min) {
                min = dist[i];
                index = i;
            }
        }
        return index;
    }

    public Lista<Integer> ruta(Integer destino, Lista<Integer> caminos) {
        destino = destino;
        Integer anterior = ultimo[destino];
        if (!Objects.equals(destino, s)) {
            ruta(anterior, caminos);
            caminos.insertarNodo(destino);
        } else {
            caminos.insertarNodo(s);
        }
        return caminos;
    }
}
