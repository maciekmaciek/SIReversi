package AI;

import GameEngine.Move;

import java.awt.*;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-05-18.
 */
public class Evaluator {

    final private Point[] vertices  = new Point[4];
    final private Point[] nearVertices = new Point[12];
    final int VERTICE_BONUS = 15;
    final int NEAR_VERTICE_BONUS = -10;
    final int NEAR_EDGE_BONUS = -5;
    final int EDGE_BONUS = 5;

    public Evaluator(){
        initVert();
    }

    private void initVert() {
        vertices[0] = new Point(0,0);
        vertices[1] = new Point(0,7);
        vertices[2] = new Point(7,0);
        vertices[3] = new Point(7,7);

        nearVertices[0] = new Point(0, 1);
        nearVertices[0] = new Point(1, 1);
        nearVertices[0] = new Point(1, 0);

        nearVertices[0] = new Point(0, 6);
        nearVertices[0] = new Point(1, 6);
        nearVertices[0] = new Point(1, 7);

        nearVertices[0] = new Point(6, 0);
        nearVertices[0] = new Point(6, 1);
        nearVertices[0] = new Point(7, 1);

        nearVertices[0] = new Point(7, 6);
        nearVertices[0] = new Point(6, 6);
        nearVertices[0] = new Point(6, 7);
    }

    public int evaluate(Move move){
        int result = move.opponentPieces();
        Point p = new Point(move.X(), move.Y());
        result += classify(p);
        return result;
    }

    private int classify(Point coords) {
        if(coords.x == 0 || coords.y == 0 || coords.x == 7 || coords.y == 7){
            return classifyEdge(coords);
        } else {
            for(Point p : nearVertices){
                if(p.equals(coords)){
                    return NEAR_VERTICE_BONUS;
                }
            }

            if(coords.x == 1 || coords.y == 1 || coords.x == 6 || coords.y == 6){
                return NEAR_EDGE_BONUS;
            }
        }
        return 0;
    }

    private int classifyEdge(Point coords) {
        for(Point p : vertices){
            if(p.equals(coords)){
                return VERTICE_BONUS;
            }
        }

        for(Point p : nearVertices){
            if(p.equals(coords)){
                return NEAR_VERTICE_BONUS;
            }
        }

        return EDGE_BONUS;
    }
}
