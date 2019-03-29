import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A Round-Robin scheduling object. Takes input paramaters of a quantum time and
 * list of processes, then will model the execution time of each one.
 */
public class Scheduler {
    /**
     * Total burst time for the Scheduler
     */
    private int quantum;
    /**
     * All the processes (with arrival and burst times) to run in this scheduler
     */
    private ArrayList<Process> inputProcesses;

    /**
     * Constructor method which assigns the quantum and process fields.
     * @param quantum - int value for burst time
     * @param processes - ArrayList of Process objects
     */
    public Scheduler(int quantum, ArrayList<Process> processes){
        this.quantum = quantum;
        this.inputProcesses = processes;
    }

    /**
     * Core method which performs the logic for scheduling.
     * Will also output a Gantt chart to show the process in real-time.
     */
    public void run() {
        // Active queue for what's running
        int startVal = this.quantum;
        ArrayList<Process> processes = new ArrayList<Process>(this.inputProcesses);
        Queue<Process> queue = new LinkedList<>();

        Process activeProcess = null;
        int tick = 0;
        int quantum = startVal;
        boolean active = false;

        // While there are still processes that haven't finished
        while (processes.size() != 0) {
            //check for new additions
            for (Process item : processes) {
                if (item.getArrivalTime() == tick) {
                    queue.add(item);
                }
            }
            //NOTHING IS CURRENTLY ACTIVE
            if (!active) {

                // THE QUEUE HAS SOMETHING FOR US
                if (!queue.isEmpty()) {
                    activeProcess = queue.remove();
                    System.out.print(activeProcess.getName());
                    activeProcess.decrement();
                    quantum = startVal - 1;
                    active = true;
                }
                // THERE IS NOTHING AVAILABLE YET. SKIP AHEAD.
                else {
                    System.out.print(" _ ");
                    quantum = startVal;
                }

            }
            // A PROCESS IS CURRENTLY ACTIVE
            else {
                // IS THE PROCESS EXPIRING?
                if (activeProcess.getTimeLeft() == 0) {
                    active = false;
                    activeProcess.decrement();
                    System.out.print("|");
                    activeProcess.setTurnaroundTime(tick);
                    processes.remove(activeProcess);
                    continue;
                }
                // THE PROCESS IS NOT EXPIRING
                else {
                    // TIME IS UP, GOTTA SWITCH
                    if (quantum == 0) {
                        queue.add(activeProcess);
                        // QUEUE IS CURRENTLY EMPTY, PLEASE HOLD FOR NEXT AVAILABLE PROCESS
                        if (((LinkedList<Process>) queue).getFirst() == null) {
                            quantum = startVal;
                        }
                        // LOAD THE CURRENT FOREMOST ITEM IN THE QUEUE
                        else {
                            activeProcess = queue.remove();
                            System.out.print("|" + activeProcess.getName() + " ");
                            quantum = startVal - 1;
                            activeProcess.decrement();
                        }
                    }
                    // YOU'VE STILL GOT TIME, DO YOUR THING
                    else {
                        activeProcess.decrement();
                        System.out.print(" _ ");
                        quantum--;
                    }
                }
            }
            tick++;
            for(Process item:queue){
                item.incrementWait();
            }
        }
        printData(tick);
    }
    /**
     * Helper method which prints out the process name, arrival time, and wait time
     * for every process after scheduling is complete.
     * @param tick - total number of ticks from the scheduler.
     */
    private void printData(int tick){
        System.out.println();
        System.out.println();
        System.out.println("Total ticks taken: " + tick);
        System.out.println();
        System.out.println("Process\t\tWait Time\tTurnaround Time");
        System.out.println("===================================================");
        for(Process item:this.inputProcesses){
            System.out.print(item.getName()+"\t\t"+item.getWaitTime()+"\t\t"+item.getTurnaroundTime());
            System.out.println();
        }
    }
}
