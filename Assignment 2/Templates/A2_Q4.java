import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;

public class A2_Q4 {

	String fib0 = "X";
	String fib1 = "Y";

	public static void main(String[] args) {
		int N = 2;
		Long K = 1L;
		BigInteger intK = BigInteger.valueOf(K.intValue());
		String s = mod_fibonacci(N, intK);
		System.out.println(fib(3));
		System.out.println(s);
	}

	public static String mod_fibonacci(int N, BigInteger K) {
		String s = fibString(N, K);
		return s;
	}




	private static String fibString(Integer n, BigInteger k){
		if(k.compareTo(BigInteger.ONE)==0 && n==1){
			return "X";
		}else if(k.compareTo(BigInteger.ONE)==0 && n==2){
			return "Y";
		}

		while(true) {
			if(n == 1){
				if(k.compareTo(BigInteger.ONE)==0){
					return "X";
				}
			}else if(n == 2){
				if(k.compareTo(BigInteger.ONE)==0){
					return "Y";
				}
			}else if(n == 3){
				if(k.compareTo(BigInteger.ONE)==0){
					return "X";
				}else {
					return "Y";
				}
			}

			BigInteger lowerNum = fib(n-2);
			BigInteger greaterNum = fib(n-1);

			/*
			if((lowerNum.compareTo(BigInteger.ONE)==0 && greaterNum.compareTo(BigInteger.ONE)==0) || (lowerNum.compareTo(BigInteger.ONE)==0 && greaterNum.compareTo(BigInteger.TWO)==0) || (lowerNum.compareTo(BigInteger.ZERO)==0 && greaterNum.compareTo(BigInteger.ONE)==0)){
				if(k.compareTo(BigInteger.ONE) == 0){
					return "X";
				}else {
					return "Y";
				}
			}



			if((lowerNum.compareTo(BigInteger.ONE)==0 && greaterNum.compareTo(BigInteger.TWO)==0)){
				if(k.compareTo(BigInteger.ONE) == 0){
					return "X";
				}else {
					return "Y";
				}
			}else if((lowerNum.compareTo(BigInteger.ONE)==0 && greaterNum.compareTo(BigInteger.ONE)==0)){
				if(k.compareTo(BigInteger.ONE) == 0){
					return "X";
				}else {
					return "Y";
				}
			}
*/

			if(k.compareTo(lowerNum) > 0){	//k > lowerNum then k is in greaterNum
				k = k.subtract(lowerNum);
				n = n-1;
			}else {	//k < lowerNum then k is in lowerNum
				n = n-2;
			}

		}
	}

	private static BigInteger fib(int n) {
		double x = (1+Math.sqrt(5))/2;
		double y = (1-Math.sqrt(5))/2;
		double a = Math.pow(x, n);
		double b = Math.pow(y, n);
		Long num = (long) ((1/Math.sqrt(5)) * (a-b));
		BigInteger bigNum = BigInteger.valueOf(num.intValue());
		return bigNum;
	}

}
