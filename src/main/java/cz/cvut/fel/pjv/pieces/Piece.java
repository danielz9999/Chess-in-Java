package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.Coordinates;

//A generic Piece class which is extended by every specific piece class
public abstract class Piece {
  private final String color;

    Piece(String player) {
    color = player;
    }


    //All of the following functions will be abstract


    public Coordinates[] getMoves(Coordinates currentCoordinates) {
        return null;
    }

    public String getType() {
        return null;
    }

  public String getColor() {
    return color;
    }
}
