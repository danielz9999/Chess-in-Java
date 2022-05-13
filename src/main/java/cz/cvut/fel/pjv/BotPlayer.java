package cz.cvut.fel.pjv;


import java.util.List;
/**
 * Abstract class that all bot players (makers of PC moves) share
 * Useful if in the future more bot players
 */
public abstract class BotPlayer {
  private PlayerColor color;

  BotPlayer(PlayerColor pieceColor) {
    color = pieceColor;
  }

  public abstract Coordinates pickPiece(List<Coordinates> options);

  public abstract Coordinates pickMove(List<Coordinates> options);

  public PlayerColor getColor() {
    return color;
  }
}
