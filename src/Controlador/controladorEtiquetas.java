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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author alberto
 */
public class controladorEtiquetas implements ActionListener{
    JFileChooser selecArchivo = new JFileChooser();
    int contAccion = 0;
    File archivo;
    File archivo2;
    vista vistaI= new vista();
    modeloEtiquetas modeloE= new modeloEtiquetas();
    
  public controladorEtiquetas(vista vistaI, modeloEtiquetas modeloE) {
        this.vistaI = vistaI;
        this.vistaI.importar.addActionListener(this);
    }
  
  public void AgregarFiltro() {
        selecArchivo.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));

    }
 
    
    
    
    public void importar() throws IOException, DocumentException {
       if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            archivo = selecArchivo.getSelectedFile();
            if (archivo.getName().endsWith("pdf")) {
                JOptionPane.showMessageDialog(null, "importación exitosa" + "\n Formato" + archivo.getName().substring(archivo.getName().indexOf(".")));
                modeloE.importarT(archivo, vistaI.tablapdf);
                modeloE.addCheckBox(archivo, vistaI.tablapdf);
                ModificarPdfbox(archivo);
            } else {
                JOptionPane.showMessageDialog(null, "agregue la extencion .pdf" + "\n  ejemplo nombre.pdf ");
            }
        }
    
}
    public void ModificarStamper( File archivo) throws IOException, DocumentException{
         
        
        System.out.println(archivo.getPath());
        String ruta= archivo.getAbsolutePath();
        PdfReader reader= new PdfReader(ruta);
        
       if(selecArchivo.showDialog(null, "Crear")==JFileChooser.APPROVE_OPTION){
            archivo2=selecArchivo.getSelectedFile();
         }
        Document doc = new Document(PageSize.LEGAL_LANDSCAPE);        
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(archivo2));
      stamper.addAnnotation(PdfAnnotation.createText(stamper.getWriter(), new Rectangle(30f, 750f, 80f, 800f), "inserted page", "This page is the title page.", true, null),
     reader.getNumberOfPages());

        stamper.close();
        reader.close();

       
    }   
    public void ModificarPdfbox( File archivo) throws IOException, DocumentException{    
        PDDocument doc = PDDocument.load(archivo);
        PDPage page = doc.getPage(1);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        contentStream.beginText();
        contentStream.newLineAtOffset(100,100);
        contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
        String text = "This is the sample document and we are adding content to it.";
        contentStream.showText(text);      
contentStream.endText();

      System.out.println("Content added");

      //Closing the content stream
      contentStream.close();

      //Saving the document
      if(selecArchivo.showDialog(null, "Crear")==JFileChooser.APPROVE_OPTION){
            archivo2=selecArchivo.getSelectedFile();
         }
      doc.save(new FileOutputStream(archivo2));

      //Closing the document
      doc.close();
    }
    
    public void actionPerformed(ActionEvent e) {
        contAccion++;
        if (contAccion == 1) {
            AgregarFiltro();

        }
        if (e.getSource() == vistaI.importar) {
            try {
                importar();
            } catch (IOException ex) {
                Logger.getLogger(controladorEtiquetas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(controladorEtiquetas.class.getName()).log(Level.SEVERE, null, ex);
            }

             
        }
      

    }
    
}
