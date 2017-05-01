package de.dis2011.data;

import java.sql.*;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="ESTATE_AGENT")
public class EstateAgent {

    @Column(name="NAME")
    private String name;
    @Column(name="ADDRESS")
    private String address;
    @Id
    @Column(name="LOGIN")
    private String login;
    @Column(name="PASSWORD")
    private String password;

    public EstateAgent(){}
    public EstateAgent(String name, String address, String login, String password) {
        this.name = name;
        this.address = address;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
