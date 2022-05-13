package cz.cvut.fel.pjv;

import java.util.ArrayList;

/**
 * A class which takes possible moves of a piece and returns a list of moves which don't put the
 * player in check The class creates a new board on which it imagines the move happening And then
 * checks the possible moves of all enemy pieces, if any of those moves threaten the players king
 * the move is removed
 */
public class CheckMoveControl {
  private PieceMover pieceMover;
  private BoardState board;
  private ArrayList<Coordinates> blackPositions;
  private ArrayList<Coordinates> whitePositions;
  private PieceMoveGenerator pieceMoveGenerator;

  public CheckMoveControl(
      BoardState board,
      ArrayList<Coordinates> whitePositions,
      ArrayList<Coordinates> blackPositions) {
    pieceMover = new PieceMover(null, null);
    pieceMoveGenerator = new PieceMoveGenerator();
    this.board = board;
    this.whitePositions = whitePositions;
    this.blackPositions = blackPositions;
  }

  public ArrayList<Coordinates> filterMoves(
      ArrayList<Coordinates> initialMoves, Coordinates startCoords) {
    ArrayList<Coordinates> filteredMoves = new ArrayList<>(initialMoves);
    filteredMoves.removeIf(move -> !checkControl(move, startCoords));
    return filteredMoves;
  }

  private boolean checkControl(Coordinates move, Coordinates startCoords) {
    BoardState movedBoard =
        new BoardState(board.isCanWhiteCastle(), board.isCanBlackCastle(), -1, -1, false);

    for (int i = 0; i < 8; i++) {
      movedBoard.getBoard()[i] = board.getBoard()[i].clone();
    }
    movedBoard.setWhiteKingPosition(board.getWhiteKingPosition());
    movedBoard.setBlackKingPosition(board.getBlackKingPosition());
    pieceMover.movePiece(movedBoard, startCoords, move);
    if (board.getCurrentTurn() == PlayerColor.WHITE) {
      return isWhiteInCheck(movedBoard);
    } else {

      return isBlackInCheck(movedBoard);
    }
  }

  private boolean isWhiteInCheck(BoardState movedBoard) {
    ArrayList<Coordinates> threatenedPositions;
    for (Coordinates position : blackPositions) {
      threatenedPositions =
          (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(movedBoard, position);
      for (Coordinates blackMove : threatenedPositions) {
        if (blackMove.equals(movedBoard.getWhiteKingPosition())) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isBlackInCheck(BoardState movedBoard) {
    ArrayList<Coordinates> threatenedPositions;
    movedBoard.setCurrentTurn(PlayerColor.WHITE);
    for (Coordinates position : whitePositions) {
      threatenedPositions =
          (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(movedBoard, position);

      for (Coordinates whiteMove : threatenedPositions) {
        if (whiteMove.equals(movedBoard.getBlackKingPosition())) {
          return false;
        }
      }
    }
    return true;
  }

  public void setPiecePositions(
      ArrayList<Coordinates> whitePositions, ArrayList<Coordinates> blackPositions) {
    this.whitePositions = whitePositions;
    this.blackPositions = blackPositions;
  }
}
