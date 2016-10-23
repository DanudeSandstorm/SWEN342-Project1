package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Actor extends Thread {

  private final List<Task> todo = new ArrayList<Task>();
  private Clock clock;

  private final Comparator<Task> taskCompare = new Comparator<Task>() {
    public int compare(Task a, Task b) {
      return a.getStart() - b.getStart();
    }
  };


  public Actor(Clock clock) {
    this.clock = clock;

    addTask();
    //conference room arriveFinal()
    //returns gate
    //gate.await();
  }
  
  /**
  * Adds a Task to the to-do list and keeps the list sorted.
  */
  protected boolean addTask(Task task) {
    if (checkConflict(task)) {
      return false;
    }

    todo.add(task);
    todo.sort(taskCompare);
    return true;
  }


  /**
  * 
  * Checks whether the time is right to perform the next task and if it is
  * poppes it off of the task list to perform.
  *
  * @return Whether a task was completed
  */
  protected boolean nextTask() {
    if(todo.isEmpty()) {
      return false;
    }

    Task t = todo.get(0);
    if(t.getStart() < clock.getTimePassedMillis()) {
      t.performTask();
      todo.remove(t);
      return true;
    } else {
      return false;
    }
  }


  /**
  * Checks if the task will result in a conflict with the current schedule.
  *
  * @param task The task that may cause a conflict.
  *
  * @return true if there is a conflict, false otherwise.
  */
  protected boolean checkConflict(Task task) {
    for(Task t : todo) {
      if(t.conflicts(task)) {
	return true;
      }
    }

    return false;
  }


  protected void printWithTime(String text) {
    System.out.println(clock.getPrintableTime() + " " + text);
  }
}
