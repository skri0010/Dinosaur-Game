package game.dinosaur;

import edu.monash.fit2099.engine.*;
import game.dinosaur.behaviours.*;
import game.dinosaur.interfaces.Herbivore;
import game.ground.Tree;
import game.items.Fruit;
import game.items.vending.VegMealKit;

import java.util.ArrayList;
import java.util.List;

/**
 * Child class of HerbivoreInterface
 * A herbivourous dinosaur that eats fruits from trees
 * @author Suchit Krishna
 * @version 1.0.0
 */
public class Brachiosaur extends Actor implements Herbivore {

    public Brachiosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }
    /** Array List of all the behaviors a dino can do (0 for unconcious,1 for breeding,2 for hungerB,3 for thirstB */
    private List<Behaviour> behaviour = new ArrayList<Behaviour>();
    private int age;
    private String gender;
    private Location location;
    private int waterLvl;

    /**
     * Constructor.
     * All Brachiosaurs are represented by a 'B' and have 160 hit points.
     *
     * @param name the name of this Brachiosaur
     * @param gender gender of the dinosaur
     * @param isBaby boolean representing where dinosaur is a baby
     */
    public Brachiosaur(String name,String gender,Boolean isBaby) {
        super(name, 'B', 160);
        setHitPoints(100);
        setAge(50);
        setGender(gender);
        setWaterLvl(60);
        if (isBaby) {
            setDisplayChar('b');
            setAge(0);
            setHitPoints(10);
        }
        behaviour.add(new UnconsciousBehaviour(15));
        behaviour.add(new BreedingBehaviour());
        behaviour.add(new HungerBehaviour());
        behaviour.add(new ThirstBehaviour());
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
        if (age == 50){
            this.removeCapability(DinosaurStatus.BABY);
            setDisplayChar('B');
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
            if (this.hitPoints >= 70) {
                if (!this.hasCapability(DinosaurStatus.BREED))
                    this.addCapability(DinosaurStatus.BREED);
                return behaviour.get(1).getAction(this,map);
            } else if (this.hasCapability(DinosaurStatus.PREGNANT))
                return behaviour.get(1).getAction(this, map);
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
        if (this.hitPoints > 140){
            this.removeCapability(DinosaurStatus.HUNGRY);
            return behaviour.get(1).getAction(this,map);
        }
        else if (!this.hasCapability(DinosaurStatus.HUNGRY)) {
            this.addCapability(DinosaurStatus.HUNGRY);
            System.out.println("Brachiosaur at (" + location.x() + "," + location.y() + ") is getting hungry!");
        }
        if (!this.hasCapability(DinosaurStatus.PREGNANT))
            return behaviour.get(2).getAction(this,map);
        return null;
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
            System.out.println("Brachiosaur at (" + location.x() + "," + location.y() + ") is getting thirsty!");
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
            return behaviour.get(0).getAction(this,map);
        }
        return null;
    }

    /**
     * This method is used to drink a specific amount of water that differs within dinosaurs
     * @param map optional parameter for the map location to drink water from a specific location
     */
    @Override
    public Action drinkWater(GameMap map) {
        setWaterLvl(getWaterLvl()+80);
        return null;
    }

    /**
     * This method is used to feed a herbivore a vegMealKit
     * @param vegMealKit an instance of the VegMealKit class
     * @see VegMealKit
     */
    @Override
    public void eatVegMeal(VegMealKit vegMealKit) {
        this.heal(160);
    }

    /**
     * This mesthod is used whenever a herbivore eats a Fruit object
     * @param fruit an instance of the Fruit class (Should be null if you use fruit location)
     * @param fruitLocation an instnace of the fruits location (should be null if you use the actual fruit)
     * @see Fruit
     */
    @Override
    public void eatFruit(Fruit fruit, Location fruitLocation) {
        if (fruit != null) {
            if (fruit.hasCapability(Fruit.Status.INREPO)) {
                fruit.removeCapability(Fruit.Status.INREPO);
                fruit.addCapability(Fruit.Status.EATEN);
                this.heal(20);
            }
        }
        if (fruitLocation != null) {
            if (fruitLocation.getGround() instanceof Tree) {
                for (Fruit item : ((Tree) fruitLocation.getGround()).getFruitsOnTree()) {
                    fruit = item;
                    Tree tree = (Tree) fruitLocation.getGround();
                    if (fruitLocation.getItems().size() > 0 && fruitLocation.getItems().get(0) instanceof Fruit) {
                        if (fruit.hasCapability(Fruit.Status.ONTREE)) {
                            fruit.removeCapability(Fruit.Status.ONTREE);
                            fruit.addCapability(Fruit.Status.EATEN);
                            this.heal(5);
                            tree.removeFruitOnTree(fruit);
                        }
                    }
                }
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
        if (this.waterLvl + waterLvl < 200)
            this.waterLvl = waterLvl;
        else
            this.waterLvl = 200;
    }
}
