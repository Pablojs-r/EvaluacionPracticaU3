/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.controaldor;

import java.io.Serializable;
import java.lang.reflect.Field;
import lista.modelo.Nodo;

/**
 *
 * @author pablo
 */
public class Lista<T> implements Serializable {

    private Nodo cabecera;
    private Class clazz;
    private Integer nro_elem;

    public static final Integer ASCENDENTE = 1;
    public static final Integer DESCENDENTE = 2;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean estaVacia() {
        return this.cabecera == null;
    }

    public boolean estaLlena() {
        return tamanio() == nro_elem;
    }

    private void insertar(T dato) {
        Nodo nodo = new Nodo(dato, cabecera);
        cabecera = nodo;
    }

    private void insertarFinal(T dato) {
        insertar(dato, tamanio());
    }

    /**
     * Insertar dato por pisicion
     *
     * @param dato El dato a insertr
     * @param pos La posicion a insertar
     */
    public void insertar(T dato, int pos) {
        if (estaVacia() || pos <= 0) {
            insertar(dato);
        } else {
            Nodo iterador = cabecera;
            for (int i = 0; i < pos - 1; i++) {
                if (iterador.getNodoSiguiente() == null) {
                    break;
                }
                iterador = iterador.getNodoSiguiente();
            }
            Nodo tmp = new Nodo(dato, iterador.getNodoSiguiente());
            iterador.setNodoSiguiente(tmp);
        }
    }

    /**
     * Agregar item a lista ascendente, queire decir que el primer elemento es
     * la cabecera
     *
     * @param dato El dato a agregar
     */
    public void insertarNodo(T dato) {
        if (tamanio() > 0) {
            insertarFinal(dato);
        } else {
            insertar(dato);
        }
    }

    /**
     * Retorno el tama;o de la lista return numero de elementos
     */
    public int tamanio() {
        int cont = 0;
        Nodo tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            cont++;
            tmp = tmp.getNodoSiguiente();
        }
        return cont;
    }

    /**
     * Permite extraer el primer dato de la lista
     *
     * @return el dato
     */
    public T extraer() {
        T dato = null;
        if (!estaVacia()) {
            dato = (T) cabecera.getDato();
            cabecera = cabecera.getNodoSiguiente();
        }
        return dato;
    }

    /**
     *
     * @Permite consulta dato por pisicion
     * @return dato encontrado en la posicion
     */
    public T consultarDatoPosicion(int pos) {
        T dato = null;
        if (!estaVacia() && (pos >= 0 && pos <= tamanio() - 1)) {
            Nodo tmp = cabecera;
            for (int i = 0; i < pos; i++) {
                tmp = tmp.getNodoSiguiente();
                if (tmp == null) {
                    break;
                }
            }
            if (tmp != null) {
                dato = (T) tmp.getDato();
            }
        }
        return dato;
    }

    public void imprimir() {
        Nodo tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            System.out.println(tmp.getDato());
            tmp = tmp.getNodoSiguiente();
        }
    }

    public void eliminarNodo(T dato) {
        Nodo tmp = cabecera;
        Nodo anterior = null;
        while (!estaVacia() && tmp != null) {
            if (tmp == dato) {
                boolean x = tmp == dato;
                if (tmp == cabecera) {
                    cabecera = cabecera.getNodoSiguiente();
                } else {
                    anterior.setNodoSiguiente(tmp.getNodoSiguiente());
                }
            }

            anterior = tmp;
            tmp = tmp.getNodoSiguiente();
        }
    }

    public boolean modificarPorPos(T dato, int pos) {
        if (!estaVacia() && (pos <= tamanio() - 1) && pos >= 0) {
            Nodo iterador = cabecera;
            for (int i = 0; i < pos; i++) {
                iterador = iterador.getNodoSiguiente();
                if (iterador == null) {
                    break;
                }
            }
            if (iterador != null) {
                iterador.setDato(dato);
                return true;
            }
        }
        return false;
    }

    private Field getField(String nombre) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(nombre)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    private Object value(T dato, String atributo) throws Exception {
        return getField(atributo).get(dato);
    }

    public Lista<T> seleccion_clase(String atributo, Integer ordenacion) {
        try {
            int i, j, k = 0;
            T t = null;
            int n = tamanio();
            for (i = 0; i < n - 1; i++) {
                k = i;
                t = consultarDatoPosicion(i);
                for (j = i + 1; j < n; j++) {
                    boolean band = false;
                    Object datoT = value(t, atributo);
                    Object datoJ = value(consultarDatoPosicion(j), atributo);
                    if (datoT instanceof Number) {
                        Number aux = (Number) datoT;
                        Number numero = (Number) datoJ;
                        System.out.println("Numero:" + aux);
                        band = (ordenacion.intValue() == Lista.ASCENDENTE.intValue())
                                ? numero.doubleValue() < aux.doubleValue()
                                : numero.doubleValue() > aux.doubleValue();
                    } else {
                        band = (ordenacion.intValue() == Lista.ASCENDENTE.intValue())
                                ? datoT.toString().compareTo(datoJ.toString()) > 0
                                : datoT.toString().compareTo(datoJ.toString()) < 0;
                    }
                    if (band) {
                        t = consultarDatoPosicion(j);
                        k = j;
                    }
                }
                modificarPorPos(consultarDatoPosicion(i), k);
                modificarPorPos(t, i);
            }
        } catch (Exception e) {
            System.out.println("Error en ordenacion" + e);
        }
        return this;
    }

    public Lista<T> shell_clase(String atributo, Integer ordenacion) {
        try {
            int intervalo, i;
            T t;
            boolean cambios;
            for (intervalo = tamanio() / 2; intervalo != 0; intervalo /= 2) {
                cambios = true;
                while (cambios) {
                    cambios = false;
                    for (i = intervalo; i < tamanio(); i++) {
                        boolean band = false;
                        Object datoT = value(consultarDatoPosicion(i - intervalo), atributo);
                        Object datoJ = value(consultarDatoPosicion(i), atributo);
                        if (datoT instanceof Number) {
                            Number aux = (Number) datoT;
                            Number numero = (Number) datoJ;
                            band = (ordenacion.intValue() == Lista.ASCENDENTE.intValue())
                                    ? numero.doubleValue() < aux.doubleValue()
                                    : numero.doubleValue() > aux.doubleValue();
                        } else {
                            band = (ordenacion.intValue() == Lista.ASCENDENTE.intValue())
                                    ? datoT.toString().compareTo(datoJ.toString()) > 0
                                    : datoT.toString().compareTo(datoJ.toString()) < 0;
                        }
                        if (band) {
                            t = consultarDatoPosicion(i);
                            modificarPorPos(consultarDatoPosicion(i - intervalo), i);
                            modificarPorPos(t, i - intervalo);
                            cambios = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error en ordenacion" + e);
        }
        return this;
    }

    
}
