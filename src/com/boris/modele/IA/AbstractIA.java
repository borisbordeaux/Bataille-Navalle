/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.modele.IA;

import com.boris.modele.Jeu;

/**
 *
 * @author boris
 */
public abstract class AbstractIA extends Coordonnee{
    
    protected Jeu jeu;
    
    public AbstractIA(Jeu j){
        super(0,0);
        jeu = j;
    }
    
    public abstract void tirerSurUneCase();
    public void reset(){
        super.reset();
    }
    
}
