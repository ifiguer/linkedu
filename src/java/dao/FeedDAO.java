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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import model.Post;

@Named
@SessionScoped
public class FeedDAO implements Serializable {

    public List<Post> getFeedsByUsername(String username) {
        List<Post> posts = new ArrayList<>();
        String following;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            // Select all of the user's posts
            String queryString = "select * from Project353.Posts join Project353.Users on Project353.Users.userid = Project353.Posts.userid where Project353.Users.userid like '" + username + "' order by Project353.Posts.datePosted desc";
            PreparedStatement pstmt = DBConn.prepareStatement(queryString);
            ResultSet rs = pstmt.executeQuery();
            //Put them in a list if there are any
            while (rs.next()) {
                String content = rs.getString("content");
                String userID = rs.getString("userID");
                long datePosted = rs.getLong("datePosted");
                Post temp = new Post(userID, content, datePosted);
                posts.add(temp);
            }
            //Get the list of users the current user is following
            queryString = "select following from Project353.Users where Project353.Users.userid like '" + username + "'";
            pstmt = DBConn.prepareStatement(queryString);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                following = rs.getString("following");
                String[] followIDs = following.split(":");
                for (String a : followIDs) {
                    queryString = "select * from Project353.Posts join Project353.Users on Project353.Users.userid = Project353.Posts.userid where Project353.Users.userid like '" + a + "' order by Project353.Posts.datePosted desc";
                    pstmt = DBConn.prepareStatement(queryString);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String content = rs.getString("content");
                        String userID = rs.getString("userID");
                        long datePosted = rs.getLong("datePosted");
                        Post temp = new Post(userID, content, datePosted);
                        posts.add(temp);
                    }
                }
            }
            
            Collections.sort(posts);
            
            DBConn.close();
            return posts;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;

    }

    public int addPostToDB(String username, String postContent) {
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
                    + "', " + temp.getTime() + ")";
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
