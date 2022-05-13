package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.BoardState;
import cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.FileLoader;
import cz.cvut.fel.pjv.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.PlayerColor.*;

/**
 * A GUI class representing the main menu the launches at the start of the application
 * Here the user can choose on the specifics of the game, timer, whether to play VS the PC or not,
 * Whether to customise the board before starting or whether to load a saved game from a PGN file
 * Consists mainly of Java Swing elements
 * Takes @param Controller so it then signal to start the game
 */
public class MainMenu {
  private final FileLoader loader = new FileLoader();
  private final JFrame frame;
  private final Logger log = Logger.getLogger(MainMenu.class.getName());
  Controller controller;
  private int whiteTime = -1;
  private int blackTime = -1;
  private BoardState boardState;
  private JFrame submenuFrame;
  private PlayerColor botColor = NONE;
  private CustomGameBuilder customGameBuilder;
 //A simple choice between playing and exiting
  public MainMenu(Controller controller) {
    this.controller = controller;

    frame = new JFrame("Chess");

    JButton exit = new JButton();
    JButton play = new JButton();

    frame.setSize(250, 200);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

    play.setAlignmentX(Component.CENTER_ALIGNMENT);

    play.setText("Play");
    play.addActionListener(e -> submenu());

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

  //Here the player sets the time
  private void timerQuestion() {

    int result =
        JOptionPane.showConfirmDialog(
            frame,
            "Would you like to play with a timer?",
            "Timer option choice",
            JOptionPane.YES_NO_OPTION);

    if (result == JOptionPane.YES_NO_OPTION) {
      try {
      whiteTime =
          Integer.parseInt(
              JOptionPane.showInputDialog(
                  null,
                  "Please enter time (in seconds) for player with white pieces (max. 3559):"));
      } catch (NumberFormatException e) {
        log.severe("Did not detect a number on user input");
        JOptionPane.showMessageDialog(
            null, "Please input a number", "Number format error", JOptionPane.ERROR_MESSAGE);
        timerQuestion();
        return;
      }
      if (whiteTime > 3559) {

        whiteTime = 3599;
      }
      try {
      blackTime =
          Integer.parseInt(
              JOptionPane.showInputDialog(
                  null,
                  "Please enter time (in seconds) for player with black pieces (max. 3559):"));
      } catch (NumberFormatException e) {
        log.severe("Did not detect a number on user input");
        JOptionPane.showMessageDialog(
            null, "Please input a number", "Number format error", JOptionPane.ERROR_MESSAGE);
        timerQuestion();
        return;
      }
      if (blackTime > 3559) {

        blackTime = 3599;
      }
    }
  }
  //The user chooses from where to load the game
  private String filePathQuestion() {
    JOptionPane.showMessageDialog(
        null, "Please load from a .pgn or .txt format", "Format notice", JOptionPane.PLAIN_MESSAGE);

    JFileChooser fileChooser = new JFileChooser();
    File workingDirectory = new File(System.getProperty("user.dir"));
    fileChooser.setCurrentDirectory(workingDirectory);
    fileChooser.setDialogTitle("Choose a file to load from: ");
    int ret = fileChooser.showOpenDialog(null);
    if (ret != JFileChooser.APPROVE_OPTION) {
      return null;
    }
    return fileChooser.getSelectedFile().getAbsolutePath();
  }
  //Choosing between different types of play, standard, custom and loaded from a file
  private void submenu() {
    frame.setVisible(false);
    submenuFrame = new JFrame("Play options");
    JButton newGame = new JButton();
    JButton load = new JButton();
    JButton customGame = new JButton();
    submenuFrame.setSize(250, 240);
    submenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    submenuFrame
        .getContentPane()
        .setLayout(new BoxLayout(submenuFrame.getContentPane(), BoxLayout.Y_AXIS));

    newGame.setText("New Game");
    newGame.setAlignmentX(Component.CENTER_ALIGNMENT);

    load.setAlignmentX(Component.CENTER_ALIGNMENT);
    load.setText("Load saved game");

    customGame.setAlignmentX(Component.CENTER_ALIGNMENT);
    customGame.setText("Custom New Game");

    submenuFrame.getContentPane().add(Box.createRigidArea(new Dimension(0, 35)));
    submenuFrame.getContentPane().add(newGame);
    submenuFrame.getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));
    submenuFrame.getContentPane().add(customGame);
    submenuFrame.getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));
    submenuFrame.getContentPane().add(load);
    submenuFrame.getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));

    newGame.addActionListener(
        e1 -> {
          playVsComputerQuestion();
          timerQuestion();
          boardState = new BoardState(true, true, whiteTime, blackTime, true);
          boardState.setBotPlayerColor(botColor);
          log.info("Starting normal game with bot color: " + boardState.getBotPlayerColor());
          controller.startGame(boardState);
        });
    load.addActionListener(
        e1 -> {
          String fileName = filePathQuestion();
          boardState = loader.loadFile(fileName);
          controller.startGame(boardState);
        });
    customGame.addActionListener(
        e1 -> {
          playVsComputerQuestion();
          timerQuestion();
          customGameBuilder = new CustomGameBuilder(this, whiteTime, blackTime);
          customGameBuilder.customGameInit();
          submenuFrame.setVisible(false);
        });
    submenuFrame.setLocationRelativeTo(null);
    submenuFrame.setVisible(true);
  }
  //Two dialogs deciding if the opponent will be a bot, and if so which side will they play
  private void playVsComputerQuestion() {
    int botPlayChoice =
        JOptionPane.showConfirmDialog(
            frame,
            "Would you like to play VS a computer?",
            "Computer opponent choice",
            JOptionPane.YES_NO_OPTION);
    if (botPlayChoice == JOptionPane.YES_OPTION) {
      Object[] colors = {WHITE, BLACK};
      int playerColorChoice =
          JOptionPane.showOptionDialog(
              frame,
              "Which side (color) do you want to play?",
              "Player color choice",
              JOptionPane.YES_NO_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              colors,
              colors[0]);
      if (playerColorChoice == JOptionPane.YES_OPTION) {
        botColor = BLACK;
        log.info("Bot player color chose: BLACK");
      } else {
        botColor = WHITE;
        log.info("Bot player color chose: WHITE");
      }
    }
  }
  //the Custom Game option of the submenu returns here after finishing its setup
  public void customGameFinishSetup(BoardState customBoard) {
    log.info("Starting custom game, botcolor: " + botColor);
    customBoard.setBotPlayerColor(botColor);
    controller.startGame(customBoard);
  }

  public int getWhiteTime() {
    return whiteTime;
  }

  public int getBlackTime() {
    return blackTime;
  }

  public BoardState getBoardState() {
    return boardState;
  }

  public void close() {
    submenuFrame.setVisible(false);
    submenuFrame.dispose();
  }
}
