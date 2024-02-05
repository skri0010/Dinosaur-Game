package game;

import edu.monash.fit2099.utils.Util;

/**
 * This class is used to generate Rain in the game
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class RainGenerator {
    /**
     * Variable representing if it's currently raining in the game
     */
    public static boolean raining;

    public static boolean hasRain(){
        double randomNumber = Util.getProbability();
        return randomNumber <= 20;
    }
}
