package dao;

import model.University;

public class UniversityDAO {
    public University getUniversity(int id) {
        return new University("Illinois State Unviersity", "A description goes here.");
    }
}
