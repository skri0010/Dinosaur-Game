package game.dinosaur;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.dinosaur.behaviours.*;
import game.dinosaur.interfaces.Carnivore;
import game.dinosaur.interfaces.DinosaurInterface;
import game.ground.Lake;
import game.ground.Tree;
import game.items.Corpse;
import game.items.Egg;
import game.items.PterodactylEgg;

import java.util.ArrayList;
import java.util.List;

/**
 * Child class of CarnivoreInterface
 * A carnivorous dinosaur in the game that eats fish, corpses and eggs and can fly
 * @author Suchit Krishna
 * @version 1.0.0
 */
public class Pterodactyl extends Actor implements Carnivore {
    /** Array List of all the behaviors a dino can do (0 for unconcious,1 for breeding,2 for hungerB,3 for thirstB,
     * 4 for flyB */
    private ArrayList<Behaviour> behaviour = new ArrayList<>();;
    private int age;
    private String gender;
    private Location location;
    private int waterLvl;

    /**
     * Constructor.
     * All Allosaurs are represented by a 'A' and have 100 hit points.
     *
     * @param name the name of this dinosaur
     * @param gender gender of the dinosaur
     * @param isBaby boolean representing where dinosaur is a baby
     */
    public Pterodactyl(String name,String gender,Boolean isBaby) {
        super(name, 'P', 100);
        setHitPoints(60);
        setAge(30);
        setGender(gender);
        setWaterLvl(60);
        if (isBaby) {
            setDisplayChar('p');
            setAge(0);
            setHitPoints(20);
            this.addCapability(DinosaurStatus.BABY);
        }
        behaviour.add(new UnconsciousBehaviour(20));
        behaviour.add(new BreedingBehaviour());
        behaviour.add(new HungerBehaviour());
        behaviour.add(new ThirstBehaviour());
        behaviour.add(new FlyBehaviour(30));

    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        age+=1;
        if (age == 50){
            this.removeCapability(DinosaurStatus.BABY);
            setDisplayChar('P');
        }
        setWaterLvl(getWaterLvl()-1);
        this.location = map.locationOf(this);
        this.hurt(1);
        if (unconscious(map)!= null)
            return unconscious(map);
        Action flyAction = behaviour.get(4).getAction(this,map);
        if (flyAction != null){
            return flyAction;
        }
        Action breedingAction = breeding(location,map);
        if (breedingAction != null){
            return breedingAction;
        }
        Action eatDeadDino = eatDeadDinosaur();
        if (eatDeadDino!= null){
            return eatDeadDino;
        }
        Action thirst = moveToWater(location,map);
        if (thirst!= null)
        {
            return thirst;
        }
        eatEgg();
        Action moveToFood = moveToFood(location,map);
        if (moveToFood!= null) {
            return moveToFood;
        }
        return new WanderBehaviour().getAction(this,map);
    }

    /**
     * This method is used to breed two dinosaurs of different genders
     *
     * @param subject is the current location of the dinosaur
     * @see BreedingBehaviour
     * @return the breedingaction/layeggaction or null if the dino is a baby or is unable to breed
     */
    @Override
    public Action breeding(Location subject, GameMap map) {
        if (!this.hasCapability(DinosaurStatus.BABY)) {
            if (this.hitPoints >= 50 && location.getGround() instanceof Tree) {
                if (!this.hasCapability(DinosaurStatus.BREED))
                    this.addCapability(DinosaurStatus.BREED);
                return behaviour.get(1).getAction(this,map);
            } else if (this.hasCapability(DinosaurStatus.PREGNANT))
                return behaviour.get(1).getAction(this,map);
            else
                this.removeCapability(DinosaurStatus.BREED);
        }
        return null;
    }

    /**
     * This method is used to move the dinosaur near food location when its hungry
     *
     * @param location
     * @see HungerBehaviour
     * @return returns HungerBehaviour action or null if not hungry or not pregnant
     */
    @Override
    public Action moveToFood(Location location, GameMap map) {
        if (this.hitPoints > 90)
            this.removeCapability(DinosaurStatus.HUNGRY);
        else if (!this.hasCapability(DinosaurStatus.HUNGRY)) {
            this.addCapability(DinosaurStatus.HUNGRY);
            System.out.println("Pterodactyl at (" + location.x() + "," + location.y() + ") is getting hungry!");
        }
        if (!this.hasCapability(DinosaurStatus.PREGNANT) && this.hasCapability(DinosaurStatus.HUNGRY))
            return behaviour.get(2).getAction(this,map);
        return null;
    }
    /**
     * This method is used to move the dinosaur near a water location when its thirsty
     *
     * @param location
     * @see ThirstBehaviour
     * @return returns ThirstBehaviourAction or null if not thirsty
     */
    @Override
    public Action moveToWater(Location location, GameMap map) {
        if (this.waterLvl > 40){
            this.removeCapability(DinosaurStatus.THIRSTY);
        }
        else if (!this.hasCapability(DinosaurStatus.THIRSTY)) {
            this.addCapability(DinosaurStatus.THIRSTY);
            System.out.println("Pterodactyl at (" + location.x() + "," + location.y() + ") is getting thirsty!");
        }
        else
            return behaviour.get(3).getAction(this,map);
        return null;
    }

    /**
     * This method is used to handle what happens when a dinosaur becomes unconcious
     *
     * @param map the current game map of the actor
     * @see UnconsciousBehaviour
     * @return the unconcious behavior action
     */
    @Override
    public Action unconscious(GameMap map) {
        if (!this.isConscious()) {
            return behaviour.get(0).getAction(this,map);
        }
        return null;
    }

    /**
     * This method is used to drink a specific amount of water that differs within dinosaurs
     * @param map optional parameter for the map location to drink water from a specific location
     */
    @Override
    public Action drinkWater(GameMap map) {
        setWaterLvl(getWaterLvl()+30);
        return null;
    }

    /**
     * This method is used to feed a carnivore a CarnivoreMealKit
     *
     */
    @Override
    public void eatCarnivoreMeal() {
        this.heal(100);
    }

    /**
     * This method is used so that Pterodactyl can eat the fish from the lake square that it flies over
     *
     */
    @Override
    public Action eatMeat() {
        int fish = (int) (Math.random() * 2);
        if (location.getGround() instanceof Lake){
            Lake lake = (Lake) location.getGround();
            lake.decreaseNumberOfFish(fish);
            this.heal(5);
        }
        return new WanderBehaviour().getAction(this, location.map());
    }

    /**
     * This method is called when a carnivore eats the corpse of a dinosaur
     */
    @Override
    public Action eatDeadDinosaur() {
        boolean danger = false;
        for (Exit exit: location.getExits()){
            if (exit.getDestination().getActor() instanceof DinosaurInterface){
                danger = true;
            }
        }
        if (!danger) {
            List<Item> items = location.getItems();
            for (Item item : items) {
                if (item.getDisplayChar() == '%' || item.getDisplayChar() == '&' || item.getDisplayChar() == '!'
                        || item.getDisplayChar() == '$' && !this.hasCapability(DinosaurStatus.BABY)) {
                    this.removeCapability(DinosaurStatus.FLYING);
                    this.heal(10);
                    ((Corpse) item).reduceDurability(10);
                    return new DoNothingAction();
                }
            }
        }
        else
            return new WanderBehaviour().getAction(this, location.map());
        return null;
    }
    /**
     * This method is called when a carnivore eats an egg
     *
     */
    @Override
    public void eatEgg() {
        List<Item> items = location.getItems();
        for (Item item : items){
            if (item instanceof Egg && !this.hasCapability(DinosaurStatus.BABY) && !(item instanceof PterodactylEgg)){
                this.heal(10);
                location.removeItem(item);
            }
        }

    }
    private void setAge(int age) {
        this.age = age;
    }

    public String getGender() { return gender; }

    @Override
    public boolean isDehydrated() {
        return this.waterLvl <= 0;
    }

    @Override
    public void increaseWaterLevel(int waterLvl) {
        this.waterLvl += waterLvl;
    }

    private void setGender(String gender) {
        this.gender = gender;
    }

    private void setHitPoints(int hitPoints){
        this.hitPoints = hitPoints;
    }

    private void setDisplayChar(char displayChar){
        this.displayChar = displayChar;
    }

    public int getWaterLvl() {
        return waterLvl;
    }

    public void setWaterLvl(int waterLvl) {
        if (this.waterLvl + waterLvl < 100)
            this.waterLvl = waterLvl;
        else
            this.waterLvl = 100;
    }
}

