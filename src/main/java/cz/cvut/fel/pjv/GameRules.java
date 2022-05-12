package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.BoardWindow;

import java.util.ArrayList;
import java.util.logging.Logger;


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
  private final BotPlayer botPlayer;

  private final Logger log = Logger.getLogger(GameRules.class.getName());

  public GameRules(BoardState board, BoardWindow boardWindow, Controller controller) {
    this.board = board;
    this.boardWindow = boardWindow;
    refreshPiecePositions();
    pieceMoveGenerator = new PieceMoveGenerator();
    pieceMover = new PieceMover(boardWindow);
    checkMoveControl = new CheckMoveControl(board, whitePositions, blackPositions);
    checkmateChecker = new CheckmateChecker(checkMoveControl);
    this.controller = controller;
    log.info("Creating bot player with color: "+ board.getBotPlayerColor());
    botPlayer = new RandomBotPlayer(board.getBotPlayerColor());

    if (botPlayer.getColor() != PlayerColor.NONE && botPlayer.getColor() == board.getCurrentTurn()) {
      botPlayerMove();
    }

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

      if (botPlayer.getColor() != PlayerColor.NONE) {
        log.info("Initiating bot move with color: " + botPlayer.getColor());
        botPlayerMove();
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

  private void botPlayerMove() {
    ArrayList<Coordinates> positions;
    if (botPlayer.getColor() == PlayerColor.WHITE) {
      positions = whitePositions;

    } else {
      positions = blackPositions;
    }

    ArrayList<Coordinates> possibleMoves;
    Coordinates pickedPiece = botPlayer.pickPiece(positions);
    ArrayList<Coordinates> generatedMoves =
        (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, pickedPiece);
    possibleMoves = checkMoveControl.filterMoves(generatedMoves, pickedPiece);
    while (possibleMoves.isEmpty()) {
      log.info("Bot Choices Looping..");
      pickedPiece = botPlayer.pickPiece(positions);

      generatedMoves =
          (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, pickedPiece);
      if (generatedMoves.isEmpty()) {
        continue;
      }
      possibleMoves = checkMoveControl.filterMoves(generatedMoves, pickedPiece);
    }
    Coordinates pickedMove = botPlayer.pickMove(possibleMoves);
    pieceMover.movePiece(board, pickedPiece, pickedMove);
    log.info("Bot move made, from " + pickedPiece.getX() + " " + pickedPiece.getY() +
                  " TO " + pickedMove.getX() + " " + pickedMove.getY());
    refreshPiecePositions();
    controller.changeTurn();
    if (checkmateChecker.checkForMate(board, whitePositions, blackPositions)) {
      boardWindow.gameEnd(
          checkmateChecker.checkForCheckmate(), board.getCurrentTurn().getOpposite());
    }
  }
}
