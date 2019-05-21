package main.java.com.brain.pulsar.data;


public class Distance {
	
	private final long distance;
	private final DistanceType type;
	
	public Distance(long d, DistanceType t) {
		distance = d;
		type = t;
	}

	public Distance(double d, DistanceType t) {
		distance = convert(d, DistanceType.METER, t);
		type = t;
	}
	
	public Distance(Distance d) {
		distance = d.distance;
		type = d.type;
	}

	public double getDistance() {
		return distance * type.getValue();
	}

	public Distance convert(DistanceType t) {
		return new Distance(convert(distance, type, t), t);
	}
	
	private static long convert(double distance, DistanceType type1, DistanceType type2) {
		return Math.round(distance * type2.getValueInverse() * type1.getValue());
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o == this) {
			return true;
		}
		
		if(o instanceof Distance) {

			Distance d1 = convert(DistanceType.METER);
			Distance d2 = ((Distance) o).convert(DistanceType.METER);
			
			if(d1.distance == d2.distance && d1.type == d2.type && d1.type == DistanceType.METER) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
}
