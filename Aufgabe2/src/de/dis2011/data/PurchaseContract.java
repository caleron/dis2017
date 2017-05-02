package de.dis2011.data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.sql.*;

@Entity
@Table(name="PURCHASECONTRACT")
@PrimaryKeyJoinColumn(name="CONTRACT")
@OnDelete(action = OnDeleteAction.CASCADE)
public class PurchaseContract extends Contract {
    @Column(name="INTERESTRATE")
    private double interestRate;
    @Column(name="NO_OF_INSTALL")
    private int noOfInstallments;

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getNoOfInstallments() {
        return noOfInstallments;
    }

    public void setNoOfInstallments(int noOfInstallments) {
        this.noOfInstallments = noOfInstallments;
    }

    @Override
    public String toString()
    {
        return toShortString() + "\nOrt: " + getPlace() + "\nInterestrate: " + getInterestRate() +
                "\nRaten: " + getNoOfInstallments();
    }
}
