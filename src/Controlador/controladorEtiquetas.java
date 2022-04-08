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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;

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
                ModificarStamper(archivo);
  //              ModificarPdfbox(archivo);
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
       PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(archivo));
       Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
         parrafo.setFont(FontFactory.getFont("Tahoma", 12, Font.BOLD, BaseColor.DARK_GRAY));
         parrafo.setIndentationLeft(8);
        parrafo.setIndentationRight(8);
        doc.open();
         parrafo.add("hola");

                doc.add(parrafo);
             
                           doc.close();

       PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(archivo2));
       stamper.addAnnotation(PdfAnnotation.createText(stamper.getWriter(), new Rectangle(30f, 750f, 80f, 800f), "inserted page", "This page is the title page.", true, null),
       reader.getNumberOfPages());

        stamper.close();
        reader.close();

       
    }   
    public void ModificarPdfbox( File archivo) throws IOException, DocumentException{    
       File newfile=new File(archivo.getPath());
        
        PDDocument pdfDocument = PDDocument.load(newfile);
  
        // PDFRenderer class to be Instantiated
        // i.e. creating it's object
        PDFRenderer pdfRenderer
            = new PDFRenderer(pdfDocument);
  
        // Rendering an image
        // from the PDF document
        // using BufferedImage class
        BufferedImage img = pdfRenderer.renderImage(0);
        // Writing the extracted
        // image to a new file
        ImageIO.write(
            img, "JPEG",
            new File("C:/Documents/GeeksforGeeks.png"));
        System.out.println(
            "Image has been extracted successfully");
  
        // Closing the PDF document
        pdfDocument.close();

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
