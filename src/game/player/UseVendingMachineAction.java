package game.player;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.ground.VendingMachine;
import game.items.Fruit;
import game.items.vending.VendingMachineItemsInterface;

/**
 * Special Action which allows player to use the vending machine
 * @version 1.0.0
 */
public class UseVendingMachineAction extends Action {

    Player player;
    public UseVendingMachineAction(Player player) {
        this.player = player;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        boolean status = boughtItem(VendingMachine.getMenu(player.getEcoPts()));
        if (status) {
            return "transaction successful";
        }
        return "";
    }

    /**
     * This method is used to store the item bought by the player in the player's possession
     * @param item the item bought by the player from the vending machine
     */
    private boolean boughtItem(VendingMachineItemsInterface item){
        boolean successful = false;
        if (item != null) {
            player.deductEcoPts(item.getItemPrice());
            if (item instanceof Fruit) {
                Fruit fruit = (Fruit) item;
                fruit.addCapability(Fruit.Status.INREPO);
            }
            player.addItemToInventory((Item)item);
            successful = true;
        }
        return successful;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses Vending Machine";
    }

    @Override
    public String hotkey() {
        return "b";
    }
}
