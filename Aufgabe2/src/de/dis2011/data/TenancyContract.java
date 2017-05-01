package de.dis2011.data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Entity
@Table(name="TENANCYCONTRACT")
@PrimaryKeyJoinColumn(name="CONTRACT")
@OnDelete(action = OnDeleteAction.CASCADE)
public class TenancyContract extends Contract {
    @Column(name="STARTDATE")
    private Date startDate;
    @Column(name="DURATION")
    private int duration;
    @Column(name="ADDITIONALCOSTS")
    private double additionalCosts;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(double additionalCosts) {
        this.additionalCosts = additionalCosts;
    }

    @Override
    public String toString()
    {
        return super.toString() + "\nOrt: " + getPlace() + "\nStartdatum: " + getStartDate() +
                "\nDauer: " + getDuration() + "\nZus. Kosten: " + getAdditionalCosts();
    }
}
