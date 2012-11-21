package edu.upenn.cis573;

/**
 * Contains a static method for checking whether a file matches the 
 * GPX format specification.
 *
 * Note that, for purposes of simplicity, this does not perform the check
 * using the REAL specification, but rather uses the one provided for this
 * particular assignment!
 */

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class GPXchecker {

    /*
     * Checks the format of the GPX file specified by the filename.
     * See the documentation for the expected file format.
     *
     * @param filename The GPX file to be checked.
     * @return a GPXformat object that indicates whether this file is valid; if so, it contains an ArrayList of all the tags that have been encountered (to make parsing easier); if not, it contains an error message.
     */
  // hold the GPX format of the file
	private static GPXformat format; 
  
   //hold all the sequence we find, insequence
   private static ArrayList<String> tags;
   
   private static boolean readFile(String filename){
	   format = new GPXformat();
	   tags = new ArrayList<String>();
	   try {
		    Scanner in = new Scanner(new File(filename));
		    in.useDelimiter(">");
		    
		    // loop through each line and find all the tags, then add them to the list
		    while (in.hasNext()) {
			// this is a line ending with ">" (which is ignored)
			String tagLine = in.next().trim();
			// this is the tag we're looking for
			String tag = null;
	
			if (tagLine.startsWith("<")) {
			    // if it starts with a "<", and has a blank space in the line, then there's more stuff but we'll just ignore it
			    if (tagLine.contains(" ")) {
				tag = tagLine.substring(0, tagLine.indexOf(' '));
			    }
			    // otherwise we want the whole line
			    else tag = tagLine;
			}
			else {
			    // if it doesn't start with a "<", we can ignore whatever comes before it
			    tag = tagLine.substring(tagLine.indexOf("<"));
			}
			//System.out.println(tag);
			tags.add(tag);
		    }
		}catch(Exception ex){
			format.setValid(false);
			format.setMessage(ex.getMessage());
			return false;
		}
		return true;	    
	}
	
   public static void checkElement(String tag, String target) throws Exception{
	   if(tag.equals(target) == false) throw new Exception("Format error! Expected "+target + "> tag");
   }
   
	public static GPXformat checkFormat(String filename) {
		
		//scan in the file, if true, continue, else return erro message
		if(readFile(filename)){
			try {
				// now go through and see what we've got in the list
		    
				// index into the list
				int index = 0;
	
				// the first tag must be <gpx>
				checkElement(tags.get(index++), "<gpx");
	
				// then <time>
				checkElement(tags.get(index++), "<time");
				
				// then </time>
				checkElement(tags.get(index++), "</time");
				
				// now we should have <trk>
				checkElement(tags.get(index++), "<trk");
	
				// the <name> tag is optional
				if (tags.get(index).equals("<name")) {
					index++;
					// now we should have </name>
					//if (tags.get(index++).equals("</name") == false) throw new Exception("Format error! Expected </name> tag");
					checkElement(tags.get(index++), "</name");
				}
	
				// now we have some number of <trkseg>
				String tag = (String)(tags.get(index++));
				while (tag.equals("<trkseg")) {
					// now we have some number of <trkpt>
					tag = (String)(tags.get(index++));
					while (tag.equals("<trkpt")) {
	
						// now we should have <ele>
						//if (tags.get(index++).equals("<ele") == false) throw new Exception("Format error! Expected <ele> tag");
						checkElement(tags.get(index++), "<ele");
			    // now we should have </ele>
					//	if (tags.get(index++).equals("</ele") == false) throw new Exception("Format error! Expected </ele> tag");
						checkElement(tags.get(index++), "</ele");
			    // now we should have <time>
					//	if (tags.get(index++).equals("<time") == false) throw new Exception("Format error! Expected <time> tag");
						checkElement(tags.get(index++), "<time");
			    // now we should have </time>
					//	if (tags.get(index++).equals("</time") == false) throw new Exception("Format error! Expected </time> tag");
						checkElement(tags.get(index++), "</time");
			    // now we should have </trkpt>
					//	if (tags.get(index++).equals("</trkpt") == false) throw new Exception("Format error! Expected </trkpt> tag");
						checkElement(tags.get(index++),"</trkpt");
			    // consume the next tag
						tag = (String)(tags.get(index++));
					}
			
			// now we should have </trkseg>
				//	if (tag.equals("</trkseg") == false) throw new Exception("Format error! Expected </trkseg> tag");
					checkElement(tag, "</trkseg");
			// consume the next tag
					tag = (String)(tags.get(index++));
		    }
	
		    // if we get here, then we should be on </trk>
				//if (tag.equals("</trk") == false) throw new Exception("Format error! Expected </trk> tag");
				checkElement(tag, "</trk");
		    // last but not least: </gpx>
			//	if (tags.get(index++).equals("</gpx") == false) throw new Exception("Format error! Expected </gpx> tag");
				checkElement(tags.get(index++), "</gpx");
		    // if we made it here, everything is okay
				format.setValid(true);
				format.setMessage("Format is valid.");
				format.setTags(tags);
	
			}
			catch (Exception e) {
				//e.printStackTrace();
				// indicate that this is not a valid file
				format.setValid(false);
				format.setMessage(e.getMessage());
			}
		}
	
		return format;
    }
}