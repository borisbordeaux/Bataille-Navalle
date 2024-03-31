package com.boris.bataillenavale;

import com.boris.bataillenavale.vue.IHM;
import com.boris.bataillenavale.vue.Menu;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new IHM();
        Menu menu = new Menu();
        root.getChildren().add(menu);
        Scene scene = new Scene(root, 500, 900);

        stage.setTitle("Bataille Navale");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
