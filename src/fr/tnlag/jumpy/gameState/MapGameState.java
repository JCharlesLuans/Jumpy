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
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

/**
 * Classe de lancement et de chargement du jeu.
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MapGameState extends BasicGameState {

    // ID
    private final int ID = 1;

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

    /**
     * Créer un nouveau jeu
     */
    public MapGameState() {
        super();
    }



    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException {

        this.container = gameContainer;
        container.setShowFPS(false);

        map.init();
        joueur = new Joueur(map);
        camera = new Camera(gameContainer, joueur, map.getWidth(), map.getHeight());
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
            game.enterState(GameOverState.ID, new FadeOutTransition(), new FadeInTransition());

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
