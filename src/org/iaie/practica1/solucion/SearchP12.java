package org.iaie.practica1.solucion;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import jnibwapi.JNIBWAPI;
import jnibwapi.Map;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import org.iaie.practica1.solucion.map.HierarchicalMap;
import org.iaie.practica1.solucion.map.TileMap;
import org.iaie.search.Successor;
import org.iaie.search.algorithm.Astar;

public class SearchP12 extends Astar {

    public SearchP12(JNIBWAPI bwapi) {
        super(8, 12, bwapi.getMap());
    }
    
    public SearchP12(TileMap map) {
        super(8, 12, map);
    }

    @Override
    public List<Successor> generateSuccessors(Point actualState) {

        List<Successor> successors = new ArrayList<>();
        Successor[] candidates = new Successor[8];

        candidates[0] = new Successor(new Point(actualState.x-1, actualState.y-1), this.movementCostDiag);
        candidates[1] = new Successor(new Point(actualState.x, actualState.y-1), this.movementCost);
        candidates[2] = new Successor(new Point(actualState.x+1, actualState.y-1), this.movementCostDiag);
        candidates[3] = new Successor(new Point(actualState.x-1, actualState.y), this.movementCost);
        candidates[4] = new Successor(new Point(actualState.x+1, actualState.y), this.movementCost);
        candidates[5] = new Successor(new Point(actualState.x-1, actualState.y+1), this.movementCostDiag);
        candidates[6] = new Successor(new Point(actualState.x, actualState.y+1), this.movementCost);
        candidates[7] = new Successor(new Point(actualState.x+1, actualState.y+1), this.movementCostDiag);

        switch (this.map.getClass().getName()) {
            case "jnibwapi.Map":
                for (Successor candidate : candidates) {
                    if ((candidate.getCoordinate().x < ((Map) this.map).getSize().getWX()) && (candidate.getCoordinate().x >= 0) && (candidate.getCoordinate().y < ((Map) this.map).getSize().getWY()) && (candidate.getCoordinate().y >= 0) && (((Map) this.map).isWalkable(new Position(candidate.getCoordinate().x, candidate.getCoordinate().y, PosType.WALK)))) {
                        successors.add(candidate);
                    }
                }
                return successors;
            case "org.iaie.practica1.solucion.map.TileMap":
                for (Successor candidate : candidates) {
                    if ((candidate.getCoordinate().x < ((TileMap) this.map).getX()) && (candidate.getCoordinate().x >= 0) && (candidate.getCoordinate().y < ((TileMap) this.map).getY()) && (candidate.getCoordinate().y >= 0) && (((TileMap) this.map).isWalkable(candidate.getCoordinate().x, candidate.getCoordinate().y))) {
                        successors.add(candidate);
                    }
                }
                return successors;
        }
        
        return successors;
    }

    @Override
    public double calculateheuristic(Point state, Point goalState) {
        return  Math.sqrt(Math.pow((goalState.x - state.x), 2) + Math.pow((goalState.y - state.y), 2));
    }
}
