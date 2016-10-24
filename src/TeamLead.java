package src;

import java.util.concurrent.*;

public class TeamLead extends Actor {

  private Manager manager;

  public TeamLead(String name, Clock clock, ConferenceRoom room, Manager manager) {
      super(name, clock, room);
      this.manager = manager;
  }


  /**
  * Begin the day!
  * Gets start time and sets up shedual
  **/
  protected void startDay() {
    //TODO!
  }

  /**
  * Randomly assigns a duration at which to perform work
  **/
  @Override
  protected void work() {
    //TODO
    //Work for random amount of time
    int duration = 0;
    busy(duration);
  }


  protected void scheduleDailyStandupWithManager() {
    //Meeting at 8:00 am
    addTask(new Task("Meeting", clock.convertTimeOfDay(480), clock.convertMinutes(15)) {
        @Override
        public void performTask() {
          outputAction(name + " arrived to the daily planning meeting.");
          //Wait for everyone to arrive
          CountDownLatch arrive = manager.dailyStandup();
          try { arrive.await(); } 
          catch (InterruptedException e) {}

          busy(15);
        }
      }
    );
  }


  protected void scheduleDailyStandupWithTeam() {
    //TODO
  }


  /**
  * Ask the team leader a question 
  * @Return the emount of time spent waiting on the manager
  * or 0 if the lead was able to answer
  */
  public synchronized long askQuestion() {
    //TODO!
    return 0;
  }
  
}
