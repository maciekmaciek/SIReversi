package UI;
import GameEngine.Engine;
import Utils.SquareState;
import Utils.Globals;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Blinker //implements Runnable
{
	private MainGUI mGUI = null;
	private Engine mEngine = null;
	private ArrayList<Integer> mSquares = null;
	private boolean mIsOn = false;
	private SquareState mOpponent = null;
	private Timer mainTimer = null;
	private tTask aTask = null;
	private int mFlashCount = 0;
	private int mX = 0;
	private int mY = 0;
	
	public Blinker()	{ return; }
	
	public Blinker(MainGUI gui, Engine engine, int x, int y, ArrayList<Integer> squares, SquareState opponent)
	{
		this.setGUI(gui);
		this.setEngine(engine);
		this.setX(x);
		this.setY(y);
		this.setSquaresList(squares);
		this.setOpponent(opponent);
		this.mEngine.setBlinkingFinished(false);
		aTask = new tTask();
		this.mainTimer = new Timer();
		this.mainTimer.scheduleAtFixedRate(aTask, 0, Globals.BLINKING_SPEED_MILLISECONDS);
		return;
	}
	
	public void setGUI(final MainGUI gui)
	{
		if(gui != null){
			this.mGUI = gui;
		}else{
			System.out.println("Blinker.setGUI - attempt to pass null object.");
		}
		return;
	}
	
	public void setEngine(final Engine engine)
	{
		if(engine != null){
			this.mEngine = engine;
		}else{
			System.out.println("Blinker.setEngine - attempt to pass null object.");
		}
		return;
	}
	
	public void setX(final int x)
	{
		if(x >= 0 && x < Globals.GRID_SIZE_INTEGER){
			this.mX = x;
		}else{
			System.out.println("Blinker.setX - attempt to pass invalid value: " + x);
		}
		return;
	}
	
	public void setY(final int y)
	{
		if(y >= 0 && y < Globals.GRID_SIZE_INTEGER){
			this.mY = y;
		}else{
			System.out.println("Blinker.setY - attempt to pass invalid value: " + y);
		}
		return;
	}
	
	public void setSquaresList(final ArrayList<Integer> squares)
	{
		if(squares != null){
			this.mSquares = squares;
		}else{
			System.out.println("Blinker.setSquaresList - attempt to pass null object.");
		}
		return;
	}
	
	public void setOpponent(final SquareState opponent)
	{
		if(opponent != null){
			this.mOpponent = opponent;
		}else{
			System.out.println("Blinker.setOpponent - attempt to pass null object.");
		}
		return;
	}
	
	public void run()
	{
		this.mEngine.setBlinkingFinished(false);
		aTask = new tTask();
		this.mainTimer = new Timer();
		this.mainTimer.scheduleAtFixedRate(aTask, 0, Globals.BLINKING_SPEED_MILLISECONDS);
		return;
	}
	
	private class tTask extends TimerTask
	{
		public void run() {
			try {
				if(mIsOn == true){
					mGUI.setPiece(mX, mY, mOpponent); // Highlight the move.
					// Highlight the pieces being flipped.
					for(int i = 0; i < mSquares.size(); i++)
					{
						mGUI.setPiece(GridMath.getX(mSquares.get(i)), GridMath.getY(mSquares.get(i)), mOpponent);
					}
					mIsOn = false;
				}else{
					mGUI.setPiece(mX, mY, SquareState.YELLOW);  // Highlight the move.
					// Highlight the pieces being flipped.
					for(int i = 0; i < mSquares.size(); i++)
					{
						mGUI.setPiece(GridMath.getX(mSquares.get(i)), GridMath.getY(mSquares.get(i)), SquareState.BLUE);
					}
					mIsOn = true;
				}
			} catch (Exception e) {
				System.out.println("Timer error has occurred: " + e.getMessage());
			}
			mFlashCount++;
			int blinks = Globals.COMPUTER_MOVE_BLINKS * 2; // Alternate the images this many times.
			if(mFlashCount >= blinks){
				this.cancel();
				mEngine.setBlinkingFinished(true);
				mEngine.postCheckComputerMove();
			}
			return;
		}
	}
}
