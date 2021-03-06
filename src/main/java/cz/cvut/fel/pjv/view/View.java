package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.BoardState;
import cz.cvut.fel.pjv.Controller;

/**
 * The main class overlooking the graphical part of the program
 * the View part of the MVC model
 */
public class View {
  private final Controller controller;
  private MainMenu mainMenu;
  private BoardWindow boardWindow;

  public View(Controller controller) {
    this.controller = controller;
  }

  public BoardWindow getBoardWindow() {
    return boardWindow;
  }

  public void mainMenu() {
    mainMenu = new MainMenu(controller);
  }

  public void boardWindow(BoardState boardState) {
    boardWindow = new BoardWindow(boardState, controller);
    mainMenu.close();
  }
}
