package src;

import java.util.Comparator;

public abstract class Task {

  private final String type;
  private long start;
  private long duration;

  //Task that has no length
  public Task(String type, long startTime) {
    this.type = type;
    start = startTime;
    duration = 0;
  }

  public Task(String type, long startTime, long duration) {
    this.type = type;
    start = startTime;
    this.duration = duration;
  }

  public String getType() {
    return type;
  }

  public long getDuration() {
    return duration;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public long getStart() {
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
        return Long.compare(a.getStart(), b.getStart());
      }
    };
  }
}
