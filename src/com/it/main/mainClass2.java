package com.it.main;
/*
	메소드 => 데이터베이스 연결
	-------------------- 예외처리
	
	
	

*/
public class mainClass2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr= new int[5]; // 0 , 0 , 0 , 0 , 0
		for(int i:arr) {
			System.out.println("i="+i);
		}
		getRand(arr); //배열, 클래스 => 매개변수로 사용하게 되면 변경된 값을 전송 받을 수 있다.
		System.out.println("변경후===");
		for(int i:arr) {
			System.out.println("i="+i);
		}
		/*
			반복문
			for 형태 : 일반for문 => 값을 제어 할 때
					  형식)
					  	for(초기값 ; 조건식 ; 증감식)
					  	{
					  		반복 실행문장
					  	}
					  for-each 구문 => 출력만 할 때(JSP화면출력) 
					  for(데이터형 변수 : 배열,컬렉션)
					  {   --------- 인덱스가 아닌 실제 배열에 저장된 값을 가지고 온다.
					  	
					  }
		*
		*/
		
	}
	public static void getRand(int[] arr) {
		for(int i=0; i<arr.length; i++) {
			arr[i]=(int)(Math.random()*100)+1;
			///          -----------------
			///			 Math.random() => 0.0~0.99
			///    ------
			///	   0.0~99.0
			///    0~99
			///                            ---
			///							   1~100
			
		}
	}
}
