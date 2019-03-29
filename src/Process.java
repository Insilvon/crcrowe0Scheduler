public class Process {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int timeLeft;

    public Process(String name, int at, int bt){
        this.name = name;
        this.arrivalTime = at;
        this.burstTime = bt;
        this.timeLeft = bt;
    }

    public String getName(){ return this.name;}
    public int getArrivalTime(){return this.arrivalTime;}
    public int getBurstTime(){return this.getBurstTime();}
    public int getTimeLeft(){
        return this.timeLeft;
    }
    public void decrement(){
        this.timeLeft--;
    }
}
