package ElevatorSystem;

import java.util.*;

public class ElevatorAlgorithm {

    public static void main(String args[]) throws InterruptedException {
        int numOfElevators = 2;
        int[] input = {5, 4, 9, 10, 7, 6, 3};
        int[] dir = {0, 1, 0, 0, 1, 0, 0};
        PriorityQueue<Integer> down = new PriorityQueue<>((a, b) -> b - a);
        PriorityQueue<Integer> up = new PriorityQueue<>();
        int[] elevators = new int[numOfElevators];
        int[] servicingElevators = new int[numOfElevators];

        // Number of threads here equals number of elevators
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    while (!down.isEmpty()) {
                        //find Elevator
                        if (Math.abs(down.peek() - elevators[0]) < Math.abs(down.peek() - elevators[1]) && servicingElevators[0] == 0) {
                            // use elevator 0
                            System.out.println("Going Down Elevator 0 At: " + elevators[0]);
                            servicingElevators[0] = 1;
                            while (!down.isEmpty()) {
                                int v = down.poll();
                                System.out.println("Going Down Serviced by Elevator 0 and now at floor:" + v);
                                elevators[0] = v;
                            }
                            servicingElevators[0] = 0;
                        } else if (servicingElevators[1] == 0) {
                            // use elevator 1
                            servicingElevators[1] = 1;
                            System.out.println("Going Down Elevator 1 At: " + elevators[1]);
                            while (!down.isEmpty()) {
                                int v = down.poll();
                                System.out.println("Going Down Serviced by Elevator 1 and now at floor:" + v);
                                elevators[1] = v;
                            }
                            servicingElevators[1] = 0;
                        }
                    }
                    floorButtonPress(random);
                }
            }

            private void floorButtonPress(Random random) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Set<Integer> set = new HashSet<>();
                while (set.size() != 4) {
                    int d = random.nextInt(10) + 1;
                    set.add(d);
                }
                for (int v : set) {
                    down.offer(v);
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                while (true) {
                    while (!up.isEmpty()) {
                        //find elevator
                        if (Math.abs(up.peek() - elevators[0]) < Math.abs(up.peek() - elevators[1]) && servicingElevators[0] == 0) {
                            // use elevator 0
                            System.out.println("Going Up Elevator 0 At: " + elevators[0]);
                            servicingElevators[0] = 1;
                            while (!up.isEmpty()) {
                                int v = up.poll();
                                System.out.println("Going Up Serviced by Elevator 0 and now at floor:" + v);
                                elevators[0] = v;
                            }
                            servicingElevators[0] = 0;
                        } else if (servicingElevators[1] == 0) {
                            // use elevator 1
                            System.out.println("Going Up Elevator 1 At: " + elevators[1]);
                            servicingElevators[1] = 1;
                            while (!up.isEmpty()) {
                                int v = up.poll();
                                System.out.println("Going Up Serviced by Elevator 1 and now at floor:" + v);
                                elevators[1] = v;
                            }
                            servicingElevators[1] = 0;
                        }
                    }
                    floorButtonPress(random);
                }
            }

            private void floorButtonPress(Random random) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Set<Integer> set = new HashSet<>();
                while (set.size() != 4) {
                    int d = random.nextInt(10) + 1;
                    set.add(d);
                }
                for (int v : set) {
                    up.offer(v);
                }
            }
        };

        Thread t1 = new Thread(runnable1, "ServiceDown_Thread");
        Thread t2 = new Thread(runnable2, "ServiceUp_Thread");

        t1.start();
        t2.start();

    }
}
