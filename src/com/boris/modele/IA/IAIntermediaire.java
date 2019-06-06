/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.modele.IA;

import com.boris.modele.Case;
import com.boris.modele.Jeu;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author boris
 */
public class IAIntermediaire extends AbstractIA {

    public IAIntermediaire(Jeu jeu) {
        super(jeu);
        reset();
    }

    @Override
    public void tirerSurUneCase() {

        if (x == -1 && y == -1) {
            Random r = new Random();
            do {
                x = r.nextInt(10);
                y = r.nextInt(10);
            } while (jeu.getCaseAt(x, y, false).estTiree());
        }

        jeu.tirerSurCase(x, y, true);

        if (x != -1 && y != -1) {
            ArrayList<Coordonnee> list = new ArrayList<Coordonnee>();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Case c = jeu.getCaseAt(i, j, false);
                    if (c.estTiree() && c.contientBateau() && !c.contientBateauCoule()) {
                        list.add(new Coordonnee(i, j));
                    }
                }
            }
            if (!list.isEmpty()) {
                boolean tester = false;
                do {
                    tester = false;
                    x = list.get(0).getX();
                    y = list.get(0).getY();
                    if (!testGauche()) {
                        if (!testDroite()) {
                            if (!testHaut()) {
                                if (!testBas()) {
                                    list.remove(0);
                                    tester = true;
                                }
                            }
                        }
                    }
                } while (tester && !list.isEmpty());
                if (list.isEmpty()) {
                    x = -1;
                    y = -1;
                }
            } else {
                x = -1;
                y = -1;
            }
        }
    }

    private boolean testGauche() {
        boolean res = false;
        if (x > 0) {
            if (!jeu.getCaseAt(x - 1, y, false).estTiree() && jeu.getCaseAt(x, y, false).contientBateau()) {
                x--;
                res = true;
            } else {
                res = false;
            }
        }
        return res;
    }

    private boolean testDroite() {
        boolean res = false;
        if (x < 9) {
            if (!jeu.getCaseAt(x + 1, y, false).estTiree() && jeu.getCaseAt(x, y, false).contientBateau()) {
                x++;
                res = true;

            } else {
                res = false;
            }
        }
        return res;
    }

    private boolean testHaut() {
        boolean res = false;
        if (y > 0) {
            if (!jeu.getCaseAt(x, y - 1, false).estTiree() && jeu.getCaseAt(x, y, false).contientBateau()) {
                y--;
                res = true;

            } else {
                res = false;
            }
        }
        return res;
    }

    private boolean testBas() {
        boolean res = false;
        if (y < 9) {
            if (!jeu.getCaseAt(x, y + 1, false).estTiree() && jeu.getCaseAt(x, y, false).contientBateau()) {
                y++;
                res = true;
            } else {
                res = false;
            }
        }
        return res;
    }

    @Override
    public void reset() {
        x = -1;
        y = -1;
    }

}
