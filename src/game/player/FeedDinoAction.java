package game.player;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.utils.UserInput;
import game.dinosaur.interfaces.Carnivore;
import game.dinosaur.interfaces.Herbivore;
import game.items.Fruit;
import game.items.vending.CarnivoreMealKit;
import game.items.vending.VegMealKit;

import java.util.List;

/**
 * Special Action which allows player to Feed a dino
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class FeedDinoAction extends Action {

    Player player;
    List<Item> inventory;
    Actor dino;

    public FeedDinoAction(Player player, Actor dino) {
        this.player = player;
        inventory = player.getInventory();
        this.dino = dino;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return feedDinoHelper(dino);
    }

    /**
     * This method is used by the player to feed a dinosaur
     * @param dino a dinosaur instance
     */
    private String feedDinoHelper(Actor dino){
        String status = "";
        // check if dino is a Carnivore
        if (dino instanceof Carnivore){
            Carnivore carnivoreDino = (Carnivore) dino;
            status = feedCarnivore(carnivoreDino);
        }
        // check if dino is a Herbivore
        else if (dino instanceof Herbivore){
            Herbivore herbivoreDino = (Herbivore) dino;
            status = feedHerbivore(herbivoreDino);
        }
        return status;
    }


    /**
     * This method is used to feed a carnivorous dinosaur
     * @param dino an instance of a carnivorous dinosaur
     */
    private String feedCarnivore(Carnivore dino){
        CarnivoreMealKit cmk = null;
        int i = 0;
        boolean successfullyFeed = false;
        String status = "successfully fed " + dino;

        while (i < inventory.size() && cmk == null){
            if (inventory.get(i) instanceof CarnivoreMealKit){
                cmk = (CarnivoreMealKit) inventory.get(i);
                player.removeItemFromInventory(cmk);
                dino.eatCarnivoreMeal();
                successfullyFeed = true;
            }
            i ++;
        }
        if (!successfullyFeed){
            status = "ERROR: You don't have any CarnivoreMealKits";
        }
        return status;
    }


    /**
     * This method is used to feed a herbivorous dinosaur
     * @param dino an instance of a herbivorous dinosaur
     */
    private String feedHerbivore(Herbivore dino){
        int option = getOption();
        boolean flag;
        String status = "successfully fed " + dino;

        if (option == 1){
            flag = feedFruitToHerbivore(dino);
            if (flag){
                player.incrementEcoPts(10);
            }
            else {
                status = "ERROR: No fruit in inventory";
            }
        }
        else if (option == 2){
            flag = feedVegMealKitToHerbivore(dino);
            if (! flag){
                status = "ERROR: You don't have any VegMealKits";
            }
        }
        else{
            status = "ERROR INVALID INPUT: Couldn't feed " + dino;
        }
        return status;
    }


    /**
     * This method is used to feed a Fruit a herbivorous dinosaur
     * @param dino an instance of a herbivorous dinosaur
     * @return a boolean variable to indicate if operation was successful
     */
    private boolean feedFruitToHerbivore(Herbivore dino){
        Fruit fruit = null;
        int i = 0;
        boolean successfullyFeed = false;

        while (i < inventory.size() && fruit == null){

            if (inventory.get(i) instanceof Fruit){
                fruit = (Fruit) inventory.get(i);
                player.removeItemFromInventory(fruit);

                // make sure fruit only has capability INREPO
                fruit.removeCapability(Fruit.Status.ONGROUND);
                fruit.removeCapability(Fruit.Status.ONBUSH);
                fruit.removeCapability(Fruit.Status.ONTREE);
                fruit.addCapability(Fruit.Status.INREPO);

                dino.eatFruit(fruit, null);
                successfullyFeed = true;
            }
            i ++;
        }
        return successfullyFeed;
    }


    /**
     * This method is used to feed a VegMealKit a herbivorous dinosaur
     * @param dino an instance of a herbivorous dinosaur
     * @return a boolean variable to indicate if operation was successful
     */
    private boolean feedVegMealKitToHerbivore(Herbivore dino){
        boolean successfullyFeed = false;
        VegMealKit vmk = null;
        int i = 0;


        while (i < inventory.size() && vmk == null){

            if (inventory.get(i) instanceof VegMealKit){
                vmk = (VegMealKit) inventory.get(i);
                player.removeItemFromInventory(vmk);

                dino.eatVegMeal(vmk);
                successfullyFeed = true;
            }
            i ++;
        }
        return successfullyFeed;
    }


    /**
     * This method is used to know which item player wants to feed to a herbivorous dinosaur
     * @return an integer representing the selected option. If user entered an invalid key, method will return 0
     */
    private int getOption(){
        String info = "Please select 1 of the options below: \n" + "1) Feed dino a Fruit\n" +
                "2) Feed dino a Veg Meal Kit";

        String prompt = "Select an option: ";

        return UserInput.getIntegerInput(info, prompt);
    }


    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds " + dino;
    }


    @Override
    public String hotkey() {
        return "f";
    }
}
