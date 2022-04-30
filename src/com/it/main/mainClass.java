package com.it.main;

/*
	자바기본
	------
	1.변수
	2.연산자
	3.제어문
	------ +메소드
			----
		   +클래스
		    ----
		   +패키지
	
*/
public class mainClass {
	public static void main(String[] args) {
		byte b1 = 127; //127
		byte b2 = 32;
		//byte b3 = (byte)(b1 + b2);
		int b3 = b2+b1;
		System.out.println(b3);
		
		double d=123456.78;
		int a=(int)((d-123456)*100);
		
	}
}
