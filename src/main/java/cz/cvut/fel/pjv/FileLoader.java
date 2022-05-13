package cz.cvut.fel.pjv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
/**
 * A class capable of loading a game from a PGN file
 * Reads the opened file character by character and based on that makes the decoded moves on a standard chessboard
 */
public class FileLoader {
  private final Logger log = Logger.getLogger(FileLoader.class.getName());
  private final PieceMover pieceMover = new PieceMover(null, null);
  private final PieceMoveGenerator pieceMoveGenerator = new PieceMoveGenerator();
  private final ArrayList<Character> pieces = new ArrayList<>();
  private final char[] piecesArray = {'K', 'Q', 'R', 'B', 'N'};
  private final char[] rowsArray = {'8', '7', '6', '5', '4', '3', '2', '1'};
  private final char[] columnsArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
  private final char[] numbersArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

  public FileLoader() {
    pieces.add('K');
    pieces.add('Q');
    pieces.add('R');
    pieces.add('B');
    pieces.add('N');
  }

  public BoardState loadFile(String pathname) {
    BoardState loadedBoardstate = new BoardState(true, true, -1, -1, true);

    try (BufferedReader reader = new BufferedReader(new FileReader(pathname))) {
      for (int i = 0; i < 8; i++) {
        reader.readLine();
      }
      parsePGN(reader, loadedBoardstate);

    } catch (IOException e) {
      log.severe("IO exception when opening file");
    }
    loadedBoardstate.setBotPlayerColor(PlayerColor.NONE);
    return loadedBoardstate;
  }

  private void parsePGN(BufferedReader reader, BoardState boardState) {
    log.info("Entered parsing of PGN format");
    int currChar;
    int prevChar = 32;
    int currIndex;
    PieceTypes currType = PieceTypes.PAWN;
    int coorX = 0;
    int coorY = 0;
    char oCounter = 0;

    try {
      while ((currChar = reader.read()) != -1) {

        log.info("New char read: " + currChar);
        if (currChar == '\n' || currChar == ' ' && oCounter > 0) {
          castle(boardState, oCounter);
          prevChar = currChar;
          oCounter = 0;
          continue;
        }
        if (currChar == ' ' || currChar == '.' || currChar == 'x') {
          prevChar = currChar;
          continue;
        }
        if ((isIn(numbersArray, currChar)) != -1
            && (prevChar == ' ' && (isIn(columnsArray, prevChar)) == -1)) {
          prevChar = currChar;
          continue;
        }
        if ((currIndex = isIn(piecesArray, currChar)) != -1) {
          log.info("Piece type set");
          currType = PieceTypes.values()[currIndex];
          prevChar = currChar;
          continue;
        }
        if ((currIndex = isIn(columnsArray, currChar)) != -1) {
          coorY = currIndex;
          prevChar = currChar;
          continue;
        }
        if ((currIndex = isIn(rowsArray, currChar)) != -1 && (isIn(columnsArray, prevChar)) != -1) {
          coorX = currIndex;
          prevChar = currChar;
          log.info("Finding move for: " + currType + " at " + coorX + " " + coorY);
          findMove(coorX, coorY, boardState, currType);
          currType = PieceTypes.PAWN;
          continue;
        }
        if (currChar == 'O') {
          oCounter++;
          prevChar = currChar;
        }
      }
    } catch (IOException e) {
      log.severe("IO exception when reading file");
    }
  }

  private void findMove(int endX, int endY, BoardState board, PieceTypes type) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board.getBoard()[i][j].getColor() == board.getCurrentTurn()
            && board.getBoard()[i][j].getType() == type) {
          ArrayList<Coordinates> moveList =
              (ArrayList<Coordinates>)
                  pieceMoveGenerator.generateMoves(board, new Coordinates(i, j));
          if (moveList.contains(new Coordinates(endX, endY))) {
            pieceMover.movePiece(board, new Coordinates(i, j), new Coordinates(endX, endY));
            log.info("Moving piece " + type + " from " + i + " " + j + " to " + endX + " " + endY);
          } else {
            for (Coordinates move : moveList) {
              log.info(move.getX() + " " + move.getY() + " NOT EQUAL TO " + endX + " " + endY);
            }
          }
        }
      }
    }
  }

  private void castle(BoardState board, int counter) {
    if (board.getCurrentTurn() == PlayerColor.WHITE) {
      if (counter == 3) {
        pieceMover.movePiece(board, new Coordinates(7, 4), new Coordinates(7, 2));
      } else if (counter == 2) {
        pieceMover.movePiece(board, new Coordinates(7, 4), new Coordinates(7, 6));
      }
    } else {
      if (counter == 3) {
        pieceMover.movePiece(board, new Coordinates(0, 4), new Coordinates(0, 2));
      } else if (counter == 2) {
        pieceMover.movePiece(board, new Coordinates(0, 4), new Coordinates(0, 6));
      }
    }
  }

  private int isIn(char[] array, int input) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == input) {
        return i;
      }
    }
    return -1;
  }
}
