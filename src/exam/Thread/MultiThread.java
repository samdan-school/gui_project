package exam.Thread;

public class MultiThread {
    Thread[] workers = new Thread[1000];
    Task newTask = new Task();

    public MultiThread() {
        try {
            for (Thread worker : workers) {
                worker = new Thread(newTask);
                worker.start();
                worker.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(newTask.sum);
    }

    public static void main(String[] args) {
        new MultiThread();
    }
}
