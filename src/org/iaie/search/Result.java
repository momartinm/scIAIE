/************************************************************************
 * Planning and Learning Group PLG,
 * Department of Computer Science,
 * Carlos III de Madrid University, Madrid, Spain
 * http://plg.inf.uc3m.es
 * 
 * Copyright 2015, Moises Martinez
 *
 * (Questions/bug reports now to be sent to Moisés Martínez)
 *
 * This file is part of IAIE.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the IAIE nor the names of its contributors may be 
 *       used to endorse or promote products derived from this software without 
 *       specific prior written permission.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with IAIE. If not, see <http://www.gnu.org/licenses/>.
 * 
 ************************************************************************/
 
package org.iaie.search;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jnibwapi.Map;
import jnibwapi.Position;
import static jnibwapi.Position.PosType.WALK;
import jnibwapi.Region;

public class Result {
	
    private long time;
    private double cost;
    private int expandedNodes;
    private int generatedNodes;
    private List<Point> path;
    private int regions;
    
    public Result() {
        this.path = null;
        this.cost = 0.0;
        this.time = 0;
        this.expandedNodes = 0;
        this.generatedNodes = 0;
        this.regions = 0;
    }

    public Result(List<Point> path, int generated, int expanded, long time) {
        this.path = path;
        this.expandedNodes = expanded;
        this.generatedNodes = generated;
        this.time = time;
        this.cost = path.size();
        this.regions = 1;
    }

    public Result(List<Point> path, int generated, int expanded, int cost, long time) {
        this.path = path;
        this.expandedNodes = expanded;
        this.generatedNodes = generated;
        this.time = time;
        this.cost = cost;
    }

    public int getExpandedNodes() {
        return this.expandedNodes;
    }
    
    public int getGeneratedNodes() {
        return this.generatedNodes;
    }

    public long getTime() {
        return this.time;
    }

    public List<Point> getPath() {
        return this.path;
    }

    public double getCost() {
        return this.cost;
    }
    
    public Point getLastPosition() {
        return this.path.get(this.path.size()-1);
    }
    
    public int getRegions() {
        return this.regions;
    }
    
    public void addPath(List<Point> path) {
        if (this.path == null) this.path = new ArrayList<>();
        this.path.addAll(path);
    }
    
    public void removePath() {
        this.path = null;
    }
    
    public void addCost(double cost) {
        this.cost += cost;
    }
    
    public void addTime(double time) {
        this.time += time;
    }
        
    public void addGeneratedNodes(int generated) {
        this.generatedNodes += generated;
    }
    
    public void addExpandedNodes(int expanded) {
        this.expandedNodes += expanded;
    }
    
    public void add(Result aux) {
        if (this.path == null) this.path = new ArrayList<>();
        this.path.addAll(aux.getPath());
        this.cost += aux.getCost();
        this.time += aux.getTime();
        this.generatedNodes += aux.getGeneratedNodes();
        this.expandedNodes += aux.getExpandedNodes();
        this.regions += 1;
    }
    
    public void print(String fileName, boolean validPath) throws IOException {
        
        PrintWriter writer = new PrintWriter(new FileWriter(fileName, false));
        
        System.out.println("********************************************************");
        if (this.path != null) {
            System.out.println("Resumen del proceso de búsqueda");
            writer.println(this.cost);
            System.out.println("Solución encontrada con coste " + this.cost);
            System.out.println("Estado inicial: [" + this.path.get(0).x + "," + this.path.get(0).y + "]");
            System.out.println("Estado final: [" + this.path.get(this.path.size()-1).x + "," + this.path.get(this.path.size()-1).y + "]");
            System.out.println("Nodos expandidos: " + this.expandedNodes);
            writer.println(this.expandedNodes);
            System.out.println("Nodos generados: " + this.generatedNodes);
            writer.println(this.generatedNodes);
            System.out.println("Longitud del camino: " + this.path.size());
            writer.println(this.path.size());
            System.out.println("Camino valido: " + validPath);
            writer.println(validPath);
            System.out.println("Regiones: " + this.regions);
            System.out.println("Tiempo de ejecución: " + this.time + " milisegundos");
            writer.println(this.time);
        }
        else {
            System.out.println("Resumen del proceso de búsqueda");
            System.out.println("Solución no encontrada");
            writer.println("0");
            System.out.println("Nodos expandidos: " + this.expandedNodes);
            writer.println(this.expandedNodes);
            System.out.println("Nodos generados: " + this.generatedNodes);
            writer.println(this.generatedNodes);
            System.out.println("Tiempo de ejecución: " + this.time + " milisegundos");
            writer.println(this.time);
        }
        System.out.println("********************************************************");
        writer.close();
    }
    
    public void savePathToFile(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));

        for (Point point : this.path) {
            writer.println(point.x + " " + point.y);
        }
        writer.close();
    }
    
    public boolean printPathInMap(Map map, String fileName) throws IOException {
        
        boolean valid = true;
        
        if (this.path != null) {
            
            byte[][] temp = new byte[map.getSize().getWX()][map.getSize().getWY()];
            
            for (int x = 0; x < map.getSize().getWX(); x++) {
                for (int y = 0; y < map.getSize().getWY(); y++) {
                    if (map.isWalkable(new Position(x, y, WALK)))
                        temp[x][y] = 1;
                    else
                        temp[x][y] = 0;
                }
            }

            for (Point point : this.path) {
                if (temp[point.x][point.y] == 0) valid = false; 
                temp[point.x][point.y] = 9;
            }

            PrintWriter writer = new PrintWriter(new FileWriter(fileName, false));

            for (int y = 0; y < map.getSize().getWY(); y++) {
                for (int x = 0; x < map.getSize().getWX(); x++) {
                    writer.print(temp[x][y]);
                }
                writer.println();
            }

            writer.close();
        }
        
        return valid;
    }
    
    public void printSolutionByRegions(Map map, String fileName) throws IOException {
        
        if (this.path != null) {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, false));

            for (int i = 0; i < this.path.size(); i++) {

                Region r = map.getRegion(new Position(this.path.get(i).x, this.path.get(i).y, WALK));

                if (r == null)
                    writer.println(this.path.get(i).x + " " + this.path.get(i).y + " -1");
                else 
                    writer.println(this.path.get(i).x + " " + this.path.get(i).y + " " + r.getID());
            }

            writer.close();
        }
    }
}
