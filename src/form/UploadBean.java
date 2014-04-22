package form;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
@ManagedBean
public class UploadBean {
	private static final int BUFFER_SIZE = 6124;
	/** Creates a new instance of UploadBean */
	public UploadBean() {
	}
	public void handleFileUpload(FileUploadEvent event) {
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
