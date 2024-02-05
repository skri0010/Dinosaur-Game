package game.items.vending;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * Class which can be used to create LaserGun object
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class LaserGun extends WeaponItem implements VendingMachineItemsInterface {

    /**
     * class variable that represents price of a LaserGun
     */
    public static final int PRICE = 500;

    /**
     * Constructor
     *
     */
    public LaserGun() {
        super("Laser Gun", 'L', 50, "shoots");
        // damage is 50 because it should be able to kill a stegosaur in 1/2 hits
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
