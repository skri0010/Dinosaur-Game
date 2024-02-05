package game.dinosaur.behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.dinosaur.DinosaurStatus;
import game.ground.Tree;

import java.util.ArrayList;

public class FlyBehaviour extends Behaviour {
    private int flyTurns;
    private int maxFly;

    /**
     * FlyBehaviour constructor
     * @param maxFly the number of turns it takes for the dino to land after flight
     */
    public FlyBehaviour(int maxFly){
        this.maxFly = maxFly;
        this.flyTurns = maxFly;
    }

    /**
     * The fly action method that check whether a dino is able to fly and adds or removes the FLYING capability
     * based on preset conditions
     * @param actor actor to perform action
     * @param map current game map
     * @return follow behaviour to go to the location for flight or null if location not reached
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        flyTurns -= 1;
        if (flyTurns <= 0) {
            actor.removeCapability(DinosaurStatus.FLYING);
            if (map.locationOf(actor).getGround() instanceof Tree) {
                flyTurns = maxFly;
                actor.addCapability(DinosaurStatus.FLYING);
            }
            else{
                ArrayList<Location> allLocations = super.getLocationsOnMap(map);
                Location subject = map.locationOf(actor);
                Location idealLocation = null;
                int minDistance = 0;
                for (Location endPoint : allLocations) {
                    if (endPoint.getGround() instanceof Tree) {
                        if (super.distance(subject, endPoint) <= minDistance || minDistance == 0) {
                            minDistance = super.distance(subject, endPoint);
                            idealLocation = endPoint;
                        }
                        if (idealLocation.equals(subject)) {
                            break;
                        }
                    }
                }
                Action action = new FollowBehaviour(idealLocation).getFollowLocationAction(actor,map);
                return action;
            }
        }
        else if (map.locationOf(actor).getItems().size() == 0 && flyTurns > 0){
            actor.addCapability(DinosaurStatus.FLYING);
        }
        return null;
    }
}
