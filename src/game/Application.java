package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;
import game.player.Player;
import game.dinosaur.Allosaur;
import game.dinosaur.Brachiosaur;
import game.dinosaur.Pterodactyl;
import game.dinosaur.Stegosaur;
import game.ground.*;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(),
				new VendingMachine(), new Lake());
		
		List<String> map = Arrays.asList(
		"V...............................................................................",
		"................................................................................",
		".....#######........................~...........................................",
		".....#_____#....................................................................",
		".....#_____#....................................................................",
		".....###.###....................................................................",
		"................................................................................",
		"......................................+++..............~........................",
		".......................................++++...........................~.........",
		".........~.........................+++++........................................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		"........................~............+++........................................",
		"................................................................................",
		"............+++.....................................~...........................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++..............~........",
		"....~...........................................................................",
		".........................................................................++.....",
		"...........................~............................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);

		List<String> secondMap = Arrays.asList(
		"V...............................................................................",
		"................................................................................",
		".....#######........................~...........................................",
		".....#_____#....................................................................",
		".....#_____#....................................................................",
		".....###.###....................................................................",
		"................................................................................",
		"......................................+++..............~........................",
		".......................................++++...........................~.........",
		".........~.........................+++++........................................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		"........................~............+++........................................",
		"................................................................................",
		"............+++.....................................~...........................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++..............~........",
		"....~...........................................................................",
		".........................................................................++.....",
		"...........................~............................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap secondGameMap = new GameMap(groundFactory, secondMap);
		world.addGameMap(secondGameMap);
		
		Player player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(9, 4));
//		world.addPlayer(player, gameMap.at(1, 0));
		player.setSecondMap(secondGameMap);
		
		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur("Stegosaur","Male",false));
		gameMap.at(32, 12).addActor(new Stegosaur("Stegosaur","Female",false));
		//Place a heard of Pterodactyls
		gameMap.at(39, 10).addActor(new Pterodactyl("Pterodactyl","Female",false));
		gameMap.at(40, 10).addActor(new Pterodactyl("Pterodactyl","Male",false));
		//Place a heard of brachiosaurs
		gameMap.at(32, 4).addActor(new Brachiosaur("Brachiosaur","Male",false));
		gameMap.at(30, 5).addActor(new Brachiosaur("Brachiosaur","Male",false));
		gameMap.at(28, 6).addActor(new Brachiosaur("Brachiosaur","Female",false));
		gameMap.at(26, 7).addActor(new Brachiosaur("Brachiosaur","Female",false));
			
		world.run();
	}
}