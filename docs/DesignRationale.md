# Design Rationale

## Allosaur
**Type:** Class

**Description:**

This class will be used to create an instance of a dinosaur of the type Allosaur.
Since, Allosaur is a carnivorous dinosaur, the class will implement the interface named Carnivore to make sure it has
all the characteristics of a carnivorous dinosaur and will extend the Actor class since, it's also an Actor in the game.
It will form part of the game.dinosaur package since this package contains everything related to dinosaurs.

**Reasoning:**

This class was created because Allosaur is also an important part of the entire system and for easier implementation, 
it should have its own class and it also follows the Single Responsibility Principle and makes the class more cohesive. 
Moreover, there can be a lot of dinosaurs of the type Allosaur. Hence, by having a class, it would be easy to just 
create an instance of the Allosaur class.


## AllosaurEgg
**Type:** Class

**Description:**

This class will be used to create an instance of an egg laid by a female dinosaur of the type Allosaur.
This class will extend from the Egg class.
This class will also implement the interface: VendingMachineItemsInterface.
It will form part of the game.items.vending package since this package contains everything related to items which need
to be bought from vending machine to appear on map.

**Reasoning:**

This class was created because female Allosaur can lay eggs and their eggs don't take the same amount of time to hatch.
Besides, player can buy Allosaur's eggs from the vending machine and the egg has a different cost than the other eggs
which belong to the other types of dinosaurs. Thus, for easier implementation, it should have its own class.
Additionally, it's better to have a class which contains the characteristics for AllosaurEgg as it adheres to the
Single Responsibility Principle makes the class more cohesive.


## Behaviour
**Type:** Abstract Class

**Description:**

This class is a factory for creating actions.
It will form part of the game.dinosaur.behaviours package since this package contains everything related to the
behaviours of the dinosaurs.

**Reasoning:**

This class was created by simply changing the Behaviour interface to become an abstract class. This was because to
reduce code duplications so that instead of writing the same method multiple times, we simply convert the interface to
an abstract class and provides the implementation for the common methods.


## Brachiosaur
**Type:** Class

**Description:**

This class will be used to create an instance of a dinosaur of the type Brachiosaur.
Since Brachiosaur is a herbivorous dinosaur, the class will implement the interface named Herbivore to make sure
it has all the characteristics of a herbivorous dinosaur and will extend the Actor class since, it's also an Actor 
in the game. It will form part of the game.dinosaur package since this package contains everything related to dinosaurs.

**Reasoning:**

This class was created because Brachiosaur is also an important part of the entire system and for easier implementation, 
it should have its own class and it also follows the Single Responsibility Principle, 
hence making the class more cohesive and system will be easier to maintain and extend. 
Moreover, there can be a lot of dinosaurs of the type Brachiosaur. Hence, by having a class, it would be easy to just 
create an instance of the Brachiosaur class.


## BrachiosaurEgg
**Type:** Class

**Description:**

This class will be used to create an instance of an egg laid by a female dinosaur of the type Brachiosaur.
This class will extend from the Egg class.
This class will also implement the interface: VendingMachineItemsInterface.
It will form part of the game.items package since this package contains everything related to items which don't need
to be bought from vending machine to appear on map.

**Reasoning:**

This class was created because female Brachiosaur can lay eggs, and their eggs don't take the same amount of time to 
hatch. Besides, player can buy Brachiosaur's eggs from the vending machine, and the egg has a different cost than 
the other eggs which belong to the other types of dinosaurs. Thus, for easier implementation, 
it should have its own class. Also, this type of eggs can be eaten by Allosaur, hence to implement that, 
it's better to have an object for this type of egg. 
Additionally, it's better to have a class which contains the characteristics for BrachiosaurEgg as it adheres to the 
Single Responsibility Principle hence making the class more cohesive and system will be easier to maintain and extend.


## BreedingBehaviour
**Type:** Class

**Description:**

This class contains the logic of how dinosaur breeds, i.e. it figures out the distance to a mate and 
perform the breeding action s well as the layEggAction when dinosaur is pregnant
This class will extend the class: Behaviour.
It will form part of the game.dinosaur.behaviours package since this package contains everything related to the
behaviours of the dinosaurs.

**Reasoning:**

This class was created because the breeding process is very large that it would be better if we had a class for itself.
Also, all dinosaurs need to have this behaviour and hence by having a class we won't need to duplicate our code.
Moreover, following the Single Responsibility Principle, it will be better to have a class only
for the breeding and in doing so, the class will be more cohesive and our codebase will be easier to 
maintain and extend later on.


## Bush
**Type:** Class

**Description:**

This class will create instances of Bush which is a source of food (it produces fruit)
It has a 10% chance to produce a ripe fruit in each turn. This class will extend from the Ground class so that it can 
be displayed on the Map.
It will form part of the game.ground package since this package contains everything related to the
objects which form parts of the ground.

**Reasoning:**

This class was created because bushes play an important role in the entire system and for easier implementation, 
it should have its own class to follow the Single Responsibility Principle hence making the class more cohesive 
and system will be easier to maintain and extend. 
Moreover, there can be a lot of bushes hence, by having a class, it would be easy to just create an instance of the Bush 
class whenever a bush grows.


## Carnivore
**Type:** Interface

**Description:**

This interface contains the main characteristics of a carnivorous dinosaur.
This interface will extend the interface named DinosaurInterface.
It will form part of the package: game.dinosaur.interfaces as this package will contain all interfaces 
related to dinosaurs.

**Reasoning:**

Although we currently only have one type of dinosaur which is a carnivore,
it’s better if we have a carnivore interface, so that if in the future we need to add a new type of dinosaur 
which is also a carnivore, it can simply implement the interface to make sure that it has all characteristics of a 
carnivorous dinosaur, meaning that our system will be easier to extend and it follows 
the Interface Segregation Principle.


## CarnivoreMealKit
**Type:** Class

**Description:**

This class will extend from the class PortableItem because the player can carry many CarnivoreMealKit objects by 
storing them in the inventory.
This class will implement the interface: VendingMachineItemsInterface.
An instance from this class can be fed to any carnivorous dinosaurs
It will form part of the game.items.vending package since this package contains everything related to items which need
to be bought from vending machine to appear on map.

**Reasoning:**
We created this class because a player can have many carnivore meal kits in his/her possession. Moreover,
the cost for a CarnivoreMealKit is different from that of the VegMealKit.


## Corpse
**Type:** Class

**Description:**

This class represent the dead body of a dinosaur.
This class extends the class PortableItem as player can pick it up.
It will form part of the game.items package since this package contains everything related to items which don't need
to be bought from vending machine to appear on map.

**Reasoning:**

This class was created because we have a lot of dinosaurs in the game and their dead bodies still need to appear on the
map, it's better if we have a class which represent the dead bodies instead of creating an attribute for it and use
a lot of if-else statements to make sure dinosaur is alive and not dead.
By having a class representing the dead bodies, it makes the game easier to extend and maintain.
Besides, we can reduce the amount of duplicate codes as all dinosaurs can die in the game hence instead of having an 
implementation for dead bodies of each specific type of dinosaurs, we can have a class to contain the logic for dead
dinosaurs.


## DinosaurInterface
**Type:** Interface

**Description:**

This interface is used to define the overarching methods for the three main dinosaurs that will be included in the game.
In essence, it will contain the methods to implement the essential behaviors for all dinosaurs.
It will form part of the package: game.dinosaur.interfaces as this package will contain all interfaces
related to dinosaurs.

**Reasoning:**

This interface needs to be created as we would need to add implementation details that will be common for all the 
dinosaurs e.g., breeding. Moreover, system will be easier to extend as it follows the Interface Segregation Principle.


## DinosaurStatus
**Type:** Enum

**Description:**

This is an Enum type which contains the common constants for all dinosaurs
It will form part of the game.dinosaur package since this package contains everything related to dinosaurs.

**Reasoning:**

This was created because the dinosaurs have a common list of constants.
By making use of Enum type, it makes code easier to code rather than using multiple attributes in each dinosaur class.


## Dirt
**Type:** Class (Modified)

**Description:**

This class represents bare dirt.
It will form part of the game.ground package since this package contains everything related to the
objects which form parts of the ground.

**Reasoning:**

The Dirt class will need some modifications as it should be able to produce a bush object on certain occasions.


## Egg
**Type:** Abstract Class

**Description:**

This class will be the parent class of the different types of eggs that we'll have in the game.
Since, Eggs can be picked up by a player, it will extend from the class PortableItem.
Additionally, it can be bought from a vending machine.
It will form part of the game.items package since this package contains everything related to items which don't need
to be bought from vending machine to appear on map.

**Reasoning:**

This class was created because we currently have 3 types of eggs(Stegosaur, Allosaur & Brachiosaur) as a result,
they share some attributes & methods in common. Hence, it's better to have a class which can be the parent for
all different types of eggs. Moreover, it can be used to reduce dependencies throughout our code. 
Besides, it's following the Dependency Inversion Principle by making sure high level modules depend on abstractions, and
hence, it will be easier to maintain or add new things in the system as if we need to do that, we just need to ensure
we're implementing everything promised by the abstract Egg class.


## FeedDinoAction
**Type:** Class

**Description:**

This class allows player to Feed a dino.
Since, this class is an Action which can be performed by the player, it will extend from the class Action.
It will form part of the game.player package since this package contains everything related to the Player.

**Reasoning:**

This class was created because it's easier to maintain the game so that if in the future we want to make some changes
in the way player feeds a dinosaur, we know which class would need change. Moreover, if there's any problem with the 
action of feeding dinosaurs, it would be easier to debug. Furthermore, this functionality was quite large that it would
be better to put it in its own class, that way it adheres to the Single Responsibility Principle and also because the 
Player can perform a lot of actions, it's better to have put each action in their respective class.
Besides, it makes the class more cohesive and in doing so, our system will be easier to maintain and extend.


## FlyBehaviour
**Type:** Class

**Description:**

This class contains the logic of how dinosaur flies (e.g. Pterodactyl)
This class will extend the class: Behaviour.
It will form part of the game.dinosaur.behaviours package since this package contains everything related to the
behaviours of the dinosaurs.

**Reasoning:**

This class was created because the logic to implement the flying behaviour is quite large that it would be better if 
we had a class for itself. Additionally, even if currently only 1 dinosaur is using it, this allows the game to add
new dinosaurs which can fly easily as the flying behaviour class is already here and we won't need to duplicate our code.
Moreover, following the Single Responsibility Principle, it will be better to have a class only
for the flying behaviour and in doing so, our codebase will be easier to maintain and extend later on and the class
will be more cohesive.


## Fruit
**Type:** Class

**Description:**

This class will create instances of fruit which is the food that the dinosaurs will feed on. 
It can be produced by both trees and bushes.
Since, an instance of Fruit can be picked up by player, it will extend from the class PortableItem.
Additionally, it can be bought from a vending machine and hence it will also implement 
the interface: VendingMachineItemsInterface.
It will form part of the game.items package since this package contains everything related to items which don't need
to be bought from vending machine to appear on map.

**Reasoning:**

This class was created because fruit is also an important part of the entire system and for easier implementation, 
it should have its own class. Moreover, there can be a lot of fruits hence, by having a class, it would be easy to just 
create an instance of the Fruit class whenever a tree and a bush produces one. Furthermore, a fruit has a chance to fall
from a tree and may even rot if not eaten or picked up by the player in 15 turns; as a result, to keep track of these 
things, it would be easier for it to have its own class and it would also follow the Single Responsibility Principle.
As a result, the class will be more cohesive and system will be easier to maintain and extend.


## GameMenu
**Type:** Class

**Description:**

This class will be used to contain everything related to the menu of the game.
This class will contain a static method which contains the logic of how player can select the game mode to play in.
It will form part of the game package.

**Reasoning:**

This class was created so because we don't want to put too much code inside the player class and the menu of the game
doesn't fit inside any of the existing class as if we do so, it won't follow the Single Responsibility Principle. 
Additionally, if in the future, we need to add new things related to the game menu e.g. select number of players, 
it would be easier to add new implementation to the GameMenu class and test the code. Furthermore, as it's in its own 
class we don't need to worry about obstructing the way other classes work.


## Herbivore
**Type:** Interface

**Description:**

This interface contains the main characteristics of a herbivorous dinosaur.
This interface will extend the interface named DinosaurInterface.
It will form part of the package: game.dinosaur.interfaces as this package will contain all interfaces
related to dinosaurs.

**Reasoning:**

Since, we currently have 2 types of Dinosaur in the game which are herbivores, they share some characteristics.
As a result, it would be better if we made all herbivorous dinosaurs implement this interface to make sure
they abide by the rules for a herbivorous dinosaur. 
Moreover, system will be easier to extend as it follows the Interface Segregation Principle.

> Note: We could not make it an abstract class because the class for the different types of dinosaurs which are also 
> herbivorous dinosaurs, are already extending the class Actor and hence can’t extend another class, 
> but can implement an interface.


## HungerBehaviour
**Type:** Class

**Description:**

This class handles the way dinosaurs try to find food based on its unique characteristic (i.e Herbivore or Carnivore)
This class will extend the class: Behaviour.
It will form part of the game.dinosaur.behaviours package since this package contains everything related to the
behaviours of the dinosaurs.

**Reasoning:**

This class was created because the logic to implement the way a dinosaur search for its food is quite large that 
it would be better if we had a class for itself. Also, all dinosaurs need to have this behaviour and hence by having
a class we won't need to duplicate our code.
Moreover, following the Single Responsibility Principle, it will be better to have a class only
for this behaviour and in doing so, our codebase will be easier to maintain and extend later on and the class will be
more cohesive.


## Lake
**Type:** Class

**Description:**

This class will create instances of Lake which is a source of food (it contains fish) & water
There's a 60% chance for a new fish to be born inside the lake in each turn. 
This class will extend from the Ground class so that it can be displayed on the Map.
It will form part of the game.ground package since this package contains everything related to the objects which form 
parts of the ground.

**Reasoning:**

This class was created because lakes play an important role in the entire system and for easier implementation,
it should have its own class. Moreover, there are a lot of lakes hence, by having a class, it would be easy to just
create an instance of the Lake class.
Additionally, it's better to have a class which contains the implementation details for a lake as it adheres to the
Single Responsibility Principle and as a result, the class will be more cohesive and system will be easier to maintain
and extend.


## LaserGun
**Type:** Class

**Description:**

This class is used to create a laser gun which the player can use as a weapon.
Since, this class is a weapon, it would extend the class WeaponItem.
It will form part of the game.items.vending package since this package contains everything related to items which need
to be bought from vending machine to appear on map.

**Reasoning:**

The laser gun must have its own class as this item can be used as a weapon in the game and it also follow the 
Single Responsibility Principle and as a result, it makes the system easier to extend i.e, we can add new features
to the LaserGun class in the future and it also makes the class more cohesive.


## LocateFoodBehaviour
**Type:** Class

**Description:**

This class contains the logic of how dinosaur search for its food.
This class will implement the interface: Behaviour.
It will form part of the game.dinosaur.behaviours package since this package contains everything related to the 
behaviours of the dinosaurs.

**Reasoning:**

This class was created because the process of how a dinosaur locate the nearest distance to travel to get to a food 
source is very large & complicated that it would be better if we had a class for itself.
Moreover, following the Single Responsibility Principle, it will be better to have a class only to handle this logic 
and in doing so, our codebase will be easier to maintain and extend later on and make the class more cohesive.


## Package: edu.monash.fit2099.utils
**Description:**

This package contains code which can be used by several classes from different packages.

**Reasoning:**

This package was created so that we can have classes which provides a functionality which is used by many 
unrelated classes. Hence, instead of writing the same functionality repeatedly, we just make use of this package to 
store these functionalities, and we can avoid code duplications.


## Package: game.dinosaur
**Description:**

This package contains everything related to the dinosaurs.

**Reasoning:**

This package was created so that we can store everything related to the dinosaurs.
This will make it easier to maintain and extend the program by having the specific components in a package


## Package: game.dinosaur.behaviours
**Description:**

This package contains all the behaviours of the dinosaurs.

**Reasoning:**

This package was created so that we can store all the behaviours of the dinosaurs in one single place.
This will make it easier to maintain and extend the program.


## package: game.dinosaur.interfaces
**Description:**

This package contains all the interfaces related to the dinosaurs.

**Reasoning:**

This package was created so that we can store all the interfaces related to the dinosaurs in one single place.
This will make it easier to maintain and extend the program.


## Package: game.ground
**Description:**

This package contains all the classes which are related to the ground.

**Reasoning:**

This package was created so that we can store all the classes related to the ground in one single place.
This will make it easier to maintain and extend the program.


## Package: game.items
**Description:**

This package contains all the classes which are an item in the game and can still appear on the map without being 
bought from the vending machine.

**Reasoning:**

This package was created so that we can store all the items in one single place.
This will make it easier to maintain and extend the program.


## package: game.player
**Description:**

This package contains everything related to the Player.

**Reasoning:**

This package was created so that we can store everything related to the Player.
This will make it easier to maintain and extend the program by having the specific components in a package


## Package: game.items.vending
**Description:**

This package contains all the classes which are an item in the game and can't appear on the map without being
bought from the vending machine.

**Reasoning:**

This package was created so that we can store all the similar items in one single place.
This will make it easier to maintain and extend the program.


## Player
**Type:** Class (Modified)

**Description:**

This class represents the player.
It will form part of the game.player package since this package contains everything related to the Player.

**Reasoning:**

The Player class will need some modifications as it should be able to make use of eco points to buy items and should 
also increment the eco points when certain events happen. 
Another example is that player should be able to pick up fruits. Also, whenever player feeds a fruit to a herbivorous
dinosaur, player's eco points will increase, and the life points of the dinosaurs will increase 
(the life points of the dinosaurs will also increase if a herbivore meal kit is fed to it).
Also, if Allosaur is fed a carnivore meal kit by the player then, its life points will increase.


## Pterodactyl
**Type:** Class

**Description:**

This class will be used to create an instance of a dinosaur of the type Pterodactyl.
Since, Pterodactyl is a carnivorous dinosaur, the class will implement the interface named Carnivore to make sure 
it has all the characteristics of a carnivorous dinosaur and will extend the Actor class since, it's also an Actor in 
the game. It will form part of the game.dinosaur package since this package contains everything related to dinosaurs.

**Reasoning:**

This class was created because Pterodactyl is also an important part of the entire system and for easier implementation,
it should have its own class and it also follows the Single Responsibility Principle. 
Hence, the class will be more cohesive and system will be easier to maintain and extend.
Moreover, there can be a lot of dinosaurs of the type Pterodactyl. Hence, by having a class, it would be easy to 
just create an instance of the Pterodactyl class.


## PterodactylEgg
**Type:** Class

**Description:**

This class will be used to create an instance of an egg laid by a female dinosaur of the type Pterodactyl.
This class will extend from the StegosaurAndPterodactylEgg class.
It will form part of the game.items package since this package contains everything related to items which don't need
to be bought from vending machine to appear on map.

**Reasoning:**

This class was created because female Pterodactyl can lay eggs and their eggs don't take the same amount of time to hatch.
Besides, player can buy Pterodactyl's eggs from the vending machine and the egg has a different cost than the other eggs
which belong to the other types of dinosaurs. Thus, for easier implementation, it should have its own class.
Additionally, it's better to have a class which contains the characteristics for PterodactylEgg as it adheres to the
Single Responsibility Principle and as a result, the class will be more cohesive and system will be easier to maintain
and extend.


## RainGenerator
**Type:** Class

**Description:**

This class will be used to generate rain in the game.
This class will contain a static method which contains the logic of how rain should be generated.
It will form part of the game package.

**Reasoning:**

This class was created so because the logic to generate rain doesn't fit inside any of the existing class as if we do so,
it won't follow the Single Responsibility Principle. Additionally, if in the future, we need to make changes in the way
rain is generated in the game, it would be easier to change and test the code because it's in its own class and won't 
need to worry about obstructing the way other classes work.


## RestartAction
**Type:** Class

**Description:**

This class allows player to restart the current game.
Since, this class is an Action which can be performed by the player, it will extend from the class Action.
It will form part of the game.player package since this package contains everything related to the Player.

**Reasoning:**

This class was created because if we added this implementation in the Player class itself, the class would become too
crowded and it would be quite difficult to debug if there was a problem with the logic of restarting the game.
Besides, it adheres to the Single Responsibility Principle and also because the Player can perform a lot of actions, 
it's better to have put each action in their respective class.


## SearchFruitAction
**Type:** Class

**Description:**

This class allows player to search fruits if player is under a tree or is near a bush.
Since, this class is an Action which can be performed by the player, it will extend from the class Action.
It will form part of the game.player package since this package contains everything related to the Player.

**Reasoning:**

This class was created because it's easier to maintain the game so that if in the future we want to make some changes
in the way player have to search for a fruit, we know which class would need change. Moreover, if there's any problem 
with the action of searching for fruits, it would be easier to debug. 
Furthermore, this functionality was quite large that it would be better to put it in its own class, 
that way it adheres to the Single Responsibility Principle and also because the Player can perform a lot of actions, 
it's better to have put each action in their respective class. Besides, it makes the class more cohesive and 
in doing so, our system will be easier to maintain and extend.


## Stegosaur
**Type:** Class (Modified)

**Description:**

This class will be used to create an instance of a dinosaur of the type Stegosaur. Since Stegosaur is a 
herbivorous dinosaur, the class will implement the interface named Herbivore to make sure it has all the 
characteristics of a herbivorous dinosaur e.g., eat fruit. This class extends the Actor class.
It will form part of the game.dinosaur package since this package contains everything related to dinosaurs.

**Reasoning:**

This class needs to be modified as we would need to change some of the implementation details. 
Some examples are: eat fruit and being fed by player


## StegosaurAndPterodactylEgg
**Type:** Abstract Class

**Description:**

This class will be the parent class of the StegosaurEgg class & PterodactylEgg class that we'll have in the game.
Since, these eggs has the same basic properties as all the other eggs in the game, it will extend the Egg class.
Additionally, it can be bought from a vending machine and hence it will implement VendingMachineItemsInterface.
It will form part of the game.items package since this package contains everything related to items which don't need
to be bought from vending machine to appear on map.

**Reasoning:**

This class was created because we currently have 2 types of eggs(Stegosaur & Pterodactyl) which are very similar to each
other in terms of characteristic. Hence, it's better to have a class which can be the parent and provide implementations
for some methods which are similar.
As a result, the child class would only override methods which are different for these 2 classes.
We created this class so that we reduce the amount of duplicate codes.


## StegosaurEgg
**Type:** Class

**Description:**

This class will be used to create an instance of an egg laid by a female dinosaur of the type Stegosaur.
This class will extend from the StegosaurAndPterodactylEgg class.
It will form part of the game.items package since this package contains everything related to items which don't need
to be bought from vending machine to appear on map.

**Reasoning:**

This class was created because female Stegosaur can lay eggs, and their eggs don't take the same amount of time to hatch.
Besides, player can buy Stegosaur's eggs from the vending machine, and the egg has a different cost than the other eggs
which belong to the other types of dinosaurs. Thus, for easier implementation, it should have its own class.
Also, this type of eggs can be eaten by Allosaur, hence to implement that, it's better to have an object for this
type of egg.
Additionally, it's better to have a class which contains the characteristics for StegosaurEgg as it adheres to the
Single Responsibility Principle and as a result, the class will be more cohesive and system will be easier to maintain
and extend.


## ThirstBehaviour
**Type:** Class

**Description:**

This class handles the way dinosaurs try to locate a water source & to then follow the water source 
and perform the drinkActions
This class will extend the class: Behaviour.
It will form part of the game.dinosaur.behaviours package since this package contains everything related to the
behaviours of the dinosaurs.

**Reasoning:**

This class was created because the logic to implement the way a dinosaur locate a water source is quite large that
it would be better if we had a class for itself. Also, all dinosaurs need to have this behaviour and hence by having
a class we won't need to duplicate our code.
Moreover, following the Single Responsibility Principle, it will be better to have a class only
for this behaviour and in doing so, the class will be more cohesive and our codebase will be easier to maintain and 
extend later on.


## Tree
**Type:** Class (Modified)

**Description:**

This class represents a tree.
It will form part of the game.ground package since this package contains everything related to the
objects which form parts of the ground.

**Reasoning:**

The Tree class will need some modifications as it should be able to produce a fruit object on certain occasions.


## UnconsciousBehaviour
**Type:** Class

**Description:**

This class contains the logic of how dinosaur behave when it's unconcious.
This class will implement the interface: Behaviour.
It will form part of the game.dinosaur.behaviours package since this package contains everything related to the
behaviours of the dinosaurs.

**Reasoning:**

This class was created because the unconcious behaviour is too large to be implemented in a method inside each dinosaur 
class. Additionally, as this process is common to all dinosaurs, we would reduce code duplication by having a class 
for itself. Moreover, following the Single Responsibility Principle, it will be better to have a class only
for the unconscious behaviour and in doing so, the class will be more cohesive and our codebase will be easier to 
maintain and extend later on.


## UserInput
**Type:** Class

**Description:**

This class will be in a package named edu.monash.fit2099.utils.
It will only contain the static methods which can be used by any classes.
It handles the user input for strings

**Reasoning:**
We created this class to reduce code duplication. One example is that we prompt the user for inputs sometimes and hence
we did the task of handling string user input in another class, so that we follow the Single Responsibility Principle 
and make the class more cohesive.


## UseVendingMachineAction
**Type:** Class

**Description:**

This class allows player to use the vending machine if player is next to it.
Since, this class is an Action which can be performed by the player, it will extend from the class Action.
It will form part of the game.player package since this package contains everything related to the Player.

**Reasoning:**

This class was created because it's easier to maintain the game so that if in the future we want to make some changes
in the interaction between player and vending machine, we know which class would need change. 
Moreover, if there's any problem with the interaction with vending machine, it would be easier to debug.
Furthermore, this functionality was quite large that it would be better to put it in its own class, that way it adheres 
to the Single Responsibility Principle and also because the Player can perform a lot of actions, it's better to have put 
each action in their respective class. Besides, it makes the class more cohesive and in doing so, our system will be 
easier to maintain and extend.


## Util
**Type:** Class

**Description:**

This class will be in a package named edu.monash.fit2099.utils.
It will only contain the static methods which can be used by any classes.

**Reasoning:**
We created this class to reduce code duplication. One example is that bush & trees have a random chance of producing
a Fruit object. Hence, we can implement a method in the Util class which generate a random number, and we simply call the
method whenever we need it.


## VegMealKit
**Type:** Class

**Description:**

This class will extend from the class PortableItem because the player can carry many VegMealKit objects by
storing them in the inventory.
This class will implement the interface: VendingMachineItemsInterface.
An instance from this class can be fed to any herbivorous dinosaurs
It will form part of the game.items.vending package since this package contains everything related to items which need
to be bought from vending machine to appear on map.

**Reasoning:**
We created this class because a player can have many veg meal kits in his/her possession. Moreover,
the cost for a VegMealKit is different from that of the CarnivoreMealKit.


## VendingMachine
**Type:** Class

**Description:**

The vending machine is a class which will extend the Ground class (need to be shown on the Map), and it will only contain the methods that a player can use
to buy items by making use of eco points.
This class will also implement the interface: VendingMachineItemsInterface.
It will form part of the game.ground package since this package contains everything related to the
objects which form parts of the ground.

**Reasoning:**

The vending machine was created because a player can use it to buy items such as fruits, eggs for the different 
dinosaurs and many more.
Moreover, it follows the Single Responsibility Principle and it also makes the class more cohesive and in doing so, our
system will be easier to maintain and extend.


## VendingMachineItemsInterface
**Type:** Interface

**Description:**

This interface will be implemented by all items that can be bought by the vending machine.
It will form part of the game.items.vending package since this package contains everything related to items which need
to be bought from vending machine to appear on map.

**Reasoning:**

This interface was created so that all items in the vending machine can be made into the same type.
Furthermore, by having all items in the vending machine implementing this interface, the system will be easier to
extend if we decide to sell a new item in the vending machine in the future as it follows the 
Interface Segregation Principle.

