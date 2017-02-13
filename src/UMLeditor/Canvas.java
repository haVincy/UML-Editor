package UMLeditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.JPanel;

import UMLeditor_mode.Mode;
import UMLeditor_shape.Group;
import UMLeditor_shape.Shape;

@SuppressWarnings("serial")
public class Canvas extends JPanel {
	private static Canvas instance = null; // for singleton
	
	private EventListener listener = null;
	protected Mode currentMode = null;

	private List<Shape> shapes = new ArrayList<Shape>();

	public Shape tempLine = null;
	public Rectangle SelectedArea = new Rectangle();
	public Shape selectedObj = null;

	/* Singleton design pattern */
	private Canvas() {
		// Exists only to defeat instantiation.
	}

	public static Canvas getInstance() {
		if (instance == null) {
			instance = new Canvas();
		}
		return instance;
	}

	public void setCurrentMode() {
		removeMouseListener((MouseListener) listener);
		removeMouseMotionListener((MouseMotionListener) listener);
		listener = currentMode;
		addMouseListener((MouseListener) listener);
		addMouseMotionListener((MouseMotionListener) listener);
	}
	
	public void reset() {
		if(selectedObj != null){
			selectedObj.resetSelectedShape();   // for selected shape inside the group
			selectedObj = null;
		}
		SelectedArea.setBounds(0, 0, 0, 0);
	}
	
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	public List<Shape> getShapeList() {
		return this.shapes;
	}

	public void GroupShape() {
		Group group = new Group();
		for (int i = 0; i < shapes.size(); i++) {
			Shape shape = shapes.get(i);
			if (shape.group_selected) {
				group.addShapes(shape);
				shapes.remove(i);
				i--;
			}
		}
		group.setBounds();
		shapes.add(group);
	}
	public void removeGroup() {
		Group group = (Group) selectedObj;
		List<Shape> groupShapes = group.getShapes();
		for(int i = 0; i < groupShapes.size(); i++){
			Shape shape = groupShapes.get(i);
			shapes.add(shape);
		}
		shapes.remove(selectedObj);
	}

	public void changeObjName(String name) {
		if(selectedObj != null){
			selectedObj.changeName(name);
			repaint();
		}
	}

	private boolean checkSelectedArea(Shape shape) {
		Point upperleft = new Point(shape.getX1(), shape.getY1());
		Point lowerright = new Point(shape.getX2(), shape.getY2());
		/* show ports of selected objects */
		if (SelectedArea.contains(upperleft) && SelectedArea.contains(lowerright)) {
			return true;
		}
		return false;
	}

	public void paint(Graphics g) {
		/* set canvas area */
		Dimension dim = getSize();
		g.setColor(new Color(35, 37, 37));
		g.fillRect(0, 0, dim.width, dim.height);
		/* set painting color */
		g.setColor(Color.white);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		
		/* paint all shape objects */
		for (int i = shapes.size() - 1; i >= 0; i--) {
			Shape shape = shapes.get(i);
			shape.draw(g);
			shape.group_selected = false;
			/* check group select */
			if (!SelectedArea.isEmpty() && checkSelectedArea(shape)) {
				shape.show(g);
				shape.group_selected = true;
			}
			
		}

		/* paint dragged line */
		if (tempLine != null) {
			tempLine.draw(g);
		}
		/* show ports when object is selected */
		if (this.selectedObj != null) {
			selectedObj.show(g);
		}
		/* paint area of group selection */
		if (!SelectedArea.isEmpty()) {
			int alpha = 85; // 33% transparent
			g.setColor(new Color(37, 148, 216, alpha));
			g.fillRect(SelectedArea.x, SelectedArea.y, SelectedArea.width, SelectedArea.height);
			g.setColor(new Color(37, 148, 216));
			g.drawRect(SelectedArea.x, SelectedArea.y, SelectedArea.width, SelectedArea.height);

		}
	}
}
