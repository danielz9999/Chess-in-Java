package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.pieces.*;
//A data class that keeps all necessary info to start a game from a certain board state
public class BoardState {
    private boolean canWhiteCastle;
    private boolean canBlackCastle;

    private int whiteTime;
    private int blackTime;

    private Piece[][] board = new Piece[8][8];

    public BoardState(boolean hasWhiteCastled, boolean hasBlackCastled, int whiteTime, int blackTime, boolean standardSetup) {
        this.canWhiteCastle = hasWhiteCastled;
        this.canBlackCastle = hasBlackCastled;
        this.whiteTime = whiteTime;
        this.blackTime = blackTime;

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
            board[1][i] = new Pawn(Color.BLACK);
            board[6][i] = new Pawn(Color.WHITE);
        }
        board[0][0] = new Rook(Color.BLACK);
        board[0][1] = new Knight(Color.BLACK);
        board[0][2] = new Bishop(Color.BLACK);
        board[0][3] = new Queen(Color.BLACK);
        board[0][4] = new King(Color.BLACK);
        board[0][5] = new Bishop(Color.BLACK);
        board[0][6] = new Knight(Color.BLACK);
        board[0][7] = new Rook(Color.BLACK);

        board[7][0] = new Rook(Color.WHITE);
        board[7][1] = new Knight(Color.WHITE);
        board[7][2] = new Bishop(Color.WHITE);
        board[7][3] = new Queen(Color.WHITE);
        board[7][4] = new King(Color.WHITE);
        board[7][5] = new Bishop(Color.WHITE);
        board[7][6] = new Knight(Color.WHITE);
        board[7][7] = new Rook(Color.WHITE);
    }

    public boolean isCanWhiteCastle() {
        return canWhiteCastle;
    }

    public boolean isCanBlackCastle() {
        return canBlackCastle;
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
    /*
    public void setPiece(Coordinates coords, PieceTypes type, Color color) {
        switch(type) {
            case KING -> board[coords.getX()][coords.getY()] = new King(color);
            case QUEEN -> board[coords.getX()][coords.getY()] = new Queen(color);
            case PAWN -> board[coords.getX()][coords.getY()] = new Pawn(color);
            case BISHOP -> board[coords.getX()][coords.getY()] = new Bishop(color);
            case ROOK -> board[coords.getX()][coords.getY()] = new Rook(color);
            case KNIGHT -> board[coords.getX()][coords.getY()] = new Knight(color);
            case NONE -> board[coords.getX()][coords.getY()] = new NullPiece();
        }
    }
"
"     */
    public void setPiece(Coordinates coords, PieceTypes type, Color color) {
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
}


