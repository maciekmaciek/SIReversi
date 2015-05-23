package GameEngine;
import AI.Evaluator;
import UI.*;
import Utils.Globals;
import Utils.SquareState;

import java.util.ArrayList;
import java.util.Random;
public class Engine
{
	private final SquareState mPlayer = SquareState.WHITE;
	private final SquareState mComputer = SquareState.BLACK;
	private int humanPlayers;
	//private final int mTotalMovesAhead = 3;
	//private final int mCornerBias = 10;
	//private final int mEdgeBias = 5;
	
	private MainGUI mGUI = null;
	private int mPlayerMoves = 0;
	private int mComputerMoves = 0;
	private SquareState[][] matrix = new SquareState[Globals.GRID_SIZE_INTEGER][Globals.GRID_SIZE_INTEGER]; // Game board dimensions.
	//private Node mRoot = null;
	//private int mMovesAhead = 0;
	private boolean blinkingIsFinished = true;
	private boolean gameInProgress = false;
	
	public Engine()
	{
		this.initialize();
		return;
	}
	
	private void initialize()
	{
		this.mGUI = new MainGUI(this);
		
		// Add items to memory matrix.
		for(int i = 0; i < Globals.GRID_SIZE_INTEGER; i++)
			for(int j = 0; j < Globals.GRID_SIZE_INTEGER; j++)
				this.matrix[j][i] = SquareState.NONE;
		
		return;
	}
	
	public void resetGame(int humanPlayers)
	{
		this.humanPlayers = humanPlayers;
		this.mGUI.setAllowResizeFlag(false);
		
		// Create grid squares.
		this.mGUI.getGridPanel().initializeGridSquares();
		
		for(int i = 0; i < Globals.GRID_SIZE_INTEGER; i++)
			for(int j = 0; j < Globals.GRID_SIZE_INTEGER; j++)
			{
				this.matrix[j][i] = SquareState.NONE;
				this.mGUI.setPiece(j, i, SquareState.NONE);
			}
		
		this.randomlyPlaceStartPositions();
		
		this.updateStatusPanel();
		this.gameInProgress = true;
		this.mGUI.setAllowResizeFlag(true);
		if(humanPlayers == 0){
			performWhiteMove(0, 0);
		}
		return;
	}
	
	private void randomlyPlaceStartPositions()
	{
		SquareState playerA = null;
		SquareState playerB = null;
		boolean first = new Random().nextBoolean();
		if(first){
			playerA = SquareState.WHITE;
			playerB = SquareState.BLACK;
		}else{
			playerA = SquareState.BLACK;
			playerB = SquareState.WHITE;
		}
		
		this.mGUI.setPiece(3, 3, playerA);
		this.matrix[3][3] = playerA;
		this.mGUI.setPiece(4, 4, playerA);
		this.matrix[4][4] = playerA;
		this.mGUI.setPiece(3, 4, playerB);
		this.matrix[3][4] = playerB;
		this.mGUI.setPiece(4, 3, playerB);
		this.matrix[4][3] = playerB;
		
		return;
	}
	
	public void resetGUIGraphics()
	{
		for(int y = 0; y < Globals.GRID_SIZE_INTEGER; y++)
		{
			for(int x = 0; x < Globals.GRID_SIZE_INTEGER; x++)
			{
				if(this.matrix[x][y] != null){
					this.mGUI.setPiece(x, y, this.matrix[x][y]);
				}
			}
		}
		return;
	}
	
	public void setBlinkingFinished(final boolean isFinished)
	{
		this.blinkingIsFinished = isFinished;
		return;
	}
	
	public synchronized boolean performMove(final int x, final int y, final SquareState player, final SquareState opponent)
	{
		boolean isValid = false;
		Traverse t = new Traverse(x, y, player, opponent, this.matrix);
		if(t.isValid()){
			if(player == SquareState.WHITE){
				this.mGUI.setPiece(x, y, SquareState.WHITE);
				this.matrix[x][y] = SquareState.WHITE;
			}else{
				this.mGUI.setPiece(x, y, SquareState.BLACK);
				this.matrix[x][y] = SquareState.BLACK;
			}
			t = new Traverse(x, y, player, opponent, this.matrix);
			ArrayList<Integer> flips = t.getFlips();
			flipPieces(flips, player, this.matrix, true);
			if(/*player == this.mComputer &&*/ flips.size() > 0){
				Blinker blink = new Blinker(this.mGUI, this, x, y, flips, player);
				/*try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}

			isValid = true;
		}
		return isValid;
	}
	
	public void performWhiteMove(final int x, final int y)
	{
		if(this.blinkingIsFinished == true) {
			if (humanPlayers == 1) {
				boolean moveMade = this.performMove(x, y, this.mPlayer, this.mComputer);
				/*this.updateStatusPanel();
				if ((moveMade == true || this.mPlayerMoves <= 0) && this.gameInProgress == true) {
					if (this.mComputerMoves > 0) {
						this.performBlackMove();    // Computer's turn immediately follows human player.
					} else {
						this.mGUI.displayMessageWindow("Computer Has No Moves", "The computer has no moves to take.\n\nPlease take another turn.");
					}
				}*/

			} else if (humanPlayers == 0) {
				Move toDo = findBestMove(this.mPlayer, this.mComputer);

				boolean moveMade = this.performMove(toDo.X(), toDo.Y(), this.mPlayer, this.mComputer);
				/*this.updateStatusPanel();
				if ((moveMade == true || this.mPlayerMoves <= 0) && this.gameInProgress == true) {
					if (this.mComputerMoves > 0) {
						this.performBlackMove();    // Computer's turn immediately follows human player.
					} else {
						//this.mGUI.displayMessageWindow("Computer Has No Moves", "The computer has no moves to take.\n\nPlease take another turn.");
						this.performWhiteMove(0, 0);
					}
				}*/
			}
			//postCheckMove(SquareState.WHITE);
		}
		return;
	}
	
	public void performBlackMove()///////////////////////////////////////////////////////////////
	{
		if(this.blinkingIsFinished == true) {
			Move bestMove = findBestMove(this.mComputer, this.mPlayer);
			this.performMove(bestMove.X(), bestMove.Y(), this.mComputer, this.mPlayer);
			//postCheckMove(SquareState.BLACK);

			/*if (humanPlayers == 0) {
				performWhiteMove(0, 0);
			}*/
		}
		return;
	}


	private Move findBestMove(SquareState attack, SquareState defense){
		Move bestMove = new Move();
		Evaluator eval = new Evaluator();
		int highest = -100;
		ArrayList<Move> moves = this.findAllPossibleMoves(attack, defense, this.matrix);
		if(moves.size() > 0){
			for(Move aMove : moves)
			{
				int temp = eval.evaluate(aMove);
				//where magic happens TODO
				if(highest < temp){
					bestMove = new Move(aMove);
					highest = temp;
				}
			}
		}
		String pl = attack == SquareState.WHITE ? "white" : "black";
		System.out.println(pl + " (" + bestMove.X() + ", " + bestMove.Y() + "), flipped: " + bestMove.opponentPieces() + ", eval: " + highest);
		return bestMove;
	}

	public synchronized void postCheckMove(SquareState player)
	{
		this.updateStatusPanel();
		if(player == SquareState.BLACK) {
			if (this.mPlayerMoves <= 0 && this.mComputerMoves > 0) {
				// Black gets to go again.
				if (humanPlayers == 1) {
					this.mGUI.displayMessageWindow("You Have No Moves", "You have no moves at the moment.\n\nThe computer will take another turn.");
				}
				this.performBlackMove();
			} else if(humanPlayers == 0){
				this.performWhiteMove(0,0);
			}
		} else if(player == SquareState.WHITE){
			if (this.mPlayerMoves > 0 && this.mComputerMoves <= 0) {
				// White gets to go again.
				if (humanPlayers == 1) {
					this.mGUI.displayMessageWindow("Computer Has No Moves", "The computer has no moves to take.\n\nPlease take another turn.");
				} else {
					this.performWhiteMove(0,0);
				}
			} else {
				this.performBlackMove();
			}
		}
		return;
	}
	
	/**
	 * findAllPossibleMoves repeatedly calls findValidMoves to retrieve a list of all possible moves for the specified player.
	 * @param player - piece representing current player.
	 * @param opponent - not the current player.
	 * @return - ArrayList of type, Move.
	 */
	private ArrayList<Move> findAllPossibleMoves(final SquareState player, final SquareState opponent, final SquareState[][] aMatrix)
	{
		// Traverse the full grid for specified player pieces.
		ArrayList<Move> allPossibleMoves = new ArrayList<Move>();
		for(int y = 0; y < Globals.GRID_SIZE_INTEGER; y++)
			for(int x = 0; x < Globals.GRID_SIZE_INTEGER; x++)
				if(aMatrix[x][y] == player){
					Traverse t = new Traverse(x, y, opponent, aMatrix);
					ArrayList<Move> someMoves = t.getMoves();
					// Don't want to double-count moves, only tally the pieces that can be taken.
					for(Move thisMove : someMoves)
					{
						boolean found = false;
						for(Move thatMove : allPossibleMoves)
						{
							if(thisMove.X() == thatMove.X() && thisMove.Y() == thatMove.Y()){
								thatMove.opponentPieces(thatMove.opponentPieces() + thisMove.opponentPieces());
								found = true;
								break;
							}
						}
						if(!found)
							allPossibleMoves.add(thisMove);
						
					}
				}
		return allPossibleMoves;
	}
	
	/**
	 * flipPieces reverses opponent pieces one a player piece has been set.
	 */
	private void flipPieces(final ArrayList<Integer> flips, final SquareState player, final SquareState[][] aMatrix, final boolean isActualMove)
	{
		if(flips.size() > 0){
			for(int flip : flips)
			{
				int i = GridMath.getX(flip);
				int j = GridMath.getY(flip);
				if(player == SquareState.WHITE){
					aMatrix[i][j] = SquareState.WHITE;
					if(isActualMove){							// An actual move would be displayed on the board.
						this.mGUI.setPiece(i, j, SquareState.WHITE);
					}
				}else{
					aMatrix[i][j] = SquareState.BLACK;
					if(isActualMove){
						this.mGUI.setPiece(i, j, SquareState.BLACK);
					}
				}
			}
		}
		return;
	}
	
	/**
	 * Counts the number of pieces the specified player has on the game board.
	 * @param player - piece.
	 * @return - integer.
	 */
	public int countPieces(final SquareState player)
	{
		int count = 0;
		for(int y = 0; y < Globals.GRID_SIZE_INTEGER; y++)
			for(int x = 0; x < Globals.GRID_SIZE_INTEGER; x++)
				if(this.matrix[x][y] == player)
					count++;
		return count;
	}
	
	public void updateStatusPanel()
	{
		int aCount = 0;
		int bCount = 0;
		ArrayList<Move> aList = this.findAllPossibleMoves(this.mPlayer, this.mComputer, this.matrix);
		this.mPlayerMoves = aList.size();
		this.mGUI.setPlayerMovesLabel(String.valueOf(this.mPlayerMoves));
		aCount = this.countPieces(mPlayer);
		this.mGUI.setPlayerPiecesLabel(String.valueOf(aCount));
		aList.clear();
		aList = this.findAllPossibleMoves(this.mComputer, this.mPlayer, this.matrix);
		this.mComputerMoves = aList.size();
		this.mGUI.setComputerMovesLabel(String.valueOf(this.mComputerMoves));
		bCount += this.countPieces(mComputer);
		this.mGUI.setComputerPiecesLabel(String.valueOf(bCount));
		// Check if game is finished.
		// It's finished if both players have no more moves, and/or all squares have been filled. 
		if(aCount + bCount >= (Globals.GRID_SIZE_INTEGER * Globals.GRID_SIZE_INTEGER) || (this.mPlayerMoves <= 0 && this.mComputerMoves <= 0)){
			this.gameInProgress = false;
			this.displayFinalMessage(aCount, bCount);
		}
		return;
	}
	
	private void displayFinalMessage(final int playerCount, final int computerCount)
	{
		if(playerCount > computerCount){
			this.mGUI.displayMessageWindow("Congratulations", "Whites have won");
		}else if(playerCount < computerCount){
			this.mGUI.displayMessageWindow("Sorry", "Blacks have won.");
		}else{
			this.mGUI.displayMessageWindow("Final", "Game has ended in a tie.");
		}
		return;
	}
	
	
	
	
	
}
