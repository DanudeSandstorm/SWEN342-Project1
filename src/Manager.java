package src;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.SynchronousQueue;

public class Manager extends Actor {
    /* Inherited from Actor
     * protected Clock clock;
     * protected ConferenceRoom conferenceRoom;
     */
    
    private long arrivalTime = 0;
    
    //statistics keeping
    private int working;
    private int lunch;
    private int meetings;
    
    private CyclicBarrier morningMeeting;
    
    
    public Manager(String name, Clock clock, ConferenceRoom room) {
        super(name, clock, room);
        morningMeeting = new CyclicBarrier(4,  new Runnable() {
            public void run() {
                try {
                    //Sleep for the duration of the meeting
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        });
        
        working = 0;
        lunch = 0;
        meetings = 0;
    }
    
    public void run() {
        clock.awaitStart();
        arrivalTime = clock.getTimePassedMillis();
        
        //Go to daily Stand Up check every five minutes if the room is 
        //available No questions during this time.
        printWithTime(getName() + " goes to morning meeting.");
        awaitMorningMeeting();
        meetings += 15;
        printWithTime(getName() + " finishes morning meeting.");
        
        //Notify that you're taking questions.
        takingQuestions = true;
        
        //Idle until 10Am meeting
        idleUntil(clock.AbsoluteMinutes(10 * 60));
        printWithTime(getName() + " goes to 10AM meeting.");
        busy(clock.convertMinutes(60));
        printWithTime(getName() + " finishes 10AM meeting.");
        meetings += 60;
        
        //Idle until 12PM Lunch
        idleUntil(clock.AbsoluteMinutes(12 * 60));
        busy(clock.convertMinutes(60));
        lunch += 60;
        
        //Idle until 2PM meeting
        idleUntil(clock.AbsoluteMinutes(14 * 60));
        printWithTime(getName() + " goes to 2PM meeting.");
        busy(clock.convertMinutes(60));
        printWithTime(getName() + " goes to 2PM meeting.");
        meetings += 60;
        
        //Idle until the meeting at 4PM
        
        idleUntil(clock.AbsoluteMinutes(16 * 60));
        
        
        //Go to end of day meeting Notify that you're not taking questions
        synchronized(questionLock) {
            takingQuestions = false;
            while(!questionBuffer.isEmpty()) {
                questionBuffer.poll();
                answerBuffer.add(-1);
            }
        }
        
        printWithTime(getName() + " goes to summary meeting.");
        conferenceRoom.arriveFinal();
        printWithTime(getName() + " finishes summary meeting.");
        meetings += 15;
        
        //Idle until it's time to go home
        idleUntil(clock.AbsoluteMinutes(17 * 60));
        printWithTime(getName() + " goes home.");
        
    }
    
    public void awaitMorningMeeting() {
        try {
            morningMeeting.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
    
    protected void idleUntil(int relativeMinutes) {
        takingQuestions = true;
        while(clock.convertSimulated((int)clock.getTimePassedMillis()) < 
                relativeMinutes) {
            //Check if should go to lunch.
                //Handle Questions
                if(!questionBuffer.isEmpty()) {
                    
                    //Track time spent waiting on the manager.
                    int time = clock.convertSimulated(
                            (int)clock.getTimePassedMillis()) - 
                            questionBuffer.poll();
                    busy(clock.convertMinutes(10));
                    answerBuffer.add(time);
                } else {
                    busy(1);
                    working += 1;
                }
        }
        
        //Developers need to be woken up since the Team Lead isn't taking
        //questions
        
        
        
    }
    
    /* Question Handling Code------------------------------------------------*/
    private ConcurrentLinkedQueue<Integer> questionBuffer = 
            new ConcurrentLinkedQueue<Integer>();
    //Guarantees fairness.
    private SynchronousQueue<Integer> answerBuffer = 
            new SynchronousQueue<Integer>(true);
    //Used to make sure that no Developers ask questions after the manager
    //stops taking them
    private Object questionLock = new Object();
    private boolean takingQuestions = false;
    
    public int askQuestion() {
        synchronized(questionLock) {
            if(!takingQuestions) {
                return -1;
            }
            questionBuffer.add(clock.convertSimulated(
                    (int)clock.getTimePassedMillis()));
        }
        int time = 0;
        try {
            time = answerBuffer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return time;
    }
}
