package main.java.com.brain.pulsar.data;

/**
 * Stores information about conversions for different unit types
 * 
 * @author Marshall Brain
 *
 */
public enum DistanceType {
	
	AU(149597870700L, 0),
	METER(1L, 0),
	SOLAR_RADIUS(695500L, 3);
	
	private final int exponent;
	
	private final long number;
	
	/**
	 * @param number
	 *            The number of meters that the unit represents
	 * @param exponent
	 *            The scientific notation that the number is in. Allows for
	 *            decimals.
	 */
	private DistanceType(long n, int e) {
		
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
