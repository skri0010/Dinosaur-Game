package game.ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.utils.Util;
import game.dinosaur.Brachiosaur;
import game.items.Fruit;

import java.util.ArrayList;
/**
 * Bush class that bares and stores fruit as a food source
 * @author Suchit Krishna
 * @version 1.0.0
 */

public class Bush extends Ground {
    private ArrayList<Fruit> fruitsOnBush = new ArrayList<>();  // represent all fruits on the bush
    private Location location;

    /**
     * Constructor for the bush display character
     */
    public Bush() {
        super('W');
    }

    /**
     * tick method called every turn to show the evolution of a bush
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        this.location = location;
        double chance = Util.getProbability();
        createFruit(chance);
        actorInBush(location,chance);
    }


    public Location getLocation(){return this.location;}

    /**
     * Creates a fruit in the bush depending on the chance
     * @param chance a probabilty value out of 100
     */
    public void createFruit(double chance){
        if (chance <= 10){
            Fruit fruit = new Fruit("fruit on bush");
            fruit.addCapability(Fruit.Status.ONBUSH);
            fruitsOnBush.add(fruit);
        }
    }

    public ArrayList<Fruit> getFruitsOnBush() {
        // prevent Privacy leak
        return new ArrayList<>(fruitsOnBush);
    }

    /**
     * This method is used to remove a fruit obj on the bush
     */
    public void removeFruitOnBush(Fruit fruit){
        fruitsOnBush.remove(fruit);
    }

    /**
     * Performs certain actions if an actor is detected inside the bush
     * right now only deletes bush instance if brachiosaur detected
     * @param chance a probabilty value out of 100
     */
    public void actorInBush(Location location, double chance){
        if (location.getActor() instanceof Brachiosaur && chance <= 50){
            Dirt dirtWithBush = new Dirt();
            dirtWithBush.deleteBush(location);
        }
    }
    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }
}
