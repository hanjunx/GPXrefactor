package edu.upenn.cis573;

import static org.junit.Assert.*;

import org.junit.Test;

public class GPXtrkTest {

	@Test
	public void testBearing() {
		GPXtrk trk = new GPXtrk(null, null, null);
		
		// due north: lat gets bigger, lon stays the same
		assertEquals(0, trk.bearing(0, 0, 20, 0), 0);

		// due south: lat gets smaller, lon stays the same
		assertEquals(180, trk.bearing(0, 0, -20, 0), 0);

		// due east: lat stays same, lon gets bigger
		assertEquals(90, trk.bearing(0, 0, 0, 20), 0);

		// due west: lat stays same, lon gets smaller
		assertEquals(-90, trk.bearing(0, 0, 0, -20), 0);
	}
}
