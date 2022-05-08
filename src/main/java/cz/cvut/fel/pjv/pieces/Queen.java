package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;

public class Queen extends Piece {
  public Queen(PlayerColor player) {
    super(player);
  }

  @Override
  public PieceTypes getType() {
    return PieceTypes.QUEEN;
  }
}
