/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.vue;

import com.boris.bataillenavale.controleur.Controleur;
import com.boris.bataillenavale.modele.Case;
import com.boris.bataillenavale.modele.Gagnant;
import com.boris.bataillenavale.modele.Jeu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Une case du jeu
 *
 * @author boris
 */
public class Carre extends Parent implements Observateur {

    //indique si on a deja clique dessus
    private boolean isChecked;
    //le contour
    private Rectangle rect;
    //les coordonnees de la case qu'elle represente
    private int valX;
    private int valY;
    //le modele pour lire les donnees
    private Jeu modele;
    //le controleur pour modifier les donnees
    private Controleur controleur;
    //indique si c'est une case de l'IA ou du joueur
    private boolean isIA;
    //les timelines d'animation
    private Timeline timelineFumee;
    private Timeline timelineFeu;
    private Timeline timelineSplash;
    private Timeline timelineExplosion;
    //les images
    private ImageView imagesFumee[];
    private ImageView imagesFeu[];
    private ImageView imagesSplash[];
    private ImageView imagesExplosion[];
    //le numero de l'image actuelle
    //permet de se reperer et de faire les animations
    private int nbImageFumee = 0;
    private int nbImageFeu = 0;
    private int nbImagesSplash = 0;
    private int nbImagesExplosion = 0;
    //indique si les animation ont deja eu lieu
    //car on ne doit voir qu'une fois l'explosion
    //et le "plouf"
    private boolean dejaExplose;
    private boolean dejaSplash;

    /**
     * le constructeur
     *
     * @param x la coordonnee en x de la case representee
     * @param y la coordonnee en y de la case representee
     * @param decalage le decalage pour deplacer la case au bon endroit
     * @param jeu le modele
     * @param c le controleur
     * @param isIA true si c'est une case de l'IA, false sinon
     */
    public Carre(int x, int y, int decalage, Jeu jeu, Controleur c, boolean isIA) {

        this.isIA = isIA;
        valX = x;
        valY = y;
        modele = jeu;
        controleur = c;
        dejaExplose = false;
        dejaSplash = false;

        //on cree un carre
        rect = new Rectangle();
        rect.setWidth(40);
        rect.setHeight(40);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);

        //on positionne le groupe plutôt que le rectangle
        //c'est plus simple pour les animations
        this.setTranslateX(x * 40 + 50);
        this.setTranslateY(y * 40 + decalage + 40);
        this.getChildren().add(rect);

        //on cree les images et la timeline (on change d'image apres un certain temps)
        imagesFumee = new ImageView[24];
        for (int i = 0; i < 24; i++) {
            imagesFumee[i] = new ImageView(new Image(getClass().getResourceAsStream("/images/fumee/" + Integer.toString(i + 1) + ".png")));
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
            imagesFeu[i] = new ImageView(new Image(getClass().getResourceAsStream("/images/feu/" + Integer.toString(i + 1) + ".png")));
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
            setCursor(Cursor.CROSSHAIR);
        }

        imagesSplash = new ImageView[8];
        for (int i = 0; i < 8; i++) {
            imagesSplash[i] = new ImageView(new Image(getClass().getResourceAsStream("/images/splash/" + Integer.toString(i + 1) + ".png")));
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
            imagesExplosion[i] = new ImageView(new Image(getClass().getResourceAsStream("/images/explosion/" + Integer.toString(i + 1) + ".png")));
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

        //on definit les differents evenement de la souris (clique et deplacement)
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

    /**
     * indique qu'on a tire sur la case et utilise le controleur pour tirer sur
     * une case
     */
    public void clique() {
        isChecked = true;
        controleur.tirerCase(valX, valY, !isIA);
    }

    /**
     * reinitialise la case et les animations
     */
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

    /**
     * passe a l'image de fumee suivante
     */
    private void majImageFumee() {
        imagesFumee[nbImageFumee].setVisible(false);
        nbImageFumee++;
        if (nbImageFumee == 24) {
            nbImageFumee = 0;
        }
        imagesFumee[nbImageFumee].setVisible(true);
    }

    /**
     * passe a l'image de feu suivante
     */
    private void majImageFeu() {
        imagesFeu[nbImageFeu].setVisible(false);
        nbImageFeu++;
        if (nbImageFeu == 6) {
            nbImageFeu = 0;
        }
        imagesFeu[nbImageFeu].setVisible(true);
    }

    /**
     * a l'image du splash suivante
     */
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

    /**
     * a l'image de l'explosion suivante
     */
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

    /**
     * permet de changer l'affichage selon le modele
     */
    @Override
    public void miseAJour() {

        if (modele.bateauxJoueurPlaces()) {
            this.setVisible(true);
            Case c = modele.getCaseAt(valX, valY, isIA);

            if (c.estTiree() && c.contientBateau() && !dejaExplose) {
                timelineExplosion.play();
                dejaExplose = true;
            }
            if (c.estTiree() && !c.contientBateau() && !dejaSplash) {
                timelineSplash.play();
                dejaSplash = true;
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
        } else {
            this.setVisible(false);
        }
    }

}
