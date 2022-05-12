package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.*;
import cz.cvut.fel.pjv.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//A class that implements a windows in which the chess board is
public class BoardWindow {
  private final JButton[][] board = new JButton[8][8];
    private JFrame frame;
    private final Controller controller;
    private boolean isFirstClick = true;



    public BoardWindow(BoardState board, Controller controller) {
        this.controller = controller;
        initialise();
        drawPieces(board);
    }
    private void initialise() {
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
                        frame.add(board[i][j]);
            }
        }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void drawPieces(BoardState boardState) {
        Piece[][] pieceBoard = boardState.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].setText("");
               if (pieceBoard[i][j].getColor() == PlayerColor.WHITE) {
                   switch (pieceBoard[i][j].getType()) {
                       case KNIGHT -> board[i][j].setText("♘");
                       case KING -> board[i][j].setText("♔");
                       case PAWN -> board[i][j].setText("♙");
                       case ROOK -> board[i][j].setText("♖");
                       case QUEEN -> board[i][j].setText("♕");
                       case BISHOP -> board[i][j].setText("♗");
                       case NONE -> board[i][j].setText("");
                   }

               } else if (pieceBoard[i][j].getColor() == PlayerColor.BLACK) {
                   switch (pieceBoard[i][j].getType()) {
                       case KNIGHT -> board[i][j].setText("♞");
                       case KING -> board[i][j].setText("♚");
                       case PAWN -> board[i][j].setText("♟");
                       case ROOK -> board[i][j].setText("♜");
                       case QUEEN -> board[i][j].setText("♛");
                       case BISHOP -> board[i][j].setText("♝");
                       case NONE -> board[i][j].setText("");
                   }
               }
            }
        }
    }
    public void updateButton(Coordinates coordinates, PieceTypes type, PlayerColor color) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        if (color == PlayerColor.WHITE) {
            switch (type) {
                case KNIGHT -> board[x][y].setText("♘");
                case KING -> board[x][y].setText("♔");
                case PAWN -> board[x][y].setText("♙");
                case ROOK -> board[x][y].setText("♖");
                case QUEEN -> board[x][y].setText("♕");
                case BISHOP -> board[x][y].setText("♗");
                case NONE -> board[x][y].setText(" ");
            }
        } else if (color == PlayerColor.BLACK) {
            switch (type) {
                case KNIGHT -> board[x][y].setText("♞");
                case KING -> board[x][y].setText("♚");
                case PAWN -> board[x][y].setText("♟");
                case ROOK -> board[x][y].setText("♜");
                case QUEEN -> board[x][y].setText("♛");
                case BISHOP -> board[x][y].setText("♝");
                case NONE -> board[x][y].setText(" ");
            }
        } else {
            board[x][y].setText(" ");
        }

    }
    public void highlightButton(Coordinates coordinates) {
        board[coordinates.getX()][coordinates.getY()].setBackground(Color.DARK_GRAY);
    }
    public void dehighlightButton(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        if (((x + y) % 2) == 0) {
            board[x][y].setBackground(Color.WHITE);
        } else {
            board[x][y].setBackground(Color.lightGray);
        }
    }
    private void buttonClicked(Coordinates coordinates) {
        if (isFirstClick) {
            boolean ret = controller.firstClick(coordinates);
            isFirstClick = !ret;
        } else {
            controller.secondClick(coordinates);
            isFirstClick = true;
        }
    }
    public PieceTypes pawnPromotion() {
        PieceTypes[] option = {PieceTypes.PAWN, PieceTypes.ROOK, PieceTypes.BISHOP, PieceTypes.QUEEN, PieceTypes.KNIGHT};
        return (PieceTypes) JOptionPane.showInputDialog(null, "Choose to which piece to promote pawn",
                                                        "Pawn promotion dialog", JOptionPane.QUESTION_MESSAGE, null,
                                                            option, option[0]);
    }
    public void gameEnd(Boolean isCheckmate, PlayerColor player) {

        if (Boolean.FALSE.equals(isCheckmate)) {
            JOptionPane.showMessageDialog(frame, "The game has ended in a draw", "Game end message", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }

        JOptionPane.showMessageDialog(frame, player.name() + " player has won the game!", "Game end message", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }
}
