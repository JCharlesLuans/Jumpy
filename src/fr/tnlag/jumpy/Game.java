/*
 * Jumpy.java             05/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy;

import fr.tnlag.jumpy.entites.Camera;
import fr.tnlag.jumpy.entites.Joueur;
import fr.tnlag.jumpy.entites.Map;
import fr.tnlag.jumpy.physique.PlayerController;
import org.newdawn.slick.*;

/**
 * Classe de lancement et de chargement du jeu.
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Game extends BasicGame {

    // Conteneur du jeu
    private GameContainer container;

    // Map afficher
    private Map map = new Map();

    // Joueur
    Joueur joueur = new Joueur();

    // Camera
    Camera camera = new Camera();

    /**
     * Créer un nouveau jeu
     */
    public Game() {
        super("Jumpy");
    }

    /**
     * Démarre une nouvelle fenêtre.
     * @param args
     */
    public static void main(String[] args) throws  SlickException{
        new AppGameContainer(new Game(), 1024, 512, false).start();
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        this.container = gameContainer;

        map.init();
        joueur.init(gameContainer);
        camera.init(joueur);

        PlayerController controller = new PlayerController(joueur);
        container.getInput().addKeyListener(controller);
        container.getInput().addControllerListener(controller);


    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        joueur.update(delta);
        joueur.setAuSol(map.isCollision(joueur.getX(), joueur.getY()));
        camera.update(delta);

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        map.render();
        joueur.render(graphics);
        camera.render(gameContainer, graphics);
    }
}
