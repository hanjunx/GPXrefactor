package edu.upenn.cis573;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GPXparserTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testParseGood() {
		GPXformat format = GPXchecker.checkFormat("files/good.gpx");

		GPXobject obj = GPXparser.parse("files/good.gpx", format);
		
		assertEquals("2009-10-17T22:58:43Z", obj.time());
		
		GPXtrk trk = obj.trk();
		
//		assertEquals(obj, trk.parent());
		assertEquals("Walking around a little bit", trk.name());
		
		ArrayList<GPXtrkseg> trksegs = trk.trksegs();
		GPXtrkpt trkpt = trksegs.get(0).getTrkpt(0);

		assertEquals(47.644548, trkpt.lat(), 0.001);
		assertEquals(-122.326897, trkpt.lon(), 0.001);
		assertEquals(4.46, trkpt.ele(), 0.001);
		assertEquals("2009-10-17T18:37:26Z", trkpt.timeString());
	}


	@Test
	public void testParseMissingName() {
		GPXformat format = GPXchecker.checkFormat("files/missingName.gpx");

		GPXobject obj = GPXparser.parse("files/missingName.gpx", format);
		
		assertEquals("2009-10-17T22:58:43Z", obj.time());
		
		GPXtrk trk = obj.trk();
		
//		assertEquals(obj, trk.parent());
		
		ArrayList<GPXtrkseg> trksegs = trk.trksegs();
		GPXtrkpt trkpt = trksegs.get(0).getTrkpt(0);

		assertEquals(47.644548, trkpt.lat(), 0.001);
		assertEquals(-122.326897, trkpt.lon(), 0.001);
		assertEquals(4.46, trkpt.ele(), 0.001);
		assertEquals("2009-10-17T18:37:26Z", trkpt.timeString());
	}

	@Test
	public void testParseNoTrkPts() {
		GPXformat format = GPXchecker.checkFormat("files/noTrkPts.gpx");

		GPXobject obj = GPXparser.parse("files/noTrkPts.gpx", format);
		
		assertEquals("2009-10-17T22:58:43Z", obj.time());
		
		GPXtrk trk = obj.trk();
		
//		assertEquals(obj, trk.parent());
		assertEquals("Walking around a little bit", trk.name());
		
		ArrayList<GPXtrkseg> trksegs = trk.trksegs();
		assertEquals(0, trksegs.get(0).getTrkpts().size());
	}

}
