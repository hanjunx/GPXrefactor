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
    public double rlat() { return lat * 2 * Math.PI / 360.0; }
    public double rlon() { return lon * 2 * Math.PI / 360.0; }
    public double ele() { return ele; }
    public String timeString() { return time.toString(); }
    
    public long time() {
		
    	return time.time();

    }
    
	/**
	 * Calculates the distance traveled over the given two points.
	 * The distance takes into account latitude, longitude, elevation, and curvature of the earth.
	 * To account for the curvature of the earth, the spherical law of cosines
	 * is used, based on http://www.movable-type.co.uk/scripts/latlong.html
	 *
	 * @return the total distance in meters
	 */
    public static double calculateDistance(GPXtrkpt pt1, GPXtrkpt pt2) {
	    
	    // convert lat and lon from degrees to radians
	    double lat1 = pt1.rlat();
	    double lon1 = pt1.rlon();
	    double lat2 = pt2.rlat();
	    double lon2 = pt2.rlon();
	    
	    // use the spherical law of cosines to figure out 2D distance
	    double d = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1)) * R;
	    // now we need to take the change in elevation into account
	    double ele1 = pt1.ele();
	    double ele2 = pt2.ele();
	    
	    // calculate the 3D distance
	    return Math.sqrt(d*d + (ele1-ele2)*(ele1-ele2));
    }

    
	/**
	 * Calculate the bearing (direction) from the start to the end,
	 * using the bearing calculation
	 * from http://www.movable-type.co.uk/scripts/latlong.html
	 *
	 * @return the bearing in degrees
	 */
    public static double bearing(GPXtrkpt start, GPXtrkpt end) {
	    // convert lat and lon from degrees to radians
	    double a = start.rlat();
	    double b = start.rlon();
	    double c = end.rlat();
	    double d = end.rlon();

	    double y = Math.sin(d-b) * Math.cos(c);
		double x = Math.cos(a)*Math.sin(c) - Math.sin(a)*Math.cos(c)*Math.cos(d-b);
				
		// return the bearing (after converting to degrees)
		return Math.atan2(y, x) * 360.0 / (2 * Math.PI);
	}
    

}
