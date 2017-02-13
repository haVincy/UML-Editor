package UMLeditor_shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;

public abstract class Line extends Shape{
	protected Port[] ports = new Port[2];
	public abstract void draw(Graphics g);
	private String selectedFlag = null;
	
	public void setPorts(Port port_1, Port port_2) {
		this.ports[0] = port_1;
		this.ports[1] = port_2;
	}
	
	public void show(Graphics g) {
		g.setColor(new Color(50, 171, 175));
		this.draw(g);
		g.setColor(Color.white);
	}
	
	public void resetLocation(){
		this.x1 = (int) ports[0].getCenterX();
		this.y1 = (int) ports[0].getCenterY();
		this.x2 = (int) ports[1].getCenterX();
		this.y2 = (int) ports[1].getCenterY();
	}
	
	public void resetStartEnd(Point p) {
		if(selectedFlag == "start"){
			this.x1 = p.x;
			this.y1 = p.y;
		}
		else if(selectedFlag == "end") {
			this.x2 = p.x;
			this.y2 = p.y;
		}
	}
	
	
	public String inside(Point p) {
		int tolerance = 5;
		if(distance(p) < tolerance) {
			double distToStart = Math.sqrt(Math.pow((p.x - x1),2) + Math.pow((p.y - y1), 2));
			double distToEnd = Math.sqrt(Math.pow((p.x - x2),2) + Math.pow((p.y - y2), 2));
			if(distToStart < distToEnd) {
				selectedFlag = "start";
			}
			else{
				selectedFlag = "end";
			}
			return "insideLine";
		}
		else
			return null;
	}
	
	public void resetPort(Port port, Line line) {
		port.addLine(line);
		if(selectedFlag == "start"){
			this.ports[0].removeLine(line);
			this.ports[0] = port;
		}
		else if(selectedFlag == "end"){
			this.ports[1].removeLine(line);
			this.ports[1] = port;
		}
		
	}
	
	private double distance(Point p) {
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		return line.ptLineDist(p.getX(), p.getY());
	}
}
 