package game.ground;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.utils.UserInput;
import game.items.BrachiosaurEgg;
import game.items.Fruit;
import game.items.PterodactylEgg;
import game.items.StegosaurEgg;
import game.items.vending.*;


/**
 * Class which represents a vending machine object which player can interact with to buy any items which implement the
 * interface: VendingMachineItemsInterface
 * @see VendingMachineItemsInterface
 * This class extends the Ground class
 * @see Ground
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class VendingMachine extends Ground {
    private Location location;

    /**
     * Constructor
     */
    public VendingMachine() {
        super('V');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        this.location = location;
    }


    /**
     * This method is used to buy items from vending machine
     * @param ecoPts the amount of Eco points that the player has
     * @return will return item bought by player if transaction was successful else will return null
     */

    public static VendingMachineItemsInterface getMenu(int ecoPts){

        // string var containing the menu options
        String menuOptions = "Please choose one of the options below:"+
                "\n1) Buy a Fruit, price: " + Fruit.PRICE +
                "\n2) Buy a Stegosaur Egg, price: " + StegosaurEgg.PRICE +
                "\n3) Buy an Allosaur Egg, price: " + AllosaurEgg.PRICE +
                "\n4) Buy a Brachiosaur Egg, price: " + BrachiosaurEgg.PRICE +
                "\n5) Buy a Veg Meal Kit, price: " + VegMealKit.PRICE +
                "\n6) Buy a Carnivore Meal Kit, price: " + CarnivoreMealKit.PRICE +
                "\n7) Buy a Laser Gun, price: " + LaserGun.PRICE +
                "\n8) Buy a Pterodactyl Egg, price: " + PterodactylEgg.PRICE +
                "\n9) Cancel Transaction";

        String prompt = "Select an option: ";

        // get user input for the choice number
        int choice = UserInput.getIntegerInput(menuOptions, prompt);
        if (choice >= 9){
            System.out.println("transaction cancelled");
            return null;
        }
        VendingMachineItemsInterface item = getItem(ecoPts, choice); // check if player can afford selected item

        if (item == null){
            System.out.println("ERROR: Insufficient Funds or Incorrect Input");
            return null;
        }
        else {
            return item;
        }
    }

    /**
     * This method is used to check if player has sufficient funds to buy the selected item from the Vending Machine
     * @param ecoPts the amount of Eco points that the player has
     * @param choice the item that the player wants to buy
     * @return returns the item selected by the player if player can afford item else will return null
     */

    private static VendingMachineItemsInterface getItem(int ecoPts, int choice){
        VendingMachineItemsInterface item = null;
        if (choice == 1){
            if (ecoPts >= Fruit.PRICE){
                item = new Fruit("fruit");
            }
        } else if (choice == 2){
            if (ecoPts >= StegosaurEgg.PRICE) {
                item = new StegosaurEgg("StegosaurEgg", 'o');
            }
        }else if (choice == 3){
            if (ecoPts >= AllosaurEgg.PRICE){
                item = new AllosaurEgg("AllosaurEgg", 'o');
            }
        }else if (choice == 4){
            if(ecoPts >= BrachiosaurEgg.PRICE){
                item = new BrachiosaurEgg("BrachiosaurEgg", 'o');
            }
        }else if (choice == 5){
            if(ecoPts >= VegMealKit.PRICE){
                item = new VegMealKit();
            }
        }else if (choice == 6){
            if(ecoPts >= CarnivoreMealKit.PRICE){
                item = new CarnivoreMealKit();
            }
        }
        else if (choice == 7){
            if (ecoPts >LaserGun.PRICE){
                item = new LaserGun();
            }
        }
        else if (choice == 8){
            if (ecoPts >= PterodactylEgg.PRICE) {
                item = new PterodactylEgg("PterodactylEgg", 'o');
            }
        }
        return item;
    }

}
