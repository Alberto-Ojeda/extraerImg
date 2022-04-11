/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javax.swing.JTable;
import Vista.vista;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
/**
 *
 * @author alberto
 */
public class modeloEtiquetas {
    int indicecolumna=0;
    public void importarT(File archivo, JTable table){
        
                
        String respuesta="No se pudo realizar la importación.";
        DefaultTableModel modeloT = new DefaultTableModel();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        modeloT.addColumn("ID");
        modeloT.addColumn("Nombre del archivo");
        modeloT.addColumn("Ruta");
        for (int i = 0; i < modeloT.getColumnCount(); i++) {
            
        }
        
        
//        modeloT.addRow(rowData);
//       listaColumna[indiceColumna]= (int)Math.round(celda.getNumericCellValue());              
}
        public void addCheckBox (File archivo, JTable table) {
      
        table.setValueAt(archivo.getName(), 0, 0);
            table.setValueAt(archivo.getName(), 0, 1);
            table.setValueAt(archivo.getAbsolutePath(), 0, 2);
            }

        
    }

