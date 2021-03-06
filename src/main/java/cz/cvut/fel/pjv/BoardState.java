package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.pieces.*;
/**
 * A class that represents what is happenign on the chessboard currently, and in general holds most of the data
 * A constructor and the getters and setters
 */
public class BoardState {
    private final Piece[][] board = new Piece[8][8];
    private boolean canWhiteCastle;
    private boolean canBlackCastle;
    private int whiteTime;
    private int blackTime;
    private Coordinates enPassantCoordinates;
    private PlayerColor currentTurn;
    private PlayerColor botPlayerColor;

    private Coordinates whiteKingPosition;
    private Coordinates blackKingPosition;

    public BoardState(boolean canWhiteCastle, boolean canBlackCastle, int whiteTime, int blackTime, boolean standardSetup) {
        this.canWhiteCastle = canWhiteCastle;
        this.canBlackCastle = canBlackCastle;
        this.whiteTime = whiteTime;
        this.blackTime = blackTime;
        this.enPassantCoordinates = new Coordinates(-1,-1);
        this.currentTurn = PlayerColor.WHITE;
        if (standardSetup) {
            standardSetup();
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = new NullPiece();
                }
            }
        }
    }

    //The board setup for a standard game of chess
    void standardSetup() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(PlayerColor.BLACK);
            board[6][i] = new Pawn(PlayerColor.WHITE);
        }
        board[0][0] = new Rook(PlayerColor.BLACK);
        board[0][1] = new Knight(PlayerColor.BLACK);
        board[0][2] = new Bishop(PlayerColor.BLACK);
        board[0][3] = new Queen(PlayerColor.BLACK);
        board[0][4] = new King(PlayerColor.BLACK);
        board[0][5] = new Bishop(PlayerColor.BLACK);
        board[0][6] = new Knight(PlayerColor.BLACK);
        board[0][7] = new Rook(PlayerColor.BLACK);

        board[7][0] = new Rook(PlayerColor.WHITE);
        board[7][1] = new Knight(PlayerColor.WHITE);
        board[7][2] = new Bishop(PlayerColor.WHITE);
        board[7][3] = new Queen(PlayerColor.WHITE);
        board[7][4] = new King(PlayerColor.WHITE);
        board[7][5] = new Bishop(PlayerColor.WHITE);
        board[7][6] = new Knight(PlayerColor.WHITE);
        board[7][7] = new Rook(PlayerColor.WHITE);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                board[i+2][j] = new NullPiece();
            }
        }
        setWhiteKingPosition(new Coordinates(7,4));
        setBlackKingPosition(new Coordinates(0,4));
    }

    public boolean isCanWhiteCastle() {
        return canWhiteCastle;
    }

    public void setCanWhiteCastle(boolean canWhiteCastle) {
        this.canWhiteCastle = canWhiteCastle;
    }

    public boolean isCanBlackCastle() {
        return canBlackCastle;
    }

    public void setCanBlackCastle(boolean canBlackCastle) {
        this.canBlackCastle = canBlackCastle;
    }

    public int getWhiteTime() {
        return whiteTime;
    }

    public int getBlackTime() {
        return blackTime;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setPiece(Coordinates coords, PieceTypes type, PlayerColor color) {
        Piece temp;
        temp = switch(type) {
            case KING -> new King(color);
            case QUEEN -> new Queen(color);
            case PAWN -> new Pawn(color);
            case BISHOP -> new Bishop(color);
            case ROOK -> new Rook(color);
            case KNIGHT -> new Knight(color);
            case NONE -> new NullPiece();
        };
        board[coords.getX()][coords.getY()] = temp;
    }

    public Coordinates getEnPassantCoordinates() {
        return enPassantCoordinates;
    }

    public void setEnPassantCoordinates(Coordinates enPassantCoordinates) {
        this.enPassantCoordinates = enPassantCoordinates;
    }

    public PlayerColor getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(PlayerColor currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Coordinates getWhiteKingPosition() {
        return whiteKingPosition;
    }

    public void setWhiteKingPosition(Coordinates whiteKingPosition) {
        this.whiteKingPosition = whiteKingPosition;
    }

    public Coordinates getBlackKingPosition() {
        return blackKingPosition;
    }

    public void setBlackKingPosition(Coordinates blackKingPosition) {
        this.blackKingPosition = blackKingPosition;
    }

    public PlayerColor getBotPlayerColor() {
        return botPlayerColor;
    }

    public void setBotPlayerColor(PlayerColor botPlayerColor) {
        this.botPlayerColor = botPlayerColor;
    }
}


