package AI;

import GameEngine.Engine;
import GameEngine.Move;
import UI.Traverse;
import Utils.Globals;
import Utils.SquareState;

import java.util.ArrayList;

/**
 * Created by Maciej Wola�ski
 * maciekwski@gmail.com
 * on 2015-05-18.
 */
public class MinMaxStrategy implements Strategy {
    private static final int THEDEPTH = 6;
    private SquareState owner;
    private SquareState opponent;
    private SquareState[][] currentMatrix;
    private Move previousBestMove;
    Engine engine;
    Evaluator evaluator;
    public MinMaxStrategy(Engine engine, SquareState owner, SquareState opponent, SquareState[][] currentMatrix){
        this.owner = owner;
        this.opponent = opponent;
        this.currentMatrix = currentMatrix;
        this.engine = engine;
        evaluator = new Evaluator();
    }
    @Override
    public Move findBestMove() {
        int currentDepth = 0;
        Move bestMove = new Move();
        int highest = -1000;
        ArrayList<Move> moves = engine.findAllPossibleMoves(owner, opponent, currentMatrix);
        if(moves.size() > 0){
            for(Move aMove : moves)
            {
                int temp = findBestMove(aMove, currentDepth+1, currentMatrix, opponent, owner).getBestScore(); //wybierz najlepszy
                if(highest < temp){
                    bestMove = aMove;
                    if(aMove.getBestMaxChild() != null){
                        previousBestMove = aMove.getBestMaxChild().getBestMaxChild(); //do heurystyki, kt�ry bra�
                    }
                    highest = temp;
                    //bestMove.setBestScore(highest);
                }
            }
        }
        //String pl = owner == SquareState.WHITE ? "white" : "black";
        //System.out.println(pl + " (" + bestMove.X() + ", " + bestMove.Y() + "), flipped: " + bestMove.opponentPieces() + ", eval: " + highest);
        return bestMove;
    }

    public Move findBestMove(Move move, int depth, SquareState[][] currentMatrix, SquareState player1, SquareState player2){
        int value = depth%2 == 1 ? evaluator.evaluate(move): -evaluator.evaluate(move);  //na minimizerze eval ruchu maxymizera -> dodawanie
        String type = depth%2 == 1 ? "Minimizer" : "Maximizer";
        if(depth == THEDEPTH){
            move.setBestScore(value);
            System.out.println(type + " " + depth + ",  val: " + move.getBestScore());
            return move;
        } else {
            SquareState[][] tempMatrix = new SquareState[Globals.GRID_SIZE_INTEGER][Globals.GRID_SIZE_INTEGER]; //kopiuj plansz�
            for (int y = 0; y < Globals.GRID_SIZE_INTEGER; y++)
                for (int x = 0; x < Globals.GRID_SIZE_INTEGER; x++)
                    tempMatrix[x][y] = currentMatrix[x][y];

            //wykonaj ruch na planszy DONE
            tempMatrix[move.X()][move.Y()] = player2;
            Traverse t = new Traverse(move.X(), move.Y(), player2, player1, tempMatrix);
            ArrayList flips = t.getFlips();
            engine.flipPieces(flips, player2, tempMatrix, false);

            ArrayList<Move> moves;
            int highest = -1000;
            int lowest = 1000;
            moves = engine.findAllPossibleMoves(player1, player2, tempMatrix);
            if (moves.size() > 0) {
                if (depth % 2 == 1) { //minimizer - minimalizuj
                    for (Move aMove : moves) {
                        int temp = value + findBestMove(aMove, depth+1, tempMatrix, player2, player1).getBestScore();
                        if (lowest > temp) {
                            lowest = temp;
                            move.setBestScore(lowest);
                            move.setBestMaxChild(aMove);

                        }
                    }
                } else { //maximizer, maksymalizuj
                    for (Move aMove : moves) {
                        int temp = value + findBestMove(aMove, depth+1, tempMatrix, player2, player1).getBestScore();
                        if (highest < temp) {
                            highest = temp;
                            move.setBestScore(highest);
                            move.setBestMaxChild(aMove);
                        }
                    }
                }
            }
            System.out.println(type + " " + depth + ",  val: " + move.getBestScore());
            return move;
        }
    }
}
