package beans;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Feed {
    
    private ArrayList<String> posts;

    public Feed() {
    }

    public ArrayList<String> getPosts() {
        return posts;
    }

    public void addToList(String info){
        posts.add(info);
        
        
        
    }
    public void setPosts(ArrayList<String> posts) {
        this.posts = posts;
    }
    
}
