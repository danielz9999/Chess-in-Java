package cz.cvut.fel.pjv;
//A generic Piece class which is extended by every specific piece class
public abstract class Piece {
    private final String owner;

    Piece(String player) {
        owner = player;
    }


    //All of the following functions will be abstract


    public Coordinates[] getMoves(Coordinates currentCoordinates) {
        return null;
    }

    public String getType() {
        return null;
    }

    public String getOwner() {
        return owner;
    }
}
