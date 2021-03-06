package cz.cvut.fel.pjv;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * A test class which tests the correct functioning of the PieceMoveGenerator class
 * The tests compare manually inputted moves based on the given board and the moves generated by the generateMoves() method
 * The initial setup of the board and the pieceMoveGenerator is done in the @BeforeAll initialise() method
*/
class TestPieceMoveGenerator {
    private static BoardState board;
    private static PieceMoveGenerator pieceMoveGenerator;
    @BeforeAll
    static void initialise() {
        pieceMoveGenerator = new PieceMoveGenerator();
        board = new BoardState(true,true, -1, -1, false);
        board.setPiece(new Coordinates(6,1), PieceTypes.PAWN, PlayerColor.WHITE);
        board.setPiece(new Coordinates(5,0), PieceTypes.PAWN, PlayerColor.BLACK);

        board.setPiece(new Coordinates(3,1), PieceTypes.PAWN, PlayerColor.WHITE);
        board.setEnPassantCoordinates(new Coordinates(2,0));

        board.setPiece(new Coordinates(0,7), PieceTypes.QUEEN, PlayerColor.WHITE);
        board.setPiece(new Coordinates(1,7), PieceTypes.PAWN, PlayerColor.WHITE);
        board.setPiece(new Coordinates(2,5), PieceTypes.PAWN, PlayerColor.BLACK);
        board.setPiece(new Coordinates(0,4), PieceTypes.PAWN, PlayerColor.WHITE);

        board.setPiece(new Coordinates(7,4), PieceTypes.KING, PlayerColor.WHITE);
        board.setPiece(new Coordinates(7,7), PieceTypes.ROOK, PlayerColor.WHITE);

        board.setPiece(new Coordinates(4,6), PieceTypes.KNIGHT, PlayerColor.WHITE);

        board.setPiece(new Coordinates(2,2), PieceTypes.BISHOP, PlayerColor.WHITE);


    }

    @Test
    void testPawnMoves(){
        ArrayList<Coordinates> pawnMoves= new ArrayList<>();
        pawnMoves.add(new Coordinates(5,1));
        pawnMoves.add(new Coordinates(4,1));
        pawnMoves.add(new Coordinates(5,0));
        assertEquals(pawnMoves, pieceMoveGenerator.generateMoves(board, new Coordinates(6,1)));
    }
    @Test
    void testEnPassant() {
        ArrayList<Coordinates> enPassantMoves= new ArrayList<>();
        enPassantMoves.add(new Coordinates(2,0));
        enPassantMoves.add(new Coordinates(2,1));
        ArrayList<Coordinates> generatedMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, new Coordinates(3,1));
        assert(enPassantMoves.containsAll(generatedMoves) && generatedMoves.containsAll(enPassantMoves));
    }

    @Test
    void testQueenMoves() {
        ArrayList<Coordinates> queenMoves= new ArrayList<>();
        queenMoves.add(new Coordinates(1,6));
        queenMoves.add(new Coordinates(2,5));
        queenMoves.add(new Coordinates(0,5));
        queenMoves.add(new Coordinates(0,6));
        ArrayList<Coordinates> generatedMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, new Coordinates(0,7));
        assert(queenMoves.containsAll(generatedMoves) && generatedMoves.containsAll(queenMoves));
    }
    //Also tests for castling
    @Test
    void testKingMoves() {
        ArrayList<Coordinates> kingMoves= new ArrayList<>();
        kingMoves.add(new Coordinates(7,3));
        kingMoves.add(new Coordinates(7,5));
        kingMoves.add(new Coordinates(7,6));
        kingMoves.add(new Coordinates(6,5));
        kingMoves.add(new Coordinates(6,4));
        kingMoves.add(new Coordinates(6,3));
        ArrayList<Coordinates> generatedMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, new Coordinates(7,4));

        assert(kingMoves.containsAll(generatedMoves) && generatedMoves.containsAll(kingMoves));

    }
    @Test
    void testRookMoves() {
        ArrayList<Coordinates> rookMoves= new ArrayList<>();
        rookMoves.add(new Coordinates(7,6));
        rookMoves.add(new Coordinates(7,5));
        rookMoves.add(new Coordinates(6,7));
        rookMoves.add(new Coordinates(5,7));
        rookMoves.add(new Coordinates(4,7));
        rookMoves.add(new Coordinates(3,7));
        rookMoves.add(new Coordinates(2,7));

        ArrayList<Coordinates> generatedMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, new Coordinates(7,7));
        assert(rookMoves.containsAll(generatedMoves) && generatedMoves.containsAll(rookMoves));
    }
    @Test
    void testKnightMoves() {
        ArrayList<Coordinates> knightMoves= new ArrayList<>();
        knightMoves.add(new Coordinates(6,7));
        knightMoves.add(new Coordinates(6,5));
        knightMoves.add(new Coordinates(5,4));
        knightMoves.add(new Coordinates(3,4));
        knightMoves.add(new Coordinates(2,7));
        knightMoves.add(new Coordinates(2,5));

        ArrayList<Coordinates> generatedMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, new Coordinates(4,6));

        assert (knightMoves.containsAll(generatedMoves) && generatedMoves.containsAll(knightMoves));

    }
    @Test
    void testBishopMoves() {
        ArrayList<Coordinates> knightMoves = new ArrayList<>();
        knightMoves.add(new Coordinates(0,0));
        knightMoves.add(new Coordinates(1,1));
        knightMoves.add(new Coordinates(1,3));
        knightMoves.add(new Coordinates(3,3));
        knightMoves.add(new Coordinates(4,4));
        knightMoves.add(new Coordinates(5,5));
        knightMoves.add(new Coordinates(6,6));
        ArrayList<Coordinates> generatedMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, new Coordinates(2,2));

        assert(knightMoves.containsAll(generatedMoves) && generatedMoves.containsAll(knightMoves));
    }
    //Tests whether empty spaces have no moves
    @Test
    void testNullMoves() {
        ArrayList<Coordinates> nullMoves = new ArrayList<>();
        ArrayList<Coordinates> generatedMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, new Coordinates(0,0));
        assertEquals(generatedMoves, nullMoves);
    }
    //Tests whether only the player whose turn it is can make moves
    @Test
    void testOppositePlayerMoves() {
        ArrayList<Coordinates> blackMoves = new ArrayList<>();
        ArrayList<Coordinates> generatedMoves = (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, new Coordinates(5,1));
        assertEquals(blackMoves, generatedMoves);
    }
}
