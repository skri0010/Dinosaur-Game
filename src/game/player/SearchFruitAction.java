package game.player;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.utils.Util;
import game.ground.Bush;
import game.ground.Tree;
import game.items.Fruit;

import java.util.ArrayList;

/**
 * Special Action for searching fruits
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class SearchFruitAction extends Action {

    Player player;

    public SearchFruitAction(Player player) {
        this.player = player;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        ArrayList<Fruit> lstOfFruits;
        boolean foundFruit = false;
        double randomNumber = Util.getProbability();
        Fruit fruit = null;
        Location location = player.getLocation();
            if (location.getGround() instanceof Tree) {
                lstOfFruits = ((Tree) location.getGround()).getFruitsOnTree();
                if (lstOfFruits.size() > 0 && randomNumber <= 40) {
                    foundFruit = true;
                    // remove fruit from Tree
                    fruit = lstOfFruits.get(0);
                    fruit.removeCapability(Fruit.Status.ONTREE);
                    ((Tree) location.getGround()).removeFruitOnTree(fruit);
                }

            } else if (location.getGround() instanceof Bush) {

                lstOfFruits = ((Bush) location.getGround()).getFruitsOnBush();
                if (lstOfFruits.size() > 0 && randomNumber <= 40) {
                    foundFruit = true;
                    // remove fruit from Bush
                    fruit = lstOfFruits.get(0);
                    fruit.removeCapability(Fruit.Status.ONBUSH);
                    ((Bush) location.getGround()).removeFruitOnBush(fruit);
                }
            }
            if (foundFruit) {
                fruit.addCapability(Fruit.Status.INREPO);
                fruit.changeName("fruit");
                player.addItemToInventory(fruit);
                player.incrementEcoPts(10); // increase eco pts by 10 for each fruit harvested
                return ("You found a fruit");
            } else {
                return ("You search the tree or bush for fruit, but you can’t find any ripe ones");
            }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " searches for fruit";
    }

    @Override
    public String hotkey() {
        return "s";
    }
}
