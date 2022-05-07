package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.Color;
import cz.cvut.fel.pjv.PieceTypes;

public class Knight extends Piece {

  public Knight(Color player) {
        super(player);
    }

  @Override
  public PieceTypes getType() {
    return PieceTypes.KNIGHT;
  }
}
