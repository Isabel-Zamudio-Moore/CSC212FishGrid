package edu.smith.cs.csc212.fishgrid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;


public class FishFood extends WorldObject {
	public int x;
	public int y;
	public int r;
	public Color color= Color.pink;
	
	public FishFood(World world) {
		super(world);
		x = rand.nextInt(50);
		y = rand.nextInt(50);
		r= 15;

	}
	
	@Override
	public void draw(Graphics2D d) {
		Graphics2D g = (Graphics2D) d.create();
		g.scale(1/100, 1/100);
		Shape pebble = new Ellipse2D.Double(x,y,r,r);
		g.setColor(color);
		g.fill(pebble);
		g.draw(pebble);
		//System.out.println("This");
	
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
