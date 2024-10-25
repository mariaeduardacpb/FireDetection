package src;
public class ControlCenter implements Runnable {
    private static final int COMBAT_DELAY = 2000; 

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Main.forestLock.lock();
            try {
                for (int i = 0; i < Main.SIZE; i++) {
                    for (int j = 0; j < Main.SIZE; j++) {
                        if (Main.forest[i][j] == 'C') { 
                            Main.logEvent("Central de controle combatendo incêndio em (" + i + "," + j + ")");
                            Main.forest[i][j] = '/'; 
                            try {
                                Thread.sleep(COMBAT_DELAY); 
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Main.refreshGUI(); 
                        }
                    }
                }
            } finally {
                Main.forestLock.unlock();
            }
        }
    }
}
