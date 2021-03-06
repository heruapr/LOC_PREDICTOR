package loc_predictor;

import controller.calculate_LOC;
import controller.fileBrowser;
import controller.matrixController;
import controller.xmlReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import model.ResultLOC;
import model.ResultsMetrics;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author HP PC
 */
public class FXMLDocumentController implements Initializable {

    xmlReader reader = new xmlReader();
    calculate_LOC cal = new calculate_LOC();
    ResultLOC resultLOC = new ResultLOC();
    ResultsMetrics result = new ResultsMetrics();
    matrixController matrix = new matrixController();

    @FXML
    private Label label;
    @FXML
    private Button btn_upload;
    @FXML
    private Button btn_calc;
    @FXML
    private Label L_file;
    @FXML
    private Label L_est;
    @FXML
    private Label L_class;
    @FXML
    private Label L_methods;
    @FXML
    private Label L_set;
    @FXML
    private Label L_attr;
    @FXML
    private Label L_cons;
    @FXML
    private Label L_get;
    @FXML
    private Label L_gen;
    @FXML
    private Label L_assoc;

    String dir;
    @FXML
    private Label L_highest_class;
    @FXML
    private Label L_roc;
    @FXML
    private Label L_RIC;
    @FXML
    private Label L_file1;
    private VBox my_viewbox;
    @FXML
    private ListView<String> my_listview;
    @FXML
    private VBox my_vbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void btn_upload(ActionEvent event) throws ParserConfigurationException, IOException, SAXException, IllegalArgumentException {
        fileBrowser path = new fileBrowser();
        L_file.setText(path.getFileName());
    }

    @FXML
    private void btn_calc(ActionEvent event) throws ParserConfigurationException, IOException, SAXException {
        
        
        reader.JMethods();
        reader.Nconstructor();
        reader.Nasso();
        reader.Ngener();
        reader.avgAttributes();
        reader.sumClasses();
        reader.Nmethods();
        matrix.makeMatrix();
        

        int range = 2;
        int indexClass=0;
        double temp = Math.pow(10, range);

        int classes = result.getClasses();
        double attr = Math.round(temp * result.getAttr()) / temp;
        int assoc = (int) ((int) Math.round(temp * result.getAssoc()) / temp);
        int gener = (int) ((int) Math.round(temp * result.getGener()) / temp);
        double methods = Math.round(temp * result.getMethods()) / temp;
        double set = Math.round(temp * result.getSet()) / temp;
        double get = Math.round(temp * result.getGet()) / temp;
        double cons = Math.round(temp * result.getCons()) / temp;

        String sClass = Integer.toString(classes);
        String Sattr = Double.toString(attr);
        String asso = Integer.toString(assoc);
        String gen = Integer.toString(gener);
        String method = Double.toString(methods);
        String Sset = Double.toString(set);
        String Sget = Double.toString(get);
        String Scons = Double.toString(cons);
       
        L_class.setText(sClass);
        L_attr.setText(Sattr);
        L_assoc.setText(asso);
        L_gen.setText(gen);
        L_methods.setText(method);
        L_set.setText(Sset);
        L_get.setText(Sget);
        L_cons.setText(Scons);
        
        L_highest_class.setText(matrix.highestMaintain);
        L_roc.setText(Integer.toString(matrix.roc));
        L_RIC.setText(Integer.toString(matrix.ric));
        
        //vbox list
          my_listview.getItems().addAll(matrix.items);
        //}
          my_vbox.getChildren().addAll(my_listview);
       
        
        //estimasi loc ///
        cal.calculate();
        L_est.setText(Integer.toString(resultLOC.getEst()));
        System.out.println(resultLOC.getEst());
        
//        matrixController mat = new matrixController();
//        mat.makeMatrix();

    }
}
