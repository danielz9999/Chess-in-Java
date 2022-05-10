package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.view.BoardWindow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class TestPieceMover {
  private static final Logger log = Logger.getLogger(TestPieceMover.class.getName());
  private static PieceMover pieceMover;
  private static BoardState board;

  @BeforeAll
  static void initialise() {
    board = new BoardState(true, true, -1, -1, true);
    BoardWindow boardWindow = new BoardWindow(board, null );
    pieceMover = new PieceMover(boardWindow);
    log.setLevel(Level.FINEST);

  }

  @Test
  void testBasicMove() {
    Piece movedPiece = board.getBoard()[0][1];
    pieceMover.movePiece(board, new Coordinates(0,1), new Coordinates(2,0));

    Assertions.assertAll(
            "basic knight move",
            () -> assertEquals(movedPiece, board.getBoard()[2][0]),
            () -> assertSame(PieceTypes.NONE, board.getBoard()[0][1].getType()),
            () -> assertSame(PlayerColor.NONE, board.getBoard()[0][1].getColor())

    );

  }

  @Test
  void testPawnDoubleMove() {
    Piece movedPiece = board.getBoard()[6][0];

    pieceMover.movePiece(board, new Coordinates(6, 0), new Coordinates(4, 0));

    Assertions.assertAll(
        "pawn moves",
        () -> assertEquals(movedPiece, board.getBoard()[4][0]),
        () -> assertSame(PieceTypes.NONE, board.getBoard()[6][0].getType()),
        () -> assertSame(PlayerColor.NONE, board.getBoard()[6][0].getColor()),
        () -> assertEquals( new Coordinates(5, 0), board.getEnPassantCoordinates()));
  }

  @Test
  void testCastling() {
    board = new BoardState(true, true, -1,-1,false);
    board.setPiece(new Coordinates(7,4), PieceTypes.KING, PlayerColor.WHITE);
    board.setPiece(new Coordinates(7,7), PieceTypes.ROOK, PlayerColor.WHITE);

    Piece movedKing = board.getBoard()[7][4];
    Piece movedRook = board.getBoard()[7][7];

    pieceMover.movePiece(board, new Coordinates(7,4), new Coordinates(7,6));
    Assertions.assertAll(
            "castling",
            () -> assertEquals(movedKing, board.getBoard()[7][6]),
            () -> assertSame(PieceTypes.NONE, board.getBoard()[7][4].getType()),
            () -> assertSame(PlayerColor.NONE, board.getBoard()[7][4].getColor()),

            () -> assertEquals( movedRook, board.getBoard()[7][5]),
            () -> assertSame(PieceTypes.NONE, board.getBoard()[7][7].getType()),
            () -> assertSame(PlayerColor.NONE, board.getBoard()[7][7].getColor()),

            () -> assertFalse(board.isCanWhiteCastle())

    );

  }

  @Test
  void testEnPassant() {
    board = new BoardState(true, true, -1,-1,false);
    board.setPiece(new Coordinates(3,1), PieceTypes.PAWN, PlayerColor.WHITE);
    board.setPiece(new Coordinates(3,0), PieceTypes.PAWN, PlayerColor.BLACK);
    board.setEnPassantCoordinates(new Coordinates(2,0));

    Piece movedPiece = board.getBoard()[3][1];

    pieceMover.movePiece(board, new Coordinates(3,1), new Coordinates(2,0));

    Assertions.assertAll(
            "enpassant move",
            () -> assertEquals(movedPiece, board.getBoard()[2][0]),
            () -> assertSame(PieceTypes.NONE, board.getBoard()[3][1].getType()),
            () -> assertSame(PlayerColor.NONE, board.getBoard()[3][1].getColor()),

            () -> assertSame(PieceTypes.NONE, board.getBoard()[3][0].getType()),
            () -> assertSame(PlayerColor.NONE, board.getBoard()[3][0].getColor())

    );
  }
}

