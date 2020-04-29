package my.test;

import java.util.Random;

public class ThreadLocalTest {
/**
 * ThreadLocal 线程局部变量，功能是为每一个使用该变量的线程都提供一个变量值的副本，是Java中一种较为特殊的线程绑定机制，是每一个线程都可以独立地改变自己的副本，而不会和其它线程的副本冲突
 * 从线程的角度看，每个线程都保持一个对其线程局部变量副本的隐式引用，只要线程是活动的并且 ThreadLocal 实例是可访问的；在线程消失之后，其线程局部实例的所有副本都会被垃圾回收（除非存在对这些副本的其他引用）
 * ThreadLocal采用了“以空间换时间”的方式，为每一个线程都提供了一份变量，因此可以同时访问而互不影响。
 * 
 * ThreadLocal中定义了一个ThreadLocalMap，用于储存每一个线程的变量的副本
 * ThreadLocal中的get、set方法，进去方法后是使用Thread.currentThread();获取当前线程，再获取当前线程中的ThreaLocal中的ThreaLocalMap
 * 通过中的get、set方法，可以获取、更新每个Thread的线程变量副本。
 * 
 */
	
	public static void main(String[] args) throws InterruptedException{
		//1和2打印的num可能不同，因为ThreadLocal传递的是副本
		ThreadLocal<Student> tl = new ThreadLocal<>();
		ThreadTest thread1=new ThreadTest("Thread1",tl);
		ThreadTest thread2=new ThreadTest("Thread2",tl);
		thread1.start();
		thread2.start();
		
		//3和4打印的num相同
		Student stu=new Student();
		ThreadTest1 thread3=new ThreadTest1("Thread3",stu);
		ThreadTest1 thread4=new ThreadTest1("Thread4",stu);
		thread3.start();
		thread4.start();
		
	}
	
	
}
class Student{
	int age;
	String name;
	
}
class ThreadTest extends Thread{
	ThreadLocal<Student> tl;
	String name;
	public ThreadTest(){
		
	}
	
	public ThreadTest(String name,ThreadLocal<Student> tl){
		this.name=name;
		this.tl=tl;
	}
	
	@Override
	public void run() {
		Student stu;
		int i=5;
		while(i>0){
			if((stu=tl.get())==null){
				Random random=new Random();
				int num=random.nextInt(1000);
				stu=new Student();
				stu.age=num;
				stu.name=this.name;
				tl.set(stu);
			}
			
			System.out.println(stu.name+" is printing the num ["+stu.age+"]");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i--;
		}
	}
}

class ThreadTest1 extends Thread{
	Student stu;
	String name;
	public ThreadTest1(){
		
	}
	
	public ThreadTest1(String name,Student stu){
		this.name=name;
		this.stu=stu;
	}
	
	@Override
	public void run() {
		int i=5;
		while(i>0){
			if(stu.age==0){
				Random random=new Random();
				int num=random.nextInt(1000);
				stu.age=num;
			}
			
			System.out.println(name+" is printing the num ["+stu.age+"]");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i--;
		}
	}
}
