package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.View;

//The class ensuring the correct application of chess rules
public class GameRules {
    private Integer turn;

    public GameRules() {
        Board board = new Board();
        View view = new View();
    }

    private void changeTurn(){}
    private void loadPGNFormat(String file_name){}

}
