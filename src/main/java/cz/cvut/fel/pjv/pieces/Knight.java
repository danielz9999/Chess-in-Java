package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.Coordinates;
import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;

import java.util.ArrayList;

public class Knight extends Piece {

  public Knight(PlayerColor player) {
        super(player);
    }

  @Override
  public PieceTypes getType() {
    return PieceTypes.KNIGHT;
  }

}
