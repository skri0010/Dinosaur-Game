package game.dinosaur.behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import game.RainGenerator;
import game.dinosaur.interfaces.DinosaurInterface;
import game.items.Corpse;
import game.items.PortableItem;

/**
 * Unconscious behavior class for handling what happens to dinosaur when their foodlvl or waterlvl is 0 
 * @author Suchit Krishna
 * @version 1.0.0
 */
public class UnconsciousBehaviour extends Behaviour {
    private int turnsSinceHunger;
    private int turnsSinceDehydrated;
    private final int ageToDeath;

    /**
     * Unconscious behavior constructor
     * @param turns the number of turns it takes for the dinos death
     */
    public UnconsciousBehaviour(int turns) {
        this.ageToDeath = turns;
    }

    /**
     * Unconscious behavior action to return the action performed by actor if unconscious
     * also creates the corpse if actor hungry or dehydrated for too long
     * @param actor actor to perform action
     * @param map current game map
     * @return DoNothing action if unconscious or null if not
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!actor.isConscious()){
            turnsSinceHunger += 1;
        }
        else if (RainGenerator.hasRain()){
            turnsSinceDehydrated = 0;
            ((DinosaurInterface)actor).increaseWaterLevel(10);
        }
        else if (actor.isDehydrated()){
            turnsSinceDehydrated += 1;
        }
        else{
            turnsSinceHunger = 0;
        }
        if (turnsSinceHunger == ageToDeath || turnsSinceDehydrated == 15){
            PortableItem corpse = null;
            if ((actor.getDisplayChar() + "").equalsIgnoreCase("d")) {
                corpse = new Corpse("stegosaur corpse", '%');
            }
            else if ((actor.getDisplayChar() + "").equalsIgnoreCase("b")) {
                corpse = new Corpse("brachiosaur corpse", '$');
            }
            else if ((actor.getDisplayChar() + "").equalsIgnoreCase("a")){
                corpse = new Corpse("allosaur corpse",'&');}
            else if ((actor.getDisplayChar() + "").equalsIgnoreCase("p")){
                corpse = new Corpse("pterodactyl corpse",'!');}
            int x = map.locationOf(actor).x();
            int y = map.locationOf(actor).y();
            map.removeActor(actor);
            map.at(x,y).addItem(corpse);
        }
        return new DoNothingAction();
    }
}
