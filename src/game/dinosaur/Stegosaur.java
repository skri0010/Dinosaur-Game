package game.dinosaur;


import edu.monash.fit2099.engine.*;
import game.*;
import game.dinosaur.behaviours.*;
import game.dinosaur.interfaces.Herbivore;
import game.ground.Bush;
import game.ground.Dirt;
import game.items.Fruit;
import game.items.vending.VegMealKit;

import java.util.ArrayList;
import java.util.List;

/**
 * Child class of HerbivoreInterface
 * A herbivourous dinosaur that eats fruits from the ground or bushes
 * @version 1.0.0
 */
public class Stegosaur extends Actor implements Herbivore {
	/** Array List of all the behaviors a dino can do (0 for unconcious,1 for breeding,2 for hungerB,3 for thirstB */
	private List<Behaviour> behaviour = new ArrayList<Behaviour>();
	private int age;
	private String gender;
	private Location location;
	private int waterLvl;

	/**
	 * Constructor.
	 * All Stegosaurs are represented by a 'D' and have 100 hit points.
	 *
	 * @param name the name of this Dinosaur
	 * @param gender gender of the dinosaur
	 * @param isBaby boolean representing where dinosaur is a baby
	 */
	public Stegosaur(String name,String gender,Boolean isBaby) {
		super(name, 'D', 100);
		setHitPoints(50);
		setAge(30);
		setGender(gender);
		setWaterLvl(60);
		if (isBaby) {
			setDisplayChar('d');
			setAge(0);
			setHitPoints(10);
			this.addCapability(DinosaurStatus.BABY);
		}
		behaviour.add(new UnconsciousBehaviour(20));
		behaviour.add(new BreedingBehaviour());
		behaviour.add(new HungerBehaviour());
		behaviour.add(new ThirstBehaviour());

	}

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions(new AttackAction(this));
	}

	/**
	 * Select and return an action to perform on the current turn.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		this.age+=1;
		if (age == 30){
			this.removeCapability(DinosaurStatus.BABY);
			setDisplayChar('D');
		}
		setWaterLvl(getWaterLvl()-1);
		this.location = map.locationOf(this);
		this.hurt(1);
		if (unconscious(map)!= null)
			return unconscious(map);
		Action breedingAction = breeding(location,map);
		if (breedingAction != null){
			return breedingAction;
		}
		Action thirst = moveToWater(location,map);
		if (thirst!= null)
		{
			return thirst;
		}
		Action moveToFood = moveToFood(location,map);
		if (moveToFood!= null) {
			return moveToFood;
		}
		return new WanderBehaviour().getAction(this,map);
	}

	/**
	 * This method is used to breed two dinosaurs of different genders
	 *
	 * @param subject is the current location of the dinosaur
	 * @see BreedingBehaviour
	 * @return the breedingaction/layeggaction or null if the dino is a baby or is unable to breed
	 */
	@Override
	public Action breeding(Location subject, GameMap map) {
		if (!this.hasCapability(DinosaurStatus.BABY)) {
			if (this.hitPoints >= 50) {
				if (!this.hasCapability(DinosaurStatus.BREED))
					this.addCapability(DinosaurStatus.BREED);
				return behaviour.get(1).getAction(this,map); // return breeding action
			} else if (this.hasCapability(DinosaurStatus.PREGNANT))
				return behaviour.get(1).getAction(this,map);
			else
				this.removeCapability(DinosaurStatus.BREED);
		}
		return null;
	}
	/**
	 * This method is used to move the dinosaur near food location when its hungry
	 *
	 * @param location
	 * @see HungerBehaviour
	 * @return returns HungerBehaviour action or null if not hungry or not pregnant
	 */
	@Override
	public Action moveToFood(Location location, GameMap map) {
		if (this.hitPoints > 90){
			this.removeCapability(DinosaurStatus.HUNGRY);
			return behaviour.get(1).getAction(this,map);} // return locate food action
		if (!this.hasCapability(DinosaurStatus.HUNGRY)) {
			this.addCapability(DinosaurStatus.HUNGRY);
			System.out.println("Stegosaur at (" + location.x() + "," + location.y() + ") is getting hungry!");
		}
		if (!this.hasCapability(DinosaurStatus.PREGNANT))
			return behaviour.get(2).getAction(this,map);
		return new WanderBehaviour().getAction(this,map);
	}

	/**
	 * This method is used to move the dinosaur near a water location when its thirsty
	 *
	 * @param location
	 * @see ThirstBehaviour
	 * @return returns ThirstBehaviourAction or null if not thirsty
	 */
	@Override
	public Action moveToWater(Location location, GameMap map) {
		if (this.waterLvl > 40){
			this.removeCapability(DinosaurStatus.THIRSTY);
		}
		else if (!this.hasCapability(DinosaurStatus.THIRSTY)) {
			this.addCapability(DinosaurStatus.THIRSTY);
			System.out.println("Stegosaur at (" + location.x() + "," + location.y() + ") is getting thirsty!");
		}
		else
			return behaviour.get(3).getAction(this,map);
		return null;
	}

	/**
	 * This method is used to handle what happens when a dinosaur becomes unconcious
	 *
	 * @param map the current game map of the actor
	 * @see UnconsciousBehaviour
	 * @return the unconcious behavior action 
	 */
	@Override
	public Action unconscious(GameMap map) {
		if (!this.isConscious()) {
			return behaviour.get(0).getAction(this,map); // return unconscious action
		}
		return null;
	}

	/**
	 * This method is used to drink a specific amount of water that differs within dinosaurs
	 * @param map optional parameter for the map location to drink water from a specific location
	 */
	@Override
	public Action drinkWater(GameMap map) {
		setWaterLvl(getWaterLvl()+30);
		return null;
	}

	/**
	 * This method is used to feed a herbivore a vegMealKit
	 * @param vegMealKit an instance of the VegMealKit class
	 * @see VegMealKit
	 */
	@Override
	public void eatVegMeal(VegMealKit vegMealKit) {
		this.heal(100);
	}

	/**
	 * This mesthod is used whenever a herbivore eats a Fruit object
	 * @param fruit an instance of the Fruit class (Should be null if you use fruit location)
	 * @param fruitLocation an instnace of the fruits location (should be null if you use the actual fruit)
	 * @see Fruit
	 */
	@Override
	public void eatFruit(Fruit fruit, Location fruitLocation) {
		if (fruitLocation != null) {
			if (fruitLocation.getGround() instanceof Bush) {
				ArrayList<Fruit> fruits = ((Bush) fruitLocation.getGround()).getFruitsOnBush();
				if (fruits.size() > 0) {
					fruit = fruits.get(0);
					((Bush) fruitLocation.getGround()).removeFruitOnBush(fruit);
				}
			}
			if (fruitLocation.getGround() instanceof Dirt) {
				List<Item> fruits = fruitLocation.getItems();
				if (fruits.size() > 0 && fruits.get(0) instanceof Fruit) {
					fruit = (Fruit) fruits.get(0);
					fruitLocation.removeItem(fruit);
				}
			}
		}
		if (fruit != null) {
			if (fruit.hasCapability(Fruit.Status.ONBUSH) || fruit.hasCapability(Fruit.Status.ONGROUND)) {
				fruit.removeCapability(Fruit.Status.ONBUSH);
				fruit.removeCapability(Fruit.Status.ONGROUND);
				fruit.addCapability(Fruit.Status.EATEN);
				this.heal(10);
			} else if (fruit.hasCapability(Fruit.Status.INREPO)) {
				fruit.removeCapability(Fruit.Status.INREPO);
				fruit.addCapability(Fruit.Status.EATEN);
				this.heal(20);
			}
		}
	}

	private void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	@Override
	public boolean isDehydrated() {
		return this.waterLvl <= 0;
	}

	@Override
	public void increaseWaterLevel(int waterLvl) {
		this.waterLvl += waterLvl;
	}

	private void setGender(String gender) {
		this.gender = gender;
	}

	private void setHitPoints(int hitPoints){
		this.hitPoints = hitPoints;
	}

	private void setDisplayChar(char displayChar){
		this.displayChar = displayChar;
	}

	public int getWaterLvl() {
		return waterLvl;
	}

	public void setWaterLvl(int waterLvl) {
		if (this.waterLvl + waterLvl < 100)
			this.waterLvl = waterLvl;
		else
			this.waterLvl = 100;
	}
}
