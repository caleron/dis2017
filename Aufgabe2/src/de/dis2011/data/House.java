package de.dis2011.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Entity
@Table(name="HOUSE")
@PrimaryKeyJoinColumn(name="ESTATE")
public class House extends Estate {
    @Column(name="FLOOR")
    private int floors;
    @Column(name="PRICE")
    private double price;
    @Column(name="GARDEN")
    private boolean garden;

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public House() {}
}
