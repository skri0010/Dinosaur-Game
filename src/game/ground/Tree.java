package game.ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.utils.Util;
import game.items.Fruit;

import java.util.ArrayList;

public class Tree extends Ground {
	private int age = 0;

	private Location location; // represent the location of the tree object

	private ArrayList<Fruit> fruitsOnGround = new ArrayList<>(); // represent all fruits on the ground

	private ArrayList<Fruit> fruitsOnTree = new ArrayList<>(); // represent all fruits on the tree

	public Tree() {
		super('+');
	}

	/**
	 * class variable which will store the number of fruits produced by all trees in the game in the current turn
	 */
	public static int  NumberOfFruitsProducedCurrentTurn;

	@Override
	public void tick(Location location) {
		super.tick(location);
		this.location = location;
		this.produceFruit();
		this.dropFruit();
		this.updateTree();
		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';
	}

	public Location getLocation() {
		return location;
	}

	public ArrayList<Fruit> getFruitsOnGround() {
		// prevent privacy leak
		return new ArrayList<>(fruitsOnGround);
	}

	public ArrayList<Fruit> getFruitsOnTree() {
		// prevent privacy leak
		return new ArrayList<>(fruitsOnTree);
	}

	/**
	 * This method is used to remove a fruit obj on the tree
	 */
	public void removeFruitOnTree(Fruit fruit){
		fruitsOnTree.remove(fruit);
	}

	/**
	 * This method is used to remove a fruit obj from the ground
	 */
	public void removeFruitFromGround(Fruit fruit){
		fruitsOnGround.remove(fruit);
	}


	/**
	 * This method is used to produce a new ripe fruit
	 * It has a 50% chance to produce one ripe fruit
	 */
	private void produceFruit(){
		double randomNumber = Util.getProbability();
		if (randomNumber <= 50){
			Fruit fruit = new Fruit("fruit on tree");
			fruit.addCapability(Fruit.Status.ONTREE);
			fruitsOnTree.add(fruit);
//			this.location.addItem(fruit);
			NumberOfFruitsProducedCurrentTurn++;
		}
	}

	/**
	 * This method is used to check if any fruits from the tree can fall
	 * Each fruit on tree has a 5% chance to fall
	 */
	private void dropFruit(){
		for (Fruit fruit: fruitsOnTree) {
			double randomNumber = Util.getProbability();
			if (randomNumber <= 5) {
				fruit.changeName("fruit on ground");
				fruit.removeCapability(Fruit.Status.ONTREE);
				fruit.addCapability(Fruit.Status.ONGROUND);
				fruitsOnGround.add(fruit);
				this.location.addItem(fruit);
			}
		}
	}

	/**
	 * This method is used to update the tree so that all fruits in the arrayList 'fruitsOnGround' aren't also
	 * found in the arrayList 'fruitsOnTree'
	 */
	private void updateTree(){
		for (Fruit fruit: fruitsOnGround){
			this.removeFruitOnTree(fruit);
		}
	}

	/**
	 * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
	 *
	 * @param actor the Actor to check
	 * @return true
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return true;
	}
}
