package edu.upenn.cis573;

/**
 * Represents a track segment, which is just a collection of zero or more track points.
 */

import java.util.ArrayList;

public class GPXtrkseg {
    
    private ArrayList<GPXtrkpt> trkpts;

    public GPXtrkseg(ArrayList<GPXtrkpt> trkpts) {
    	this.trkpts = trkpts;
    }

    /**
     * Get the track point for the given index.
     *
     * @param index The index of the point to be retrieved.
     * @return The track point at the provided index. Return null if the index is too large (i.e., is larger than the number of points)
     */
    public GPXtrkpt getTrkpt(int index) {
		if (index >= trkpts.size()) return null;
		else return trkpts.get(index);
    }

    /**
     * @return the number of track points in this segment
     */
    public int numPoints() {
    	return trkpts.size();
    }

    /**
     * @return an array of track point objects
     */
    public ArrayList<GPXtrkpt> getTrkpts() {
		return trkpts;
    }
    
    /**
     * Calculates the elapsed time for the given segment by returning
     * the difference between the first and last track points.
	 * The distance takes into account latitude, longitude, elevation, and curvature of the earth.
	 * To account for the curvature of the earth, the spherical law of cosines
	 * is used, based on http://www.movable-type.co.uk/scripts/latlong.html
     * 
     * @return the elapsed time in seconds; -1 if the track segment object is null
     */

    public long calculateElapsedTime() {
		// get the time of the first point of the segment
		GPXtrkpt firstPt = getTrkpt(0);
		long start = firstPt.time();
		
		// get the time of the last point of the segment
		GPXtrkpt lastPt = getTrkpt(numPoints() - 1);
		long end = lastPt.time();
		
		// total elapsed time in milliseconds
		return end - start;

    }

	/**
	 * Calculates the distance traveled over the given segment by returning
	 * the sum of the distances between successive track points.
	 * The distance takes into account latitude, longitude, elevation, and curvature of the earth.
	 * To account for the curvature of the earth, the spherical law of cosines
	 * is used, based on http://www.movable-type.co.uk/scripts/latlong.html
	 *
	 * @return the total distance in meters
	 */
	public double calculateDistanceTraveled() {
	
		double totalDistance = 0;
		
		// iterate over all the trkpts
	
		for (int j = 0; j < trkpts.size()-1; j++) {
		    
		    // get this point and the next one
		    GPXtrkpt pt1 = trkpts.get(j);
		    GPXtrkpt pt2 = trkpts.get(j + 1);
		    
		    // add it to the running total
		    totalDistance += GPXtrkpt.calculateDistance(pt1, pt2);
	    
		}
		
		return totalDistance;
	
	}


}