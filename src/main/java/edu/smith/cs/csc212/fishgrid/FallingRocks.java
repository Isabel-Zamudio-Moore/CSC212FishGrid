// citation guidance from TAs 
// & https://moodle.smith.edu/pluginfile.php/735964/mod_resource/content/0/05_Abstraction_Inheritance.pdf
package edu.smith.cs.csc212.fishgrid;

/**
 * Create class for falling rocks
 */

public class FallingRocks extends Rock {
	public FallingRocks(World world) {
		super(world);				  
		this.world= world;			
	}
	
	@Override
	public void step(){
		//move the falling rock down
		moveDown();
	}

}
