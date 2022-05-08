package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.BoardWindow;

import java.util.ArrayList;

// The class ensuring the correct application of chess rules
public class GameRules {
  private final PieceMoveGenerator pieceMoveGenerator;
  private BoardState board;
  private BoardWindow boardWindow;
  private final PieceMover pieceMover;
  private ArrayList<Coordinates> moves;
  private Coordinates startCoordinates;

  public GameRules(BoardState board, BoardWindow boardWindow) {
    this.board = board;
    this.boardWindow = boardWindow;
    pieceMoveGenerator = new PieceMoveGenerator();
    pieceMover = new PieceMover(boardWindow);
  }

  public boolean firstClick(Coordinates coordinates) {
    startCoordinates = coordinates;
    ArrayList<Coordinates> possibleMoves =
        (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, coordinates);
    moves = possibleMoves;
    if (possibleMoves.isEmpty()) {
      return false;
    } else {
      for (Coordinates move : possibleMoves) {
        boardWindow.highlightButton(move);
      }

      return true;
    }
  }
  public boolean secondClick(Coordinates coordinates) {
    for (Coordinates move : moves) {
      boardWindow.dehighlightButton(move);
    }
    if (moves.contains(coordinates)) {

      pieceMover.movePiece(board, startCoordinates, coordinates);
    } else {
      startCoordinates = null;
      moves = null;
      return false;
    }
    return true;
  }
}
