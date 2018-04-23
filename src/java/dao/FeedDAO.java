package dao;

import beans.Feed;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import model.LoginBean;

public class FeedDAO {

    public static Feed getFeedsByUsername(String username) {
        Feed lb = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            // The ? below are parameters (i.e., placeholders) to the query and are resolved
// in the setString method below
            String queryString = "select * from Project353.Posts join Project353.Users on Project353.Users.userid = Project353.Posts.userid order by Project353.Posts.date desc";

// Note the use of a diff class, called PreparedStatement
            PreparedStatement pstmt = DBConn.prepareStatement(queryString);
            ResultSet rs = pstmt.executeQuery();

            boolean r = rs.next();
            lb = new Feed();
            if (r) {
                
                lb.addToList(rs.getString("content"));
            }
            DBConn.close();
            return lb;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        
        return null;
        
    }
    public static int addPostToDB(String username, String postContent){
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
            Date temp = new Date();
            
            insertString = "INSERT INTO Project353.Posts VALUES ('"
                    + username
                    + "', '" + postContent
                    + "', " + temp.getTime()+")";
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