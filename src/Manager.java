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
		if (finalMeeting.getCount() == 1) {
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
		//TODO!
	}

	protected void scheduleLeave(long start) {
		//TODO!
	}

}
