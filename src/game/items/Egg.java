package game.items;

import edu.monash.fit2099.utils.Util;
import game.dinosaur.interfaces.DinosaurInterface;

/**
 * Abstract class which extends the class PortableItem
 * @see PortableItem
 * This class will be the parent class of all eggs objects produced by the different types of dinosaurs
 * @author Kamal Varma Joosery
 * @version 2.0.0
 */

public abstract class Egg extends PortableItem{


    /**
     * boolean var to indicate if egg's is in player's possession
     */
    private boolean inPlayerPossession;

    /**
     * counter to know how many turns are left for egg to hatch
     */
    private int hatchCounter;

    /**
     * constructor for the Egg class
     * @param name name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     */
    public Egg(String name, char displayChar) {
        super(name, displayChar);
    }


    public void setInPlayerPossession(boolean inPlayerPossession) {
        this.inPlayerPossession = inPlayerPossession;
    }

    public int getHatchCounter() {
        return hatchCounter;
    }


    /**
     * method used to increment the hatchCounter attribute of Egg (object provided it's not in player's possession)
     */
    public void incrementHatchCounter() {
        this.hatchCounter += 1;
    }

    /**
     * Method called when egg hatch to create a new baby dinosaur
     * @return a newborn dinosaur
     */
    public abstract DinosaurInterface hatch();

    /**
     * This method is used to generate the gender of the dinosaur when the egg hatches randomly
     * There's a 50-50 chance for dinosaur to be female/male
     * @return it returns a string which indicates if dinosaur is 'Male' or 'Female'
     */
    public String generateGender(){
        String gender;
        // generate random number so that there's 50-50 chance for the gender
        double randomNumber = Util.getProbability();

        if (randomNumber<=50){
            gender = "Male";
        }
        else {
            gender = "Female";
        }
        return gender;
    }


}
