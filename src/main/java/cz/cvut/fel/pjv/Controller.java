package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.View;

//The controller part of the MVC model
public class Controller {
    public Controller() {
        GameRules gameRules = new GameRules();

        View view = new View();
        Timer timer = new Timer(view.getMainMenu().getWhiteTime(),view.getMainMenu().getBlackTime());
    }
}
