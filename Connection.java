// --== CS400 File Header Information ==--
// Name: Anthony Reis
// Email: atreis@wisc.edu
// Team: Red
// Role: Data Wrangler
// TA: Mu Cai
// Lecturer: Gary Dahl
// Notes to Grader: N/A

/**
 * This connection class represents the connection between one intersection and another.
 * 
 * @author Anthony Reis
 *
 */

public class Connection {
  private Intersection target;
  private int distance;

  public Connection(Intersection target, int distance) {
    this.target = target;
    this.distance = distance;
  }

  public Intersection getTarget() {
    return target;
  }

  public int getDistance() {
    return distance;
  }

  @Override
  public String toString() {
    return target.getName();
  }
}