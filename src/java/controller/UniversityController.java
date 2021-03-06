package controller;

import dao.UniversityDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.University;

@ManagedBean
@SessionScoped
public class UniversityController implements Serializable {
    
    @Inject
    UniversityDAO universityDAO;
    
    private String universityInput;
    private ArrayList<University> featured;
    private University university;
    private List<University> searchUniversityResults;

    public String getUniversity() {
        return universityInput;
    }

    public void setUniversity(String university) {
        this.universityInput = university;
    }
    public void addToFeatured(){
        String u = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("university");
        if (universityDAO.addToFeatured(u) == 1){
            System.out.print("Added to featured");
        }
        else{
            System.out.print("Did not add to featured");
        }
    }
    public void removeFeatured(){
        String u = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("university");
        if (universityDAO.removeFeatured(u) == 1){
            System.out.print("Added to featured");
        }
        else{
            System.out.print("Did not add to featured");
        }
    }
    public String showUniversity() {
        searchUniversityResults = universityDAO.getUniversityByName(universityInput);
        if(searchUniversityResults.isEmpty()) {
            return "profile";
        }
        return "universitySearchResults.xhtml";
    }

    public ArrayList<University> getFeaturedUniversities() {
        featured = universityDAO.getFeaturedUniversities();
        return featured;
    }

    public String getName() {
        return university.getName();
    }

    public String getDescription() {
        return university.getDescription();
    }

    public List<University> getSearchUniversityResults() {
        return searchUniversityResults;
    }

    public void setSearchUniversityResults(List<University> searchUniversityResults) {
        this.searchUniversityResults = searchUniversityResults;
    }
    
}
