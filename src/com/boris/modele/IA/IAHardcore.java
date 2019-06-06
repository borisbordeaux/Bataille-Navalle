/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.modele.IA;

import com.boris.modele.Case;
import com.boris.modele.Jeu;

/**
 *
 * @author boris
 */
public class IAHardcore extends AbstractIA {

    public IAHardcore(Jeu j) {
        super(j);
    }

    @Override
    public void tirerSurUneCase() {
        boolean cibleTrouvee = false;
        int i = 0;
        int j = 0;
        do {
            Case c = jeu.getCaseAt(i, j, false);
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
        jeu.tirerSurCase(x, y, true);
    }

}
