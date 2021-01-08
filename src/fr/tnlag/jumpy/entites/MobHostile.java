/*
 * MobHostile.java             08/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.entites;

import org.newdawn.slick.*;

/**
 * Personnage non joueur hostile. Une dizaine spawn aléatoirement dans la map
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MobHostile {

    public static final int DROITE = 1;
    public static final int GAUCHE = -1;

    private Animation[] listeAnimation = new Animation[2];

    int direction;

    private float x, // Position en x du personnage
            y; // Position en y du personnage

    private boolean auSol;

    public MobHostile(float x, float y) throws SlickException {

        /* Direction par défaut du personnage non joueur */
        direction = GAUCHE;

        this.x = x;
        this.y = y;

        /* Initialisation du tableau des animation */
        for (int i = 0; i < 2; i++) {
            listeAnimation[i] = new Animation();
        }
        // Chargement du sprite
        SpriteSheet feuilleSprite = new SpriteSheet("/ressource/sprite/mobHostile.png", 32, 32);

        // Chargement de l'animation
        listeAnimation[0].addFrame(feuilleSprite.getSprite(0, 0), 100);
        listeAnimation[0].addFrame(feuilleSprite.getSprite(1, 0), 100);
        listeAnimation[0].addFrame(feuilleSprite.getSprite(2, 0), 100);

        listeAnimation[1].addFrame(feuilleSprite.getSprite(0, 1), 100);
        listeAnimation[1].addFrame(feuilleSprite.getSprite(1, 1), 100);
        listeAnimation[1].addFrame(feuilleSprite.getSprite(2, 1), 100);

    }


    private void mouvementHorizontal(int delta, int limite) {

        float futurX = x;   // X aprés mouvement du joueur

        /* Déplacement vers la droite ou vers la gauche */

        if (direction == DROITE) {
            // DEBUG
            // System.out.println("Vers la droite");
            futurX += .005f * delta;
        } else {
            // DEBUG
            // System.out.println("Vers la gauche");
            futurX -= .005f * delta;
        }

        // Limite de la map (0 : premiere coordonnée, si inf a 0, out of range car personnage hors de la map)
        if (futurX > 0) {
            if (futurX < limite) {
                this.x = futurX;
            } else {
                direction = GAUCHE;
            }
        } else {
            direction = DROITE;
        }

    }

    /**
     * Applique la gravité a ce mob
     *
     * @param delta
     */
    private void gravity(int delta) {
        if (!this.auSol) {
            this.y += .2f * delta;
        }
    }

    /**
     * Affichage du joueur
     *
     * @param g graphic
     * @throws SlickException
     */
    public void render(Graphics g) throws SlickException {

        int mouvement = 0; // Numéro du mouvement a effectuer dans la liste d'animation

        mouvement = direction == DROITE ? 0 : 1;

        // DEBUG
        //System.out.println("Direction : " + direction);
        //System.out.println("Mouvement : " + mouvement);

        g.drawAnimation(listeAnimation[mouvement], x, y);
    }

    public void update(int delta, int limite) {
        mouvementHorizontal(delta, limite);
        gravity(delta);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setAuSol(boolean auSol) {
        this.auSol = auSol;
    }
}
