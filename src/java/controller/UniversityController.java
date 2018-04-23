package controller;

import dao.UniversityDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import model.University;

@ManagedBean
public class UniversityController {

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
        UniversityDAO dao = new UniversityDAO();
        university = dao.getUniversityByName(universityInput);
        return "university.xhtml";
    }

    public ArrayList<University> getFeaturedUniversities() {
    
        featured = UniversityDAO.getFeaturedUniversities();
        
        return featured;
        
    }
    
    public String getName() {
        return university.getName();
    }

    public String getDescription() {
        return university.getDescription();
    }

}
