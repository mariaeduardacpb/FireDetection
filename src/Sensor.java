package src;
public class Sensor implements Runnable {
    private int x, y;
    private static final int DETECTION_DELAY = 1000; 

    public Sensor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        while (true) {
            Main.forestLock.lock();
            try {
                if (Main.forest[x][y] == '@') {
                    Main.logEvent("Sensor (" + x + "," + y + ") detectou incêndio!");
                    notifyNeighbors(x, y);
                    try {
                        Thread.sleep(DETECTION_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Main.forest[x][y] = 'C'; 
                    Main.refreshGUI(); 
                }
            } finally {
                Main.forestLock.unlock();
            }
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyNeighbors(int x, int y) {
        if (x > 0 && Main.forest[x - 1][y] == 'T') Main.forest[x - 1][y] = '@';
        if (x < Main.SIZE - 1 && Main.forest[x + 1][y] == 'T') Main.forest[x + 1][y] = '@';
        if (y > 0 && Main.forest[x][y - 1] == 'T') Main.forest[x][y - 1] = '@';
        if (y < Main.SIZE - 1 && Main.forest[x][y + 1] == 'T') Main.forest[x][y + 1] = '@';
    }
}
