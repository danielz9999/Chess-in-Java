package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;

public class Bishop extends Piece {
  public Bishop(PlayerColor player) {
    super(player);
  }

  @Override
  public PieceTypes getType() {
    return PieceTypes.BISHOP;
  }
}
