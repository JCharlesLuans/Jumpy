/*
 * Police.java             13/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * Police d'écriture du jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Police {


    public static UnicodeFont getFont(int size) throws SlickException {
        UnicodeFont font = new UnicodeFont("/ressource/texture/font/font.ttf", size, false, false);
        font.addAsciiGlyphs();
        font.addGlyphs(400,600);
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        font.loadGlyphs();
        return font;
    }
}
