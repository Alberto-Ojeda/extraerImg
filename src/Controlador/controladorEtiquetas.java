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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfChunk;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfEFStream;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.PDFSplit;

/**
 *
 * @author alberto
 */
public class controladorEtiquetas implements ActionListener {

    /* creacion del archivo que se utilizara en modo lectura para su modificacion y extracción de las etiquetas
    junto con las variables que estaran a nivel global*/
    JFileChooser selecArchivo = new JFileChooser();
    int contAccion = 0;
    File archivo;
    File archivo1;    
    File archivo2;
    vista vistaI = new vista();
    modeloEtiquetas modeloE = new modeloEtiquetas();

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
/*              ModificarStamper(archivo);
                ModificarStamper2(archivo);*/
                tamaño(archivo);
                tamaño2(archivo);
                Agregar(archivo);
                //              ModificarPdfbox(archivo);
            } else {
                JOptionPane.showMessageDialog(null, "agregue la extencion .pdf" + "\n  ejemplo nombre.pdf ");
            }
        }

    }

    public void ModificarStamper(File archivo) throws IOException, DocumentException {
        File archivo3 = null;
        String ruta = archivo.getAbsolutePath();
        PdfReader reader = new PdfReader(ruta);
        System.out.println(reader.getPageSize(2).toString());
        int width = 131;
        int height = 131;
        Rectangle rec = new Rectangle(width, height);
        if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            archivo2 = selecArchivo.getSelectedFile();
        }

        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream(archivo2)); // output PDF

        BaseFont bf = BaseFont.createFont(
                BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {

            PdfContentByte over = stamper.getOverContent(i);
            over.rectangle(rec);
            over.rectangle(rec);
            // write text
            /*           over.beginText();
            over.setFontAndSize(bf, 10);    // escribir texto, modificar su tamaño y su posición 
            over.setTextMatrix(107, 740);   // set x,y position (0,0 is at the bottom left)
            over.showText("I can write at page " + i);  // set text
            over.endText();
             */
            // draw a red circle
            over.setRGBColorStroke(0xFF, 0xFF, 0xFF);
            over.lineTo(5, 5);
            over.setLineWidth(200f);
            over.setTextMatrix(200, 200);

            //          over.rectangle(200,600, 200, 200);
            over.rectangle(200, 200, 200, 200); //para conseguir el primer rectangulo estas son las medidas

            over.stroke();
        }

        stamper.close();

    }

    public void tamaño(File archivo) throws IOException, DocumentException {
//        int width = 841;
//        int height = 595;
//        Rectangle rec = new Rectangle(width, height);

        PdfReader reader = new PdfReader(archivo.getAbsolutePath());
        if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            archivo2 = selecArchivo.getSelectedFile();
        }
        
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(archivo2));
        //stamper.insertPage(2, rec);
        int n = reader.getNumberOfPages();
        PdfDictionary page;
        PdfArray crop;
        PdfArray media;
        for (int p = 1; p <= n; p++) {
            page = reader.getPageN(p);
            media = page.getAsArray(PdfName.CROPBOX);
            if (media == null) {
                media = page.getAsArray(PdfName.MEDIABOX);
            }
            crop = new PdfArray();
            crop.add(new PdfNumber(0));
            crop.add(new PdfNumber(0));
            crop.add(new PdfNumber(media.getAsNumber(2).floatValue() / 2));
            crop.add(new PdfNumber(media.getAsNumber(3).floatValue() / 3));
            page.put(PdfName.MEDIABOX, crop);
            page.put(PdfName.CROPBOX, crop);

            stamper.getUnderContent(p).setLiteral("\nq .95 0 0 .95 -133 -190 cm\nq");

        }
        stamper.close();
        System.out.println(reader.getPageSize(2).toString());
        reader.close();
        System.out.println(reader.getPageSize(2).toString());
    }

    public void tamaño2(File archivo) throws IOException, DocumentException {
//        int width = 841;
//        int height = 595;
//        Rectangle rec = new Rectangle(width, height);

        PdfReader reader = new PdfReader(archivo.getAbsolutePath());
        if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            archivo2 = selecArchivo.getSelectedFile();
        }
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(archivo2));
        //stamper.insertPage(2, rec);
        int n = reader.getNumberOfPages();
        PdfDictionary page;
        PdfArray crop;
        PdfArray media;
        for (int p = 1; p <= n; p++) {
            page = reader.getPageN(p);
            media = page.getAsArray(PdfName.CROPBOX);
            if (media == null) {
                media = page.getAsArray(PdfName.MEDIABOX);
            }
            crop = new PdfArray();
            crop.add(new PdfNumber(0));
            crop.add(new PdfNumber(0));
            crop.add(new PdfNumber(media.getAsNumber(2).floatValue() / 2));
            crop.add(new PdfNumber(media.getAsNumber(3).floatValue() / 3));
            page.put(PdfName.MEDIABOX, crop);
            page.put(PdfName.CROPBOX, crop);

            stamper.getUnderContent(p).setLiteral("\nq .95 0 0 .95 -133 -492 cm\nq");

        }
        stamper.close();
        System.out.println(reader.getPageSize(2).toString());
        reader.close();
        System.out.println(reader.getPageSize(2).toString());
    }

    public void ModificarStamper2(File archivo) throws IOException, DocumentException {
        File archivo3 = null;
        String ruta = archivo.getAbsolutePath();
        PdfReader reader = new PdfReader(ruta);
        int width = 131;
        int height = 131;
        Rectangle rec = new Rectangle(width, height);
        if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            archivo2 = selecArchivo.getSelectedFile();
        }

        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream(archivo2)); // output PDF
        BaseFont bf = BaseFont.createFont(
                BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font

        //loop on pages (1-based)
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {

            // get object for writing over the existing content;
            // you can also use getUnderContent for writing in the bottom layer
            PdfContentByte over = stamper.getOverContent(i);
            over.beginText();
            over.setFontAndSize(bf, 10);    // set font and size
            over.setTextMatrix(107, 740);   // set x,y position (0,0 is at the bottom left)

            over.rectangle(rec);
            over.endText();
            over.rectangle(rec);
            // write text
            /*           over.beginText();
            over.setFontAndSize(bf, 10);    // set font and size
            over.setTextMatrix(107, 740);   // set x,y position (0,0 is at the bottom left)
            over.showText("I can write at page " + i);  // set text
            over.endText();
             */
            // draw a red circle
            over.setRGBColorStroke(0xFF, 0xFF, 0xFF);
            over.lineTo(5, 5);
            over.setLineWidth(200f);
            over.setTextMatrix(200, 200);

            over.rectangle(200, 600, 200, 200);
//            over.rectangle(200,200, 200, 200); //para conseguir el primer rectangulo estas son las medidas

            over.stroke();
        }

        stamper.close();

    }

    public void Agregar(File archivo) throws IOException, DocumentException {
        File main_file = null, to_be_inserted = null,dest = null;
        
        if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            main_file = selecArchivo.getSelectedFile();
        }
            PdfReader reader = new PdfReader(main_file.getPath());
        if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            to_be_inserted = selecArchivo.getSelectedFile();}
            PdfReader reader2 = new PdfReader(to_be_inserted.getPath());
// Create a stamper
        if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            dest = selecArchivo.getSelectedFile();}
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
           int impares=0;
           int impares2=1;

            for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
        // Create an imported page to be inserted
        if (i % 2 != 0) {
            
            if (i>1) {
            impares=impares+2;
            System.out.println(impares);
            PdfImportedPage page = stamper.getImportedPage(reader2, i);
            stamper.insertPage(impares+i, reader2.getPageSize(i));
            stamper.getUnderContent(impares+i).addTemplate(page, 0, 0);
            }else{
            PdfImportedPage page = stamper.getImportedPage(reader2, i);
            stamper.insertPage(i, reader2.getPageSize(i));
            stamper.getUnderContent(i).addTemplate(page, 0, 0);
            }      
                    }else{
            if (i>2) {
            impares2=impares2+2;
            PdfImportedPage page = stamper.getImportedPage(reader2, i);
            stamper.insertPage(impares2+i, reader2.getPageSize(i));
            stamper.getUnderContent(impares2+i).addTemplate(page, 0, 0);
                
            }else{
            PdfImportedPage page = stamper.getImportedPage(reader2, i);
            stamper.insertPage(i+1, reader2.getPageSize(i));
            stamper.getUnderContent(i+1).addTemplate(page, 0, 0);
        }}
        
// Close the stamper and the readers
    }
            stamper.close();
            reader.close();
            reader2.close();
        }
        /* metodo experimental para poder leer y modificar el pdf existente.*/
    public void ModificarPdfbox(File archivo) throws IOException, DocumentException {
        File newfile = new File(archivo.getPath());

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
