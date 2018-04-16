package Controller;

import Equipment.Code;
import Equipment.Meter;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class EventHandler {

    /**
     * Limit the user to only input numbers, and only one period
     * REFERANSE https://stackoverflow.com/questions/15615890/recommended-way-to-restrict-input-in-javafx-textfield
     * @param event
     */
    public static void eventHandler(KeyEvent event, TextField field){
        if(!"0123456789.-".contains(event.getCharacter())){
            event.consume();
        }
        if(field.getText().contains(event.getCharacter()) && event.getCharacter().equals(".")){
            event.consume();
        }
        if((field.getText().startsWith("-") && event.getCharacter().equals("-"))){
            event.consume();
        }
        if(field.getText().length() > 1){
            if(field.getText().substring(0, 1).matches("[\\d.]") && event.getCharacter().equals("-")){
                event.consume();
            }
        }
    }

    public static boolean eventHandler(String shelfCode, Text shelfCodeMessage, Meter meter){
        if(Code.isShelfCodeInUse(shelfCode) && !meter.getShelfCode().equals(shelfCode)){
            shelfCodeMessage.setVisible(true);
            return true;
        } else {
            shelfCodeMessage.setVisible(false);
            return false;
        }
    }
}
