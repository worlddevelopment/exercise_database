package converter;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sun.nio.zipfs.ZipDirectoryStream;
 


/**
 * Origin: http://www.journaldev.com/957/java-zip-example-to-zip-single-file-and-a-directory-recursively
 * TODO Change the recursive populateMethod by passing the zipStream instead? Could pay back as we have plenty of files.
 * 		Aim: Saving the extra loop and the heavy memory usage to store all files in a list which also
 *           involves copying around if the list is no longer big enough and has to be enlarged.
 *      This allows to get rid of static members too.
 */
public class Zip {
     
    List<String> filesListInDir = new ArrayList<String>();
 
//    public static void main(String[] args) {
//        File file = new File("/Users/pankaj/sitemap.xml");
//        String zipFileName = "/Users/pankaj/sitemap.zip";
//         
//        File dir = new File("/Users/pankaj/tmp");
//        String zipTargetFilelink = "/Users/pankaj/tmp.zip";
//         
//        zipSingleFile(file, zipFileName);
//         
//        Zip zip = new Zip();
//        zip.zipDirectory(dir, zipTargetFilelink);
//    }

    public static boolean zip(File file) {
    	String zipTargetFilelink = file.getAbsolutePath() + ".zip";
    	return zip(file, zipTargetFilelink);
    }
    public static boolean zip(File file, String zipTargetFilelink) {
    	
    	if (!file.exists()) {
    		return false;
    	}
    	if (file.isDirectory()) {
    		new Zip().zipDirectory(file, zipTargetFilelink);
    	}
    	else {
    		zipSingleFile(file, zipTargetFilelink /*+ ".zip"*/);
    	}
    	
    	if (new File(zipTargetFilelink).exists()) {
    		return true;//<-- TODO it's not granted the zip file is not empty.
    	}
    	return false;
    	
    }
    
    /**
     * This method zips the directory
     * @param dir
     * @param zipTargetFilelink
     */
    private void zipDirectory(File dir, String zipTargetFilelink) {
        try {
            populateFilesList(dir);
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipTargetFilelink);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
                System.out.println("Zipping "+filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    /**
     * This method populates all the files in a directory to a List
     * @param dir
     * @throws IOException
     */
    private void populateFilesList(File dir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(file);
        }
    }
 
    /**
     * This method compresses the single file to zip format
     * @param file
     * @param zipFileName
     */
    private static void zipSingleFile(File file, String zipFileName) {
        try {
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            //add a new Zip Entry to the ZipOutputStream
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            //read the file and write to ZipOutputStream
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
             
            //Close the zip entry to write to zip file
            zos.closeEntry();
            //Close resources
            zos.close();
            fis.close();
            fos.close();
            System.out.println(file.getCanonicalPath()+" is zipped to "+zipFileName);
             
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
 
}