/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorDao;

import Controlador.grafo.Adyacencia;
import Controlador.grafo.GrafoEtiquetaND;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lista.controaldor.Lista;
import modelo.Escuela;

/**
 *
 * @author pablo
 */
public class AdaptadorDao<T> implements InterfazDao<T> {
     private Class<T> clazz;
    private String carpeta = "datos" + File.separatorChar;
    private GrafoEtiquetaND<T> grafo;

    public AdaptadorDao(Class<T> clazz) {
        this.clazz = clazz;
        carpeta += this.clazz.getSimpleName().toLowerCase() + ".txt";
    }

    public GrafoEtiquetaND<T> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafoEtiquetaND<T> grafo) {
        this.grafo = grafo;
    }

    private void anadirVertice() {
        if (grafo == null) {
            grafo = new GrafoEtiquetaND<>(1);
        } else {
            GrafoEtiquetaND aux = new GrafoEtiquetaND(grafo.numVertices() + 1);
            for (int i = 1; i <= grafo.numVertices(); i++) {
                aux.etiquetaVertice(i, grafo.obtenerEtiqueta(i));
                Lista<Adyacencia> lista = grafo.adyacentes(i);
                for (int j = 0; j < lista.tamanio(); j++) {
                    Adyacencia ad = lista.consultarDatoPosicion(j);
                    aux.InsertarArista(i, ad.getDestino(), ad.getPeso());
                }
            }
            grafo = aux;
        }
    }

    public Boolean actualizarGrafo(GrafoEtiquetaND<T> grafoAux) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            oos.writeObject(grafoAux);
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en actualizar grafo" + e);
        }
        return false;
    }

    @Override
    public boolean guardar(T dato) {
        try {
            //guardar en grafo aumentar la dimension
            listarGrafo();
            anadirVertice();
            grafo.etiquetaVertice(grafo.numVertices(), dato);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            oos.writeObject(grafo);
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar" + e);
        }
        return false;
    }

    @Override
    public boolean modificar(T dato) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            grafo = listarGrafo();
            oos.writeObject(grafo);
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("No se ha guardado");
        }
        return false;
    }

    @Override
    public GrafoEtiquetaND<T> listarGrafo() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(carpeta));
            grafo = (GrafoEtiquetaND<T>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Error en listar: " + e);
        }
        return grafo;
    }

}
