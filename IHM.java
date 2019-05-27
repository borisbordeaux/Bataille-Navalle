/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.vue;

import com.boris.controleur.Controleur;
import com.boris.modele.AbstractIA;
import com.boris.modele.IABasique;
import com.boris.modele.Jeu;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author boris
 */
public class IHM extends Group implements Observateur{
    
    private Grille grilleJoueur;
    private Grille grilleIA;
    
    public IHM() throws MalformedURLException{
        super();
        
        File file = new File("images/background.png");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);

        ImageView imageView = new ImageView(image);
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
