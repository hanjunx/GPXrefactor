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
     *
     * @param trkseg The track segment for which to calculate the elapsed time.
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


}