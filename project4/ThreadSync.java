// File: ThreadSync.java
// For: CS 350, Project #4
// Author: Matthew Leeds
// Last Edit: 10.26.2014
// Purpose: Use Semaphores to coordinate three threads printing this pattern:
// ":--))))):---))))):---))))):---)))))..."

import java.lang.Thread;
import java.util.concurrent.*;

public class ThreadSync
{
	
    private static boolean runFlag = true;
	
    private static Semaphore canPrintC = new Semaphore(1); // start to print colons (and control others)
    private static Semaphore canPrintD = new Semaphore(0); // start to print dashes
    private static Semaphore donePrintD = new Semaphore(0); // have all dashes been printed?
    private static Semaphore canPrintP = new Semaphore(0); // start to print parentheses
    private static Semaphore donePrintP = new Semaphore(0); // have all parentheses been printed?

    
    public static void main( String[] args ) {

        // create and start each runnable
        Runnable task1 = new TaskPrintC();
        Runnable task2 = new TaskPrintD();
        Runnable task3 = new TaskPrintP();

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        Thread thread3 = new Thread(task3);

        thread1.start();
        thread2.start();
        thread3.start();

        // Let them run for 500ms
        try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        // put up the stop sign
        runFlag=false;
        
        thread3.interrupt();
        thread2.interrupt();
        thread1.interrupt();

    }
    
    public static class TaskPrintC implements Runnable 
    {
        public void run(){
    	    while (runFlag) {
    	    	try {
    	    		canPrintC.acquire(1);
    	    	} catch (InterruptedException e) {	}
    	    	System.out.printf( "%s", ":");
    	        try {
    	        	canPrintD.release(3); // print '---'
    	        	donePrintD.acquire(3); // wait for that to finish
    	        	canPrintP.release(5); // print ')))))'
    	        	donePrintP.acquire(5); // wait for that to finish
    	        } catch (InterruptedException e) { }
    	    	canPrintC.release(1); // repeat
    	    }
        }
    }
    
    public static class TaskPrintD implements Runnable 
    {
        public void run(){
        	while (runFlag) {
        		try {
        			canPrintD.acquire(1);
        		} catch (InterruptedException e) { }
    	        System.out.printf( "%s", "-");
    	        donePrintD.release(1);
    	    }
        }
    }
    
    public static class TaskPrintP implements Runnable 
    {
        public void run(){
    	    while (runFlag) {
    	    	try {
    	    		canPrintP.acquire(1);
    	    	} catch (InterruptedException e) { }
    	        System.out.printf( "%s", ")");
    	        donePrintP.release(1);
    	    }
        }
    }

}
