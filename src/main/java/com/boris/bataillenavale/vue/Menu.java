/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.vue;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Le menu pour demarrer une partie
 *
 * @author boris
 */
public class Menu extends Parent {

    private Text text;
    //aniamtion du texte qui clignotte
    private Timeline anim;
    //le fond
    private ImageView imageView;

    /**
     * le contructeur
     */
    public Menu() {
        //l'image
        imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/menu.png")));
        imageView.setFitHeight(900);
        imageView.setFitWidth(500);
        imageView.setX(0);
        imageView.setY(0);

        //le texte
        text = new Text(35, 600, "CLIQUER ICI POUR CONTINUER");
        text.setFont(new Font(28));

        this.getChildren().add(imageView);
        this.getChildren().add(text);

        //l'animation du texte
        anim = new Timeline();
        anim.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(500), new KeyValue(text.opacityProperty(), 0.0)),
                new KeyFrame(new Duration(500), new KeyValue(text.opacityProperty(), 1.0))
        );
        anim.setAutoReverse(true);
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.play();

        //l'evenement de clique pour demarrer une partie
        text.setOnMousePressed((MouseEvent e) -> {
            anim.stop();
            text.setVisible(false);
            imageView.setVisible(false);
        });

        text.setCursor(Cursor.HAND);

    }

}
