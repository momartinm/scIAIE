/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file+x, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iaie.practica1.solucion.map;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author momartin
 */
public class TileMap {
    
    private final boolean[][] environment;
    private int walkableTiles;
    
    public TileMap(int sX, int sY) {
        this.walkableTiles = 0;
        this.environment = new boolean[sX][sY];
        
        for (int x = 0; x < sX; x++) {
            for (int y = 0; y < sY; y++) {
                this.environment[x][y] = false;
            }
        }
    }
    
    public void makeWalkable(int x, int y, boolean addTile) {
        this.environment[x][y] = true;
        if (addTile) this.walkableTiles++;
    }
    
    public boolean isWalkable(int x, int y) {
        return this.environment[x][y];
    }
    
    public int getNumberWalkableTiles() {
        return this.walkableTiles;
    }
    
    public int getX(){
        return this.environment.length;
    }
    
    public int getY() {
        return this.environment[0].length;
    }
    
    public void printMap(String fileName) throws IOException {
        
        byte[][] temp = new byte[this.environment.length][this.environment[0].length];
            
        for (int x = 0; x < this.environment.length; x++) {
            for (int y = 0; y < this.environment[0].length; y++) {
                if (this.environment[x][y])
                    temp[x][y] = 1;
                else
                    temp[x][y] = 0;
            }
        }
        
        PrintWriter writer = new PrintWriter(new FileWriter(fileName, false));

        for (int y = 0; y < this.environment.length; y++) {
            for (int x = 0; x < this.environment[0].length; x++) {
                writer.print(temp[x][y]);
            }
            writer.println();
        }

        writer.close();
    }
    
    
    public void printMap(Point start, Point end, String fileName) throws IOException {
        
        byte[][] temp = new byte[this.environment.length][this.environment[0].length];
            
        for (int x = 0; x < this.environment.length; x++) {
            for (int y = 0; y < this.environment[0].length; y++) {
                if (this.environment[x][y])
                    temp[x][y] = 1;
                else
                    temp[x][y] = 0;
            }
        }
        
        temp[start.x][start.y] = 9;
        temp[end.x][end.y] = 9;
        
        PrintWriter writer = new PrintWriter(new FileWriter(fileName, false));

        for (int y = 0; y < this.environment.length; y++) {
            for (int x = 0; x < this.environment[0].length; x++) {
                writer.print(temp[x][y]);
            }
            writer.println();
        }

        writer.close();
    }
}
