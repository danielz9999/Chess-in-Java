package cz.cvut.fel.pjv;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Logger;
/**
 * A class capable of saving a game to a PGN file
 * the PGNHistory class keeps a list of every move made during the game, this class simply outputs them to a file
 */
public class FileSaver {
  private final PGNHistory pgnHistory;
  private final Logger log = Logger.getLogger(FileSaver.class.getName());

  public FileSaver(PGNHistory pgnHistory) {
    this.pgnHistory = pgnHistory;
  }

  public void savePGN() {
    ArrayList<String> notes = pgnHistory.getNotes();
    JFileChooser jfc = new JFileChooser();
    String fileName;
    JOptionPane.showMessageDialog(
        null, "Please save in a .pgn or .txt format", "Format notice", JOptionPane.PLAIN_MESSAGE);
    int isSaved = jfc.showSaveDialog(null);
    if (isSaved == JFileChooser.APPROVE_OPTION) {
      File saveFile = jfc.getSelectedFile();
      fileName = saveFile.getName();
    } else {
      log.severe("Error when choosing save location");
      return;
    }
    try (Writer wr =
        new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)); ) {
      for (int i = 0; i < 7; i++) {
        wr.write("[]" + System.lineSeparator());
      }
      wr.write(System.lineSeparator());
      for (String note : notes) {
        wr.write(note);
      }
    } catch (UnsupportedEncodingException e) {
      log.severe("Unsupported encoding exception when attempting to save PGn");
    } catch (IOException e) {
      log.severe("IO exception when attempting to save PGN");
    }
  }
}
