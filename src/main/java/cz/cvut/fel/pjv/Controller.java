package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.View;

// The controller part of the MVC model
public class Controller {
  private BoardState board;
  private Timer timer;
  private View view;
  private GameRules gameRules = null;

  public Controller() {
    view = new View(this);
    view.mainMenu();
  }

  public void startGame(BoardState board) {
    this.board = board;
    timer = new Timer(board.getWhiteTime(), board.getBlackTime());
    view.boardWindow(board);


  }

  public boolean firstClick(Coordinates coordinates) {
    if (gameRules == null) {
      gameRules = new GameRules(board, view.getBoardWindow());
    }
    return gameRules.firstClick(coordinates);
  }

  public boolean secondClick(Coordinates coordinates) {
    return gameRules.secondClick(coordinates);
  }
}
