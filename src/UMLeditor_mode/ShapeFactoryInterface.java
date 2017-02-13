package UMLeditor_mode;

import java.awt.Point;

import UMLeditor_shape.BasicObj;
import UMLeditor_shape.Line;

public interface ShapeFactoryInterface {
	public BasicObj createObj(String objType, Point p);
	public Line createLine(String lineType, Point startP, Point endP);
}
