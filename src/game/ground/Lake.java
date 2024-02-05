package game.ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.utils.Util;
import game.RainGenerator;
import game.dinosaur.DinosaurStatus;


/**
 * Lake class that contains Water & Fish
 * Child class of Ground class
 * @see edu.monash.fit2099.engine.Ground
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */

public class Lake extends Ground {

    private int numberOfFish;

    private final static int  MAX_NUMBER_OF_FISH = 25;

    private Location location; // represent the location of the tree object

    private double numberOfSips;

    /**
     * Constructor
     */
    public Lake() {
        super('~');
        this.numberOfSips = 25;
        this.numberOfFish = 5;
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        this.location = location;
//        System.out.println("Number of fish in lake: " + numberOfFish);
//        System.out.println("Number of sips in lake: " + numberOfSips);
        addNewFish();
        increaseNumberOfSips();
    }


    /**
     * This method has a probability of 60% to add a new fish in the current lake
     */
    private void addNewFish(){
        double randomNumber = Util.getProbability();
        if (randomNumber <= 60){
            if (numberOfFish + 1 <= MAX_NUMBER_OF_FISH) {
                this.numberOfFish++;
//                System.out.println("new fish added, fish count is now: " + numberOfFish);
            }
        }
    }

    /**
     * This method fill in the lake whenever it rains
     */
    private void increaseNumberOfSips(){
        if (RainGenerator.raining){
            double multiplier = 0.1 +(Math.random() *0.5);
            numberOfSips += 20 * multiplier;
//            System.out.println("Number of sips increased, new count is: " + numberOfSips);
        }
    }

    /**
     * A method which is used to reduce the number of sips (water) in the current lake, whenever a dinosaur drinks
     * from it
     * @param numberOfSips it's the number by which we should reduce the lake's number of sips
     */
    public void reduceNumberOfSips(double numberOfSips){
        if (this.numberOfSips - numberOfSips >=0) {
            this.numberOfSips -= numberOfSips;
//            System.out.println("Number of sips decreased, new count is: " + this.numberOfSips);
        }
    }

    public int getNumberOfFish() {
        return numberOfFish;
    }

    /**
     * This method is used to decrease the number of fish in the lake, e.g. when dino eats a fish from lake
     * @param decrement the number of fish which needs to be removed
     */
    public void decreaseNumberOfFish(int decrement) {
        if (this.numberOfFish - decrement >= 0) {
            this.numberOfFish -= numberOfFish;
//            System.out.println("fish removed, fish count is now: " + numberOfFish);
        }
    }

    public Location getLocation() {
        return location;
    }

    public double getNumberOfSips() {
        return numberOfSips;
    }

    /**
     * Method used to check if actor can enter the lake
     * @param actor the Actor to check
     * @return true only if actor is a Pterodactyl, else return false
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(DinosaurStatus.FLYING);
    }
}

