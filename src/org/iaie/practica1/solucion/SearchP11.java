package org.iaie.practica1.solucion;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import jnibwapi.JNIBWAPI;
import jnibwapi.Map;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import org.iaie.practica1.solucion.map.TileMap;
import org.iaie.search.Successor;
import org.iaie.search.algorithm.Astar;

public class SearchP11 extends Astar {

    public SearchP11(JNIBWAPI bwapi) {
        super(1, 1, bwapi.getMap());
    }
    
    public SearchP11(TileMap map) {
        super(1, 1, map);
    }

    @Override
    public List<Successor> generateSuccessors(Point actualState) {

        List<Successor> successors = new ArrayList<>();
        Point[] candidates = new Point[4];

        candidates[0] = new Point(actualState.x+1, actualState.y);
        candidates[1] = new Point(actualState.x-1, actualState.y);
        candidates[2] = new Point(actualState.x, actualState.y+1);
        candidates[3] = new Point(actualState.x, actualState.y-1);

        switch (this.map.getClass().getName()) {
            case "jnibwapi.Map":
                for (Point candidate : candidates) {
                    if ((candidate.x < ((Map) this.map).getSize().getWX()) && (candidate.y < ((Map) this.map).getSize().getWY()) && (((Map) this.map).isWalkable(new Position(candidate.x, candidate.y, PosType.WALK)))) {
                        successors.add(new Successor(candidate, this.movementCost));
                    }
                }
                return successors;
            case "org.iaie.practica1.solucion.map.TileMap":
                for (Point candidate : candidates) {
                    if ((candidate.x < ((TileMap) this.map).getX()) && (candidate.y < ((TileMap) this.map).getY()) && (((TileMap) this.map).isWalkable(candidate.x, candidate.y))) {
                        successors.add(new Successor(candidate, this.movementCost));
                    }
                }
                return successors;
        }
        return successors;
    }

    @Override
    public double calculateheuristic(Point state, Point goalState) {
        return Math.sqrt(Math.pow((goalState.x - state.x), 2) + Math.pow((goalState.y - state.y), 2));
    }
}
