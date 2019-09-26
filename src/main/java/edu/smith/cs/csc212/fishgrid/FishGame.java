package edu.smith.cs.csc212.fishgrid;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class manages our model of gameplay: missing and found fish, etc.
 * @author jfoley
 *
 */
public class FishGame {
	/**
	 * This is the world in which the fish are missing. (It's mostly a List!).
	 */
	World world;
	/**
	 * The player (a Fish.COLORS[0]-colored fish) goes seeking their friends.
	 */
	Fish player;
	/**
	 * The home location.
	 */
	FishHome home;
	/**
	 * These are the missing fish!
	 */
	List<Fish> missing;
	
	/**
	 * These are fish we've found!
	 */
	List<Fish> found;
	
	/**
	 * Number of steps!
	 */
	int stepsTaken;
	
	/**
	 * Score!
	 */
	int score;
	
	public static final int Rockconstant=5;
	
	/**
	 * Create a FishGame of a particular size.
	 * @param w how wide is the grid?
	 * @param h how tall is the grid?
	 */
	public FishGame(int w, int h) {
		world = new World(w, h);
		
		missing = new ArrayList<Fish>();
		found = new ArrayList<Fish>();
		
		// Add a home!
		home = world.insertFishHome();
		
		//  TODO Create note: Generate some more rocks!
		for (int i=0; i<2; i++) {
			 world.insertRockRandomly();
		}
		
		// (lab) Make 5 into a constant, so it's easier to find & change.
		for (int i=0; i<Rockconstant; i++) {
			world.insertRockRandomly();
		}
		
		// (lab) Make the snail!
		Snail Al= new Snail(world);
			world.insertSnailRandomly();

		// TODO falling rock
		FallingRocks FRs= new FallingRocks(world);
		FRs.step();

		// Make the player out of the 0th fish color.
		player = new Fish(0, world);
		// Start the player at "home".
		player.setPosition(home.getX(), home.getY());
		player.markAsPlayer();
		world.register(player);
		
		// Generate fish of all the colors but the first into the "missing" List.
		for (int ft = 1; ft < Fish.COLORS.length; ft++) {
			Fish friend = world.insertFishRandomly(ft);
			friend.IsScared(friend);
			missing.add(friend);
			if (friend.getColor()==Color.blue){
				
			}
		}
	}
	
	
	/**
	 * How we tell if the game is over: if missingFishLeft() == 0.
	 * @return the size of the missing list.
	 */
	public int missingFishLeft() {
		return missing.size();
	}
	
	/**
	 * This method is how the Main app tells whether we're done.
	 * @return true if the player has won (or maybe lost?).
	 */
	public boolean gameOver() {
		// (FishGrid) We want to bring the fish home before we win!
	if ((player.getX()==home.getX()) && 
		(player.getY()==home.getY() )){		
		return missing.isEmpty();
		
	}	else {
			return false;
		}
	}

	/**
	 * Update positions of everything (the user has just pressed a button).
	 */
	public void step() {
		// Keep track of how long the game has run.
		this.stepsTaken += 1;
				
		// These are all the objects in the world in the same cell as the player.
		List<WorldObject> overlap = this.player.findSameCell();
		// The player is there, too, let's skip them.
		overlap.remove(this.player);
		// If we find a fish, remove it from missing.
		for (WorldObject wo : overlap) {
			// It is missing if it's in our missing list.
			if (missing.contains(wo)) {
				// Remove this fish from the missing list.
				missing.remove(wo); 
				
				// Remove from world.
				// add to found instead! (So we see objectsFollow work!)
				found.add((Fish) wo);
				
				// Increase score when you find a fish!
				// if fish is part of special group then add 70 points
				if ((((Fish) wo).getColor() ==  Color.gray) |
				(((Fish) wo).getColor() == Color.darkGray)  |
				(((Fish) wo).getColor() == Color.black) |
				(((Fish) wo).getColor() == Color.white))		
				{
					score += 70;
					// if fish is green add 12 points		
				} else if (	(((Fish) wo).getColor() == Color.green) ) {
					score += 12;
					// if fish is yellow add 14 points
				} else if (((Fish) wo).getColor() == Color.yellow) {
					score += 14;
					// if fish is orange add 16 points
				} else if (((Fish) wo).getColor() == Color.orange) {
					score += 16;
					// if fish is cyan add 18 points
				} else if (((Fish) wo).getColor() == Color.cyan) {
					score += 18;
				} else {
					score += 20;
				}
			}
		}
		
		// Make sure missing fish *do* something.
		wanderMissingFish();
		// When fish get added to "found" they will follow the player around.
		World.objectsFollow(player, found);
		// Step any world-objects that run themselves.
		world.stepAll();
	}
	
	/**
	 * Call moveRandomly() on all of the missing fish to make them seem alive.
	 */
	private void wanderMissingFish() {
		Random rand = ThreadLocalRandom.current();
		for (Fish lost : missing) {
			// Scared Fish will move 80% of time
			if (lost.IsScared(lost)==true) {
			// 30% of the time, lost fish move randomly.
				if (rand.nextDouble() < 0.8) {
					//else it should not remove randomly
					// What goes here?
					lost.moveRandomly();}
				// Normal Fish will move 30% of time
			} else {
			if (rand.nextDouble()< 0.3) {
				lost.moveRandomly();
			}
				
				
			}	
		
			}
	
	}

	/**
	 * This gets a click on the grid. We want it to destroy rocks that ruin the game.
	 * @param x - the x-tile.
	 * @param y - the y-tile.
	 */
	public void click(int x, int y) {
		// TODO(FishGrid) use this print to debug your World.canSwim changes!
		System.out.println("Clicked on: "+x+","+y+ " world.canSwim(player,...)="+world.canSwim(player, x, y));
		List<WorldObject> atPoint = world.find(x, y);
		// (FishGrid) allow the user to click and remove rocks.
		//Suggestions we can say if an instance is the same the atPoint index
		//then remove rock
		for (WorldObject n: atPoint) {
			if (n instanceof Rock) {
				n.remove();
				
				}
			
			
		}
		

	}
	
}
