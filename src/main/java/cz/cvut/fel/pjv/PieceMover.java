package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.BoardWindow;

public class PieceMover {
  private final BoardWindow boardWindow;


  public PieceMover(BoardWindow boardWindow) {
    this.boardWindow = boardWindow;
  }

  public void movePiece(BoardState board, Coordinates start, Coordinates end) {
    String specialMove = detectSpecialMove(board, start, end);
    PieceTypes type = board.getBoard()[start.getX()][start.getY()].getType();
    PlayerColor color = board.getBoard()[start.getX()][start.getY()].getColor();

    board.setPiece(end, type, color);
    board.setPiece(start, PieceTypes.NONE, PlayerColor.NONE);
    if (boardWindow != null) {
      boardWindow.updateButton(end, type, color);
      boardWindow.updateButton(start, PieceTypes.NONE, PlayerColor.NONE);
    }

    if (specialMove.equals("castling")) {
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
      return "castling";
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
      board.setCurrentTurn(PlayerColor.BLACK);
    } else {
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
}
