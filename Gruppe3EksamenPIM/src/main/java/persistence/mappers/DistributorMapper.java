package persistence.mappers;

import businessLogic.Distributor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DB;

/**
 * 
 * @author Marcus
 */
public class DistributorMapper {
    
    private DB database;

    public DistributorMapper(DB database) {
        this.database = database;
    }
    
    public Distributor addNewDistributor(String distributorName, String distributorDescription) {
        try {
            String SQL = "INSERT INTO Distributor (Distributor_Name, Distributor_Description) VALUES (?, ?)";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, distributorName);
            ps.setString(2, distributorDescription);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            Distributor distributor = new Distributor (id, distributorName, distributorDescription);
            return distributor;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Distributor cannot be inserted in the database");
        }
    }
    
    public TreeSet<Distributor> getDistributors() {
        try {
            TreeSet<Distributor> distributorList = new TreeSet();
            String SQL = "SELECT * FROM Distributor";
            ResultSet rs = database.getConnection().prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                int distributor_ID = rs.getInt("Distributor_ID");
                String distributor_Name = rs.getString("Distributor_Name");
                String distributor_Description = rs.getString("Distributor_Description");
                
                Distributor distributor = new Distributor (distributor_ID, distributor_Name, distributor_Description);
                distributorList.add(distributor);
            }
            return distributorList;

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get distributors from Database");
        }
    }

    public int deleteDistributor(int distributorID) {
        int rowsAffected = 0;
        try {
            database.setAutoCommit(false);

            String sqlDeleteProductDistributors = "DELETE FROM Product_Distributor WHERE distributor_ID = ?";
            PreparedStatement psDeleteProductDistributors = database.getConnection().prepareStatement(sqlDeleteProductDistributors);
            psDeleteProductDistributors.setInt(1, distributorID);
            rowsAffected += psDeleteProductDistributors.executeUpdate();

            String sqlDeleteDistributor = "DELETE FROM Distributor WHERE distributor_ID = ?";
            PreparedStatement psDeleteDistributor = database.getConnection().prepareStatement(sqlDeleteDistributor);
            psDeleteDistributor.setInt(1, distributorID);
            rowsAffected += psDeleteDistributor.executeUpdate();
            
        database.getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(DistributorMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't delete distributor from DB");
        }
        database.setAutoCommit(true);
        return rowsAffected;
    }

    public int editDistributor(Distributor distributor) {
        try {
            String SQL = "UPDATE Distributor SET Distributor_Name = ?, Distributor_Description = ? WHERE Distributor_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ps.setString(1, distributor.getDistributorName());
            ps.setString(2, distributor.getDistributorDescription());
            ps.setInt(3, distributor.getDistributorID());
            int result = ps.executeUpdate();
            return result;
            
        } catch (SQLException ex) {
            Logger.getLogger(DistributorMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't update selected distributor from DB");
        }
    }
}