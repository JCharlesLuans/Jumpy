/*
 * Piece.java             08/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.gameState.entites;

import fr.tnlag.jumpy.gameState.physique.HitBox;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Pièce qui permet d' augmenter le score du joueur.
 * Au contact de celui si la pièce est détruite, et le score du joueur augmente de 1
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Piece {

    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private boolean active;

    private float x,  // Position du coin gauche supérieur
                  y;  // Position du coin gauche supérieur

    private Animation animation; // Animation de l'objet

    private HitBox hitBox;

    /**
     * Créer une nouvelle pièce
     */
    public Piece(float newX, float newY) throws SlickException {

        this.x = newX;
        this.y = newY;

        active = true; // Affiche et active la pièce

        SpriteSheet feuilleSprite = new SpriteSheet("ressource/sprite/piece.png", WIDTH, HEIGHT);
        animation = new Animation();
        animation.addFrame(feuilleSprite.getSprite(0, 0), 150);
        animation.addFrame(feuilleSprite.getSprite(1, 0), 150);
        animation.addFrame(feuilleSprite.getSprite(2, 0), 150);

        hitBox = new HitBox(x, y, WIDTH, HEIGHT);

    }

    public void render(Graphics g) {
        if (active)
            g.drawAnimation(animation, x, y);
    }

    /**
     * @return la position du point gauche sur l'axe des absisses
     */
    public float getX() {
        return x;
    }

    /**
     * @return la position sur l'axe des ordonnées
     */
    public float getY() {
        return y;
    }

    /**
     * Active ou non la piece
     */
    public void setActive(boolean activer) {
        this.active = activer;
    }

    /**
     * @return  estActive
     */
    public boolean isActive() {
        return this.active;
    }

    public HitBox getHitBox() {
        return hitBox;
    }
}
