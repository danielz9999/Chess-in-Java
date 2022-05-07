package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.Color;
import cz.cvut.fel.pjv.PieceTypes;

public class King extends Piece {
  public King(Color player) {
        super(player);
    }

  @Override
  public PieceTypes getType() {
    return PieceTypes.KING;
  }
}
