package cz.cvut.fel.pjv;

import java.util.ArrayList;

//The class ensuring the correct application of chess rules
public class GameRules {
    private BoardState board;
    private final PieceMoveGenerator pieceMoveGenerator;

    public GameRules(BoardState board) {
        this.board = board;
        pieceMoveGenerator = new PieceMoveGenerator();
    }
    public boolean firstClick(Coordinates coordinates) {
        ArrayList<Coordinates> possibleMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, coordinates);
        if (possibleMoves.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

}
