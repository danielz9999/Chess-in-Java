package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.BoardWindow;

import java.util.logging.Logger;

public class PieceMover {
  private final BoardWindow boardWindow;
  private final PGNHistory pgnHistory;
  private final PieceMoveGenerator pieceMoveGenerator;
  private final static String castling = "castling";
  private final Logger log = Logger.getLogger(PieceMover.class.getName());


  public PieceMover(BoardWindow boardWindow, PGNHistory pgnHistory) {
    this.boardWindow = boardWindow;
    this.pgnHistory = pgnHistory;
    pieceMoveGenerator = new PieceMoveGenerator();
  }

  public void movePiece(BoardState board, Coordinates start, Coordinates end) {
    String specialMove = detectSpecialMove(board, start, end);
    PieceTypes type = board.getBoard()[start.getX()][start.getY()].getType();
    PlayerColor color = board.getBoard()[start.getX()][start.getY()].getColor();
    boolean isCapture = false;
    int specialNotation = -1;
    if (specialMove.equals(castling) && pgnHistory != null) {

      if (end.getY() == 6) {
        specialNotation = 0;
        log.info("passing short castling to notation..");
      } else {
        specialNotation = 1;
      }
    }

    if (board.getBoard()[end.getX()][end.getY()].getColor() == color.getOpposite()) {
      isCapture = true;
    }
    boolean isOverlap = doesNotationOverlap(board, end, start, type, color);
    if (pgnHistory != null) {
      pgnHistory.saveMove(start, end, type, specialNotation, isCapture, isOverlap);
    }
    board.setPiece(end, type, color);
    board.setPiece(start, PieceTypes.NONE, PlayerColor.NONE);
    if (boardWindow != null) {
      boardWindow.updateButton(end, type, color);
      boardWindow.updateButton(start, PieceTypes.NONE, PlayerColor.NONE);
    }

    if (specialMove.equals(castling)) {
      castle(board, end, color);
    } else if (specialMove.equals("enPassant")) {
      enPassant(board, end, color);
    }
    if (type == PieceTypes.KING) {
      if (color == PlayerColor.WHITE){
        board.setWhiteKingPosition(end);
      } else {
        board.setBlackKingPosition(end);
      }
    }
    endMoveCleanup(board, type, specialMove, end);
  }

  private String detectSpecialMove(BoardState board, Coordinates start, Coordinates end) {
    PieceTypes type = board.getBoard()[start.getX()][start.getY()].getType();
    if (type == PieceTypes.KING && Math.abs(start.getY() - end.getY()) > 1) {
      return castling;
    }
    if (type == PieceTypes.PAWN) {
      if (Math.abs(start.getX()) - end.getX() > 1) {
        return "doublePawn";
      }
      if (end.equals(board.getEnPassantCoordinates())) {
        return "enPassant";
      }
    }
    return "";
  }

  private void endMoveCleanup(
      BoardState board, PieceTypes type, String specialMove, Coordinates end) {
    if (specialMove.equals("doublePawn")) {
      int x = end.getX() - 1;
      if (end.getX() >= 4) {
        x = end.getX() + 1;
      }
      board.setEnPassantCoordinates(new Coordinates(x, end.getY()));
    } else {
      board.setEnPassantCoordinates(new Coordinates(-10, -10));
    }
    if (type == PieceTypes.ROOK || type == PieceTypes.KING) {
      if (board.getCurrentTurn() == PlayerColor.WHITE) {
        board.setCanWhiteCastle(false);
      } else {
        board.setCanBlackCastle(false);
      }
    }
    if (board.getCurrentTurn() == PlayerColor.WHITE) {
      whitePawnPromotionCheck(board, end, type);
      board.setCurrentTurn(PlayerColor.BLACK);
    } else {
      blackPawnPromotionCheck(board, end, type);
      board.setCurrentTurn(PlayerColor.WHITE);
    }
  }

  private void castle(BoardState board, Coordinates end, PlayerColor color) {
    if (end.equals(new Coordinates(7, 6))) {
      board.setPiece(new Coordinates(7, 5), PieceTypes.ROOK, color);
      board.setPiece(new Coordinates(7, 7), PieceTypes.NONE, PlayerColor.NONE);
      if (boardWindow != null) {
        boardWindow.updateButton(new Coordinates(7, 5), PieceTypes.ROOK, color);
        boardWindow.updateButton(new Coordinates(7, 7), PieceTypes.NONE, PlayerColor.NONE);
      }
    }
    if (end.equals(new Coordinates(7, 2))) {
      board.setPiece(new Coordinates(7, 3), PieceTypes.ROOK, color);
      board.setPiece(new Coordinates(7, 0), PieceTypes.NONE, PlayerColor.NONE);
      if (boardWindow != null) {
        boardWindow.updateButton(new Coordinates(7, 3), PieceTypes.ROOK, color);
        boardWindow.updateButton(new Coordinates(7, 0), PieceTypes.NONE, PlayerColor.NONE);
      }
    }
    if (end.equals(new Coordinates(0, 2))) {
      board.setPiece(new Coordinates(0, 3), PieceTypes.ROOK, color);
      board.setPiece(new Coordinates(0, 0), PieceTypes.NONE, PlayerColor.NONE);
      if (boardWindow != null) {
        boardWindow.updateButton(new Coordinates(0, 3), PieceTypes.ROOK, color);
        boardWindow.updateButton(new Coordinates(0, 0), PieceTypes.NONE, PlayerColor.NONE);
      }
    }
    if (end.equals(new Coordinates(0, 6))) {
      board.setPiece(new Coordinates(0, 5), PieceTypes.ROOK, color);
      board.setPiece(new Coordinates(0, 7), PieceTypes.NONE, PlayerColor.NONE);
      if (boardWindow != null) {
        boardWindow.updateButton(new Coordinates(0, 5), PieceTypes.ROOK, color);
        boardWindow.updateButton(new Coordinates(0, 7), PieceTypes.NONE, PlayerColor.NONE);
      }
    }
  }

  private void enPassant(BoardState board, Coordinates end, PlayerColor color) {
    int x = 0;
    if (color == PlayerColor.WHITE) {
      x = 3;
    } else if (color == PlayerColor.BLACK) {
      x = 4;
    }
    board.setPiece(new Coordinates(x, end.getY()), PieceTypes.NONE, PlayerColor.NONE);
    if (boardWindow != null) {
      boardWindow.updateButton(new Coordinates(x, end.getY()), PieceTypes.NONE, PlayerColor.NONE);
    }
  }

  private void whitePawnPromotionCheck(BoardState board, Coordinates end, PieceTypes type) {
    if (end.getX() == 0 && type == PieceTypes.PAWN && boardWindow != null) {
      PieceTypes chosenPiece = boardWindow.pawnPromotion();
      boardWindow.updateButton(end, chosenPiece, PlayerColor.WHITE);
      board.setPiece(end, chosenPiece, PlayerColor.WHITE);
    }
  }

  private void blackPawnPromotionCheck(BoardState board, Coordinates end, PieceTypes type) {
    if (end.getX() == 7 && type == PieceTypes.PAWN && boardWindow != null) {
      PieceTypes chosenPiece = boardWindow.pawnPromotion();
      boardWindow.updateButton(end, chosenPiece, PlayerColor.BLACK);
      board.setPiece(end, chosenPiece, PlayerColor.BLACK);
    }
  }
  private boolean doesNotationOverlap(BoardState board, Coordinates end, Coordinates start, PieceTypes type, PlayerColor color) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
          if (board.getBoard()[i][j].getColor() == color) {
            Coordinates position = new Coordinates(i, j);
            if (board.getBoard()[i][j].getType() == type && !position.equals(start)
                    && pieceMoveGenerator.generateMoves(board, position).contains(end)) {
              return true;
            }
          }
      }
    }
    return false;
  }
}
