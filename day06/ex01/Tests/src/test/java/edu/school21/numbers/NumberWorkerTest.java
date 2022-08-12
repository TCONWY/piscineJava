package edu.school21.numbers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

	@ParameterizedTest
	@ValueSource(ints = {2, 3, 5, 7, 2147483647})
	void isPrimeReturnTrue(int prime) {
		assertTrue(new NumberWorker().isPrime(prime));
	}

	@ParameterizedTest
	@ValueSource(ints = {4, 6, 8, 9, 34})
	public void isPrimeReturnFalse(int notPrime) {
		assertFalse(new NumberWorker().isPrime(notPrime));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, Integer.MIN_VALUE, -12105 - 1212024})
	public void isPrimeReturnException(int num) {
		assertThrows(RuntimeException.class, ()-> new NumberWorker().isPrime(num));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
	public void isWorkCorrect(int in, int exp) {
		assertEquals(exp, new NumberWorker().digitsSum(in));
	}

	@Test
	public  void testNegativeSum() {
		assertEquals(-10, new NumberWorker().digitsSum(-154));
	}
}