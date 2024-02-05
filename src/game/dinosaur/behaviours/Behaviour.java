package game.dinosaur.behaviours;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

public abstract class Behaviour {
	
	/**
	 * A factory for creating actions. Chaining these together can result in an actor performing more complex tasks.
	 *
	 * A Behaviour represents a kind of objective that an Actor can have.  For example
	 * it might want to seek out a particular kind of object, or follow another Actor, 
	 * or run away and hide.  Each implementation of Behaviour returns an Action that the 
	 * Actor could take to achieve its objective, or null if no useful options are available.
	 * method that determines which Behaviour to perform.  This allows the Behaviour's logic
	 * to be reused in other Actors via delegation instead of inheritance.
	 *
	 * An Actor's {@code playTurn()} method can use Behaviours to help decide which Action to
     * perform next.  It can also simply create Actions itself, and for simpler Actors this is
	 * likely to be sufficient.  However, using Behaviours allows
	 * us to modularize the code that decides what to do, and that means that it can be 
	 * reused if (e.g.) more than one kind of Actor needs to be able to seek, follow, or hide.
	 *
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return an Action that actor can perform, or null if actor can't do this.
	 * @see Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	public abstract Action getAction(Actor actor, GameMap map);


	/**
	 * Method to get all the locations in the map
	 * @param map map to get all locations from
	 * @return allLocations
	 */
	public ArrayList<Location> getLocationsOnMap(GameMap map){
		ArrayList<Location> allLocations = new ArrayList<>();
		NumberRange xVals = map.getXRange();
		NumberRange yVals = map.getYRange();
		for (int x=0;x<= xVals.max();x++){
			for (int y=0;y<= yVals.max();y++){
				Location tempLocation = map.at(x,y);
				allLocations.add(tempLocation);
			}
		}
		return allLocations;
	}

	/**
	 * Compute the Manhattan distance between two locations.
	 *
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	public int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}
