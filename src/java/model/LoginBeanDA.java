package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginBeanDA {

    public static LoginBean validInfo(String username, String password) {
        LoginBean lb = null;
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
            String queryString = "select * from Project353.USERS where USERID = ? and PASSWORD = ?";

            // Note the use of a diff class, called PreparedStatement
            PreparedStatement pstmt = DBConn.prepareStatement(queryString);
            pstmt.setString(1, username); // replace the 1st ? with username
            pstmt.setString(2, password); // replace the 2nd ? with password
            ResultSet rs = pstmt.executeQuery();

            boolean r = rs.next();
            if (r) {
                lb = new LoginBean();
                lb.setEmail(rs.getString("email"));
                lb.setFirstname(rs.getString("firstname"));
                lb.setLastname(rs.getString("lastname"));
                lb.setActScore(rs.getInt("act"));
                lb.setProfileURL(rs.getString("profileurl"));

            }
            DBConn.close();
            return lb;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;

    }

    public static boolean usernameTaken(String username) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String queryString = "select * from Project353.Users where USERID = '" + username + "'";
            Statement stmt = DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            boolean r = rs.next();

            DBConn.close();
            return r;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static int storeCustomerToDB(LoginBean cust) {
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
            insertString = "INSERT INTO Project353.Users VALUES ('"
                    + cust.getUsername()
                    + "', '" + cust.getPassword()
                    + "', '" + cust.getFirstname()
                    + "', '" + cust.getLastname()
                    + "', '" + cust.getEmail()
                    + "', 'img/egg.jpg"
                    + "', " + cust.getActScore()
                    + ")";
            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }

    public static int storeProfileImgUrlToDB(String profileURL, String username) {
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
            insertString = "UPDATE Project353.Users set " + "profileURL='uploads\\" + profileURL + "' where userid='" + username + "'";

            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }

    public static int updateCustomerToDB(LoginBean cust) {
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
            insertString = "UPDATE Project353.Users set ";

            insertString += (cust.getPassword().equals("") ? "" : "password='" + cust.getPassword() + "',");
            insertString += (cust.getEmail().equals("") ? "" : "email='" + cust.getEmail() + "',");
            insertString += (cust.getFirstname().equals("") ? "" : "firstname='" + cust.getFirstname() + "',");
            insertString += (cust.getLastname().equals("") ? "" : "lastname='" + cust.getLastname() + "',");
            insertString += (cust.getSecQuestion().equals("") ? "" : "secquestion='" + cust.getSecQuestion() + "',");
            insertString += (cust.getSecAnswer().equals("") ? "" : "secanswer='" + cust.getSecAnswer() + "'");
            //insertString += " userid = '" + cust.getUsername() + "' ";
            insertString += " where userid='" + cust.getUsername() + "'";
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
