package game.items;

import edu.monash.fit2099.engine.Location;

public class Corpse extends PortableItem{
    private int durability;
    public Corpse(String name, char displayChar) {
        super(name, displayChar);
        durability = 50;
    }
    public void reduceDurability(int damage) {
        if (durability >= 0)
            durability -= damage;
    }

    /**
     * Inform an Item on the ground of the passage of time.
     * This method is called once per turn, if the item rests upon the ground.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        if (this.durability < 0){
            currentLocation.removeItem(this);
        }
    }
}
