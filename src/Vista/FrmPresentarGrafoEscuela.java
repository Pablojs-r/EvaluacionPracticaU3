/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.grafo.Adyacencia;
import Controlador.grafo.GrafoEtiquetaND;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxGraph;
import controladorDao.EscuelaDao;
import java.awt.BorderLayout;
import java.awt.Dimension;
import lista.controaldor.Lista;

/**
 *
 * @author pablo
 */
public class FrmPresentarGrafoEscuela extends javax.swing.JDialog {

    /**
     * Creates new form PresentarGrafoE1
     */
    private EscuelaDao ed = null;

    public FrmPresentarGrafoEscuela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public FrmPresentarGrafoEscuela(java.awt.Frame parent, boolean modal, EscuelaDao ed) {
        super(parent, modal);
        this.ed = ed;
        initComponents();
        mostrarDatos();
    }

    private void mostrarDatos() {
        mxGraph graph = new mxGraph();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setSize(new Dimension(450, 300));
        jPanel1.add(graphComponent, BorderLayout.CENTER);
        jPanel1.updateUI();

        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

        try {
            GrafoEtiquetaND rc = ed.getGrafo();
            Lista<Object> vertices = new Lista();
            for (int i = 0; i < rc.numVertices(); i++) {
                Object start = graph.insertVertex(parent, rc.obtenerEtiqueta(i + 1).toString() +" "+(i+1), rc.obtenerEtiqueta(i + 1).toString() +" "+ (i+1), 100, 100, 80, 30);
                vertices.insertarNodo(start);
            }
            for (int j = 0; j < rc.numVertices(); j++) {
                Lista<Adyacencia> lista = rc.adyacentes((j + 1));
                for (int k = 0; k < lista.tamanio(); k++) {
                    Adyacencia aux = lista.consultarDatoPosicion(k);
                    if ((vertices.consultarDatoPosicion(aux.getDestino() - 1) != null)) {
                        graph.insertEdge(parent, null, aux.getPeso(), vertices.consultarDatoPosicion(j) , vertices.consultarDatoPosicion(aux.getDestino() - 1));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Hubo un error en listar grafos");
        } finally {
            graph.getModel().endUpdate();
        }
        morphGraph(graph, graphComponent);
        new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
    }

    private static void morphGraph(mxGraph graph, mxGraphComponent graphComponent) {
        mxGraphLayout layout = new mxFastOrganicLayout(graph);
        try {
            layout.execute(graph.getDefaultParent());
        } catch (Exception e) {

        } finally {
            mxMorphing morph = new mxMorphing(graphComponent, 20, 1.5, 20);
            morph.addListener(mxEvent.DONE, new mxEventSource.mxIEventListener() {

                @Override
                public void invoke(Object o, mxEventObject eo) {
                    graph.getModel().endUpdate();
                }
            });
            morph.startAnimation();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Grafo"));
        jPanel1.setLayout(null);
        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 440, 330);

        setSize(new java.awt.Dimension(474, 385));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPresentarGrafoEscuela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPresentarGrafoEscuela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPresentarGrafoEscuela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPresentarGrafoEscuela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmPresentarGrafoEscuela dialog = new FrmPresentarGrafoEscuela(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}