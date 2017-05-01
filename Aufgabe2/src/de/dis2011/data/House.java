package de.dis2011.data;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Entity
@Table(name="HOUSE")
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(name="ESTATE")
public class House extends Estate {
    @Column(name="FLOOR")
    private int floor;
    @Column(name="PRICE")
    private double price;
    @Column(name="GARDEN")
    private boolean garden;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
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
}
