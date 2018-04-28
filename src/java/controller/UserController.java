/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.util.ArrayList;
import javax.inject.Inject;
import model.User;

/**
 *
 * @author it353s836
 */
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
    
    
    
    
}
