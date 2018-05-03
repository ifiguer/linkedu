package model;

import controller.AuthenticationBean;
import dao.FeedDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import utility.EncryptPass;

@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    @Inject
    private FeedDAO feedDAO;

    private List<Post> posts;

    private String username;
    private String usernamemessage;
    private String fullname;
    private String firstname;
    private String lastname;
    private String password;
    private String confirmPassword;
    private String email;
    private String following;
    private String universityOfChoice;
    private String majorOfChoice;
    private String gradDetails;
    private String highSchoolDetails;
    private String[] interests;
    private String profileURL;
    private int actScore;
    private int satScore;
    private String postContent;
    private String usernamestyle;
    private int loginAttempts = 0;
    private boolean loginSuccess = false;
    private boolean administrator = false;

    public LoginBean() {
        posts = new ArrayList<>();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String update() {
        if (!password.equals(confirmPassword)) {
            addFlashAttribute("updateError", "Passwords do not match");
            return "update";
        } else if (LoginBeanDA.updateCustomerToDB(this) != 1) {
            addFlashAttribute("updateError", "An error has occurred while updating your profile. Please check input and try again");
            return "update";
        }
        return "profile";
    }

    private void addFlashAttribute(String key, String value) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .put(key, value);
    }

    public String login() {
        if (validateLogin()) {
            loginSuccess = true;
            authenticationBean.setName(username);
            authenticationBean.login();
            return "profile";
        } else {
            loginSuccess = false;
            return "";
        }

    }

    public String logOut() {
        loginSuccess = false;
        authenticationBean.logout();

        username = "";
        firstname = "";
        lastname = "";
        actScore = 0;
        satScore = 0;
        email = "";
        this.password = "";
        this.confirmPassword = "";
        this.fullname = "";
        this.universityOfChoice = "";
        this.following = "";
        this.highSchoolDetails = "";
        this.majorOfChoice = "";
        this.gradDetails = "";
        
        return "welcome";
    }

    public String persist() {
        if (!validateSignUpForm()) {
            return "signUp";
        } else {
            if (LoginBeanDA.storeCustomerToDB(this) != 1) {
                addFlashAttribute("signUpError", "There was a problem creating your account. Please try again later.");
                return "signUp";
            } else {
                sendMail();
                setProfileURL("img/egg.jpg");
                loginSuccess = true;
                authenticationBean.setName(username);
                authenticationBean.login();
                return "profile";
            }

        }

    }

    public String addPost() {

        if (feedDAO.addPostToDB(username, postContent) != 1) {
            addFlashAttribute("addPostError", "There was a problem creating your post. Please try again later.");
            return "profile";
        } else {

            Date now = new Date();

            Post temp = new Post(username, postContent, profileURL, now.getTime());
            posts.add(temp);
            Collections.sort(posts);
            postContent = "";
            return "profile";
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
            @Override
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
                    + "<br/>Full Name: " + getFirstname() + " " + getLastname();

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
            addFlashAttribute("logInError", "You have unsuccessfully logged in too many times. Please try again later.");
            return false;
        }

        if ((temp = LoginBeanDA.validInfo(username, password)) == null) {
            loginAttempts++;
            addFlashAttribute("logInError", "Invalid username or password");
            return false;
        }

        fullname = temp.getFirstname() + " " + temp.getLastname();
        posts = feedDAO.getFeedsByUsername(username);
        setEmail(temp.getEmail());
        setFirstname(temp.getFirstname());
        setLastname(temp.getLastname());
        setActScore(temp.getActScore());
        setProfileURL(temp.getProfileURL());
        setSatScore(temp.getSatScore());
        setFollowing(temp.getFollowing());
        setHighSchoolDetails(temp.getHighSchoolDetails());
        setGradDetails(temp.getGradDetails());
        setAdministrator(temp.isAdministrator());
        loginSuccess = true;

        return true;
    }

    private boolean validateSignUpForm() {
        if (!checkNames()) {
            return false;
        } else if (LoginBeanDA.usernameTaken(username)) {
            addFlashAttribute("signUpError", "Username is already taken");
            return false;
        } else if (!password.equals(confirmPassword)) {
            addFlashAttribute("signUpError", "Passwords do not match");
            return false;
        }

        return true;
    }

    public void validateUserNameOnKey() {
        if (LoginBeanDA.usernameTaken(getUsername())) {
            usernamemessage = "Username is already taken";
            usernamestyle = "bad";
        } else {
            usernamemessage = "Username is valid!";
            usernamestyle = "good";
        }
    }

    private boolean checkNames() {
        String fullname2 = firstname + " " + lastname;
        if (fullname2.length() < 6 || fullname2.length() > 25) {
            addFlashAttribute("signUpError", "Fullname length must be between 6 and 25 characters");
            return false;
        }
        return true;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getUniversityOfChoice() {
        return universityOfChoice;
    }

    public void setUniversityOfChoice(String universityOfChoice) {
        this.universityOfChoice = universityOfChoice;
    }

    public String getMajorOfChoice() {
        return majorOfChoice;
    }

    public void setMajorOfChoice(String majorOfChoice) {
        this.majorOfChoice = majorOfChoice;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public int getActScore() {
        return actScore;
    }

    public void setActScore(int actScore) {
        this.actScore = actScore;
    }

    public int getSatScore() {
        return satScore;
    }

    public void setSatScore(int satScore) {
        this.satScore = satScore;
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

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
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

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = EncryptPass.hashPassword(confirmPassword);
    }

    public String getConfirmPassword() {
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

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getGradDetails() {
        return gradDetails;
    }

    public void setGradDetails(String gradDetails) {
        this.gradDetails = gradDetails;
    }

    public String getHighSchoolDetails() {
        return highSchoolDetails;
    }

    public void setHighSchoolDetails(String highSchoolDetails) {
        this.highSchoolDetails = highSchoolDetails;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

}
