package edu.upenn.cis573;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GPXobjectTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testToString() {
		GPXtrkpt pt0 = new GPXtrkpt(0, 0, 0, "2012-10-29T15:03:00Z");
		GPXtrkpt pt1 = new GPXtrkpt(10, 10, 10, "2012-10-30T15:03:00Z");
		GPXtrkpt pt2 = new GPXtrkpt(20, 20, 20, "2012-10-31T15:03:00Z");
		GPXtrkpt pt3 = new GPXtrkpt(30, 30, 30, "2012-11-01T15:03:00Z");
		
		ArrayList<GPXtrkpt> pts0 = new ArrayList<GPXtrkpt>();
		pts0.add(pt0);
		pts0.add(pt1);
		
		ArrayList<GPXtrkpt> pts1 = new ArrayList<GPXtrkpt>();
		pts1.add(pt2);
		pts1.add(pt3);
		
		GPXtrkseg seg0 = new GPXtrkseg(pts0);
		GPXtrkseg seg1 = new GPXtrkseg(pts1);
		
		ArrayList<GPXtrkseg> segs = new ArrayList<GPXtrkseg>();
		segs.add(seg0);
		segs.add(seg1);
		
		GPXobject obj = new GPXobject("2012-11-05T15:03:00Z", "Test track", segs);
		
		String value = "<gpx>\n<time>2012-11-05T15:03:00Z</time>\n\t<trk>\n\t\t<name>Test track</name>\n\t\t<trkseg>\n\t\t\t<trkpt lat=\"0.0\" lon=\"0.0\">\n\t\t\t\t<ele>0.0</ele>\n\t\t\t\t<time>2012-10-29T15:03:00Z</time>\n\t\t\t</trkpt>\n\t\t\t<trkpt lat=\"10.0\" lon=\"10.0\">\n\t\t\t\t<ele>10.0</ele>\n\t\t\t\t<time>2012-10-30T15:03:00Z</time>\n\t\t\t</trkpt>\n\t\t</trkseg>\n\t\t<trkseg>\n\t\t\t<trkpt lat=\"20.0\" lon=\"20.0\">\n\t\t\t\t<ele>20.0</ele>\n\t\t\t\t<time>2012-10-31T15:03:00Z</time>\n\t\t\t</trkpt>\n\t\t\t<trkpt lat=\"30.0\" lon=\"30.0\">\n\t\t\t\t<ele>30.0</ele>\n\t\t\t\t<time>2012-11-01T15:03:00Z</time>\n\t\t\t</trkpt>\n\t\t</trkseg>\n\t</trk>\n</gpx>\n";

		assertEquals(value, obj.toString());
	}
	

}
