package com.dis;
import com.dis.menu.Menu;
import java.util.Arrays;
import java.util.List;
import com.dis.table.Block;
import com.dis.table.Table;
import com.dis.table.Board;

public class Managertool {

	public static void main(String[] args) {
		showMainMenu();
	}

	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		// Menüoptionen
		final int MENU_TESTOUTPUT = 0;
		final int QUIT = 1;

		// Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Testoutput", MENU_TESTOUTPUT);
		mainMenu.addEntry("Beenden", QUIT);
		
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case MENU_TESTOUTPUT:
					testoutput();
					break;
				case QUIT:
					return;
			}
		}
	}
	
	public static void testoutput(){
		String header = ""
                + "TESTOUTPUT\n";
        List<String> t1Headers = Arrays.asList("CITY", "SALES", "PRODUCT1", "PRODUCT2", "PRODUCT3", "TOTAL");
        List<List<String>> t1Rows = Arrays.asList(
                Arrays.asList("HAMBURG", "quarter 1, 2011", "10", "20", "30", "60"),
                Arrays.asList("HAMBURG", "quarter 2, 2011", "10", "20", "30", "60"),
                Arrays.asList("HAMBURG", "quarter 3, 2011", "10", "20", "30", "60"),
                Arrays.asList("HAMBURG", "quarter 4, 2011", "10", "20", "30", "60")
        );

        //bookmark
        Board b = new Board(120);
        b.setInitialBlock(new Block(b, 118, 2, header).allowGrid(false).setBlockAlign(Block.BLOCK_CENTRE).setDataAlign(Block.DATA_CENTER));
        b.appendTableTo(0, Board.APPEND_BELOW, new Table(b, 120, t1Headers, t1Rows));
        System.out.println(b.invalidate().build().getPreview());
	}
}
