package Controller;

import Equipment.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuBarController {

    private Gui g;
    private Gson gson;

    @FXML
    public void initialize() throws IOException {
        g = new Gui();
    }

    @FXML
    private void showAbout(){
        g.showAlert("About", "Equipment Manager\n" + "Author: Kjell-Olaf Slagnes\n" + "Version: 1.0\n", false);
    }

    @FXML
    private void createNewList(){
        g.showAlert("Create new list", "Unsaved actions will not be saved.\nContinue?", true);
        if(g.isBool()){
            MeterArchive.clearMeterList();
            g.getClientController().reloadList();
        }
    }

    @FXML
    public void openList() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().add(ext);
        File file = chooser.showOpenDialog(g.getPrimaryStage());
        if(file != null){
            openFile(file);
        }
    }

    @FXML
    private void openSaveDialog(){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.setTitle("Choose location");
        chooser.getExtensionFilters().add(ext);
        File file = chooser.showSaveDialog(g.getPrimaryStage());
        if(file != null){
            if(!file.getAbsolutePath().endsWith(".json")){
                saveList(file.getAbsolutePath() + ".json");
            } else {
                saveList(file.getAbsolutePath());
            }
        }
    }

    /**
     * Write the meter list to file
     * @param path
     */
    private void saveList(String path) {
        try {
            PrintWriter out = new PrintWriter(path);
            Gson gson = new Gson();
            String json = gson.toJson(MeterArchive.getMeterList());
            out.println(json);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the list of meters from file
     * @param file
     */
    private void openFile(File file){
        try {
            MeterArchive.clearMeterList();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder sb = new StringBuilder();
            JsonParser jsonParser = new JsonParser();
            Gson gson = new Gson();
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            JsonArray met = jsonParser.parse(sb.toString()).getAsJsonArray();
            final Meter[] newMeter = new Meter[1];
            met.forEach(e -> {
                JsonObject meterObj = e.getAsJsonObject();
                    switch (meterObj.get("prefixRegNr").toString().substring(1, 3)){
                    case "TH":
                        newMeter[0] = gson.fromJson(e, Thermometer.class);
                        break;
                    case "CL":
                        newMeter[0] = gson.fromJson(e, Clock.class);
                        break;
                    case "WE":
                        newMeter[0] = gson.fromJson(e, Weight.class);
                        break;
                }
                MeterArchive.addMeter(newMeter[0]);
            });
            g.getClientController().reloadList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitProgram() {
        g.showAlert("Exit", "Are you sure you want to exit?\nUnsaved data will be lost!", true);
        if(g.isBool()){
            g.getPrimaryStage().close();
        }
    }
}
