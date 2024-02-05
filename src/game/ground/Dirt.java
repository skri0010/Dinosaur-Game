package game.ground;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.utils.Util;

import java.util.List;


/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground{
	private Bush bush;
	public Dirt() {
		super('.');
	}

	@Override
	public void tick(Location location) {
		super.tick(location);
		double chance = Util.getProbability();
		createBush(location,chance);

	}

	/**
	 * Creates a bush in the dirt depending on the chance and the location surrounding the dirt
	 * @param chance a probabilty value out of 100
	 */
	private boolean createBush(Location location, double chance){
		List<Exit> exits = location.getExits();
		boolean treeFlag = false;
		int bushCounter = 0;
		for (int i = 0; i < exits.size(); i++){
			if (exits.get(i).getDestination().getGround() instanceof Tree){
				treeFlag = true;
			}
			if (exits.get(i).getDestination().getGround() instanceof Bush){
				bushCounter ++;
			}
		}
		// don't grow bush if there's a tree nearby
		if (treeFlag)
			return false;
		// probability of growing bush if there are at least 2 bushes is < 10
		else if (bushCounter >= 2 && location.getGround() instanceof Dirt && chance <= 10){
			bush = new Bush();
			location.setGround(bush);
			return true;
		}
		// probability of growing bush if doesn't have any bush nearby is < 1
		else if (location.getGround() instanceof Dirt && chance <= 1){
			bush = new Bush();
			location.setGround(bush);
			return true;
		}
		return false;
	}
	/**
	 * Method to delete the bush at a specific location
	 * @param location location of dirt
	 */
	public void deleteBush(Location location){
		Dirt dirt = new Dirt();
		location.setGround(dirt);
	}

}
