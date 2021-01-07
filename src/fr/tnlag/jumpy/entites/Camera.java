/*
 * Camera.java             07/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.entites;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Caméra du joueur. Permet de faire défiler le décor et la map derriere le joueur.
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Camera {

    private float x, // Position en x du point au centre de l' écran
                  y; // Position en y du point au centre de l' écran

    private Joueur joueur; // Joueur que suis la camera


    /**
     * Création de la camera
     */
    public Camera() {
        x = y = 0;
    }

    /**
     * Initialise la camera qui serra centré sur les paramètres
     * @param joueur : joueur que dois suivre la camera
     */
    public void init(Joueur joueur) {
        this.joueur = joueur;
        this.x = joueur.getX();
        this.y = joueur.getY();
    }

    /**
     * Mise a jour de la camera
     * Fréquence de MaJ
     */
    public void update(int delta) {
        x = joueur.getX();
        y = joueur.getY();

        // DEBUG
        // System.out.println("X = " + x + "\nY = " + y );
    }

    /**
     * Affichage de la camera
     * @param graphics
     * @param gameContainer
     */
    public void render(GameContainer gameContainer , Graphics graphics) throws SlickException {
        graphics.translate(gameContainer.getWidth() / 2f - x, // place le x de la camera au centre
                            0 );    // place le y de la camera au centre
    }
}
