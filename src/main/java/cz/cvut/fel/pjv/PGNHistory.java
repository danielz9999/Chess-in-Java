package cz.cvut.fel.pjv;

import java.util.ArrayList;
import java.util.logging.Logger;
/**
 * After a move is made, adds the appropriate notation to its notes ArrayList
 *
 */
public class PGNHistory {
    private final char[] rows = {'8','7','6','5','4','3','2','1'};
    private final char[] columns = {'a','b','c','d','e','f','g','h'};
    private final ArrayList<String> notes;
    private final Logger log = Logger.getLogger(PGNHistory.class.getName());
    private int noteCounter;
    private int turnCounter;

    public PGNHistory() {
        notes = new ArrayList<>();
        noteCounter = 0;
        turnCounter = 0;
    }

    public void saveMove(Coordinates start, Coordinates end, PieceTypes type, int specialMove,
                         boolean isCapture, boolean samePieceOverlap) {
        String turn = "";
        if (noteCounter % 2 == 0) {
            turnCounter++;
            turn = turnCounter + ". ";
        }
        noteCounter++;

        String note;
        String special;
        String linebreak = "";

        special = switch (specialMove) {
            case 0 -> "O-O ";
            case 1 -> "O-O-O ";
            default -> "";
        };
        if (noteCounter % 10 == 0) {
            linebreak = System.lineSeparator();
        }

        if (!special.equals("")) {
            String output = turn + special + linebreak;
            log.info("Castling notation detected: " + output);
            notes.add(output);
        } else {
            note = basicMovement(start, end, type, isCapture, samePieceOverlap);
            String output = turn + note + linebreak;
            notes.add(output);
        }

    }
    private String basicMovement(Coordinates start, Coordinates end,PieceTypes type,
                                 boolean isCapture, boolean samePieceOverlap) {
        String piece = switch(type) {
            case KING -> "K";
            case QUEEN -> "Q";
            case KNIGHT -> "N";
            case PAWN -> "";
            case NONE -> "";
            case BISHOP -> "B";
            case ROOK -> "R";
        };
        String overlap = "";
        if (samePieceOverlap) {
            overlap = String.valueOf(columns[start.getY()]);
            log.info("overlap in notation");
        }
        String captureX = "";
        if (isCapture) {
            captureX = "x";
            if (type == PieceTypes.PAWN && overlap.equals("")) {
                captureX = columns[start.getY()] + captureX;
            }
        }
        String position = columns[end.getY()] + "" +rows[end.getX()] + " ";
        String output = piece + overlap + captureX + position;
        log.info("Movement notation: " + output);
        return output;
    }
    public ArrayList<String> getNotes() {
        return notes;
    }
}
