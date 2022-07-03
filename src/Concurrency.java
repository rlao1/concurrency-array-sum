import java.util.*;


public class Concurrency {

	public static void main(String[] args) throws InterruptedException
	{
		Random rand = new Random();
		int[] array = new int[2000000];

		for(int i = 0; i < array.length; i++) 
		{
			array[i] = rand.nextInt(10) + 1;
		}
		
		System.out.printf("%-10s %-10s Time\n", new String("Threads"), new String("Output"));
		
		SingleThread.sum(array);
		
		// multithread
		int numOfThreads = 4;
		int finalSum = 0;

		long startTime = System.nanoTime();
		for(int i = 0; i < numOfThreads; i++)
		{
			MultiThread multi = new MultiThread(array, numOfThreads, i);
			multi.start();
			multi.join();
			finalSum += multi.getSum();
		}
		long duration = System.nanoTime()-startTime;
		System.out.printf("%-10s %-10d %d (ns)\n", new String("Multi"), finalSum, duration);
	
	}

}

class SingleThread {
	
	public static void sum(int[] arr)
	{
		int sum = 0;
		long startTime = System.nanoTime();
		
		for(int i = 0; i < arr.length; i++) 
		{
			sum += arr[i];
		}
		
		long duration = System.nanoTime()-startTime;
		System.out.printf("%-10s %-10d %d (ns)\n", new String("Single"), sum, duration);
	}
}

class MultiThread extends Thread {
	private int[] array;
	private int threadTotal;
	private int threadCount;
	private volatile int threadSum;
	
	public MultiThread(int[] arr, int total, int count) 
	{
		array = arr;
		threadTotal = total;
		threadCount = count;
	}
	
	
	public void run() 
	{ 
		int split = array.length / threadTotal; 
		int position = split * threadCount; 
		int sum = 0;
  
		for(int i = position; i < position + split; i++) 
		{ 
			sum += array[i]; 
		}
  
		threadSum += sum;
	}
	
	public int getSum()
	{
		return threadSum;
	}
	
}
