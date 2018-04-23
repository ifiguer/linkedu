package beans;

import dao.FeedDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.LoginBean;

@ManagedBean
@RequestScoped
public class Feed {

    private String newPost;
    private ArrayList<String> posts;

    public Feed() {
        posts = new ArrayList<String>();
    }
    
    public String addPost() {
        FeedDAO dao = new FeedDAO();
        dao.addPostToDB("test", newPost);
        return "landing.xhtml";
    }

    public String getNewPost() {
        return newPost;
    }

    public void setNewPost(String newPost) {
        this.newPost = newPost;
    }

    public ArrayList<String> getPosts() {
        return posts;
    }

    public void addToList(String info) {
        posts.add(info);
    }

    public void setPosts(ArrayList<String> posts) {
        this.posts = posts;
    }

}
