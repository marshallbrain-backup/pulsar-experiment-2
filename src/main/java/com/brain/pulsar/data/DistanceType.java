package main.java.com.brain.pulsar.data;


public enum DistanceType {
	
	METER(1L, 0),
	AU(149597870700L, 0),
	SOLAR_RADIUS(695500L, 3),
	;
	
	private final long number;
	private final int exponent;
	
	private DistanceType(long n, int e) {
		number = n;
		exponent = e;
	}
	
	public double getValue() {
		return number * Math.pow(10, exponent);
	}

	public double getValueInverse() {
		return 1/getValue();
	}
	
}
