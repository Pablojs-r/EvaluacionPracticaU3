/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.controaldor;

import java.io.Serializable;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author pablo
 */
public class PilaCola<T> extends Lista<T> implements Serializable {

    private Integer nro_elementos;
    private String tipo;
    static Queue<String> queue;

    public PilaCola(Integer nro_elementos, String tipo) {
        this.nro_elementos = nro_elementos;
        this.tipo = tipo;
    }

    public PilaCola() {
    }

    public boolean estaLena() {
        return !(tamanio() < nro_elementos);
    }

    public boolean push(T dato) {
        if (tipo.equalsIgnoreCase("PILA")) {
            if (!estaLena()) {
                insertar(dato, 0);
                return true;
            }
        } else {
            System.out.println("No es una pila");
        }
        return false;
    }

    public T pop() {
        T tmp = null;
        if (tipo.equalsIgnoreCase("PILA")) {
            if (estaLena()) {
                tmp = this.extraer();
            } else {
                System.out.println("No hay elementos");
            }
        } else {
            System.out.println("No es una pila");
        }
        return tmp;
    }

    public boolean queue(T dato) {
        if (tipo.equalsIgnoreCase("COLA")) {
            if (!estaLena()) {
                insertar(dato, tamanio());
                return true;
            }
        } else {
            System.out.println("No es una cola");
        }
        return false;
    }

    public T dequeue() {
        T tmp = null;
        if (tipo.equalsIgnoreCase("COLA")) {
            if (estaLena()) {
                tmp = this.extraer();
            } else {
                System.out.println("No hay elementos");
            }
        } else {
            System.out.println("No es una cola");
        }
        return tmp;
    }

    public void invertir() {
        Stack<String> stack = new Stack();
        while (!queue.isEmpty()) {
            stack.add(queue.peek());
            queue.remove();
        }
        while (!stack.isEmpty()) {
            queue.add(stack.peek());
            queue.poll();
        }

    }

    public static void main(String[] args) {
        System.out.println("---PILA");
        PilaCola<String> pila = new PilaCola(3, "PILA");
        for (int i = 0; i < 3; i++) {
            if (pila.push("Hola " + i));
        }
        pila.imprimir();
        System.out.println("***************************");
        System.out.println("Se extrae: " + pila.pop());
        pila.imprimir();
        System.out.println("***************************");

        System.out.println("---COLA");
        PilaCola<String> cola = new PilaCola(3, "COLA");
        PilaCola<String> aux = new PilaCola(3, "COLA");
        for (int i = 0; i < 3; i++) {
            if (cola.queue("Hola " + i));
        }
        cola.imprimir();
        System.out.println("***********************************");
        cola.imprimir();
    }

}
