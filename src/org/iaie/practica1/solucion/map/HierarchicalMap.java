/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iaie.practica1.solucion.map;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jnibwapi.ChokePoint;
import jnibwapi.Map;
import jnibwapi.Position;
import static jnibwapi.Position.PosType.WALK;
import jnibwapi.Region;

public class HierarchicalMap {
    
    private final ConnectionArea connections[][];
    private final Position[] centers;
    private final TileMap[] maps;
    
    
    public HierarchicalMap(Map map) {
        
        this.connections = new ConnectionArea[map.getRegions().size()][map.getRegions().size()];
        this.maps = new TileMap[map.getRegions().size()];
        this.centers = new Position[map.getRegions().size()];
        
        for (Region region : map.getRegions()) {
            this.maps[region.getID()-1] = new TileMap(map.getSize().getWX(), map.getSize().getWY());
            this.centers[region.getID()-1] = region.getCenter();
        }
            
        for (Region region : map.getRegions()) {
            if (region.getID() == 12) {
                System.out.println("");
            }
            for (ChokePoint choke : region.getChokePoints()) {
                //this.connections[choke.getFirstRegion().getID()-1][choke.getSecondRegion().getID()-1] = new ConnectionArea(map, choke.getFirstSide(), choke.getSecondSide(), choke.getCenter(), choke.getFirstRegion().getID()-1);
                //this.connections[choke.getSecondRegion().getID()-1][choke.getFirstRegion().getID()-1] = new ConnectionArea(map, choke.getFirstSide(), choke.getSecondSide(), choke.getCenter(), choke.getSecondRegion().getID()-1);;
                
                this.connections[choke.getFirstRegion().getID()-1][choke.getSecondRegion().getID()-1] = new ConnectionArea(map, (int) (choke.getRadius()/8), choke.getCenter(), choke.getFirstRegion().getID()-1);
                this.connections[choke.getSecondRegion().getID()-1][choke.getFirstRegion().getID()-1] = new ConnectionArea(map, (int) (choke.getRadius()/8), choke.getCenter(), choke.getSecondRegion().getID()-1);;
                
                Position p1 = this.connections[choke.getFirstRegion().getID()-1][choke.getSecondRegion().getID()-1].getConnection();
                
                if (p1 != null) {
                    this.maps[choke.getFirstRegion().getID()-1].makeWalkable(p1.getWX(), p1.getWY(), true);
                    //this.maps[choke.getSecondRegion().getID()-1].makeWalkable(p1.getWX(), p1.getWY(), true);
                    //System.out.println("Generating ChokePoint[" + (choke.getFirstRegion().getID()-1) + "," + (choke.getSecondRegion().getID()-1) + "] = " + "(" + p1.getWX() + "," + p1.getWY() + ")");
                }
                
                Position p2 = this.connections[choke.getSecondRegion().getID()-1][choke.getFirstRegion().getID()-1].getConnection();
               
                if (p2 != null) {
                    this.maps[choke.getSecondRegion().getID()-1].makeWalkable(p2.getWX(), p2.getWY(), true);
                    //this.maps[choke.getFirstRegion().getID()-1].makeWalkable(p2.getWX(), p2.getWY(), true);
                    //System.out.println("Generating ChokePoint[" + (choke.getSecondRegion().getID()-1) + "," + (choke.getFirstRegion().getID()-1) + "] = " + "(" + p2.getWX() + "," + p2.getWY() + ")");
                }
            }
        }
        
        Position temp = null;
        
        for (int x = 0; x < map.getSize().getWX(); x++) {
            for (int y = 0; y < map.getSize().getWY(); y++) {
                temp = new Position(x, y, WALK);
                if (map.isWalkable(temp)) {
                    Region region = map.getRegion(temp);
                    if (region != null) {
                        this.maps[region.getID()-1].makeWalkable(x, y, true);
                    }
                    else {
                        for (int i = 0; i < this.maps.length; i++)
                            this.maps[i].makeWalkable(x, y, false);
                    } 
                }
            }
        }
    }
    
    public ArrayList<Integer> getRegions(int region) {
        ArrayList<Integer> temp = new ArrayList<>();
        
        for (int y = 0; y < this.connections[region].length; y++) {
            if (this.connections[region][y] != null) {
                if (this.connections[region][y].isConnected())
                    temp.add(y);
            }
        }
        return temp;
    }
    
    public int getWalkableTilesByRegions(int region) {
        return this.maps[region].getNumberWalkableTiles();
    }
    
    public Point getPointConnection(int region1, int region2) {
        Position position = this.connections[region1][region2].getConnection();
        return new Point(position.getWX(), position.getWY());
    }
    
    public TileMap getRegionMap(int region) {
        return this.maps[region];
    }
    
    public Position getRegionCenter(int region) {
        return this.centers[region];
    }
    
    public void printMap(int region, Point start, Point end, String fileName) throws IOException {
        this.maps[region].printMap(start, end, fileName);
    }
    
    public void printConnections(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(fileName, false));

        writer.print("   ");
        
        for (int y = 0; y < this.connections.length; y++) {
            writer.print(this.printNumber(y));
        }
        
        writer.println();
        
        for (int x = 0; x < this.connections.length; x++) {
            writer.print(this.printNumber(x));
            
            for (int y = 0; y < this.connections[0].length; y++) {
                if ((this.connections[x][y] != null) && (this.connections[x][y].isConnected()))
                    writer.print("TT ");
                else
                    writer.print("FF ");
            }
            writer.println();
        }

        writer.close();
    }
    
    private String printNumber(int n) {
        if (n < 10)
            return "0" + n + " ";
        else
            return n + " ";
    }
}