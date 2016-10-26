package src;

import java.util.concurrent.*;

public class Manager extends Actor {

  private final int MAXIMUM_WORK_TIME = 10;

  private final CountDownLatch dailyMeeting;

  public Manager(String name, Clock clock, ConferenceRoom room) {
    super(name, clock, room);
    dailyMeeting = new CountDownLatch(3); // 3 Leads
  }

  /**
  * Begin the day.
  * Wait for the clock to notify that the day has begun then schedule tasks that
  * need to be done and prepare to do work.
  **/
  @Override
  protected void startDay() {
    startTime = clock.startDay();
    //Daily Standup
    scheduleDailyStandup();
    //Last meeting
    finalMeeting();
    //Morning meeting at 10:00am
    scheduleMeeting(600, 60);
    //Afternoon meeting at 2:00 pm
    scheduleMeeting(840, 60);
    //Lunch at 12:00 pm
    scheduleLunch(720, 60);
    //End of day at 5:00pm
    scheduleLeave(1020);
  }


  /**
  * Randomly assigns a duration at which to perform work
  **/
  @Override
  protected void doingWork() {
    doingWork(MAXIMUM_WORK_TIME);
  }


  /**
  * Prints stats
  **/
  @Override
  protected void printStats() {
    printStats(working, lunch, meetings);
  }


  protected void scheduleDailyStandup() {
    //Meeting at 8:00 am
    addTask(new Task("Meeting", clock.convertTimeOfDay(480), clock.convertMinutes(15)) {
        @Override
        public void performTask() {
          awaitStandup();
        }
      }
    );
  }


  /**
  * The Manager waits for all the team leads to arrive for the standup.
  */
  private void awaitStandup() {
    outputAction(name + " is waiting for team leads to arrive.");
    //Wait for everyone to arrive
    try { dailyMeeting.await(); } 
    catch (InterruptedException e) {}

    synchronized(this) {
      outputAction(name + " has started the daily planning meeting.");
      inMeeting(15);
      this.notifyAll();
    }
  }

  /**
  * The team leads arrive at the standup and wait for the meeting to end.
  */
  public synchronized void dailyStandup() {
    dailyMeeting.countDown();
    try {
      this.wait();
    } catch(InterruptedException e) {}
    
  }


  public synchronized void askQuestion() {
    //TODO!
    //creates a task for a question
  }


}
