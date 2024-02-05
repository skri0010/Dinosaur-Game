package game;

import edu.monash.fit2099.utils.UserInput;

/**
 * A class which will contain the game menu which will be shown to the player at the start of the game
 * @author Kamal Varma Joosery
 * @version 1.0.0
 */
public class GameMenu {

    /**
     * This method is used to show the game menu to the player where player can select the game mode
     * @return an integer representing which game mode player decided to play in. If user keyed in a wrong input,
     * method will return -1 by default.
     */
    public static int showGameMenu(){

        String gameMenuMessage = "Welcome to the Jurassic Park game!!!\nPlease select the Game Mode from the options shown below to: \n" +
                "1) Sandbox\n2) Challenge\nOr press any key to exit";

        int choice = UserInput.getIntegerInput(gameMenuMessage, "Select an option: ");

        if (choice == 1){
            System.out.println("sandbox game loading...");
        }else if (choice ==2){
            System.out.println("Loading Challenge ...");
        }else{
            System.exit(0);
        }
        return choice;
    }
}
