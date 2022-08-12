import java.util.Scanner;
import java.util.regex.MatchResult;

public class Program {
	private static int y = 0;
	private static int a = 0;
	private static int suma = 0;
	private static int count = 0;
	public static void main(String[] args){
		if (args.length != 2){
			return;
		}
		int sum = 0;
		int countThreads = 0;
		final Object object = new Object();
		final int arraySize = pars_count(args[0], 0);
		final int threadsCount = pars_count(args[1], 1);

		if (arraySize <= 0 || threadsCount <= 0 || arraySize > 2000000 || threadsCount > arraySize){
			System.out.println("illegal argument");
			return;
		}

		if (arraySize % threadsCount == 0)
			count = arraySize / threadsCount;
		else {
			count = arraySize / threadsCount;
			count++;
		}

		final int[] array = new int[arraySize];
		for (int i = 0; i < array.length; i++) {
			array[i] = ((int) ((Math.random() * 2000) - 1000));
			sum += array[i];
		}

		System.out.println("Sum:" + sum);
		for (int i = 0; i < threadsCount; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (object) {
						if (y + 1 == threadsCount)
							count = arraySize - a;
						int c = count;
						int f = 0;
						System.out.print("Thread " + (y + 1) + ": from " + a + " to ");
						while (f++ < c) {
							suma += array[a++];
						}
						System.out.println(a - 1 + " sum is " + c);
						y++;
					}
				}
			}).run();
		}
		while (y != threadsCount) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Sum by threads:" + sum);
	}

	public static int pars_count(String args, int i){
		Scanner scanner = new Scanner(args).useDelimiter("=");
		String str = scanner.next();
		if(str.equals("--threadsCount") && i == 1) {
			if (scanner.hasNextInt())
				return scanner.nextInt();
		}
		else if(str.equals("--arraySize") && i == 0) {
			if (scanner.hasNextInt())
				return scanner.nextInt();
		}
		return 0;
	}
}
