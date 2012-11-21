package edu.upenn.cis573;

/**
 * This class contains static methods used for performing statistics and
 * other calculations on GPX data.
*/


public class GPXcalculator {

    public static final int R = 6371; // radius of the earth in km
    
    /**
     * Calculates the distance traveled over the given segment by returning
     * the sum of the distances between successive track points.
     * The distance takes into account latitude, longitude, elevation, and curvature of the earth.
     * To account for the curvature of the earth, the spherical law of cosines
     * is used, based on http://www.movable-type.co.uk/scripts/latlong.html
     *
     * @param trkseg The track segment for which to calculate the distance traveled.
     * @return the total distance in meters
     */
    public double calculateDistanceTraveled(GPXtrkseg trkseg) {

		double totalDistance = 0;
		
		// iterate over all the trkpts
		GPXtrkpt pts[] = trkseg.getTrkpts();
	
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
		    double d = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1)) * R;	
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


    /**
     * Calculates the distance traveled over all segments in the specified
     * track by returning the sum of the distances for each track segment.
     *
     * @param trk The track for which to calculate the distance traveled.
     * @return the total distance in meters
     */
    public double calculateDistanceTraveled(GPXtrk trk) {
	
		double totalDistance = 0;
	
		// iterate over all the trksegs
		GPXtrkseg segs[] = trk.trksegs();
	
		for (int i = 0; i < segs.length; i++) {
		    // calculate the distance for each segment
		    // add it to the running total

			totalDistance += calculateDistanceTraveled(segs[i]);
		}
	
		return totalDistance;
	

    }

    /**
     * Calculate the average speed over the entire track by determining
     * the distance traveled and the total time for each segment.
     *
     * @param trk The track for which to calculate the average speed
     * @return the average speed in meters per second.
     */
    public double calculateAverageSpeed(GPXtrk trk) {

		long time = 0;

		// iterate over all the segments and calculate the time for each
		GPXtrkseg trksegs[] = trk.trksegs();
	
		for (int i = 0; i < trksegs.length; i++) {
		    // keep a running total of the time for each segment
		    time += trksegs[i].calculateElapsedTime();
		}		
		
		// figure out the distance in kilometers
		double distance = calculateDistanceTraveled(trk);
	
		// return the average in meters/second
		return distance/time;

    }


    /**
     * Determines which track segment has the fastest average speed.
     *
     * @param trk The track for which to calculate the fastest segment.
     * @return the 0-based index of the fastest segment.
     */
    public int calculateFastestSegment(GPXtrk trk) {

		GPXtrkseg trksegs[] = trk.trksegs();
	
		int fastestSegment = 0;
		double fastestTime = 0;
	
		for (int i = 0; i < trksegs.length; i++) {
		    if (calculateDistanceTraveled(trksegs[i])/trksegs[i].calculateElapsedTime() >= fastestTime)
			fastestSegment = i;
		}
	
		return fastestSegment;

    }

}