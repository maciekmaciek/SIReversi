package GameEngine;
public class Move
{
	private int mX = 0;
	private int mY = 0;
	private int mOpponentPieces = 0;
	private int mBestScore = 0;
	
	public Move(){
	}
	
	public Move(int x, int y)
	{
		this.X(x);
		this.Y(y);
	}
	
	public Move(int x, int y, int numPieces)
	{
		this.X(x);
		this.Y(y);
		this.opponentPieces(numPieces);
		return;
	}
	
	public Move(Move that)
	{
		if(that != null){
			this.mX = that.mX;
			this.mY = that.mY;
			this.mOpponentPieces = that.mOpponentPieces;
		}else{
			System.out.println("Move copy constructor - attempt to pass null object.");
		}
	}
	
	public void X(int x)
	{
		if(x > -1 && x < 8){
			this.mX = x;
		}else{
			System.out.println("Move.X(): Attempt to pass invalid value.");
		}
		return;
	}
	
	public int X()
	{
		return this.mX;
	}
	
	public void Y(int y)
	{
		if(y > -1 && y < 8){
			this.mY = y;
		}else{
			System.out.println("Move.Y(): Attempt to pass invalid value.");
		}
		return;
	}
	
	public int Y()
	{
		return this.mY;
	}
	
	public void opponentPieces(int numPieces)
	{
		if(numPieces > 0 && numPieces < 64){
			this.mOpponentPieces = numPieces;
		}else{
			System.out.println("Move.opponentPieces(): Attempt to pass invalid value.");
		}
		return;
	}
	
	public int opponentPieces()
	{
		return this.mOpponentPieces;
	}
	
	public int getBestScore()
	{
		return this.mBestScore;
	}
	
	public void setBestScore(int score)
	{
		this.mBestScore = score;
		return;
	}
}

