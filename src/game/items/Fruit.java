package game.items;


import edu.monash.fit2099.engine.Location;
import game.items.vending.VendingMachineItemsInterface;

/**
 * Class representing a Fruit object
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class Fruit extends PortableItem implements VendingMachineItemsInterface {

    @Override
    public void tick(Location location){
        this.rot(location);
    }

    /**
     * PRICE represents the number of Eco points required to be able to buy a Fruit from the Vending Machine
     */
    public static final int PRICE = 30;

    /**
     * Variable which represents the number of turns fruit object spent on ground
     */
    private int timeOnGround;

    /**
     * constructor
     * @param name the name of the item
     */
    public Fruit(String name) {
        super(name, 'q');
    }

    /**
     * method used to increment timeOnGround if Fruit obj is on Ground
     */
    private void rot(Location location){
        if (this.hasCapability(Status.ONGROUND)){
            this.timeOnGround ++;
        }
        if(this.timeOnGround >= 15){
            location.removeItem(this);
        }
    }

    public void changeName(String newName){
        this.name = newName;
    }

    /**
     * Enum class which represents the different states that a Fruit can be in
     */
    public enum Status {
        ONGROUND, INREPO, ONTREE, ONBUSH, EATEN
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





