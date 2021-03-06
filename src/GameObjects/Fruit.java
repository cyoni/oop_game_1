package GameObjects;


import GIS.Map;
import GUI.Gui_dialog;
import algorithms.Point2D;
import game.GameBoard;

/**
 *  This class represents a fruit.
 * @author Yoni
 *
 */
public class Fruit extends GameObject {

	public static String picture = "fruit.png";
	
	private double weight;
	
		
	public Fruit(int id, Point2D location, double weight) {
		super(id, location);
		setWeight(weight);
		setObjectSize(30);
	}

	
	public String toString() {
		Point2D global_location = getLocation();
		return "F,"	+ getId() + 
				","	+ global_location.y() + 
				"," + global_location.x() +
				"," + getWeight() + 
				"," ;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public static String getTag() {
		return "fruit";
	}



}
