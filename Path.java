import java.util.ArrayList;

public class Path {
    private int distance;
    private ArrayList<Intersection> path;
    private ArrayList<String> poi;

    public Path(int distance, ArrayList<Intersection> path, ArrayList<String> poi) {
        this.distance = distance;
        this.path = path;
        this.poi = poi;
    }

    public int getDistance() { return distance; }
    public ArrayList<Intersection> getPath() { return path; }
    public ArrayList<String> getPOI() { return poi; }
}
