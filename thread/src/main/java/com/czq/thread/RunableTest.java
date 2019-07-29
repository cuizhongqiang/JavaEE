package com.czq.thread;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RunableTest {

	public static void main(String[] args) {
		TaskClient client = new TaskClient();
		new Thread(client, "ToyotaYQ_001").start();
		new Thread(client, "ToyotaYQ_002").start();
		new Thread(client, "ToyotaYQ_003").start();
	}

}

/**
 * 任务来源
 */
class MyTask {

	private static Queue<String> queue = null; // 并发队列(线程安全)

	/**
	 * 初始化并发队列
	 */
	public static Queue<String> initQueue() {

		if (queue == null) {
			queue = new ConcurrentLinkedQueue<String>();
		}

		String tasklist = "01-,02-,03-,04-,05-,06-,07-,08-,09-,10-,11-,12-,13-,14-,15-,16-,17-,18-,19-,20-";
		String[] strArray = tasklist.split(",");
		List<String> list = Arrays.asList(strArray);
		queue.addAll(list);
		return queue;
	}

}

/**
 * 制单客户端
 */
class TaskClient implements Runnable {

	private static final Object lock = new Object();
	private static Queue<String> queueYQ = MyTask.initQueue();

	@Override
	public void run() {
		while (true) {
			synchronized (lock) {// 尽量减小锁的粒度和范围
				String task = queueYQ.poll();// 获取并移除此队列的头，如果此队列为空，则返回 null
				if (task == null) {
					break;
				}
				System.out.println(Thread.currentThread().getName() + "成功制单：" + task + "。剩余：" + queueYQ.size() + "个任务");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
