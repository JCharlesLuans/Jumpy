/*
 * GameOverState.java             10/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.statiqueState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu de game over. Page afficher lorsque le joueur a perdu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class GameOverState extends BasicGameState {

    public static final int ID = 2;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
}
