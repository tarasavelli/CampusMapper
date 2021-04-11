import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This backend class represents this project's backend. It takes data from within the data wrangler and store it within
 * our graph data type; Dijkstra's algorithm is then used to determine shortest path between two points of interest,
 * which are provided by the input from the frontend.
 *
 * @author Nijae
 */
public class Backend {
    // Private Static Fields; used statically throughout class
    private static DataWrangler data; // Gets data wrangler object
    private static ArrayList<Intersection> intersections; // Gets intersections from data wrangler
    private static CS400Graph<Intersection> map; // Gets graph of intersections

    /**
     * Initializes private fields of class. Iterates through intersections adding the intersections as verticies
     * and adding connections as edges.
     */
    public Backend() {
        // Initialize private fields
        data = new DataWrangler();
        intersections = data.getAllIntersections();
        map = new CS400Graph<>();

        // Store data within map
        for(int i = 0; i < intersections.size(); i++) {
            // Insert intersection as vertex
            Intersection cIntersection = intersections.get(i);
            map.insertVertex(cIntersection);
            // Get current connections & add edges
            ArrayList<Connection> cConnections = cIntersection.getConnections();
            for(int e = 0; e < cConnections.size(); e++) {
                Connection cEdge = cConnections.get(e);
                map.insertEdge(cIntersection, cEdge.getTarget(), cEdge.getDistance());
            }
        }
    }

    /**
     * Takes two intersection points as parameters and gives the shortest path between the two
     * @param start Starting intersection
     * @param end Ending intersection
     * @return Path object containing the distance, path, and points of interest passed from start to end node
     */
    public Path getRoute(String start, String end) {
        Path route = null;
        Intersection startVertex = null;
        Intersection endVertex = null;
        // Find intersections from name
        for (int i = 0; i < intersections.size(); i++) {
            Intersection cIntersection = intersections.get(i);
            if(cIntersection.getName().equalsIgnoreCase(start)) { // If intersection matches start string
                startVertex = cIntersection;
            } else if(cIntersection.getName().equalsIgnoreCase(end)) { // If intersection matches end string
                endVertex = cIntersection;
            }
        }
        // Confirm each vertex was found
        if(startVertex == null || endVertex == null)
            throw new NoSuchElementException("Intersection with specified name does not exist");
        // Find shortest path
        // NoSuchElementException error should be caught by Frontend
        CS400Graph<Intersection>.Path djikstras = map.dijkstrasShortestPath(startVertex, endVertex);
        // Get data fields to pass to route
        int distance = djikstras.distance;
        ArrayList<Intersection> path = new ArrayList<Intersection>(djikstras.dataSequence);
        ArrayList<String> points = new ArrayList<>();
        // Get points of interest
        for(int i = 0; i < path.size(); i++) {
            ArrayList<String> cPoints = path.get(i).getPointOfInterests();
            for(int p = 0; p < cPoints.size(); p++) {
                points.add(cPoints.get(p));
            }
        }

        // Convert to Path object
        route = new Path(distance, path, points);
        return route;
    }

    /**
     * Takes three intersection points as parameters and gives the shortest path between the three. First stopping
     * at the first intersection then the second.
     * @param start Starting intersection
     * @param first First intersection to visit
     * @param second Second intersection to visit
     * @return Array list of Path objects containing the distance, path, and points of interest passed
     * from start to first node to second node
     */
    public ArrayList<Path> getRoute(String start, String first, String second) {
        ArrayList<Path> routes = new ArrayList<>();
        // Find paths
        routes.add(getRoute(start, first));
        routes.add(getRoute(first, second));
        return routes;
    }

    /**
     * Makes an intersection closed/open based upon previous state
     * @param intersection Intersection to toggle
     * @return True if intersection is active after method completion; false if intersection is closed after
     * method completion
     * @throws NoSuchElementException if string does not match name of any existing intersection
     */
    public boolean toggleIntersection(String intersection) {
        Intersection toToggle = null;
        // Search for intersection
        for(int i = 0; i < intersections.size(); i++) {
            Intersection cIntersection = intersections.get(i);
            if(cIntersection.getName().equalsIgnoreCase(intersection)) {
                toToggle = cIntersection;
                break;
            }
        }
        // If not found, throw error
        if(toToggle == null)
            throw new NoSuchElementException("Intersection with specified name does not exist");
        toToggle.isValid = !toToggle.isValid;
        return toToggle.isValid;
    }

    /**
     * Reports all significant land marks and all intersections
     * @return Path object containing all intersections and points of interest
     */
    public Path getPoints() {
        Path collection; // Path object to hold data
        ArrayList<String> points = new ArrayList<>();

        // Get points of interest
        for(int i = 0; i < intersections.size(); i++) {
            // Iterate through each intersections points of interest
            Intersection cIntersection = intersections.get(i);
            ArrayList<String> cPoints = cIntersection.getPointOfInterests();
            for(int p = 0; p < cPoints.size(); p++)
                points.add(cPoints.get(p));
        }
        collection = new Path(0, intersections, points);
        return collection;
    }
}
