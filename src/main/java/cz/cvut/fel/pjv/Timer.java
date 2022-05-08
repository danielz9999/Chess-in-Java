package cz.cvut.fel.pjv;
// A timer on a separate thread from the test of the application
// Maintains the chess clock
public class Timer extends Thread {
  private int timeW;
  private int timeB;

  Timer(int timeWhite, int timeBlack) {
    timeW = timeWhite;
    timeB = timeBlack;
  }

  public void run() {}
}
