package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.View;

//The controller part of the MVC model
public class Controller {
    private BoardState board;
    private Timer timer;
    private View view;
    private GameRules gameRules;


    public Controller() {
        view = new View(this);
        view.mainMenu();
    }

    public void startGame(BoardState board) {
        this.board = board;
        timer = new Timer(board.getWhiteTime(), board.getBlackTime());
        gameRules = new GameRules(board);
        view.boardWindow(board);
    }
    public boolean firstClick(Coordinates coordinates) {
        return gameRules.firstClick(coordinates);
    }
    public void secondClick(Coordinates coordinates) {

    }


}
