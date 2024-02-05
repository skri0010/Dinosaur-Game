package game.dinosaur.interfaces;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.dinosaur.Allosaur;
import game.dinosaur.Brachiosaur;
import game.dinosaur.Stegosaur;
import game.dinosaur.behaviours.ThirstBehaviour;
import game.dinosaur.behaviours.UnconsciousBehaviour;

public interface DinosaurInterface {
    /**
     * This method is used to breed two dinosaurs of different genders
     * @param location is the current location of the dinosaur
     * @see Allosaur
     * @see Stegosaur
     * @see Brachiosaur
     * @return breeding action
     */
    Action breeding(Location location, GameMap map);

    /**
     * This method is used to move the dinosaur near food location when its hungry
     * @see Allosaur
     * @see Stegosaur
     * @see Brachiosaur
     * @return move to food action
     */
    Action moveToFood(Location location, GameMap map);


    /**
     * This method is used to move the dinosaur near a water location when its thirsty
     *
     * @param location
     * @see ThirstBehaviour
     * @return returns ThirstBehaviourAction or null if not thirsty
     */
    Action moveToWater(Location location, GameMap map);

    /**
     * This method is used to handle what happens when a dinosaur becomes unconcious
     *
     * @param map the current game map of the actor
     * @see UnconsciousBehaviour
     * @return the unconscious action
     */
    Action unconscious(GameMap map);

    /**
     * This method is used to drink a specific amount of water that differs within dinosaurs
     * @param map optional parameter for the map location to drink water from a specific location
     */
    Action drinkWater(GameMap map);

    /**
     * This method is used to increase the waterLvl of dinos by a certain amount (usually because of rain)
     * @param waterLvl the amount of water to increase the waterLvl by
     */
    void increaseWaterLevel(int waterLvl);

}
