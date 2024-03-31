/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.modele.IA;

import com.boris.bataillenavale.modele.Case;
import com.boris.bataillenavale.modele.Jeu;

/**
 * Une IA tres difficile a battre, car elle tirera sur vos bateaux sans rater un
 * seul tir
 *
 * @author boris
 */
public class IAHardcore extends AbstractIA {

    /**
     * le constructeur
     *
     * @param j le modele sur lequel l'IA pourra jouer
     */
    public IAHardcore(Jeu j) {
        super(j);
    }

    /**
     * permet de jouer un coup
     */
    @Override
    public void tirerSurUneCase() {
        boolean cibleTrouvee = false;
        int i = 0;
        int j = 0;
        do {
            Case c = modele.getCaseAt(i, j, false);
            if (c.contientBateau() && !c.estTiree()) {
                cibleTrouvee = true;
                x = i;
                y = j;
            }
            i++;
            if (i == 10) {
                i = 0;
                j++;
            }
        } while (!cibleTrouvee);

        System.out.println("tir en " + x + ";" + y);
        modele.tirerSurCase(x, y, true);
    }

}
