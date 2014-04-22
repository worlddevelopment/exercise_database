package converter;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.MimeMapping;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.io.IEntryInputStreamProvider;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Delivers several ways to generate an image out of a PDF file.
 * May be called from the command line as 'PDFToImageUsingPDFView <pdffile> <targetDirForGeneratedImages>'.
 * TODO IConverter or rather the own creation (Global.convert)? 
 */
public class PDFToImageUsingPDFView implements IConverter {

    public static void main(String[] args) {
    	if (args.length != 2) {
    		System.err.println("Usage: " + PDFToImageUsingPDFView.class + " <pdffile> <targetDirectoryForGeneratedImages>");
    		return;
    	}
    	
    	String source = args[0];//"*.pdf";
    	File sourceFile = new File(source);
    	if (!sourceFile.exists()) {
    		System.err.println("Source file not exists: " + source);
    		System.err.println("Usage: Pdf2Image <pdffile> <targetDirectoryForGeneratedImages>");
    	}
    	
    	String destination = args[1];//"directory/";

    	convert(sourceFile, new File(destination), 1200, 1400);
    	
    }
    
    
    public static void convert(File sourceFile, File targetDir) {
    	//http://blog.rubypdf.com/2008/01/30/pdftoimage-convert-pdf-to-image-by-using-pdfrenderer-library/

		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(sourceFile, "r");

			FileChannel channel = raf.getChannel();
			ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			PDFFile pdffile = new PDFFile(buf);
			// draw the first page to an image
			int num=pdffile.getNumPages();
			for(int i=0;i<num;i++)
			{
				PDFPage page = pdffile.getPage(i);
				
				//get the width and height for the doc at the default zoom				
				int width=(int)page.getBBox().getWidth();
				int height=(int)page.getBBox().getHeight();				
				
				Rectangle rect = new Rectangle(0,0,width,height);
				int rotation=page.getRotation();
				Rectangle rect1=rect;
				if(rotation==90 || rotation==270)
					rect1=new Rectangle(0,0,rect.height,rect.width);
				
				//generate the image
				BufferedImage img = (BufferedImage)page.getImage(
							rect.width, rect.height, //width & height
							rect1, // clip rect
							null, // null for the ImageObserver
							true, // fill background with white
							true  // block until drawing is done
					);

				ImageIO.write(img, "png", new File(targetDir + System.getProperty("file.separator") + i + ".png"));
			}
		} 
		catch (FileNotFoundException e1) {
			System.err.println(e1.getLocalizedMessage());
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}
    }
    
    
    
    public static void convert(File sourceFile, File targetDir, int width, int height) {

    	try {
    		if (!sourceFile.exists()) {
    			System.err.println("Source file " + sourceFile.getAbsolutePath() + " not exists!");
    			return ;
    		}
 
    		RandomAccessFile raf = new RandomAccessFile(sourceFile, "r");
    		FileChannel channel = raf.getChannel();
    		ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
    		PDFFile pdf = new PDFFile(buf);
    		int pageNumber = 1;
    		for (int i = 0; i < pdf.getNumPages(); i++) {
    			PDFPage page = pdf.getPage(i);
    			String fileName = sourceFile.getName().replace(".pdf", "");
    			
    			
    			// create the image
    			Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
    			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    			
    			// width & height, // clip rect, // null for the ImageObserver, // fill background with white, // block until drawing is done
    			Image image = page.getImage(width, height, rect, null, true, true );
    			Graphics2D bufImageGraphics = bufferedImage.createGraphics();
    			bufImageGraphics.drawImage(image, 0, 0, null);
    			ImageIO.write(bufferedImage, "png", new File( targetDir + fileName +"_"+ pageNumber +".png" ));
    			pageNumber++;
    		}
    	
            System.out.println("Total pages converted: "+ pageNumber);
            
            
            
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    
    }
    
    
    
	@Override
	public boolean canSupportEntries() {
		return false;
	}
	@Override
	public void convert(IEntryInputStreamProvider arg0, OutputStream arg1,
			Options arg2) throws XDocConverterException {
		
	}
	@Override
	public void convert(InputStream arg0, OutputStream arg1, Options arg2)
			throws XDocConverterException {
		
	}
	@Override
	public MimeMapping getMimeMapping() {
		return null;
	}
	@Override
	public boolean isDefault() {
		return false;
	}

    
}
    