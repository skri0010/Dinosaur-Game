package edu.monash.fit2099.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class which is used to get an input from user
 * @author Kamal Varma Joosery
 * @version 2.0.0
 */

public class UserInput {


    /**
     * This method is used to prompt the user for an integer input
     * @param info contains the information of what's being asked to the user
     * @param promptMessage the prompt message which will be shown to the user
     * @return the integer the user keyed in. If user gave wrong input type, method will return -1
     */
    public static int getIntegerInput(String info, String promptMessage){
        Scanner input = new Scanner(System.in);
        System.out.println(info);
        System.out.print(promptMessage);
        int choice;

        try {
            choice = input.nextInt();
        }catch (InputMismatchException e){
            choice = -1;
        }
        return choice;
    }


}
