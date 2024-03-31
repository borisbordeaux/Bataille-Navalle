/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.vue;

import com.boris.bataillenavale.controleur.Controleur;
import com.boris.bataillenavale.modele.Gagnant;
import com.boris.bataillenavale.modele.Jeu;
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
 * Affiche l'image de fin de partie et permet d'en recommencer une
 *
 * @author boris
 */
public class FinPartie extends Parent implements Observateur {

    private Jeu modele;
    private Controleur controleur;
    private ImageView images[];
    private boolean finPartie;
    private Text text;
    //l'animation du texte qui cligotte
    private Timeline anim;

    /**
     * le constructeur
     *
     * @param m le modele
     * @param c le controleur
     */
    public FinPartie(Jeu m, Controleur c) {
        modele = m;
        controleur = c;
        controleur.abonne(this);

        //les images
        images = new ImageView[2];

        Image image[] = new Image[2];
        image[0] = new Image(getClass().getResourceAsStream("/images/victoire.png"));
        image[1] = new Image(getClass().getResourceAsStream("/images/defaite.png"));

        for (int i = 0; i < 2; i++) {
            images[i] = new ImageView(image[i]);
            images[i].setFitHeight(900);
            images[i].setFitWidth(500);
            images[i].setX(0);
            images[i].setY(0);
            images[i].setVisible(false);
            this.getChildren().add(images[i]);
        }

        //le texte
        text = new Text(12, 600, "CLIQUER ICI POUR RECOMMENCER");
        text.setFont(new Font(30));
        text.setVisible(false);
        text.setCursor(Cursor.HAND);
        this.getChildren().add(text);

        //l'animation du texte via les timelines
        anim = new Timeline();
        anim.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(500), new KeyValue(text.opacityProperty(), 0.0)),
                new KeyFrame(new Duration(500), new KeyValue(text.opacityProperty(), 1.0))
        );
        anim.setAutoReverse(true);
        anim.setCycleCount(Timeline.INDEFINITE);

        //l'evenement du clique permet de reinitialiser la partie
        text.setOnMouseClicked((MouseEvent me) -> {
            if (finPartie) {
                controleur.resetPartie();
                finPartie = false;
            }
        });
    }

    /**
     * permet de mettre a jour l'affichage selon le modele
     */
    @Override
    public void miseAJour() {
        if (modele.getGagnant() == Gagnant.JOUEUR) {
            text.setVisible(true);
            finPartie = true;
            images[0].setVisible(true);
            anim.play();
        }
        if (modele.getGagnant() == Gagnant.IA) {
            text.setVisible(true);
            finPartie = true;
            images[1].setVisible(true);
            anim.play();
        }
        if (modele.getGagnant() == Gagnant.PERSONNE) {
            finPartie = false;
            images[0].setVisible(false);
            images[1].setVisible(false);
            anim.stop();
            text.setVisible(false);
        }
    }

}
