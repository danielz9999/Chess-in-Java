package cz.cvut.fel.pjv;

public abstract class BotPlayer {

  BotPlayer(PlayerColor pieceColor) {}

  public abstract Coordinates pickAMove(Coordinates[][] options);
}
