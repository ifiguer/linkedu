package model;

public class User {
    private String name;
    private String profileURL;
    private String highSchoolName;
    private String gradDetails;
    private String userID;
    public User() {
    }

    public User(String name, String profileURL,String userID, String highSchoolName, String gradDetails) {
        this.name = name;
        this.profileURL = profileURL;
        this.highSchoolName = highSchoolName;
        this.gradDetails = gradDetails;
        this.userID = userID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getHighSchoolName() {
        return highSchoolName;
    }

    public void setHighSchoolName(String highSchoolName) {
        this.highSchoolName = highSchoolName;
    }

    public String getGradDetails() {
        return gradDetails;
    }

    public void setGradDetails(String gradDetails) {
        this.gradDetails = gradDetails;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    
    
}
