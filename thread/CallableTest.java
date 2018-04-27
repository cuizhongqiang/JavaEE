package com.cbmie.lh.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableTest {

	public static void main(String[] args) {

		// 模拟需要并发的参数 也有可能是对象集合，String可以使用其他任意对象（包含集合对象）进行替换
		List<String> params = new ArrayList<String>();
		params.add("first");
		params.add("Second");
		params.add("Third");
		params.add("Forth");

		// 线程池，可以为固定大小，也可以使用Executors.newCachedThreadPool()根据环境自动创建合适的大小池

		ExecutorService executor = Executors.newFixedThreadPool(5);

		// 将线程加入到集合中
		List<Callable<String>> list = new ArrayList<Callable<String>>();
		for (String param : params) {
			list.add(new Job(param));
		}
		try {
			// 线程池加载线程
			List<Future<String>> futures = executor.invokeAll(list);

			// 获取每个线程返回的结果，可以进行合并，作为多并发的结果
			for (Future<String> future : futures) {
				String result = future.get();
				System.out.println(result);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}
}

/**
 * Created by Administrator on 2016/6/30.
 */
class Job implements Callable<String> {
	private String name;

	private String jobName;

	public Job(String name) {
		this.name = name;
	}

	@Override
	public String call() throws Exception {
		this.jobName = Thread.currentThread().getName();
		// 使用传入的参数执行一些其他的操作，比如对数据库进行操作
		return "单钱线程为：" + jobName + ",传入的参数为：" + name;
	}
}