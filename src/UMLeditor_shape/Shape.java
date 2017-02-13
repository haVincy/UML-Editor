package UMLeditor_shape;

import java.awt.Graphics;
import java.awt.Point;

public abstract class Shape {
	protected int x1, y1, x2, y2;
	public boolean group_selected = false;
	
	public abstract void draw(Graphics g);
	
	public int getX1(){
		return x1;
	}
	public int getY1(){
		return y1;
	}
	public int getX2(){
		return x2;
	}
	public int getY2(){
		return y2;
	}
	
	public void resetLocation(){}   // for Line 
	public void resetLocation(int moveX, int moveY){}  // for Basic object and Group
	
	public void changeName(String name){}
	public void show(Graphics g){}
	public String inside(Point p){
		return null;
	}
	// Basic object
	public Port getPort(int portIndex){
		return null;
	}

	
	// Group
	public void resetSelectedShape() {}
	public Shape getSelectedShape() {
		return null;
	}
	
}
