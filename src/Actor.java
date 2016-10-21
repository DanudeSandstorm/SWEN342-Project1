

public abstract class Actor extends Thread {

  

  /**
  * Adds a Task to the to-do list and keeps the list sorted.
  */
  protected void addTask(Task t) {
    
  }


  /**
  * Pops the next task that needs to be done off of the to-do list.
  */
  protected Task nextTask() {
    
  }


  /**
  * Checks if the task will result in a conflict with the current schedule.
  */
  protected boolean checkConflict(Task t) {
    return false;
  }
}
