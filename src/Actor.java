package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.*;

public abstract class Actor implements Runnable {

  private final List<Task> todoList;
  protected Clock clock;
  protected ConferenceRoom room;
  protected String name;
  protected long startTime;
  protected boolean leave; //should the person leave work?


  public Actor(String name, Clock clock, ConferenceRoom room) {
    this.name = name;
    this.clock = clock;
    this.room = room;
    leave = false;

    //Instantiate to-do list
    todoList = new ArrayList<Task>();
  }


  /** Abstract Methods **/
  public abstract void run();

  protected abstract void startDay();


  /**
  * Thread sleeps for a specified amount of time
  * @param minutes - amount of minutes to sleep
  **/
  protected void busy(int minutes) {
    try { 
      Thread.sleep(clock.convertMinutes(minutes)); 
    } 
    catch(InterruptedException e) {}
  }


  /**
  *
  **/
  protected void scheduleMeeting(int time, int duration) {
    addTask(new Task("Meeting", clock.convertTimeOfDay(time), clock.convertMinutes(duration)) {
        @Override
        public void performTask() {
          outputAction(name + " went to meeting");
          busy(duration);
        }
      }
    );
  }

  /**
  *
  **/
  protected void scheduleLunch(int time, int duration) {
    //Lunch at 12:00 for 1 hour
    addTask(new Task("Lunch", clock.convertTimeOfDay(time), clock.convertMinutes(duration)) {
        @Override
        public void performTask() {
          outputAction(name + " went to lunch");
          busy(duration);
        }
      }
    );
  }


  /**
  * The final meeting of the day
  **/
  protected void finalMeeting() {
    //Meeting at 4:00pm
    addTask(new Task("Meeting", clock.convertTimeOfDay(600), clock.convertMinutes(15)) {
        @Override
        public void performTask() {
          outputAction(name + " arrived to the end of day meeting");
          //Wait for everyone to arrive
          CountDownLatch arrive = room.arriveFinal();
          try { arrive.await(); } 
          catch (InterruptedException e) {}

          busy(15);
        }
      }
    );
  }

  /**
  *
  **/
  protected void scheduleLeave(int time) {
    addTask(new Task("Leave", clock.convertTimeOfDay(time)) {
        @Override
        public void performTask() {
          outputAction(name + " went home");
          leave = true;
        }
      }
    );
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
