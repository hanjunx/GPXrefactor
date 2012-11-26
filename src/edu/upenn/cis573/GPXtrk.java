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

    public GPXtrk(String name, ArrayList<GPXtrkseg> trksegs) {
		this.name = name;
		this.trksegs = trksegs;
    }

    public String name() { return name; }

    /**
     * Get the track segment for the given index.
     *
     * @param index The index of the segment to be retrieved.
     * @return The track segment at the provided index. Return null if the index is too large (i.e., is larger than the number of segments)
     */
    public GPXtrkseg trkseg(int index) {
		if (index >= trksegs.size()) return null;
		else return trksegs.get(index);
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
    public ArrayList<GPXtrkseg> trksegs() {
		return trksegs;
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
	
		return GPXtrkpt.bearing(start, end);
	
	}

	/**
	 * Calculates the elapsed time for all segments in the track.
	 * Note that it does not include the time <b>between</b> segments.
	 *
	 * @return the elapsed time in seconds; -1 if the track object is null
	 */
	public long calculateElapsedTime() {
	
		long time = 0;
	
		for (GPXtrkseg trkseg : trksegs) {
		    // keep a running total of the time for each segment
		    time += trkseg.calculateElapsedTime();
		}
		
		return time;
	}

	/**
	 * Calculate the average speed over the entire track by determining
	 * the distance traveled and the total time for each segment.
	 *
	 * @return the average speed in meters per second.
	 */
	public double calculateAverageSpeed() {
		// return the average in meters/second
		return calculateDistanceTraveled() / calculateElapsedTime();
	
	}

	/**
	 * Calculates the distance traveled over all segments in the specified
	 * track by returning the sum of the distances for each track segment.
	 *
	 * @return the total distance in meters
	 */
	public double calculateDistanceTraveled() {
	
		double totalDistance = 0;
	
		// iterate over all the trksegs
	
		for (GPXtrkseg trkseg : trksegs) {
		    // calculate the distance for each segment
		    // add it to the running total
	
			totalDistance += trkseg.calculateDistanceTraveled();
		}
	
		return totalDistance;
	
	
	}

	/**
	 * Determines which track segment has the fastest average speed.
	 *
	 * @param trk The track for which to calculate the fastest segment.
	 * @return the 0-based index of the fastest segment.
	 */
	public int calculateFastestSegment() {
	
		int fastestSegment = 0;
		double fastestSpeed = 0;
		
		int i = 0;
		for (GPXtrkseg trkseg : trksegs) {
		    if (trkseg.calculateDistanceTraveled()/trkseg.calculateElapsedTime() >= fastestSpeed)
			fastestSegment = i++;
		}
	
		return fastestSegment;
	
	}


}