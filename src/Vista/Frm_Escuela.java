/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.exepcion.AdyacenciaExepcion;
import Vista.ModeloTabla.TableEscuela;
import controladorDao.EscuelaDao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lista.controaldor.Lista;

/**
 *
 * @author Home
 */
public class Frm_Escuela extends javax.swing.JDialog {

    private EscuelaDao ed = new EscuelaDao();
    private TableEscuela modelo = new TableEscuela();
    private final Integer r = 6357;

    /**
     * Creates new form Frm_Escuela
     */
    public Frm_Escuela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiarDatos();
    }

    private void cargarTabla() {
        if (ed.getGrafo() != null) {
            modelo.setGrafoND(ed.getGrafo());
            tableescuelas.setModel(modelo);
            modelo.fireTableStructureChanged();
            tableescuelas.updateUI();
        } else {
            DefaultTableModel modelo = new DefaultTableModel(1, 1);
            tableescuelas.setModel(modelo);
            tableescuelas.updateUI();
        }
    }

    private void cargarCombos() {
        cbxorigen.removeAllItems();
        cbxdestino.removeAllItems();
        if (ed.getGrafo() != null) {
            for (int i = 0; i < ed.getGrafo().numVertices(); i++) {
                String label = ed.getGrafo().obtenerEtiqueta((i + 1)).toString();
                cbxorigen.addItem(label);
                cbxdestino.addItem(label);
            }
        }
    }

    private void limpiarDatos() {
        txtlatitud.setText("");
        txtlongitud.setText("");
        txtnombre.setText("");
        txtubicacion.setText("");
        ed.setEscuela(null);
        cargarTabla();
        cargarCombos();
    }

    private void agregarAdyacencias() {
        if (ed.getGrafo() != null) {
            if (cbxdestino.getSelectedIndex() == cbxorigen.getSelectedIndex()) {
                JOptionPane.showMessageDialog(null, "No se puede agregar un mismo valor ", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Integer p1 = cbxorigen.getSelectedIndex() + 1;
                Integer p2 = cbxdestino.getSelectedIndex() + 1;
                Double d = calcularDistancia(p1, p2);
                System.out.println("Distancia" + d);
                ed.getGrafo().InsertarArista(p1, p2, Math.round(d * 100.0) / 100.0);
                if (ed.actualizarGrafo(ed.getGrafo())) {
                    JOptionPane.showMessageDialog(null, "Grafo actualizado", "OK", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
                limpiarDatos();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Double calcularDistancia(Integer p1, Integer p2) {
        Double lat1 = ed.getGrafo().obtenerEtiqueta(p1).getLatitud();
        Double lat2 = ed.getGrafo().obtenerEtiqueta(p2).getLatitud();
        Double lon1 = ed.getGrafo().obtenerEtiqueta(p1).getLongitud();
        Double lon2 = ed.getGrafo().obtenerEtiqueta(p2).getLongitud();

        Double dlat = lat2 - lat1;
        Double dlon = lon2 - lon1;

        Double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = r * c;
        return c;
    }

    private Boolean validarDatos() {
        return (txtnombre.getText().trim().length() > 0 && txtubicacion.getText().trim().length() > 0 && txtlatitud.getText().trim().length() > 0
                && txtlongitud.getText().trim().length() > 0);
    }

    private void guardar() {
        if (validarDatos()) {
            try {
                ed.getEscuela().setLatitud(Double.parseDouble(txtlatitud.getText()));
                ed.getEscuela().setLongitud(Double.parseDouble(txtlongitud.getText()));
                ed.getEscuela().setNombre(txtnombre.getText());
                if (ed.guardar()) {
                    JOptionPane.showMessageDialog(null, "Se ha registrado", "OK", JOptionPane.INFORMATION_MESSAGE);
                    limpiarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos", "Error", JOptionPane.ERROR_MESSAGE);

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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtlongitud = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txtubicacion = new javax.swing.JTextField();
        txtlatitud = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableescuelas = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxdestino = new javax.swing.JComboBox<>();
        cbxorigen = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCamino = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        jPanel2.setLayout(null);

        jLabel1.setText("UBICACION");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 80, 80, 16);

        jLabel2.setText("LONGITUD");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(280, 80, 80, 16);

        jLabel3.setText("NOMBRE:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 30, 80, 16);

        jLabel4.setText("LATITUD");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(280, 30, 80, 16);
        jPanel2.add(txtlongitud);
        txtlongitud.setBounds(350, 70, 130, 30);
        jPanel2.add(txtnombre);
        txtnombre.setBounds(90, 24, 130, 30);
        jPanel2.add(txtubicacion);
        txtubicacion.setBounds(90, 74, 130, 30);
        jPanel2.add(txtlatitud);
        txtlatitud.setBounds(350, 20, 130, 30);

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(520, 70, 80, 32);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(50, 40, 620, 140);

        jPanel3.setLayout(null);

        tableescuelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableescuelas);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(10, 59, 630, 170);

        jLabel5.setText("Destino");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(190, 10, 41, 16);

        jLabel6.setText("Origen");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(20, 10, 37, 16);

        jPanel3.add(cbxdestino);
        cbxdestino.setBounds(240, 10, 100, 26);

        jPanel3.add(cbxorigen);
        cbxorigen.setBounds(70, 10, 100, 26);

        jButton2.setText("Agregar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);
        jButton2.setBounds(380, 10, 90, 32);

        jButton3.setText("Ver");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);
        jButton3.setBounds(490, 10, 77, 32);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(50, 200, 610, 240);

        txtCamino.setColumns(20);
        txtCamino.setRows(5);
        jScrollPane2.setViewportView(txtCamino);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(60, 480, 510, 100);

        jLabel7.setText("CAMINO MINIMO");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(70, 450, 120, 16);

        jButton4.setText("Camino");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(590, 500, 90, 32);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(-20, 0, 750, 590);

        setSize(new java.awt.Dimension(727, 636));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        guardar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        agregarAdyacencias();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new FrmPresentarGrafoEscuela(null, true, ed).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_Escuela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Escuela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Escuela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Escuela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Frm_Escuela dialog = new Frm_Escuela(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cbxdestino;
    private javax.swing.JComboBox<String> cbxorigen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableescuelas;
    private javax.swing.JTextArea txtCamino;
    private javax.swing.JTextField txtlatitud;
    private javax.swing.JTextField txtlongitud;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtubicacion;
    // End of variables declaration//GEN-END:variables
}
