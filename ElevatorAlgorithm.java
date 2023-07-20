package ElevatorSystem;

import java.util.*;

public class ElevatorAlgorithm {

    static int numOfElevators = 2;
    static int numberOfTimesButtonPresses = 4;
// test change
//    int[] input = {5, 4, 9, 10, 7, 6, 3};
//    int[] dir = {0, 1, 0, 0, 1, 0, 0};
    static PriorityQueue<Integer> down = new PriorityQueue<>((a, b) -> b - a);
    static PriorityQueue<Integer> up = new PriorityQueue<>();
    static int[] elevators = new int[numOfElevators];
    static int[] servicingElevators = new int[numOfElevators];

    public static void main(String args[]) throws InterruptedException {

        // One thread for processing UP requests and another to process Down requests
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    runElevators(down, random, "DOWN");
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    runElevators(up, random, "UP");
                }
            }
        };

        Thread t1 = new Thread(runnable1, "ServiceDown_Thread");
        Thread t2 = new Thread(runnable2, "ServiceUp_Thread");

        t1.start();
        t2.start();

    }

    private static void runElevators(PriorityQueue<Integer> queue, Random random, String dir) {
        while (!queue.isEmpty()) {
            //find elevator
            if (Math.abs(queue.peek() - elevators[0]) < Math.abs(queue.peek() - elevators[1]) && servicingElevators[0] == 0) {
                // use elevator 0
                System.out.println("Going " + dir + " Elevator 0 At: " + elevators[0]);
                servicingElevators[0] = 1;
                while (!queue.isEmpty()) {
                    int v = queue.poll();
                    System.out.println("Going " + dir + " Serviced by Elevator 0 and now at floor:" + v);
                    elevators[0] = v;
                }
                servicingElevators[0] = 0;
            } else if (servicingElevators[1] == 0) {
                // use elevator 1
                System.out.println("Going " + dir + " Elevator 1 At: " + elevators[1]);
                servicingElevators[1] = 1;
                while (!queue.isEmpty()) {
                    int v = queue.poll();
                    System.out.println("Going  " + dir + "  Serviced by Elevator 1 and now at floor:" + v);
                    elevators[1] = v;
                }
                servicingElevators[1] = 0;
            }
        }
        floorButtonPress(queue, random);
    }

    private static void floorButtonPress(PriorityQueue<Integer> queue, Random random) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Set<Integer> set = new HashSet<>();
        while (set.size() != numberOfTimesButtonPresses) {
            int d = random.nextInt(10) + 1;
            set.add(d);
        }
        for (int v : set) {
            queue.offer(v);
        }
    }
}
