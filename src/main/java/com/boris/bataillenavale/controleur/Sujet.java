/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.controleur;

import com.boris.bataillenavale.vue.Observateur;
import java.util.ArrayList;

/**
 * Sujet est un sujet d'observation utilisé dans le MVC. Il permet de mettre à
 * jour tous ses observateurs
 *
 * @author boris
 */
public class Sujet {

    private ArrayList<Observateur> obs = new ArrayList<Observateur>();

    public void abonne(Observateur o) {
        obs.add(o);
    }

    public void notifie() {
        for (Observateur o : obs) {
            o.miseAJour();
        }
    }

}
