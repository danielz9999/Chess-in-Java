package cz.cvut.fel.pjv;

import java.util.Objects;

/**
 * A class that holds two integers, meant to represent positions on the chessboard
 * The Java class Pair should work similarly, but it was not exactly what I was looking for, so I made my own
 */
public class Coordinates {
  private final Integer x;
  private final Integer y;

  public Coordinates(int a, int b) {
    x = a;
    y = b;
  }

  public Integer getX() {
    return x;
  }

  public Integer getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordinates that = (Coordinates) o;
    return Objects.equals(x, that.x) && Objects.equals(y, that.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
