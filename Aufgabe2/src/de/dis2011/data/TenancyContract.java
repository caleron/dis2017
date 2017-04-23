package de.dis2011.data;

import java.sql.*;

public class TenancyContract extends Contract {
    private Date startDate;
    private int duration;
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

    public TenancyContract(int contract_no) {
        super(contract_no);
    }

    @Override
    protected void saveSpecificFields(boolean createNew) {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (createNew) {
                String insertSQL = "INSERT INTO TENANCYCONTRACT(STARTDATE, DURATION, ADDITIONALCOSTS, CONTRACT) VALUES (?,?,?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL);

                // Setze Anfrageparameter und fC<hre Anfrage ausp
                pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
                pstmt.setInt(2, duration);
                pstmt.setDouble(3, additionalCosts);
                pstmt.setInt(4, contract_no);
                pstmt.executeUpdate();

                pstmt.close();
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE TENANCYCONTRACT SET STARTDATE= ?, DURATION= ?, ADDITIONALCOSTS =? WHERE CONTRACT = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
                pstmt.setInt(2, duration);
                pstmt.setDouble(3, additionalCosts);
                pstmt.setInt(4, contract_no);
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
            String selectSQL = "SELECT * FROM TENANCYCONTRACT WHERE CONTRACT = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, this.getContract_no());

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.setStartDate(rs.getDate("STARTDATE"));
                this.setDuration(rs.getInt("DURATION"));
                this.setAdditionalCosts(rs.getDouble("ADDITIONALCOSTS"));
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
