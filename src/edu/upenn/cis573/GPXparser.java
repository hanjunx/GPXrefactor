package edu.upenn.cis573;


/**
 * Contains a static method to parse a well-formed GPX file and create
 * a GPXobject.
 */

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class GPXparser {

    /**
     * This method takes a file in GPX format and converts it into a GPXobject,
     * using the GPXformat to determine what needs to be read.
     * It assumes that the file has already been checked and that the format
     * is valid.
     *
     * @param filename The file to be read
     * @param format A GPXformat object that would be created as the result of calling GPXchecker.checkFormat
     * @returns a GPXobject that holds all the data in the file
     */
    //return object
	private static GPXobject object;
    
	//Scanner which uses to read the input
    private static Scanner in; 
  
    // index for reading the list
    private static int index;
    
	//initilize the filed variable
    private static void init(String filename){
    	object = null;
    	index = 0;
    	
    	try{
    		in = new Scanner(new File(filename));
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    }
    
    //method to increase the index and the Scanner in
    private static void increaseBy(int step){
    	for(int i = 0; i < step; i ++){
    		in.next();
    		index++;
    	}
    }
    
    //method to parse the element in the list
    private static String parseElement(){
    	String element = in.next();
    	index++;
    	element = element.substring(0, element.indexOf('<'));
    	return element;
    }
    
	public static GPXobject parse(String filename, GPXformat format) {
		// make sure the format is valid before proceeding
		
		if (format.isValid() == false) return null;
		
		//initilize the variable
		init(filename);
		
		// create a scanner to read the file and set its delimeter
		in.useDelimiter(">");
	
	    // get the list of tags
	    ArrayList<String> tags = format.tags();
	    

	    // read the <gpx> tag

	    // next is <time>
	    //increase two steps
	   increaseBy(2); 
	    
	    // now the content and </time>
	    String objtime = parseElement();
	    //System.out.println("time: " + objtime);
	
	    // now we're on <trk>
	   increaseBy(1);
	    
	    String name = null;
	    // next is <name> but it's optional
	    if (tags.get(index).equals("<name")) {
	    	
	    	increaseBy(1);
	    
	    	// then the content and </name>
	    	name = parseElement();
	    	//System.out.println("name: " + name);
	    }

    // to hold the GPXtrk objects
	    ArrayList<GPXtrkseg> trksegs = new ArrayList<GPXtrkseg>();


    // now we have some number of <trkseg> tags
	    while (tags.get(index++).equals("<trkseg")) {
			// consume the token
			in.next();
			
			// to hold the GPXtrkpt objects
			ArrayList<GPXtrkpt> trkpts = new ArrayList<GPXtrkpt>();
	
			// now we have some number of <trkpt> tags
			while (tags.get(index++).equals("<trkpt")) {
			    // get the latitude and longitude
			    String latlon = in.next().trim();
			    //System.out.println("LATLON: " + latlon);
	
			    // the latitude will be something like lat="xx.xxxx"
			    String lat = latlon.split(" ")[1];
			    lat = lat.substring(5, lat.length()-1);
			    //System.out.println("lat: " + lat);
	
			    // same for longitude
			    String lon = latlon.split(" ")[2];
			    lon = lon.substring(5, lon.length()-1);
			    //System.out.println("lon: " + lon);
			    
	
			    // read <ele>
			   increaseBy(1);
	
			    // read elevation and </ele>
			    String ele = parseElement();
			    index++;
	
			    // read <time>
			    increaseBy(1);
	
			    // read time and </time>
			    String time = parseElement();
			    //System.out.println("time: " + time);
			    index++;
	
			    // read </trkpt>
			    increaseBy(1);
	
			    // create a GPXtrkpt object
			    GPXtrkpt trkpt = new GPXtrkpt(Double.parseDouble(lat), Double.parseDouble(lon), Double.parseDouble(ele), time);
			    
			    // put it into the list
			    trkpts.add(trkpt);
			}
	
			// read </trkseg>
			in.next();
	
			// create a GPXtrkseg object
			GPXtrkseg trkseg = new GPXtrkseg(trkpts);
	
			// add it to the list
			trksegs.add(trkseg);
	
		}
	    
	    // don't care about </trk> and </gpx>
	     in.close();

	    // create the GPXobject
	    object = new GPXobject(objtime, name, trksegs);

	    return object;
    }

}