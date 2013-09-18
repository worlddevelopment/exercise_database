package swp.pfade;

public class Paths {
	
	/**OS dependend fileseparator */
	private static String fileSeparator ;
	
	/**rootverzeichnis */
	private String root;
	
	
	private String upload_path;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getupload_path() {
		return upload_path;
	}

	public void setUpload_path(String upload_path) {
		this.upload_path = upload_path;
	}
	
	/**
	 * Setzt fileseparator abhaengig von OS
	 */
	public static void setFileSeparator(String fileSeparator) {
		Paths.fileSeparator = fileSeparator;
	}
	
	public static String getFileSeparator() {
		return fileSeparator;
	}

}
