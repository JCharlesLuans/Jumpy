/*
 * Camera.java             07/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.gameState.entites;

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

    private int maxWidth,
                maxHeight;

    /**
     * Initialise la camera qui serra centré sur les paramètres
     * @param container : fenètre de jeu
     * @param joueur    : joueur que dois suivre la camera
     * @param maxWidth  : width  maximal de la map
     * @param maxHeight : height maximal de la map
     */
    public Camera(GameContainer container, Joueur joueur, int maxWidth, int maxHeight) {

        this.maxHeight = maxHeight * Map.TAILLE_TUILLE;
        this.maxWidth = maxWidth * Map.TAILLE_TUILLE;

        this.joueur = joueur;
        this.x = container.getWidth() / 2f;
        this.y = 0;
    }

    /**
     * Mise a jour de la camera
     * Fréquence de MaJ
     */
    public void update(GameContainer container, int delta) {

        boolean limiteGauche = joueur.getX() + container.getWidth() / 2f <= maxWidth;
        boolean limiteDroite = joueur.getX() - container.getWidth() / 2f >= 0;
        if ( limiteDroite && limiteGauche ) {

            /* Mise a jours du centre de la camera */
            x = joueur.getX();
            y = 0;

        }

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
