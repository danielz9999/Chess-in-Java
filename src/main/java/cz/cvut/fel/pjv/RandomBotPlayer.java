package cz.cvut.fel.pjv;

import java.util.ArrayList;
import java.util.Collections;

public class RandomBotPlayer extends BotPlayer {
  public RandomBotPlayer(PlayerColor pieceColor) {
    super(pieceColor);
  }

  @Override
  public Coordinates pickPiece(ArrayList<Coordinates> options) {
    return pickMove(options);
  }

  @Override
  public Coordinates pickMove(ArrayList<Coordinates> options) {
    Collections.shuffle(options);
    return options.get(0);
  }
}
