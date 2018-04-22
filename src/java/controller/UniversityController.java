package controller;

import dao.UniversityDAO;
import javax.faces.bean.ManagedBean;
import model.University;

@ManagedBean
public class UniversityController {

    private String universityInput;
    private University university;

    public String getUniversity() {
        return universityInput;
    }

    public void setUniversity(String university) {
        this.universityInput = university;
    }

    public String showUniversity() {
        UniversityDAO dao = new UniversityDAO();
        university = dao.getUniversity(0);
        return "university.xhtml";
    }

    public String getName() {
        return university.getName();
    }

    public String getDescription() {
        return university.getDescription();
    }

}
