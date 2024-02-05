package game.dinosaur.behaviours;

import edu.monash.fit2099.engine.*;
import game.dinosaur.interfaces.DinosaurInterface;
import game.ground.Lake;

import java.util.ArrayList;

/**
 * A class that figures out the distance to a water source and perform the drinking action
 * for all dinosaurs
 * @version 1.0.0
 * @author Suchit Krishna
 */

public class ThirstBehaviour extends Behaviour {


    /**
     * Locate water source action to follow the water source and perform the drinkActions
     * @param actor actor to perform action
     * @param map current game map
     * @return returns the action to carry out
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location waterSource = findWaterSource(actor,map);
        FollowBehaviour follow = new FollowBehaviour(waterSource);
        Action followAction = follow.getFollowLocationAction(actor,map);
        if (followAction == null || waterSource.equals(map.locationOf(actor))){
            Lake lake = (Lake)waterSource.getGround();
            if (lake.getNumberOfSips() > 0){
                ((DinosaurInterface) actor).drinkWater(map);
                lake.reduceNumberOfSips(1);
            }
            return new WanderBehaviour().getAction(actor,map);
        }
        return followAction;
    }
    /**
     * Method to locate water source closest to actor
     * @param actor actor in the map to find water for
     * @param map current game map
     * @return returns the closest water source to actor
     */
    public Location findWaterSource(Actor actor, GameMap map){
        ArrayList<Location> allLocations = super.getLocationsOnMap(map);
        Location subject = map.locationOf(actor);
        Location idealLocation = null;
        int minDistance = 0;
        for (Location endPoint : allLocations) {
            if (endPoint.getGround() instanceof Lake) {
                if (super.distance(subject, endPoint) <= minDistance || minDistance == 0) {
                    minDistance = super.distance(subject, endPoint);
                    idealLocation = endPoint;
                }
                if (idealLocation.equals(subject)) {
                    break;
                }
            }
        }
        return idealLocation;
    }
}
