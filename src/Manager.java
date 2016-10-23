package src;

import java.util.concurrent.*;

public class Manager extends Actor {


  private final CountDownLatch dailyMeeting;

  public Manager(Clock clock) {
    super(clock);
    dailyMeeting = new CountDownLatch(3);
  }

  public void run() {
    startDay();
    //additional schedule
    //TODO
  }

  public synchronized CountDownLatch dailyPlanningMeeting() {
    //Checks if all members are here,
    //waits 15 minutes and then final countdown
    if (dailyMeeting.getCount() == 1) {
      //TODO
    }
    dailyMeeting.countDown();
    return dailyMeeting;
  }

  public synchronized void askQuestion() {
    //TODO!
  }

  protected void scheduleMeetings() {
    //TODO!
  }

  protected void scheduleLunch() {
    //Lunch at 12:00 for 1 hour
    addTask(new Task("Lunch", clock.convertTimeOfDay(720), clock.convertMinutes(60)) {
        @Override
        public void performTask() {
          outputAction("Manager went to lunch");
          //TODO!
        }
      }
    );
  }

  protected void scheduleLeave(long start) {
    addTask(new Task("Leave", clock.convertTimeOfDay(1020), clock.convertMinutes(60)) {
        @Override
        public void performTask() {
          outputAction("Manager went home");
          //TODO!
        }
      }
    );
  }

}
