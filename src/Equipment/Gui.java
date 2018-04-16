package Equipment;

import Controller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {
    private static Stage primaryStage;
    private static BorderPane borderPane;
    private static Scene clientScene;
    private static ClientController clientController;
    private boolean bool = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // Set the title for the frame
        primaryStage.setTitle("Equipment Manager");
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            showAlert("Exit", "Are you sure you want to exit?\nUnsaved data will be lost!", true);
            if(isBool()){
                primaryStage.close();
            }
        });

        // Load Client scene
        setClientScene();

        // Create a borderpane. Center is for main content and top is always a menu bar
        borderPane = new BorderPane();
        borderPane.setTop(getMenuBarScene().getRoot());
        borderPane.setCenter(getClientScene().getRoot());

        // Set start scene to client layout
        primaryStage.setScene(new Scene(borderPane, 600, 500));
        primaryStage.show();
    }

    /**
     * Load a layout from FXML
     * @param path
     * @return
     * @throws Exception
     */
    public FXMLLoader getLayout(String path) {
        return new FXMLLoader(getClass().getResource(path));
    }

    /**
     * Set the scene in primary stage
     * @param scene
     */
    public void setCenterScene(Scene scene) {
        borderPane.setCenter(scene.getRoot());
    }

    /**
     * Get the menubar scene
     * @return
     * @throws Exception
     */
    public Scene getMenuBarScene() throws Exception {
        return new Scene(getLayout("/Scene/MenuBar.fxml").load(), 600, 500);
    }

    /**
     * Set the client scene
     * @return
     * @throws Exception
     */
    public void setClientScene() throws Exception {
        FXMLLoader clientFXML = getLayout("/Scene/Client.fxml");
        clientScene = new Scene(clientFXML.load(), 600, 500);
        clientController = clientFXML.getController();
    }

    /**
     * Get the client scene
     */
    public Scene getClientScene() {
        return clientScene;
    }

    /**
     * Get the add scene
     * @return
     */
    public Scene getAddScene() throws Exception {
        return new Scene(getLayout("/Scene/Add.fxml").load(), 600, 500);
    }

    /**
     * Get the edit scene
     * @return
     */
    public Scene createEditScene() throws Exception {
        return new Scene(getLayout("/Scene/Edit.fxml").load(), 600, 500);
    }

    public void setPrimaryStageTitle(String title){
        primaryStage.setTitle(title);
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public ClientController getClientController(){
        return clientController;
    }

    public boolean isBool() {
        return bool;
    }

    /**
     * Show an alert box if the user did something wrong.
     * The user have to close this box to continue.
     */
    public void showAlert(String title, String message, boolean showYesno){
        // Create a new stage
        Stage stage = new Stage();
        stage.setTitle(title);

        // Create a layout
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(10, 10, 10, 10));

        // Create a text area and add it to the layout
        Text alertMessage = new Text(message);
        pane.getChildren().add(alertMessage);

        // If showYesNo is true create yes and no buttons else create an ok button
        if(showYesno){
            Button yesButton = new Button("Yes");
            yesButton.setOnAction(e -> {
                bool = true;
                stage.close();
            });
            Button noButton = new Button("No");
            noButton.setOnAction(e -> stage.close());
            pane.getChildren().addAll(yesButton, noButton);
        } else {
            Button ok = new Button("OK");
            ok.setOnAction(e -> stage.close());
            pane.getChildren().add(ok);
        }

        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(10);
        pane.setHgap(20);
        pane.setAlignment(Pos.CENTER);
        // Create a scene with the StackPane layout
        Scene alert = new Scene(pane, alertMessage.getLayoutBounds().getWidth() + 20, 150);

        // Set the stage to use the newly created scene and show it.
        stage.setScene(alert);
        stage.showAndWait();
        stage.setAlwaysOnTop(true);
    }
}
