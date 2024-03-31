/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.modele.IA;

/**
 * une classe avec 2 membres x et y
 *
 * @author boris
 */
public class Coordonnee {

    protected int x;
    protected int y;

    /**
     * le constructeur
     *
     * @param x la coordonnee en x
     * @param y la coordonnee en y
     */
    public Coordonnee(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return la coordonnee en x
     */
    public int getX() {
        return x;
    }

    /**
     * setter
     *
     * @param x la coordonnee en x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return la coordonnee en y
     */
    public int getY() {
        return y;
    }

    /**
     * setter
     *
     * @param y la coordonnee en y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * reinitialise les coordonnees a 0
     */
    public void reset() {
        this.x = 0;
        this.y = 0;
    }

}
