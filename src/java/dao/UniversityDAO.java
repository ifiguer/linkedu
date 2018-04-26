package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.University;

public class UniversityDAO implements Serializable {

    private static final String DB_URL = "jdbc:derby://localhost:1527/Project353";
    private static final String USERNAME = "itkstu";
    private static final String PASSWORD = "student";
    private static final String QUERY_BY_NAME = "SELECT * FROM Project353.UNIVERSITIES WHERE NAME LIKE ?";

    public University getUniversity(int id) {
        return new University("Illinois State Unviersity", "A description goes here.");
    }

    public University getUniversityByName(String name) {
        University university = null;

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

            if (resultSet.next()) {
                String n = resultSet.getString("name");
                String description = resultSet.getString("description");
                university = new University(n, description);
            }
        } catch (SQLException exception) {
            System.out.println("SQL ERROR: " + exception.getMessage());
        }

        return university;
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

            if (rs.next()) {
                String n = rs.getString("name");
                String description = rs.getString("description");
                featured.add(new University(n, description));
            }
        } catch (SQLException exception) {
            System.out.println("SQL ERROR: " + exception.getMessage());
        }
        return featured;
    }
}
