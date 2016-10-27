package src;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

public class TeamLead extends Actor{
    /* Inherited from Actor
     * protected Clock clock;
     * protected ConferenceRoom conferenceRoom;
     */
    
    private Manager manager;
    private long arrivalTime = 0;
    private boolean hasHadLunch;
    private int lunchLength;
    
    //statistics keeping
    private int working;
    private int lunch;
    private int meetings;
    
    
    public TeamLead(String name, Clock clock, ConferenceRoom room, 
            Manager manager) {
        super(name, clock, room);
        this.manager = manager;
        hasHadLunch = false;
        lunchLength = 30;
        
        
        
        working = 0;
        lunch = 0;
        meetings = 0;
    }
    
    public void run() {
        clock.awaitStart();
        
        //Wait Random time to simulate when they come in.
        busy((int)(Math.random() * clock.convertMinutes(30)));
        arrivalTime = clock.getTimePassedMillis();
        printWithTime(getName() + " arrives at work.");
        
        //Find out how long lunch can be based on arrival time
        lunchLength = 30 + (int)(Math.random() * (30 - 
                clock.convertSimulated((int)arrivalTime)));
        
        //Go to manager Morning Meeting
        printWithTime(getName() + " goes to morning meeting.");
        manager.awaitMorningMeeting();
        printWithTime(getName() + " finishes morning meeting.");
        meetings += 15;
        
        //Go to daily Stand Up check every five minutes if the room is 
        //available No questions during this time.
        printWithTime(getName() + " tries to start standUp.");
        conferenceRoom.reserveRoom(this, 4);
        printWithTime(getName() + " finishes standUp.");
        meetings += 15;
        
        //Idle until the meeting at 4PM
        idleUntil(clock.AbsoluteMinutes(16 * 60));
        
        //Go to end of day meeting
        printWithTime(getName() + " goes to summary meeting.");
        conferenceRoom.arriveFinal();
        printWithTime(getName() + " finishes summary meeting.");
        meetings += 15;
        
        //Idle until it's time to go home
        idleUntil(clock.convertSimulated((int)arrivalTime) + (8 * 60) + 
                lunchLength);
        printWithTime(getName() + " goes home.");
        
    }
    
    protected void idleUntil(int relativeMinutes) {
        takingQuestions = true;
        while(clock.convertSimulated((int)clock.getTimePassedMillis()) < 
                relativeMinutes) {
            //Check if should go to lunch.
            if(!hasHadLunch) {
                //Currently Just checks if there's enough time to have lunch.
                if((relativeMinutes - clock.convertSimulated( 
                        (int)clock.getTimePassedMillis())) > lunchLength) {
                    //Go on lunch break
                    busy(lunchLength);
                    lunch += lunchLength;
                }
            } else {
                //Handle Questions
                if(!questionBuffer.isEmpty()) {
                    //50% chance of automatically answering
                    questionBuffer.poll();
                    if(Math.random() * 2 < 1) {
                        printWithTime(getName() + " answers a question" +
                                " immediately.");
                        answerBuffer.offer(0);
                    } else {
                        printWithTime(getName() + " asks a manager a" +
                                " question.");
                        answerBuffer.offer(manager.askQuestion());
                    }
                } else {
                    busy(1);
                    working += 1;
                }
                
            }
        }
        
        //Developers need to be woken up since the Team Lead isn't taking
        //questions
        synchronized(questionLock) {
            takingQuestions = false;
            while(!questionBuffer.isEmpty()) {
                questionBuffer.poll();
                answerBuffer.add(-1);
            }
        }
        
        
    }
    
    /* Question Handling Code------------------------------------------------*/
    private ConcurrentLinkedQueue<Developer> questionBuffer = 
            new ConcurrentLinkedQueue<Developer>();
    //Guarantees fairness.
    private SynchronousQueue<Integer> answerBuffer = 
            new SynchronousQueue<Integer>(true);
    //Used to make sure that no Developers ask questions after the tean lead
    //stops taking them
    private Object questionLock = new Object();
    private boolean takingQuestions = false;
    
    //If It's time for a meeting, make sure developers are woken up.
    private boolean meeting = false;
    
    public int askQuestion(Developer developer) {
        synchronized(questionLock) {
            if(!takingQuestions) {
                return -1;
            }
            questionBuffer.add(developer);
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
