package com.czq.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Data;

public class RunableTest2 {
	
	public static void main(String[] args) throws Exception {
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		
//		String[] lwj = new String[] {"关羽", "张飞", "赵云"};
//		String[] cwj = new String[] {"张辽", "许褚", "张郃"};
//		String[] swj = new String[] {"程普", "黄盖", "韩当"};
		
		Task taskL = new Task("蜀", "关羽");
		Task taskC = new Task("魏", "张辽");
		Task taskS = new Task("吴", "程普");
		
		Worker workC = new Worker("曹二");
		Worker workL = new Worker("刘一");
		Worker workS = new Worker("孙三");
		
		executor.execute(taskL);
		executor.execute(taskC);
		executor.execute(taskS);
		executor.execute(workC);
		executor.execute(workL);
		executor.execute(workS);
		
	}
	
	public static BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>(5); //定长为2的阻塞队列
	
	/**
	 * task
	 */
	@Data
	public static class Task implements Runnable {
		
		public Task(String name, String string) {
			this.name = name;
			this.string = string;
		}
		
		private String name;
		
		private String string;

		@Override
		public void run() {
			
			try {
				System.out.println(this.name + "派出" + this.string + "执行任务！！！！！！");
				queue.put(this);
				Thread.sleep(5000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * worker
	 */
	@Data
	public static class Worker implements Runnable {
		
		private String name;
		
		public Worker(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			
			while (true) {
				try {
					Task task = queue.take();
					System.out.println(this.name + "杀了" + task.getName() + " : " + task.getString());
					Thread.sleep(5000l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
