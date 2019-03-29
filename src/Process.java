public class Process {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int timeLeft;
    private int waitTime=0;
    private int turnaroundTime;

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
    public int getWaitTime(){
        return this.waitTime;
    }
    public int getTurnaroundTime(){
        return this.turnaroundTime;
    }
    public void decrement(){
        this.timeLeft--;
    }
    public void setTurnaroundTime(int tick){
        this.turnaroundTime=tick-this.arrivalTime;
    }
    public void incrementWait(){
        this.waitTime++;
    }
}
