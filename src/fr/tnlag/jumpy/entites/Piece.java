/*
 * Piece.java             08/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.entites;

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

    private boolean estActive;

    private float x,
                  y;

    private Animation animation;

    private static final int TAILLE_PIECE = 32;

    /**
     * Créer une nouvelle pièce
     */
    public Piece(float newX, float newY) throws SlickException {
        this.x = newX;
        this.y = newY;

        SpriteSheet feuilleSprite = new SpriteSheet("ressource/sprite/piece.png", 32, 32);
        animation = new Animation();
        animation.addFrame(feuilleSprite.getSprite(0, 0), 150);
        animation.addFrame(feuilleSprite.getSprite(1, 0), 150);
        animation.addFrame(feuilleSprite.getSprite(2, 0), 150);

    }

    public void render(Graphics g) {
        System.out.println(x);
        System.out.println(y);
        g.drawAnimation(animation, x, y);
    }
}
