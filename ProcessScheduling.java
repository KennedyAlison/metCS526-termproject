
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ProcessScheduling {
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		
		// Read all processes from an input file and store them in an appropriate data structure, D
		
		// Get path to current user directory 
		String dir = System.getProperty("user.dir");
		
		// Replace with any specific src/package location on your environment
		String packageLoc = "/src";
		
		// Create new Scanner to read in file contents 
		Scanner fileInput = new Scanner (new File(dir + packageLoc + "/process_scheduling_input.txt"));
		
		// Create an Array List of process objects 
		ArrayList<Process> processes = new ArrayList<>();
		
		// Create a comparator of Process objects 
		Comparator<Process> comparator = new ProcessComparator();
		
		// While the file has more content, read subsequent lines, create Process objects, and add them to processes Array List 
		while (fileInput.hasNextLine()){

			// Get the next line, split it by a comma and put the content into an array 
			String[] splitLine = fileInput.nextLine().split(" ");
			
			int id =  Integer.parseInt(splitLine[0]);
			int pr = Integer.parseInt(splitLine[1]);
			int arrivalTime = Integer.parseInt(splitLine[2]);
			int duration = Integer.parseInt(splitLine[3]);
			
			
			processes.add(new Process(id, pr, arrivalTime, duration));
		}
		
		// Close out the input file 
		fileInput.close();
		
		// Create an output file to write output to 
		File outputFile = new File(dir + packageLoc + "/process_scheduling_output.txt");
		PrintWriter fileOutput = new PrintWriter(outputFile);
		
		// Print and write all process objects from the input file
		System.out.println("\nAll processes:");
		fileOutput.println("\nAll processes:");
		for (Process process:processes) {
			System.out.println("Id = " + process.getId() + ", priority = " + process.getPr() + ", duration = " + process.getDuration() + ", arrival time = " + process.getArrivalTime());
		    fileOutput.println("Id = " + process.getId() + ", priority = " + process.getPr() + ", duration = " + process.getDuration() + ", arrival time = " + process.getArrivalTime());
		}
		
		// Initialize currentTime
		int currentTime = 0;
		
		// Initialize running to false to indicate no process is running yet 
		boolean running = false;
		
		// Initialize endTime for the duration a process runs compared to the currentTime and ensure initial value is higher than current time 
		int endTime = 10000000;
		
		// Create an empty priority queue Q with Process comparator 
		PriorityQueue<Process> Q = new PriorityQueue<Process>(comparator);
		
		// Initialize variable to track total wait time  
		double totalWaitTime = 0;
		
		// Track the total number of processes 
		int totalProcesses = processes.size();
		
		// Create variable to hold maximum wait time 
		int MAX_WAIT_TIME = 30;
		System.out.println("\nMaximum wait time = " + MAX_WAIT_TIME);
		fileOutput.println("\nMaximum wait time = " + MAX_WAIT_TIME);
		
		// Initialize variable track first element removed from the queue 
		Process removed = new Process(0, 0, 0, 0);
		
		// Each iteration of the while loop represents what occurs during one time unit
		// Must increment currentTime in each iteration, while loop runs once for every time unit until processes is empty
		// While processes ArrayList is not empty
		while(!processes.isEmpty()) {
			
			// Get (don’t remove) a process p from ArrayList processes that has the earliest arrival time
			Process p = Collections.min(processes);
			
			// If the arrival time of p <= currentTime, remove p from ArrayList of processes and insert it into Q
			if(p.getArrivalTime() <= currentTime) {
				Q.add(p);
				processes.remove(p);
				if(processes.isEmpty()) {
					System.out.println("\nD becomes empty at time " + currentTime + "\n");
					fileOutput.println("\nD becomes empty at time " + currentTime + "\n");
				}
			}
			// If Q is not empty and the flag running is false
			if(!Q.isEmpty() && !running) {
				// Remove a process with the smallest priority from Q
				removed = Q.remove();
				// Set end time equal to when this process will be done running (i.e. the currentTime + the duration of this process
				endTime = currentTime + removed.getDuration();
				
				// Calculate the wait time of the process
				int waitTime = currentTime - removed.getArrivalTime();
				// Add wait time to total wait time 
				totalWaitTime += waitTime;
				
				//Set a flag running to true
				running = true;
				
				// Print and write details of process that was removed to the output file 
				System.out.println("\nProcess removed from queue is: id = " + removed.getId() + ", at time " + currentTime + ", wait time = " + waitTime + " Total wait time = " + totalWaitTime);
				System.out.println(removed.toString());
				fileOutput.println("\nProcess removed from queue is: id = " + removed.getId() + ", at time " + currentTime + ", wait time = " + waitTime + " Total wait time = " + totalWaitTime);
				fileOutput.println(removed.toString());
			}
			
			// Increment the current time after checking if running is false, then check if we've reached the end time of the process and set running to false if so 
			currentTime++;
			
			// If currently running process has finished
			if(currentTime == endTime) {
				// Write the finished time of the process to the output file 
				System.out.println("Process " + removed.getId() + " finished at time " + currentTime);
				fileOutput.println("Process " + removed.getId() + " finished at time " + currentTime);
				
				// Set a flag running to false
				running = false;
				
				// Update priorities of processes that have been waiting longer than max wait time
				System.out.println("\nUpdate Priority: ");
				fileOutput.println("\nUpdate Priority: ");
				for(Process pq: Q) {
					if((currentTime - pq.getArrivalTime()) > MAX_WAIT_TIME) {
						int currPr = pq.getPr();
						System.out.println("PID = " + pq.getId() + ", wait time = " + (currentTime - pq.getArrivalTime()) + ", current priority = " + currPr);
						fileOutput.println("PID = " + pq.getId() + ", wait time = " + (currentTime - pq.getArrivalTime()) + ", current priority = " + currPr);
						pq.setPr(currPr - 1);
						System.out.println("PID = " + pq.getId() + ", new priority = " + pq.getPr());
						fileOutput.println("PID = " + pq.getId() + ", new priority = " + pq.getPr());
					}
				}
			}
		}

		// At this time all processes in D have been moved to Q.
		// Execute all processes that are still in Q, one at a time.
		// While the currentTime is less than or equal to the endTime of each process running 
		// When the Q is empty, the currentTime with be greater than the endTime after the last process finishes
		while(currentTime <= endTime) {
			
			// If a process is not currently being executed and the Q is not empty
			if(!Q.isEmpty() && !running) {
				// Remove a process with the smallest priority from Q
				removed = Q.remove();
				
				// Calculate the time that this process will finish running
				endTime = currentTime + removed.getDuration();
				
				// Calculate the wait time of the process
				int waitTime = currentTime - removed.getArrivalTime();
				totalWaitTime += waitTime;
				
				//Set a flag running to true
				running = true;
				
				// Print and write details of process that was removed to the output file 
				System.out.println("\nProcess removed from queue is: id = " + removed.getId() + ", at time " + currentTime + " wait time = " + waitTime + " Total wait time = " + totalWaitTime);
				System.out.println(removed.toString());
				fileOutput.println("\nProcess removed from queue is: id = " + removed.getId() + ", at time " + currentTime + " wait time = " + waitTime + " Total wait time = " + totalWaitTime);
				fileOutput.println(removed.toString());
			}
			
			// Increment the current time after checking if running is false, then check if we've reached the end time of the process and set running to false if so 
			currentTime++;
			
			// If currently running process has finished
			if(currentTime == endTime) {
				// Write the finished time of the process to the output file 
				System.out.println("Process " + removed.getId() + " finished at time " + currentTime);
				fileOutput.println("Process " + removed.getId() + " finished at time " + currentTime);
				
				// Set a flag running to false
				running = false;
				
				// Update priorities of processes that have been waiting longer than max. wait time
				System.out.println("\nUpdate Priority: ");
				fileOutput.println("\nUpdate Priority: ");
				
				for(Process pq: Q) {
					if((currentTime - pq.getArrivalTime()) > MAX_WAIT_TIME) {
						int currPr = pq.getPr();
						System.out.println("PID = " + pq.getId() + ", wait time = " + (currentTime - pq.getArrivalTime()) + ", current priority = " + currPr);
						fileOutput.println("PID = " + pq.getId() + ", wait time = " + (currentTime - pq.getArrivalTime()) + ", current priority = " + currPr);
						pq.setPr(currPr - 1);
						System.out.println("PID = " + pq.getId() + ", new priority = " + pq.getPr());
						fileOutput.println("PID = " + pq.getId() + ", new priority = " + pq.getPr());
					}
				}
			}
		}
		
		// Print and write wait time statistics to the output file 
		System.out.println("\nTotal wait time = " + totalWaitTime);
		System.out.println("Average wait time = " + (totalWaitTime / totalProcesses));
		fileOutput.println("\nTotal wait time = " + totalWaitTime);
		fileOutput.println("Average wait time = " + (totalWaitTime / totalProcesses));
		
		// Close the output file stream
		fileOutput.close();	
	}
}

