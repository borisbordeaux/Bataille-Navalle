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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
    private Timeline timelineFumee;
    private Timeline timelineFeu;
    private Timeline timelineSplash;
    private Timeline timelineExplosion;
    private ImageView imagesFumee[];
    private ImageView imagesFeu[];
    private ImageView imagesSplash[];
    private ImageView imagesExplosion[];
    private int nbImageFumee = 0;
    private int nbImageFeu = 0;
    private int nbImagesSplash = 0;
    private int nbImagesExplosion = 0;
    private boolean dejaExplose;
    private boolean dejaSplash;

    public Carre(int x, int y, int decalage, Jeu jeu, Controleur c, boolean isIA) {

        this.isIA = isIA;
        valX = x;
        valY = y;
        modele = jeu;
        controleur = c;
        dejaExplose = false;
        dejaSplash = false;

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

        imagesFumee = new ImageView[24];
        for (int i = 0; i < 24; i++) {
            imagesFumee[i] = new ImageView(new Image(this.getClass().getResourceAsStream("/com/boris/images/fumee/" + Integer.toString(i + 1) + ".png")));
            this.getChildren().add(imagesFumee[i]);
            imagesFumee[i].setVisible(false);
            imagesFumee[i].setX(0);
            imagesFumee[i].setY(0);
            imagesFumee[i].setFitHeight(40);
            imagesFumee[i].setFitWidth(40);
        }
        timelineFumee = new Timeline();
        KeyFrame keyFumee = new KeyFrame(new Duration(20), (fin) -> {
            this.majImageFumee();
        });
        timelineFumee.getKeyFrames().add(keyFumee);
        timelineFumee.setCycleCount(Timeline.INDEFINITE);

        imagesFeu = new ImageView[6];
        for (int i = 0; i < 6; i++) {
            imagesFeu[i] = new ImageView(new Image(this.getClass().getResourceAsStream("/com/boris/images/feu/" + Integer.toString(i + 1) + ".png")));
            this.getChildren().add(imagesFeu[i]);
            imagesFeu[i].setVisible(false);
            imagesFeu[i].setX(0);
            imagesFeu[i].setY(0);
            imagesFeu[i].setFitHeight(40);
            imagesFeu[i].setFitWidth(40);
        }
        timelineFeu = new Timeline();
        KeyFrame keyFeu = new KeyFrame(new Duration(50), (fin) -> {
            this.majImageFeu();
        });
        timelineFeu.getKeyFrames().add(keyFeu);
        timelineFeu.setCycleCount(Timeline.INDEFINITE);
        if (isIA) {
            Image im[] = new Image[1];
            im[0] = new Image(this.getClass().getResourceAsStream("/com/boris/images/viseur.png"));
            setCursor(ImageCursor.chooseBestCursor(im, 15, 15));
        }

        imagesSplash = new ImageView[8];
        for (int i = 0; i < 8; i++) {
            imagesSplash[i] = new ImageView(new Image(this.getClass().getResourceAsStream("/com/boris/images/splash/" + Integer.toString(i + 1) + ".png")));
            this.getChildren().add(imagesSplash[i]);
            imagesSplash[i].setVisible(false);
            imagesSplash[i].setX(0);
            imagesSplash[i].setY(0);
            imagesSplash[i].setFitHeight(40);
            imagesSplash[i].setFitWidth(40);
        }
        timelineSplash = new Timeline();
        KeyFrame keySplash = new KeyFrame(new Duration(80), (fin) -> {
            this.majImageSplash();
        });
        timelineSplash.getKeyFrames().add(keySplash);
        timelineSplash.setCycleCount(8);

        imagesExplosion = new ImageView[16];
        for (int i = 0; i < 16; i++) {
            imagesExplosion[i] = new ImageView(new Image(this.getClass().getResourceAsStream("/com/boris/images/explosion/" + Integer.toString(i + 1) + ".png")));
            this.getChildren().add(imagesExplosion[i]);
            imagesExplosion[i].setVisible(false);
            imagesExplosion[i].setX(0);
            imagesExplosion[i].setY(0);
            imagesExplosion[i].setFitHeight(40);
            imagesExplosion[i].setFitWidth(40);
        }
        timelineExplosion = new Timeline();
        KeyFrame keyExplosion = new KeyFrame(new Duration(30), (fin) -> {
            this.majImageExplosion();
        });
        timelineExplosion.getKeyFrames().add(keyExplosion);
        timelineExplosion.setCycleCount(16);

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
    }

    public void reset() {
        this.rect.setStroke(Color.BLACK);
        this.isChecked = false;
        this.rect.setFill(Color.TRANSPARENT);
        timelineExplosion.stop();
        timelineFeu.stop();
        timelineFumee.stop();
        timelineSplash.stop();
        nbImageFumee = 0;
        nbImageFeu = 0;
        nbImagesSplash = 0;
        nbImagesExplosion = 0;
        dejaExplose = false;
        dejaSplash = false;
        for (int i = 0; i < 16; i++) {
            imagesExplosion[i].setVisible(false);
        }
        for (int i = 0; i < 24; i++) {
            imagesFumee[i].setVisible(false);
        }
        for (int i = 0; i < 8; i++) {
            imagesSplash[i].setVisible(false);
        }
        for (int i = 0; i < 6; i++) {
            imagesFeu[i].setVisible(false);
        }
    }

    private void majImageFumee() {
        imagesFumee[nbImageFumee].setVisible(false);
        nbImageFumee++;
        if (nbImageFumee == 24) {
            nbImageFumee = 0;
        }
        imagesFumee[nbImageFumee].setVisible(true);
    }

    private void majImageFeu() {
        imagesFeu[nbImageFeu].setVisible(false);
        nbImageFeu++;
        if (nbImageFeu == 6) {
            nbImageFeu = 0;
        }
        imagesFeu[nbImageFeu].setVisible(true);
    }

    private void majImageSplash() {
        if (nbImagesSplash != 0 && nbImagesSplash != 1 && nbImagesSplash != 8) {
            imagesSplash[nbImagesSplash - 1].setVisible(false);
            imagesSplash[nbImagesSplash].setVisible(true);
            nbImagesSplash++;
        }
        if (nbImagesSplash == 1) {
            imagesSplash[0].setVisible(false);
            imagesSplash[1].setVisible(true);
            nbImagesSplash++;
        }
        if (nbImagesSplash == 0) {
            imagesSplash[7].setVisible(false);
            imagesSplash[0].setVisible(true);
            nbImagesSplash++;
        }
        if (nbImagesSplash == 8) {
            nbImagesSplash = 0;
            for (int i = 0; i < 8; i++) {
                imagesSplash[i].setVisible(false);
            }
        }
    }

    private void majImageExplosion() {
        if (nbImagesExplosion != 0 && nbImagesExplosion != 1 && nbImagesExplosion != 16) {
            imagesExplosion[nbImagesExplosion - 1].setVisible(false);
            imagesExplosion[nbImagesExplosion].setVisible(true);
            nbImagesExplosion++;
        }
        if (nbImagesExplosion == 1) {
            imagesExplosion[0].setVisible(false);
            imagesExplosion[1].setVisible(true);
            nbImagesExplosion++;
        }
        if (nbImagesExplosion == 0) {
            imagesExplosion[7].setVisible(false);
            imagesExplosion[0].setVisible(true);
            nbImagesExplosion++;
        }
        if (nbImagesExplosion == 16) {
            nbImagesExplosion = 0;
            for (int i = 0; i < 16; i++) {
                imagesExplosion[i].setVisible(false);
            }
            if (timelineFumee.getStatus() != Timeline.Status.RUNNING) {
                timelineFeu.play();
            }
        }
    }

    @Override
    public void miseAJour() {
        Case c = modele.getCaseAt(valX, valY, isIA);

        if (c.estTiree() && c.contientBateau() && !dejaExplose) {
            timelineExplosion.play();
            dejaExplose = true;
            //System.out.println("boom !");
        }
        if (c.estTiree() && !c.contientBateau() && !dejaSplash) {
            timelineSplash.play();
            dejaSplash = true;
            //System.out.println("plouf !");
        }

        if (c.contientBateau() && c.estTiree() && !c.contientBateauCoule()) {
            this.rect.setFill(Color.ORANGE);
            if (timelineExplosion.getStatus() != Timeline.Status.RUNNING) {
                timelineFeu.play();
            }
        } else {
            timelineFeu.stop();
            for (int i = 0; i < 6; i++) {
                imagesFeu[i].setVisible(false);
            }
        }
        if (c.contientBateau() && c.estTiree() && c.contientBateauCoule()) {
            this.rect.setFill(Color.BLACK);
            timelineFumee.play();
        } else {
            timelineFumee.stop();
            for (int i = 0; i < 24; i++) {
                imagesFumee[i].setVisible(false);
            }
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
        
        if (modele.getGagnant() != Gagnant.PERSONNE) {
            reset();
        }
    }

}
