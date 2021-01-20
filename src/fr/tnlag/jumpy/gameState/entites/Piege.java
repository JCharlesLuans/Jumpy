/*
 * Jean-Charles Luans                       20/01/2021
 * Copyright et copyleft TNL-Corp
 */
package fr.tnlag.jumpy.gameState.entites;

import fr.tnlag.jumpy.gameState.physique.HitBox;

/**
 * Piege existant sur une carte
 */
public class Piege {

    private int height,
                width;

    private int x,
                y;

    private HitBox hitBox;

    /**
     * Création d'un nouveau piege sur la map
     * @param x position coin gauche supperieur
     * @param y position coin gauche supperieur
     * @param width largeur
     * @param height hauteur
     */
    public Piege(int x,int y,int width,int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        hitBox = new HitBox(x, y, width, height);
    }

    /**
     * @return la hitBox du piege
     */
    public HitBox getHitBox() {
        return hitBox;
    }
}
