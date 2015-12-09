import net.datastructures.Edge;
import net.datastructures.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class BuildItinerary {

    AdjacencyListGraph<TimeAtAirport, Flight> graph;

    public BuildItinerary(ArrayList<Flight> flights) {

        buildGraph(flights);

    }

    private void buildGraph(ArrayList<Flight> flights) {
        graph = new AdjacencyListGraph<>(true); //empty directed graph

        // Insert vertices and edges for all flight legs
        Hashtable<TimeAtAirport, Vertex<TimeAtAirport>> vertices = new Hashtable<>();

        for (Flight flightLeg : flights) {
            TimeAtAirport depart = new TimeAtAirport(flightLeg.getAirportDepart(), flightLeg.getTimeDepart());
            TimeAtAirport destin = new TimeAtAirport(flightLeg.getAirportDestin(), flightLeg.getTimeDestin());

            Vertex<TimeAtAirport> sourceV = vertices.get(depart);
            if (sourceV == null) {
                // Source vertex not in graph -- insert
                sourceV = graph.insertVertex(depart);
                vertices.put(depart, sourceV);
            }
            Vertex<TimeAtAirport> destV = vertices.get(destin);
            if (destV == null) {
                // Destination vertex not in graph -- insert
                destV = graph.insertVertex(destin);
                vertices.put(destin, destV);
            }

            graph.insertEdge(sourceV, destV, flightLeg);
        }

        // connect vertices in the same airport in increasing direction of time

        ArrayList<TimeAtAirport> allTAA = new ArrayList<>();
        for (Vertex<TimeAtAirport> v : graph.vertices()) {
            allTAA.add(v.getElement());
        }

        Collections.sort(allTAA); // sort according to Comparable TimeAtAirport (first airport the time)

        TimeAtAirport taa1 = allTAA.get(0);
        TimeAtAirport taa2;

        for (int i = 1; i < allTAA.size(); i++) {
            taa2 = allTAA.get(i);
            if (taa1.sameAirport(taa2)) {
                Flight dummyFlight = new Flight("Connection", taa1.getAirport(), taa2.getAirport(), "", "", taa1.getTime(), taa2.getTime(), 0);
                graph.insertEdge(vertices.get(taa1), vertices.get(taa2), dummyFlight);
            }
            taa1 = taa2;
        }

    }

    private void displayAllPathInfo(DijkstraAlgorithm.ShortestPathsInfo spInfo) {
        int[] listV = spInfo.getPrevVertex();
        int[] listD = spInfo.getDist();
        Edge[] listE = spInfo.getPrevEdge();

        System.out.println("\n*** Displaying All Shortest Path Info given by DijkstraAlgorithm.ShortestPathsInfo***");
        for (int i = 0; i < spInfo.getNumVertices(); i++) {
            System.out.print("Vertex " + i + ":" + graph.getVertexAtPosition(i).getElement() +
                    " Cost=" + listD[i]);
            if (listV[i] != -1) {
                System.out.print(", PrevVertex:" + graph.getVertexAtPosition(listV[i]).getElement() + ",PrevEdge:" + listE[i].getElement());
            } else if (listD[i] == 0) {
                System.out.print(", PrevVertex:(source)");
            } else {
                System.out.print(",(Unreachable)");
            }
            System.out.println();
        }
        System.out.println("*** Enf of Displaying All Shortest Path Information:***\n");
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Flight> cheapestItinerary(String airportDepart, String airportDestin, Time minDepTime, Time maxArrTime, boolean verbose) {

        System.out.println("\n>>>Customer requested departure from " + airportDepart + " after " + minDepTime +
                " arriving at " + airportDestin + " before " + maxArrTime);


        DijkstraAlgorithm dijk = new DijkstraAlgorithm();
        Vertex<TimeAtAirport> minVertex = null, maxVertex = null;

        //This part is to be implemented by students - currently hardcoded values of minVertex and maxVertex
        //that works with the test 'flights1.txt' but students will have to find minVertex and maxVertex
        //by considering 'minDepTime' and 'maxArrTime'

        /********************** CODE TO BE ADDED HERE ******************************************/


        TimeAtAirport startVertex = new TimeAtAirport(airportDepart, minDepTime);
        TimeAtAirport endVertex = new TimeAtAirport(airportDestin, maxArrTime);

        for (Vertex<TimeAtAirport> v : graph.vertices()) {
            TimeAtAirport current = v.getElement();
            if (current.sameAirport(startVertex)) {
                if (current.getTime().compareTo(startVertex.getTime()) >= 0) {
                    if (minVertex == null || current.getTime().compareTo(minVertex.getElement().getTime()) < 0) {
                        minVertex = v;
                    }
                }
            }
            if (current.sameAirport(endVertex)) {
                if (current.getTime().compareTo(endVertex.getTime()) <= 0) {
                    if (maxVertex == null || current.getTime().compareTo(maxVertex.getElement().getTime()) >= 0) {
                        maxVertex = v;
                    }
                }
            }
        }

        if (minVertex == null || maxVertex == null) {
            return null;
        }

        // if bounds for arrival and departure do not work
        if ((minVertex.getElement().getTime().compareTo(new Time(25, 0)) == 0) || (maxVertex.getElement().getTime().compareTo(new Time(-1, -1)) == 0)) {
            return null;
        }


        DijkstraAlgorithm.ShortestPathsInfo spInfo = dijk.findShortestPaths(minVertex, graph);

        if (verbose) {
            displayAllPathInfo(spInfo);
        }
        // Itinerary finally print below
        if (spInfo.isReachable(graph.getPositionOfVertex(maxVertex))) {
            if (verbose) {
                // This is the unclean itinerary that prints wait at an airport as different stops
                System.out.println("Unclean Itinerary: from " + minVertex.getElement() + " to " +
                        maxVertex.getElement() + "\n" + spInfo.stringPathFromSourceToVertex(maxVertex, graph));
            }


            // System.out.println("To be implemented by the student using <spInfo> (do not use the String above)");
            System.out.print("Official Itinerary: " + minVertex.getElement() + " to " +
                    maxVertex.getElement() + "\n");
            // *** Here students must add a second printout of Itinerary, but without the waiting edges
            // *** This must use spInfo path returned by the method below; not parsing the previous output!
            int[] path = spInfo.pathFromSourceTo(graph.getPositionOfVertex(maxVertex));

            ArrayList<Flight> shortestPath = new ArrayList<>();
            int totalCost = 0;
            /************* add the missing code here *************************/
            for (int i = 0; i < path.length - 1; i++) {
                Flight flight = graph.getEdge(graph.getVertexAtPosition(path[i]), graph.getVertexAtPosition(path[i + 1])).getElement();
                if (flight.getCost() != 0) {
                    shortestPath.add(flight);
                    totalCost += flight.getCost();
                }
            }

            shortestPath.forEach(System.out::println);
            System.out.println("Total cost=" + totalCost);

            return shortestPath;

        }
        System.out.println("No itineraries available; please widen your search times.");
        return null;
    }

    public AdjacencyListGraph<TimeAtAirport, Flight> getGraph() {
        return graph;
    }

    @SuppressWarnings("unchecked")
    public void whereMoneyCanGetMe(String airportDepart, Time minDepTime, int dollars, boolean verbose) {

        // Similar to the cheapestItinerary in the determination of the source vertex
        // Using a fixed dummy value now.

//        Vertex<TimeAtAirport> minVertex = null;

        TimeAtAirport startVertex = new TimeAtAirport(airportDepart, minDepTime);

        ArrayList<Vertex<TimeAtAirport>> minVerticies = new ArrayList<>();

        for (Vertex<TimeAtAirport> v : graph.vertices()) {
            TimeAtAirport current = v.getElement();
            if (current.sameAirport(startVertex)) {
                if (current.getTime().compareTo(startVertex.getTime()) >= 0) {
                    minVerticies.add(v);
                }
            }
        }

        if (minVerticies.isEmpty()) {
            System.out.println("You money will get you nowhere; please change your search parameters");
            return;
        }

//        if (verbose) {
//            displayAllPathInfo(spInfo);
//        }

        /************ code for part 3 to be added ******************************/

        System.out.println(">>>> Here you will provide info on where to go under $" + dollars);

        ArrayList<TimeAtAirport> visited = new ArrayList<>();
        for (Vertex<TimeAtAirport> minVertex : minVerticies) {
            DijkstraAlgorithm dijk = new DijkstraAlgorithm();
            DijkstraAlgorithm.ShortestPathsInfo spInfo = dijk.findShortestPaths(minVertex, graph);
            for (Vertex<TimeAtAirport> v : graph.vertices()) {
                if (!v.getElement().getAirport().equals(airportDepart)) {
                    if (spInfo.isReachable(graph.getPositionOfVertex(v))) {
                        int[] path = spInfo.pathFromSourceTo(graph.getPositionOfVertex(v));

                        int cost = 0;
                        TimeAtAirport lastVisited = null;

                        ArrayList<Flight> itinerary = new ArrayList<>();

                        for (int i = 0; i < path.length - 1; i++) {
                            Vertex<TimeAtAirport> leave = graph.getVertexAtPosition(path[i]);
                            Vertex<TimeAtAirport> arrive = graph.getVertexAtPosition(path[i + 1]);

                            Flight flight = graph.getEdge(leave, arrive).getElement();
                            if (flight.getCost() != 0) {
                                lastVisited = graph.getVertexAtPosition(path[i + 1]).getElement();
                                cost += flight.getCost();
                                itinerary.add(flight);
                            }
                        }

                        if (cost <= dollars && lastVisited != null && !visited.contains(lastVisited)) {
                            visited.add(lastVisited);
                            System.out.println("----Itenerary----");
                            itinerary.forEach(System.out::println);
                            System.out.println("Total cost=" + cost);
                            System.out.println();
                        }
                    }
                }
            }
        }

    }


    public class TimeAtAirport implements Comparable {
        String airport;
        Time time;

        public TimeAtAirport(String airport, Time time) {
            this.airport = airport;
            this.time = time;
        }

        Time getTime() {
            return time;
        }

        String getAirport() {
            return airport;
        }

        boolean sameAirport(TimeAtAirport other) {
            return (this.airport.equals(other.airport));
        }

        @Override
        public int compareTo(Object o) throws ClassCastException {
            if (!(o instanceof TimeAtAirport)) {
                throw new ClassCastException("A TimeAtAirport object expected.");
            }
            TimeAtAirport otherTAA = (TimeAtAirport) o;
            int result = this.airport.compareTo(otherTAA.airport);
            if (result == 0) {
                result = this.time.compareTo(otherTAA.time);
            }
            return result;
        }

        public boolean equals(Object o) {
            return (this.compareTo(o)==0);
        }

        public String toString() {
            return airport + " " + time;
        }

    }

}
