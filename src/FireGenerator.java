package src;
public class FireGenerator implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int x = (int) (Math.random() * Main.SIZE);
            int y = (int) (Math.random() * Main.SIZE);

            Main.forestLock.lock();
            try {
                if (Main.forest[x][y] == 'T') { 
                    Main.forest[x][y] = '@'; 
                    Main.logEvent("Incêndio gerado em (" + x + "," + y + ")");
                    Main.refreshGUI(); 
                }
            } finally {
                Main.forestLock.unlock();
            }
        }
    }
}
