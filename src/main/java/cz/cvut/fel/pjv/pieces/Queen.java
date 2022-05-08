package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PlayerColor;
import cz.cvut.fel.pjv.PieceTypes;

public class Queen extends Piece {
  public Queen(PlayerColor player) {
        super(player);
    }

  @Override
  public PieceTypes getType() {
    return PieceTypes.QUEEN;
  }
}
