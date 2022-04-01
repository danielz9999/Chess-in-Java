package cz.cvut.fel.pjv;

public abstract class BotPlayer {
    private final String color;

    BotPlayer(String pieceColor) {
        color = pieceColor;
    }

    public abstract Coordinates pickAMove(Coordinates[][] options);
}
