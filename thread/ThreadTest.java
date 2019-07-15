package com.cbmie.lh.thread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadTest {

	public static void main(String[] args) {

		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(2);
		ExecutorService service = Executors.newCachedThreadPool();

		// 生产者
		Producer p1 = new Producer("A", queue);
		Producer p2 = new Producer("B", queue);
		Producer p3 = new Producer("C", queue);
		Producer p4 = new Producer("D", queue);
		Producer p5 = new Producer("E", queue);

		// 消费者
		Consumer c1 = new Consumer("ToyotaYQ_001", queue);
		Consumer c2 = new Consumer("ToyotaYQ_002", queue);
		Consumer c3 = new Consumer("ToyotaYQ_003", queue);

		service.execute(p1);
		service.execute(p2);
		service.execute(p3);
		service.execute(p4);
		service.execute(p5);
		service.execute(c1);
		service.execute(c2);
		service.execute(c3);

	}

}

/**
 * 生产者
 */
class Producer extends Thread {

	private String producerName;
	private BlockingQueue<Integer> blockingQueue;// 阻塞队列(线程安全)
	private Random r = new Random();

	public Producer(String producerName, BlockingQueue<Integer> blockingQueue) {
		this.producerName = producerName;
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {

		while (true) {
			try {

				int task = r.nextInt(100);
				System.out.println(producerName + "开始生产任务：" + task);
				blockingQueue.put(task); // 生产者向队列中放入一个随机数
				Thread.sleep(5000); // 减缓生产者生产的速度，如果队列为空，消费者就会阻塞不会进行消费直到有数据被生产出来

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}

class Consumer extends Thread {
	private String consumerName;
	private BlockingQueue<Integer> blockingQueue;// 阻塞队列

	// 构造函数,传入消费者名称和操作的阻塞队列
	public Consumer(String consumerName, BlockingQueue<Integer> blockingQueue) {
		this.consumerName = consumerName;
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(consumerName + "开始消费任务：" + blockingQueue.take());// 消费者从阻塞队列中消费一个随机数
				// Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}