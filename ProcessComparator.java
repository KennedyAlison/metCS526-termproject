import java.util.Comparator;

public class ProcessComparator implements Comparator<Process>{

	@Override
	/**
	 * This method overrides the compare method for a Process Comparator to compare the two Process object by their priority 
	 * @param o1 is the first Process object input for comparison 
	 * @param o2 is the second Process object input for comparison
	 * @return -1, 0, or 1 integers based on whether process o1 has lower priority than process o2, equal priority, or greater priority
	 */
	public int compare(Process o1, Process o2) {
		return Integer.valueOf(o1.getPr()).compareTo(Integer.valueOf(o2.getPr()));
	}
	
}