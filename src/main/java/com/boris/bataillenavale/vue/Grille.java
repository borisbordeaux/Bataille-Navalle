/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.vue;

import com.boris.bataillenavale.controleur.Controleur;
import com.boris.bataillenavale.modele.Jeu;
import javafx.scene.Parent;

/**
 * Une grille carree de jeu avec 100 cases
 *
 * @author boris
 */
public class Grille extends Parent implements Observateur {

    private Carre cases[][];
    private Jeu modele;
    private Controleur controleur;

    /**
     * le constructeur
     *
     * @param isIA
     * @param jeu
     * @param c
     */
    public Grille(boolean isIA, Jeu jeu, Controleur c) {

        modele = jeu;
        controleur = c;
        c.abonne(this);

        cases = new Carre[10][10];

        int decalage = isIA ? 0 : 420;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cases[i][j] = new Carre(i, j, decalage, jeu, c, isIA);
                this.getChildren().add(cases[i][j]);
            }
        }
    }

    /**
     * permet d'actualiser l'affichage en fonction du modele
     */
    @Override
    public void miseAJour() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cases[i][j].miseAJour();
            }
        }
    }

}
