package GUI;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import FileFormat.CSVReaderAndWriter;
import game.DropingItemsOnScreen;
import game.InitGame;

public class Menu_Gui {
	
    private JMenuItem loadFile, exportToCSV, exit, dropItems, startGame, cleanBoard;
    private JMenuBar menubar; // Window menu bar
	private Gui_algo gui_algo;
	
	
	public Menu_Gui(Gui_algo gui_algo) {
		this.gui_algo = gui_algo;
		setMenu();
		startMouseListener();
	}

	protected void setMenu() {
		menubar = new JMenuBar(); 
		JMenu menu_File = new JMenu("File"); 
	    JMenu menu_Game = new JMenu("Game"); 
        
	    loadFile = new JMenuItem("Load a file...");
	    exportToCSV = new JMenuItem("Export to CSV..."); 
	    exit = new JMenuItem("Exit");
	        
	    dropItems = new JMenuItem("Drop items...");
	    startGame = new JMenuItem("Start Game");
	    cleanBoard = new JMenuItem("Clean Board");
	        
	    // add menu items to menu 
	    menu_File.add(loadFile); 
	    menu_File.add(exportToCSV);
	    menu_File.addSeparator();
	    menu_File.add(exit); //exit
	        
	    menu_Game.add(dropItems);
	    menu_Game.addSeparator();
	    menu_Game.add(startGame);
	    menu_Game.add(cleanBoard);

	    // add menu to menu bar 
	    menubar.add(menu_File); 
	    menubar.add(menu_Game);
	        
	}

    private void startMouseListener() {
    	
        cleanBoard.addActionListener((ActionEvent e) -> { 
        	gui_algo.gameboard.cleanBoard();
        });
        
        startGame.addActionListener((ActionEvent e) -> { 
        	gui_algo.gameboard.startGame();
        });
    	
        dropItems.addActionListener((ActionEvent e) -> { 
        	DropingItemsOnScreen dropping = new DropingItemsOnScreen();
        	dropping.selectToDropAll();
        	dropping.startDroppingItems();
        });
    	
        loadFile.addActionListener((ActionEvent e) -> { 
        	gui_algo.getGameboard().cleanBoard();
        	loadFileAndRead();
        });
    	
        exportToCSV.addActionListener((ActionEvent e) -> { 
        	try {
				gui_algo.exportToCSV();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        
        exit.addActionListener((ActionEvent e) -> { 
        	System.exit(0);
        });
    }
	
	private void loadFileAndRead() {
		CSVReaderAndWriter reader = new CSVReaderAndWriter();
		String path = reader.chooseFolder();
		List<String> elements = reader.processFile(path);
		
		gui_algo.initGame.initGameboard(elements);
	}

	public JMenuBar getMenu() {
		return menubar;
	}

	
	
}
