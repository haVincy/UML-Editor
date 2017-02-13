package UMLeditor_mode;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import UMLeditor_shape.Line;
import UMLeditor_shape.Shape;

public class createLineMode extends Mode {
	private String lineType = null;
	private ShapeFactoryInterface factory = new ShapeFactory();
	private Point startP = null;
	private List<Shape> shapes;
	private int portIndex_1 = -1, portIndex_2 = -1;
	private Shape shape_1 = null, shape_2 = null;

	public createLineMode(String lineType) {
		this.lineType = lineType;
	}

	public void mousePressed(MouseEvent e) {
		shapes = canvas.getShapeList();
		/* find which basic object, record its reference and port number */
		startP = findConnectedObj(e.getPoint(), "first");
	}

	public void mouseDragged(MouseEvent e) {
		/* show dragged line */
		if (startP != null) {
			Line line = factory.createLine(lineType, startP, e.getPoint());
			canvas.tempLine = line;
			canvas.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		Point endP = null;
		if (startP != null) {
			/* find which basic object, record its reference and port number */
			endP = findConnectedObj(e.getPoint(), "second");

			/* if end of line inside the basic object */
			if (endP != null) {
				Line line = factory.createLine(lineType, startP, endP);
				canvas.addShape(line);

				/* add relative ports to line */
				line.setPorts(shape_1.getPort(portIndex_1), shape_2.getPort(portIndex_2));

				/* add line to relative port of two basic object */
				shape_1.getPort(portIndex_1).addLine(line);
				shape_2.getPort(portIndex_2).addLine(line);
			}
			// reset
			canvas.tempLine = null;
			canvas.repaint();
			startP = null;
		}
	}

	private Point findConnectedObj(Point p, String target) {
		for (int i = 0; i < shapes.size(); i++) {
			Shape shape = shapes.get(i);

			/* check if or not mouse pressed inside the basic object */
			int portIndex;
			String judgeInside = shape.inside(p);
			if (judgeInside != null && judgeInside != "insideLine") {
				
				
				/* if shape inside the group */
				if(judgeInside == "insideGroup"){  
					shape = shape.getSelectedShape();
					portIndex = Integer.parseInt(shape.inside(p));
				}
				else
					portIndex = Integer.parseInt(judgeInside);
			
				/* if inside the basic object, get the location of relative port */
				switch (target) {
				case "first":
					shape_1 = shape;
					portIndex_1 = portIndex;
					break;
				case "second":
					shape_2 = shape;
					portIndex_2 = portIndex;
					break;
				}
				Point portLocation = new Point();
				portLocation.setLocation(shape.getPort(portIndex).getCenterX(), shape.getPort(portIndex).getCenterY());
				return portLocation;
			}

		}
		return null;
	}
}
