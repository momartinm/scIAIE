/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iaie.practica1.solucion;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import jnibwapi.Position;
import org.iaie.practica1.solucion.map.HierarchicalMap;
import org.iaie.search.Successor;
import org.iaie.search.algorithm.Astar;

/**
 *
 * @author momartin
 */
public class SearchP13 extends Astar {

    public SearchP13(Object map) {
        super(map);
    }

    @Override
    public List<Successor> generateSuccessors(Point actualState) {
        List<Successor> successors = new ArrayList<>();
        ArrayList<Integer> candidates = ((HierarchicalMap) this.map).getRegions(actualState.x);
        
        for (int i = 0; i < candidates.size(); i++) {
            successors.add(new Successor(new Point(candidates.get(i), 0), 1));
        }
        
        return successors;
    }

    @Override
    public double calculateheuristic(Point state, Point goalState) {
        
        int value = ((HierarchicalMap) this.map).getWalkableTilesByRegions(state.x);
        
        if ((value == 0) || (value < 500)) 
            return 1000000000.00;
        else {
            Position stateCenter = ((HierarchicalMap) this.map).getRegionCenter(state.x);
            Position goalCenter = ((HierarchicalMap) this.map).getRegionCenter(goalState.x);

            return Math.sqrt(Math.pow((goalCenter.getWX() - stateCenter.getWX()), 2) + Math.pow((goalCenter.getWY() - stateCenter.getWY()), 2));
        }
    }
    
}
