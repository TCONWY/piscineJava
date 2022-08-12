public class MyThread extends Thread{
	private String word;
	private int count;
	private int tar;

	public MyThread(String word, int count){
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