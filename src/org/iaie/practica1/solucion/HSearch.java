/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iaie.practica1.solucion;

import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jnibwapi.JNIBWAPI;
import jnibwapi.Map;
import jnibwapi.Position;
import static jnibwapi.Position.PosType.WALK;
import org.iaie.search.Result;
import org.iaie.search.Successor;
import org.iaie.search.algorithm.Astar;
import org.iaie.search.algorithm.HierarchicalSearch;
import org.iaie.practica1.solucion.map.HierarchicalMap;
import org.iaie.tools.Options;

/**
 *
 * @author momartin
 */
public class HSearch extends HierarchicalSearch {

    private final Map bwapiMap;
    private final HierarchicalMap map;
    private final Astar searchL1;
    private Astar searchL2;
    
    public HSearch(JNIBWAPI bwapi) {
        this.bwapiMap = bwapi.getMap();
        this.map = new HierarchicalMap(this.bwapiMap);
        this.searchL1 = new SearchP13(this.map);
        this.searchL1.setDebug(Options.getInstance().isDebug());
    }
    
    @Override
    public List<Successor> generateSuccessors(Point actualState) {
        return null;
    }

    @Override
    public int calculateheuristic(Point state, Point goalState) {
        return 0;
    }

    @Override
    public Result search(Point start, Point end) {
        
        Result global = new Result();
        
        Position startPosition = new Position(start.x, start.y, WALK);
        Position endPosition = new Position(end.x, end.y, WALK);
        
        if ((this.bwapiMap.getRegion(startPosition) != null) && (this.bwapiMap.getRegion(endPosition) != null)) {
        
            int startRegion = this.bwapiMap.getRegion(startPosition).getID()-1;
            int endRegion = this.bwapiMap.getRegion(endPosition).getID()-1;
        
            Point startPoint = start;
            Point endPoint = null;

            Result phase1 = this.searchL1.search(new Point(startRegion, 0), new Point(endRegion, 0));

            if (phase1.getPath() != null) {

                List<Point> regions = phase1.getPath();

                for (int i = 0; i < regions.size(); i++) {
                    endPoint = (i < regions.size()-1) ? this.map.getPointConnection(regions.get(i).x, regions.get(i+1).x):end;

                    if (this.debugMode) {
                        try {
                            this.map.printMap(regions.get(i).x, startPoint, endPoint, "region-" + regions.get(i).x + ".txt");
                        } catch (IOException ex) {
                            Logger.getLogger(HSearch.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    this.searchL2 = new SearchP12(this.map.getRegionMap(regions.get(i).x));
                    this.searchL2.setDebug(Options.getInstance().isDebug());

                    Result resultSearch = this.searchL2.search(startPoint, endPoint);

                    if (resultSearch.getPath() != null) {
                        startPoint = resultSearch.getLastPosition();
                        global.add(resultSearch); 
                    }
                    else {
                        global.removePath();
                        break;
                    }
                }
            }
        }
        return global;
    }
}
