package model;

public class User {
    private String name;
    private String profileURL;
    private String highSchoolName;
    private String gradDetails;

    public User() {
    }

    public User(String name, String profileURL, String highSchoolName, String gradDetails) {
        this.name = name;
        this.profileURL = profileURL;
        this.highSchoolName = highSchoolName;
        this.gradDetails = gradDetails;
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
    
    
    
}
