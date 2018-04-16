package Controller;

import Equipment.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;


public class AddController {

    @FXML private ComboBox comboBox;
    @FXML private TextField minValue, maxValue;
    @FXML private Text maxValueField, minValueField;
    @FXML private CheckBox isFunctioning;
    private Gui g;
    private Meter m;


    @FXML
    public void initialize(){
        minValue.setVisible(false);
        minValue.addEventFilter(KeyEvent.KEY_TYPED, event -> EventHandler.eventHandler(event, minValue));
        maxValue.setVisible(false);
        maxValue.addEventFilter(KeyEvent.KEY_TYPED, event -> EventHandler.eventHandler(event, maxValue));
        maxValueField.setVisible(false);
        minValueField.setVisible(false);
        g = new Gui();
        ObservableList list = FXCollections.observableArrayList("Thermometer", "Clock", "Weight");
        comboBox.setItems(list);
        comboBox.valueProperty().addListener(e -> {
            String minText = "", maxText = "";
            switch (comboBox.getSelectionModel().getSelectedItem().toString()){
                case "Thermometer":
                    minText = "Minimum temperature:";
                    maxText = "Maximum temperature:";
                    break;
                case "Clock":
                    minText = "Minimum interval:";
                    maxText = "";
                    maxValue.setVisible(false);
                    break;
                case "Weight":
                    minText = "Minimum weight:";
                    maxText = "Maximum weight:";
                    break;
            }
            minValueField.setText(minText);
            maxValueField.setText(maxText);
            minValue.setVisible(true);
            minValueField.setVisible(true);
            if(!comboBox.getSelectionModel().getSelectedItem().toString().equals("Clock")){
                maxValue.setVisible(true);
            }
            maxValueField.setVisible(true);
        });
    }

    @FXML
    private void addEquipment() {
        if(comboBox.getSelectionModel().getSelectedItem() != null){
            if(minValue.getText().equals("")){
                minValue.setText("1");
            }
            if(maxValue.getText().equals("")){
                maxValue.setText("10");
            }
            double minimumValue = Double.parseDouble(minValue.getText());
            double maximumValue = Double.parseDouble(maxValue.getText());
            if(comboBox.getSelectionModel().getSelectedItem().toString().equals("Clock")){
                createNewMeter();
            } else {
                if(minimumValue < maximumValue){
                    createNewMeter();
                } else {
                    g.showAlert("Incorrect values", "Minimum value can't be greater than maximum value", false);
                }
            }
        } else {
            g.showAlert("Select a meter", "You must define the type of meter to add", false);
        }
    }

    private void createNewMeter(){
        m = getNewMeter(
                comboBox.getSelectionModel().getSelectedItem().toString(),
                Double.parseDouble(minValue.getText()),
                Double.parseDouble(maxValue.getText()),
                isFunctioning.selectedProperty().getValue());
        MeterArchive.addMeter(m);
        g.setCenterScene(g.getClientScene());
        g.getClientController().reloadList();
    }

    @FXML
    private void setSceneToClient() {
        g.setCenterScene(g.getClientScene());
    }

    private Meter getNewMeter(String meter, double minValue, double maxValue, boolean isFunctioning){
        switch (meter){
            case "Thermometer":
                m = new Thermometer(minValue, maxValue, isFunctioning);
                break;
            case "Clock":
                m = new Clock(minValue, maxValue, isFunctioning);
                break;
            case "Weight":
                m = new Weight(minValue, maxValue, isFunctioning);
                break;
        }
        return m;
    }
}
