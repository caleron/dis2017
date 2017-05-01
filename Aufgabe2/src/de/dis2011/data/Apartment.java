package de.dis2011.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Patrick on 23.04.2017.
 */
@Entity
@Table(name="APARTMENT")
@PrimaryKeyJoinColumn(name="ESTATE")
public class Apartment extends Estate {
    @Column(name="FLOOR")
    private int floor;
    @Column(name="RENT")
    private double rent;
    @Column(name="ROOMS")
    private int rooms;
    @Column(name="BALCONY")
    private int balcony;
    @Column(name="BUILT_IN_KITCHEN")
    private int builtInKitchen;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int isBalcony() {
        return balcony;
    }

    public void setBalcony(int balcony) {
        this.balcony = balcony;
    }

    public int isBuiltInKitchen() {
        return builtInKitchen;
    }

    public void setBuiltInKitchen(int builtInKitchen) {
        this.builtInKitchen = builtInKitchen;
    }

    public Apartment(){}
}
