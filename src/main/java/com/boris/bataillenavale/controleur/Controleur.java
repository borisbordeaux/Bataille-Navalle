/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.controleur;

import com.boris.bataillenavale.modele.IA.AbstractIA;
import com.boris.bataillenavale.modele.Jeu;

/**
 * Le controleur, il sert à modifier le modele et à mettre à jour toutes les
 * vues associees
 *
 * @author boris
 */
public class Controleur extends Sujet {

    //le modele à modifier
    private Jeu modele;
    //l'ia qui jouera apres le joueur
    private AbstractIA IA;

    /**
     * Le constructeur
     *
     * @param j le modele
     * @param ia l'ia du controleur (plusieurs difficultes possibles)
     */
    public Controleur(Jeu j, AbstractIA ia) {
        modele = j;
        this.IA = ia;
    }

    /**
     * permet de placer les bateaux automatiquement
     *
     * @param forIA si true, place les bateaux de l'ia automatiquement, sinon ça
     * place les bateaux du joueur automatiquement
     */
    public void placerBateau(boolean forIA) {
        modele.placerBateau(forIA);
        this.notifie();
    }

    /**
     * permet de placer un bateau
     *
     * @param x la coordonnee x du bateau
     * @param y la coordonnee y du bateau
     * @param longueurBateau la taille du bateau
     * @param horizontal indique si le bateau est horizontal ou vertical
     */
    public void placerBateauJoueur(int x, int y, int longueurBateau, boolean horizontal) {
        modele.placerBateauJoueur(x, y, longueurBateau, horizontal);
        this.notifie();
    }

    /**
     * permet de tirer sur une case
     *
     * @param x la coordonnee x de la case
     * @param y la coordonnee y de la case
     * @param forIA si true, fait tirer l'IA
     */
    public void tirerCase(int x, int y, boolean forIA) {
        modele.tirerSurCase(x, y, forIA);
        if (!forIA) {
            IA.tirerSurUneCase();
        }

        this.notifie();
    }

    /**
     * permet de changer la difficulte
     *
     * @param ia l'ia qu'il faut utiliser
     */
    public void changerDifficulte(AbstractIA ia) {
        this.IA = ia;
    }

    /**
     * permet de recommencer une partie
     */
    public void resetPartie() {
        modele.resetPartie();
        IA.reset();
        this.notifie();
    }

}
