package game.player;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Application;

/**
 * Special Action which allows player to restart game
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class RestartAction extends Action {

    public RestartAction() {}

    @Override
    public String execute(Actor actor, GameMap map) {
        Application.main(null);
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " restarts game";
    }

    @Override
    public String hotkey() {
        return "r";
    }
}
