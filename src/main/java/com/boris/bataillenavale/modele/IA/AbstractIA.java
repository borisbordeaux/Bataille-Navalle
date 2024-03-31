/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.modele.IA;

import com.boris.bataillenavale.modele.Jeu;

/**
 * classe abstraite d'une IA elle doit pouvoir jouer un coup
 *
 * @author boris
 */
public abstract class AbstractIA extends Coordonnee {

    //le modele
    protected Jeu modele;

    public AbstractIA(Jeu j) {
        super(0, 0);
        modele = j;
    }

    /**
     * permet de jouer un coup
     */
    public abstract void tirerSurUneCase();

    /**
     * reinitialise l'IA
     */
    public void reset() {
        super.reset();
    }

}
