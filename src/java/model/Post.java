/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ibran
 */
public class Post implements Comparable<Post>{

    private String content;
    private String userID;
    private long datePosted;
    private String profileURL;

    public Post(String userID, String content,String profileURL, long datePosted) {
        this.userID = userID;
        this.content = content;
        this.profileURL = profileURL;
        this.datePosted = datePosted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(long datePosted) {
        this.datePosted = datePosted;
    }
    @Override
    public int compareTo(Post other){
        if (this.datePosted == other.datePosted){
            return 0;
        }
        else if (this.datePosted < other.datePosted){
            return 1;
        }
        else{
            return -1;
        }
        
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }
    
}
