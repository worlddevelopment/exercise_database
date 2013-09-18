package form;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
public class FileUploadController {
	private static final int BUFFER_SIZE = 6124;
//	public FileUploadController(){
//		
//	}

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload(FileUploadEvent event) throws IOException {
//        FacesMessage msg = new FacesMessage("Succesful",  event.getFile().getFileName() + " is uploaded.");
//		FacesContext.getCurrentInstance().addMessage(null, msg);
    	File f = new File("new_fucking_file.txt");
    	f.createNewFile();
    	 System.out.println("FileUpload finished");
    	    file = event.getFile();
    	    //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    	   
    	    String newFileName = "D:/uploaded"+ "1.jpg";
    	    FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
    	    FacesContext.getCurrentInstance().addMessage(null, msg);
    	    try {
    	        FileOutputStream fos = new FileOutputStream(new File(newFileName));

    	        InputStream is = file.getInputstream();
    	        int BUFFER_SIZE = 8192;
    	        byte[] buffer = new byte[BUFFER_SIZE];
    	        int a;
    	        while(true) {
    	            a = is.read(buffer);
    	            if(a < 0) break;
    	            fos.write(buffer, 0, a);
    	            fos.flush();
    	        }
    	        fos.close();
    	        is.close();
    	    } catch(IOException e) { }
    	}
    public void handleFileUpload(FileUploadEvent event) throws IOException {
//		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//		FacesContext.getCurrentInstance().addMessage(null, msg);
    	File f = new File("new_fucking_file.txt");
    	f.createNewFile();
    	
    	ExternalContext extContext = FacesContext.getCurrentInstance().
    			getExternalContext();
    			File result = new File(extContext.getRealPath
    			("//WEB-INF//upload") + "//" + event.getFile().getFileName());
    			try {
    				FileOutputStream fileOutputStream = new
    				FileOutputStream(result);
    				byte[] buffer = new byte[BUFFER_SIZE];
    				int bulk;
    				InputStream inputStream = event.getFile().getInputstream();
    				while (true) {
    				bulk = inputStream.read(buffer);
    				if (bulk < 0) {
    				break;
    				}
    				fileOutputStream.write(buffer, 0, bulk);
    				fileOutputStream.flush();
    				}
    				fileOutputStream.close();
    				inputStream.close();
    				FacesMessage msg = new FacesMessage("Succesful",
    				event.getFile().getFileName() + " is uploaded.");
    				FacesContext.getCurrentInstance().addMessage(null, msg);
    			} catch (IOException e) {
    				e.printStackTrace();
    				FacesMessage error = new FacesMessage("The files were not uploaded!");
    				FacesContext.getCurrentInstance().addMessage(null, error);
    				}
    	
	}
}
                    