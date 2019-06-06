/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.controleur;

import com.boris.modele.IA.AbstractIA;
import com.boris.modele.Jeu;

/**
 *
 * @author boris
 */
public class Controleur extends Sujet {
    
    private Jeu modele;
    private AbstractIA IA;
    
    public Controleur(Jeu j, AbstractIA ia){
        modele = j;
        this.IA = ia;
    }
    
    public void placerBateau(boolean forIA){
        modele.placerBateau(forIA);
        this.notifie();
    }
    
    public void tirerCase(int x, int y, boolean forIA){
        modele.tirerSurCase(x, y, forIA);
        if (!forIA){
            IA.tirerSurUneCase();
        }
        
        this.notifie();
    }
    
    public void resetPartie(){
        modele.resetPartie();
        IA.reset();
        this.notifie();
    }
    
}
