/*
 * PlayerController.java             06/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.physique;

import fr.tnlag.jumpy.entites.Joueur;
import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;


/**
 * Contrôle du joueur (clavier et manette)
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class PlayerController implements KeyListener, ControllerListener{
     Joueur joueur;

     public PlayerController(Joueur joueur) {
         this.joueur = joueur;
     }

    @Override
    public void keyPressed(int touche, char c) {
        switch (touche) {

            case Input.KEY_LEFT:
                joueur.setDirection(Joueur.GAUCHE);
                joueur.setMouvig(true);
                break;

            case Input.KEY_RIGHT:
                joueur.setDirection(Joueur.DROITE);
                joueur.setMouvig(true);
                break;

            case Input.KEY_UP:
                joueur.saut();
                break;
        }
    }

    @Override
    public void keyReleased(int touche, char c) {

         /*
            Le movement est stoppé uniquement si la touche relâché est la touche qui permet d' aller dans la direction
            Ex : si la touche droite est relâcher alors le mouvement serra stoppé uniquement si le joueur va
            vers la droite
          */
        
        switch (touche) {

            case Input.KEY_LEFT:
                joueur.setMouvig(joueur.getDirection() != Joueur.GAUCHE);
                break;

            case Input.KEY_RIGHT:
                joueur.setMouvig(joueur.getDirection() != Joueur.DROITE);
                break;

            case Input.KEY_UP:
                joueur.saut();
                break;
        }
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }

    @Override
    public void controllerLeftPressed(int i) {
        joueur.setDirection(Joueur.GAUCHE);
        joueur.setMouvig(true);
    }

    @Override
    public void controllerLeftReleased(int i) {
        joueur.setMouvig(false);
    }

    @Override
    public void controllerRightPressed(int i) {
        joueur.setDirection(Joueur.DROITE);
        joueur.setMouvig(true);
    }

    @Override
    public void controllerRightReleased(int i) {
        joueur.setMouvig(false);
    }

    @Override
    public void controllerUpPressed(int i) {

    }

    @Override
    public void controllerUpReleased(int i) {

    }

    @Override
    public void controllerDownPressed(int i) {

    }

    @Override
    public void controllerDownReleased(int i) {

    }

    @Override
    public void controllerButtonPressed(int i, int j) {
        if (j == 1) {
            joueur.saut();
        }
    }

    @Override
    public void controllerButtonReleased(int i, int j) {

    }
}
