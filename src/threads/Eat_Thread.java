package threads;



import java.util.ArrayList;
import java.util.List;
import GameObjects.*;
import algorithms.Line;
import game.GameBoard;
import GUI.Gui_algo;

public class Eat_Thread extends Thread {
	
	private GameBoard gameboard;
	private List<GameObject> fruits;
	private List<MoveableObject> moveable_objects = new ArrayList<>();;
	
	public Eat_Thread(GameBoard gameboard) {
		this.gameboard = gameboard;
		moveable_objects = gameboard.getMoveableObjects();
		fruits = gameboard.getFruits();
	}
	
	public synchronized void run() {
		System.out.println("Thread " + getId() + " joined.");
		while (gameboard.isRunning()) {
			try {sleep(200);} catch (InterruptedException e) {}
			
			for (MoveableObject currentMoveable_object : moveable_objects) {
				if (currentMoveable_object instanceof Player) {
					for (int i=0; i<gameboard.getPacmans().size(); i++) {
						Pacman current_pacman = (Pacman) gameboard.getPacmans().get(i);
						checkIfThisObjectIsCloseEnoughToPacman(currentMoveable_object, current_pacman);
					}
				}
				
				checkIfAMoveableObjectIsCloseToFruit_And_RemoveIt(currentMoveable_object);
				if (fruits.size() == 0) gameboard.stopGame();
			}			
		}
		System.out.println("Thread " + getId() + " (eat_thread) terminated.");
	}


	private boolean checkIfThisObjectIsCloseEnoughToPacman(MoveableObject currentMoveable_object, Pacman current_pacman) {
		double distance = Line.distance(currentMoveable_object.getLocation(), current_pacman.getLocation());
		if (distance <= (currentMoveable_object.getEatingRadius())) {
			currentMoveable_object.increaseEatenFruits();
			currentMoveable_object.addToScore(15);
			currentMoveable_object.increaseEatenObject(current_pacman);
			((Player)currentMoveable_object).eatPacman(current_pacman);
			System.out.println("Pacman " + current_pacman.getId() + " was eaten.");
			gameboard.getPacmans().remove(current_pacman);
			return true;
		}
		return false;
	}

	private void checkIfAMoveableObjectIsCloseToFruit_And_RemoveIt(MoveableObject currentMoveable_object) {
		for (int i=0; i<fruits.size(); i++) {
			Fruit current_fruit = (Fruit) fruits.get(i);
			double distance = Line.distance(currentMoveable_object.getLocation(), current_fruit.getLocation());

			if (distance <= (currentMoveable_object.getEatingRadius())) {
				currentMoveable_object.increaseEatenObject(current_fruit);
				System.out.println("Fruit " + current_fruit.getId() + " was eaten.");
				fruits.remove(current_fruit);
				currentMoveable_object.addToScore(5);				
				notifyOtherThreadsThatYouAte(current_fruit);
				i--;
			}
		}		
	}

	private void notifyOtherThreadsThatYouAte(Fruit current_fruit) {
		for (int i=0; i<gameboard.getPacmanThreads().size(); i++) {
			gameboard.getPacmanThreads().get(i).getNotifiedOfDeadFruits(current_fruit);
		}
	}
}
