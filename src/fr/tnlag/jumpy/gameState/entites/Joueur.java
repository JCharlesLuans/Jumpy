/*
 * fr.tnlag.jumpy.gameState.entites.Joueur.java             05/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.gameState.entites;

import fr.tnlag.jumpy.gameState.physique.HitBox;
import org.newdawn.slick.*;

/**
 * Personnage joueur
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Joueur {

    private static final float VITESSE = 0.4f;

    private static final int WIDTH_SPRITE = 32;
    private static final int HEIGHT_SPRITE = 32;

    public static final int DROITE = 1;
    public static final int GAUCHE = -1;

    public final float DISTANCE_SAUT = 128f;

    /* -------------------------------------------------------------------------------------------------- */
    /* Champs de la classe */

    private Map map; // Map sur laquelle évolue le joueur

    private int score; // Score du joueur
    private int vie;   // Nombre de vie du joueur

    private boolean takingDegas; // Indicateur du joueur qui est en train de se prendre des dégas

    private float positionMaxSaut; // Indicateur de la position max pour le saut
    private boolean jumping;       // Indicateur de saut

    private float x, // Position en x du personnage
                  y; // Position en y du personnage

    private int direction;   // 1 -> Déplacement vers la droite | -1 déplacement vers la gauche

    private boolean mooving; // Indicateur du déplacement
    private boolean auSol;   // Indicateur pour l' application de la gravité

    private HitBox hitBoxHaut;
    private HitBox hitBoxBas;

    /* Son du joueur */
    private Sound sonSaut;
    private Sound sonPiece;

    /* Timer */
    private int timer;
    /* ------------------------------------------------------------------------------------------------------- */
    /* Méthode */

    private Animation[] listeAnimation = new Animation[4]; // Liste des animation du personnage

    /**
     * Création du joueur avec les paramètre par défaut indiquer
     */
    public Joueur(Map map) throws SlickException {

        sonSaut = new Sound("/ressource/son/bruitages/bond.wav");
        sonPiece = new Sound("/ressource/son/bruitages/piece.wav");

        // Init de la map dans le joueur
        this.map = map;

        /* Position par défaut du personnage joueur */
        x = 16;
        y = 448;
        positionMaxSaut = 0;

        /* Direction par défaut du personnage joueur */
        direction = DROITE;
        
        /* Indicateur de mouvement */
        mooving = false;

        initAnimation();

        /* Initialisation du score et de la vie */
        score = 0;
        vie = 3;

        /* Création des hit box */
        hitBoxHaut = new HitBox(x, y, WIDTH_SPRITE, HEIGHT_SPRITE);
        hitBoxBas  = new HitBox(x, y + HEIGHT_SPRITE, WIDTH_SPRITE, HEIGHT_SPRITE / 8f);

        /* Initialisation du joueur qui se prend des dégas */
        takingDegas = false;

        /* Initialisation du joueur qui se prend des dégas */
        takingDegas = false;
    }
    
    /**
     * Enlève une vie au joueur si il entre en collision avec un mob présent sur la map
     */
    private void collisionMob() {
        for (int i = 0; i < map.getMobs().length; i++) {

            /*
              Si la hit box du corps du joueur se retrouve en contact avec la hit box du mob, que celui ci est actif
              et que le joueur ne  se prend pas des dégas, alors il peut se prendre des dégats
             */
            if (hitBoxHaut.isCollision(map.getMobs()[i].getHitBox()) && map.getMobs()[i].isActive() && !takingDegas) {
                takingDegas = true;
                vie -= 1;
                map.getMobs()[i].changementSens();

            /*
                Si la hit box du bas entre en colision avec la hit box du mob et que celui ci est actif alors il deviens
                inactif
             */
            } else if (hitBoxBas.isCollision(map.getMobs()[i].getHitBox()) && map.getMobs()[i].isActive()) {
                auSol = true;
                saut();
                map.getMobs()[i].setActive(false);
            }
        }

    }

    /**
     * Enleve une vie au joueur si il entre en colision avec un pige
     */
    private void collisionPiege() {

        for (int i = 0; i < map.getPieges().length; i++) {
            if(hitBoxBas.isCollision(map.getPieges()[i].getHitBox())) {
                System.out.println("Taking dega");
                vie--;
            }
        }
    }

    /**
     * Augmente le score de un s' il y a une collision avec un joueur
     */
    private void collisionPiece() {
        for (int i = 0; i < map.getPieces().length; i++) {
            if (hitBoxHaut.isCollision(map.getPieces()[i].getHitBox()) && map.getPieces()[i].isActive() ) {
                map.getPieces()[i].setActive(false);
                score++;
                sonPiece.play();
            }
        }
    }

    /**
     * @return true si le joueur n'a plus de vie, false sinon
     */
    public boolean estMort() {
        return vie == 0;
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
     * Initialisation de l' animation du joueur
     * @throws SlickException
     */
    public void initAnimation() throws SlickException {
        /* Initialisation du tableau des animation */
        for (int i = 0; i < 4; i++) {
            listeAnimation[i] = new Animation();
        }

        // Chargement du sprite
        SpriteSheet feuilleSprite = new SpriteSheet("/ressource/sprite/joueur.png", WIDTH_SPRITE, HEIGHT_SPRITE);

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
     * Fait bouger le joueur vers la droite ou vers la gauche
     */
    private void mouvementHorizontal(int delta) {

        float futurX = x;   // X aprés mouvement du joueur

        /* Déplacement vers la droite ou vers la gauche */
        if (this.mooving) {
            if (direction == DROITE) {
                // DEBUG
                // System.out.println("Vers la droite");
                futurX += VITESSE * delta;
            } else {
                // DEBUG
                // System.out.println("Vers la gauche");
                futurX -= VITESSE * delta;
            }
        }

        // Limite de la map (0 : premiere coordonnée, si inf a 0, out of range car personnage hors de la map)
        if (futurX > 0) {
            this.x = futurX;
        }
    }

    /**
     * Fait bouger le joueur vers le haut
     */
    private void mouvementVertical(int delta) {
        float futurY = y;

        if (this.jumping) {

            futurY -= .5f * delta;

            if (futurY <= positionMaxSaut) {
                jumping = false;
            }

            y = futurY;
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

        g.drawAnimation(listeAnimation[mouvement],  x, y);

        // Affichage des hit box
    }

    /**
     * Fait sauter le joueur
     */
    public void saut() {
        if (auSol) {
            this.jumping = true;
            positionMaxSaut = y - DISTANCE_SAUT;
            sonSaut.play();
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

        // Mise à jour du timer
        timer += delta;

        if (timer >= 3000) {
            timer = 0;
            if (takingDegas) {
                takingDegas = false;
            }
        }

        gravity(delta);
        mouvementVertical(delta);
        mouvementHorizontal(delta);

        hitBoxHaut.update(x, y);
        hitBoxBas.update(x, (y + HEIGHT_SPRITE));

        collisionMob();
        collisionPiece();
        collisionPiege();
    }


    /* ----------------------------------------------------------------------------------------------------- */
    /* Accesseur */


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

    /**
     * @return le nombre de vie du joueur
     */
    public int getVie() {
        return vie;
    }
}
