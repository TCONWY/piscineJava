package edu.school21.numbers;

public class NumberWorker {
    public  boolean isPrime(int number){
        if (number < 2) {
            throw new IllegalNumberException();
        }
        long _sqrtI = (long) Math.sqrt((double) number);
        int c = 2;
        while(c <= _sqrtI){
            if (number % c == 0){
                c++;
                return false;
            }
            c++;
        }
        return true;
    }

    public int digitSum(int number){
        int i = 0;
        while(number != 0){
            i += number % 10;
            number = number / 10;
        }
        return i;
    }
    class IllegalNumberException extends RuntimeException {
        public IllegalNumberException() {
            super("Number has to be >= 2");
        }
    }
}