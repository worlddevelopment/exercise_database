package converter;


import com.lowagie.text.Document;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import java.awt.Image;
import java.awt.Rectangle;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.*;
/** by Lucas Jellema:
 *The steps for achieving this are amazingly simple:

Steps:

1. Download iText

Go to http://www.lowagie.com/iText/download.html and download iText-2.1.4.jar.

2. Download PDF Renderer

Go to https://pdf-renderer.dev.java.net/ and download PDFRenderer.jar

3. Create Project

In your favorite IDE, create an Application/Workspace/Project – whatever you need to get going on an application. Add the two JAR-files you just downloaded as libraries.

4. Create Class

Create a class that creates a PDF document on the fly and then has its first page turned into an image that can be previewed, for example in a Swing Panel: 
 *
 */
//technology.amis.nl/2008/11/28/java-generating-pdf-and-previewing-it-as-an-image-itext-and-pdf-renderer/
//Original source: http://java-polis.blogspot.com/2007/11/creating-pdf-documents-dynamically-in.html
public class PDFToImageUsingPDFViewSwing {
	

    private static ByteArrayOutputStream createPDF() throws DocumentException,
                                                            MalformedURLException,
                                                            IOException {
        Document doc = new Document();
        ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
        PdfWriter docWriter = null;
        docWriter = PdfWriter.getInstance(doc, baosPDF);
        doc.open();
        URL imageUrl = new URL(&quot;http://www.bruinenfit.nl/images/pdf-logo.jpg&quot;);
        com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(imageUrl);

        image.scaleToFit(300,100);
        image.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
        doc.add(image);
        doc.add(new Paragraph("This special PDF document was created on " +
                              new java.util.Date()));

        doc.close();
        docWriter.close();
        return baosPDF;
    }

    public static void previewPDFDocumentInImage() throws IOException {
        ByteBuffer buf = null;

        try {
            buf = ByteBuffer.wrap(createPDF().toByteArray());
        } catch (DocumentException e) {
        }
        // use the PDF&nbsp;Renderer library on the buf which contains the in memory PDF document
        PDFFile pdffile = new PDFFile(buf);
        PDFPage page = pdffile.getPage(1);

        //get the width and height for the doc at the default zoom
        Rectangle rect =
            new Rectangle(0, 0, (int)page.getBBox().getWidth(), (int)page.getBBox().getHeight());

        //generate the image
        Image img = page.getImage(rect.width, rect.height, //width &amp; height
                rect, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true) // block until drawing is done
        ;

        //show the image in a frame
        JFrame frame = new JFrame("My incredible PDF document");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        PDFToImageUsingPDFViewSwing.previewPDFDocumentInImage();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
    }
}

