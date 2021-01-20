/*
 * Map.java             06/01/2021
 * Copyright et copyleft TNLag Corp.
 */

package fr.tnlag.jumpy.gameState.entites;

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

    private static final int NOMBRE_MOBS = 1 ; // Nombre de mob par défaut

    public static final int TAILLE_TUILLE = 32;

    public static final int ID_GROUPE_PIECE = 0;
    public static final int ID_GROUPE_PIEGE = 1;

    private TiledMap tiledMap;

    /* Objets présent sur la map */
    private Piece[] listePiece;     // Piece présente sur la map
    private Piege[] listePiege;
    private MobHostile[] listeMobs; // Mob présent sur la map

    /**
     * Initialisation de la map
     */
    public void init() throws SlickException {

        correctifMap("ressource/map/niveau_1.tmx");
        this.tiledMap = new TiledMap("ressource/map/niveau_1.tmx");

        int nbPiece = tiledMap.getObjectCount(ID_GROUPE_PIECE); // Nombre de pièce sur la map
        int nbPiege = tiledMap.getObjectCount(ID_GROUPE_PIEGE); // Nombre de piege sur la map

        int x = 0,
            y = 0;

        int width = 0,
            height = 0;

        /* Recherche des piece sur la map et génération des pièces */
        listePiece = new Piece[nbPiece];
        for (int idPiece = 0; idPiece < nbPiece; idPiece++) {

            x = tiledMap.getObjectX(ID_GROUPE_PIECE, idPiece);
            y = tiledMap.getObjectY(ID_GROUPE_PIECE, idPiece);

            listePiece[idPiece] = new Piece(x, y);
        }

        /* Recherche des pièges sur la map et génération des pièges */
        listePiege = new Piege[nbPiege];
        for (int idPiege = 0; idPiege < nbPiege; idPiege++) {
            x = tiledMap.getObjectX(ID_GROUPE_PIEGE, idPiege);
            y = tiledMap.getObjectY(ID_GROUPE_PIEGE, idPiege);
            width = tiledMap.getObjectWidth(ID_GROUPE_PIEGE, idPiege);
            height = tiledMap.getObjectHeight(ID_GROUPE_PIEGE, idPiege);

            listePiege[idPiege] = new Piege(x, y, width, height);
        }


        // Génération des mobs
        listeMobs = new MobHostile[NOMBRE_MOBS];
        for (int i = 0; i < NOMBRE_MOBS; i ++)  {
            x = 32 + (int)(Math.random() * ((tiledMap.getWidth()*32 - 32) + 1));
            listeMobs[i] = new MobHostile(this, x, tiledMap.getHeight()*32 - 128);
        }
    }

    /**
     * Affichage de la map
     */
    public void render(Graphics graphics) throws SlickException {
        this.tiledMap.render(0,0,0); // Affichage du calque 0 (ciel)
        this.tiledMap.render(0,0,1); // Affichage du background lointain
        this.tiledMap.render(0,0,2); // Affichage du background proche
        this.tiledMap.render(0,0,3); // Affichage du premier plan

        // Affichage des pièces
        for (int i = 0; i < listePiece.length; i ++) {
            listePiece[i].render(graphics);
        }

        // Affichage des mobs
        for (int i = 0; i < listeMobs.length; i++) {
            listeMobs[i].render(graphics);
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

    public void update(int delta) {

        // Mise a jour des mobs
        for(int i = 0; i < listeMobs.length; i++) {
            listeMobs[i].setAuSol(this.isCollision(listeMobs[i].getX(), listeMobs[i].getY()));
            listeMobs[i].update(delta, tiledMap.getWidth()*32-64);
        }
    }

    /**
     * @return toute les piece sur la map active
     */
    public Piece[] getPieces() {
        return listePiece;
    }

    /**
     * @return touts les piege sur la map active
     */
    public Piege[] getPieges() {
        return listePiege;
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

        boolean objectGroup = false; // Indique si le curseur est dans une balise object group

        ArrayList<String> newMap = new ArrayList<>();  // Map réécrite

        BufferedReader lecteurAvecBuffer = null;
        PrintWriter ecrivain;

        String ligne;
        String listeLigneSecondaire[];

        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(cheminMap));

            // Parcours du fichier
            while ((ligne = lecteurAvecBuffer.readLine()) != null) {

                ligne = correctifBaliseGroupObject(ligne);

                // Correction des positions dans les groupes d'objets
                // Indicateur dans un object groupe
                if (ligne.contains("<objectgroup")) {
                    objectGroup = true;
                } else if (ligne.contains("</objectgroup>")) {
                    objectGroup = false;
                }

                if (objectGroup && ligne.contains(".")) {
                    ligne = correctifBaliseObject(ligne);
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

    /**
     * Transforme tout les float des position dans les objectGroup en int
     * @param ligne ligne qui contient les floats
     * @return la ligne sans les floats
     */
    private static String correctifBaliseObject(String ligne) {

        String[] listeLigneSecondaire;
        String[] ligneCouper;

            ligneCouper = ligne.split("\\.");

            // Cas ou il faut gere que x ou y
            ligne = ligneCouper[0];
            listeLigneSecondaire = ligneCouper[1].split("\"");
            for (int i = 1; i < listeLigneSecondaire.length; i++) {
                ligne += "\"";
                ligne += listeLigneSecondaire[i];
            }

            // Cas ou il faut gerer x et y
            if (ligneCouper.length != 2) {

                listeLigneSecondaire = ligneCouper[2].split("\"");
                for (int i = 1; i < listeLigneSecondaire.length; i++) {
                    ligne += "\"";
                    ligne += listeLigneSecondaire[i];
                }
            }

        return ligne;
    }


    /**
     * Corrige les balises objectGroup en leurs ajoutant une height et une width
     * @param ligne ligne a corriger
     * @return la ligne corriger
     */
    private static String correctifBaliseGroupObject(String ligne) {
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
        }
        return ligne;
    }

    public int getWidth() {
        return tiledMap.getWidth();
    }

    public int getHeight() {
        return tiledMap.getHeight();
    }

}
