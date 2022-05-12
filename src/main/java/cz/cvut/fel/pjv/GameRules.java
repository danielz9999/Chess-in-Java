package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.BoardWindow;

import java.util.ArrayList;

// The class ensuring the correct application of chess rules
public class GameRules {
  private final PieceMoveGenerator pieceMoveGenerator;
  private final BoardState board;
  private final BoardWindow boardWindow;
  private final PieceMover pieceMover;
  private ArrayList<Coordinates> moves;
  private Coordinates startCoordinates;
  private final CheckMoveControl checkMoveControl;
  private final CheckmateChecker checkmateChecker;

  private ArrayList<Coordinates> whitePositions;
  private ArrayList<Coordinates> blackPositions;

  private final Controller controller;

  public GameRules(BoardState board, BoardWindow boardWindow, Controller controller) {
    this.board = board;
    this.boardWindow = boardWindow;
    refreshPiecePositions();
    pieceMoveGenerator = new PieceMoveGenerator();
    pieceMover = new PieceMover(boardWindow);
    checkMoveControl = new CheckMoveControl(board, whitePositions, blackPositions);
    checkmateChecker = new CheckmateChecker(checkMoveControl);
    this.controller = controller;
  }

  public boolean firstClick(Coordinates coordinates) {

    startCoordinates = coordinates;
    ArrayList<Coordinates> possibleMoves =
        (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, coordinates);
    possibleMoves = checkMoveControl.filterMoves(possibleMoves, startCoordinates);
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
  public void secondClick(Coordinates coordinates) {
    for (Coordinates move : moves) {
      boardWindow.dehighlightButton(move);
    }
    if (moves.contains(coordinates)) {

      pieceMover.movePiece(board, startCoordinates, coordinates);
      refreshPiecePositions();


      controller.changeTurn();
      if (checkmateChecker.checkForMate(board, whitePositions, blackPositions)) {
        boardWindow.gameEnd(checkmateChecker.checkForCheckmate(), board.getCurrentTurn().getOpposite());
      }


    }
    startCoordinates = null;
    moves = null;
  }

  private void refreshPiecePositions() {
    whitePositions = new ArrayList<>();
    blackPositions = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board.getBoard()[i][j].getColor() == PlayerColor.WHITE) {
          whitePositions.add(new Coordinates(i, j));
        } else if (board.getBoard()[i][j].getColor() == PlayerColor.BLACK) {
          blackPositions.add(new Coordinates(i, j));
        }
      }
    }
    if (checkMoveControl != null) {
      this.checkMoveControl.setPiecePositions(whitePositions, blackPositions);
    }
  }

}
