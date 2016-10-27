package src;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * FileWriter allows the system to log output from multiple threads similarly to
 * writing standard out. It is a static class that keeps track of only one log
 * file to which everything is written. This class is thread safe so multiple
 * threads can write to the log without losing order or waiting too long. Note
 * that the FileWriter must be notified at the end of the program to clean up so
 * the thread will be terminated.
 */
public class FileWriter {

  private static final String DEFAULT_FILENAME = "out.txt"; //Subject to change


  private static ConcurrentLinkedQueue<String> buffer = new
    ConcurrentLinkedQueue<String>();
  private static volatile boolean finished = false;
  private static Thread writeThread = new Thread(new Runnable() {
    public void run() {
      continuouslyWrite();
    }
  });

  private static BufferedWriter writer;
  static {
    try {
      writer = new BufferedWriter(new java.io.FileWriter(DEFAULT_FILENAME));
    } catch(IOException e) {e.printStackTrace();}
    writeThread.start();
  }


  private static void continuouslyWrite() {
    String line;
    //Nand check to make sure that the queue is empty before terminatint the
    //thread.
    while(!(buffer.isEmpty() && finished)) {
      line = buffer.poll();
      if(line == null) {
        //Yield if there's nothing to write, would use blocking queue but then
        //there's the problem of terminating the thread at the end of the
        //program.
    Thread.yield(); 
      } else {
        try {
          writer.write(line);
          writer.newLine();
        } catch(IOException e) {
      e.printStackTrace();
        }
      }
    }

    try {
      writer.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  

  public static void writeLine(String line) {
    buffer.add(line);
  }

  public static void terminateWriter() {
    finished = true;
  }


  //Only used for testing the FileWriter Class
  public static void main(String[] args) {
    writeLine("Testing Testing");
    writeLine("1 2 3 4");
    terminateWriter();
  }

}
