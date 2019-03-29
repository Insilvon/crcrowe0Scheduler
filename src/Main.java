import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int quantum = 3;
        if (args.length!=0) quantum = Integer.parseInt(args[0]);
        ArrayList<Process> temp = setup();
        run2(quantum, temp);
    }

    private static ArrayList<Process> setup(){
        ArrayList<Process> processes = new ArrayList<Process>();
        File file = new File("./sample_input_sp19.txt");
        try {
            Scanner inp = new Scanner(file);
            while(inp.hasNextLine()){
                String line = inp.nextLine();
                if (line.charAt(0) != '#'){
                    String[] values = line.split("\t");
                    if(values.length==3) {
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
    private static void run2(int startVal, ArrayList<Process> processes) {
        // Active queue for what's running
        Queue<Process> queue = new LinkedList<>();

        Process activeProcess = null;
        int tick = 0;
        int quantum = startVal;
        boolean active = false;

        // While there are still processes that haven't finished
        String line = "";
        while(processes.size()!=0){
            //check for new additions
            for (Process item : processes) {
                if (item.getArrivalTime() == tick) {
                    queue.add(item);
                }
            }
            //are we running?
            if(!active){
                // No, nothing is running
                if (!queue.isEmpty()) {
                    activeProcess = queue.remove();
                    System.out.print(activeProcess.getName());
                    activeProcess.decrement();
                    quantum = startVal-1;
                    active = true;
                } else {
                    quantum = startVal;
                }
            } else {
                // Yes, we are running
                if(activeProcess.getTimeLeft()==0){
                    active = false;
                    activeProcess.decrement();
                    System.out.print("|");
                    processes.remove(activeProcess);
                    continue;
                }
                else {
                    if(quantum==0){
                        queue.add(activeProcess);
                        if (((LinkedList<Process>) queue).getFirst() == null) {
                            quantum = startVal;
                        }
                        else {
                            activeProcess = queue.remove();

                            System.out.print("|"+activeProcess.getName()+" ");
                            quantum = startVal-1;
                            activeProcess.decrement();
                        }
                    }
                    else {
                        activeProcess.decrement();
                        System.out.print(" _ ");
                        quantum--;
                    }
                }
            }
            tick++;
        }
        System.out.println();
        System.out.println("Total ticks taken: "+tick);
    }
    private static void run(int startVal, ArrayList<Process> processes){
        // Active queue for what's running
        Queue<Process> queue = new LinkedList<>();

        Process activeProcess = null;
        int tick = 0;
        int quantum = startVal;
        boolean active = false;

        // While there are still processes that haven't finished
        String line = "";
        while(processes.size()!=0) {
            //check for new arrivals
            for (Process item : processes) {
                if (item.getArrivalTime() == tick) {
                    queue.add(item);
                }
            }
            // Is anything on the hot seat?
            if (active) {
                // If the active process is donezo, remove it and turn off its hold
                if (activeProcess.getTimeLeft() == 0) {
                    processes.remove(activeProcess);
                    line+=("| ");
                    active = false;
                    quantum = startVal;
                }
                // If the active process is out of time, add it back to the queue
                else if (quantum == 1){
                    activeProcess.decrement();
                    queue.add(activeProcess);
                    line+=(" _| ");
                    activeProcess = queue.remove();
                    activeProcess.decrement();
                    line+=(activeProcess.getName());
                    active = true;
                    quantum = startVal-1;

                }
                else {
                    activeProcess.decrement();
                    quantum--;
                    line+=(" _ ");
                }

            }
            // If nothing is currently running
            else {
                // Is the next thing ready???
                if (((LinkedList<Process>) queue).getFirst().getArrivalTime() <= tick) {
                    activeProcess = queue.remove();
                    activeProcess.decrement();
                    line+=(activeProcess.getName());
                    active = true;
                    quantum = startVal-1;
                }

            }
            tick++;
        }
        System.out.println();
        System.out.println(line);
        System.out.println("Total ticks taken originally: "+tick);
//        tick = 0;
//        for(int i = 0; i<line.length();i++){
//            if(line.charAt(i)=='_')tick++;
//        }
//        System.out.println(line);
//        System.out.println("Total ticks taken: "+tick);
    }
}

