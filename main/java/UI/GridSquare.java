package UI;
import java.awt.*;
import java.util.Hashtable;

public class GridSquare
{
	private int mX = 0;		// integer value representing a horizontal grid location.
	private int mY = 0;		// integer value representing a vertical grid location.
	private int mSize = 0;	// integer value for grid square width.
	private Image mCurrentImage = null;		// The currently visible image.
	private String mCurrentColor = null;	// The current game state of this piece.
	private Hashtable<String, Image> mImages = null; // Images this piece can display.
	
	public GridSquare()	{ return; }
	
	/**
	 * Piece constructor.  A convenience constructor to fully initialize a Piece object.
	 * @param blank - Image object representing a blank grid square.
	 * @param white - Image object representing a grid square with a white piece.
	 * @param black - Image object representing a grid square with a black piece.
	 * @param blue - Image object representing a grid square with a blue piece.
	 * @param yellow - Image object representing a grid square with a yellow piece.
	 * @param x - integer value representing a horizontal grid location.
	 * @param y - integer value representing a vertical grid location.
	 * @param size - integer value for grid square width.
	 */
	public GridSquare(final Image blank, final Image white, final Image black, final Image blue, final Image yellow, final int x, final int y, final int size)
	{
		this.setImages(blank, white, black, blue, yellow);
		this.setX(x);
		this.setY(y);
		this.setSize(size);
		return;
	}
	
	public void setImages(final Image blank, final Image white, final Image black, final Image blue, final Image yellow)
	{
		if(blank != null && white != null && black != null && blue != null && yellow != null){
			this.mImages = new Hashtable<String, Image>();
			this.mImages.put("blank", blank);
			this.mImages.put("white", white);
			this.mImages.put("black", black);
			this.mImages.put("blue", blue);
			this.mImages.put("yellow", yellow);
			this.mCurrentImage = blank;
			this.mCurrentColor = "blank";
		}else{
			System.out.println("Piece.setImages - attempt to pass null object.");
		}
		return;
	}
	
	public Image getCurrentImage()
	{
		return this.mCurrentImage;
	}
	
	public String getCurrentColor()
	{
		return this.mCurrentColor;
	}
	
	public synchronized void switchImage(final String name)
	{
		if(name != null && name.length() > 0){
			if(this.mImages.containsKey(name)){
				this.mCurrentImage = this.mImages.get(name);
				this.mCurrentColor = name;
			}else{
				System.out.println("Piece.switchImage - key name not recognized: " + name);
				this.mCurrentImage = null;
				this.mCurrentColor = null;
			}
		}else{
			System.out.println("Piece.switchImage - attempt to pass null or empty object.");
		}
		return;
	}
	
	public void setX(final int x)
	{
		if(x >= 0){
			this.mX = x;
		}else{
			System.out.println("Piece.setX - attempt to pass invalid value: " + x);
		}
		return;
	}
	
	public int getX()
	{
		return this.mX;
	}
	
	public void setY(final int y)
	{
		if(y >= 0){
			this.mY = y;
		}else{
			System.out.println("Piece.setY - attempt to pass invalid value: " + y);
		}
		return;
	}
	
	public int getY()
	{
		return this.mY;
	}
	
	public void setSize(final int size)
	{
		if(size > 0){
			this.mSize = size;
		}else{
			System.out.println("Piece.setSize - attempt to pass invalid value: " + size);
		}
		return;
	}
	
	public int getSize()
	{
		return this.mSize;
	}
	
}
