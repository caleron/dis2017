package de.dis2011.data;

import javax.persistence.*;
import java.sql.*;

/**
 * Created by Max on 22.04.2017.
 */
@Entity
@Table(name="PERSON")
public class Person {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private int id;
    @Column(name="FIRST_NAME")
    private String first_name;
    @Column(name="NAME")
    private String name;
    @Column(name="ADRESS")
    private String adress;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString()
    {
        return getFirst_name() + " " + getName();
    }
}
