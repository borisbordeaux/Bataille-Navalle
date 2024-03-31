/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.vue;

import com.boris.bataillenavale.controleur.Controleur;
import com.boris.bataillenavale.modele.IA.IABasique;
import com.boris.bataillenavale.modele.IA.IAHardcore;
import com.boris.bataillenavale.modele.IA.IAIntermediaire;
import com.boris.bataillenavale.modele.Jeu;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author boris
 */
public class GrillePlacerBateaux extends Parent implements Observateur {

    private final Jeu modele;
    private Controleur controleur;
    private final CarrePlacerBateaux carres[][];
    //orientation du bateau à placer
    private boolean horizontal;
    //la difficulte
    private Difficulte difficulte;

    //les differents rectangle
    private final Rectangle panelInfo;
    private Rectangle buttonAutoSet;
    private Rectangle buttonChangeDirection;
    private Rectangle buttonChangeDifficulte;

    //les differents textes
    private final Text textAutoSet;
    private final Text textChangerDirection;
    private final Text textInfo;
    private final Text textDifficulte;

    //permet de regrouper les rectangle et les texte pour centrer le texte
    //cela permet de creer un boutton
    private final StackPane stackButtonAutoSet;
    private final StackPane stackButtonChangeDirection;
    private final StackPane stackButtonChangeDifficulty;

    /**
     * le constructeur
     *
     * @param m le modele
     * @param c le controleur
     */
    public GrillePlacerBateaux(Jeu m, Controleur c) {
        this.modele = m;
        controleur = c;
        c.abonne(this);

        horizontal = true;

        carres = new CarrePlacerBateaux[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                carres[i][j] = new CarrePlacerBateaux(i, j, 200, m, c);
                this.getChildren().add(carres[i][j]);
            }
        }

        //les differents rectangle qui feront guise de bouton
        buttonAutoSet = new Rectangle();
        buttonAutoSet.setWidth(400);
        buttonAutoSet.setHeight(40);
        buttonAutoSet.setArcWidth(10);
        buttonAutoSet.setArcHeight(10);
        buttonAutoSet.setFill(new Color(0.56, 0.87, 0.8, 1));
        buttonAutoSet.setStroke(Color.BLACK);

        buttonChangeDirection = new Rectangle();
        buttonChangeDirection.setWidth(400);
        buttonChangeDirection.setHeight(40);
        buttonChangeDirection.setArcWidth(10);
        buttonChangeDirection.setArcHeight(10);
        buttonChangeDirection.setFill(new Color(0.56, 0.87, 0.8, 1));
        buttonChangeDirection.setStroke(Color.BLACK);

        buttonChangeDifficulte = new Rectangle();
        buttonChangeDifficulte.setWidth(400);
        buttonChangeDifficulte.setHeight(40);
        buttonChangeDifficulte.setArcWidth(10);
        buttonChangeDifficulte.setArcHeight(10);
        buttonChangeDifficulte.setFill(new Color(0.56, 0.87, 0.8, 1));
        buttonChangeDifficulte.setStroke(Color.BLACK);

        difficulte = Difficulte.MEDIUM;

        //les differents texte
        textChangerDirection = new Text(this.horizontal ? "Horizontal" : "Vertical");
        textChangerDirection.setFont(new Font(30));

        textAutoSet = new Text("Placer aléatoirement");
        textAutoSet.setFont(new Font(30));

        textDifficulte = new Text("Medium");
        textDifficulte.setFont(new Font(30));

        //les groupes permettant de creer les boutons
        stackButtonChangeDirection = new StackPane();
        stackButtonAutoSet = new StackPane();
        stackButtonChangeDifficulty = new StackPane();

        //a chaque fois on ajoute le texte et le rectangle
        //et on definit les differents evenement de la souris
        stackButtonChangeDirection.getChildren().add(buttonChangeDirection);
        stackButtonChangeDirection.getChildren().add(textChangerDirection);
        stackButtonChangeDirection.setOnMousePressed((MouseEvent) -> {
            horizontal = !horizontal;
            setHorizontal(horizontal);
        });
        stackButtonChangeDirection.setOnMouseEntered((MouseEvent) -> {
            buttonChangeDirection.setStroke(Color.BLUE);
        });
        stackButtonChangeDirection.setOnMouseExited((MouseEvent) -> {
            buttonChangeDirection.setStroke(Color.BLACK);
        });
        stackButtonChangeDirection.setCursor(Cursor.HAND);

        stackButtonChangeDifficulty.getChildren().add(buttonChangeDifficulte);
        stackButtonChangeDifficulty.getChildren().add(textDifficulte);
        stackButtonChangeDifficulty.setOnMousePressed((MouseEvent) -> {
            changerDifficulte();
        });
        stackButtonChangeDifficulty.setOnMouseEntered((MouseEvent) -> {
            buttonChangeDifficulte.setStroke(Color.BLUE);
        });
        stackButtonChangeDifficulty.setOnMouseExited((MouseEvent) -> {
            buttonChangeDifficulte.setStroke(Color.BLACK);
        });
        stackButtonChangeDifficulty.setCursor(Cursor.HAND);

        stackButtonAutoSet.getChildren().add(buttonAutoSet);
        stackButtonAutoSet.getChildren().add(textAutoSet);
        stackButtonAutoSet.setOnMousePressed((MouseEvent) -> {
            controleur.placerBateau(false);
        });
        stackButtonAutoSet.setOnMouseEntered((MouseEvent) -> {
            buttonAutoSet.setStroke(Color.BLUE);
        });
        stackButtonAutoSet.setOnMouseExited((MouseEvent) -> {
            buttonAutoSet.setStroke(Color.BLACK);
        });
        stackButtonAutoSet.setCursor(Cursor.HAND);

        //on definit les positions des boutons
        stackButtonChangeDifficulty.setTranslateX(50);
        stackButtonChangeDifficulty.setTranslateY(690);

        stackButtonAutoSet.setTranslateX(50);
        stackButtonAutoSet.setTranslateY(750);

        stackButtonChangeDirection.setTranslateX(50);
        stackButtonChangeDirection.setTranslateY(810);

        //on cree un petit paneau d'information
        panelInfo = new Rectangle();
        panelInfo.setWidth(400);
        panelInfo.setHeight(125);
        panelInfo.setArcWidth(10);
        panelInfo.setArcHeight(10);
        panelInfo.setFill(new Color(0.56, 0.87, 0.8, 1));
        panelInfo.setStroke(Color.BLACK);
        panelInfo.setTranslateX(50);
        panelInfo.setTranslateY(60);

        //le texte dinformation
        textInfo = new Text();
        textInfo.setTranslateX(55);
        textInfo.setTranslateY(82);
        textInfo.setFont(new Font(20));

        this.getChildren().add(panelInfo);
        this.getChildren().add(textInfo);
        this.getChildren().add(stackButtonChangeDifficulty);
        this.getChildren().add(stackButtonAutoSet);
        this.getChildren().add(stackButtonChangeDirection);
    }

    /**
     * permet de changer la direction du bateau à placer
     *
     * @param horizontal true si ça doit etre horizontal, false sinon
     */
    public void setHorizontal(boolean horizontal) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                carres[i][j].setHorizontal(horizontal);
            }
        }
        textChangerDirection.setText(this.horizontal ? "Horizontal" : "Vertical");
    }

    /**
     * permet de changer la difficulte, ça tourne en boucle entre easy, medium
     * et hard
     */
    public void changerDifficulte() {
        switch (difficulte) {
            case EASY:
                difficulte = Difficulte.MEDIUM;
                textDifficulte.setText("Medium");
                controleur.changerDifficulte(new IAIntermediaire(modele));
                break;
            case MEDIUM:
                difficulte = Difficulte.HARD;
                textDifficulte.setText("Hard");
                controleur.changerDifficulte(new IAHardcore(modele));
                break;
            case HARD:
                difficulte = Difficulte.EASY;
                textDifficulte.setText("Easy");
                controleur.changerDifficulte(new IABasique(modele));
        }
    }

    /**
     * permet d'actualiser l'affichage en fonction du modele
     */
    @Override
    public void miseAJour() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                carres[i][j].miseAJour();
            }
        }

        textInfo.setText("Veuillez choisir une difficulté et placer\nvos bateaux !\nCliquez sur une case jaune pour placer\nle bateau de "
                + this.carres[0][0].getLongueurBateau() + " cases. Vous pouvez\nchanger sa direction.");

        if (modele.bateauxJoueurPlaces()) {
            stackButtonAutoSet.setVisible(false);
            stackButtonChangeDirection.setVisible(false);
            textInfo.setVisible(false);
            stackButtonChangeDifficulty.setVisible(false);
            panelInfo.setVisible(false);
        } else {
            stackButtonAutoSet.setVisible(true);
            stackButtonChangeDirection.setVisible(true);
            textInfo.setVisible(true);
            stackButtonChangeDifficulty.setVisible(true);
            panelInfo.setVisible(true);
        }
    }

}
