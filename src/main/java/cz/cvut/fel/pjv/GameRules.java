package cz.cvut.fel.pjv;
//The class ensuring the correct application of chess rules
//Also functions as the Controller part of the MVC model
public class GameRules {
    private Integer turn;

    public GameRules() {
        Board board = new Board();
        View view = new View();
        Timer timer = new Timer(0,0);
    }

    private void changeTurn(){}
    private void loadPGNFormat(String file_name){}

}
