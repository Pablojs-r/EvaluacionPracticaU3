/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.ModeloTabla;

import Controlador.grafo.GrafoEtiquetaND;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Home
 */
public class TableEscuela extends AbstractTableModel{
    private GrafoEtiquetaND grafoND;
    private String[] columnas;

    public GrafoEtiquetaND getGrafoD() {
        return grafoND;
    }

    public void setGrafoND(GrafoEtiquetaND grafoD) {
        this.grafoND = grafoD;
        generarColumnas();
    }
    
    @Override
    public int getRowCount() {
        return grafoND.numVertices();
    }

    @Override
    public int getColumnCount() {
        return grafoND.numVertices() + 1;
    }
    
    private String[] generarColumnas() {
        columnas = new String[grafoND.numVertices() + 1];
        columnas[0] = "--";
        for(int i = 1; i < columnas.length;i++) {
            columnas[i] = grafoND.obtenerEtiqueta(i).toString();
        }
        return columnas;
    }

    @Override
    public String getColumnName(int i) {
        return columnas[i]; //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public Object getValueAt(int i, int i1) {
        if(i1 == 0) {
            return columnas[i + 1];
        } else {
            try {
                if(grafoND.existeArista((i+1), i1)) {
                    return grafoND.pesoArista(i+1, i1);
                } else {
                    return "--";
                }
            } catch (Exception e) {
                System.out.println("Error en ver arista");
            }
        }
        return "";
    }
    
}
