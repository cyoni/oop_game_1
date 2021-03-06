package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import GIS.Map;
import GUI.Gui_algo;
import GUI.Gui_dialog;
import GUI.Screen;
import GameObjects.Fruit;
import GameObjects.Ghost;
import GameObjects.MoveableObject;
import GameObjects.Pacman;
import GameObjects.Player;
import GameObjects.Rectangle;
import GameObjects.GameObject;
import algorithms.Graph;
import algorithms.Line;
import algorithms.Node;
import algorithms.Point2D;
import algorithms.node_data;
import threads.Eat_Thread;
import threads.ManageGhostThread;
import threads.ManagePacmanThread;
import threads.MovementThread;

public class InitGame{

	private Gui_algo gui_algo;
	
	public InitGame(Gui_algo gui_algo) {
		this.gui_algo = gui_algo;
	}
	
	public void startGame() {
		if (gui_algo.getGameboard().isRunning() == false) {
			
			DropingItemsOnScreen thread_drop = new DropingItemsOnScreen();		
			if (gui_algo.getGameboard().getFruits().size() == 0) {
				return;
			} else if (gui_algo.getGameboard().getPlayer() == null) {
				dropPlayerInCenter();
			} 
			
			addMoveableItems();
			
			System.out.println("\nGAME STARTED");
			gui_algo.getGameboard().startGame();
			
			Eat_Thread eat_thread = new Eat_Thread(gui_algo.getGameboard()); // the thread that displaying the fruits on the screen
			eat_thread.start();
			
			startMenualVersion();
		}
	}
	
	private void dropPlayerInCenter() {
		Point2D location = new Point2D(Screen.WIDTH/2, Screen.HEIGHT/2);
		location = Map.convertPixelToglobal(location);
		gui_algo.gameboard.setPlayer(new Player(GameObject.GLOBAL_ID++, location, 1, 2));
	}

	private void addMoveableItems() {
		ArrayList<MoveableObject> moveable = new ArrayList<>();

		for (int i=0; i<gui_algo.getGameboard().getPacmans().size(); i++) {
			moveable.add((MoveableObject) gui_algo.getGameboard().getPacmans().get(i));
		}
		
		moveable.add(gui_algo.getGameboard().getPlayer());
		
		gui_algo.getGameboard().setMovableObjects(moveable);		
	}

	private void startMenualVersion() {
				
		for (int i=0; i<gui_algo.getGameboard().getPacmans().size(); i++) {
			MovementThread movementThread_ = new MovementThread(gui_algo.getGameboard(), (MoveableObject) gui_algo.getGameboard().getPacmans().get(i));
			movementThread_.start();
		}
		
		MovementThread movementThread = new MovementThread(gui_algo.getGameboard(), gui_algo.getGameboard().getPlayer());
		movementThread.start();
		
		initializeAndStartPacmansThreads();
		initializeAndStartGhosts();
	}

	private void initializeAndStartGhosts() {
		gui_algo.getGameboard().initializeAndStartGhostThread();		
	}

	private void initializeAndStartPacmansThreads() {
		gui_algo.getGameboard().getPacmanThreads().clear();
		gui_algo.getGameboard().initializeAndStartPacmansThreads();
	}
	

	public void initGameboard(List<String> elements) {
		if (!elements.isEmpty()) {
	        gui_algo.getGameboard().cleanBoard();
			GameBoard gameboard = new GameBoard(gui_algo);
			for (int i=0; i< elements.size(); i++) {
				String current_element = elements.get(i);
				String data[] = current_element.split(",");
				String type = data[0];
				double lat = Double.parseDouble(data[2]);
				double lon =  Double.parseDouble(data[3]);
				double velocity_or_weight = Double.parseDouble(data[4]);
				System.out.println(elements.get(i));
				
				if (type.equals("F")) {
					gameboard.addFruit(new Fruit(GameObject.GLOBAL_ID++, new Point2D(lon, lat), velocity_or_weight));}
				else if (type.equals("G")) {
					double eatingRadius = Double.parseDouble(data[5]);
					gameboard.addGhost(new Ghost(GameObject.GLOBAL_ID++, new Point2D(lon, lat), Double.parseDouble(data[5]), eatingRadius));}
				else if (type.equals("P")) {
					double eatingRadius = Double.parseDouble(data[5]);
					gameboard.addPacman(new Pacman(GameObject.GLOBAL_ID++, new Point2D(lon, lat), velocity_or_weight, eatingRadius));
				} else if (type.equals("M")) {
					double eatingRadius = Double.parseDouble(data[5]);
					gameboard.setPlayer(new Player(GameObject.GLOBAL_ID++, new Point2D(lon, lat), velocity_or_weight, eatingRadius));
				} else if (type.equals("B")) {
					gameboard.addBlock(new Rectangle(new Point2D(Double.parseDouble(data[6]),Double.parseDouble(data[5])) , new Point2D(lon, Double.parseDouble(data[2]))));
				}
			}
//new Point2D(lon, Double.parseDouble(data[5])), new Point2D(Double.parseDouble(data[6]), lat)
			gui_algo.setGameBoard(gameboard);
		}
	}
	
	public void initDroppingObjects() {
    	gui_algo.getGameboard().flushIfNeeded();
    	DropingItemsOnScreen dropping = new DropingItemsOnScreen();
    	dropping.selectToDropAll();
    	dropping.startThreadDroppingItems();		
	}

	
}
