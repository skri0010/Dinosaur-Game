package game.dinosaur.interfaces;

import edu.monash.fit2099.engine.Location;
import game.items.Fruit;
import game.items.vending.VegMealKit;

/**
 * Interface extends the DinosaurInterface
 * @see DinosaurInterface
 * Interface that will need to be implemented by all herbivores
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public interface Herbivore extends DinosaurInterface {
    /**
     * This method is used to feed a herbivore a vegMealKit
     * @param vegMealKit an instance of the VegMealKit class
     * @see VegMealKit
     */
    void eatVegMeal(VegMealKit vegMealKit);

    /**
     * This mesthod is used whenever a herbivore eats a Fruit object
     * @param fruit an instance of the Fruit class
     * @see Fruit
     */
    void eatFruit(Fruit fruit, Location fruitLocation);

}
