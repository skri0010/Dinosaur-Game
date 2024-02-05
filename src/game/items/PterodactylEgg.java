package game.items;

import edu.monash.fit2099.engine.Location;
import game.dinosaur.Pterodactyl;
import game.dinosaur.interfaces.DinosaurInterface;

/**
 * This class represents the egg laid by a Pterodactyl
 * Child class of StegosaurAndPterodactylEgg class
 * @see StegosaurAndPterodactylEgg
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class PterodactylEgg extends StegosaurAndPterodactylEgg {

    @Override
    public void tick(Location location){

        this.location = location;
        this.incrementHatchCounter();

        Pterodactyl PterodactylBaby = (Pterodactyl) hatch();
        if (PterodactylBaby != null) {
            location.addActor(PterodactylBaby);
            location.removeItem(this); // remove item
            System.out.println("Pterodactyl hatches");
        }
    }

    /**
     * Boolean var to know if egg has hatch
     */
    private boolean hasHatch = false;

    /**
     * Variable representing location of Pterodactyl egg
     */
    private Location location;

    /**
     * constructor
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     */
    public PterodactylEgg(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     * constructor
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param possession boolean variable to indicate if egg is in player's possession
     */
    public PterodactylEgg(String name, char displayChar, boolean possession) {
        super(name, displayChar, possession);
    }

    /**
     * This method is used to hatch the Pterodactyl egg. The egg will hatch only when it's time for it to hatch &
     * provided there's no actor in the same location
     */
    @Override
    public DinosaurInterface hatch() {
        System.out.println("counterBabyPterodactyl: "+getHatchCounter());

        boolean isActorHere = this.location.containsAnActor(); // check if there's an actor in egg's location

        if (! hasHatch && this.getHatchCounter() >= TIME_TAKEN_TO_HATCH && !isActorHere) {

            String gender = this.generateGender();
            hasHatch = true;
            bonus += 100;
            return new Pterodactyl("pterodactyl",gender, true);
        }
        else {
            return null;
        }
    }
}
