package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.pieces.Piece;

import java.util.ArrayList;

//A class representing the chessboard
//
public class Board {
    Piece[][] chessboard = new Piece[8][8];
    ArrayList<Coordinates> whitePiecesPositions;
    ArrayList<Coordinates> blackPiecesPositions;


    //Moves piece from starting coordinates to the destiantion coordinates
    public boolean movePiece(Piece piece, Coordinates start, Coordinates destination) {
        return false;
    }

    //Checks whether the current positions of pieces cause a check
    public boolean isCheck() { return false;}

    //Creates pieces on the board according to the standard chess layout
    public void standardSetup() {}


}
