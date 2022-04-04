/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import Vista.vista;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Modelo.modeloEtiquetas;
/**
 *
 * @author alberto
 */
public class controladorEtiquetas implements ActionListener{
    JFileChooser selecArchivo = new JFileChooser();
    int contAccion = 0;
    File archivo;
    vista vistaI= new vista();
    
  public controladorEtiquetas(vista vistaI, modeloEtiquetas modeloE) {
        this.vistaI = vistaI;
        this.vistaI.importar.addActionListener(this);
    }
  
  public void AgregarFiltro() {
        selecArchivo.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));

    }
  
    public void importar() {
       if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            archivo = selecArchivo.getSelectedFile();
            if (archivo.getName().endsWith("pdf")) {
                JOptionPane.showMessageDialog(null, "importación exitosa" + "\n Formato" + archivo.getName().substring(archivo.getName().indexOf(".")));
            } else {
                JOptionPane.showMessageDialog(null, "agregue la extencion .pdf" + "\n  ejemplo nombre.pdf ");
            }
        }
    
}
    
    
    
    
    public void actionPerformed(ActionEvent e) {
        contAccion++;
        if (contAccion == 1) {
            AgregarFiltro();
        }
        if (e.getSource() == vistaI.importar) {
            
                importar();
             
        }
      

    }
    
}
