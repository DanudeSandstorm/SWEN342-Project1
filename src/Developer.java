package src;

public class Developer extends Actor {
	
	public Developer(Clock clock, ConferenceRoom room) {
    	super(clock, room);
	}

	public void run() {
		startDay();
		//additional schedule
		//TODO
	}

	private void question() {
		//TODO!
	}

	protected void scheduleMeetings() {
		//TODO!
	}

	protected void scheduleLunch() {
		//TODO!
	}

	protected void scheduleLeave(long start) {
		//TODO!
	}

}
