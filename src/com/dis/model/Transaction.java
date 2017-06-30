package com.dis.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Transaction {
    public Transaction(String[] csvLine, References references) throws ParseException {
        //field in csv: Datum;Shop;Artikel;Verkauft;Umsatz
        //sample line: 01.01.2017;Superstore Berlin;AEG Öko Lavatherm 59850 Sensidry;25;24975,00
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        date = format.parse(csvLine[0]).getTime();

        References.Shop shop = null;
        for (References.Shop shop1 : references.shops.values()) {
            if (shop1.name.equals(csvLine[1])) {
                shop = shop1;
                break;
            }
        }

        References.Article article = null;
        for (References.Article article1 : references.articles.values()) {
            if (article1.name.equals(csvLine[2])) {
                article = article1;
                break;
            }
        }

        if (shop == null || article == null) {
            System.out.println("SHIT");
            return;
        }

        shopId = shop.id;
        cityId = shop.city.id;
        regionId = shop.city.region.id;
        countryId = shop.city.region.country.id;
        articleId = article.id;
        productCategoryId = article.productGroup.family.category.id;
        productFamilyId = article.productGroup.family.id;
        productGroupId = article.productGroup.id;
        salesCount = Integer.parseInt(csvLine[3]);
        salesAmount = new BigDecimal(csvLine[4].replace(",", "."));
    }

    public int cityId;
    public int shopId;
    public int articleId;
    public int countryId;
    public int productCategoryId;
    public int productGroupId;
    public int productFamilyId;
    public int regionId;
    public long date;
    public int salesCount;
    public BigDecimal salesAmount;
}
