package org.iaie.practica1.solucion.map;

import java.awt.Point;
import jnibwapi.Map;
import jnibwapi.Position;
import static jnibwapi.Position.PosType.WALK;

public class ConnectionArea {
    private final byte[][] area;
    private final Point c;
    private final Point choke;
    private final int x;
    private final int y;
    
    public ConnectionArea(Map map, Position a, Position b, Position center, int region) {
        
        this.x = this.min(a.getWX(), b.getWX());
        this.y = this.min(a.getWY(), b.getWY());
        this.area = new byte[this.distance(a.getWX(), b.getWX()) + 1][this.distance(a.getWY(), b.getWY())+1];
        this.c = new Point(center.getWX()-this.x, center.getWY()-this.y);
        
        int endX = this.max(a.getWX(), b.getWX()) + 1;
        int endY = this.max(a.getWY(), b.getWY()) + 1;

        for (int i = this.x; i < endX; i++) {
            for (int j = this.y; j < endY; j++) {
                Position tmp = new Position(i, j, WALK);
                if (map.isWalkable(tmp)) {
                    if (map.getRegion(tmp) != null)
                        this.area[i-this.x][j-this.y] = (byte) (map.getRegion(tmp).getID()-1);
                    else
                        this.area[i-this.x][j-this.y] = -1;
                }
            }
        }
        this.choke = this.calculateChokePoint(region);
    }
    
    public ConnectionArea(Map map, int radio, Position center, int region) {
        
        this.x = center.getWX() - radio;
        this.y = center.getWY() - radio;
        this.area = new byte[(radio*2)+1][(radio*2)+1];
        this.c = new Point(center.getWX()-this.x, center.getWY()-this.y);
        
        int endX = center.getWX() + radio;
        int endY = center.getWY() + radio;

        for (int i = this.x; i < endX; i++) {
            for (int j = this.y; j < endY; j++) {
                Position tmp = new Position(i, j, WALK);
                if (map.isWalkable(tmp)) {
                    if (map.getRegion(tmp) != null)
                        this.area[i-this.x][j-this.y] = (byte) (map.getRegion(tmp).getID()-1);
                    else
                        this.area[i-this.x][j-this.y] = -1;
                }
            }
        }
        this.choke = this.calculateChokePoint(region);
    }
    
    private int distance(int x, int y) {
        return Math.abs(x - y);
    }
    
    private double distance(Point a, Point b) {
        return  Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
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
        return (this.choke != null);
    }
    
    public Position getConnection() {
        return (choke != null) ? new Position(choke.x + this.x, choke.y + this.y, WALK):null;
    }
    
    private Point calculateChokePoint(int region) {
        
        double distance = 100000;
        double tmp;
        int cx = -1, cy = -1;
        
        for (int i = 0; i < this.area.length; i++) {
            for (int j = 0; j < this.area[i].length; j++) {
                if ((this.area[i][j] == region) && (this.isChokePoint(i, j, region))) {
                    tmp = this.distance(new Point(i, j), this.c);
                    if (tmp < distance) {
                        distance = tmp;
                        cx = i;
                        cy = j;
                    }
                }
            }
        }
        return ((cx != -1)&&(cy != -1)) ? new Point(cx, cy):null;
    }
    
    private boolean isChokePoint(int x, int y, int region) {
       
        int count1 = 0;
        int count2 = 0;
        
        Point[] points = new Point[8];
        
        points[0] = new Point(x-1, y-1);
        points[1] = new Point(x, y-1);
        points[2] = new Point(x+1, y-1);
        points[3] = new Point(x-1, y);
        points[4] = new Point(x+1, y);
        points[5] = new Point(x-1, y+1);
        points[6] = new Point(x, y+1);
        points[7] = new Point(x+1, y+1);
        
        for (int i = 0; i < points.length; i++) {
            if ((points[i].x >= 0) && (points[i].y >= 0) && (points[i].x < this.area.length) && (points[i].y < this.area[0].length)) {
                if (this.area[points[i].x][points[i].y] != -1) {
                    if (this.area[points[i].x][points[i].y] == region)
                        count1++;
                    else
                        count2++;
                }
            }
        }
        
        return ((count1 > 0) && (count2 > 0));
    }
    
    public void print() {
        for (int i = 0; i < this.area.length; i++) {
            for (int j = 0; j < this.area[i].length; j++) {
                if ((this.choke != null) && (i == this.choke.x) && (j == this.choke.y))
                    System.out.print("[" + this.area[i][j] + "] ");
                else
                    System.out.print(this.area[i][j] + " ");
            }
            System.out.println();
        }
    }
}
