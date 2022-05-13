package cz.cvut.fel.pjv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A bot player which picks random pieces and makes random moves with them
 */
public class RandomBotPlayer extends BotPlayer {
  public RandomBotPlayer(PlayerColor pieceColor) {
    super(pieceColor);
  }

  @Override
  public Coordinates pickPiece(List<Coordinates> options) {
    return pickMove(options);
  }

  @Override
  public Coordinates pickMove(List<Coordinates> options) {
    Collections.shuffle(options);
    return options.get(0);
  }
}
