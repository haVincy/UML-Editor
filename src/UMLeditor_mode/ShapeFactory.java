package UMLeditor_mode;

import java.awt.Point;

import UMLeditor_shape.BasicObj;
import UMLeditor_shape.Line;
import UMLeditor_shape.associationLine;
import UMLeditor_shape.classObj;
import UMLeditor_shape.compositionLine;
import UMLeditor_shape.generalizationLine;
import UMLeditor_shape.usecaseObj;

public class ShapeFactory implements ShapeFactoryInterface{
	public BasicObj createObj(String objType, Point p) {
		if(objType.equals("class")){
			return new classObj(p.x, p.y);
		}
		else if(objType.equals("usecase")){
			return new usecaseObj(p.x, p.y);
		}
		return null;
	}

	public Line createLine(String lineType, Point startP, Point endP) {
		if(lineType.equals("associate")){
			return new associationLine(startP.x, startP.y, endP.x, endP.y);
		}
		else if(lineType.equals("general")){
			return new generalizationLine(startP.x, startP.y, endP.x, endP.y);
		}
		else if(lineType.equals("composite")) {
			return new compositionLine(startP.x, startP.y, endP.x, endP.y);
		}
		return null;
	}
}
