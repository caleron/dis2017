package de.dis2011.data;

import java.sql.*;

public class PurchaseContract extends Contract {
    private double interestRate;
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

    public PurchaseContract(int id) {
        super("house", id);
    }

    @Override
    protected void saveSpecificFields(boolean createNew) {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (createNew) {
                String insertSQL = "INSERT INTO PURCHASECONTRACT(INTERESTRATE, NO_OF_INSTALL, CONTRACT) VALUES (?,?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL);

                // Setze Anfrageparameter und fC<hre Anfrage ausp
                pstmt.setDouble(1, interestRate);
                pstmt.setInt(2, noOfInstallments);
                pstmt.setInt(3, contract_no);
                pstmt.executeUpdate();

                pstmt.close();
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE PURCHASECONTRACT SET INTERESTRATE = ?, NO_OF_INSTALL = ? WHERE CONTRACT = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setDouble(1, interestRate);
                pstmt.setInt(2, noOfInstallments);
                pstmt.setInt(3, contract_no);
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadSpecificFields() {
        try {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM PURCHASECONTRACT WHERE CONTRACT = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, this.getContract_no());

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                this.setInterestRate(rs.getDouble("INTERESTRATE"));
                this.setNoOfInstallments(rs.getInt("NO_OF_INSTALL"));
                rs.close();
                pstmt.close();
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
