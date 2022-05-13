package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.TimerWindow;

import static cz.cvut.fel.pjv.PlayerColor.BLACK;
import static cz.cvut.fel.pjv.PlayerColor.WHITE;

/**
 * A timer running on a separate thread to the rest of the application
 * Updates time through the System.currentTimeMillis() method
 * Maintains the logic of the chess clock and the TimerWindow
 */
public class Timer extends Thread {
  private final Controller controller;
  private volatile long timeW;
  private volatile long timeB;
  private TimerWindow timerWindow = null;
  private PlayerColor turn = WHITE;
  private long startTime;

  Timer(int timeWhite, int timeBlack, Controller controller) {
    this.controller = controller;
    timeW = timeWhite * 1000L;
    timeB = timeBlack * 1000L;

    startTime = System.currentTimeMillis();
  }

  @Override
  public void run() {
    PlayerColor winner;
    timerWindow = new TimerWindow((int) timeW / 1000, (int) timeB / 1000, controller);
    if (timeW == -1000 || timeB == -1000) return;
    long timeElapsed;
    while (timeW > 0 && timeB > 0) {
      timeElapsed = System.currentTimeMillis() - startTime;
      startTime = System.currentTimeMillis();
      if (turn == WHITE) {
        timeW = timeW - timeElapsed;
        int whiteSec = (int) (timeW / 1000);
        int whiteMins = whiteSec / 60;
        whiteSec = whiteSec - whiteMins * 60;
        if ((timeW % 50) == 0) {
          timerWindow.updateWhiteTime(whiteMins, whiteSec);
        }
      } else if (turn == PlayerColor.BLACK) {

        timeB = timeB - timeElapsed;
        int blackSec = (int) (timeB / 1000);
        int blackMins = blackSec / 60;
        blackSec = blackSec - blackMins * 60;
        if ((timeB % 50) == 0) {
          timerWindow.updateBlackTime(blackMins, blackSec);
        }
      }
    }
    if (timeW <= 0) {
      winner = BLACK;
    } else {
      winner = WHITE;
    }
    gameEnd(winner);
  }

  public void changeTurn() {
    turn = turn.getOpposite();
    timerWindow.changeTurn(turn);
  }

  public void gameEnd(PlayerColor color) {
    controller.gameEnd(color);
  }

  public void decrementTimer(int amount) {
    if (turn == WHITE) {
      timeW = timeW - amount * 1000L;
      int whiteSec = (int) (timeW / 1000);
      int whiteMins = whiteSec / 60;
      whiteSec = whiteSec - whiteMins * 60;

        timerWindow.updateWhiteTime(whiteMins, whiteSec);

    } else {
      timeB = timeB - amount * 1000L;
      int blackSec = (int) (timeB / 1000);
      int blackMins = blackSec / 60;
      blackSec = blackSec - blackMins * 60;

        timerWindow.updateBlackTime(blackMins, blackSec);

    }
  }
}
