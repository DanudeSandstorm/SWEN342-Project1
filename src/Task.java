package src;

import java.util.Comparator;

public abstract class Task {

  private final int start;
  private final int duration;
  private final String type;

  public Task(String type, int startTime, int duration) {
    this.type = type;
    start = startTime;
    this.duration = duration;
  }

  public String getType() {
    return type;
  }

  public int getStart() {
    return start;
  }

  public boolean conflicts(Task t) {
    return ((t.start >= start && t.start < start + duration) 
            || (t.start + t.duration > t.start 
                && t.start + t.duration <= start + duration) 
            || (start >= t.start && start + duration <= t.start + t.duration)
            );
  }

  public abstract void performTask();

  public static Comparator<Task> compare() {
    return new Comparator<Task>() {
      public int compare(Task a, Task b) {
        return a.getStart() - b.getStart();
      }
    };
  }
}
