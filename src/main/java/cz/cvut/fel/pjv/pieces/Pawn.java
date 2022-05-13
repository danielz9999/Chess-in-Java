package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;
/**
 * A class representing the Pawn
 */
public class Pawn extends Piece {

  public Pawn(PlayerColor player) {
    super(player);
  }

  @Override
  public PieceTypes getType() {
    return PieceTypes.PAWN;
  }
}
