package converter;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.RandomAccess;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.util.PDFImageWriter;

import aufgaben_db.Global;

import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.MimeMapping;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.io.IEntryInputStreamProvider;


/**
 * TODO see how xdocreport french colleagues program their converters. Also use automatic registering 
	of source-target pairs according to converters found in the converter package. File.list()?
 */
public class PDFToImage extends Converter implements IConverter{

    private static final String /*DocumentKind*/ to = "pdf"; /*TODO this is too static => more dynamic?*/
    private static int width = 1024;
    
    public static void convert(File sourceFile) {
    	convert(sourceFile, null, null);
    }
    /**
     * 
     * @param sourceFile
     * @param targetDirOrFile
     * @param options
     */
    public static void convert(String sourceFile, String targetDirOrFile) {
    	convert(new File(sourceFile), new File(targetDirOrFile), null);
    }
    public static void convert(File sourceFile, File targetDirOrFile, Options options) {
    	
    	String target_type = "jpg";
		if (options != null && options.getTo() != null && options.getTo() != "") {
			target_type = options.getTo();
		}
		
    	if (targetDirOrFile == null) {
    		targetDirOrFile = new File(Global.replaceEnding(sourceFile.getAbsolutePath(), target_type)); 
    	}
    	File targetDir = targetDirOrFile;
    	File targetFile = null;
    	if (!targetDirOrFile.isDirectory()) {//Global.isFile(targetFile)) {
    		targetFile = targetDirOrFile;
    		targetDir = targetFile.getParentFile();
    		//TODO Investigate: This filepath might still specify a path without ending.
    		target_type = Global.extractEnding(targetDirOrFile.getAbsolutePath()); 
    	}
    	
    	
    	if (!sourceFile.exists()) {
    		System.err.println("Source file " + sourceFile.getAbsolutePath() + " not exists!");
    		return ;
    	}
    	
    	try {
    		
    		/*
			 Using GHOST4J (having all the native libraries (.dll, .so, ..) on the classpath.
			 As there occurred problems it might be required to compile for other operating systems. For reference see:
			 http://ghostscript.com/doc/8.54/Make.htm#Cross-compiling
			 http://stackoverflow.com/questions/17653569/unable-to-load-library-gs-libgs-so-cannot-open-shared-object-file-no-such-f
			 */
			//Load the file:
			org.ghost4j.document.PDFDocument document = new org.ghost4j.document.PDFDocument();
			document.load(sourceFile);
			
			//Setup the Ghost renderer:
			org.ghost4j.renderer.SimpleRenderer renderer = new org.ghost4j.renderer.SimpleRenderer();
			//options:
			renderer.setResolution(100);//<-- set resolution in dpi (dots per inch).
			renderer.setMaxProcessCount(1);
			//Render/convert:
			List<Image> images = renderer.render(document);

			
			//Write it as png file:
			int images_i = -1;
			String image_filelink_base = Global.replaceEnding(targetFile.getAbsolutePath(), "");
			while (++images_i < images.size()) {
				if (images_i > 0) {
					ImageIO.write((RenderedImage)images.get(images_i)//.getScaledInstance(width, images.get(images_i).getHeight(null)*getWidth/1024, Image.SCALE_FAST)
							, target_type
							, new File(image_filelink_base + (images_i + 1) + Global.extractEnding(targetFile.getAbsolutePath()))
					);
				}
				else { //images_i == 0
					ImageIO.write((RenderedImage)images.get(images_i), target_type, targetFile);
				}
				
			}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	
    	if (targetFile.exists()) {
    		System.out.println("[success-ghost4j] Target file (pdf first page as image) " + targetFile.getAbsolutePath());
    		return ;
    	}
    	
		/*
		 Using Apache PDFBox: http://pdfbox.apache.org/commandline/#pdfToImage
		 */
		//org.apache.pdfbox.ExtractImages;
		//org.apache.pdfbox.PDFToImage;
		//Load the file:
		PDDocument document = null;
		try {
//			document = PDDocument.loadNonSeq(sourceFile, new org.apache.pdfbox.io.RandomAccessFile(new File(sourceFile.getAbsolutePath() + "--tmp--pdf-creation"), "rw"));
			document = PDDocument.load(sourceFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (document != null) {
	    	try {
				
				List<PDPage> pages = document.getDocumentCatalog().getAllPages();
	
				//Write it as png file:
				boolean first_page_only_condition = options != null 
						&& options.getProperty("convertFirstPageOnly") != null
						&& options.getProperty("convertFirstPageOnly") instanceof Short
						&& (short)options.getProperty("convertFirstPageOnly") == 1;
				
				
				int page_i = -1;
				int length = pages.size();
				while (++page_i < length) {
					//A) following http://stackoverflow.com/questions/20862335/pdfbox-converttoimage-not-rendering-some-pdfs-correctly
					//ImageIO.write((RenderedImage) pages .get(page_i).convertToImage(), "png", new File("pdf--page_" + (page_i + 1) + ".png"));
				
					//B) following PDFRenderer source code (strange it seems non-existent in pdfbox<version>.jar!)
					org.apache.pdfbox.rendering.PDFRenderer renderer = new org.apache.pdfbox.rendering.PDFRenderer(document);
					BufferedImage bufImg = renderer.renderImage(page_i, 1.0f, ImageType.RGB);//<-- JPG (as AlphaRGB is rather PNG)
					//only for the first page the initial target filelink as given per parameter will be used:
					if (targetFile == null || page_i > 0) {
						//derive filename from source file: e.g. Aufgabe_1--1.jpg for page 1 and so forth.
						//TODO will this ever be reached?
						String file =  sourceFile.getName() + "--" + (page_i + 1) + "." + target_type;//TODO Check if ImageType above in renderImage changes.
						targetFile = new File(targetDir.getAbsolutePath() + System.getProperty("file.separator") + file);
					}
					ImageIO.write(bufImg, target_type, targetFile);
					System.out.println("Stored image: " + targetFile.getAbsolutePath());
					
					if (first_page_only_condition) {
						break;
					}
					
	
				}
				
				if (first_page_only_condition) {
					System.out.println("Total pages converted: " + page_i + " (first page only).");
				}
				else {
					System.out.println("Total pages converted: " + page_i); //<-- To have it separated by : makes automatic parsing easier.
				}
				
	            
	    	}
	    	catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	finally {
	    		if (document != null) {
	    			try {
						document.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}
	    	}
		}
    }
		
    
    
    
    
    
    
//import java.awt.HeadlessException;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//import org.apache.pdfbox.exceptions.InvalidPasswordException;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.util.PDFImageWriter;
    /**
	  * Convert a PDF document to an image.
	  *
	  * @author <a href="ben@benlitchfield.com">Ben Litchfield</a>
	  * @version $Revision: 1.6 $
	  */
    private static final String PASSWORD = "-password";
    private static final String START_PAGE = "-startPage";
    private static final String END_PAGE = "-endPage";
    private static final String IMAGE_FORMAT = "-imageType";
    private static final String OUTPUT_PREFIX = "-outputPrefix";
    private static final String COLOR = "-color";
    private static final String RESOLUTION = "-resolution";

    /**
     * private constructor.
    */
    private PDFToImage()
    {
        //static class
    }

    /**
     * Infamous main method.
     *
     * @param args Command line arguments, should be one and a reference to a file.
     * @throws IOException 
     *
     * @throws Exception If there is an error parsing the document.
     */
    // The superclass makes trouble here:
//    public static void main( String[] args ) throws IOException
//    {
//   	 	convert(args);
//   	 
//    }
    public static void convert(String[] args) throws IOException {
        String password = "";
        String pdfFile = null;//"";//D:/Dokumentation.pdf";
        String outputPrefix = "";//D:/
        String imageFormat = "jpg";
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;
        String color = "rgb";
        int resolution;
        try
        {
            resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        }
        catch( HeadlessException e )
        {
            resolution = 96;
        }
        for( int i = 0; i < args.length; i++ )
        {
            if( args[i].equals( PASSWORD ) )
            {
                i++;
                if( i >= args.length )
                {
                    usage();
                }
                password = args[i];
            }
            else if( args[i].equals( START_PAGE ) )
            {
                i++;
                if( i >= args.length )
                {
                    usage();
                }
                startPage = Integer.parseInt( args[i] );
            }
            else if( args[i].equals( END_PAGE ) )
            {
                i++;
                if( i >= args.length )
                {
                    usage();
                }
                endPage = Integer.parseInt( args[i] );
            }
            else if( args[i].equals( IMAGE_FORMAT ) )
            {
                i++;
                imageFormat = args[i];
            }
            else if( args[i].equals( OUTPUT_PREFIX ) )
            {
                i++;
                outputPrefix = args[i];
            }
            else if( args[i].equals( COLOR ) )
            {
                i++;
                color = args[i];
            }
            else if( args[i].equals( RESOLUTION ) )
            {
                i++;
                resolution = Integer.parseInt(args[i]);
            }
            else
            {
                if( pdfFile == null )
                {
                    pdfFile = args[i];
                }
            }
        }
        if( pdfFile == null )
        {
            usage();
        }
        else
        {
            if(outputPrefix == null)
            {
                outputPrefix = pdfFile.substring( 0, pdfFile.lastIndexOf( '.' ));
            }

            PDDocument document = null;
            try
            {
                document = PDDocument.load( pdfFile );     
                //document.print();

                if( document.isEncrypted() )
                {
                    try
                    {
                        document.decrypt( password );
                    }
                    catch( InvalidPasswordException e )
                    {
                        if( args.length == 4 )//they supplied the wrong password
                        {
                            System.err.println( "Error: The supplied password is incorrect." );
                            System.exit( 2 );
                        }
                        else
                        {
                            //they didn't supply a password and the default of "" was wrong.
                            System.err.println( "Error: The document is encrypted." );
                            usage();
                        }
                    }
                }
                int imageType = 24;
                if ("bilevel".equalsIgnoreCase(color))
                {
                    imageType = BufferedImage.TYPE_BYTE_BINARY;
                }
                else if ("indexed".equalsIgnoreCase(color))
                {
                    imageType = BufferedImage.TYPE_BYTE_INDEXED;
                }
                else if ("gray".equalsIgnoreCase(color))
                {
                    imageType = BufferedImage.TYPE_BYTE_GRAY;
                }
                else if ("rgb".equalsIgnoreCase(color))
                {
                    imageType = BufferedImage.TYPE_INT_RGB;
                }
                else if ("rgba".equalsIgnoreCase(color))
                {
                    imageType = BufferedImage.TYPE_INT_ARGB;
                }
                else
                {
                    System.err.println( "Error: the number of bits per pixel must be 1, 8 or 24." );
                    System.exit( 2 );
                }

                //Make the call
                PDFImageWriter imageWriter = new PDFImageWriter();
                boolean success = imageWriter.writeImage(document, imageFormat, password,
                        startPage, endPage, outputPrefix, imageType, resolution);
                if (!success)
                { /* org.apache.pdfbox.pdmodel.PDPage().convertToImage does not exist!! */
                    System.err.println( "Error: no writer found for image format '"
                            + imageFormat + "'" );
                    System.exit(1);
                }
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
            finally
            {
                if( document != null )
                {
                    document.close();
                }
            }
        }
    }

    /**
     * This will print the usage requirements and exit.
     */
    private static void usage()
    {
        System.err.println( "Usage: java org.apache.pdfbox.PDFToImage [OPTIONS] <PDF file>\n" +
            "  -password  <password>          Password to decrypt document\n" +
            "  -imageType <image type>        (" + getImageFormats() + ")\n" +
            "  -outputPrefix <output prefix>  Filename prefix for image files\n" +
            "  -startPage <number>            The first page to start extraction(1 based)\n" +
            "  -endPage <number>              The last page to extract(inclusive)\n" +
            "  -color <string>                The color depth (valid: bilevel, indexed, gray, rgb, rgba)\n" +
            "  -resolution <number>           The bitmap resolution in dpi\n" +
            "  <PDF file>                     The PDF document to use\n"
            );
        System.exit(1);
    }

    private static String getImageFormats()
    {
        StringBuffer retval = new StringBuffer();
        String[] formats = ImageIO.getReaderFormatNames();
        for( int i = 0; i < formats.length; i++ )
        {
            retval.append( formats[i] );
            if( i + 1 < formats.length )
            {
                retval.append( "," );
            }
        }
        return retval.toString();
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
    