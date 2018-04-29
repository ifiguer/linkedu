/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import model.LoginBean;
import model.LoginBeanDA;
import model.User;

/**
 *
 * @author it353s836
 */
@ManagedBean
@SessionScoped
public class UserController implements Serializable {
    @Inject
    private UserDAO userDAO;
    @ManagedProperty(value="#{LoginBean}")
    private LoginBean temp;
    private ArrayList<User> searchStudentResults;
    private String errorResponse;
    private String studentSearch;
    private String studentFollowID;
    
    public String searchForStudents(){
        if((searchStudentResults = userDAO.checkDBForStudents(studentSearch))==null){
            return "welcome";
        }
        else{
            return "studentSearchResults";
        }
        
    }
      public void followUser(){
        System.out.println("Trying");
        if(userDAO.addUserToFollowingList(temp,studentFollowID)==1){
            errorResponse="Successfully followed user";
        }
        else{
            errorResponse="An error occurred following user";
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

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getStudentFollowID() {
        return studentFollowID;
    }

    public void setStudentFollowID(String studentFollowID) {
        this.studentFollowID = studentFollowID;
    }

    public LoginBean getTemp() {
        return temp;
    }

    public void setTemp(LoginBean temp) {
        this.temp = temp;
    }
    
    
}
