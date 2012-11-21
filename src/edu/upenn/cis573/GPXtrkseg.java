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
		else return (GPXtrkpt)(trkpts.get(index));
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
    public GPXtrkpt[] getTrkpts() {
		GPXtrkpt pts[] = new GPXtrkpt[trkpts.size()];
		for (int i = 0; i < pts.length; i++) pts[i] = (GPXtrkpt)trkpts.get(i);
		return pts;
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
		GPXtrkpt pts[] = getTrkpts();
	
		for (int j = 0; j < pts.length-1; j++) {
		    
		    // get this point and the next one
		    GPXtrkpt pt1 = pts[j];
		    GPXtrkpt pt2 = pts[j+1];
		    
		    // convert lat and lon from degrees to radians
		    double lat1 = pt1.lat() * 2 * Math.PI / 360.0;
		    double lon1 = pt1.lon() * 2 * Math.PI / 360.0;
		    double lat2 = pt2.lat() * 2 * Math.PI / 360.0;
		    double lon2 = pt2.lon() * 2 * Math.PI / 360.0;
		    
		    // use the spherical law of cosines to figure out 2D distance
		    double d = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1)) * GPXtrkpt.R;	
		    // now we need to take the change in elevation into account
		    double ele1 = pt1.ele();
		    double ele2 = pt2.ele();
		    
		    // calculate the 3D distance
		    double distance = Math.sqrt(d*d + (ele1-ele2)*(ele1-ele2));
		    
		    // add it to the running total
		    totalDistance += distance;
	    
		}
		
		return totalDistance;
	
	}


}