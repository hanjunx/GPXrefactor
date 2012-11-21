package edu.upenn.cis573;

/**
 * Represents a track point in a GPX file.
 */

public class GPXtrkpt {

    // radius of the earth in km
	public static final int R = 6371; 
    // latitude
    private double lat;
    // longitude
    private double lon;
    // elevation
    private double ele;
    // time is assumed to be in the following format: YYYY-MM-DDThh:mm:ssZ
    private Time time;

    public GPXtrkpt(double lat, double lon, double ele, String time) {
		this.lat = lat;
		this.lon = lon;
		this.ele = ele;
		this.time = new Time(time);
    }

    /* Accessors */
    public double lat() { return lat; }
    public double lon() { return lon; }
    public double ele() { return ele; }
    public String timeString() { return time.toString(); }
    
    public long time() {
		
    	return time.time();

    }
    

}
