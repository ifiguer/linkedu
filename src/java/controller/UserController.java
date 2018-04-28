/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import model.User;

/**
 *
 * @author it353s836
 */
@ManagedBean
@SessionScoped
public class UserController {
    @Inject
    private UserDAO userDAO;
    private ArrayList<User> searchStudentResults;
    private String studentSearch;
    
    public String searchForStudents(){
        if((searchStudentResults = userDAO.checkDBForStudents(studentSearch))==null){
            return "landing.xhtml";
        }
        else{
            return "studentSearchResults.xhtml";
        }
        
    }

    public ArrayList<User> getSearchStudentResults() {
        return searchStudentResults;
    }

    public void setSearchStudentResults(ArrayList<User> searchStudentResults) {
        this.searchStudentResults = searchStudentResults;
    }

    public String getStudentSearch() {
        return studentSearch;
    }

    public void setStudentSearch(String studentSearch) {
        this.studentSearch = studentSearch;
    }
    
    
    
    
    
    
}
