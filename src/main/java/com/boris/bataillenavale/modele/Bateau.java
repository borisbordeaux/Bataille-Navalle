/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.modele;

import java.util.ArrayList;

/**
 * Classe representant un bateau
 *
 * @author boris
 */
public class Bateau {

    //le bateau est placé sur des cases
    private final ArrayList<Case> cases;

    //le bateau a un nom
    private final String nom;

    //il possede un nombre de case maxi
    private final int nbCase;

    /**
     * Le constructeur, il initialise le bateau
     *
     * @param nom le nom du bateau
     * @param nbCases le nombre de cases qu'il prend
     */
    public Bateau(String nom, int nbCases) {
        this.cases = new ArrayList<Case>();
        this.nom = nom;
        this.nbCase = nbCases;
    }

    /**
     * indique si le bateau est coule ou non
     *
     * @return vrai si le bateau ne contient que des cases tiree
     */
    public boolean estCoule() {
        boolean res = true;
        for (Case c : cases) {
            if (!c.estTiree()) {
                res = false;
            }
        }
        if (res) {
            for (Case c : cases) {
                c.mettreBateauCoule();
            }
        }
        return res;
    }

    /**
     * indique si le bateau est sur la case passee en parametre
     *
     * @param c la case dont on veut savoir si le bateau est dessus
     * @return vrai si le bateau est sur la case, faux sinon
     */
    public boolean appartient(Case c) {
        return cases.contains(c);
    }

    /**
     * permet de placer le bateau sur la case passee en parametre
     *
     * @param c la case sur laquelle on veut placer le bateau
     */
    public void ajouterCase(Case c) {
        if (cases.size() < nbCase) {
            cases.add(c);
            c.mettreBateau();
        }
    }

    /**
     * @return le nombre de case du bateau
     */
    public int getNbCase() {
        return nbCase;
    }

    /**
     * retire les cases associees au bateau permet de recommencer une partie
     */
    public void reset() {
        for (Case c : cases) {
            c.reset();
        }
        cases.clear();
    }

    /**
     * permet de savoir le nom du bateau
     *
     * @return "Le " suivi du nom du bateau
     */
    @Override
    public String toString() {
        return "Le " + nom;
    }

}
