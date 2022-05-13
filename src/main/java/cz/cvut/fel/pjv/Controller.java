package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.View;

// The controller part of the MVC model
public class Controller {
  private BoardState board;
  private Timer timer;
  private final View view;
  private GameRules gameRules = null;
  private PGNHistory pgnHistory;
  private FileSaver fileSaver;


  public Controller() {
    view = new View(this);
    view.mainMenu();
  }

  public void startGame(BoardState board) {
    this.board = board;
    view.boardWindow(board);
    timer = new Timer(board.getWhiteTime(), board.getBlackTime(), this);
    timer.start();
    pgnHistory = new PGNHistory();
    gameRules = new GameRules(board, view.getBoardWindow(), this, pgnHistory);
    fileSaver = new FileSaver(pgnHistory);

  }

  public boolean firstClick(Coordinates coordinates) {
    if (gameRules == null) {
      gameRules = new GameRules(board, view.getBoardWindow(), this, pgnHistory);
    }
    return gameRules.firstClick(coordinates);
  }

  public void secondClick(Coordinates coordinates) {
   gameRules.secondClick(coordinates);
  }
  public void changeTurn() {
    timer.changeTurn();
  }
  public void gameEnd(PlayerColor color) {
    view.getBoardWindow().gameEnd(true, color);
  }
  public void saveGame() {
      fileSaver.savePGN();
  }
}
