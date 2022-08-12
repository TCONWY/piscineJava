import java.io.IOException;
import java.util.ArrayList;

public class MyThread extends Thread{

	private ParsUrls pu;
	private int n;
	private int i;
	private ArrayList<String> urls;

	MyThread(ParsUrls pu, int i, ArrayList<String> al){
		this.i = i;
		this.pu = pu;
		this.urls = al;
	}

	public ArrayList<String> getUrls(){
		return this.urls;
	}

	@Override
	public void run() {

		while (true) {
			n = pu.getIndex();
			if(urls.size() > n) {
				try {
					System.out.println("Thread-" + (i + 1) + " start download file number " + (n + 1));
					pu.urlDown(urls.get(n));
					System.out.println("Thread-" + (i + 1) + " end download file number " + (n + 1));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
	}

}
