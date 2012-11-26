package edu.upenn.cis573;

import java.util.ArrayList;

/**
 * Represents all the data in a GPX file.
 */

public class GPXobject {

	// name of this object
	private String name;
	// time at which it was created
	private String time;
    // holds all the information about the track
    private GPXtrk trk;
    // list of all the track segments
    // private ArrayList<GPXtrkseg> trksegs;
    // string buffer used for printing
    private StringBuffer out;

    public GPXobject(String time, String name, ArrayList<GPXtrkseg> trksegs) {
    	this.time = time;
    	this.trk = new GPXtrk(name, trksegs);
    }

    /* Accessors */
    public GPXtrk trk() { return trk; }
    public String name() { return name; }
    public String time() { return time; }


    /**
     * This method writes out the XML for this GPX object.
     * It should primarily be used for debugging purposes.
     *
     * @return a well-formatted (according to the GPX file specification provided for this assignment) string representation of the object
     */
    public String toString() {

		out = new StringBuffer();
	
		// always start with <gpx>
		out.append("<gpx>\n");
		
		out.append("<time>"+time+"</time>\n");		
		
		out.append("\t<trk>\n");
	
		out.append("\t\t<name>" + trk.name() + "</name>\n");
	
	    // iterate over the trksegs
	    for (GPXtrkseg trkseg : trk.trksegs()) {
		
			out.append("\t\t<trkseg>\n");
			
			// iterate over the trkpts
			for (GPXtrkpt trkpt : trkseg.getTrkpts()) {
			    
			    out.append("\t\t\t<trkpt lat=\"" + trkpt.lat() + "\" lon=\"" + trkpt.lon() + "\">\n");
			    out.append("\t\t\t\t<ele>" + trkpt.ele() + "</ele>\n");
			    out.append("\t\t\t\t<time>" + trkpt.timeString() + "</time>\n");
			    out.append("\t\t\t</trkpt>\n");
	
			}
	
			out.append("\t\t</trkseg>\n");
	    }
	
		out.append("\t</trk>\n");
	
		out.append("</gpx>\n");
	
		return out.toString();

    }
    
}