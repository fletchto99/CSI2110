import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class ReadFlights {
	
    ArrayList<Flight> read( String fileName) throws Exception, IOException {
        ArrayList<Flight> a = new ArrayList<Flight>();
        Flight fly;
        String line;
        BufferedReader flightFile = 
        	      new BufferedReader( new FileReader(fileName));
        line = flightFile.readLine( ); // read header line
        
        while( ( line = flightFile.readLine( ) ) != null ) {
            StringTokenizer st = new StringTokenizer( line, ",");
            String aDp,cDp,tDp,aDs,cDs,tDs,fN, costS; int cost;          
            aDp=st.nextToken(); cDp=st.nextToken(); aDs=st.nextToken(); cDs=st.nextToken();
            fN=st.nextToken(); tDp=st.nextToken(); tDs=st.nextToken(); costS=st.nextToken();
            Time timeDep=new Time(tDp);
            Time timeDes=new Time(tDs);
            cost=Integer.parseInt(costS);
            fly= new Flight(fN,aDp,aDs,cDp,cDs,timeDep,timeDes,cost);
            a.add(fly);
        }
        return a;
    }

	public static void main(String[] args) throws IOException, Exception {
		// TODO Auto-generated method stub
		ReadFlights mine = new ReadFlights();
		ArrayList<Flight> f = mine.read("flights.txt");
		for (int i=0; i< f.size(); i++) {
			System.out.println(f.get(i));
		}
		
	
		

	}

}
