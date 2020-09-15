/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.util.Date;

/**
 *
 * @author hoxha
 */
public class ReturnedBooks {
    
    private String name, faculty, department, booktitle;
    private int regno, yearOfStudy, bookId;
    private Date dateReturned;

    public ReturnedBooks(String name, String faculty, String department, String booktitle, int regno, int yearOfStudy, int bookId, Date dateReturned) {
        this.name = name;
        this.faculty = faculty;
        this.department = department;
        this.booktitle = booktitle;
        this.regno = regno;
        this.yearOfStudy = yearOfStudy;
        this.bookId = bookId;
        this.dateReturned = dateReturned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public int getRegno() {
        return regno;
    }

    public void setRegno(int regno) {
        this.regno = regno;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    

}
