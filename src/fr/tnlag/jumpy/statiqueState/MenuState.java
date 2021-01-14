/*
 * MenuState.java             11/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.statiqueState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu principal du jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class MenuState extends BasicGameState {

    public static final int ID = 1;

    private Image imageBackground;
    private Music musicBackground;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        imageBackground = new Image("ressource/imageBackground/mainMenu.png");
        musicBackground = new Music("ressource/son/musiques/menus.wav");
        musicBackground.loop();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(imageBackground, 0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

}
