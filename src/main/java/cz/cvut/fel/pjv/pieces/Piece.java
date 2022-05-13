package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;

/**
 * An abstract class from which all of the specific piece types inherit
 * takes a PlayerColor @param player and sets it
 * what all pieces inherit is the two getters, getType() and getColo()
 */
public abstract class Piece {
  private final PlayerColor color;

  Piece(PlayerColor player) {
    color = player;
  }

  public abstract PieceTypes getType();

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
