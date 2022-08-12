public class MyThread extends Thread{
    private String word;
    private int count;
    private int tar;

    public MyThread(int arraySize, int threadsCount, int i){
        this.word = word;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.count; i++) {
            System.out.println(this.word);
        }
    }
}
