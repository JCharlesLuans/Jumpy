/*
 * MenuState.java             11/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.statiqueState;

import fr.tnlag.jumpy.Police;
import fr.tnlag.jumpy.gameState.MapGameState;
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

    /* Id de la game state */
    public static final int ID = 0;

    /* Texte du menu */
    private static final String TXT_NOUVELLE_PARTIE = "Nouvelle partie";
    private static final String TXT_CONTINUER_PARTIE = "Continuer partie";
    private static final String TXT_MULTI = "Multijoueur";
    private static final String TXT_QUITTER = "Quitter";

/* -------------------------------------------------------------------------------------------------- */

    /* Game State et fenêtre */
    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    /* Image et musique de fond */
    private Image imageBackground;
    private Music musicBackground;

    /* Bruitage du menu */
    private Sound sonScroll;
    private Sound sonClick;

    /* Choix du menu */
    private int choix;

/* ------------------------------------------------------------------------------------------------------ */

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        this.stateBasedGame = stateBasedGame;
        this.gameContainer = gameContainer;

        imageBackground = new Image("ressource/imageBackground/mainMenu.png");
        musicBackground = new Music("ressource/son/musiques/menus.wav");

        sonScroll = new Sound("ressource/son/menus/scroll.ogg");
        sonClick = new Sound("ressource/son/menus/click.wav");

        musicBackground.loop(1, 0.25f);

        choix = 2;

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
            throws SlickException {

        graphics.drawImage(imageBackground, 0, 0);

        graphics.setFont(Police.getFont(30));
        renderChoixMenu(graphics, gameContainer);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_DOWN) {
            scrollBas();
        }
        if (key == Input.KEY_UP) {
            scrollHaut();
        }

        if (key == Input.KEY_ENTER) {
            validerChoix();
        }
    }

    public void controllerDownPressed(int controller) {
        scrollBas();
    }

    public void controllerUpPressed(int controller) {
        scrollHaut();
    }

    public void controllerButtonPressed(int controller, int button) {
        validerChoix();
    }

    /**
     * Affiche les choix du menu et colore la sélection
     * @param graphics
     * @param gameContainer
     */
    private void renderChoixMenu(Graphics graphics, GameContainer gameContainer) {

        switch (choix) {
            case 1:
                graphics.setColor(Color.red);
                graphics.drawString(TXT_NOUVELLE_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_NOUVELLE_PARTIE) / 2F, gameContainer.getHeight() / 2 - 64f);
                graphics.setColor(Color.blue);
                graphics.drawString(TXT_CONTINUER_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_CONTINUER_PARTIE) / 2F, gameContainer.getHeight() / 2 - 32f);
                graphics.drawString(TXT_MULTI, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_MULTI) / 2F, gameContainer.getHeight() / 2f);
                graphics.drawString(TXT_QUITTER, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_QUITTER) / 2F, gameContainer.getHeight() / 2 + 32);
                break;

            case 2:
                graphics.setColor(Color.blue);
                graphics.drawString(TXT_NOUVELLE_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_NOUVELLE_PARTIE) / 2F, gameContainer.getHeight() / 2 - 64f);
                graphics.setColor(Color.red);
                graphics.drawString(TXT_CONTINUER_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_CONTINUER_PARTIE) / 2F, gameContainer.getHeight() / 2 - 32f);
                graphics.setColor(Color.blue);
                graphics.drawString(TXT_MULTI, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_MULTI) / 2F, gameContainer.getHeight() / 2f);
                graphics.drawString(TXT_QUITTER, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_QUITTER) / 2F, gameContainer.getHeight() / 2 + 32);
                break;

            case 3:
                graphics.setColor(Color.blue);
                graphics.drawString(TXT_NOUVELLE_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_NOUVELLE_PARTIE) / 2F, gameContainer.getHeight() / 2 - 64f);
                graphics.drawString(TXT_CONTINUER_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_CONTINUER_PARTIE) / 2F, gameContainer.getHeight() / 2 - 32f);
                graphics.setColor(Color.red);
                graphics.drawString(TXT_MULTI, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_MULTI) / 2F, gameContainer.getHeight() / 2f);
                graphics.setColor(Color.blue);
                graphics.drawString(TXT_QUITTER, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_QUITTER) / 2F, gameContainer.getHeight() / 2 + 32);
                break;

            case 4:
                graphics.setColor(Color.blue);
                graphics.drawString(TXT_NOUVELLE_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_NOUVELLE_PARTIE) / 2F, gameContainer.getHeight() / 2 - 64f);
                graphics.drawString(TXT_CONTINUER_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_CONTINUER_PARTIE) / 2F, gameContainer.getHeight() / 2 - 32f);
                graphics.drawString(TXT_MULTI, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_MULTI) / 2F, gameContainer.getHeight() / 2f);
                graphics.setColor(Color.red);
                graphics.drawString(TXT_QUITTER, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_QUITTER) / 2F, gameContainer.getHeight() / 2 + 32);
                break;

            default:
                graphics.setColor(Color.blue);
                graphics.drawString(TXT_NOUVELLE_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_NOUVELLE_PARTIE) / 2F, gameContainer.getHeight() / 2 - 64f);
                graphics.drawString(TXT_CONTINUER_PARTIE, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_CONTINUER_PARTIE) / 2F, gameContainer.getHeight() / 2 - 32f);
                graphics.drawString(TXT_MULTI, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_MULTI) / 2F, gameContainer.getHeight() / 2f);
                graphics.drawString(TXT_QUITTER, gameContainer.getWidth() / 2f - graphics.getFont().getWidth(TXT_QUITTER) / 2F, gameContainer.getHeight() / 2 + 32);
                break;
        }
    }

    /**
     * Execute l'action en fonction du choix
     */
    private void validerChoix() {
        sonClick.play();
        switch (choix) {
            case 1:
                stateBasedGame.enterState(MapGameState.ID);
                break;
            case 4 :
                gameContainer.exit();
        }
    }

    private void scrollBas() {
        sonScroll.play();
        choix++;
        if (choix == 5)
            choix = 1;
    }

    private void scrollHaut() {
        sonScroll.play();
        choix--;
        if (choix == 0)
            choix = 4;
    }

}
