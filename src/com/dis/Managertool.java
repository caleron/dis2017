package com.dis;
import com.dis.data.DerbyConnectionManager;
import com.dis.menu.Menu;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.dis.table.Block;
import com.dis.table.Table;
import com.dis.table.Board;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
		final int MENU_MAINOUTPUT = 1;
		final int MENU_SECONDARYOUTPUT = 2;
		final int MENU_TERTIARYOUTPUT = 3;
		final int MENU_QUADROUPILARYOUTPUT = 4;
		final int QUIT = 5;


		// Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Testoutput", MENU_TESTOUTPUT);
		mainMenu.addEntry("Main Output", MENU_MAINOUTPUT);
		mainMenu.addEntry("Secondary Output", MENU_SECONDARYOUTPUT);
		mainMenu.addEntry("Tertiary Output", MENU_TERTIARYOUTPUT);
		mainMenu.addEntry("Quadroupilary Output", MENU_QUADROUPILARYOUTPUT);
		mainMenu.addEntry("Beenden", QUIT);

		
		while(true) {
			int response = mainMenu.show();
			String query = "";

			switch(response) {
				case MENU_TESTOUTPUT:
					testoutput();
					break;
                case MENU_MAINOUTPUT:
                	query = "SELECT  CITIES.NAME, TRANSACTIONS.DATE, ARTICLES.NAME, SUM(SALES_COUNT) " +
							"FROM TRANSACTIONS " +
							"JOIN CITIES ON TRANSACTIONS.CITY_ID=CITIES.ID " +
							"JOIN ARTICLES ON TRANSACTIONS.ARTICLE_ID=ARTICLES.ID " +
							"GROUP BY ROLLUP(CITIES.Name, TRANSACTIONS.DATE, ARTICLES.NAME)";
                    outputData(query);
                    break;
				case MENU_SECONDARYOUTPUT:
					query = "SELECT  REGIONS.NAME, TRANSACTIONS.DATE, PRODUCTGROUPS.NAME, SUM(SALES_COUNT) " +
							"FROM TRANSACTIONS " +
							"JOIN REGIONS ON TRANSACTIONS.REGION_ID=REGIONS.ID " +
							"JOIN PRODUCTGROUPS ON TRANSACTIONS.PRODUCT_GROUP_ID=PRODUCTGROUPS.ID " +
							"GROUP BY ROLLUP(REGIONS.Name, TRANSACTIONS.DATE, PRODUCTGROUPS.NAME)";
					outputData(query);
					break;
				case MENU_TERTIARYOUTPUT:
					query = "SELECT  COUNTRIES.NAME, TRANSACTIONS.DATE, PRODUCTFAMILIES.NAME, SUM(SALES_COUNT) " +
							"FROM TRANSACTIONS " +
							"JOIN COUNTRIES ON TRANSACTIONS.COUNTRY_ID=COUNTRIES.ID " +
							"JOIN PRODUCTFAMILIES ON TRANSACTIONS.PRODUCT_FAMILY_ID=PRODUCTFAMILIES.ID " +
							"GROUP BY ROLLUP(COUNTRIES.Name, TRANSACTIONS.DATE, PRODUCTFAMILIES.NAME)";
					outputData(query);
					break;
				case MENU_QUADROUPILARYOUTPUT:
					query = "SELECT  SHOPS.NAME, TRANSACTIONS.DATE, PRODUCTCATEGORIES.NAME, SUM(SALES_COUNT) " +
							"FROM TRANSACTIONS " +
							"JOIN SHOPS ON TRANSACTIONS.SHOP_ID=SHOPS.ID " +
							"JOIN PRODUCTCATEGORIES ON TRANSACTIONS.PRODUCT_CATEGORY_ID=PRODUCTCATEGORIES.ID " +
							"GROUP BY ROLLUP(SHOPS.Name, TRANSACTIONS.DATE, PRODUCTCATEGORIES.NAME)";
					outputData(query);
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

	public static void outputData(String querystring) {
		try {
			Connection connection = DerbyConnectionManager.getInstance().getConnection();

			PreparedStatement statement0 = connection.prepareStatement("SELECT NAME FROM ARTICLES");
			ResultSet rs = statement0.executeQuery();
            List<String> t1Headers = new ArrayList<String>();
            t1Headers.add("Ort");
            t1Headers.add("Datum");
			while(rs.next())
            {
                t1Headers.add(rs.getString("NAME"));
            }
            t1Headers.add("total");

			PreparedStatement statement = connection.prepareStatement(querystring);

            ResultSet result = statement.executeQuery();
            DisplayData displayData = new DisplayData();
            while(result.next()) {
                String city = result.getString(1);
                Date date = new Date(result.getLong(2));
                String article = result.getString(3);
                int amount = result.getInt(4);

                displayData.add(city,date,article,amount);

            }
            List<List<String>> t1Rows = displayData.makeOutput(t1Headers);

            Board b = new Board(800);
            b.setInitialBlock(new Block(b, 798, 2, "Standard Ausgabe").allowGrid(false).setBlockAlign(Block.BLOCK_CENTRE).setDataAlign(Block.DATA_CENTER));
            b.appendTableTo(0, Board.APPEND_BELOW, new Table(b, 800, t1Headers, t1Rows));
            System.out.println(b.invalidate().build().getPreview());
		}
		catch(Exception e)	{
			System.out.println("QUERY FAILED");
			e.printStackTrace();
		}
	}
}
