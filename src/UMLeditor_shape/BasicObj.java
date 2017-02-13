package UMLeditor_shape;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public abstract class BasicObj extends Shape{
	private int offset = 5;
	protected int width, height; 
	protected String objectName = "Object Name";
	protected Font font = new Font(Font.DIALOG, Font.BOLD, 14);
	protected Port[] ports = new Port[4];

	public abstract void draw(Graphics g);
	
	public void show(Graphics g) {
		for(int i = 0; i < ports.length; i++) {
			g.fillRect(ports[i].x, ports[i].y, ports[i].width, ports[i].height);
		}
	}
	
	public String inside(Point p) {
		Point center = new Point();
		center.x = (x1 + x2) / 2;
		center.y = (y1 + y2) / 2;
		Point[] points = { new Point(x1, y1), new Point(x2, y1), new Point(x2, y2), new Point(x1, y2) };
		
		for (int i = 0; i < points.length; i++) {
			Polygon t = new Polygon();
			// (0,1,center) (1,2,center) (2,3,center) (3,0,center)
			int secondIndex = ((i + 1) % 4);
			t.addPoint(points[i].x, points[i].y);
			t.addPoint(points[secondIndex].x, points[secondIndex].y);
			t.addPoint(center.x, center.y);

			if (t.contains(p)) {
				return Integer.toString(i);
			}
		}
		return null;
	}
	
	public Port getPort(int portIndex) {
		return ports[portIndex];
	}
	
	public void resetLocation(int moveX, int moveY) {
		int x1 = this.x1 + moveX;
		int y1 = this.y1 + moveY;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + width;
		this.y2 = y1 + height;
		int[] xpoint = {(x1+x2)/2, x2 + offset, (x1+x2)/2, x1 - offset};
		int[] ypoint = {y1 - offset, (y1+y2)/2, y2+offset, (y1+y2)/2};
		
		for(int i = 0; i < ports.length; i++) {
			ports[i].setPort(xpoint[i], ypoint[i], offset);
			ports[i].resetLines();
		}
	}
	
	public void changeName(String name){
		this.objectName = name;
	}
	
	protected void createPorts() {
		int[] xpoint = {(x1+x2)/2, x2 + offset, (x1+x2)/2, x1 - offset};
		int[] ypoint = {y1 - offset, (y1+y2)/2, y2+offset, (y1+y2)/2};

		for(int i = 0; i < ports.length; i++) {
			Port port = new Port();
			port.setPort(xpoint[i], ypoint[i], offset);
			ports[i] = port;
		}
	}
}
