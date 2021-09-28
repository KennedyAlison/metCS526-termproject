public class Process implements Comparable<Process>{
 
   private int pr;
   private int id;
   private int arrivalTime;
   private int duration;

   public Process(int id, int pr, int duration, int arrivalTime) {
	   this.id = id;
	   this.pr = pr;
	   this.duration = duration;
	   this.arrivalTime = arrivalTime;
   }
   
   /**
    * @return the process id
    */
   public int getId() { return id; }
   
   /**
    * @return the process priority
    */
   public int getPr() { return pr; } 
   
   /**
    * @return the process duration
    */
   public int getDuration() { return duration; }
   
   /**
    * @return the process arrival time
    */
   public int getArrivalTime() { return arrivalTime; }
 
   /**
    * @param id to set the id of a process
    */
   public void setId(int id){
	   this.id = id;
   }
   
   /**
    * @param pr to set the priority of a process
    */
   public void setPr(int pr) {        
	   this.pr = pr ; 
   }             

   /**
    * @param duration to set the duration of a process
    */
   public void setDuration(int duration){
	   this.duration = duration;
   }
   
   /**
    * @param arrivalTime to set the arrival time of a process
    */
   public void setArrivalTime(int arrivalTime){
	   this.arrivalTime = arrivalTime;
   }
   
   
   /**
    * @return a specified format string representation of each process object 
    */
   public String toString() {
	   String p =
				"Process id = " + id +
	            "\n\tPriority = " + pr +
	            "\n\tArrival = " + arrivalTime +
	   			"\n\tDuration = " + duration;
	   return p;
   }

	@Override
	/**
	 * Override the compareTo method of a process object to compare Process objects by arrival time
	 * @param o a Process object to compare to another Process object
	 * @return -1, 0, or 1 integers based on whether process o has a lower arrivalTime, equal arrivalTime, or greater arrivalTime
	 */
	public int compareTo(Process o) {
		return Integer.valueOf(this.getArrivalTime()).compareTo(Integer.valueOf(o.getArrivalTime()));
	}

 }
