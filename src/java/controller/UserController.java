/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.LoginBean;
import model.LoginBeanDA;
import model.User;

/**
 *
 * @author it353s836
 */
@ManagedBean
@SessionScoped
public class UserController implements Serializable {

    @Inject
    private UserDAO userDAO;
    @ManagedProperty(value = "#{LoginBean}")
    private LoginBean temp;
    private ArrayList<User> searchStudentResults;
    private String errorResponse;
    private String studentSearch;
    private String studentFollowID;
    private String userToContact;
    private static final String DB_URL = "jdbc:derby://localhost:1527/Project353";
    private static final String USERNAME = "itkstu";
    private static final String PASSWORD = "student";

    public String searchForStudents() {
        if ((searchStudentResults = userDAO.checkDBForStudents(studentSearch)) == null) {
            return "welcome";
        } else {
            return "studentSearchResults";
        }

    }

    public void followUser() {
        if (userDAO.addUserToFollowingList(temp, studentFollowID) == 1) {
            errorResponse = "Successfully followed user";
        } else {
            errorResponse = "An error occurred following user";
        }

    }

    public void contactUser() {
        String email = "";
        String n="";
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String queryString = "select * from Project353.Users where Project353.Users.userid like '" + userToContact + "'";
            PreparedStatement query = connection.prepareStatement(queryString);
            
            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                 n = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
                 email = resultSet.getString("email");
            }
        } catch (SQLException exception) {
            System.out.println("SQL ERROR: " + exception.getMessage());
        }
        String to = "brandonjames879@gmail.com"; //email
        String from = "bmjame1@ilstu.edu";
        String host = "outlook.office365.com";
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bmjame1@ilstu.edu", "passwordgoeshere");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("Someone would like to know more about you, " + n + "!");
            String content = "<h2>"+ temp.getFullname()+" is looking to get in touch with you! <br/> You can contact them at " + temp.getEmail() + "</h2>";

            message.setContent(content,
                    "text/html");

            Transport.send(message);
            System.out.println("Sent message successfully....");
            
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (Exception e) {

        }
        
    }

    public ArrayList<User> getSearchStudentResults() {
        return searchStudentResults;
    }

    public void setSearchStudentResults(ArrayList<User> searchStudentResults) {
        this.searchStudentResults = searchStudentResults;
    }

    public String getStudentSearch() {
        return studentSearch;
    }

    public void setStudentSearch(String studentSearch) {
        this.studentSearch = studentSearch;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getStudentFollowID() {
        return studentFollowID;
    }

    public void setStudentFollowID(String studentFollowID) {
        this.studentFollowID = studentFollowID;
    }

    public LoginBean getTemp() {
        return temp;
    }

    public void setTemp(LoginBean temp) {
        this.temp = temp;
    }

    public String getUserToContact() {
        return userToContact;
    }

    public void setUserToContact(String userToContact) {
        this.userToContact = userToContact;
    }

}
