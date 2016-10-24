package src;

public class Developer extends Actor {
  
  public Developer(String name, Clock clock, ConferenceRoom room) {
      super(name, clock, room);
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

  private void question() {
    //TODO!
  }

}
