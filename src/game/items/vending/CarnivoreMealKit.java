package game.items.vending;

import game.items.PortableItem;

/**
 * Extends PortableItem
 * @see PortableItem
 * Implements VendingMachineItemsInterface
 * @see VendingMachineItemsInterface
 * An instance of this class can be fed to the carnivores
 * @author Kamal Varma Joosery
 * @version 2.0.0
 */
public class CarnivoreMealKit extends PortableItem implements VendingMachineItemsInterface {

    /**
     * class variable that represents price of a CarnivoreMealKit
     */
    public static final int PRICE = 500;

    /**
     * Constructor
     */
    public CarnivoreMealKit() {
        super("Carnivore Meal Kit", 'c');
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
