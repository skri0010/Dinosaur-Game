package game.dinosaur;

import edu.monash.fit2099.engine.*;
import game.*;
import game.dinosaur.behaviours.*;
import game.dinosaur.interfaces.Carnivore;
import game.items.Egg;
import game.items.PterodactylEgg;
import game.items.vending.AllosaurEgg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Child class of CarnivoreInterface
 * A carnivorous dinosaur in the game that eats dinos, corpses and eggs
 * @author Suchit Krishna
 * @version 1.0.0
 */

public class Allosaur extends Actor implements Carnivore {
    /** Array List of all the behaviors a dino can do (0 for unconcious,1 for breeding,2 for hungerB,3 for thirstB */
    private ArrayList<Behaviour> behaviour = new ArrayList<>();;
    private int age;
    private String gender;
    private Location location;
    private int waterLvl;
    /** Hash map to keep track of all the stegosaur that have been attacked by this dinosaur*/
    private HashMap<Stegosaur,Integer> attackedStegosaur = new HashMap<>();

    /**
     * Constructor.
     * All Allosaurs are represented by a 'A' and have 100 hit points.
     *
     * @param name the name of this dinosaur
     * @param gender gender of the dinosaur
     * @param isBaby boolean representing where dinosaur is a baby
     */
    public Allosaur(String name,String gender,Boolean isBaby) {
        super(name, 'A', 100);
        setHitPoints(50);
        setAge(30);
        setGender(gender);
        setWaterLvl(60);
        if (isBaby) {
            setDisplayChar('a');
            setAge(0);
            setHitPoints(20);
            this.addCapability(DinosaurStatus.BABY);
        }
        behaviour.add(new UnconsciousBehaviour(20));
        behaviour.add(new BreedingBehaviour());
        behaviour.add(new HungerBehaviour());
        behaviour.add(new ThirstBehaviour());

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
        this.age+=1;
        if (age == 50){
            this.removeCapability(DinosaurStatus.BABY);
            setDisplayChar('A');
        }
        this.location = map.locationOf(this);
        setWaterLvl(getWaterLvl()-1);
        this.hurt(1);
        updateAttackCounter();
        eatDeadDinosaur();
        eatEgg();
        if (unconscious(map)!= null)
            return unconscious(map);
        Action breedingAction = breeding(location,map);
        if (breedingAction != null){
            return breedingAction;
        }
        Action thirst = moveToWater(location,map);
        if (thirst!= null)
        {
            return thirst;
        }
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
            if (this.hitPoints >= 50) {
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
            if (!this.hasCapability(DinosaurStatus.HUNGRY)) {
                this.addCapability(DinosaurStatus.HUNGRY);
                System.out.println("Allosaur at (" + location.x() + "," + location.y() + ") is getting hungry!");
            }
            if (!this.hasCapability(DinosaurStatus.PREGNANT))
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
            System.out.println("Allosaur at (" + location.x() + "," + location.y() + ") is getting thirsty!");
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
     * This method is used so that allosaur can attack a stegosaur or Pterodactyl.
     * The allosaur will only attack a stegosaur if in the previous 20 turns it didn't attack that stegosaur
     *
     */
    @Override
    public Action eatMeat() {
        List<Exit> adjacentLocations = this.location.getExits();
        for (Exit adjacentLocation : adjacentLocations) {
            Location currentLocation = adjacentLocation.getDestination();

            if (currentLocation.getActor() instanceof Stegosaur) {
                Stegosaur prey = (Stegosaur) currentLocation.getActor();
                Integer numberOfTurnsLeft = attackedStegosaur.get(prey);
                if (numberOfTurnsLeft == null || numberOfTurnsLeft == 0){
                    attackedStegosaur.put(prey, 20);
                    this.heal(getIntrinsicWeapon().damage());
                    return new AttackAction(currentLocation.getActor());
                }
            }
            if (currentLocation.getActor() instanceof Pterodactyl) {
                Pterodactyl pterodactyl = (Pterodactyl)currentLocation.getActor();
                if (!pterodactyl.hasCapability(DinosaurStatus.FLYING)) {
                    Action attackAction = new AttackAction(currentLocation.getActor());
                    location.map().removeActor(currentLocation.getActor());
                    this.heal(this.maxHitPoints);
                    return attackAction;
                }
            }
        }

        return null;
    }
    /**
     * This method is used to update the counters of all the stegosaurs that the current allosaur has attcked
     * In each turn, we reduce the Integer value associated with each stegosaur by 1.
     * This integer value represents the number of turns the current allosaur has to wait till it can attack
     * the very same stegosaur again
     */
    private void updateAttackCounter(){
        Set<Stegosaur> keys = attackedStegosaur.keySet();
        for (Stegosaur key:keys){
            Integer newVal = attackedStegosaur.get(key) - 1;
            if (newVal < 0){
                newVal = 0;
            }
            attackedStegosaur.put(key, newVal);
        }
    }

    /**
     * This method is called when a carnivore eats the corpse of a dinosaur
     */
    @Override
    public Action eatDeadDinosaur() {
        for (Item item : location.getItems()){
            if (item.getDisplayChar() == '$' && !this.hasCapability(DinosaurStatus.BABY)){
                this.heal(100);
                location.map().locationOf(this).removeItem(item);
                break;
            }
            else if (item.getDisplayChar() == '%' || item.getDisplayChar() == '&' ){
                this.heal(50);
                location.map().locationOf(this).removeItem(item);
                break;
            }
            else if (item.getDisplayChar() == '!'){
                this.heal(30);
                location.map().locationOf(this).removeItem(item);
                break;
            }
        }
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
            if (item instanceof Egg && !this.hasCapability(DinosaurStatus.BABY)&& !(item instanceof AllosaurEgg)){
                this.heal(10);
                location.removeItem(item);
            }
        }

    }
    /**
     * This method returns how much damage a allosaur will do based on the age of the dino
     *
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon(){
        if (this.hasCapability(DinosaurStatus.BABY))
            return new IntrinsicWeapon(10, "attacks");
        return new IntrinsicWeapon(20, "attacks");
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
