package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.Color;
import cz.cvut.fel.pjv.Coordinates;
import cz.cvut.fel.pjv.PieceTypes;

//A generic Piece class which is extended by every specific piece class
public abstract class Piece {
  private final Color color;

  Piece(Color player) {
    color = player;
    }


    //All of the following functions will be abstract


    public Coordinates[] getMoves(Coordinates currentCoordinates) {
        return new Coordinates[0];
    }

  public PieceTypes getType() {
        return null;
    }

  public Color getColor() {
    return color;
    }
}
