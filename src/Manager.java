package src;

import java.util.concurrent.*;

public class Manager extends Actor {


  private final CountDownLatch dailyMeeting;

  public Manager(String name, Clock clock, ConferenceRoom room) {
    super(name, clock, room);
    dailyMeeting = new CountDownLatch(3);
  }

  public void run() {
    startDay();

    //TODO
    //Main execution loop
    while (!leave) {

    }
  }

  /**
  * Begin the day!
  * Gets start time and sets up shedual
  **/
  protected void startDay() {
    startTime = clock.startDay();
    //TODO schedual daily standup meeting

    //Last meeting
    finalMeeting();
    //Morning meeting
    scheduleMeeting(600, 60);
    //Afternoon meeting
    scheduleMeeting(840, 60);
    //Lunch
    scheduleLunch(720, 60);
    //End of day
    scheduleLeave(1020);
  }

  public synchronized CountDownLatch dailyPlanningMeeting() {
    dailyMeeting.countDown();
    return dailyMeeting;
  }

  public synchronized void askQuestion() {
    //TODO!
  }

}
