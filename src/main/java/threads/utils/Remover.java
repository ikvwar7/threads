package threads.utils;

public class Remover implements Runnable {

    public Thread remover;

    public Remover() {
        remover = new Thread(this, "remover");
        remover.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                int removed;
                Thread.sleep(5000);
                synchronized (Values.list) {
                    if (Values.list.size() == 0) continue;

                    removed = Values.list.remove(0);
                }

                synchronized (Values.lock) {
                    while (!Values.allowToPrint) Values.lock.wait();

                    Values.allowToPrint = false;
                    System.out.println("Удалено " + removed);
                    Values.lock.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
