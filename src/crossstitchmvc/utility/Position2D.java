package crossstitchmvc.utility;

import java.util.Objects;

/**
 * This class represents a 2D position.
 */
public final class Position2D {
  private final double x;
  private final double y;

  /**
   * Initialize this object to the specified position.
   * 
   * @param x the initial x position
   * @param y the initial y position
   */
  public Position2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Copy constructor.
   * 
   * @param v the position to copy
   */
  public Position2D(Position2D v) {
    this(v.x, v.y);
  }

  /**
   * Accessor for the x-position.
   * 
   * @return the x position
   */
  public double getX() {
    return x;
  }

  /**
   * Accessor for the y-position.
   * 
   * @return the y position
   */
  public double getY() {
    return y;
  }

  public double getDistance(Position2D another) {
    return Math.sqrt(Math.pow(another.getX() - x, 2) + Math.pow(another.getY() - y, 2));
  }

  @Override
  public String toString() {
    return String.format("(%f, %f)", this.x, this.y);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Position2D)) {
      return false;
    }

    Position2D that = (Position2D) a;

    return ((Math.abs(this.x - that.x) < 0.01) && (Math.abs(this.y - that.y) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
