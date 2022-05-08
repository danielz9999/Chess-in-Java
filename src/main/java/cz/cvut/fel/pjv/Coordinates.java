package cz.cvut.fel.pjv;

import java.util.Objects;

// The Coordinates class equivalent to Pair<Integer, Integer>
// Created to improve readability, clarity
public class Coordinates {
  private Integer x;
  private Integer y;

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
