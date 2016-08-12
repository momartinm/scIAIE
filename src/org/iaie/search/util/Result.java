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
 *     * Neither the name of the IAIE  nor the names of its contributors may 
 *       be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
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

package org.iaie.search.util;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jnibwapi.Map;
import jnibwapi.Position;
import static jnibwapi.Position.PosType.WALK;
import jnibwapi.Region;

public class Result {
	
    private final long time;
    private final double cost;
    private final int expandedNodes;
    private final List<Point> path;

    public Result(List<Point> path, int nodes, long time) {
        this.path = path;
        this.expandedNodes = nodes;
        this.time = time;
        this.cost = path.size();
    }

    public Result(List<Point> path, int nodes, int cost, long time) {
        this.path = path;
        this.expandedNodes = nodes;
        this.time = time;
        this.cost = cost;
    }

    public int getExpandedNodes() {
        return this.expandedNodes;
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
    
    public void savePath(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));

        for (Point point : this.path) {
            writer.println(point.x + " " + point.y);
        }
        writer.close();
    }
    
    public void printPath() {
        
    }
    
    public void printPathInMap(Map map, String fileName) throws IOException {
        
        byte[] temp = new byte[map.getSize().getWX()*map.getSize().getWY()];
        
        for (int x = 0; x < map.getSize().getWX(); x++) {
            for (int y = 0; y < map.getSize().getWY(); y++) {
                if (map.isWalkable(new Position(x, y, WALK)))
                    temp[x + x *y] = 1;
                else
                    temp[x + x *y] = 0;
            }
        }
        
        for (int i = 0; i < this.path.size(); i++) {
            temp[this.path.get(i).x + this.path.get(i).x + this.path.get(i).y] = 9;
        }

        PrintWriter writer = new PrintWriter(new FileWriter(fileName));

        writer.print(temp[0]);
        
        for (int i = 1; i < this.path.size(); i++) {
            if (temp[i] % map.getSize().getWX() == 0) {
                writer.println();
            }
            else {
                writer.print(temp[i]);
            }
        }
        
        writer.close();
    }
    
    public void generateSolution(Map map, String fileName) {
        
        for (int i = 0; i < this.path.size(); i++) {
            
            Region r = map.getRegion(new Position(this.path.get(i).x, this.path.get(i).y, WALK));
            
            if (r != null)
                System.out.println(this.path.get(i).x + " " + this.path.get(i).y + " -1");
            else 
                System.out.println(this.path.get(i).x + " " + this.path.get(i).y + " " + r.getID());
        }
    }
}
