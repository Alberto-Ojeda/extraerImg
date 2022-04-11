/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;
import Vista.vista;
import Controlador.controladorEtiquetas;
import Modelo.modeloEtiquetas;
/**
 *
 * @author alberto
 * se declara nuestro main en donde se llamara a las clases que se crearon con 
 * anterioridad
 */
public class gestionArchivo {
     public static void main(String[] args) {
        
        vista vistaI = new vista();
        modeloEtiquetas modeloE= new modeloEtiquetas();
        controladorEtiquetas control = new controladorEtiquetas(vistaI, modeloE);
        
        vistaI.setVisible(true);
        vistaI.setLocationRelativeTo(null);
         
    }
}
