package cz.cvut.fel.pjv.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
//Class implementing the gui menu that shows up when launching the application
public class MainMenu {
  private int whiteTime = -1;
  private int blackTime = -1;

  public MainMenu() {
    JFrame frame = new JFrame("Chess");

    JButton exit = new JButton();
    JButton play = new JButton();

    frame.setSize(250, 200);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

    play.setAlignmentX(Component.CENTER_ALIGNMENT);

    play.setText("Play");
    play.addActionListener(
        e -> {
          frame.setVisible(false);
          JFrame frame2 = new JFrame("Play options");
          JButton newGame = new JButton();
          JButton load = new JButton();
          JButton customGame = new JButton();
          frame2.setSize(250, 240);
          frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame2
              .getContentPane()
              .setLayout(new BoxLayout(frame2.getContentPane(), BoxLayout.Y_AXIS));

          newGame.setText("New Game");
          newGame.setAlignmentX(Component.CENTER_ALIGNMENT);

          load.setAlignmentX(Component.CENTER_ALIGNMENT);
          load.setText("Load saved game");

          customGame.setAlignmentX(Component.CENTER_ALIGNMENT);
          customGame.setText("Custom New Game");

          frame2.getContentPane().add(Box.createRigidArea(new Dimension(0, 35)));
          frame2.getContentPane().add(newGame);
          frame2.getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));
          frame2.getContentPane().add(customGame);
          frame2.getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));
          frame2.getContentPane().add(load);
          frame2.getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));

          newGame.addActionListener(e1 -> timerQuestion(frame));
          load.addActionListener(e1 -> timerQuestion(frame));
          customGame.addActionListener(e1 -> timerQuestion(frame));
          frame2.setLocationRelativeTo(null);
          frame2.setVisible(true);
        });

    exit.setText("Exit");
    exit.addActionListener(
        e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
    exit.setAlignmentX(Component.CENTER_ALIGNMENT);

    frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 35)));
    frame.getContentPane().add(play);
    frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));
    frame.getContentPane().add(exit);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private void timerQuestion(JFrame frame) {
    int result =
        JOptionPane.showConfirmDialog(
            frame,
            "Would you like to play with a timer?",
            "Timer option choice",
            JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_NO_OPTION) {
      whiteTime =
          Integer.parseInt(
              JOptionPane.showInputDialog(
                  null, "Please enter time (in seconds) for player with white pieces:"));
      if (whiteTime > 3559) {

        whiteTime = 3599;
      }
      blackTime =
          Integer.parseInt(
              JOptionPane.showInputDialog(
                  null, "Please enter time (in seconds) for player with black pieces (max. 3559)"));
      if (blackTime > 3559) {

        blackTime = 3599;
      }
    }
  }

  public int getWhiteTime() {
    return whiteTime;
  }

  public int getBlackTime() {
    return blackTime;
  }
}
