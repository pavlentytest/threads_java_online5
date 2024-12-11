public class Main {
    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            // логика потока
        }).start();


        /*
        2 потока
        1 поток -> [+][+][+][+]
        2 поток -> [-][-][-][-]
        Синхронизация: [+][-][+][-][+][-]......
         */

        MyThread thread1 = new MyThread("+");
        MyThread thread2 = new MyThread("-");
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        thread1.flag = false;
        thread1.join(); // присоед к потоку и ждет его завершения
        printMessage("First thread is stopped!");
    }

    public static Object key = new Object();
    public static void printMessage(String message) {
        synchronized (key) {
            System.out.print("[");
            System.out.print(message);
            System.out.print("]");

            try {
                Thread.sleep(300);
             //   key.notify(); // возобновляет поток наход в режиме waiting
             //   key.wait(); // поставить поток в режим waiting
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
class MyThread extends Thread {

    public String message;
    public boolean flag = true;

    MyThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        while(flag) {
            Main.printMessage(this.message);
        }
    }
}


/*
class MyThread2 implements Runnable {

    @Override
    public void run() {
        // логика потока
    }
}

 */