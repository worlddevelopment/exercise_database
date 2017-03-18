package form;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

//@ManagedBean
public class TestUpload {

	public void handleFileUpload(FileUploadEvent event) {

		System.out.println("HALLOOOOOOO");

		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
}
