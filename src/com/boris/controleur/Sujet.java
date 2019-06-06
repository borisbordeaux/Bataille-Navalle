/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.controleur;

import com.boris.vue.Observateur;
import java.util.ArrayList;

/**
 *
 * @author boris
 */
public class Sujet {
    
    private ArrayList<Observateur> obs = new ArrayList<Observateur>();
    
    public void abonne(Observateur o){
        obs.add(o);
    }
    
    public void notifie(){
        for(Observateur o : obs){
            o.miseAJour();
        }
    }
    
}
