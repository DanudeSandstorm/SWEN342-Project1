package src;

import java.util.concurrent.*;

public class Manager extends Actor {


  private final CountDownLatch dailyMeeting;

  public Manager(String name, Clock clock, ConferenceRoom room) {
    super(name, clock, room);
    dailyMeeting = new CountDownLatch(4); // 3 Leads + Self
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
    //TODO
    //Work for random amount of time
    int duration = 0;
    doingWork(duration);
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
          outputAction(name + " is waiting for team leads to arrive.");
          //Wait for everyone to arrive
          CountDownLatch arrive = dailyStandup();
          try { arrive.await(); } 
          catch (InterruptedException e) {}

          outputAction(name + " has started the daily planning meeting.");
          inMeeting(15);
        }
      }
    );
  }


  public synchronized CountDownLatch dailyStandup() {
    dailyMeeting.countDown();
    return dailyMeeting;
  }


  public synchronized void askQuestion() {
    //TODO!
    //creates a task for a question
  }

}
