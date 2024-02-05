package game.dinosaur.behaviours;

import edu.monash.fit2099.engine.*;


/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor or Location.
 */
public class FollowBehaviour extends Behaviour {

	private Object target;

	/**
	 * Constructor.
	 * 
	 * @param subject the Actor to follow
	 */
	public FollowBehaviour(Actor subject) {
		this.target = subject;
	}
	public FollowBehaviour(Location subject){
		this.target = subject;
	}

	/**
	 * The normal follow behavior action for when target is an actor
	 * @param actor actor to perform action
	 * @param map current game map
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if(!map.contains((Actor) target) || !map.contains(actor))
			return null;

		Location here = map.locationOf(actor);
		Location there = map.locationOf((Actor) target);

		return getAction(actor, here, there);
	}

	/**
	 * The follow behavior action for when target is a Location
	 * @param actor actor to perform action
	 * @param map current game map
	 */
	public Action getFollowLocationAction(Actor actor, GameMap map){
		Location here = map.locationOf(actor);
		Location there = (Location) target;


		return getAction(actor, here, there);
	}

	/**
	 * Continuation of the getAction for the previous two methods
	 * @param actor actor to perform action
	 * @param here the starting location
	 * @param there the ending location
	 */
	private Action getAction(Actor actor, Location here, Location there) {
		if (there != null) {
			int currentDistance = super.distance(here, there);
			for (Exit exit : here.getExits()) {
				Location destination = exit.getDestination();
				if (destination.canActorEnter(actor)) {
					int newDistance = super.distance(destination, there);
					if (newDistance < currentDistance) {
						return new MoveActorAction(destination, exit.getName());
					}
				}
			}
		}
		return null;
	}
}