/*
 * HitBox.java             08/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.gameState.physique;

import org.newdawn.slick.Graphics;

/**
 * Boite de colision des entitées
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class HitBox {

    private float x, // Coordonnée coins supérieur gauche
                         y; // Coordonnée coins supérieur gauche

    private float height, // Hauteur
                         width;  // Longueur

    /**
     * Créer une nouvelle hit box
     * @param x position du coin sup gauche de la hit box
     * @param y position du coin sup gauche de la hit box
     */
    public HitBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * @param hitBox qui entre en collision avec cet objet
     * @return true si colision false sinon
     */
    public boolean isCollision(HitBox hitBox) {
        boolean hitBoxIn = x <= hitBox.getX() && hitBox.getX() <= (x+ width)
                            && y <= hitBox.getY() && hitBox.getY() <= (y + height);


        boolean thisIn = hitBox.getX() <= x && x <= (hitBox.getX()+32f)
                            && hitBox.getY() <= y && y <= (hitBox.getY()+32f);

        // TODO comprendre pourquoi elle renvoie toujour true

        return (thisIn || hitBoxIn);
    }

    public void render(Graphics graphics) {
        graphics.drawRect(x, y, width, height);
    }

    public void update(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
