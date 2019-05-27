/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.modele;

import java.util.Random;

/**
 *
 * @author boris
 */
public class Jeu {

    /**
     * les 2 premieres dimension pour le plateau et la derniere pour savoir de
     * quel joueur il s'agit (0: joueur ; 1 : IA)
     */
    private Case cases[][][];
    /**
     * La premiere dimension pour les bateaux et la deuxieme pour savoir de quel
     * joueur il s'agit (0: joueur ; 1 : IA)
     */
    private Bateau bateaux[][];
    
    private Gagnant gagnant;

    /**
     * initialise le jeu
     */
    public Jeu() {
        cases = new Case[10][10][2];
        bateaux = new Bateau[5][2];
        gagnant = Gagnant.PERSONNE;

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

    }

    public void tirerSurCase(int x, int y, boolean forIA) {
        if (gagnant == Gagnant.PERSONNE) {
            cases[x][y][forIA ? 0 : 1].tirer();
            controleGagnant();
        }
    }

    public Case getCasesAt(int x, int y, boolean forIA) {
        return cases[x][y][forIA ? 1 : 0];
    }

    /**
     * place les bateaux de l'IA aléatoirement
     *
     * @param forIA indique si on veut placer les bateaux pour l'ia, sinon c'est
     * que c'est pour le joueur
     */
    public void placerBateau(boolean forIA) {

        int joueur = forIA ? 1 : 0;

        System.out.println("debut placement bateau " + (forIA ? "IA" : "Joueur"));

        //pour chaque bateau
        for (int i = 0; i < 5; i++) {
            System.out.println("bateau numero " + i + " " + bateaux[i][joueur]);
            //generateur aleatoire
            Random r = new Random();
            //vertical ou horizontal
            boolean vertical = r.nextBoolean();

            //System.out.println("vertical ? " + vertical);
            //si vertical
            if (vertical) {
                //on recupere des coordonnees aleatoires
                //qui permettent de placer le bateau dans la grille
                int x = r.nextInt(10 - 1);
                int y = r.nextInt(10 - bateaux[i][joueur].getNbCase());

                //System.out.println("estimé en x = " + x + " y = " + y);
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
                        //System.out.println("on augmente le x pour le bateau numero "+i);
                        if (tempX == x) {
                            //System.out.println("on augmente le y");
                            y++;
                            y %= 10;
                        }
                    }
                } while (xIncorrect);
                x = tempX;
                System.out.println("placé en x = " + x + " y = " + y);

                for (int j = 0; j < bateaux[i][joueur].getNbCase(); j++) {
                    bateaux[i][joueur].ajouterCase(cases[x][y + j][joueur]);
                }
            } else { //horizontal
                int x = r.nextInt(10 - bateaux[i][joueur].getNbCase());
                int y = r.nextInt(10 - 1);

                //System.out.println("estimé en x = " + x + " y = " + y);
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
                        //System.out.println("on augmente le y pour le bateau numero "+i);
                        if (tempY == y) {
                            //System.out.println("on augmente le x");
                            x++;
                            x %= 10;
                        }
                    }
                } while (yIncorrect);
                y = tempY;
                //System.out.println("placé en x = " + x + " y = " + y);

                for (int j = 0; j < bateaux[i][joueur].getNbCase(); j++) {
                    bateaux[i][joueur].ajouterCase(cases[x + j][y][joueur]);
                }
            }

        }
    }

    private void controleGagnant() {
        int perdant = -1;
        for (int i = 0; i < 2; i++) {
            boolean estPerdant = true;
            for (int j = 0; j < 5; j++) {
                if (!this.bateaux[j][i].estCoule()) {
                    estPerdant = false;
                }
            }
            if (estPerdant) {
                perdant = i;
            }
        }

        if (perdant == 0) {
            System.out.println("L'IA a gagné !");
            gagnant = Gagnant.IA;
        }
        if (perdant == 1) {
            System.out.println("Vous avez gagné !");
            gagnant = Gagnant.JOUEUR;
        }
    }

    public Gagnant getGagnant() {
        return gagnant;
    }

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
        
        placerBateau(true);
        placerBateau(false);
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

}
