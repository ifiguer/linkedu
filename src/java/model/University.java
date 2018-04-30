package model;

public class University implements Comparable<University>{
    private String name;
    private String description;
    private boolean featured;
    
    public University() {
    }
    
    public University(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
    @Override
    public int compareTo(University other){
        if (this.name.equals(other.name)){
            return 0;
        }
        else if (this.name.compareTo(other.name) > 0 ){
            return 1;
        }
        else{
            return -1;
        }
    }
}
