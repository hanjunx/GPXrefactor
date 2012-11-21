package edu.upenn.cis573;

/**
 * Represents a GPX track, which includes a name and some number
 * of GPX track segments.
 */

import java.util.ArrayList;

public class GPXtrk {
    
    // the name for this track
    private String name;
    // a list of track segments
    private ArrayList<GPXtrkseg> trksegs;
    // reference to parent GPXobject
    private GPXobject parent;

    public GPXtrk(String name, ArrayList<GPXtrkseg> trksegs, GPXobject parent) {
		this.name = name;
		this.trksegs = trksegs;
		this.parent = parent;
    }

    public String name() { return name; }
    public GPXobject parent() { return parent; }

    /**
     * Get the track segment for the given index.
     *
     * @param index The index of the segment to be retrieved.
     * @return The track segment at the provided index. Return null if the index is too large (i.e., is larger than the number of segments)
     */
    public GPXtrkseg trkseg(int index) {
		if (index >= trksegs.size()) return null;
		else return (GPXtrkseg)(trksegs.get(index));
    }

    /**
     * @return the number of segments
     */
    public int numSegments() {
    	return trksegs.size();
    }
    
    /**
     * @return an array containing all of the track segments.
     */
    public GPXtrkseg[] trksegs() {
		GPXtrkseg segs[] = new GPXtrkseg[trksegs.size()];
		for (int i = 0; i < segs.length; i++) segs[i] = (GPXtrkseg)trksegs.get(i);
		return segs;
    }
    
    public double bearing(double a, double b, double c, double d) {
		double y = Math.sin(d-b) * Math.cos(c);
		double x = Math.cos(a)*Math.sin(c) - Math.sin(a)*Math.cos(c)*Math.cos(d-b);
				
		// return the bearing (after converting to degrees)
		return Math.atan2(y, x) * 360.0 / (2 * Math.PI);

    }

	/**
	 * Calculate the bearing (direction) from the first point in the
	 * track to the last point in the track, using the bearing calculation
	 * from http://www.movable-type.co.uk/scripts/latlong.html
	 *
	 * @return the bearing in degrees
	 */
	public double bearing() {
	
		// get the first trkpt in the first trkseg
		GPXtrkpt start = trkseg(0).getTrkpt(0);
		// get the last trkpt in the last trkseg
		GPXtrkseg lastSeg = trkseg(numSegments()-1);
		GPXtrkpt end = lastSeg.getTrkpt(lastSeg.numPoints()-1);
	
		// get the points and convert to radians
		double lat1 = start.lat() * 2 * Math.PI / 360.0;
		double lon1 = start.lon() * 2 * Math.PI / 360.0;
		double lat2 = end.lat() * 2 * Math.PI / 360.0;
		double lon2 = end.lon() * 2 * Math.PI / 360.0;
		
		return bearing(lat1, lon1, lat2, lon2);
	
	
	}

	/**
	 * Calculates the elapsed time for all segments in the track.
	 * Note that it does not include the time <b>between</b> segments.
	 *
	 * @return the elapsed time in seconds; -1 if the track object is null
	 */
	long calculateElapsedTime() {
	
		if (this == null) return -1;
	
		long t = 0;
	
		// iterate over all the segments and calculate the time for each
		GPXtrkseg trksegs[] = trksegs();
	
		for (int i = 0; i < trksegs.length; i++) {
		    // keep a running total of the time for each segment
		    t += trksegs[i].calculateElapsedTime();
		}
		
		return t;
	}


}