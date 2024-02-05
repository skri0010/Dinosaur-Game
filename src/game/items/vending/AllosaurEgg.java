package game.items.vending;

import edu.monash.fit2099.engine.Location;
import game.dinosaur.Allosaur;
import game.dinosaur.interfaces.DinosaurInterface;
import game.items.Egg;

/**
 * This class represents the egg laid by an Allosaur
 * Child class of Egg class
 * @see Egg
 * Implements the interface: VendingMachineItemsInterface
 * @see VendingMachineItemsInterface
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class AllosaurEgg extends Egg implements VendingMachineItemsInterface {

    @Override
    public void tick(Location location){
        this.location = location;
        this.incrementHatchCounter();
        Allosaur allosaurBaby = (Allosaur) hatch();
        if (allosaurBaby != null) {
            location.addActor(allosaurBaby);
            location.removeItem(this); // remove item
            System.out.println("Allo hatches");
        }
    }

    /**
     * bonus is a class variable that represents the amount of Eco points player will get on current turn
     * for each AllosaurEgg that hatches
     */
    public static int bonus = 0;

    /**
     * PRICE represents the number of Eco points required to be able to buy an AllosaurEgg from the Vending Machine
     */
    public static final int PRICE = 1000;

    /**
     * TIME_TAKEN_TO_HATCH represents the number of turns it takes for an AllosaurEgg to hatch
     */
    private static final int TIME_TAKEN_TO_HATCH = 50;

    /**
     * Boolean var to know if egg has hatch
     */
    private boolean hasHatch = false;

    /**
     * Variable representing location of stegosaur egg
     */
    private Location location;

    /**
     * constructor
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     */
    public AllosaurEgg(String name, char displayChar){
        super(name, displayChar);
    }

    /**
     * constructor
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param possession boolean variable to indicate if egg is in player's possession
     */
    public AllosaurEgg(String name, char displayChar, boolean possession){
        super(name, displayChar);
        this.setInPlayerPossession(possession);
    }

    /**
     * This method is used to hatch the allosaur egg. The egg will hatch only when it's time for it to hatch &
     * provided there's no actor in the same location
     */
    @Override
    public DinosaurInterface hatch() {
        System.out.println("counterBabyAllo: "+getHatchCounter());

        boolean isActorHere = this.location.containsAnActor(); // check if there's an actor in egg's location

        if (! hasHatch && this.getHatchCounter() >= TIME_TAKEN_TO_HATCH && !isActorHere) {
            String gender = this.generateGender();
            hasHatch = true;
            bonus += 1000;
            return new Allosaur("allosaur",gender, true);
        }
        else {
            return null;
        }
    }

    /**
     * This method will be used to get price of an egg item
     * @return the cost price of the egg object
     */
    @Override
    public int getItemPrice() {
        return PRICE;
    }

}
