import java.util.ArrayList;

public class TestDataWrangler {
  public static void main(String[] args) {
    DataWrangler data = new DataWrangler();
    ArrayList<Intersection> allInter = data.getAllIntersections();

    for (Intersection i : allInter) {
      System.out.println("Name: " + i.getName() + " Connections: " + i.getConnections().toString());
    }

    
    
    
  }
}
