package game.items.vending;

import game.items.PortableItem;

/**
 * Extends PortableItem
 * @see PortableItem
 * Implements VendingMachineItemsInterface
 * @see VendingMachineItemsInterface
 * An instance of this class can be fed to the herbivores
 * @author Kamal Varma Joosery
 * @version 2.0.0
 */

public class VegMealKit extends PortableItem implements VendingMachineItemsInterface {

    /**
     * class variable that represents price of a VegMealKit
     */
    public static final int PRICE = 100;

    /**
     * Constructor
     */
    public VegMealKit() {
        super("Veg Meal Kit", 'v');
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

