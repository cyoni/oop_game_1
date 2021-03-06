package mouse;

import GIS.Map;
import GUI.Gui_algo;
import GameObjects.GameObject;
import algorithms.Point2D;

public class DragFruitOnBoard {

	private Gui_algo gui_algo;
	GameObject object_to_drag;

	public DragFruitOnBoard(Gui_algo gui_algo) {
		this.gui_algo = gui_algo;
	}

	public void drag(Point2D localCoords) {
		Point2D global_point = Map.convertPixelToglobal(localCoords);
		
		if (object_to_drag == null)
			lookForObject(global_point);
		else {
			
			object_to_drag.setLocation(global_point);
			gui_algo.repaint();
		}
	}

	private void lookForObject(Point2D globalPoint) {
		/*
		 * List<GameObject> game_objects = new ArrayList<>();
		 * 
		 * List<GameObject> allObjects = gui_algo.getGameboard().addAllObjects();
		 * game_objects.addAll(allObjects);
		 * 
		 * for (GameObject current_object : game_objects) { double distance =
		 * Line.distance(globalPoint, current_object.getLocation()); if (distance < 10)
		 * { setCurrentDraggedObject(current_object); } }
		 */
	}

	public void setCurrentDraggedObject(GameObject object) {
		this.object_to_drag = object;
	}

}
