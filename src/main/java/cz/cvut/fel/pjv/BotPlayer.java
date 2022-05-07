package cz.cvut.fel.pjv;

public abstract class BotPlayer {

  BotPlayer(Color pieceColor) {
  }

  public abstract Coordinates pickAMove(Coordinates[][] options);
}
