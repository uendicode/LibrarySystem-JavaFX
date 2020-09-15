/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfx;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hoxha
 */
public class ReturnBookController implements Initializable {

    private static final int MAX_COUNT = 3;

    @FXML
    private TextField btitleTxt;

    @FXML
    private TextField searchBookCode;

    @FXML
    private TextField bpriceTxt;

    @FXML
    private TextField bcategoryTxt;

    @FXML
    private TextField bpublisherTxt;

    @FXML
    private TextField beditionTxt;

    @FXML
    private Label msgLabel;

    @FXML
    private Button searchBookBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField searchStudentID;

    @FXML
    private Button searchStudentBtn;

    @FXML
    private TextField stNameTxt;

    @FXML
    private TextField stYearTxt;

    @FXML
    private TextField stMajorTxt;

    @FXML
    private TextField stDepartmentTxt;

    @FXML
    private TextField stEmailTxt;

    @FXML
    private TextField issueDateTxt;

    @FXML
    private Button returnBookBtn;

    @FXML
    private DatePicker returnDate;

    @FXML
    public void returnBook(ActionEvent event) throws IOException {
        returned();
        del();
        updateBook();
        reset();
    }

    public void reset()
    {
        searchBookCode.setDisable(false);
        searchStudentID.setDisable(false);
        searchBookCode.setText("");
        btitleTxt.setText("");
        bpriceTxt.setText("");
        
        bcategoryTxt.setText("");
        bpublisherTxt.setText("");
        beditionTxt.setText("");
        stNameTxt.setText("");
        stYearTxt.setText("");
        stMajorTxt.setText("");
        stEmailTxt.setText("");
        stDepartmentTxt.setText("");
        searchStudentID.setText("");
        issueDateTxt.setText("");
        returnDate.setValue(null);
    }
    
    public void updateBook() {
        ResultSet rs;
        PreparedStatement pst;
        Connectivity connect = new Connectivity();
        String sql1 = "insert into books (booktitle,bookid,price,categ,publishers,edition) values (?,?,?,?,?,?)";
        try {
            Connection conn = Connectivity.ConnectDb();
            pst = conn.prepareStatement(sql1);
            pst.setString(1, btitleTxt.getText());
            pst.setString(2, searchBookCode.getText());
            pst.setString(3, bpriceTxt.getText());
            pst.setString(4, bcategoryTxt.getText());
            pst.setString(5, bcategoryTxt.getText());
            pst.setString(6, bpublisherTxt.getText());
            pst.execute();

            msgLabel.setText("Book returned succesfully");
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void del() {
        ResultSet rs;
        PreparedStatement pst;
        Connectivity connect = new Connectivity();
        try {
            Connection conn = Connectivity.ConnectDb();
            String bId = searchBookCode.getText();
            String sql = "DELETE FROM issued where bookid = '" + bId + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void decrementBook() {
        ResultSet rs;
        PreparedStatement pst3;
        Connectivity connect = new Connectivity();

        String reg = searchStudentID.getText();
        String sql4 = "select *from students where regno='" + reg + "'";
        String sql3 = "update students set noIssued = ? where regno='" + reg + "' ";

        try {
            Connection conn = Connectivity.ConnectDb();
            pst3 = conn.prepareStatement(sql4);
            rs = pst3.executeQuery();
            rs.next();
            int count = rs.getInt(8);
            if (count >= 0 && count <= MAX_COUNT) {
                int counter = count;
                counter--;
                PreparedStatement prepstm = conn.prepareStatement(sql3);
                prepstm.setInt(1, counter);
                prepstm.executeUpdate();
                pst3.close();
                rs.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void returned() {
        ResultSet rs;
        PreparedStatement pst;
        Connectivity connect = new Connectivity();
        String sql2 = "insert into returned (booktitle,bookid,price,category,publishers,edition,regno,name,yearOfStudy,faculty,department,email,dateIssued,dateReturned) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = Connectivity.ConnectDb();
            pst = conn.prepareStatement(sql2);
            pst.setString(1, btitleTxt.getText());
            pst.setString(2, searchBookCode.getText());
            pst.setString(3, bpriceTxt.getText());
            pst.setString(4, bcategoryTxt.getText());
            pst.setString(5, bpublisherTxt.getText());
            pst.setString(6, beditionTxt.getText());
            pst.setString(7, searchStudentID.getText());
            pst.setString(8, stNameTxt.getText());
            pst.setString(9, stYearTxt.getText());
            pst.setString(10, stMajorTxt.getText());
            pst.setString(11, stDepartmentTxt.getText());
            pst.setString(12, stEmailTxt.getText());
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            String date = ft.format(dNow);
            // String date = sdf.format(jDateChooser1.getDate());
            pst.setString(13, issueDateTxt.getText());
            pst.setString(14, date);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Book returned succesfully");
            decrementBook();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void search() {
        ResultSet rs;
        PreparedStatement pst;
        Connectivity connect = new Connectivity();

        String id = searchBookCode.getText();
        String sql = "select *from issued where bookId = '" + id + "' ";

        try {
            Connection conn = Connectivity.ConnectDb();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                btitleTxt.setText(rs.getString(9));
                bpriceTxt.setText(rs.getString(10));
                bcategoryTxt.setText(rs.getString(11));
                bpublisherTxt.setText(rs.getString(12));
                beditionTxt.setText(rs.getString(13));
                searchStudentID.setText(rs.getString(2));
                stNameTxt.setText(rs.getString(1));
                stYearTxt.setText(rs.getString(3));
                stMajorTxt.setText(rs.getString(4));
                stDepartmentTxt.setText(rs.getString(5));
                stEmailTxt.setText(rs.getString(6));
                issueDateTxt.setText(rs.getString(7));
                pst.close();
                rs.close();
            } else {
                JOptionPane.showMessageDialog(null, "Wrong book Number");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

     @FXML
    public void backto(ActionEvent event) throws IOException {
        Parent view3 = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene scene3 = new Scene(view3);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
