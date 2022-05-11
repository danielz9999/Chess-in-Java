package cz.cvut.fel.pjv;

import java.util.ArrayList;

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
  public boolean checkForCheckmate(
                                   ) {
        Coordinates nullMove = new Coordinates(0,0);
        ArrayList<Coordinates> allMoves = new ArrayList<>();
        allMoves.add(nullMove);
        allMoves = checkMoveControl.filterMoves(allMoves, nullMove);
        return allMoves.isEmpty();
  }
}
