/*
 * Map.java             06/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.entites;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Map du jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Map {

    private TiledMap tiledMap;

    public final int TAILLE_TUILLE = 32;

    /**
     * Initialisation de la map
     */
    public void init() throws SlickException {
        this.tiledMap = new TiledMap("/ressource/map/niveau_1.tmx");
    }

    /**
     * Affichage de la map
     */
    public void render() {
        this.tiledMap.render(0,0,0); // Affichage du calque 0 (ciel)
        this.tiledMap.render(0,0,1); // Affichage du background lointain
        this.tiledMap.render(0,0,2); // Affichage du background proche
        this.tiledMap.render(0,0,3); // Affichage du premier plan
    }

    /**
     * Determine s'il y a une colision avec le sol
     * @param x position en x
     * @param y position en y
     * @return
     */
    public boolean isCollision(float x, float y) {
        Image tile = tiledMap.getTileImage((int) x / this.tiledMap.getTileWidth(),
                (int) (y + TAILLE_TUILLE) / this.tiledMap.getTileHeight(),
                this.tiledMap.getLayerIndex("logic"));

        return tile != null;
    }
}
