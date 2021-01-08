/*
 * Interface.java             07/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.HUD;

import fr.tnlag.jumpy.gameState.entites.Joueur;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * Gere l' affichage du jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Interface {

    GameContainer container;
    Joueur joueur;

    UnicodeFont font; // Police de l'affichage

    private Image affichagePiece;

    /**
     * Nouvelle interface
     */
    public void init(Joueur joueur) throws SlickException {
        font = new UnicodeFont("/ressource/texture/font/font.ttf", 24, false, false);
        font.addAsciiGlyphs();
        font.addGlyphs(400,600);
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        font.loadGlyphs();

        this.joueur = joueur;
        affichagePiece = new Image("ressource/texture/interface/icone_piece.png");
    }

    public void render(Graphics g) {

        g.setFont(font);
        String text = "Pièce : " + joueur.getScore();
        g.resetTransform();
        font.drawString(40, 6f, text, Color.white);
        g.drawImage(this.affichagePiece, 0, 0);

    }
}
