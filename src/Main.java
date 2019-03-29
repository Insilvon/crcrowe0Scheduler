import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Main class which will create a new Scheduler and run it with the quantum time
 * specified by the user.
 */
public class Main {
    /**
     * Main method. Checks arguments for a supplied quantum time, reads the input file
     * to generate a list of Process Objects, then will create a scheduler with the two
     * paramaters and run it.
     * @param args - a quantum time for the Round Robin Scheduler
     */
    public static void main(String[] args) {
        System.out.println();
        int quantum = 3;
        if (args.length != 0) quantum = Integer.parseInt(args[0]);
        ArrayList<Process> temp = setup();
        Scheduler scheduler = new Scheduler(quantum, temp);
        scheduler.run();
    }

    /**
     * Helper method that will look for "sample_input_sp19.txt"
     * and will read through it, creating a Process Object for each process line.
     * @return ArrayList of all the Process Objects created.
     */
    private static ArrayList<Process> setup() {
        ArrayList<Process> processes = new ArrayList<>();
        File file = new File("sample_input_sp19.txt");
        try {
            Scanner inp = new Scanner(file);
            while (inp.hasNextLine()) {
                String line = inp.nextLine();
                if (line.length()!=0&&line.charAt(0) != '#') {
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
}