package UMLeditor_mode;

import java.awt.event.MouseEvent;
import UMLeditor_shape.BasicObj;

public class createObjMode extends Mode{
	private String objType = null;
	private ShapeFactoryInterface factory = new ShapeFactory();
	public createObjMode(String objType) {
		this.objType = objType;
	}
	public void mousePressed(MouseEvent e) {
		BasicObj basicObj = factory.createObj(objType, e.getPoint());
		canvas.addShape(basicObj);
		canvas.repaint();
	}

}
