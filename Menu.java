/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.vue;

import java.io.File;
import java.net.MalformedURLException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author boris
 */
public class Menu extends Parent {

    private Text text;
    private Timeline anim;
    private ImageView imageView;

    public Menu() throws MalformedURLException {
        
        File file = new File("images/menu.png");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);

        imageView = new ImageView(image);
        imageView.setFitHeight(900);
        imageView.setFitWidth(500);
        imageView.setX(0);
        imageView.setY(0);
        
        text = new Text(40, 600, "CLIQUER ICI POUR CONTINUER");
        text.setFont(new Font(30));
        
        this.getChildren().add(imageView);
        this.getChildren().add(text);
        
        anim = new Timeline();
        anim.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(500), new KeyValue(text.opacityProperty(), 0.0)),
                new KeyFrame(new Duration(500), new KeyValue(text.opacityProperty(), 1.0))
        );
        anim.setAutoReverse(true);
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.play();

        text.setOnMousePressed((MouseEvent e) -> {
            fin();
        });

    }

    public void fin() {
        anim.stop();
        text.setVisible(false);
        imageView.setVisible(false);
    }

}
