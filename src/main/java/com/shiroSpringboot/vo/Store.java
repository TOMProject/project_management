package com.shiroSpringboot.vo;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * 仓库类
 * @author Administrator
 *
 */
public class Store {
	/**最低仓库容量*/
	private int MAX_SIZE;
	/**当前仓库容量*/
	private int count;
	//初始化仓库容量
	public  Store(int n) {
		MAX_SIZE = n;
		count = 0;
	}
	
	//添加商品
	public synchronized void add() {
		while(count>=MAX_SIZE) {
			System.out.println("添加商品货物已经满了---》");
			try {
				this.wait();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		count++;
		//打印显示添加商品的线程和数量
		System.out.println(Thread.currentThread().toString()+" put "+count);
		//仓库还有货物，通知消费者来拿。
		this.notifyAll();
	}
	
	
	//拿走商品
	public synchronized void remove() {
		while(count<=0) {
			System.out.println("仓库已经空了");
			try {
				this.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		count--;
		//打印哪个线程拿走了商品
		System.out.println(Thread.currentThread().toString()+" remove " + count);
		//仓库还没有满通知生成者添加货物,notify,和notifyAll 的区别notify 只是唤醒等待的一个线程，具体唤醒哪个不知道
		//notifyAll是唤醒所以等待的线程，让他们自己去竞争锁。
		this.notifyAll();
	}
	
	public static void main(String[] args) {
		Store s = new Store(5);
		Thread pro = new Thread(new Producer(s));
		Thread pro2 = new Thread(new Producer(s));
		Thread pro3 = new Thread(new Producer(s));
		
		Thread con = new Thread(new Consumer(s));
		Thread con2 = new Thread(new Consumer(s));
		
		pro.setName("producer");
		pro2.setName("producer2");
		con.setName("consumer");
		con2.setName("consumer2");
		
		pro.start();
		pro2.start();
		pro3.start();
		con.start();
		//con2.start();
	}
}
//生产者类
class Producer extends Thread{
	private Store s;
	public  Producer(Store s) {
		this.s=s;
	}
	
	public void run() {
		while(true) {
			s.add();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
//消费者类
class Consumer extends Thread{
	private Store s;
	public  Consumer(Store s) {
		this.s = s;
	}
	
	public void run() {
		while(true) {
			s.remove();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	
}


