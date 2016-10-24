package src;

public class FileWriter {

	private static final FileWriter instance = new FileWriter();

	protected FileWriter() {
	   // Exists only to defeat instantiation.
	}

	public static FileWriter getInstance() {
	   return instance;
	}

	/**
	* Synchronized method that writes a string to the file buffer
	* Adds request to queue
	**/
	public synchronized void write(String text) {
	  //TODO
	  //Create lock
	  //Add request to queue
	}

}
