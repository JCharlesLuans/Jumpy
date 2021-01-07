/*
 * fr.tnlag.jumpy.entites.Joueur.java             05/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.entites;

import org.newdawn.slick.*;

/**
 * Personnage joueur
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Joueur {

    public static final int DROITE = 1;
    public static final int GAUCHE = -1;

    public final float DISTANCE_SAUT = 128f;

    private int score;

    private float positionMax;
    private boolean jumping;  // Indicateur de saut

    private float x, // Position en x du personnage
                  y; // Position en y du personnage

    private int direction; // 1 -> Déplacement vers la droite | -1 déplacement vers la gauche

    private boolean mooving; // Indicateur du déplacement
    private boolean auSol;   // Indicateur pour l' application de la gravité


    private Animation[] listeAnimation = new Animation[4]; // Liste des animation du personnage

    /**
     * Création du joueur avec les paramètre par défaut indiquer
     */
    public Joueur() {

        /* Position par défaut du personnage joueur */
        x = 16;
        y = 448;
        positionMax = 0;

        /* Direction apr défaut du personnage joueur */
        direction = DROITE;
        
        /* Indicateur de mouvement */
        mooving = false;

        /* Initialisation du tableau des animation */
        for (int i = 0; i < 4; i++) {
            listeAnimation[i] = new Animation();
        }

        /* Initialisation du score */
        score = 0;
    }


    /**
     * Applique la gravité au personnage
     */
    private void gravity(int delta) {
        if (!this.auSol) {
            this.y += .2f * delta;
        }

    }

    /**
     * Initialisation du joueur
     * @param container conteneur du jeu dans le quel le joueur va évoluer
     * @throws SlickException
     */
    public void init(GameContainer container) throws SlickException {

        // Chargement du sprite
        SpriteSheet feuilleSprite = new SpriteSheet("/ressource/sprite/joueur.png", 32, 32);

        // Chargement de l'animation
        listeAnimation[0].addFrame(feuilleSprite.getSprite(0,0), 100);
        listeAnimation[0].addFrame(feuilleSprite.getSprite(1,0), 100);
        listeAnimation[0].addFrame(feuilleSprite.getSprite(2,0), 100);

        listeAnimation[1].addFrame(feuilleSprite.getSprite(0,1), 100);
        listeAnimation[1].addFrame(feuilleSprite.getSprite(1,1), 100);
        listeAnimation[1].addFrame(feuilleSprite.getSprite(2,1), 100);

        listeAnimation[2].addFrame(feuilleSprite.getSprite(0,2), 100);
        listeAnimation[2].addFrame(feuilleSprite.getSprite(1,2), 100);
        listeAnimation[2].addFrame(feuilleSprite.getSprite(2,2), 100);

        listeAnimation[3].addFrame(feuilleSprite.getSprite(0,3), 100);
        listeAnimation[3].addFrame(feuilleSprite.getSprite(1,3), 100);
        listeAnimation[3].addFrame(feuilleSprite.getSprite(2,3), 100);
    }

    /**
     * Fait bouger le joueur
     */
    private void mouvement(int delta) {

        float futurX = x;   // X aprés mouvement du joueur

        /* Déplacement vers la droite ou vers la gauche */
        if (this.mooving) {
            if (direction == DROITE) {
                // DEBUG
                // System.out.println("Vers la droite");
                futurX += .2f * delta;
            } else {
                // DEBUG
                // System.out.println("Vers la gauche");
                futurX -= .2f * delta;
            }
        }

        // Limite de la map (0 : premiere coordonnée, si inf a 0, out of range car personnage hors de la map)
        if (futurX > 0) {
            this.x = futurX;
        }
    }

    /**
     * Affichage du joueur
     * @param g graphic
     * @throws SlickException
     */
    public void render(Graphics g) throws SlickException {

        int mouvement = 0; // Numéro du mouvement a effectuer dans la liste d'animation

        mouvement = direction == DROITE ? 0 : 1;
        mouvement = auSol ? mouvement : mouvement + 2;

        // DEBUG
        //System.out.println("Direction : " + direction);
        //System.out.println("Mouvement : " + mouvement);

        g.drawAnimation(listeAnimation[mouvement],  x, y);
    }



    /**
     * Fait sauter le joueur
     */
    public void saut() {
        if (auSol) {
            this.jumping = true;
            positionMax = y - DISTANCE_SAUT;

            try {
                Music saut = new Music("/ressource/son/bond.wav");
                saut.play();
            } catch (Exception err) {
                System.out.println(err);
            }
        }
    }

    /**
     * Arret du joueur lorsque toute les touches sont relâchées
     */

    /**
     * Mise à jours du personnage
     * @param delta fréquence de la mise à jour
     */
    public void update(int delta) {

        float futurY = y;

        /* Déplacement vers la haut */

        if (this.jumping) {

            futurY -= .5f * delta;
            if (futurY <= positionMax) {
                jumping = false;
            }

            y = futurY;

            //DEBUG
            //System.out.println("Distance de saut : " + positionMax);
            //System.out.println("Futur Y : " + futurY);

        }

        gravity(delta);
        mouvement(delta);
    }


    /* ----------------------------------------------------------------------------------------------------- */


    /**
     * @param auSol
     */
    public void setAuSol(boolean auSol) {
        this.auSol = auSol;
    }

    /**
     * @return auSol
     */
    public boolean getAuSol() {
        return auSol;
    }


    /**
     * @return position en x
     */
    public float getX() {
        return x;
    }

    /**
     * @return position en y
     */
    public float getY() {
        return y;
    }

    public void setDirection(int newDirection) {
        this.direction = newDirection;
    }

    public int getDirection () {
        return direction;
    }

    public void setMouvig(boolean b) {
        this.mooving = b;
    }

    /**
     * @param newScore nouvelle valeur de score
     */
    public void setScore(int newScore) {
        score = newScore;
    }

    /**
     * @return score
     */
    public int getScore() {
        return score;
    }
}
