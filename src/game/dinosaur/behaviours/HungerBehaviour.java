package game.dinosaur.behaviours;

import edu.monash.fit2099.engine.*;
import game.dinosaur.Allosaur;
import game.dinosaur.DinosaurStatus;
import game.dinosaur.Pterodactyl;
import game.dinosaur.interfaces.Carnivore;
import game.dinosaur.interfaces.Herbivore;
import game.ground.Bush;
import game.ground.Lake;
import game.ground.Tree;

import java.util.ArrayList;

/**
 * LocateFoodBehavior behavior class for handling dinosaur trying to find food based on
 * its unique characteristic (i.e Herbivore or Carnivore)
 * @author Suchit Krishna
 * @version 1.0.0
 */

public class HungerBehaviour extends Behaviour {


    /**
     * Locate food action to follow the food and perform the eatActions
     * @param actor actor to perform action
     * @param map current game map
     * @return returns the action to carry out
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Action action;
        Location idealLocation = null;
        Location subject = map.locationOf(actor);
        if (actor instanceof Herbivore)
            idealLocation = findVegSource(actor,map);
        if (actor instanceof Carnivore){
            idealLocation = findNonVegSource(actor,map);
        }
        FollowBehaviour followFood = new FollowBehaviour(idealLocation);
        action = followFood.getFollowLocationAction(actor, map);
        if (action == null || idealLocation.equals(subject)) {
            if (actor instanceof Herbivore){
                Herbivore herbivore = (Herbivore) actor;
                herbivore.eatFruit(null,idealLocation);
            }
            else if (actor instanceof Carnivore){
                Carnivore carnivore = (Carnivore) actor;
                action = carnivore.eatMeat();
                if (action != null){
                    return action;
                }
            }
            action = new WanderBehaviour().getAction(actor,map);
        }
        return action;
    }

    /**
     * Method to locate vegetarian food source
     * @param actor actor in the map to find food for
     * @param map current game map
     * @return returns the closest vegetarian food source
     */
    public Location findVegSource(Actor actor,GameMap map){
        ArrayList<Location> allLocations = super.getLocationsOnMap(map);
        Location subject = map.locationOf(actor);
        Location idealLocation = null;
        if (actor instanceof Herbivore) {
            int minDistance = 0;
            for (Location endPoint : allLocations) {
                if (endPoint.getGround() instanceof Tree || endPoint.getGround() instanceof Bush) {
                    if (super.distance(subject, endPoint) <= minDistance || minDistance == 0) {
                        minDistance = super.distance(subject, endPoint);
                        idealLocation = endPoint;
                    }
                    if (idealLocation.equals(subject)) {
                        break;
                    }
                }
            }
        }
        return idealLocation;
    }
    /**
     * Method to locate Non vegetarian food source
     * @param actor actor in the map to find food for
     * @param map current game map
     * @return returns the closest non vegetarian food source
     */
    public Location findNonVegSource(Actor actor,GameMap map) {
        ArrayList<Location> allLocations = super.getLocationsOnMap(map);
        Location subject = map.locationOf(actor);
        Location idealLocation = null;
        int minDistance = 0;
        char[] eatables = new char[0];
        if (actor instanceof Allosaur)
            eatables = new char[]{'o', 'D', 'd', '%', '$', '&','!','P'};
        if (actor instanceof Pterodactyl)
            eatables = new char[]{'~', '%', '$', '&','!'};
        for (Location endPoint: allLocations){
            if (super.distance(subject,endPoint) <= minDistance || minDistance == 0){
                for (char c : eatables) {
                    if (c == endPoint.getDisplayChar()) {
                        minDistance = super.distance(subject,endPoint);
                        idealLocation = endPoint;
                    }
                }
            }
        }
        return idealLocation;
    }


}
