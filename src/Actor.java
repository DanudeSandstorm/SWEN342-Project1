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
  protected int working, lunch, meetings; //stats


  public Actor(String name, Clock clock, ConferenceRoom room) {
    this.name = name;
    this.clock = clock;
    this.room = room;
    leave = false;
    working = 0;
    lunch = 0;
    meetings = 0;

    //Instantiate to-do list
    todoList = new ArrayList<Task>();
  }

  /**********************/
  /** Abstract Methods **/
  /**********************/

  protected abstract void startDay();

  protected abstract void doingWork();

  //this method is overloaded for different amounts of stats
  //classes which inherit implement this and
  //decide which method to invoke
  protected abstract void printStats();


  /**
  * Actor starts the day
  * Works or does tasks until its time to leave
  **/
  public void run() {
   startDay();

   while (!leave) {
     //Checks to see if has a task
     //If doesn't, works for a bit
     if (!nextTask()) {
       doingWork();
     }
   }

   printStats();
  }


  /**
  * @return the name of the actor
  **/
  public String getName() {
    return name;
  }

  /********************/
  /***    Sleep     ***/
  /********************/

  /**
  * Thread sleeps for a specified amount of time
  * @param minutes - amount of minutes to sleep
  **/
  protected void busy(int duration) {
    try { 
      Thread.sleep(clock.convertMinutes(duration)); 
    } 
    catch(InterruptedException e) {}
  }


  /**
  * Overloaded method that increases time spend working
  * and sets the actor to busy
  **/
  protected void doingWork(int duration) {
    working += duration;
    busy(duration);
  }


  protected void eatingLunch(int duration) {
    lunch = duration;
    busy(duration);
  }


  protected void inMeeting(int duration) {
    meetings += duration;
    busy(duration);
  }

  /********************/
  /***  Scheduling  ***/
  /********************/

  /**
  *
  **/
  protected void scheduleMeeting(int time, int duration) {
    addTask(new Task("Meeting", clock.convertTimeOfDay(time), clock.convertMinutes(duration)) {
        @Override
        public void performTask() {
          outputAction(name + " went to a meeting.");
          inMeeting(duration);
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
          outputAction(name + " went to lunch.");
          eatingLunch(duration);
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
          outputAction(name + " arrived to the end of day meeting.");
          //Wait for everyone to arrive
          CountDownLatch arrive = room.arriveFinal();
          try { arrive.await(); } 
          catch (InterruptedException e) {}

          inMeeting(15);
        }
      }
    );
  }


  /**
  * The actor leaves the office
  **/
  protected void scheduleLeave(int time) {
    addTask(new Task("Leave", clock.convertTimeOfDay(time)) {
        @Override
        public void performTask() {
          outputAction(name + " left the office.");
          leave = true;
        }
      }
    );
  }


  /********************/
  /***   Tasking    ***/
  /********************/

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
    if (todoList.isEmpty()) {
      return false;
    }

    Task t = todoList.get(0);
    if (t.getStart() < clock.getTimePassedMillis()) {
      t.performTask();
      todoList.remove(t);
      return true;
    } 
    else {
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


  /********************/
  /***    Output    ***/
  /********************/

  /**
  * Saves an action and the time it occured to a file
  **/
  protected void outputAction(String action) {
    String text = "";
    clock.getPrintableTime();
    //TODO!
    //save timestap: action
    writeToFile(text);
  }


  /**
  * Prints the stats of an actor
  * (a) working, (b) at lunch, (c) in meetings
  **/
  protected void printStats(int a, int b, int c) {
    String text = "";
    //TODO
    //Name:
    //for each action
      //string = "Action - Amount"
    writeToFile(text);
  }


  /**
  * Prints the stats of an actor
  * (a) working, (b) at lunch, (c) in meetings, and
  * (d) waiting for the manager to be free to answer a question.
  * Overloaded method for actors with the 4th stat
  **/
  protected void printStats(int a, int b, int c, int d) {
    printStats(a, b, c);

    String text = "";
    //TODO
    //string = "Action - Amount"
    writeToFile(text);
  }



  /**
  * Synchronized method that writes a string to the file buffer
  **/
  private synchronized void writeToFile(String text) {
    //TODO
  }
}
