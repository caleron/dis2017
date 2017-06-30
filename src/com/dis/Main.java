package com.dis;

import com.dis.data.DB2ConnectionManager;
import com.dis.data.DatabaseInitializer;
import com.dis.model.References;
import com.dis.model.Transaction;
import com.opencsv.CSVReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseInitializer.init();
            References references = References.getInstance();
            insertReferences(references);
            readCsv(references);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readCsv(References references) throws SQLException, IOException, ParseException {
        System.out.println("start reading csv file");
        Connection connection = DB2ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO VSISP16.TRANSACTIONS (CITY_ID, SHOP_ID, ARTICLE_ID, COUNTRY_ID, PRODUCT_CATEGORY_ID, PRODUCT_FAMILY_ID, PRODUCT_GROUP_ID, DATE, SALES_COUNT, SALES_AMOUNT, REGION_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("sales.csv"), "windows-1252"), ';');
        String[] nextLine;
        //skip the first line
        reader.readNext();
        int counter = 0;
        while ((nextLine = reader.readNext()) != null) {
            Transaction transaction = new Transaction(nextLine, references);
            statement.setInt(1, transaction.cityId);
            statement.setInt(2, transaction.shopId);
            statement.setInt(3, transaction.articleId);
            statement.setInt(4, transaction.countryId);
            statement.setInt(5, transaction.productCategoryId);
            statement.setInt(6, transaction.productFamilyId);
            statement.setInt(7, transaction.productGroupId);
            statement.setLong(8, transaction.date);
            statement.setInt(9, transaction.salesCount);
            statement.setBigDecimal(10, transaction.salesAmount);
            statement.setInt(11, transaction.regionId);
            statement.addBatch();
            counter++;
            if (counter >= 1000) {
                Date start = new Date();
                System.out.print("inserting 1000 rows... ");
                statement.executeBatch();
                System.out.println("done within " + (new Date().getTime() - start.getTime()) + "ms");
                counter = 0;
            }
        }
        if (counter > 0) {
            Date start = new Date();
            System.out.print("inserting remaining " + counter + " rows... ");
            statement.executeBatch();
            System.out.println("done within " + (new Date().getTime() - start.getTime()) + "ms");
        }
        System.out.println("finished!");
    }

    /**
     * Inserts all data from the DB2INST1 database into our own tables that were created by the {@link
     * com.dis.data.DatabaseInitializer}
     *
     * @param references
     */
    private static void insertReferences(References references) throws SQLException {
        Connection connection = DB2ConnectionManager.getInstance().getConnection();

        System.out.print("inserting reference data...");
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
        System.out.println("done");
    }
}
