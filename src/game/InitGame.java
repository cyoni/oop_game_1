package game;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import GUI.Gui_algo;
import GUI.Gui_dialog;
import GameObjects.Fruit;
import GameObjects.Ghost;
import GameObjects.MoveableObject;
import GameObjects.Pacman;
import GameObjects.Player;
import Threads_Game.Eat_Thread;
import Threads_Game.ManageGhostThread;
import Threads_Game.ManagePacmanThread;
import Threads_Game.MovementThread;
import GameObjects.Game_object;
import algorithms.DFS;
import algorithms.Graph;
import algorithms.Line;
import algorithms.Node;
import algorithms.Point2D;
import algorithms.Prim;
import algorithms.node_data;

public class InitGame{


	private Gui_algo gui_algo;
	private InitializeGameGraph init_gameboard;
	
	public InitGame(Gui_algo gui_algo) {
		this.gui_algo = gui_algo;
		this.init_gameboard = new InitializeGameGraph(gui_algo.getGameboard());
	}
	
	public void startGame() {
		if (gui_algo.getGameboard().isRunning() == false) {
			
			DropingItemsOnScreen thread_drop = new DropingItemsOnScreen();		
			if (gui_algo.getGameboard().getFruits().size() == 0) {
				return;
			} else if (gui_algo.getGameboard().getPlayer() == null) {
				DropingItemsOnScreen.global_dropping_player = true;
				thread_drop.startThreadDroppingItems();
				return;
			} else if (gui_algo.getGameboard().getGraph().nodeSize() == 0) {
				buildGraphGame();
			}
			
			System.out.println("\nGAME STARTED");
			gui_algo.getGameboard().startGame();;
			
			Eat_Thread eat_thread = new Eat_Thread(gui_algo.getGameboard()); // the thread that displaying the fruits on the screen
			eat_thread.start();
			
			startMenualVersion();
		}
	}
	
	private void startMenualVersion() {
		MovementThread movementThread = new MovementThread(gui_algo.getGameboard(), gui_algo.getGameboard().getPlayer());
		movementThread.start();
		initializeAndStartPacmansThreads();
		initializeAndStartGhosts();
	}

	private void initializeAndStartPacmansThreads() {
		gui_algo.getGameboard().getPacmanThreads().clear();
		gui_algo.getGameboard().initializeAndStartPacmansThreads();
	}
	
	private void initializeAndStartGhosts() {
		gui_algo.getGameboard().getGhostsThreads().clear();
		gui_algo.getGameboard().initializeAndStartGhosts();
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
					gameboard.addFruit(new Fruit(Game_object.GLOBAL_ID++, new Point2D(lon, lat), velocity_or_weight));}
				//else if (type.equals("G"))
				//	gameboard.addGhost(new Ghost(id, new Point2D(lat, lon), Double.parseDouble(data[5])));
				else if (type.equals("P")) {
					double eatingRadius = Double.parseDouble(data[5]);
					gameboard.addPacman(new Pacman(Game_object.GLOBAL_ID++, new Point2D(lon, lat), velocity_or_weight, eatingRadius));
				} else if (type.equals("M")) {
					double eatingRadius = Double.parseDouble(data[5]);
					gameboard.setPlayer(new Player(Game_object.GLOBAL_ID++, new Point2D(lon, lat), velocity_or_weight, eatingRadius));
				}
			}

			gui_algo.setGameBoard(gameboard);
		}
	}
	
	public void initDroppingObjects() {
    	gui_algo.getGameboard().flushIfNeeded();
    	DropingItemsOnScreen dropping = new DropingItemsOnScreen();
    	dropping.selectToDropAll();
    	dropping.startThreadDroppingItems();		
	}

	public void buildGraphGame() {
		init_gameboard.buildGraphGame();
	}
	
}