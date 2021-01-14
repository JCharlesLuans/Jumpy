/*
 * Jumpy.java             10/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy;

import fr.tnlag.jumpy.gameState.MapGameState;
import fr.tnlag.jumpy.statiqueState.GameOverState;
import fr.tnlag.jumpy.statiqueState.MenuState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Lancement du jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Jumpy extends StateBasedGame {

    public Jumpy() {
        super("Jumpy");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new MenuState());
        addState(new MapGameState());
        addState(new GameOverState());

    }

    /**
     * Démarre une nouvelle fenêtre.
     * @param args
     */
    public static void main(String[] args) throws  SlickException{
        new AppGameContainer(new Jumpy(), 1024, 512, false).start();
    }

}
