package cz.cvut.fel.pjv;
/**
 * enum for player colors (or sides), with the NONE color for the null piece and a getOpposite() method
 */
public enum PlayerColor {
  WHITE,
  BLACK,
  NONE;

  static {
    WHITE.opposite = BLACK;
    BLACK.opposite = WHITE;
    NONE.opposite = NONE;
  }

  private PlayerColor opposite;

  public PlayerColor getOpposite() {
    return opposite;
  }
}
