/*
 * Map.java             06/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.entites;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.*;
import java.util.ArrayList;

/**
 * Map du jeu
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class Map {

    public static final int ID_GROUPE_PIECE = 0;

    private TiledMap tiledMap;

    public final int TAILLE_TUILLE = 32;

    private Piece[] listePiece; // Piece présente sur la map

    /**
     * Initialisation de la map
     */
    public void init() throws SlickException {
        correctifMap("ressource/map/niveau_1.tmx");
        this.tiledMap = new TiledMap("ressource/map/niveau_1.tmx");

        int nbPiece = tiledMap.getObjectCount(ID_GROUPE_PIECE); // Nombre de pièce dans le groupe

        System.out.println("Nb piece = " + nbPiece);

        /* Recherche des piece sur la map */
        listePiece = new Piece[nbPiece];

        int x = 0,
            y = 0;

        for (int idPiece = 0; idPiece < nbPiece; idPiece++) {

            x = tiledMap.getObjectX(ID_GROUPE_PIECE, idPiece);
            y = tiledMap.getObjectY(ID_GROUPE_PIECE, idPiece);

            listePiece[idPiece] = new Piece(x, y);

        }

    }

    /**
     * Affichage de la map
     */
    public void render(Graphics graphics) {
        this.tiledMap.render(0,0,0); // Affichage du calque 0 (ciel)
        this.tiledMap.render(0,0,1); // Affichage du background lointain
        this.tiledMap.render(0,0,2); // Affichage du background proche
        this.tiledMap.render(0,0,3); // Affichage du premier plan

        for (int i = 0; i < listePiece.length; i ++) {
            listePiece[i].render(graphics);
        }
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

    /**
     * @return toute les piece sur la map active
     */
    public Piece[] getPieces() {
        return listePiece;
    }

    /**
     * @return touts les mobs qui sont sur la map
     */
    public MobHostile[] getMobs() {
        return listeMobs;
    }

    /**
     * Applique un correctif au fichier tmx (XML) de la carte pour que cette dernière soit lisible par Slick2D.
     * Recherche la balise "objectgroup" pour lui ajouter les attribut "height = 1" et "width = 1"
     * @param cheminMap chemin de la map a modifier
     *
     */
    private static void correctifMap(String cheminMap) {

        ArrayList<String> newMap = new ArrayList<>();  // Map réécrite

        BufferedReader lecteurAvecBuffer = null;
        PrintWriter ecrivain;

        String ligne;

        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(cheminMap));

            // Parcours du fichier
            while ((ligne = lecteurAvecBuffer.readLine()) != null) {

                // Si la ligne est la balise "objectgroup", et qu' elle ne contient
                // pas deja un attribut width, on la modifie
                if (ligne.contains("<objectgroup") && !ligne.contains("width")) {

                    // DEBUG
                    System.out.println(ligne);

                    // Enlever fin de la balise pour ajout des attributs
                    ligne = ligne.replace('>', ' ');

                    // La balise était orpheline
                    if (ligne.contains("/")) {
                        ligne = ligne.replace('/', ' ');      // Réouverture de la balise orpheline
                        ligne = ligne.concat("height=\"1\" width=\"1\" ");  // Ajout des attributs
                        ligne = ligne.concat("/>");                          // Fermeture de la balise orpheline

                    } else {
                        // La balise n' est pas orpheline
                        ligne = ligne.concat("height=\"1\" width=\"1\" ");  // Ajout des attributs
                        ligne = ligne.concat(">");                           // Fermeture de la balise
                    }
                    System.out.println(ligne);
                }

                newMap.add(ligne); // Ajout de la ligne a la réécriture de la map
            }
            lecteurAvecBuffer.close();

        } catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Réécriture du fichier a partir de l' arraylist */
        try {

            ecrivain =  new PrintWriter(new BufferedWriter (new FileWriter(cheminMap)));

            for (int i = 0; i < newMap.size(); i++) {
                ecrivain.println(newMap.get(i));
            }
            ecrivain.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
