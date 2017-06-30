package com.dis;

import com.dis.data.DB2ConnectionManager;
import com.dis.model.References;
import com.opencsv.CSVReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
//        DatabaseInitializer.init();
        References references = References.getInstance();
        insertReferences(references);
//        readCsv(references);
    }

    private static void readCsv(References references) {

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("sales.csv"), "windows-1252"), ';');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts all data from the DB2INST1 database into our own tables that were created by the {@link
     * com.dis.data.DatabaseInitializer}
     *
     * @param references
     */
    private static void insertReferences(References references) {
        Connection connection = DB2ConnectionManager.getInstance().getConnection();

        try {
            //insert articles
            PreparedStatement statement = connection.prepareStatement("INSERT INTO VSISP16.ARTICLES VALUES (?,?,?)");
            for (References.Article article : references.articles.values()) {
                statement.setInt(1, article.id);
                statement.setString(2, article.name);
                statement.setBigDecimal(3, article.price);
                statement.addBatch();
            }
            statement.executeBatch();

            //insert product categories
            statement = connection.prepareStatement("INSERT INTO VSISP16.PRODUCTCATEGORIES VALUES (?,?)");
            for (References.ProductCategory category : references.productCategories.values()) {
                statement.setInt(1, category.id);
                statement.setString(2, category.name);
                statement.addBatch();
            }
            statement.executeBatch();

            //insert product families
            statement = connection.prepareStatement("INSERT INTO VSISP16.PRODUCTFAMILIES VALUES (?,?)");
            for (References.ProductFamily family : references.productFamilies.values()) {
                statement.setInt(1, family.id);
                statement.setString(2, family.name);
                statement.addBatch();
            }
            statement.executeBatch();

            //insert product groups
            statement = connection.prepareStatement("INSERT INTO VSISP16.PRODUCTGROUPS VALUES (?,?)");
            for (References.ProductGroup productGroup : references.productGroups.values()) {
                statement.setInt(1, productGroup.id);
                statement.setString(2, productGroup.name);
                statement.addBatch();
            }
            statement.executeBatch();


            //insert countries
            statement = connection.prepareStatement("INSERT INTO VSISP16.COUNTRIES VALUES (?,?)");
            for (References.Country country : references.countries.values()) {
                statement.setInt(1, country.id);
                statement.setString(2, country.name);
                statement.addBatch();
            }
            statement.executeBatch();

            //insert regions
            statement = connection.prepareStatement("INSERT INTO VSISP16.REGIONS VALUES (?,?)");
            for (References.Region region : references.regions.values()) {
                statement.setInt(1, region.id);
                statement.setString(2, region.name);
                statement.addBatch();
            }
            statement.executeBatch();

            //insert cities
            statement = connection.prepareStatement("INSERT INTO VSISP16.CITIES VALUES (?,?)");
            for (References.City city : references.cities.values()) {
                statement.setInt(1, city.id);
                statement.setString(2, city.name);
                statement.addBatch();
            }
            statement.executeBatch();

            //insert shops
            statement = connection.prepareStatement("INSERT INTO VSISP16.SHOPS VALUES (?,?)");
            for (References.Shop shop : references.shops.values()) {
                statement.setInt(1, shop.id);
                statement.setString(2, shop.name);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
