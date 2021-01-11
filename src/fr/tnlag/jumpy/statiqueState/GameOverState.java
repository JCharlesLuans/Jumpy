/*
 * GameOverState.java             10/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.statiqueState;

import fr.tnlag.jumpy.Police;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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

    private static final float VOLUME_SON = 0.01f;

    private Image backgroundImage; // Image de fond
    private Music backgroundMusic; // Musique de fond

    private StateBasedGame stateBasedGame;
    private GameContainer container;

    private Police police;

    private boolean affiche;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        police = new Police();

        this.stateBasedGame = stateBasedGame;
        this.container = gameContainer;

        backgroundImage = new Image("ressource/imageBackground/gameOver.png");
        backgroundMusic = new Music("ressource/son/musiques/gameOver.wav");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        UnicodeFont font = Police.getFont(12);
        graphics.drawImage(backgroundImage, 0,0);
        String texte = "(Appuyer sur une touche pour revenir au menu principal)";

        // Calcul de x et y pour le positionnement du texte
        float x = gameContainer.getWidth() / 2f - (font.getWidth(texte) /2f);
        float y = gameContainer.getHeight() / 2f - (font.getHeight(texte) /2f);

        if (affiche)
            font.drawString(x, y, texte, Color.white);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        affiche = true;
        
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        backgroundMusic.play(1.0f, VOLUME_SON);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        backgroundMusic.stop();
    }

    @Override
    public void keyPressed(int key, char c) {
        stateBasedGame.enterState(MenuState.ID);
    }

    @Override
    public void controllerButtonPressed(int controller, int buttton) {
        stateBasedGame.enterState(MenuState.ID);
    }
}
