package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.BoardState;
import cz.cvut.fel.pjv.Controller;

//The main class overlooking the graphical displaying and user interfacing
//The View part of the MVC model
public class View {
  private MainMenu mainMenu;
  private BoardWindow boardWindow;
  private TimerWindow timerWindow;
  private final Controller controller;
  public View(Controller controller) {
    this.controller = controller;
  }

  public void buttonUpdate() {}


  public void mainMenu() {
    mainMenu = new MainMenu(controller);
  }
  public void boardWindow(BoardState boardState) {
    BoardWindow boardWindow = new BoardWindow(boardState, controller);
  }

}
