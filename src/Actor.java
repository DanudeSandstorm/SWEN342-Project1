package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.*;

public abstract class Actor implements Runnable {

  private final List<Task> todoList;
  protected Clock clock;
  protected ConferenceRoom room;
  protected long startTime;


  public Actor(Clock clock, ConferenceRoom room) {
    this.clock = clock;
    this.room = room;

    //Instantiate to-do list
    todoList = new ArrayList<Task>();
  }

  public abstract void run();

  protected abstract void scheduleMeetings();

  protected abstract void scheduleLunch();

  protected abstract void scheduleLeave(long start);

  protected void finalMeeting() {
    addTask(new Task("Meeting", clock.convertTimeOfDay(600), clock.convertMinutes(60)) {
        @Override
        public void performTask() {
          outputAction("Arrived to end of day meeting");
          CountDownLatch arriveFinal = room.arriveFinal();
          try {
            arriveFinal.await();
          }
          catch (InterruptedException e) {}
        }
      }
    );
  }

  //Begins the day
  protected void startDay() {
    startTime = clock.startDay();

    /** Shedules the day **/
    scheduleMeetings();

    scheduleLunch();

    finalMeeting();

    scheduleLeave(startTime);
  }

  /**
  * Adds a Task to the to-do list and keeps the list sorted.
  */
  protected boolean addTask(Task task) {
    if (checkConflict(task)) {
      return false;
    }

    todoList.add(task);
    todoList.sort(Task.compare());
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


  /**
  * Saves an action and the time it occured to a file
  **/
  protected synchronized void outputAction(String action) {
    clock.getPrintableTime();
    //TODO!
    //save timestap: action
  }
}
