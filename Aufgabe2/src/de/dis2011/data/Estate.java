package de.dis2011.data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by Patrick on 21.04.2017.
 */
@Entity
@Table(name="ESTATE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Estate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    private int id;
    @Column(name="CITY")
    private String city;
    @Column(name="STREET")
    private String street;
    @Column(name="STREETNUMBER")
    private int streetNumber;
    @Column(name="POSTCODE")
    private int postCode;
    @Column(name="SQUAREAREA")
    private int squareArea;
    @ManyToOne
    @JoinColumn(name="AGENT", referencedColumnName = "LOGIN")
    private EstateAgent agent;

    public Estate(){}

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public int getPostCode() {
        return postCode;
    }

    public int getSquareArea() {
        return squareArea;
    }

    public EstateAgent getAgent() {
        return agent;
    }

    public void setCity(String city) {  this.city = city;    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public void setSquareArea(int squareArea) {
        this.squareArea = squareArea;
    }

    public void setAgent(EstateAgent agent) {
        this.agent = agent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    @Override
    public String toString()
    {
        return "Immobilie Nr. " + getId();
    }
}
