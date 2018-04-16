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

import java.util.ArrayList;

public class EditController {

    @FXML private Text meterText, minVal, maxVal, shelfCodeMessage;
    @FXML private TextField fieldMinVal, fieldMaxVal;
    @FXML private CheckBox checkBox;
    @FXML private ComboBox roomBox, shelfBox, placeBox;
    private boolean isFunctioning, shelfCodeInUse;
    private Meter meter;
    private Gui g;
    private double minValue, maxValue;
    private String shelfCode;

    /**
     * Setup the edit scene with the appropriate values
     */
    @FXML
    public void initialize(){
        g = new Gui();
        meter = ClientController.getSelectedMeter();
        setMeterText(meter.toString());
        fieldMinVal.addEventFilter(KeyEvent.KEY_TYPED, event -> EventHandler.eventHandler(event, fieldMinVal));
        fieldMaxVal.addEventFilter(KeyEvent.KEY_TYPED, event -> EventHandler.eventHandler(event, fieldMaxVal));
        minValue = meter.getMinValue();
        maxValue = meter.getMaxValue();
        if(meter.getPrefixRegNr().substring(0,2).equals("CL")){
            fieldMaxVal.setVisible(false);
            maxVal.setVisible(false);
        }
        setMinVal("Minimum " + meter.getValueType() + ":");
        setMaxVal("Maximum " + meter.getValueType() + ":");
        setFieldMinVal(Double.toString(minValue));
        setFieldMaxVal(Double.toString(maxValue));
        fillComboBox(roomBox, "R");
        fillComboBox(shelfBox, "S");
        fillComboBox(placeBox, "P");
        setCurrentCode(roomBox, Integer.parseInt(meter.getShelfCode().substring(1, 3)));
        setCurrentCode(shelfBox, Integer.parseInt(meter.getShelfCode().substring(4, 6)));
        setCurrentCode(placeBox, Integer.parseInt(meter.getShelfCode().substring(7)));
        shelfCodeMessage.setVisible(false);
        setShelfCode();
        checkBox.selectedProperty().set(meter.getIsFunctioning());
        roomBox.setOnAction(e -> {
            setShelfCode();
            shelfCodeInUse = EventHandler.eventHandler(shelfCode, shelfCodeMessage, meter);
        });
        shelfBox.setOnAction(e -> {
            setShelfCode();
            shelfCodeInUse = EventHandler.eventHandler(shelfCode, shelfCodeMessage, meter);
        });
        placeBox.setOnAction(e -> {
            setShelfCode();
            shelfCodeInUse = EventHandler.eventHandler(shelfCode, shelfCodeMessage, meter);
        });
    }

    /**
     * Save edited fields to the object
     * @throws Exception
     */
    @FXML
    private void saveMeter() {
        if(!shelfCodeInUse){
            if(!fieldMinVal.getText().equals("")) minValue = Double.parseDouble(fieldMinVal.getText());
            if(!fieldMaxVal.getText().equals("")) maxValue = Double.parseDouble(fieldMaxVal.getText());
            if(minValue < maxValue){
                isFunctioning = checkBox.selectedProperty().getValue();
                meter.saveData(minValue, maxValue, isFunctioning, shelfCode);
                g.setCenterScene(g.getClientScene());
                g.getClientController().reloadList();
            } else {
                g.showAlert("Incorrect values", "Minimum value can't be greater than maximum value", false);
            }
        } else {
            g.showAlert("Can't place meter", "There is a meter at that location already, choose a different location.", false);
        }
    }

    private void setShelfCode(){
        shelfCode = roomBox.getSelectionModel().getSelectedItem().toString() +
                shelfBox.getSelectionModel().getSelectedItem().toString() +
                placeBox.getSelectionModel().getSelectedItem().toString();
    }

    private void fillComboBox(ComboBox box, String code){
        ObservableList<String> list = FXCollections.observableArrayList(
                getNumberList(code)
        );
        box.setItems(list);
    }

    private ArrayList<String> getNumberList(String code){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            list.add(code + String.format("%02d", i));
        }
        return list;
    }

    private void setCurrentCode(ComboBox box, int index){
        box.getSelectionModel().select(index);
    }

    @FXML
    private void setSceneToClient() {
        g.getPrimaryStage().setTitle("Equipment Manager");
        g.setCenterScene(g.getClientScene());
    }

    private void setMeterText(String text){
        meterText.setText(text);
    }

    private void setMinVal(String text){
        minVal.setText(text);
    }

    private void setMaxVal(String text){
        maxVal.setText(text);
    }

    private void setFieldMinVal(String text){
        fieldMinVal.promptTextProperty().set(text);
    }

    private void setFieldMaxVal(String text){
        fieldMaxVal.promptTextProperty().set(text);
    }
}
