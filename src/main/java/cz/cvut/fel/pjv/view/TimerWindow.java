package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.BoardState;
import cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.FileLoader;
import cz.cvut.fel.pjv.PlayerColor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
/**
 * A GUI class that belongs to the Timer class, meaning it runs on a separate thread
 * Displays the clock chess, the current turn and basic instructions
 * Also has buttons to save/load/exit the game
 */
public class TimerWindow {
  private final FileLoader loader;
  JFrame frame;
  JLabel turnText;
  JLabel timeOne;
  JLabel timeTwo;
  Controller controller;

  public TimerWindow(int whiteTime, int blackTime, Controller controller) {
    this.controller = controller;
    this.loader = new FileLoader();
    init(whiteTime, blackTime);
  }

  private void init(int whiteTime, int blackTime) {
    frame = new JFrame("Timer");
    frame.setSize(355, 275);
    if (whiteTime >= 0 && blackTime >= 0) {
      frame.setSize(new Dimension(355, 310));
    }
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

    JPanel panel = new JPanel();
    JPanel labelPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    turnText = new JLabel("    It is the turn of player: " + PlayerColor.WHITE + "        ");

    int whiteMins = whiteTime / 60;
    whiteTime = whiteTime - whiteMins * 60;
    int blackMins = blackTime / 60;
    blackTime = blackTime - blackMins * 60;

    timeOne = new JLabel("    Remaining time of player 1:     " + whiteMins + " : " + whiteTime);
    timeTwo = new JLabel("    Remaining time of player 2:     " + blackMins + " : " + blackTime);
    JLabel label3 = new JLabel("    Left-click to move");
    JLabel label4 = new JLabel("    If a piece doesn't show any possible moves its because:");
    JLabel label5 = new JLabel("      A)    It has no available moves");
    JLabel label6 = new JLabel("      B)    The moves it does have lead to check");
    JLabel label7 = new JLabel("    If no moves are truly possible, the game ends");

    JButton saveButton = new JButton("Save Game");
    JButton loadButton = new JButton("Load Game");
    JButton exitButton = new JButton("Exit");

    saveButton.setPreferredSize(new Dimension(100, 40));
    loadButton.setPreferredSize(new Dimension(100, 40));
    exitButton.setPreferredSize(new Dimension(100, 40));

    saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    ArrayList<JLabel> labels = new ArrayList<>();
    labels.add(label3);
    labels.add(label4);
    labels.add(label5);
    labels.add(label6);
    labels.add(label7);

    labelPanel.add(turnText);
    if (whiteTime > 0 && blackTime > 0) {
      timeOne.setAlignmentX(Component.LEFT_ALIGNMENT);
      timeTwo.setAlignmentX(Component.LEFT_ALIGNMENT);
      labelPanel.add(timeOne);
      labelPanel.add(timeTwo);
    }

    labelPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    for (int i = 0; i < labels.size(); i++) {
      labels.get(i).setAlignmentX(Component.LEFT_ALIGNMENT);
      labelPanel.add(labels.get(i));
      labelPanel.add(Box.createRigidArea(new Dimension(0, 3)));
      if (i == 0) {
        labelPanel.add(Box.createRigidArea(new Dimension(0, 7)));
      }
    }
    labelPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    saveButton.addActionListener(e -> controller.saveGame());
    loadButton.addActionListener(
        e -> {
          String fileName = filePathQuestion();
          BoardState boardState = loader.loadFile(fileName);
          controller.startGame(boardState);
        });
    exitButton.addActionListener(e -> System.exit(0));

    buttonPanel.add(saveButton);
    buttonPanel.add(loadButton);
    buttonPanel.add(exitButton);
    panel.add(labelPanel);
    panel.add(buttonPanel);
    frame.setContentPane(panel);
    frame.setVisible(true);
  }

  public void updateWhiteTime(int mins, int sec) {
    timeOne.setText("    Remaining time of player 1:     " + mins + " : " + sec);
  }

  public void updateBlackTime(int mins, int sec) {
    timeTwo.setText("    Remaining time of player 2:     " + mins + " : " + sec);
  }

  public void changeTurn(PlayerColor color) {
    turnText.setText("    It is the turn of player: " + color + "        ");
  }
  //A dialog on where the game should be loaded from, identical to the one in MainMenu
  private String filePathQuestion() {
    JOptionPane.showMessageDialog(
        null, "Please load from a .pgn or .txt format", "Format notice", JOptionPane.PLAIN_MESSAGE);

    JFileChooser fileChooser = new JFileChooser();
    File workingDirectory = new File(System.getProperty("user.dir"));
    fileChooser.setCurrentDirectory(workingDirectory);
    fileChooser.setDialogTitle("Choose a file to load from: ");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("png fromats", "pgn", "txt");
    fileChooser.setFileFilter(filter);

    int ret = fileChooser.showOpenDialog(null);
    if (ret != JFileChooser.APPROVE_OPTION) {
      return null;
    }
    return fileChooser.getSelectedFile().getAbsolutePath();
  }
}
