/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.main;

import com.boris.vue.FinPartie;
import com.boris.vue.IHM;
import com.boris.vue.Menu;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author boris
 */
public class BatailleNavale extends Application {
    
    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

        Group root = new IHM();
        Menu menu = new Menu();
        
        root.getChildren().add(menu);
        Scene scene = new Scene(root, 500, 900);
        
        primaryStage.setTitle("Bataille Navale");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
