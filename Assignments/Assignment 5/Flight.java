/*
 * Store Flight Information
 */
public class Flight implements Integral {
	
	String flightNumber;
	String airportDepart;
	String airportDestin;
	Time timeDepart;
	Time timeDestin;
	String cityDepart;
	String cityDestin;
	int costFlight;
	
	public Flight(String flightN, String airDep, String airDest, String cityDep,String citDest,
			Time timeDep, Time timeDest, int cost) {
		flightNumber=flightN;
		airportDepart=airDep; 
		airportDestin=airDest;
		timeDepart=timeDep;
		timeDestin=timeDest;
		cityDepart=cityDep;
		cityDestin=citDest;
		costFlight=cost;
	}
	
	public String getflightNumber() {
		return flightNumber;
	}
	
	public String getAirportDepart() {
		return airportDepart;
	}
	
	public String getAirportDestin() {
		return airportDestin;
	}
	
	public Time getTimeDepart() {
		return timeDepart;
	}
	
	public Time getTimeDestin() {
		return timeDestin;
	}
	
	public String getCityDepart() {
		return cityDepart;
	}
	
	public String getityDestin() {
		return cityDestin;
	}
	
	public int getCost() {
		return costFlight;
	}
	public String toString() {
		return flightNumber+" "+ airportDepart+" "+cityDepart+ " " + timeDepart+
				" "+ airportDestin+" "+cityDestin+ " " + timeDestin+ " $"+costFlight;
	}
	
	public int toIntegral() {
		return costFlight;
	}

}
