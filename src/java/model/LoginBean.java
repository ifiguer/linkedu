/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import utility.EncryptPass;

/**
 *
 * @author ibran
 */
@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {

    private String username;

    private String usernamemessage;
    private String fullname;
    private String firstname;
    private String lastname;
    private String password;
    private String confirmPassword;
    private String email;
    private String secQuestion;
    private String secAnswer;

    private String usernamestyle;
    private int loginAttempts = 0;
    private String errorResponse = "";
    private boolean loginSuccess = false;

    public String update() {
        if (!password.equals(confirmPassword)) {
            errorResponse = "Passwords do not match";
            return "update.xhtml";
        } else if (LoginBeanDA.updateCustomerToDB(this) != 1) {
            errorResponse = "An error has occurred while updating your profile. Please check input and try again";
            return "update.xhtml";
        }
        return "echo.xhtml";
    }

    public String login() {
        if (validateLogin()) {
            loginSuccess = true;
            return "LoginGood.xhtml";
        } else {
            loginSuccess = false;
            return "LoginBad.xhtml";
        }

    }

    public String persist() {
        if (!validateSignUpForm()) {
            return "signUp.xhtml";
        } else {
            if (LoginBeanDA.storeCustomerToDB(this) != 1) {
                errorResponse = "There was a problem creating your account. Please try again later.";
                return "signUp.xhtml";
            } else {
                sendMail();
                loginSuccess = true;
                return "echo.xhtml";
            }

        }

    }

    private boolean sendMail() {
        String to = email;
        String from = "bmjame1@ilstu.edu";
        String host = "outlook.office365.com";
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bmjame1@ilstu.edu", "oops");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("Welcome to the club, " + getFirstname() + " " + getLastname() + "!");
            String content = "Username: " + getUsername()
                    + "<br/>Email: " + getEmail()
                    + "<br/>Full Name: " + getFirstname() + " " + getLastname()
                    + "<br/>Security Question: " + getSecQuestion()
                    + "<br/>Security Answer: " + getSecAnswer();

            message.setContent("<h1>Welcome to our club!</h1>" + "<br/>" + "<img src=\"https://media1.tenor.com/images/84639bd1ba31a05d8ec04a880eecde21/tenor.gif?itemid=10909950\">" + "<br/>" + "<h2>Here is your information: </h2>" + "<br/>" + content,
                    "text/html");

            Transport.send(message);
            System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (Exception e) {

        }
        return false;
    }

    private boolean validateLogin() {
        LoginBean temp;
        if (loginAttempts >= 3) {
            errorResponse = "You have unsuccessfully logged in too many times. Please try again later.";
            return false;
        }

        if ((temp = LoginBeanDA.validInfo(username, password)) == null) {
            loginAttempts++;
            errorResponse = "Invalid username or password";
            return false;
        }

        fullname = temp.getFirstname() + " " + temp.getLastname();
        setEmail(temp.getEmail());
        setSecQuestion(temp.getSecQuestion());
        setSecAnswer(temp.getSecAnswer());
        setFirstname(temp.getFirstname());
        setLastname(temp.getLastname());
        loginSuccess = true;

        return true;
    }

    private boolean validateSignUpForm() {
        if (!checkNames()) {
            return false;
        } else if (LoginBeanDA.usernameTaken(username)) {
            errorResponse = "Username is already taken";
            return false;
        } else if (!password.equals(confirmPassword)) {
            errorResponse = "Passwords do not match";
            return false;
        }

        return true;
    }

    public String validateUserNameOnKey() {
        if (LoginBeanDA.usernameTaken(getUsername())) {
            usernamemessage = "Username is already taken";
            usernamestyle = "color:red";
            return "Username is already taken";
        } else {
            usernamemessage = "Username is valid!";
            usernamestyle="color:green";
            return "Username is valid!";

        }
    }

    private boolean checkNames() {
        String fullname2 = firstname + " " + lastname;
        if (fullname2.length() < 6 || fullname2.length() > 25) {
            errorResponse = "Fullname length must be between 6 and 25 characters";
            return false;
        }
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = EncryptPass.hashPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecQuestion() {
        return secQuestion;
    }

    public void setSecQuestion(String secQuestion) {
        this.secQuestion = secQuestion;
    }

    public String getSecAnswer() {
        return secAnswer;
    }

    public void setSecAnswer(String secAnswer) {
        this.secAnswer = secAnswer;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public void setconfirmPassword(String confirmPassword) {
        this.confirmPassword = EncryptPass.hashPassword(confirmPassword);
    }

    public String getconfirmPassword() {
        return confirmPassword;
    }

    public String redirectLogin() {
        return "login.xhtml";
    }

    public String getUsernamemessage() {
        return usernamemessage;
    }

    public void setUsernamemessage(String usernamemessage) {
        this.usernamemessage = usernamemessage;
    }

    public String getUsernamestyle() {
        return usernamestyle;
    }

    public void setUsernamestyle(String usernamestyle) {
        this.usernamestyle = usernamestyle;
    }

}
