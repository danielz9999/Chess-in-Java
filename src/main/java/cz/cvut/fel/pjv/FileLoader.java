package cz.cvut.fel.pjv;

public class FileLoader {

    public BoardState loadFile(String pathname) {
        return new BoardState(false, false, 0, 0, false);
    }
}
