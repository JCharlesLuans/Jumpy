/*
 * Interface.java             07/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.HUD;

import fr.tnlag.jumpy.entites.Joueur;
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
        font = new UnicodeFont("/ressource/texture/font/font.ttf", 32, false, false);
        // font = new UnicodeFont(new java.awt.Font("DejaVu Serif", java.awt.Font.PLAIN, 28));
        font.addAsciiGlyphs();
        font.addGlyphs(400,600);
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));//Ca sert a quoi ?
        font.loadGlyphs();

        this.joueur = joueur;
        affichagePiece = new Image("ressource/texture/interface/nb_piece.png");
    }

    public void render(Graphics g) {

        g.setFont(font);
        String text = "" + joueur.getScore();



        g.resetTransform();
        font.drawString(126f, 0f, text, Color.white);
        g.drawImage(this.affichagePiece, 0, 0);

    }
}
