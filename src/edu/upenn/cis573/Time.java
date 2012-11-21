package edu.upenn.cis573;
 

public class Time {
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	//this filed is to track the incoming raw string time data, to provide full function
	//for the GPtrkpt
	private String rawTime;
	
	public Time(String time) {
		//take in the time of the string format, parse it in to the correct format
			rawTime = time;
			try{
		 		year = Integer.parseInt(time.substring(0, 4));
			    month = Integer.parseInt(time.substring(5, 7));
			    day = Integer.parseInt(time.substring(8, 10));
			    hour = Integer.parseInt(time.substring(11, 13));
			    minute = Integer.parseInt(time.substring(14, 16));
			    second = Integer.parseInt(time.substring(17, 19));
		 	}catch(Exception ex){
		 	// presumably a NumberFormatException
		 		System.out.println("The time format is not valid");
		 	}
			
	}
	
	public long time(){
	
	    // make sure the values are valid
	    if (year < 1970 || month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) return -1;

	    // if we made it here, we're okay
		long time = 0;
		
		// first, take care of the years
		time = ((year - 1970) * (60 * 60 * 24 * 365));
		
		// now, those pesky leap years... for each one, we have to add an extra day
		for (int i = 1970; i < year; i++) {
		    // keep in mind that 2000 was a leap year but 2100, 2200, etc. are not!
		    if ((i % 4 == 0 && i % 100 != 0) || (i % 400 == 0)) {
		    	time += (60 * 60 * 24);
		    }
		}
	
		// then, months
		int daysPerMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } ;
		for (int i = 0; i < month-1; i++) {
		    time += (daysPerMonth[i] * (60 * 60 * 24));
		}
	
		// then, days
		time += ((day) * 60 * 60 * 24);
	
		// then, hours
		time += (hour * 60 * 60);
		
		// MAGIC FOUR-HOUR FUDGE FACTOR TO ACCOUNT FOR TIME ZONE DIFFERENCE
		time += 4 * 60 * 60;
	
		// then, minutes
		time += (minute * 60);
	
		// last, seconds
		time += second;
	
		// done!
		return time * (long)1000;
	}
	
	public String toString(){
		return rawTime;
	}

}
