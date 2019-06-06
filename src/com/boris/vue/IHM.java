/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.vue;

import com.boris.controleur.Controleur;
import com.boris.modele.IA.AbstractIA;
import com.boris.modele.IA.IABasique;
import com.boris.modele.IA.IAHardcore;
import com.boris.modele.IA.IAIntermediaire;
import com.boris.modele.Jeu;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author boris
 */
public class IHM extends Group implements Observateur {

    private Grille grilleJoueur;
    private Grille grilleIA;

    public IHM() {
        super();

        ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/com/boris/images/background.png")));
        imageView.setFitHeight(900);
        imageView.setFitWidth(500);
        imageView.setX(0);
        imageView.setY(0);

        Jeu j = new Jeu();
        AbstractIA ia = new IABasique(j);
        Controleur c = new Controleur(j, ia);
        FinPartie fin = new FinPartie(j, c);

        grilleJoueur = new Grille(false, j, c);
        grilleIA = new Grille(true, j, c);

        j.placerBateau(true);
        j.placerBateau(false);

        System.out.println(j);

        this.getChildren().add(imageView);
        this.getChildren().add(grilleJoueur);
        this.getChildren().add(grilleIA);
        this.getChildren().add(fin);
        miseAJour();
    }

    @Override
    public void miseAJour() {
        grilleIA.miseAJour();
        grilleJoueur.miseAJour();
    }

}
