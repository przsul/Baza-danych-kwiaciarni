/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raport_zamowienia;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author przem
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Button button1;
    @FXML private TextField miasto_TextField;
    @FXML private DatePicker od_DatePicker;
    @FXML private DatePicker do_DatePicker;
    
    Connection con;
    String url = "jdbc:mysql://localhost/Kwiaciarnia";
    String user = "root";
    String pass = "projektbazy";
    
    JasperReport jasperReport;
    JasperPrint jasperPrint;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws ClassNotFoundException {
        if(event.getSource() == button1) {
            try {
                Class.forName("com.mysql.jdbc.Driver"); 
                con = DriverManager.getConnection(url, user, pass);
                JasperDesign jd = JRXmlLoader.load("C:\\Users\\przem\\JaspersoftWorkspace\\MyReports\\raport3.jrxml");
                JasperReport jr = JasperCompileManager.compileReport(jd);
                HashMap p = new HashMap();
                p.put("MIASTO", miasto_TextField.getText());
                p.put("DATA_OD", od_DatePicker.getValue());
                p.put("DATA_DO", do_DatePicker.getValue());
                JasperPrint jp = JasperFillManager.fillReport(jr, p, con);
                JasperViewer viewer = new JasperViewer(jp, false);
                viewer.setTitle("Kwiaciarnia - Produkty");
                viewer.setVisible(true);
            } catch(SQLException ex) {
                if(ex.getErrorCode() == 1045)
                    System.out.println("błąd SQL - " + "Błąd logowania");
                else
                    System.out.println("błąd SQL - " + ex);
            } catch(JRException ex) {
                System.out.println(ex);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
