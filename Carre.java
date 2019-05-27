/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.vue;

import com.boris.controleur.Controleur;
import com.boris.modele.Case;
import com.boris.modele.Gagnant;
import com.boris.modele.Jeu;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author boris
 */
public class Carre extends Parent implements Observateur {

    private boolean isChecked;
    private Rectangle rect;
    private int valX;
    private int valY;
    private Jeu modele;
    private Controleur controleur;
    private boolean isIA;

    public Carre(int x, int y, int decalage, Jeu jeu, Controleur c, boolean isIA) throws MalformedURLException {

        this.isIA = isIA;
        valX = x;
        valY = y;
        modele = jeu;
        controleur = c;

        rect = new Rectangle();
        rect.setWidth(40);
        rect.setHeight(40);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);

        this.setTranslateX(x * 40 + 50);//on positionne le groupe plutôt que le rectangle
        this.setTranslateY(y * 40 + decalage + 20);
        this.getChildren().add(rect);

        if (isIA) {
            File file = new File("images/viseur.png");
            String localUrl = file.toURI().toURL().toString();
            Image im[] = new Image[1];
            im[0] = new Image(localUrl);
            setCursor(ImageCursor.chooseBestCursor(im, 15, 15));
        }

        this.setOnMousePressed((MouseEvent me) -> {
            if (!isChecked && isIA && jeu.getGagnant() == Gagnant.PERSONNE) {
                clique();
            }
        });

        this.setOnMouseEntered((MouseEvent me) -> {
            if (!isChecked) {
                rect.setStroke(Color.WHITE);
            }
        });

        this.setOnMouseExited((MouseEvent me) -> {
            if (!isChecked) {
                rect.setStroke(Color.BLACK);
            }
        });
    }

    public void clique() {
        isChecked = true;
        controleur.tirerCase(valX, valY, !isIA);
        System.out.println("un tir en " + valX + ":" + valY);
    }

    public void reset() {
        this.rect.setStroke(Color.BLACK);
        this.isChecked = false;
        this.rect.setFill(Color.TRANSPARENT);
    }

    @Override
    public void miseAJour() {
        if (modele.getGagnant() != Gagnant.PERSONNE) {
            reset();
        }
        Case c = modele.getCasesAt(valX, valY, isIA);
        if (c.contientBateau() && c.estTiree() && !c.contientBateauCoule()) {
            this.rect.setFill(Color.RED);
        }
        if (c.contientBateau() && c.estTiree() && c.contientBateauCoule()) {
            this.rect.setFill(Color.BLACK);
        }
        if (c.contientBateau() && !c.estTiree()) {
            if (isIA) {
                this.rect.setFill(Color.TRANSPARENT);
            } else {
                this.rect.setFill(Color.GREEN);
            }
        }
        if (!c.contientBateau() && c.estTiree()) {
            this.rect.setFill(Color.BLUE);
        }
        if (!c.contientBateau() && !c.estTiree()) {
            this.rect.setFill(Color.TRANSPARENT);
        }
    }

}
