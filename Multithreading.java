/**
 * @author AJWuu
 */

package multithreading;

/*
 * Multithreading allows the execution of multiple parts of a program at the same time.
 * These parts are known as threads and are lightweight processes available within the process.
 * So, multithreading leads to maximum utilization of the CPU by multitasking.
 * 
 * Resource Sharing:
 * All the threads of a process share its resources such as memory, data, files etc.
 * A single application can have different threads within the same address space using resource sharing.
 * 
 * Responsiveness:
 * Program responsiveness allows a program to run even if part of it is blocked using multithreading.
 * This can also be done if the process is performing a lengthy operation.
 * For example - A web browser with multithreading can use one thread for user contact and another for image loading at the same time.
 * 
 * Utilization of Multiprocessor Architecture:
 * In a multiprocessor architecture, each thread can run on a different processor in parallel using multithreading.
 * This increases concurrency of the system.
 * This is in direct contrast to a single processor system, where only one process or thread can run on a processor at a time.
 * 
 * Economy:
 * It is more economical to use threads as they share the process resources.
 * Comparatively, it is more expensive and time-consuming to create processes as they require more memory and resources.
 * The overhead for process creation and management is much higher than thread creation and management.
 */

class ThreadDemo extends Thread {
	public void run() {
		try {
			System.out.println("ThreadDemo " + Thread.currentThread().getId() + " is running");
		} catch (Exception e) {
			System.out.println("Exception is caught");
		}
	}
}

class RunnableDemo implements Runnable {
	public void run() {
		try {
			System.out.println("RunnableDemo " + Thread.currentThread().getId() + " is running");
		} catch (Exception e) {
			System.out.println("Exception is caught");
		}
	}	
}

public class Multithreading {

	public static void main(String[] args) {
		int n = 8;
		for (int i=0; i<n; i++) {
			ThreadDemo T = new ThreadDemo();
			Thread R = new Thread(new RunnableDemo());
			//Runnable does not have start(), so we need to create a thread for it
			T.start();
			R.start();
		}
	}

}
