import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Program {
	public static void main(String[] args) {
		final Object object = new Object();
		int count1 = 0;
		final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
		if (args.length == 1 && args[0] != null){
			count1 = pars_count(args);
		}
		if (count1 == 0){
			System.out.println("Error Argument");
			return;
		}
		final int count = count1;
		new Thread(new Runnable() {
			int c = count;
			@Override
			public void run() {
				while (c-- >= 0)
					synchronized (object){
						while (!atomicBoolean.get()){
							try {
								object.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println("Egg");
						atomicBoolean.set(false);
						object.notify();
					}
			}
		}).start();

		new Thread(new Runnable() {
			int c = count;
			@Override
			public void run() {
				while (c-- >= 0)
					synchronized (object){
						while (atomicBoolean.get()){
							try {
								object.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println("Hen");
						atomicBoolean.set(true);
						object.notify();
					}
			}
		}).start();
	}

	public static int pars_count(String[] args){
		Scanner scanner = new Scanner(args[0]).useDelimiter("=");
		if(scanner.next().equals("--count")) {
			if (scanner.hasNextInt())
				return scanner.nextInt();
		}
		return 0;
	}

}
