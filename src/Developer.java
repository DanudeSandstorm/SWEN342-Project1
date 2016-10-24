package src;

public class Developer extends Actor {

  protected int waiting; 
  
  public Developer(String name, Clock clock, ConferenceRoom room) {
      super(name, clock, room);
      waiting = 0;
  }

  @Override
  protected void startDay() {
    //TODO!
  }

  /**
  * Randomly assigns a duration at which to perform work
  **/
  @Override
  protected void work() {
    //TODO
    //Chance to have a question
    //Or
    //Work for random amount of time
    int duration = 0;
    busy(duration);
  }

  /**
  * Prints stats
  **/
  @Override
  protected void printStats() {
    printStats(working, lunch, meetings, waiting);
  }

  private void scheduleDailyStandup() {
    //TODO
  }

  private void question() {
    //TODO!
  }

}
