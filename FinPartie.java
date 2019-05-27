/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.vue;

import com.boris.controleur.Controleur;
import com.boris.modele.Gagnant;
import com.boris.modele.Jeu;
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
public class FinPartie extends Parent implements Observateur {

    private Jeu jeu;
    private Controleur controleur;
    private ImageView images[];
    private boolean finPartie;
    private Text text;
    private Timeline anim;

    public FinPartie(Jeu j, Controleur c) throws MalformedURLException {
        jeu = j;
        controleur = c;
        controleur.abonne(this);

        images = new ImageView[2];

        File fileV = new File("images/victoire.png");
        File fileD = new File("images/defaite.png");
        String localUrlV = fileV.toURI().toURL().toString();
        String localUrlD = fileD.toURI().toURL().toString();
        Image image[] = new Image[2];
        image[0] = new Image(localUrlV);
        image[1] = new Image(localUrlD);

        for (int i = 0; i < 2; i++) {
            images[i] = new ImageView(image[i]);
            images[i].setFitHeight(900);
            images[i].setFitWidth(500);
            images[i].setX(0);
            images[i].setY(0);
            images[i].setVisible(false);
            this.getChildren().add(images[i]);
        }

        text = new Text(12, 600, "CLIQUER ICI POUR RECOMMENCER");
        text.setFont(new Font(30));
        text.setVisible(false);
        this.getChildren().add(text);

        anim = new Timeline();
        anim.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(500), new KeyValue(text.opacityProperty(), 0.0)),
                new KeyFrame(new Duration(500), new KeyValue(text.opacityProperty(), 1.0))
        );
        anim.setAutoReverse(true);
        anim.setCycleCount(Timeline.INDEFINITE);

        text.setOnMouseClicked((MouseEvent me) -> {
            if (finPartie) {
                controleur.resetPartie();
                finPartie = false;
            }
        });
    }

    @Override
    public void miseAJour() {
        if (jeu.getGagnant() == Gagnant.JOUEUR) {
            text.setVisible(true);
            finPartie = true;
            images[0].setVisible(true);
            anim.play();
        }
        if (jeu.getGagnant() == Gagnant.IA) {
            text.setVisible(true);
            finPartie = true;
            images[1].setVisible(true);
            anim.play();
        }
        if (jeu.getGagnant() == Gagnant.PERSONNE) {
            finPartie = false;
            images[0].setVisible(false);
            images[1].setVisible(false);
            anim.stop();
            text.setVisible(false);
        }
    }

}
