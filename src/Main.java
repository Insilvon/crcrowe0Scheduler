import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int quantum = 3;
        if (args.length != 0) quantum = Integer.parseInt(args[0]);
        ArrayList<Process> temp = setup();
        run2(quantum, temp);
    }

    private static ArrayList<Process> setup() {
        ArrayList<Process> processes = new ArrayList<Process>();
        File file = new File("./sample_input_sp19.txt");
        try {
            Scanner inp = new Scanner(file);
            while (inp.hasNextLine()) {
                String line = inp.nextLine();
                if (line.charAt(0) != '#') {
                    String[] values = line.split("\t");
                    if (values.length == 3) {
                        Process proc = new Process(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                        processes.add(proc);
                    } else {
                        System.out.println("ERROR IN INPUT FILE, EXITING");
                        System.exit(0);
                    }
                }
            }
            return processes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void run2(int startVal, ArrayList<Process> processesOriginal) {
        // Active queue for what's running
        ArrayList<Process> processes = new ArrayList<Process>(processesOriginal);
        Queue<Process> queue = new LinkedList<>();

        Process activeProcess = null;
        int tick = 0;
        int quantum = startVal;
        boolean active = false;

        // While there are still processes that haven't finished
        String line = "";
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
        System.out.println();
        System.out.println("Total ticks taken: " + tick);
        for(Process item:processesOriginal){
            System.out.print("Process "+item.getName()+"\tWait Time: "+item.getWaitTime()+"\tTurnaround Time: "+item.getTurnaroundTime());
            System.out.println();
        }

    }
}