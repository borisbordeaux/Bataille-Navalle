/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.modele;

/**
 * Represente un case du jeu, elle peut etre tirée ou non
 *
 * @author boris
 */
public class Case {

    //indique si la case est tirée
    private boolean tiree;

    //indique si la case contient un bateau
    private boolean contientBateau;

    //indique si la case contient un bateau coule
    private boolean contientBateauCoule;

    /**
     * Constructeur qui initialise la case comme etant non tiree et ne contenant
     * pas de bateau
     */
    public void Case() {
        tiree = false;
        contientBateau = false;
        contientBateauCoule = false;
    }

    /**
     * permet de rendre cette case tiree
     */
    public void tirer() {
        tiree = true;
    }

    /**
     * indique si la case est tiree
     *
     * @return vrai si la case est tiree, faux sinon
     */
    public boolean estTiree() {
        return tiree;
    }

    /**
     * indique si la case contient un bateau
     *
     * @return vrai si elle contient un bateau, faux sinon
     */
    public boolean contientBateau() {
        return contientBateau;
    }

    /**
     * permet d'indiquer que la case contient un bateau
     */
    public void mettreBateau() {
        this.contientBateau = true;
    }

    /**
     * permet de savoir si la case contient un bateau coule
     *
     * @return true si c'est le cas
     */
    public boolean contientBateauCoule() {
        return contientBateauCoule;
    }

    /**
     * permet d'indiquer que le bateau contient un bateau coule
     */
    public void mettreBateauCoule() {
        contientBateauCoule = true;
    }

    /**
     * reinitialise la case
     */
    public void reset() {
        this.tiree = false;
        this.contientBateau = false;
        this.contientBateauCoule = false;
    }

    /**
     * @return "X" si la case est tiree et contenait un bateau "O" si la case
     * est tiree mais ne contenait pas de bateau "-" si la case n'a pas ete
     * tiree et n'a pas de bateau et "o" si la case n'a pas ete tiree et a un
     * bateau
     */
    @Override
    public String toString() {
        return this.tiree ? (this.contientBateau ? "X" : "O") : (this.contientBateau ? "o" : "-");
    }

}
