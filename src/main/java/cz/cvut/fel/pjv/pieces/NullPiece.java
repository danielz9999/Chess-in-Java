package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PlayerColor;
import cz.cvut.fel.pjv.PieceTypes;

// A piece representing an empty space on a board
public class NullPiece extends Piece {

  public NullPiece() {
    super(PlayerColor.NONE);
    }

  @Override
  public PieceTypes getType() {
    return PieceTypes.NONE;
  }
}
