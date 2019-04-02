package exam.Thread;

public class Task implements Runnable {
    static Integer sum = 0;

    @Override
    public void run() {
        addOne();
    }

    public synchronized void addOne() {
        sum++;
    }

    public synchronized int getSum() {
        return sum;
    }
}
