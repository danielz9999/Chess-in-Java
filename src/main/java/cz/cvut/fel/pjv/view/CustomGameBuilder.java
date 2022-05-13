package cz.cvut.fel.pjv.view;


import cz.cvut.fel.pjv.BoardState;
import cz.cvut.fel.pjv.Coordinates;
import cz.cvut.fel.pjv.PieceTypes;
import cz.cvut.fel.pjv.PlayerColor;
import cz.cvut.fel.pjv.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A GUI class the takes care of custom game setups
 * The user is shown an 8x8  grid of JButtons, similar to the normal playing board of the BoardWindow class
 * Then by left-clicking the board, the user can place the piece in whichever positions they want
 * Right-clicking brings up a menu to finish the setup and begin the game
 * Takes @Param mainMenu so it can return there after finishing the setup
 */
public class CustomGameBuilder {
    private final MainMenu mainMenu;
    private final Piece[][] chessBoard = new Piece[8][8];
    private final Logger log = Logger.getLogger(CustomGameBuilder.class.getName());
    private boolean isWhiteKingPlaced = false;
    private boolean isBlackKingPlaced = false;
    private JButton[][] board = new JButton[8][8];
    private int whiteTime;
    private int blackTime;
    private JFrame frame;

    public CustomGameBuilder(MainMenu mainMenu, int whiteTime, int blackTime) {
        log.setLevel(Level.ALL);
        this.mainMenu = mainMenu;
        this.blackTime = blackTime;
        this.whiteTime = whiteTime;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j] = new NullPiece();
            }
        }
    }
    //Sets up the board
    public void customGameInit() {
        JOptionPane.showMessageDialog(null, "Left click to place pieces, right click to finish placing pieces",
                                "Custom game info", JOptionPane.PLAIN_MESSAGE);
        frame = new JFrame("Chess");
        frame.setSize(800,800);
        frame.setLayout(new GridLayout(8,8));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int a = i;
                int b = j;
                board[i][j] = new JButton();
                if (((i+j) % 2) == 0) {
                    board[i][j].setBackground(Color.WHITE);
                } else {
                    board[i][j].setBackground(Color.lightGray);
                }
                board[i][j].setFont(new Font("Arial Unicode MS", Font.PLAIN, 62));
                board[i][j].addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonClicked(new Coordinates(a, b));
                    }
                });
                board[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            rightClick();
                        }
                    }
                });
                frame.add(board[i][j]);
            }
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    //Brings up two dropdown menus to choose color and type of piece
    private void buttonClicked(Coordinates coordinates) {
        ArrayList<PieceTypes> pieces = new ArrayList<>();
        pieces.add(PieceTypes.PAWN);
        pieces.add(PieceTypes.BISHOP);
        pieces.add(PieceTypes.KNIGHT);
        pieces.add(PieceTypes.ROOK);
        pieces.add(PieceTypes.KING);
        pieces.add(PieceTypes.QUEEN);
        PlayerColor color;
        PieceTypes type = PieceTypes.NONE;

        PlayerColor[] colors = {PlayerColor.WHITE, PlayerColor.BLACK};
        color = (PlayerColor) JOptionPane.showInputDialog(null, "Choose which color of piece to place",
                "Color choice dialog", JOptionPane.QUESTION_MESSAGE, null,
                colors, colors[0]);
        if (color != null) {
            if (color == PlayerColor.WHITE && isWhiteKingPlaced || color == PlayerColor.BLACK && isBlackKingPlaced) {
                pieces.remove(PieceTypes.KING);
            }
            PieceTypes[] pieceArray = pieces.toArray(new PieceTypes[0]);
            type = (PieceTypes) JOptionPane.showInputDialog(null, "Choose which piece to place",
                    "Piece type choice dialog", JOptionPane.QUESTION_MESSAGE, null,
                    pieceArray, pieceArray[0]);
        }
        if (color == null || type == null) {
            log.fine("Custom piece placment cancelled");
        } else {
            createPiece(coordinates, type, color);
        }
        refreshKingConditions();



    }
    //Both adds the piece to the "logic" chessboard and displays the immediate change on the clicked JButton
    private void createPiece(Coordinates coordinates, PieceTypes type, PlayerColor color) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        Piece temp = new NullPiece();
        if (color == PlayerColor.WHITE) {
            switch (type) {
                case QUEEN -> {temp = new Queen(color);
                                board[x][y].setText("♕");}
                case KNIGHT -> {
                    temp = new Knight(color);
                    board[x][y].setText("♘");}

                case BISHOP -> {
                    temp = new Bishop(color);
                    board[x][y].setText("♗");
                }
                case ROOK -> {
                    temp = new Rook(color);
                    board[x][y].setText("♖");
                }
                case PAWN -> {
                    temp = new Pawn(color);
                    board[x][y].setText("♙");
                }
                case KING -> {
                    temp = new King(color);
                    board[x][y].setText("♔");
                }
                case NONE -> {
                    temp = new NullPiece();
                    board[x][y].setText("");
                }
            }
        } else {
            switch (type) {
                case QUEEN -> {temp = new Queen(color);
                    board[x][y].setText("♛");}
                case KNIGHT -> {
                    temp = new Knight(color);
                    board[x][y].setText("♞");}

                case BISHOP -> {
                    temp = new Bishop(color);
                    board[x][y].setText("♝");
                }
                case ROOK -> {
                    temp = new Rook(color);
                    board[x][y].setText("♜");
                }
                case PAWN -> {
                    temp = new Pawn(color);
                    board[x][y].setText("♟");
                }
                case KING -> {
                    temp = new King(color);
                    board[x][y].setText("♚");
                }
                case NONE -> {
                    temp = new NullPiece();
                    board[x][y].setText("");
                }
            }
        }

        chessBoard[x][y] = temp;
    }
    private void rightClick() {
        int botPlayChoice =
                JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to finish placing pieces and begin the game?",
                        "Finish placing dialog",
                        JOptionPane.YES_NO_OPTION);
        if (botPlayChoice == JOptionPane.YES_OPTION) {
            if (!isBlackKingPlaced || !isWhiteKingPlaced) {
                JOptionPane.showMessageDialog(null, "Both kings need to be placed before starting the game!",
                        "Not enough kings", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mainMenu.customGameFinishSetup(generateBoardstate());
            frame.setVisible(false);
            frame.dispose();
        }
    }
    private BoardState generateBoardstate() {
        BoardState customBoard = new BoardState(false, false, whiteTime,blackTime,false);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                customBoard.getBoard()[i][j] = chessBoard[i][j];
            }
        }
        if (customBoard.getBoard()[7][4].getType() == PieceTypes.KING && customBoard.getBoard()[7][4].getColor() == PlayerColor.WHITE
                && (customBoard.getBoard()[7][7].getType() == PieceTypes.ROOK || customBoard.getBoard()[7][0].getType() == PieceTypes.ROOK)) {
            customBoard.setCanWhiteCastle(true);
        }
        if (customBoard.getBoard()[0][4].getType() == PieceTypes.KING && customBoard.getBoard()[0][4].getColor() == PlayerColor.BLACK
                && (customBoard.getBoard()[0][7].getType() == PieceTypes.ROOK || customBoard.getBoard()[0][0].getType() == PieceTypes.ROOK)) {
            customBoard.setCanBlackCastle(true);
        }
        return customBoard;
    }
    private void refreshKingConditions() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].getType() == PieceTypes.KING) {
                    if (chessBoard[i][j].getColor() == PlayerColor.WHITE) {
                        isWhiteKingPlaced = true;
                    } else {
                        isBlackKingPlaced = true;
                    }
                }
            }
        }
    }
}
