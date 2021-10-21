package multithreading;

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
