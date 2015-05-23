package UI;
import GameEngine.Move;
import Utils.Globals;
import Utils.SquareState;

import java.awt.*;
import java.util.ArrayList;
public class Traverse
{
	private SquareState[][] mMatrix = null;
	private SquareState mPlayer = null;
	private SquareState mOpponent = null;
	private int mX = 0;
	private int mY = 0;
	private ArrayList<Integer> mFlips = null; // pieces intended to be flipped.
	private ArrayList<Move> mMoves = null; // all possible moves in relation to one specified grid location.
	private ArrayList<Point> mSources = null;
	private boolean mIsValid = false;
	private static final int NUMBER_OF_DIRECTIONS = 8;
	
	private final int[][] directions = new int[][] {{0, -1},	// North.
											 		{1, -1},	// Northeast.
											 		{1, 0},		// East.
											 		{1, 1},		// Southeast.
											 		{0, 1},		// South.
											 		{-1, 1},	// Southwest.
											 		{-1, 0},	// West.
											 		{-1, -1}};	// Northwest.
	
	public Traverse(){return;}
	
	public Traverse(final int x, final int y, final SquareState opponent, final SquareState[][] matrix)
	{
		this.setX(x);
		this.setY(y);
		this.setOpponent(opponent);
		this.setMatrix(matrix);
		this.search();
		return;
	}
	
	public Traverse(final int x, final int y, final SquareState player, final SquareState opponent, final SquareState[][] matrix)
	{
		this.setX(x);
		this.setY(y);
		this.setPlayer(player);
		this.setOpponent(opponent);
		this.setMatrix(matrix);
		this.search();
		return;
	}
	
	public void search()
	{
		this.mMoves = new ArrayList<Move>();
		this.mFlips = new ArrayList<Integer>();
		this.mSources = new ArrayList<Point>();
		int opponentPieces = 0;
		boolean foundMove = false;
		Move aMove = null;
		SquareState target = this.mPlayer;
		
		if(target == null){
			target = SquareState.NONE;
		}
		
		for(int k = 0; k < Traverse.NUMBER_OF_DIRECTIONS; k++) // Search through all eight directions.
		{
			int i = this.mX;
			int j = this.mY;
			opponentPieces = 0;
			foundMove = false;
			ArrayList<Integer> temp = new ArrayList<Integer>();
			boolean done = false;
			while(!done)
			{
				i += this.directions[k][0];
				j += this.directions[k][1];
				if(i >= 0 && j >= 0 && i < Globals.GRID_SIZE_INTEGER && j < Globals.GRID_SIZE_INTEGER){
					if(this.mMatrix[i][j] == this.mOpponent){	// Is an opponent's piece.
						opponentPieces++;
						temp.add(GridMath.getID(i, j));
					}else if(this.mMatrix[i][j] == target){		// square at end of traversal.
						if(this.mMatrix[i][j] == this.mPlayer && opponentPieces > 0){
							this.mSources.add(new Point(i, j));
						}
						foundMove = true;
						break;
					}else{		// Is player's own piece.
						break;
					}
				}else{		// Edge of game board found.
					break;
				}
			}
			if(foundMove == true && opponentPieces > 0){
				aMove = new Move(i, j, opponentPieces);
				this.mMoves.add(aMove);
				if(this.mMatrix[mX][mY] == SquareState.NONE){
					this.mIsValid = true;
				}
				if(temp.size() > 0){
					for(int flip : temp)
						this.mFlips.add(flip);
				}
			}
		}
		return;
	}
	
	public void setMatrix(SquareState[][] matrix)
	{
		if(matrix != null){
			this.mMatrix = matrix;
		}else{
			System.out.println("Traverse.setMatrix - attempt to pass null object.");
		}
		return;
	}
	
	public void setPlayer(SquareState player)
	{
		if(player != null){
			this.mPlayer = player;
		}else{
			System.out.println("Traverse.setPlayer - attempt to pass null object.");
		}
		return;
	}
	
	public void setOpponent(SquareState opponent)
	{
		if(opponent != null){
			this.mOpponent = opponent;
		}else{
			System.out.println("Traverse.setOpponent - attempt to pass null object.");
		}
		return;
	}
	
	public void setX(int x)
	{
		if(x >= 0 && x < Globals.GRID_SIZE_INTEGER){
			this.mX = x;
		}else{
			System.out.println("Traverse.setX - attempt to pass invalid value: " + x);
		}
		return;
	}
	
	public int getX()
	{
		return this.mX;
	}
	
	public void setY(int y)
	{
		if(y >= 0 && y < Globals.GRID_SIZE_INTEGER){
			this.mY = y;
		}else{
			System.out.println("Traverse.setY - attempt to pass invalid value: " + y);
		}
		return;
	}
	
	public int getY()
	{
		return this.mY;
	}
	
	public ArrayList<Move> getMoves()
	{
		if(this.mMoves != null){
			return this.mMoves;
		}else{
			System.out.println("Traverse.getMoves - this.mMoves is null.");
			return null;
		}
	}
	
	// Convenience method that converts Move objects into DBMove objects.
//	public ArrayList<DBMove> getDBMoves()
//	{
//		ArrayList<DBMove> DBMoves = null;
//		if(this.mMoves != null){
//			DBMoves = new ArrayList<DBMove>();
//			for(Move aMove : this.mMoves)
//			{
//				DBMoves.add(new DBMove(this.mX, this.mY, aMove.X(), aMove.Y(), aMove.opponentPieces()));
//			}
//		}else{
//			System.out.println("Traverse.getDBMoves - this.mMoves is null.");
//		}
//		return DBMoves;
//	}
	
	public ArrayList<Integer> getFlips()
	{
		if(this.mFlips != null){
			return this.mFlips;
		}else{
			System.out.println("Traverse.getFlips - this.mFlips is null.");
			return null;
		}
	}
	
	public ArrayList<Point> getSources()
	{
		if(this.mSources != null){
			return this.mSources;
		}else{
			System.out.println("Traverse.getSources - this.mSources is null.");
			return null;
		}
	}
	
	public boolean isValid()
	{
		return this.mIsValid;
	}
	
	
}

