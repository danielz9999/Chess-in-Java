package cz.cvut.fel.pjv.view;
//The main class overlooking the graphical displaying and user interfacing
//The View part of the MVC model
public class View {
  private final MainMenu mainMenu;
  private BoardWindow boardWindow;
  private TimerWindow timerWindow;

  public View() {
    mainMenu = new MainMenu();
  }

    public void buttonUpdate() {}

  public MainMenu getMainMenu() {
    return mainMenu;
  }

  public BoardWindow getBoardWindow() {
    return boardWindow;
  }

  public TimerWindow getTimerWindow() {
    return timerWindow;
  }
}
