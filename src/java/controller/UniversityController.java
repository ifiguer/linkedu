package controller;

import dao.UniversityDAO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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

    public String getUniversity() {
        return universityInput;
    }

    public void setUniversity(String university) {
        this.universityInput = university;
    }

    public String showUniversity() {
        university = universityDAO.getUniversityByName(universityInput);
        if(university == null) {
            return "landing.xhtml";
        }
        return "university.xhtml";
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

}
