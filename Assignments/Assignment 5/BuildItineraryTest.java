import java.util.ArrayList;


public class BuildItineraryTest {
	
	
	public static BuildItinerary readFlightsIntoGraph(String flightFileName, boolean verbose) throws Exception{
	
		ReadFlights mine = new ReadFlights();
		ArrayList<Flight> f = mine.read(flightFileName);
		if (verbose) {
			System.out.println("\n**** Flights Available ****");
			for (Flight aF : f) {
				System.out.println(aF);
			}
			System.out.println("**** End of Flights Available ****\n");
		}
		
		BuildItinerary buildItiner= new BuildItinerary(f);
		AdjacencyListGraph<BuildItinerary.TimeAtAirport,Flight> myGraph = buildItiner.getGraph();
		
		if (verbose) {
			System.out.println("**** Displaying Complete Graph Information ****");
			myGraph.print();
			System.out.println("**** End of Displaying Complete Graph Information **** \n");
		}
        return buildItiner;
        
	}

	public static void main(String[] args) throws Exception {
		
		//AdjacencyListGraph<BuildItinerary.TimeAtAirport,Flight> myGraph;
		
		boolean verbose=false;
		System.out.println("\n----- Starting Test for file flights1.txt (verbose="+verbose+")\n");
		
		BuildItinerary buildItiner= readFlightsIntoGraph("flights1.txt",verbose);
        
		ArrayList<Flight> itin;
		
		itin=buildItiner.cheapestItinerary("YOW","YVR",new Time("8:10"), new Time("22:30"),verbose);
		
		itin=buildItiner.cheapestItinerary("YOW","YVR",new Time("10:00"), new Time("18:30"),verbose);
		
		itin=buildItiner.cheapestItinerary("YOW","YVR",new Time("10:00"), new Time("24:00"),verbose);
		
		itin=buildItiner.cheapestItinerary("YOW","YVR",new Time("13:30"), new Time("15:30"),verbose);	
		
		itin=buildItiner.cheapestItinerary("YUL","YVR",new Time("12:00"), new Time("24:00"),verbose);
		
		System.out.println("\n----- Ending Test for file flight1.txt\n");
		
		
		verbose=false;
		System.out.println("\n----- Starting Test for file flights2.txt (verbose="+verbose+")\n");
		
		buildItiner= readFlightsIntoGraph("flights2.txt",verbose);
		
		itin=buildItiner.cheapestItinerary("YOW","YVR",new Time("8:10"), new Time("22:30"),verbose);
		
		itin=buildItiner.cheapestItinerary("YOW","YVR",new Time("10:00"), new Time("18:30"),verbose);
	
		itin=buildItiner.cheapestItinerary("YOW","YVR",new Time("10:00"), new Time("24:00"),verbose);
		
		itin=buildItiner.cheapestItinerary("YOW","YVR",new Time("13:30"), new Time("15:30"),verbose);	
		
		itin=buildItiner.cheapestItinerary("YUL","YVR",new Time("12:00"), new Time("24:00"),verbose);
		
		System.out.println("\n----- Ending Test for file flight2.txt\n");
		
		//*************************** part 3 ***************************************************//
		System.out.println("\nNow tests for Part 3:");
		
		buildItiner= readFlightsIntoGraph("flights1.txt",false);
        
		verbose=false;
		buildItiner.whereMoneyCanGetMe("YOW", new Time("5:00"), 500, verbose);
		buildItiner.whereMoneyCanGetMe("YOW", new Time("5:00"), 1000, verbose);
			
		
	}

}
