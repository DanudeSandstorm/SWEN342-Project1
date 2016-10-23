package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Actor implements Runnable {

  private final Comparator<Task> taskCompare;
  private final List<Task> todoList;
  private Clock clock;
  private long startTime;


  public Actor(Clock clock) {
    this.clock = clock;

    todoList = new ArrayList<Task>();

    taskCompare = new Comparator<Task>() {
      public int compare(Task a, Task b) {
        return a.getStart() - b.getStart();
      }
    };

    //Begins the day
    startTime = clock.startDay();

    /** Shedules the day **/
    scheduleMeetings();

    scheduleLunch();

    finalMeeting();

    scheduleLeave(startTime);

  }

  public abstract void run();

  protected abstract void scheduleMeetings();

  protected abstract void scheduleLunch();

  protected abstract void scheduleLeave(long start);

  protected void finalMeeting() {
    //addTask();
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

    todoList.add(task);
    todoList.sort(taskCompare);
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
    if(todoList.isEmpty()) {
      return false;
    }

    Task t = todoList.get(0);
    if(t.getStart() < clock.getTimePassedMillis()) {
      t.performTask();
      todoList.remove(t);
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
    for(Task t : todoList) {
      if(t.conflicts(task)) {
	     return true;
      }
    }

    return false;
  }


  //TODO replace printing with outputing to a file
  //Needs to be a synchronos method for output
  protected void printWithTime(String text) {
    System.out.println(clock.getPrintableTime() + " " + text);
  }
}
