package cz.cvut.fel.pjv;

import java.util.ArrayList;

public abstract class BotPlayer {

  BotPlayer(PlayerColor pieceColor) {}

  public abstract Coordinates pickPiece(ArrayList<Coordinates> options);

  public abstract Coordinates pickMove(ArrayList<Coordinates> options);
}
