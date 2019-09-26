// citation guidance from TAs 
// & https://moodle.smith.edu/pluginfile.php/735964/mod_resource/content/0/05_Abstraction_Inheritance.pdf
package edu.smith.cs.csc212.fishgrid;

/**
 * Create class for falling rocks
 */

public class FallingRocks extends Rock {

	public final int NumFr= 3;
	
	public FallingRocks(World world) {
		super(world);

		this.world= world;
		// Create a for loop that creates multiple Rocks
		for (int i=0; i<NumFr; i++) {
			step();	
			world.insertRockRandomly();
			}
		//Move the falling rocks down
		//step();
	}
	
	@Override
	public void step(){
		moveDown();
	}

}
