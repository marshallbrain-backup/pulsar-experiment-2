package com.brain.pulsar.units;

/**
 * Stores information about conversions for different unit types
 * 
 * @author Marshall Brain
 *
 */
public enum DistanceUnit {
	
	/**
	 * The astronomical unit. Used to measure distances in a star system. 1 AU is
	 * the distance between earth and the sun. 1 AU = 149597870700 meters
	 */
	AU(149597870700L, 0),
	/**
	 * The meter 1 meter = 1 meter
	 */
	METER(1L, 0),
	/**
	 * The solar radius. Used to measure the radiuses of stars. 1 solar radius is
	 * the radius of the sun. 1 AU = 695500000 meters
	 */
	SOLAR_RADIUS(695500000L, 0);
	
	private final int exponent;
	
	private final long number;
	
	/**
	 * @param number
	 *            The number of meters that the unit represents
	 * @param exponent
	 *            The scientific notation that the number is in. Allows for
	 *            decimals.
	 */
	private DistanceUnit(long n, int e) {
		
		number = n;
		exponent = e;
	}
	
	/**
	 * @return A double representation of the unit in meters
	 */
	public double getValue() {
		
		return number * Math.pow(10, exponent);
	}
	
	/**
	 * @return The inverse of the number gotten from value
	 */
	public double getValueInverse() {
		
		return 1 / getValue();
	}
	
}
