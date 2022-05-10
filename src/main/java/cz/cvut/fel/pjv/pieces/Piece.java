package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;

// A generic Piece class which is extended by every specific piece class
public abstract class Piece {
  private final PlayerColor color;

  Piece(PlayerColor player) {
    color = player;
  }

  // All of the following functions will be abstract

  public PieceTypes getType() {
    return null;
  }

  public PlayerColor getColor() {
    return color;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Piece piece = (Piece) o;
    return (color == piece.color && this.getType() == piece.getType());
  }


}
