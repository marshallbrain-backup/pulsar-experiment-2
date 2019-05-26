package main.java.com.brain.pulsar.data;

import java.math.BigDecimal;

/**
 * Allows easer conversion between different units of measurement
 * 
 * @author Marshall Brain
 *
 */
public class Distance {
	
	/**
	 * Converts a double of the oldType a long of the newType
	 * 
	 * @param distance
	 *            The distance being converted
	 * @param oldScale
	 *            The scientific notation of the distance being converted
	 * @param oldType
	 *            The type of units being converted from
	 * @param newType
	 *            The type of units being converted to
	 * @return The distance in the new units
	 */
	public static Distance convert(double distance, int oldScale, DistanceType oldType, DistanceType newType) {
		
		// Do not switch newType and oldType
		BigDecimal converted = BigDecimal.valueOf(distance * newType.getValueInverse() * oldType.getValue());
		converted = converted.scaleByPowerOfTen(-oldScale);
		
		int scale = converted.scale();
		long normalized = converted.scaleByPowerOfTen(scale).longValueExact();
		
		return new Distance(normalized, scale, newType);
		
	}
	
	private final int scale;
	
	private final long amount;
	
	private final DistanceType type;
	
	/**
	 * Clone the given distance
	 * 
	 * @param distance
	 *            The distance being cloned
	 */
	public Distance(Distance distance) {
		
		amount = distance.amount;
		type = distance.type;
		scale = distance.scale;
	}
	
	/**
	 * Converts a distance in meters to a distance of the given type
	 * 
	 * @param distance
	 *            The distance
	 * @param type
	 *            The unit of measurement that the long is in
	 */
	public Distance(double distance, DistanceType type) {
		
		Distance converted = convert(distance, 0, DistanceType.METER, type);
		amount = converted.amount;
		this.type = converted.type;
		scale = converted.scale;
	}
	
	/**
	 * Creates a new Distance
	 * 
	 * @param distance
	 *            The distance
	 * @param type
	 *            The unit of measurement that the long is in
	 */
	public Distance(long distance, DistanceType type) {
		
		this.type = type;
		amount = distance;
		scale = 0;
	}
	
	/**
	 * Creates a new Distance
	 * 
	 * @param distance
	 *            The distance
	 * @param scale
	 *            The scientific notation of the distance
	 * @param type
	 *            The unit of measurement that the long is in
	 */
	public Distance(long distance, int scale, DistanceType type) {
		
		this.type = type;
		this.scale = scale;
		amount = distance;
	}
	
	/**
	 * Converts the current unit of distance into a new one
	 * 
	 * @param newType
	 *            The unit type being converted to
	 * @return A new distance of the given type
	 */
	public Distance convert(DistanceType newType) {
		
		return convert(amount, scale, type, newType);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		
		if (o == this) {
			return true;
		}
		
		if (o instanceof Distance) {
			
			Distance d1 = convert(DistanceType.METER);
			Distance d2 = ((Distance) o).convert(DistanceType.METER);
			
			if (d1.amount != d2.amount) {
				return false;
			}
			if (d1.scale != d2.scale) {
				return false;
			}
			if (d1.type != d2.type) {
				return false;
			}
			
			return (d1.type == DistanceType.METER);
			
		}
		
		return false;
		
	}
	
	/**
	 * Converts the distance into meters using the conversion given by type
	 * 
	 * @return The number of meters being represented by this object
	 */
	public double getDistance() {
		
		return amount * Math.pow(10, -scale) * type.getValue();
	}
	
}
