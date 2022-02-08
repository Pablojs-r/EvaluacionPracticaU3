/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.exepcion;

/**
 *
 * @author pablo
 */
public class AdyacenciaExepcion extends Exception {

    /**
     * Creates a new instance of <code>AdyacenciaExepcion</code> without detail
     * message.
     */
    public AdyacenciaExepcion() {
    }

    /**
     * Constructs an instance of <code>AdyacenciaExepcion</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AdyacenciaExepcion(String msg) {
        super(msg);
    }
}
