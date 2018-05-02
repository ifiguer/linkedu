package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.University;

public class UniversityDAO implements Serializable {

    private static final String DB_URL = "jdbc:derby://localhost:1527/Project353";
    private static final String USERNAME = "itkstu";
    private static final String PASSWORD = "student";
    private static final String QUERY_BY_NAME = "SELECT * FROM Project353.UNIVERSITIES WHERE upper(NAME) LIKE upper('%'||?||'%')";

    public University getUniversity(int id) {
        return new University("Illinois State Unviersity", "A description goes here.");
    }

    public List<University> getUniversityByName(String name) {
        University university;
        List<University> universityResults = new ArrayList<>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement query = connection.prepareStatement(QUERY_BY_NAME);
            query.setString(1, name);

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                String n = resultSet.getString("name");
                String description = resultSet.getString("description");
                university = new University(n, description);
                universityResults.add(university);
            }
        } catch (SQLException exception) {
            System.out.println("SQL ERROR: " + exception.getMessage());
        }
        Collections.sort(universityResults);
        return universityResults;
    }

    public ArrayList<University> getFeaturedUniversities() {
        ArrayList<University> featured = new ArrayList<>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String queryString = "select * from Project353.Universities where Project353.Universities.featured";

            // Note the use of a diff class, called PreparedStatement
            PreparedStatement pstmt = connection.prepareStatement(queryString);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String n = rs.getString("name");
                String description = rs.getString("description");
                featured.add(new University(n, description));
            }
           
        } catch (SQLException exception) {
            System.out.println("SQL ERROR: " + exception.getMessage());
        }
        return featured;
    }

    public int addToFeatured(String u) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "UPDATE Project353.Universities set ";
            insertString += "featured=true";
            
            
            insertString += " where name='" + u + "'";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }

    public int removeFeatured(String u) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "UPDATE Project353.Universities set ";
            insertString += "featured=false";
            
            
            insertString += " where name='" + u + "'";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }
}
