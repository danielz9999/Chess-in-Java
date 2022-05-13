package cz.cvut.fel.pjv;

import java.util.ArrayList;
/**
 * A class that looks at a BoardState and decides if either player is in checkmate/mate
 * Goes through all possible moves of all pieces of each player, and if all of them end in check, checkForMate() returns true
 * checkForCheckmate() is only called after checkForMate() and just test if a null move will still leave the player in check, and if it is then it returns true
 */
public class CheckmateChecker {
  private final CheckMoveControl checkMoveControl;
  private final PieceMoveGenerator pieceMoveGenerator;

  public CheckmateChecker(CheckMoveControl checkMoveControl) {
    this.checkMoveControl = checkMoveControl;
    this.pieceMoveGenerator = new PieceMoveGenerator();
  }

  public boolean checkForMate(
      BoardState board,
      ArrayList<Coordinates> whitePositions,
      ArrayList<Coordinates> blackPositions) {
    ArrayList<Coordinates> allMoves = new ArrayList<>();

    if (board.getCurrentTurn() == PlayerColor.WHITE) {
      for (Coordinates position : whitePositions) {
        ArrayList<Coordinates> generatedMoves =
            (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, position);
        generatedMoves = checkMoveControl.filterMoves(generatedMoves, position);
        allMoves.addAll(generatedMoves);
      }

    } else if (board.getCurrentTurn() == PlayerColor.BLACK) {
      for (Coordinates position : blackPositions) {
        ArrayList<Coordinates> generatedMoves =
            (ArrayList<Coordinates>) pieceMoveGenerator.generateMoves(board, position);
        generatedMoves = checkMoveControl.filterMoves(generatedMoves, position);
        allMoves.addAll(generatedMoves);
      }
    }
    return allMoves.isEmpty();
  }

  public boolean checkForCheckmate() {
    Coordinates nullMove = new Coordinates(0, 0);
    ArrayList<Coordinates> allMoves = new ArrayList<>();
    allMoves.add(nullMove);
    allMoves = checkMoveControl.filterMoves(allMoves, nullMove);
    return allMoves.isEmpty();
  }
}
