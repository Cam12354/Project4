import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum WorkerType {
    NURSE, DOCTOR, OTHER
}

class Worker {
    WorkerType type;
    int arrivalTime;

    public Worker(WorkerType type, int arrivalTime) {
        this.type = type;
        this.arrivalTime = arrivalTime;
    }
}

public class CafeteriaSimulator {
    public static void main(String[] args) {
        // Constants
        final int SIMULATION_TIME = 120; // in minutes
        final int MAX_TOPPINGS = 6;
        final int MIN_TOPPINGS = 0;
        final int ORDER_PREPARATION_TIME = 1; // 1 minute for making the sandwich
        final int TIME_PER_TOPPING = 1; // 1 additional minute per topping

        // Variables to track wait times and counts for each worker type
        int totalNurses = 0;
        int totalDoctors = 0;
        int totalOthers = 0;
        int totalWaitTimeNurses = 0;
        int totalWaitTimeDoctors = 0;
        int totalWaitTimeOthers = 0;

        List<Worker> lunchLine = new ArrayList<>();
        Random random = new Random();

        for (int time = 0; time <= SIMULATION_TIME; time++) {
            // Check if a new person arrives in line
            if (random.nextDouble() < 1.0 / 3.0) {
                WorkerType newWorkerType = getRandomWorkerType(random);
                lunchLine.add(new Worker(newWorkerType, time));
                switch (newWorkerType) {
                    case NURSE:
                        totalNurses++;
                        break;
                    case DOCTOR:
                        totalDoctors++;
                        break;
                    case OTHER:
                        totalOthers++;
                        break;
                }
            }

            // Check if the person in front has received their sandwich
            if (!lunchLine.isEmpty()) {
                Worker currentWorker = lunchLine.get(0);
                int orderTime = currentWorker.arrivalTime;
                int toppings = random.nextInt(MAX_TOPPINGS - MIN_TOPPINGS + 1) + MIN_TOPPINGS;
                int preparationTime = ORDER_PREPARATION_TIME + (toppings * TIME_PER_TOPPING);
                int totalWaitTime = time - orderTime + preparationTime;

                switch (currentWorker.type) {
                    case NURSE:
                        totalWaitTimeNurses += totalWaitTime;
                        break;
                    case DOCTOR:
                        totalWaitTimeDoctors += totalWaitTime;
                        break;
                    case OTHER:
                        totalWaitTimeOthers += totalWaitTime;
                        break;
                }

                lunchLine.remove(0);
            }
        }

        // Calculate and display the average wait time for each worker type
        double avgWaitTimeNurses = (double) totalWaitTimeNurses / totalNurses;
        double avgWaitTimeDoctors = (double) totalWaitTimeDoctors / totalDoctors;
        double avgWaitTimeOthers = (double) totalWaitTimeOthers / totalOthers;

        System.out.println("Average Wait Time for Nurses: " + avgWaitTimeNurses + " minutes");
        System.out.println("Average Wait Time for Doctors: " + avgWaitTimeDoctors + " minutes");
        System.out.println("Average Wait Time for Others: " + avgWaitTimeOthers + " minutes");
    }

    // Helper method to get a random WorkerType
    private static WorkerType getRandomWorkerType(Random random) {
        int typeIndex = random.nextInt(3);
        return WorkerType.values()[typeIndex];
    }
}

