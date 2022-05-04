package cz.cvut.fel.pjv;

public abstract class BotPlayer {

  BotPlayer(String pieceColor) {
  }

  public abstract Coordinates pickAMove(Coordinates[][] options);
}
