
public class Time implements Comparable{

	int hour;
	int min;
	
	public Time(int hour, int min) {
		this.hour=hour;
		this.min=min;
	}
	
	public Time (String hhmm) throws IllegalArgumentException { // convert string in the form hh:mm
		int endofHour=hhmm.indexOf(":");
		if (endofHour==-1) throw new IllegalArgumentException("Invalid Time");
		this.hour=Integer.parseInt(hhmm.substring(0, endofHour));
		this.min=Integer.parseInt(hhmm.substring(hhmm.length()-2,hhmm.length()));
		
	}
	
	public int getHour() { return hour;}
	public int getMin() {return min;}
	
	public String toString() {
		String s;
		if (min<10) s=":0"+min;
		else s=":"+min;
		if (hour<10) s=" "+hour+s;
		else s=hour+s;
		return s;
	}

	@Override
	public int compareTo(Object o) {
		Time time = (Time) o;
		int compare = this.hour-time.hour;
		if (compare==0)
			compare = this.min-time.min;
		return compare;
	}
	
	
	
}
