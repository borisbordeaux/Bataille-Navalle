/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.modele.IA;

import com.boris.bataillenavale.modele.Jeu;
import com.boris.bataillenavale.modele.Case;
import java.util.ArrayList;
import java.util.Random;

/**
 * une IA assez complexe qui joue comme un humain, elle se base sur les tirs
 * qu'elle a deja fait pour couler les bateaux
 *
 * @author boris
 */
public class IAIntermediaire extends AbstractIA {

    //direction et sens dans lesquels l'IA fera son prochain tir
    private Direction direction;
    private Sens sens;
    //la liste des tirs qu'elle a faits
    private ArrayList<Coordonnee> listeTirs;

    /**
     * le constructeur
     *
     * @param jeu le modele sur lequel l'IA pourra jouer
     */
    public IAIntermediaire(Jeu jeu) {
        super(jeu);
        reset();
    }

    /**
     * permet de jouer un coup
     */
    @Override
    public void tirerSurUneCase() {

        //si on a aucun tir touché
        if (listeTirs.isEmpty()) {
            System.out.println("tir random");
            Random r = new Random();
            do {
                x = r.nextInt(10);
                y = r.nextInt(10);
            } while (modele.getCaseAt(x, y, false).estTiree());
        }

        //on tire
        modele.tirerSurCase(x, y, true);
        Case c = modele.getCaseAt(x, y, false);
        if (c.contientBateau() && !c.contientBateauCoule() && c.estTiree()) {
            listeTirs.add(new Coordonnee(x, y));
        }

        //on vide la liste des tirs s'il ont servi à couler un bateau
        ArrayList<Coordonnee> listAEnlever = new ArrayList<Coordonnee>();
        for (Coordonnee coord : listeTirs) {
            if (modele.getCaseAt(coord.getX(), coord.getY(), false).contientBateauCoule()) {
                listAEnlever.add(coord);
            }
        }
        for (Coordonnee coord : listAEnlever) {
            listeTirs.remove(coord);
        }

        //on prevoit le prochain tire
        //si un bateau est en feu
        if (!listeTirs.isEmpty()) {
            //s'il y a une direction particuliere
            if (direction != Direction.AUCUNE) {
                if (!continuerDirection()) { //si on ne peut pas continuer la direction
                    //cela veut dire qu'il il ya des bateaux à cote
                    //on teste si on peut tirer a gauche, a droite, en haut ou en bas
                    testGDHB();
                }
            } else {
                //si pas de direction particuliere
                //on teste si on peut tirer a gauche, a droite, en haut ou en bas
                testGDHB();
            }
        }
    }

    /**
     * permet de tester si on peut continuer à tirer dans la direction actuelle
     *
     * @return true si on peut, false sinon
     */
    private boolean continuerDirection() {
        boolean res = false;
        //on se base sur le premier tir
        x = listeTirs.get(0).getX();
        y = listeTirs.get(0).getY();

        //on essaie de continuer le sens d'abord
        //en partant de la premiere case touchée et de maniere itérative
        //selon la direction on teste si on est pas au bord du jeu et si la case
        //sur laquelle on veut tirer ne l'est pas deja, si c'est le cas on essaye
        //de tirer de l'autre cote et si on ne peut pas, on renvoie false
        if (direction == Direction.HORIZONTALE) {
            if (sens == Sens.GAUCHE) {
                if (x > 0) {
                    x--;
                    while (x > 0 && modele.getCaseAt(x, y, false).estTiree() && modele.getCaseAt(x, y, false).contientBateau() && !modele.getCaseAt(x, y, false).contientBateauCoule()) {
                        x--;
                    }
                }
                if (modele.getCaseAt(x, y, false).estTiree()) {
                    sens = Sens.DROITE;
                    x = listeTirs.get(0).getX();
                    y = listeTirs.get(0).getY();
                    if (x < 9) {
                        x++;
                        while (x < 9 && modele.getCaseAt(x, y, false).estTiree() && modele.getCaseAt(x, y, false).contientBateau() && !modele.getCaseAt(x, y, false).contientBateauCoule()) {
                            x++;
                        }
                    }
                    if (modele.getCaseAt(x, y, false).estTiree()) {
                        sens = Sens.AUCUN;
                        direction = Direction.AUCUNE;
                    } else {
                        res = true;
                    }
                } else {
                    res = true;
                }
            } else { //sens droite
                if (x < 9) {
                    x++;
                    while (x < 9 && modele.getCaseAt(x, y, false).estTiree() && modele.getCaseAt(x, y, false).contientBateau() && !modele.getCaseAt(x, y, false).contientBateauCoule()) {
                        x++;
                    }
                }
                if (modele.getCaseAt(x, y, false).estTiree()) {
                    x = listeTirs.get(0).getX();
                    y = listeTirs.get(0).getY();
                    sens = Sens.GAUCHE;
                    if (x > 0) {
                        x--;
                        while (x > 0 && modele.getCaseAt(x, y, false).estTiree() && modele.getCaseAt(x, y, false).contientBateau() && !modele.getCaseAt(x, y, false).contientBateauCoule()) {
                            x--;
                        }
                    }
                    if (modele.getCaseAt(x, y, false).estTiree()) {
                        sens = Sens.AUCUN;
                        direction = Direction.AUCUNE;
                    } else {
                        res = true;
                    }
                } else {
                    res = true;
                }
            }
        } else { //direction verticale
            if (sens == Sens.HAUT) {
                if (y > 0) {
                    y--;
                    while (y > 0 && modele.getCaseAt(x, y, false).estTiree() && modele.getCaseAt(x, y, false).contientBateau() && !modele.getCaseAt(x, y, false).contientBateauCoule()) {
                        y--;
                    }
                }
                if (modele.getCaseAt(x, y, false).estTiree()) {
                    sens = Sens.BAS;
                    if (y < 9) {
                        y++;
                        while (y < 9 && modele.getCaseAt(x, y, false).estTiree() && modele.getCaseAt(x, y, false).contientBateau() && !modele.getCaseAt(x, y, false).contientBateauCoule()) {
                            y++;
                        }
                    }
                    if (modele.getCaseAt(x, y, false).estTiree()) {
                        sens = Sens.AUCUN;
                        direction = Direction.AUCUNE;
                    } else {
                        res = true;
                    }
                } else {
                    res = true;
                }
            } else { //sens bas
                if (y < 9) {
                    y++;
                    while (y < 9 && modele.getCaseAt(x, y, false).estTiree() && modele.getCaseAt(x, y, false).contientBateau() && !modele.getCaseAt(x, y, false).contientBateauCoule()) {
                        y++;
                    }
                }
                if (modele.getCaseAt(x, y, false).estTiree()) {
                    sens = Sens.HAUT;
                    if (y > 0) {
                        y--;
                        while (y > 0 && modele.getCaseAt(x, y, false).estTiree() && modele.getCaseAt(x, y, false).contientBateau() && !modele.getCaseAt(x, y, false).contientBateauCoule()) {
                            y--;
                        }
                    }
                    if (modele.getCaseAt(x, y, false).estTiree()) {
                        sens = Sens.AUCUN;
                        direction = Direction.AUCUNE;
                    } else {
                        res = true;
                    }
                } else {
                    res = true;
                }
            }
        }
        return res;
    }

    /**
     * on teste si on peut tirer autour de la premiere case tiree
     */
    private void testGDHB() {
        //test tir autour de la premiere case tirée et mise en place de la direction et du sens
        x = listeTirs.get(0).getX();
        y = listeTirs.get(0).getY();
        if (!testGauche()) {
            if (!testDroite()) {
                if (!testHaut()) {
                    testBas();
                }
            }
        }
    }

    /**
     * on teste si on peut tirer à gauche de la permiere case tiree
     *
     * @return vrai si on peut, false sinon
     */
    private boolean testGauche() {
        boolean res = false;
        if (x > 0) {
            if (!modele.getCaseAt(x - 1, y, false).estTiree()) {
                x--;
                res = true;
                direction = Direction.HORIZONTALE;
                sens = Sens.GAUCHE;
            } else {
                res = false;
            }
        }
        return res;
    }

    /**
     * on teste si on peut tirer à droite de la permiere case tiree
     *
     * @return vrai si on peut, false sinon
     */
    private boolean testDroite() {
        boolean res = false;
        if (x < 9) {
            if (!modele.getCaseAt(x + 1, y, false).estTiree()) {
                x++;
                res = true;
                direction = Direction.HORIZONTALE;
                sens = Sens.DROITE;

            } else {
                res = false;
            }
        }
        return res;
    }

    /**
     * on teste si on peut tirer en haut de la permiere case tiree
     *
     * @return vrai si on peut, false sinon
     */
    private boolean testHaut() {
        boolean res = false;
        if (y > 0) {
            if (!modele.getCaseAt(x, y - 1, false).estTiree()) {
                y--;
                res = true;
                direction = Direction.VERTICALE;
                sens = Sens.HAUT;
            } else {
                res = false;
            }
        }
        return res;
    }

    /**
     * on teste si on peut tirer en bas de la permiere case tiree
     *
     * @return vrai si on peut, false sinon
     */
    private boolean testBas() {
        boolean res = false;
        if (y < 9) {
            if (!modele.getCaseAt(x, y + 1, false).estTiree()) {
                y++;
                res = true;
                direction = Direction.VERTICALE;
                sens = Sens.BAS;
            } else {
                res = false;
            }
        }
        return res;
    }

    /**
     * reinitialise l'IA
     */
    @Override
    public void reset() {
        x = 0;
        y = 0;
        direction = Direction.AUCUNE;
        sens = Sens.AUCUN;
        listeTirs = new ArrayList<Coordonnee>();
    }

}
