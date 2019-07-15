package com.cnbmtech;

public class ThreadLocalTest {

	public static ThreadLocal<Integer> num = new ThreadLocal<Integer>() {
		 public Integer initialValue() {
			return 0;
		 }
	};

	public static void main(String[] args) throws InterruptedException {
		Num test1 = new Num();
		Num test2 = new Num();
		Num test3 = new Num();
		
		test1.start();
		test2.start();
		test3.start();
	}
	
	public static class Num extends Thread {
		
		public void run() {
			for(int i = 0; i < 5; i++){
				System.out.println(Thread.currentThread().getName() + "线程：" + "i = " + ThreadLocalTest.num.get());
				ThreadLocalTest.num.set(ThreadLocalTest.num.get() + 1);
			}
		}
		
	}
	
}
