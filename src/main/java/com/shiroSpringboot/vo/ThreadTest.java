package com.shiroSpringboot.vo;

public class ThreadTest {
	public static void main(String[] args) {
		new myThread01().start();
		new myThread01().start();
		new myThread01().start();
	}
} 

/**
 * synchronized 关键子让线程同步，synchronized可以加在方法上就是同步方法，
 * synchronize单独将需要同步的代码包含起来就是同步代码块。
 * synchronize的原理是：每个对象都可以有一个线程锁，synchronize可以用任何一个对象来锁住一个代码块，任何想进入该代码块的
 * 线程都必须在解锁之后才能继续执行，否则进入等待状态，其中，只用等待占用资源的线程执行完毕后，该资源才能会被释放。
 * 如果synchronize加在方法上面，此时的锁就是加在this所引用的对象上。
 * 
 * @author 
 *
 */


class myThread01 extends Thread{
	private static int a;
	private static Object obj = new Object();
	public  void run() {
		synchronized (obj) {
			for(int i = 0 ;i<=10;i++) {
				System.out.println(getName()+":"+a++);
			}
		}
	}

}
