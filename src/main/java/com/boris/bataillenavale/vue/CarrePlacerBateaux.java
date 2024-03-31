/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.vue;

import com.boris.bataillenavale.controleur.Controleur;
import com.boris.bataillenavale.modele.Jeu;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * case permettant de placer nos bateaux
 *
 * @author boris
 */
public class CarrePlacerBateaux extends Parent implements Observateur {

    private Rectangle rect;
    private int valX;
    private int valY;
    private Jeu modele;
    private Controleur controleur;
    private int longueurBateau;
    private boolean horizontal;

    /**
     * le constructeur
     *
     * @param x la coordonnee en x de la case representee
     * @param y la coordonnee en y de la case representee
     * @param decalage le decalage pour deplacer la case au bon endroit
     * @param jeu le modele
     * @param c le controleur
     */
    public CarrePlacerBateaux(int x, int y, int decalage, Jeu jeu, Controleur c) {
        valX = x;
        valY = y;
        modele = jeu;
        controleur = c;

        //on cree le rectangle
        rect = new Rectangle();
        rect.setWidth(40);
        rect.setHeight(40);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);
        this.setTranslateX(x * 40 + 50);//on positionne le groupe plutôt que le rectangle
        this.setTranslateY(y * 40 + decalage + 40);
        this.getChildren().add(rect);

        longueurBateau = 5;
        horizontal = true;

        this.setCursor(Cursor.HAND);

        //on definit les evenements
        this.setOnMouseEntered((MouseEvent) -> {
            if (modele.peutPlacerBateauJoueur(valX, valY, longueurBateau, horizontal)) {
                rect.setFill(Color.YELLOW);
            } else {
                rect.setFill(Color.RED);
            }
        });

        this.setOnMouseExited((MouseEvent) -> {
            miseAJour();
        });

        this.setOnMousePressed((MouseEvent me) -> {
            controleur.placerBateauJoueur(valX, valY, longueurBateau, horizontal);
        });

    }

    /**
     * permet de changer l'affichage en fonction des donnees
     */
    @Override
    public void miseAJour() {
        if (!modele.bateauxJoueurPlaces()) {
            this.setVisible(true);
            if (modele.getCaseAt(valX, valY, false).contientBateau()) {
                rect.setFill(Color.GREEN);
            } else {
                rect.setFill(Color.TRANSPARENT);
            }
            switch (modele.getNbBateauxJoueursPlaces()) {
                case 0:
                    this.longueurBateau = 5;
                    break;
                case 1:
                    this.longueurBateau = 4;
                    break;
                case 2:
                case 3:
                    this.longueurBateau = 3;
                    break;
                case 4:
                    this.longueurBateau = 2;
            }
        } else {
            this.setVisible(false);
        }
    }

    /**
     * @param horizontal true si la direction doit etre horizontal, false sinon
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * @return la longueur du bateau
     */
    public int getLongueurBateau() {
        return longueurBateau;
    }

}
