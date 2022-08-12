package edu.school21.numbers;

public class NumberWorker {
	public boolean isPrime(int number){
		if (number < 2)
			throw new IllegalNumberException();
		long q = (long)Math.sqrt((double) number);
		for (int j = 2; j <= q; j++){
			if (number % j == 0)
				return false;
		}
		return true;
	}
	public int digitsSum(int number){
		int sum = 0;

		while (number != 0){
			sum += number % 10;
			number /= 10;
		}
		return sum;
	}

	class IllegalNumberException extends RuntimeException {
		public IllegalNumberException() {
			super("Number has to be >= 2");
		}
	}

}
