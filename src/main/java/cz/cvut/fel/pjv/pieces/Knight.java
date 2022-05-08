package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;

public class Knight extends Piece {

  public Knight(PlayerColor player) {
    super(player);
  }

  @Override
  public PieceTypes getType() {
    return PieceTypes.KNIGHT;
  }
}
