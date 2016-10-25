package src;

import java.util.concurrent.BlockingQueue;
import java.io.BufferedWriter;

public class FileWriter {

  private static FileWriter instance = new FileWriter();
  private static final String DEFAULT_FILENAME = "out.txt"; //Subject to change

/*
  private final BlockingQueue<String> toWrite;
  private final BufferedWriter writer;
  private volatile boolean finished;
  private final Thread writeThread;
*/

  private FileWriter() {
//    toWrite = new BlockingQueue();
//    writer = new BufferedWriter(new java.io.FileWriter(DEFAULT_FILENAME));
//    finished = false;
  }

  //Dummy for compilations reasons, will be changed later.
  public static FileWriter getInstance() {return null;}

  /**
   * Synchronized method that writes a string to the file buffer
   * Adds request to queue
   **/
  public static void write(String text) {
//    toWrite.add(text); //Safety handled by BlockingQueue
  }

  
  

}
