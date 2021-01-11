/*
 * Interface.java             07/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.gameState.HUD;

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

    private Image affichagePiece; // Image de l'icone du score
    private Image affichageVie;   // Image de l'icone de la vie

    /**
     * Nouvelle interface
     */
    public void init(Joueur joueur) throws SlickException {

        initFont();

        this.joueur = joueur;
        affichagePiece = new Image("ressource/texture/interface/icone_piece.png");
        affichageVie   = new Image("ressource/texture/interface/icone_vie.png");
    }

    public void render(Graphics g) {

        String texteScore = "Pièce:" + joueur.getScore();
        String texteVie   = "Vie:" + joueur.getVie();

        g.setFont(font);

        g.resetTransform();

        g.drawImage(this.affichagePiece, 0, 0);
        g.drawImage(this.affichageVie, 0, affichagePiece.getHeight());

        font.drawString(40, 6f, texteScore, Color.white);
        font.drawString(40, affichagePiece.getHeight() + 6f, texteVie, Color.white);


    }

    // Charge la police du jeu
    private void initFont() throws SlickException {
        font = new UnicodeFont("/ressource/texture/font/font.ttf", 24, false, false);
        font.addAsciiGlyphs();
        font.addGlyphs(400,600);
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        font.loadGlyphs();
    }
}
