package Controller;

import Equipment.Gui;
import Equipment.Meter;
import Equipment.MeterArchive;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ClientController {

    @FXML private ListView<String> listView;
    private static ObservableList<String> items;
    @FXML private Text selectedMeter;
    @FXML private TextField regNr;
    private Gui g;
    private String selectedItem;
    private static Meter meter;


    /**
     * Load meterList at startup
     */
    @FXML
    public void initialize() {
        g = new Gui();
        items = getItems();
        listView.setItems(items);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Only set the text if a value is selected
            if(listView.getSelectionModel().getSelectedItem() != null){
                selectedMeter.setText(MeterArchive.getMeter(newValue).toString());
                selectedItem = newValue;
                meter = MeterArchive.getMeter(newValue);
            } else {
                selectedMeter.setText("");
            }
        });
    }

    /**
     * Change scene to Controller.AddController so the user can add a new meter
     */
    public void addMeter() throws Exception {
        g.setCenterScene(g.getAddScene());
    }

    /**
     * Change scene to Edit so the user can edit selected meter
     */
    public void editMeter() throws Exception {
        if(listView.getSelectionModel().getSelectedItem() != null){
            g.setPrimaryStageTitle("Edit " + MeterArchive.getMeter(selectedItem).getClass().getSimpleName());
            g.setCenterScene(g.createEditScene());
        }
    }

    /**
     * Delete selected meter.
     */
    public void deleteMeter(){
        if(listView.getSelectionModel().getSelectedItem() != null){
            MeterArchive.removeMeter(selectedItem);
        } else {
            g.showAlert("No meter selected", "Please select a meter to delete", false);
        }
        reloadList();
    }

    @FXML
    private void deleteMeterWithReg(){
        if(MeterArchive.removeMeter(regNr.getText())){
            g.showAlert("Success", "Meter with reg number: " + regNr.getText() + " \nwas successfully deleted.", false);
        } else {
            g.showAlert("No meter found", "Found no meter with reg number: " + regNr.getText(), false);
        }
        regNr.setText("");
        reloadList();
    }

    public static Meter getSelectedMeter(){
        return meter;
    }

    /**
     * Get an array of reg numbers from the array of meters
     * The stream finds the regNr field in all meters and puts them in a new array
     * @return List of type string with all reg numbers
     */
    public List<String> getRegNrList() {
        return MeterArchive.getMeterList().stream()
                .map(Meter::getPrefixRegNr).collect(Collectors.toList());
    }

    /**
     * Put the the reg number list in an observable list to be able to display it in the ListView
     * @return
     */
    private ObservableList<String> getItems(){
        return FXCollections.observableArrayList(getRegNrList());
    }

    public void reloadList(){
        listView.setItems(getItems());
    }

    @FXML
    private void printList(){
        Stage stage = new Stage();
        StackPane pane = new StackPane();
        TextArea list = new TextArea();
        list.setText(MeterArchive.printMeterList());
        pane.getChildren().add(list);
        Scene scene = new Scene(pane, 400, 600);
        list.setPrefHeight(scene.getHeight());
        list.setPrefWidth(scene.getWidth());
        list.setEditable(false);
        stage.setScene(scene);
        stage.show();
    }
}
