package game.player;

import edu.monash.fit2099.engine.*;
import game.Application;
import game.GameMenu;
import game.RainGenerator;
import game.dinosaur.interfaces.DinosaurInterface;
import game.ground.Bush;
import game.ground.Tree;
import game.ground.VendingMachine;
import game.items.vending.*;
import edu.monash.fit2099.utils.UserInput;
import game.items.*;

import java.util.List;


/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private Menu menu = new Menu();

	/**
	 * Variable which represents the number of eco pts currently in player's possession
	 */
	private int ecoPts;

	/**
	 * Variable which represents the first map (i.e. the map on which player starts at the beginning of the game)
	 */
	private GameMap firstMap;

	/**
	 * Variable which represents the second map
	 */
	private GameMap secondMap;


	/**
	 * Variable representing the player's location
	 */
	private Location location;

	/**
	 * Variable representing the total number of turns played by the player
	 */
	private int numberOfTurns = 0;

	/**
	 * Variable to indicate if player just started a new game
	 */
	private boolean startGame = true;

	/**
	 * Variable to indicate if player is playing in the challenge mode
	 */
	private boolean challengeMode = false;

	/**
	 * Variable which represents the number of turns that the player chose to play in the challenge mode
	 */
	private int maxNumberOfTurns;

	/**
	 * Variable which represents the number eco pts the player is looking to get at the end of the challenge mode
	 */
	private int ecoPtsTarget;


	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}


	public void setSecondMap(GameMap newMap) {
		this.secondMap = newMap;
	}


	protected Location getLocation() {
		return location;
	}


	protected int getEcoPts(){
		return this.ecoPts;
	}


	/**
	 * This method is used to increment player's eco point
	 * @param bonus number of ecoPts that player received
	 */
	protected void incrementEcoPts(int bonus){
		if (bonus > 0) {
			this.ecoPts += bonus;
		}
	}


	@Override
	public String getGender() {
		return null;
	}

	@Override
	public boolean isDehydrated() {
		return false;
	}


	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

		// prompt user each time app is running for the first time
		if (startGame){
			int userChoice = GameMenu.showGameMenu();
			startGame = false;
			handleUserChoice(userChoice);
			firstMap = map;  // store reference to the map on which the player appears when game first starts
		}

		System.out.println("eco before update: " + ecoPts);
		location = map.locationOf(this);

		// Handle multi-turn Actions

		RainGenerator.raining = false;
		numberOfTurns ++;
		System.out.println("current number of turn: "+numberOfTurns);
		if (numberOfTurns % 10 == 0) {
			System.out.println("raining check");
			RainGenerator.raining = RainGenerator.hasRain();
			System.out.println("it is raining: " + RainGenerator.raining);
		}

		actions.add(restartGame());
		actions.add(searchForFruit());
		actions.add(useVM());
		actions.add(feedDino());
		actions.add(moveToNextMap());
		actions.add(moveToPreviousMap());
		updateEcoPts();
		checkGameStatus();

//		System.out.println(this.getInventory());
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}


	/**
	 * This method is used to update the player's eco pts in each turn.
	 * The number of eco pts is increased by how many fruits were produced by the trees in the game &
	 * number of eggs that hatched
	 */
	private void updateEcoPts(){
		int bonus = Tree.NumberOfFruitsProducedCurrentTurn + StegosaurAndPterodactylEgg.bonus;
		bonus += BrachiosaurEgg.bonus;
		bonus += AllosaurEgg.bonus;
		this.ecoPts += bonus;

		System.out.println("bonus from SE and PE: " + StegosaurAndPterodactylEgg.bonus);
		System.out.println("bonus from BE: " + BrachiosaurEgg.bonus);
		System.out.println("bonus from AE: " + AllosaurEgg.bonus);
		System.out.println("bonus from Tree: " + Tree.NumberOfFruitsProducedCurrentTurn);
		System.out.println("bonus: "+bonus);

		// reset bonus amount
		StegosaurAndPterodactylEgg.bonus = 0;
		AllosaurEgg.bonus = 0;
		BrachiosaurEgg.bonus = 0;
		Tree.NumberOfFruitsProducedCurrentTurn = 0;

		System.out.println("eco pts after update: " + ecoPts);
	}


	/**
	 * This method is used to deduct the player's eco pts by a specific amount, for example when buying items
	 * @param amount represent the amount of eco pts that will be deducted from player's eco pts
	 */
	protected void deductEcoPts(int amount){
		this.ecoPts -= amount;
	}


	/**
	 * This method is executed after user chose the game mode.
	 * If user selected the challenge mode, then user will be prompted for the number of turns and eco pts he/she plan
	 * to get in the selected number of turns
	 * @param choice the game option that the user chose
	 */
	private void handleUserChoice(int choice){
		if (choice == 2){
			this.challengeMode = true;
			String numberOfTurnsPrompt = "How many turns do you want to play?";
			String enterNumberOfTurns = "Enter number of Turns: ";
			String ecoPtsPrompt = "How many eco pts are you targeting?";
			String enterEcoPts = "Enter number of ecoPts: ";
			boolean flag = false;
			while (! flag) {
				this.maxNumberOfTurns = UserInput.getIntegerInput(numberOfTurnsPrompt, enterNumberOfTurns);
				this.ecoPtsTarget = UserInput.getIntegerInput(ecoPtsPrompt, enterEcoPts);
				if (this.maxNumberOfTurns > 0 && this.ecoPtsTarget > 0){
					flag = true;
				}
				else {
					System.out.println("ERROR: Please enter a number greater than 0");
				}
			}
		}
	}


	/**
	 * This method is used to check if player won or lost the game at the end of the challenge mode
	 */
	private void checkGameStatus(){
		if (challengeMode && numberOfTurns == maxNumberOfTurns){
			System.out.println("You played the maximum number of turns");
			System.out.println("Before the game you said you would achive: " + ecoPtsTarget + " eco pts");
			System.out.println("You managed to achive: " + ecoPts + " eco pts");

			if (ecoPts < ecoPtsTarget){
				System.out.println("You lost :(, better luck next time");
			}else {
				System.out.println("YAAAAAAAAAAAAAYYYYYYYYYYYYYYYYYYY :), YOU WON!!");
			}
			// re-run the game
			Application.main(null);
		}
	}


	// Actions methods:

	/**
	 * This method is used to check if there's a bush / tree in the same location of player.
	 * If so, then player can decide to search for a fruit on the source
	 * @return Action which will allow player to search for a fruit
	 */
	private SearchFruitAction searchForFruit(){
		if (location.getGround() instanceof Tree || location.getGround() instanceof Bush) {
			return new SearchFruitAction(this);
		}
		return null;
	}


	/**
	 * This method is used by the player to feed a dinosaur only if player is adjacent to a dinosaur
	 * @return Action which will allow player to feed the dinosaur
	 */
	private FeedDinoAction feedDino() {
		List<Exit> adjacentLocations = this.location.getExits();
		for (Exit adjacentLocation : adjacentLocations) {
			Location currentLocation = adjacentLocation.getDestination();
			if (currentLocation.getActor() instanceof DinosaurInterface) {
				return new FeedDinoAction(this, currentLocation.getActor());
			}
		}
		return null;
	}


	/**
	 *This method is used to check if player is at the very top of the first map, if so then player can move to the
	 * second map
	 * @return Action which will move player to the previous/first map
	 */
	private MoveActorAction moveToNextMap(){
		if (this.location.y() == 0){
			// make sure player can only move to second map if no other actor is at the position
			if (! secondMap.isAnActorAt(secondMap.at(location.x(), secondMap.getYRange().min()))) {
				return new MoveActorAction(secondMap.at(location.x(), secondMap.getYRange().max()), "to next map");
			}
			return null;
		}
		return null;

	}


	/**
	 * This method is used to check if player is at the very end of the second map, if so then player can return to the
	 * first map
	 * @return Action which will move player to the previous/first map
	 */
	private MoveActorAction moveToPreviousMap(){
		if (this.location.y() == secondMap.getYRange().max()){
			// make sure player can only return to previous map if no other actor is at the position
			if (! firstMap.isAnActorAt(firstMap.at(location.x(), firstMap.getYRange().min()))) {
				return new MoveActorAction(firstMap.at(location.x(), firstMap.getYRange().min()), "to previous map");
			}
		}
		return null;
	}


	/**
	 * This method gives the player to restart the game
	 * @return Action which will restart the game for the player
	 */
	private RestartAction restartGame(){
		return new RestartAction();
	}


	/**
	 * This method is used so that player can use the vending machine(VM) provided player is in same location as the VM
	 * @return Action which will allow the player to use the vending machine
	 */
	private UseVendingMachineAction useVM(){
		if (location.getGround() instanceof VendingMachine) {
				return new UseVendingMachineAction(this);
		}
		return null;
	}

}
