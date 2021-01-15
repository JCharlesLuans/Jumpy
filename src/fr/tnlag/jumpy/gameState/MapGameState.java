/*
 * Jumpy.java             05/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.gameState;

import fr.tnlag.jumpy.gameState.HUD.Interface;
import fr.tnlag.jumpy.gameState.entites.Camera;
import fr.tnlag.jumpy.gameState.entites.Joueur;
import fr.tnlag.jumpy.gameState.entites.Map;
import fr.tnlag.jumpy.gameState.physique.PlayerController;
import fr.tnlag.jumpy.statiqueState.GameOverState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Classe de lancement et de chargement du jeu.
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MapGameState extends BasicGameState {

    // ID
    public static final int ID = 1;

    // Conteneur du jeu
    private GameContainer container;

    // Map afficher
    private Map map = new Map();

    // Joueur
    Joueur joueur;

    // Camera
    Camera camera;

    // Affichage
    Interface affichage = new Interface();

    /* Musique de fond */
    private Music backgroundMusic;

    /**
     * Créer un nouveau jeu
     */
    public MapGameState() {
        super();
    }

    public void enter(GameContainer gameContainer, StateBasedGame game) throws SlickException {
        backgroundMusic.play(1, 0.10f);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException {

        backgroundMusic = new Music("ressource/son/musiques/musique1.wav");

        this.container = gameContainer;
        container.setShowFPS(false);

        map.init();
        joueur = new Joueur(map);
        camera = new Camera(container, joueur, map.getWidth(), map.getHeight());
        affichage.init(joueur);

        PlayerController controller = new PlayerController(joueur);
        container.getInput().addKeyListener(controller);
        container.getInput().addControllerListener(controller);


    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame game,int delta) throws SlickException {
        joueur.update(delta);
        joueur.setAuSol(map.isCollision(joueur.getX(), joueur.getY()));
        if (joueur.estMort())
            game.enterState(GameOverState.ID);
        map.update(delta);
        camera.update(gameContainer, delta);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame game,Graphics graphics) throws SlickException {
        camera.render(gameContainer, graphics);
        map.render(graphics);
        joueur.render(graphics);
        affichage.render(graphics);
    }

    public int getID() {
        return ID;
    }
}
