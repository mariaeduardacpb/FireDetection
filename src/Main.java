package src;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.SwingUtilities;

public class Main {
    public static final int SIZE = 30;
    public static char[][] forest = new char[SIZE][SIZE];
    public static ReentrantLock forestLock = new ReentrantLock();
    public static ForestGUI gui; 

    public static void main(String[] args) {
        Thread[][] sensorThreads = new Thread[SIZE][SIZE];
        Thread fireThread;
        Thread controlCenterThread;

        initializeForest();

        SwingUtilities.invokeLater(() -> {
            gui = new ForestGUI();
        });

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (forest[i][j] == 'T') {
                    Sensor sensor = new Sensor(i, j);
                    sensorThreads[i][j] = new Thread(sensor);
                    sensorThreads[i][j].start();
                }
            }
        }

        fireThread = new Thread(new FireGenerator());
        fireThread.start();

        controlCenterThread = new Thread(new ControlCenter());
        controlCenterThread.start();

        try {
            fireThread.join();
            controlCenterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initializeForest() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                forest[i][j] = (Math.random() < 0.7) ? '-' : 'T'; 
            }
        }
    }

    public static void refreshGUI() {
        SwingUtilities.invokeLater(() -> gui.refreshForest());
    }

    public static void logEvent(String event) {
        SwingUtilities.invokeLater(() -> gui.addLog(event));
    }
}
