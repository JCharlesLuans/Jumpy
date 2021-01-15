/*
 * MobHostile.java             08/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.gameState.entites;

import fr.tnlag.jumpy.gameState.physique.HitBox;
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

    private static final float VITESSE = 0.25f;

    private static final int WIDTH_SPRITE  = 32;
    private static final int HEIGHT_SPRITE = 32;

    /* ------------------------------------------------------------------------------------------ */

    private Map mapActuelle; // Map sur laquelle évolue le PNJ

    private Animation[] listeAnimation = new Animation[2];

    int direction;  // Direction dans laquel se déplace le PNJ

    private float x, // Position en x du personnage non joueur
                  y; // Position en y du personnage non joueur

    private boolean auSol;

    private HitBox hitBox; // Hit box du PNJ

    private boolean active; // Indique si le PNJ est actif ou pas

    /* --------------------------------------------------------------------------- */

    public MobHostile(Map map, float x, float y) throws SlickException {

        this.mapActuelle = map;

        /* Direction par défaut du personnage non joueur */
        direction = GAUCHE;

        this.x = x;
        this.y = y;

        /* Initialisation du tableau des animation */
        for (int i = 0; i < 2; i++) {
            listeAnimation[i] = new Animation();
        }
        // Chargement du sprite
        SpriteSheet feuilleSprite = new SpriteSheet("/ressource/sprite/mobHostile.png", WIDTH_SPRITE, HEIGHT_SPRITE);

        // Chargement de l'animation
        listeAnimation[0].addFrame(feuilleSprite.getSprite(0, 0), 100);
        listeAnimation[0].addFrame(feuilleSprite.getSprite(1, 0), 100);
        listeAnimation[0].addFrame(feuilleSprite.getSprite(2, 0), 100);

        listeAnimation[1].addFrame(feuilleSprite.getSprite(0, 1), 100);
        listeAnimation[1].addFrame(feuilleSprite.getSprite(1, 1), 100);
        listeAnimation[1].addFrame(feuilleSprite.getSprite(2, 1), 100);

        // Initialisation de la hit box
        hitBox = new HitBox(x, y, WIDTH_SPRITE, HEIGHT_SPRITE);

        // Activation du mob
        active = true;

    }

    public void changementSens() {
        if (direction == DROITE) {
            direction = GAUCHE;
        } else {
            direction = DROITE;
        }
    }

    /**
     * Déplacement du mobs
     * @param delta
     * @param limite
     */
    private void mouvementHorizontal(int delta, int limite) {

        float futurX = x;   // X aprés mouvement du joueur

        /* Déplacement vers la droite ou vers la gauche */

        if (direction == DROITE) {
            // DEBUG
            // System.out.println("Vers la droite");
            futurX += VITESSE * delta;
        } else {
            // DEBUG
            // System.out.println("Vers la gauche");
            futurX -= VITESSE * delta;
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

        // Evite les troue dans la map
        if (!mapActuelle.isCollision(futurX, y)) {
            this.changementSens();
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

        if (active) {
            int mouvement = 0; // Numéro du mouvement a effectuer dans la liste d' animation

            mouvement = direction == DROITE ? 0 : 1; // Affichage du mouvement en fonction de la direction

            g.drawAnimation(listeAnimation[mouvement], x, y);
        }

    }

    public void update(int delta, int limite) {
        mouvementHorizontal(delta, limite);
        hitBox.update(x, y);
        gravity(delta);
    }

    public HitBox getHitBox() {
        return hitBox;
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

    public void setActive(boolean active) {this.active = active;}

    public boolean isActive() {return active;}
}
