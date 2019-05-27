/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.modele;

/**
 *
 * @author boris
 */
public abstract class AbstractIA {
    
    protected Jeu jeu;
    
    public AbstractIA(Jeu j){
        jeu = j;
    }
    
    public abstract void tirerSurUneCase();
    public abstract void reset();
    
}
