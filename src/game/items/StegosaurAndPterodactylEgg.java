package game.items;

import game.items.vending.VendingMachineItemsInterface;

/**
 * This class represents the egg laid by a Stegosaur and a Pterodactyl
 * Child class of Egg class
 * @see Egg
 * Implements the interface: VendingMachineItemsInterface
 * @see VendingMachineItemsInterface
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public abstract class StegosaurAndPterodactylEgg extends Egg implements VendingMachineItemsInterface {


    /**
     * bonus is a class variable that represents the amount of Eco points player will get on current turn
     * for each StegosaurEgg and PterodactylEgg that hatches
     */
    public static int bonus = 0;

    /**
     * PRICE represents the number of Eco points required to be able to buy a StegosaurEgg or a PterodactylEgg
     * from the Vending Machine
     */
    public static final int PRICE = 200;

    /**
     * TIME_TAKEN_TO_HATCH represents the number of turns it takes for a StegosaurEgg or a PterodactylEgg to hatch
     */
    public static final int TIME_TAKEN_TO_HATCH = 20;  // will need to do trial & error to find the perfect value


    /**
     * constructor
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     */
    public StegosaurAndPterodactylEgg(String name, char displayChar){
        super(name, displayChar);
    }

    /**
     * constructor
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param possession boolean variable to indicate if egg is in player's possession
     */
    public StegosaurAndPterodactylEgg(String name, char displayChar, boolean possession){
        super(name, displayChar);
        this.setInPlayerPossession(possession);
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
