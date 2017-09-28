/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iaie.practica1.solucion.map;

import jnibwapi.Map;
import jnibwapi.Position;
import static jnibwapi.Position.PosType.WALK;

/**
 *
 * @author momartin
 */
public class ChokeArea {
    
    private final byte[][] area;
    private final Position center;
    private final int posX;
    private final int posY;
    private Position chokePoint;
    
    public ChokeArea(Map map, Position a, Position b, Position center) {
        this.chokePoint = null;
        this.center = center;
        this.area = new byte[this.distance(a.getWX(), b.getWX()) + 1][this.distance(a.getWY(), b.getWY())+1];

        int startX = this.min(a.getWX(), b.getWX());
        int startY = this.min(a.getWY(), b.getWY());
        int endX = this.max(a.getWX(), b.getWX()) + 1;
        int endY = this.max(a.getWY(), b.getWY()) + 1;
        
        this.posX = center.getWX()-startX;
        this.posY = center.getWY()-startY;

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                Position tmp = new Position(x, y, WALK);
                if (map.isWalkable(tmp)) {
                    if (map.getRegion(tmp) != null)
                        this.area[x-startX][y-startY] = (byte) (map.getRegion(tmp).getID()-1);
                    else
                        this.area[x-startX][y-startY] = -1;
                }
            }
        }
        
        for (int x = 0; x < this.area.length; x++) {
            for (int y = 0; y < this.area[0].length; y++) {
                if (this.calculateRealChokePoint(x, y)) {
                    this.chokePoint = new Position(x+startX, y+startY, WALK);
                }
            }
        }
    }
    
    private boolean calculateRealChokePoint(int x, int y) {
        
        int startX = (x-1) > 0 ? x-1:0;
        int startY = (y-1) > 0 ? y-1:0;
        int endX = (x+2) <= this.area.length ? x+2:this.area.length;
        int endY = (y+2) <= this.area[0].length ? y+2:this.area[0].length;
        
        int value = this.area[x][y];
        
        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                if (this.area[i][j] != this.area[x][y]) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private int distance(int x, int y) {
        return Math.abs(x - y);
    }
    
    private int min(int x, int y) {
        return (x > y) ? y:x;
    }
    
    private int max(int x, int y) {
        return (x > y) ? x:y;
    }
    
    public void add(int x, int y, byte value) {
        this.area[x][y] = value;
    }
    
    public boolean isConnected() {
        return (this.chokePoint != null);
    }
    
    public Position getConnection() {
        return this.chokePoint;
    }
    
    public void draw() {
        for (int i = 0; i < this.area.length; i++) {
            for (int j = 0; j < this.area[i].length; j++) {
                if ((i == this.posX) && (j == this.posY))
                    System.out.print("[" + this.area[i][j] + "] ");
                else
                    System.out.print(this.area[i][j] + " ");
            }
            System.out.println();
        }
        if (this.chokePoint != null) {
            System.out.println("SI");
        }
    }
}
