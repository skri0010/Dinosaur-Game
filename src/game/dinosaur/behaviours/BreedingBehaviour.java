package game.dinosaur.behaviours;

import edu.monash.fit2099.engine.*;
import game.dinosaur.Pterodactyl;
import game.ground.Tree;
import game.items.vending.AllosaurEgg;
import game.items.BrachiosaurEgg;
import game.dinosaur.DinosaurStatus;
import game.items.StegosaurEgg;
import game.items.PterodactylEgg;

import java.util.ArrayList;

/**
 * A class that figures out the distance to a mate and perform the breeding action
 * as well as the layEggAction when dinosaur is pregnant
 * @version 1.0.0
 * @author Suchit Krishna
 */
public class BreedingBehaviour extends Behaviour {
    /** Counter to check how long dinsosaur has been pregnant for*/
    private int breedCounter;

    /**
     * The breeding action method that directs dino to layeggaction or moves dinosaur
     * closer to the mate and finally makes them breed
     * @param actor actor to perform action
     * @param map current game map
     * @return follow partner action if not near and wander behavior if cannot be followed
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (actor.hasCapability(DinosaurStatus.PREGNANT)) {
            return layEggAction(actor, map);
        }
        Location idealLocation = findMate(actor,map);
        if (idealLocation == null)
            return new WanderBehaviour().getAction(actor,map);
        FollowBehaviour followPartner = new FollowBehaviour(idealLocation.getActor());
        Action follow = followPartner.getAction(actor,map);

        if (follow == null){
            if (actor.getGender().equalsIgnoreCase("female") && !actor.hasCapability(DinosaurStatus.PREGNANT))
            {
                actor.addCapability(DinosaurStatus.PREGNANT);
            }
            return new WanderBehaviour().getAction(actor,map);
        }
        return follow;
    }

    /**
     * This method finds the location of the partner closest to the actor
     * @param actor actor to perform action
     * @param map current game map
     * @return location of the ideal mate
     */
    public Location findMate(Actor actor,GameMap map){
        ArrayList<Location> locations = super.getLocationsOnMap(map);
        Location subject = map.locationOf(actor);

        Location idealLocation = null;
        int minDistance = 0;
        for (Location endPoint: locations) {
            if (endPoint.getActor() != null) {
                if (endPoint.getActor().getDisplayChar() == actor.getDisplayChar()) {
                    if (!endPoint.getActor().getGender().equalsIgnoreCase(actor.getGender()) &&
                            endPoint.getActor().hasCapability(DinosaurStatus.BREED))
                        if (super.distance(subject, endPoint) <= minDistance || minDistance == 0) {
                            minDistance = super.distance(subject, endPoint);
                            idealLocation = endPoint;
                        }
                }
            }
        }
        return idealLocation;
    }
    /**
     * the layeggaction that lays and egg based on the breeding counter and type of dinosaur
     * @param actor actor to perform action
     * @param map current game map
     * @return  DropItemAction if dino can lay egg or find food otherwise
     */
    public Action layEggAction(Actor actor,GameMap map){
        this.breedCounter += 1;
        if (actor.getDisplayChar() == 'D'){
            if (breedCounter == 10){
                StegosaurEgg egg = new StegosaurEgg("Stegosaur egg",'o',false);
                actor.addItemToInventory(egg);
                actor.removeCapability(DinosaurStatus.PREGNANT);
                this.breedCounter = 0;
                return new DropItemAction(egg);
            }
        }
        else if (actor.getDisplayChar() == 'B'){
            if (breedCounter == 30){
                BrachiosaurEgg egg = new BrachiosaurEgg("Brachiosaur egg",'o',false);
                actor.addItemToInventory(egg);
                actor.removeCapability(DinosaurStatus.PREGNANT);
                this.breedCounter = 0;
                return new DropItemAction(egg);
            }
        }
        else if (actor.getDisplayChar() == 'A'){
            if (breedCounter == 20){
                AllosaurEgg egg = new AllosaurEgg("Allosaur egg",'o',false);
                actor.addItemToInventory(egg);
                actor.removeCapability(DinosaurStatus.PREGNANT);
                this.breedCounter = 0; // reset
                return new DropItemAction(egg);
            }
        }
        else if (actor.getDisplayChar() == 'P'){
            System.out.println("Breed counter " + breedCounter);
            if (breedCounter >= 20 && map.locationOf(actor).getGround() instanceof Tree){
                PterodactylEgg egg = new PterodactylEgg("Pterodactyl egg",'o',false);
                actor.addItemToInventory(egg);
                actor.removeCapability(DinosaurStatus.PREGNANT);
                this.breedCounter = 0; // reset
                return new DropItemAction(egg);
            }
            else if (breedCounter > 20){
                Location treeLocation = findLayLocation(actor,map);
                return new FollowBehaviour(treeLocation).getFollowLocationAction(actor,map);
            }
        }
        return new HungerBehaviour().getAction(actor,map);
    }

    /**
     * This method finds the location of the type of ground that a dinosaur can lay its eggs in
     * @param actor actor to perform action
     * @param map current game map
     * @return idealLocation to lay an egg on
     */
    public Location findLayLocation(Actor actor, GameMap map){
        ArrayList<Location> locations = super.getLocationsOnMap(map);
        Location subject = map.locationOf(actor);

        Location idealLocation = null;
        int minDistance = 0;
        for (Location endPoint: locations) {
            if (endPoint.getGround() instanceof Tree && actor instanceof Pterodactyl){
                if (super.distance(subject, endPoint) <= minDistance || minDistance == 0) {
                    minDistance = super.distance(subject, endPoint);
                    idealLocation = endPoint;
                }
            }
        }
        return idealLocation;

    }
}
