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
    public void init(Joueur joueur, GameContainer container) {
        this.joueur = joueur;
        this.x = container.getWidth() / 2f;
        this.y = 0;
    }

    /**
     * Mise a jour de la camera
     * Fréquence de MaJ
     */
    public void update(GameContainer container, int delta) {

        int middle = container.getWidth() / 4;

        boolean limiteGauche = joueur.getX() - container.getWidth() / 2f <= container.getWidth();
        boolean limiteDroite = joueur.getX() - container.getWidth() / 2f >= 0;
        if ( limiteDroite && limiteGauche ) {



            /* Mise a jours du centre de la camera */
            x = joueur.getX();
            y = 0;

//            if (joueur.getX() > this.x + middle) this.x = joueur.getX() - middle;
//            if (joueur.getX() < this.x - middle) this.x = joueur.getX() + middle;
        }

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
                            0 );    // place le y de la camera en haut
    }
}
