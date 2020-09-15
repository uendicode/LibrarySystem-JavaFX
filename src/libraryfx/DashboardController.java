/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoxha
 */
public class DashboardController implements Initializable {

     @FXML
    private Button logoutBtn;

    @FXML
    private Button addBookBtn;

    @FXML
    private Button addStudentBtn;

    @FXML
    private Button issueBookBtn;

    @FXML
    private Button returnBookBtn;

    @FXML
    private Button statisticsBtn;

    
     @FXML
    void backLogin(ActionEvent event) throws IOException {
       Parent view6=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene6=new Scene(view6);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene6);
                window.show();
     
    }
    
    @FXML
    void addBook(ActionEvent event) throws IOException {
       Parent view6=FXMLLoader.load(getClass().getResource("AddBook.fxml"));
                Scene scene6=new Scene(view6);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene6);
                window.show();
     
    }
    
     @FXML
    void addStudent(ActionEvent event) throws IOException {
       Parent view6=FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
                Scene scene6=new Scene(view6);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene6);
                window.show();
     
    }
    
     @FXML
    void issueBook(ActionEvent event) throws IOException {
       Parent view6=FXMLLoader.load(getClass().getResource("IssueBook.fxml"));
                Scene scene6=new Scene(view6);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene6);
                window.show();
     
    }
    
     @FXML
    void returnBook(ActionEvent event) throws IOException {
       Parent view6=FXMLLoader.load(getClass().getResource("ReturnBook.fxml"));
                Scene scene6=new Scene(view6);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene6);
                window.show();
     
    }
    
     @FXML
    void checkStatistics(ActionEvent event) throws IOException {
       Parent view6=FXMLLoader.load(getClass().getResource("BookStatistics.fxml"));
                Scene scene6=new Scene(view6);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene6);
                window.show();
     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
