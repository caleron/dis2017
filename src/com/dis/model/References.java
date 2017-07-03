package com.dis.model;

import com.dis.data.DB2ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class References {
	public Map<Integer, Article> articles = new HashMap<>();
	public Map<Integer, City> cities = new HashMap<>();
	public Map<Integer, Country> countries = new HashMap<>();
	public Map<Integer, ProductCategory> productCategories = new HashMap<>();
	public Map<Integer, ProductFamily> productFamilies = new HashMap<>();
	public Map<Integer, ProductGroup> productGroups = new HashMap<>();
	public Map<Integer, Region> regions = new HashMap<>();
	public Map<Integer, Shop> shops = new HashMap<>();

	private static References instance;

	public static References getInstance() {
		if (instance == null) {
			try {
				instance = new References();
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(88);
			}
		}
		return instance;
	}

	private References() throws SQLException {
		Connection connection = DB2ConnectionManager.getInstance().getConnection();

		Statement stmt = connection.createStatement();
		System.out.print("loading reference data...");
		// load countries
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM DB2INST1.LANDID");
		while (resultSet.next()) {
			Country country = new Country();
			country.name = resultSet.getString("NAME");
			countries.put(resultSet.getInt("LANDID"), country);
		}
		resultSet.close();

		// load regions
		resultSet = stmt.executeQuery("SELECT * FROM DB2INST1.REGIONID");
		while (resultSet.next()) {
			Region region = new Region();
			region.name = resultSet.getString("NAME");
			region.country = countries.get(resultSet.getInt("LANDID"));
			region.id = resultSet.getInt("REGIONID");
			regions.put(region.id, region);
		}
		resultSet.close();

		// load cities
		resultSet = stmt.executeQuery("SELECT * FROM DB2INST1.STADTID");
		while (resultSet.next()) {
			City city = new City();
			city.name = resultSet.getString("NAME");
			city.region = regions.get(resultSet.getInt("REGIONID"));
			city.id = resultSet.getInt("STADTID");
			cities.put(city.id, city);
		}
		resultSet.close();

		// load shops
		resultSet = stmt.executeQuery("SELECT * FROM DB2INST1.SHOPID");
		while (resultSet.next()) {
			Shop shop = new Shop();
			shop.name = resultSet.getString("NAME");
			shop.city = cities.get(resultSet.getInt("STADTID"));
			shop.id = resultSet.getInt("SHOPID");
			shops.put(shop.id, shop);
		}
		resultSet.close();

		// load product categories
		resultSet = stmt.executeQuery("SELECT * FROM DB2INST1.PRODUCTCATEGORYID");
		while (resultSet.next()) {
			ProductCategory category = new ProductCategory();
			category.name = resultSet.getString("NAME");
			category.id = resultSet.getInt("PRODUCTCATEGORYID");
			productCategories.put(category.id, category);
		}
		resultSet.close();

		// load product families
		resultSet = stmt.executeQuery("SELECT * FROM DB2INST1.PRODUCTFAMILYID");
		while (resultSet.next()) {
			ProductFamily family = new ProductFamily();
			family.name = resultSet.getString("NAME");
			family.id = resultSet.getInt("PRODUCTFAMILYID");
			family.category = productCategories.get(resultSet.getInt("PRODUCTCATEGORYID"));
			productFamilies.put(family.id, family);
		}
		resultSet.close();

		// load product groups
		resultSet = stmt.executeQuery("SELECT * FROM DB2INST1.PRODUCTGROUPID");
		while (resultSet.next()) {
			ProductGroup group = new ProductGroup();
			group.name = resultSet.getString("NAME");
			group.id = resultSet.getInt("PRODUCTGROUPID");
			group.family = productFamilies.get(resultSet.getInt("PRODUCTFAMILYID"));
			productGroups.put(group.id, group);
		}
		resultSet.close();

		// load articles
		resultSet = stmt.executeQuery("SELECT * FROM DB2INST1.ARTICLEID");
		while (resultSet.next()) {
			Article article = new Article();
			article.name = resultSet.getString("NAME");
			article.price = resultSet.getBigDecimal("PREIS");
			article.id = resultSet.getInt("ARTICLEID");
			article.productGroup = productGroups.get(resultSet.getInt("PRODUCTGROUPID"));
			articles.put(article.id, article);
		}
		resultSet.close();
		System.out.println("done");
	}

	public static class Article {
		public String name;
		public BigDecimal price;
		public int id;
		public ProductGroup productGroup;
	}

	public class Country {
		public int id;
		public String name;
	}

	public class ProductCategory {
		public int id;
		public String name;
	}

	public class ProductFamily {
		public int id;
		public String name;
		public ProductCategory category;
	}

	public class ProductGroup {
		public int id;
		public String name;
		public ProductFamily family;
	}

	public class Region {
		public int id;
		public String name;
		public Country country;
	}

	public class City {
		public int id;
		public String name;
		public Region region;
	}

	public class Shop {
		public int id;
		public String name;
		public City city;

	}
}
