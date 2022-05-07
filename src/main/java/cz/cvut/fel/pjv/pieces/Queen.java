package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.Color;
import cz.cvut.fel.pjv.PieceTypes;

public class Queen extends Piece {
  public Queen(Color player) {
        super(player);
    }

  @Override
  public PieceTypes getType() {
    return PieceTypes.QUEEN;
  }
}
