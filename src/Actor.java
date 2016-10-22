package src;

import java.util.List;
import java.util.ArrayList;

public abstract class Actor extends Thread {

  private List<Task> todo = new ArrayList<Task>();
  
  /**
  * Adds a Task to the to-do list and keeps the list sorted.
  */
  protected void addTask(Task t) {
    
  }


  /**
  * 
  * The popped task must be stored locally until completed.
  *
  * @return The next task to perform.
  */
  protected boolean nextTask() {
    return new null;  
  }


  /**
  * Checks if the task will result in a conflict with the current schedule.
  */
  protected boolean checkConflict(Task t) {
    return false;
  }


  protected void printWithTime(String text) {
    
  }
}
