/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.modele;

import java.util.Random;

/**
 * Le jeu, c'est la que l'on va agencer toutes les cases et derouler la partie
 *
 * @author boris
 */
public final class Jeu {

    //les 2 premieres dimension pour le plateau et la derniere pour savoir de
    //quel joueur il s'agit (0: joueur ; 1 : IA)
    private Case cases[][][];

    //La premiere dimension pour les bateaux et la deuxieme pour savoir de quel
    //joueur il s'agit (0: joueur ; 1 : IA)
    private Bateau bateaux[][];

    //le gagnant
    private Gagnant gagnant;

    //indique si le joueur a place ses bateau
    private boolean bateauxJoueurPlaces;

    //indique le nombre de bateaux que l'utilisateur a place
    private int nbBateauxJoueursPlaces;

    /**
     * initialise le jeu
     */
    public Jeu() {
        cases = new Case[10][10][2];
        bateaux = new Bateau[5][2];
        gagnant = Gagnant.PERSONNE;
        bateauxJoueurPlaces = false;
        nbBateauxJoueursPlaces = 0;

        for (int i = 0; i < 2; i++) {
            bateaux[0][i] = new Bateau("Porte-Avion", 5);
            bateaux[1][i] = new Bateau("Croiseur", 4);
            bateaux[2][i] = new Bateau("Contre-Torpilleur", 3);
            bateaux[3][i] = new Bateau("Sous-marin", 3);
            bateaux[4][i] = new Bateau("Torpilleur", 2);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 2; k++) {
                    cases[i][j][k] = new Case();
                }
            }
        }

        //on place les bateau de l'ia
        placerBateau(true);
    }

    /**
     * permet de tirer sur une case
     *
     * @param x la coordonnee x de la case
     * @param y la coordonnee y de la case
     * @param forIA si true, fait tirer l'IA
     */
    public void tirerSurCase(int x, int y, boolean forIA) {
        if (gagnant == Gagnant.PERSONNE) {
            cases[x][y][forIA ? 0 : 1].tirer();
            controleGagnant();
        }
    }

    /**
     * permet de recuperer une case du jeu
     *
     * @param x la coordonnee x de la case
     * @param y la coordonnee y de la case
     * @param forIA si true, renvoie une case de l'ia, sinon renvoie une case du
     * joueur
     * @return un objet Case
     */
    public Case getCaseAt(int x, int y, boolean forIA) {
        return cases[x][y][forIA ? 1 : 0];
    }

    /**
     * place les bateaux aléatoirement
     *
     * @param forIA indique si on veut placer les bateaux pour l'ia, sinon c'est
     * que c'est pour le joueur
     */
    public void placerBateau(boolean forIA) {
        int joueur = forIA ? 1 : 0;
        if (!forIA) {
            bateauxJoueurPlaces = true;
        }

        //generateur aleatoire
        Random r = new Random();

        //pour chaque bateau
        for (int i = 0; i < 5; i++) {
            //vertical ou horizontal
            boolean vertical = r.nextBoolean();
            //si vertical
            if (vertical) {
                //on recupere des coordonnees aleatoires
                //qui permettent de placer le bateau dans la grille
                int x = r.nextInt(10 - 1);
                int y = r.nextInt(10 - bateaux[i][joueur].getNbCase());

                //on doit faire des verifications et ajuster en cas
                //de probleme : on ne peut pas mettre un bateau sur un autre
                //on commence par ajuster la coordonnee en x
                //on deplace le bateau si besoin
                boolean xIncorrect = false;
                int tempX = x;
                do {
                    xIncorrect = false;
                    for (int j = 0; j < bateaux[i][joueur].getNbCase(); j++) {
                        if (cases[tempX][y + j][joueur].contientBateau()) {
                            xIncorrect = true;
                        }
                    }
                    if (xIncorrect) {
                        tempX++;
                        tempX %= 10;
                        if (tempX == x) {
                            y++;
                            y %= 10;
                        }
                    }
                } while (xIncorrect);
                x = tempX;

                for (int j = 0; j < bateaux[i][joueur].getNbCase(); j++) {
                    bateaux[i][joueur].ajouterCase(cases[x][y + j][joueur]);
                }
            } else { //horizontal
                int x = r.nextInt(10 - bateaux[i][joueur].getNbCase());
                int y = r.nextInt(10 - 1);

                //on doit faire des verifications et ajuster en cas
                //de probleme : on ne peut pas mettre un bateau sur un autre
                //on commence par ajuster la coordonnee en y
                //on deplace le bateau si besoin
                boolean yIncorrect = false;
                int tempY = y;
                do {
                    yIncorrect = false;
                    for (int j = 0; j < bateaux[i][joueur].getNbCase(); j++) {
                        if (cases[x + j][tempY][joueur].contientBateau()) {
                            yIncorrect = true;
                        }
                    }
                    if (yIncorrect) {
                        tempY++;
                        tempY %= 10;
                        if (tempY == y) {
                            x++;
                            x %= 10;
                        }
                    }
                } while (yIncorrect);
                y = tempY;

                for (int j = 0; j < bateaux[i][joueur].getNbCase(); j++) {
                    bateaux[i][joueur].ajouterCase(cases[x + j][y][joueur]);
                }
            }
        }
    }

    /**
     * permet de savoir s'il y a un gagnant
     */
    private void controleGagnant() {
        for (int i = 0; i < 2; i++) {
            boolean estPerdant = true;
            for (int j = 0; j < 5; j++) {
                if (!this.bateaux[j][i].estCoule()) {
                    estPerdant = false;
                }
            }
            if (estPerdant) {
                gagnant = i == 0 ? Gagnant.IA : Gagnant.JOUEUR;
            }
        }
    }

    /**
     * indique le gagnant
     *
     * @return le gagnant de la partie
     */
    public Gagnant getGagnant() {
        return gagnant;
    }

    /**
     * reinitialise la partie
     */
    public void resetPartie() {
        gagnant = Gagnant.PERSONNE;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                bateaux[j][i].reset();
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 2; k++) {
                    cases[i][j][k].reset();
                }
            }
        }

        nbBateauxJoueursPlaces = 0;
        this.bateauxJoueurPlaces = false;

        //place les bateaux de l'ia
        placerBateau(true);

        //affiche les grilles du jeu
        System.out.println(this);
    }

    /**
     * @return les grilles des 2 joueurs l'une apres l'autre
     */
    @Override
    public String toString() {
        String res = "Bateaux du joueur\n";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                res += cases[j][i][0] + " ";
            }
            res += "\n";
        }
        res += "\nBateaux de l'IA\n";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                res += cases[j][i][1] + " ";
            }
            res += "\n";
        }

        return res;
    }

    /**
     * indique si on peut placer un bateau
     *
     * @param x la coordonnee x de la premiere case du bateau
     * @param y la coordonnee y de la premiere case du bateau
     * @param tailleBateau la taille du bateau
     * @param horizontal l'orientation du bateau
     * @return vrai si on peut placer le bateau
     */
    public boolean peutPlacerBateauJoueur(int x, int y, int tailleBateau, boolean horizontal) {
        boolean res = true;
        if (horizontal) {
            if (x + tailleBateau <= 10) {
                for (int i = x; i < x + tailleBateau; i++) {
                    if (cases[i][y][0].contientBateau()) {
                        res = false;
                    }
                }
            } else {
                res = false;
            }
        } else {
            if (y + tailleBateau <= 10) {
                for (int i = y; i < y + tailleBateau; i++) {
                    if (cases[x][i][0].contientBateau()) {
                        res = false;
                    }
                }
            } else {
                res = false;
            }
        }
        return res;
    }

    /**
     * permet de placer le bateau du joueur
     *
     * @param x la coordonnee x de la premiere case du bateau
     * @param y la coordonnee y de la premiere case du bateau
     * @param tailleBateau la taille du bateau
     * @param horizontal l'orientation du bateau
     */
    public void placerBateauJoueur(int x, int y, int tailleBateau, boolean horizontal) {
        if (peutPlacerBateauJoueur(x, y, tailleBateau, horizontal)) {
            if (horizontal) {
                for (int i = x; i < x + tailleBateau; i++) {
                    cases[i][y][0].mettreBateau();
                    bateaux[nbBateauxJoueursPlaces][0].ajouterCase(cases[i][y][0]);
                }
            } else {
                for (int i = y; i < y + tailleBateau; i++) {
                    cases[x][i][0].mettreBateau();
                    bateaux[nbBateauxJoueursPlaces][0].ajouterCase(cases[x][i][0]);
                }
            }
            nbBateauxJoueursPlaces++;
            if (nbBateauxJoueursPlaces == 5) {
                this.bateauxJoueurPlaces = true;
            } else {
                this.bateauxJoueurPlaces = false;
            }
        }
    }

    /**
     * @return the bateauxJoueurPlaces
     */
    public boolean bateauxJoueurPlaces() {
        return bateauxJoueurPlaces;
    }

    /**
     * @return the nbBateauxJoueursPlaces
     */
    public int getNbBateauxJoueursPlaces() {
        return nbBateauxJoueursPlaces;
    }
}
