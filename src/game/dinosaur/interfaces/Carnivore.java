package game.dinosaur.interfaces;


import edu.monash.fit2099.engine.Action;

/**
 * Interface extends the DinosaurInterface
 * @see DinosaurInterface
 * Interface that will need to be implemented by all carnivores
 * @author Kamal Varma Joosery
 * @version 2.0.0
 */
public interface Carnivore extends DinosaurInterface {

    /**
     * This method is used to feed a carnivore a CarnivoreMealKit
     */
    void eatCarnivoreMeal();

    /**
     * This method is called whenever a carnivore locates its food source and continues to carry out tbe action specific
     * to the carnivore type to increase its food level
     */
    Action eatMeat();

    /**
     * This method is called when a carnivore eats the corpse of a dinosaur
     */
    Action eatDeadDinosaur();

    /**
     * This method is called when a carnivore eats an egg
     */
    void eatEgg();

}
