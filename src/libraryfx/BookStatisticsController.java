/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfx;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import statistics.IssuedBooks;

/**
 * FXML Controller class
 *
 * @author hoxha
 */
public class BookStatisticsController implements Initializable {

     @FXML
    private TableView<IssuedBooks> issuedBooksTable;

    @FXML
    private TableColumn<IssuedBooks, String> booktitle;

    @FXML
    private TableColumn<IssuedBooks, Integer> bookId;

    @FXML
    private TableColumn<IssuedBooks, Integer> price;

    @FXML
    private TableColumn<IssuedBooks, String> category;

    @FXML
    private TableColumn<IssuedBooks, String> publishers;

    @FXML
    private TableColumn<IssuedBooks, String> edition;
    
    ObservableList<IssuedBooks> issued = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Connection conn = Connectivity.ConnectDb();
        try {
            ResultSet rs = conn.createStatement().executeQuery("select booktitle,bookId,price,category,publishers,edition from issued");

            while (rs.next()) {
                issued.add(new IssuedBooks(rs.getString("booktitle"), rs.getString("category"), rs.getString("publishers"), rs.getString("edition"), rs.getInt("bookId"), rs.getInt("price")));
          
            }
            
        } 
        
        catch (SQLException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        booktitle.setCellValueFactory(new PropertyValueFactory<>("booktitle"));
        bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        publishers.setCellValueFactory(new PropertyValueFactory<>("publishers"));
        edition.setCellValueFactory(new PropertyValueFactory<>("edition"));

        issuedBooksTable.setItems(issued);
        
    }    
    @FXML
    void getback(ActionEvent event) throws IOException {
       Parent view6=FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                Scene scene6=new Scene(view6);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene6);
                window.show();
     
    }
    
     @FXML
    void checkReturned(ActionEvent event) throws IOException {
       Parent view6=FXMLLoader.load(getClass().getResource("ReturnedBooks.fxml"));
                Scene scene6=new Scene(view6);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene6);
                window.show();
     
    }
}
