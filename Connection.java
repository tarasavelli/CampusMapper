public class Connection {
  private String target;
  private int distance;
  
  public Connection(String target, int distance) {
    this.target = target;
    this.distance = distance;
  }
  
  public String getTarget() {
    return target;
  }
  
  public int getDistance() {
    return distance;
  }
  
  @Override
  public String toString() {
    return target;
  }
}
