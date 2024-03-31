/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.vue;

import com.boris.bataillenavale.controleur.Controleur;
import com.boris.bataillenavale.modele.IA.AbstractIA;
import com.boris.bataillenavale.modele.IA.IAIntermediaire;
import com.boris.bataillenavale.modele.Jeu;
import java.io.File;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * l'IHM qui contriendra les differentes grilles du jeu
 *
 * @author boris
 */
public class IHM extends Group implements Observateur {

    private Grille grilleJoueur;
    private Grille grilleIA;
    private GrillePlacerBateaux grillePlacerBateaux;

    /**
     * le constructeur
     */
    public IHM() {
        super();

        System.out.println("chemin : " + new File("").getAbsolutePath());
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/background.png")));
        imageView.setFitHeight(900);
        imageView.setFitWidth(500);
        imageView.setX(0);
        imageView.setY(0);

        Jeu j = new Jeu();
        AbstractIA ia = new IAIntermediaire(j);
        Controleur c = new Controleur(j, ia);
        FinPartie fin = new FinPartie(j, c);

        grilleJoueur = new Grille(false, j, c);
        grilleIA = new Grille(true, j, c);
        grillePlacerBateaux = new GrillePlacerBateaux(j, c);

        System.out.println(j);

        this.getChildren().add(imageView);
        this.getChildren().add(grilleJoueur);
        this.getChildren().add(grilleIA);
        this.getChildren().add(grillePlacerBateaux);
        this.getChildren().add(fin);
        miseAJour();
    }

    /**
     * permet de mettre à jour l'IHM
     */
    @Override
    public void miseAJour() {
        grilleIA.miseAJour();
        grilleJoueur.miseAJour();
        grillePlacerBateaux.miseAJour();
    }

}
