package edu.monash.fit2099.utils;
import java.util.Random;
/**
 * Utility class for the entire game package
 * @author Suchit Krishna
 * @version 1.0.0
 */
public class Util {

    /**
     * Method to get a double value generated at random which is out of 100
     */
    public static double getProbability() {
        Random probability = new Random();
        double chance = probability.nextDouble() * 100;
        return chance;
    }
}