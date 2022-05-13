package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;
/**
 * A class representing the empty space on the board, takes no parameters
 * holds a NONE PlayerColor and PieceType
 */
public class NullPiece extends Piece {

  public NullPiece() {
    super(PlayerColor.NONE);
  }

  @Override
  public PieceTypes getType() {
    return PieceTypes.NONE;
  }
}
