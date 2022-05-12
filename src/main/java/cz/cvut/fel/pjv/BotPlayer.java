package cz.cvut.fel.pjv;

import java.util.ArrayList;

public abstract class BotPlayer {
  private PlayerColor color;

  BotPlayer(PlayerColor pieceColor) {
    color = pieceColor;
  }

  public abstract Coordinates pickPiece(ArrayList<Coordinates> options);

  public abstract Coordinates pickMove(ArrayList<Coordinates> options);

  public PlayerColor getColor() {
    return color;
  }
}
