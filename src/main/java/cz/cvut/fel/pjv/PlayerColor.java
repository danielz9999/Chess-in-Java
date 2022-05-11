package cz.cvut.fel.pjv;

public enum PlayerColor {
  WHITE,
  BLACK,
  NONE;

  private PlayerColor opposite;
  static {
    WHITE.opposite = BLACK;
    BLACK.opposite = WHITE;
    NONE.opposite = NONE;
  }
  public PlayerColor getOpposite() {
    return opposite;
  }
}
