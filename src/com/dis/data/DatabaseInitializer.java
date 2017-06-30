package com.dis.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void init() {
        Connection connection = DB2ConnectionManager.getInstance().getConnection();

        Statement statement;
        try {
            statement = connection.createStatement();
            //drop all tables
            System.out.print("Dropping tables... ");
            dropTables(statement, new String[]{"TRANSACTIONS", "ARTICLES", "COUNTRIES",
                    "PRODUCTCATEGORIES", "PRODUCTFAMILIES", "PRODUCTGROUPS", "REGIONS",
                    "SHOPS", "CITIES"});

            System.out.println("done");

            System.out.print("Creating tables... ");
            statement.execute("CREATE TABLE SHOPS (ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255) NOT NULL)");
            statement.execute("CREATE TABLE ARTICLES (ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255) NOT NULL, PRICE DOUBLE NOT NULL )");
            statement.execute("CREATE TABLE COUNTRIES (ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255) NOT NULL)");
            statement.execute("CREATE TABLE PRODUCTCATEGORIES (ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255) NOT NULL)");
            statement.execute("CREATE TABLE PRODUCTFAMILIES (ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255) NOT NULL)");
            statement.execute("CREATE TABLE PRODUCTGROUPS (ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255) NOT NULL)");
            statement.execute("CREATE TABLE REGIONS (ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255) NOT NULL)");
            statement.execute("CREATE TABLE CITIES (ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255) NOT NULL)");
            statement.execute("CREATE TABLE VSISP16.transactions\n" +
                    "(\n" +
                    "    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1 INCREMENT BY 1),\n" +
                    "    city_id INT,\n" +
                    "    shop_id INT,\n" +
                    "    article_id INT,\n" +
                    "    country_id INT,\n" +
                    "    product_category_id INT,\n" +
                    "    product_family_id INT,\n" +
                    "    product_group_id INT,\n" +
                    "    date BIGINT,\n" +
                    "    sales_count INT,\n" +
                    "    sales_amount DECIMAL(16,2),\n" +
                    "    region_id INT,\n" +
                    "    CONSTRAINT transactions_ARTICLES_ID_fk FOREIGN KEY (article_id) REFERENCES ARTICLES (ID) ON DELETE CASCADE,\n" +
                    "    CONSTRAINT transactions_CITIES_ID_fk FOREIGN KEY (city_id) REFERENCES CITIES (ID) ON DELETE CASCADE,\n" +
                    "    CONSTRAINT transactions_PRODUCTCATEGORIES_ID_fk FOREIGN KEY (product_category_id) REFERENCES PRODUCTCATEGORIES (ID) ON DELETE CASCADE,\n" +
                    "    CONSTRAINT transactions_PRODUCTFAMILIES_ID_fk FOREIGN KEY (product_family_id) REFERENCES PRODUCTFAMILIES (ID) ON DELETE CASCADE,\n" +
                    "    CONSTRAINT transactions_PRODUCTGROUPS_ID_fk FOREIGN KEY (product_group_id) REFERENCES PRODUCTGROUPS (ID) ON DELETE CASCADE,\n" +
                    "    CONSTRAINT transactions_REGIONS_ID_fk FOREIGN KEY (region_id) REFERENCES REGIONS (ID) ON DELETE CASCADE,\n" +
                    "    CONSTRAINT transactions_SHOPS_ID_fk FOREIGN KEY (shop_id) REFERENCES SHOPS (ID) ON DELETE CASCADE,\n" +
                    "    CONSTRAINT transactions_COUNTRIES_ID_fk FOREIGN KEY (country_id) REFERENCES COUNTRIES (ID) ON DELETE CASCADE" +
                    ")");

            System.out.println("done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropTables(Statement stmt, String[] tables) {
        for (String table : tables) {
            //ignore error if a table does not exist
            try {
                stmt.execute("DROP TABLE " + table);
            } catch (SQLException ignored) {
            }
        }
    }
}
