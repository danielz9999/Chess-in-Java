package cz.cvut.fel.pjv;

import java.util.ArrayList;
import java.util.List;

public class PieceMoveGenerator {

    public List<Coordinates> generateMoves(BoardState board, Coordinates coordinates) {
        ArrayList<Coordinates> possibleMoves;
        possibleMoves = switch (board.getBoard()[coordinates.getX()][coordinates.getY()].getType()) {
            case KING -> generateKingMoves(board, coordinates);
            case QUEEN -> generateQueenMoves(board, coordinates);
            case PAWN -> generatePawnMoves(board, coordinates);
            case KNIGHT -> generateKnightMoves(board, coordinates);
            case BISHOP -> generateBishopMoves(board, coordinates);
            case ROOK -> generateRookMoves(board, coordinates);
            case NONE -> new ArrayList<>();
        };
        return possibleMoves;
    }
    private ArrayList<Coordinates> generateKingMoves(BoardState board, Coordinates coords) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        int x = coords.getX();
        int y = coords.getY();
        PlayerColor color = board.getBoard()[x][y].getColor();

        possibleMoves.add(new Coordinates(x+1, y+1));
        possibleMoves.add(new Coordinates(x+1, y-1));
        possibleMoves.add(new Coordinates(x-1, y+1));
        possibleMoves.add(new Coordinates(x-1, y-1));
        possibleMoves.add(new Coordinates(x+1, y));
        possibleMoves.add(new Coordinates(x-1, y));
        possibleMoves.add(new Coordinates(x, y+1));
        possibleMoves.add(new Coordinates(x, y-1));
        possibleMoves.removeIf(move -> move.getX() < 0 || move.getX() > 7 || move.getY() < 0 || move.getY() > 7);
        possibleMoves.removeIf(move -> board.getBoard()[move.getX()][move.getY()].getColor() == color);
        //Castling check
        if (color == PlayerColor.WHITE) {
            possibleMoves.addAll(whiteCastlingCheck(board));
        } else if (color == PlayerColor.BLACK) {
            possibleMoves.addAll(blackCastlingCheck(board));
        }
        return possibleMoves;
    }
    private ArrayList<Coordinates> generateQueenMoves(BoardState board, Coordinates coords) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        int x = coords.getX();
        int y = coords.getY();
        PlayerColor color = board.getBoard()[x][y].getColor();
        Coordinates[] directions = new Coordinates[8];
        directions[0] = new Coordinates(1,0);
        directions[1] = new Coordinates(0,-1);
        directions[2] = new Coordinates(-1,0);
        directions[3] = new Coordinates(0,1);
        directions[4] = new Coordinates(1,1);
        directions[5] = new Coordinates(1,-1);
        directions[6] = new Coordinates(-1,1);
        directions[7] = new Coordinates(-1,-1);
        for (Coordinates dir : directions) {
            int a = x + dir.getX();
            int b = y + dir.getY();
            while (a >= 0 && a <= 7 && b >= 0 && b <= 7) {
                if (board.getBoard()[a][b].getColor() == color) {
                    break;
                } else if (board.getBoard()[a][b].getColor() == PlayerColor.NONE) {
                    possibleMoves.add(new Coordinates(a,b));
                } else {
                    possibleMoves.add(new Coordinates(a,b));
                    break;
                }
                a += dir.getX();
                b += dir.getY();
            }
        }
        return possibleMoves;
    }

    private ArrayList<Coordinates> generatePawnMoves(BoardState board, Coordinates coords) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        int x = coords.getX();
        int y = coords.getY();
        PlayerColor color = board.getBoard()[x][y].getColor();
        if (color == PlayerColor.WHITE) {
            if (board.getBoard()[x-1][y].getColor() == PlayerColor.NONE) {
                possibleMoves.add(new Coordinates(x-1,y));
            }
            if (board.getBoard()[x-1][y+1].getColor() == PlayerColor.BLACK) {
                possibleMoves.add(new Coordinates(x-1,y+1));
            }
            if (board.getBoard()[x-1][y-1].getColor() == PlayerColor.BLACK) {
                possibleMoves.add(new Coordinates(x-1,y-1));
            }
        } else if (color == PlayerColor.BLACK) {
            if (board.getBoard()[x+1][y].getColor() == PlayerColor.NONE) {
                possibleMoves.add(new Coordinates(x-1,y));
            }
            if (board.getBoard()[x+1][y+1].getColor() == PlayerColor.WHITE) {
                possibleMoves.add(new Coordinates(x-1,y+1));
            }
            if (board.getBoard()[x+1][y-1].getColor() == PlayerColor.WHITE) {
                possibleMoves.add(new Coordinates(x-1,y-1));
            }
        }
        return possibleMoves;
    }
    private ArrayList<Coordinates> generateKnightMoves(BoardState board, Coordinates coords) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        int x = coords.getX();
        int y = coords.getY();
        PlayerColor color = board.getBoard()[x][y].getColor();

        possibleMoves.add(new Coordinates(x+2, y+1));
        possibleMoves.add(new Coordinates(x+2, y-1));
        possibleMoves.add(new Coordinates(x-2, y+1));
        possibleMoves.add(new Coordinates(x-2, y-1));
        possibleMoves.add(new Coordinates(x+1, y+2));
        possibleMoves.add(new Coordinates(x+1, y-2));
        possibleMoves.add(new Coordinates(x-1, y+2));
        possibleMoves.add(new Coordinates(x-1, y-2));
        possibleMoves.removeIf(move -> move.getX() < 0 || move.getX() > 7 || move.getY() < 0 || move.getY() > 7);
        possibleMoves.removeIf(move -> board.getBoard()[move.getX()][move.getY()].getColor() == color);
        return possibleMoves;
    }
    private ArrayList<Coordinates> generateBishopMoves(BoardState board, Coordinates coords) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        int x = coords.getX();
        int y = coords.getY();
        PlayerColor color = board.getBoard()[x][y].getColor();
        Coordinates[] directions = new Coordinates[4];
        directions[0] = new Coordinates(1,1);
        directions[1] = new Coordinates(1,-1);
        directions[2] = new Coordinates(-1,1);
        directions[3] = new Coordinates(-1,-1);
        for (Coordinates dir : directions) {
            int a = x + dir.getX();
            int b = y + dir.getY();
            while (a >= 0 && a <= 7 && b >= 0 && b <= 7) {
                if (board.getBoard()[a][b].getColor() == color) {
                    break;
                } else if (board.getBoard()[a][b].getColor() == PlayerColor.NONE) {
                    possibleMoves.add(new Coordinates(a,b));
                } else {
                    possibleMoves.add(new Coordinates(a,b));
                    break;
                }
                a += dir.getX();
                b += dir.getY();
            }
        }
        return possibleMoves;
    }
    private ArrayList<Coordinates> generateRookMoves(BoardState board, Coordinates coords) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        int x = coords.getX();
        int y = coords.getY();
        PlayerColor color = board.getBoard()[x][y].getColor();
        Coordinates[] directions = new Coordinates[4];
        directions[0] = new Coordinates(1,0);
        directions[1] = new Coordinates(0,-1);
        directions[2] = new Coordinates(-1,0);
        directions[3] = new Coordinates(0,1);
        for (Coordinates dir : directions) {
            int a = x + dir.getX();
            int b = y + dir.getY();
            while (a >= 0 && a <= 7 && b >= 0 && b <= 7) {
                if (board.getBoard()[a][b].getColor() == color) {
                    break;
                } else if (board.getBoard()[a][b].getColor() == PlayerColor.NONE) {
                    possibleMoves.add(new Coordinates(a,b));
                } else {
                    possibleMoves.add(new Coordinates(a,b));
                    break;
                }
                a += dir.getX();
                b += dir.getY();
            }
        }
        return possibleMoves;
    }
    private ArrayList<Coordinates> whiteCastlingCheck(BoardState board) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        if (board.isCanWhiteCastle()) {
            boolean clearPath = true;
            for (int i = 5; i < 7; i++) {
                clearPath = (board.getBoard()[7][i].getColor() == PlayerColor.NONE);
            }
            if (clearPath) {
                possibleMoves.add(new Coordinates(7,6));
            }
            clearPath = true;
            for (int i = 1; i < 4; i++) {
                clearPath = (board.getBoard()[7][i].getColor() == PlayerColor.NONE);
            }
            if (clearPath) {
                possibleMoves.add(new Coordinates(7,2));
            }
        }
        return possibleMoves;
    }
    private ArrayList<Coordinates> blackCastlingCheck(BoardState board) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        if (board.isCanBlackCastle()) {
            boolean clearPath = true;
            for (int i = 5; i < 7; i++) {
                clearPath = (board.getBoard()[0][i].getColor() == PlayerColor.NONE);
            }
            if (clearPath) {
                possibleMoves.add(new Coordinates(0,6));
            }
            clearPath = true;
            for (int i = 1; i < 4; i++) {
                clearPath = (board.getBoard()[0][i].getColor() == PlayerColor.NONE);
            }
            if (clearPath) {
                possibleMoves.add(new Coordinates(0,2));
            }
        }
        return possibleMoves;
    }
}
