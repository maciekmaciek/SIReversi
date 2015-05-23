package UI;
import Utils.Globals;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class GridPanel extends JPanel
{
	private MainGUI mGUI = null;
	private GraphicsEnvironment mGE = null;
	private GraphicsDevice mGD = null;
	private GraphicsConfiguration mGC = null;
	private int mCurrentGridSize = 0;
	
	public GridPanel()
	{
		this.initializeGraphicsDeviceVariables();
		return;
	}
	
	public GridPanel(final MainGUI aGUI)
	{
		this.mGUI = aGUI;
		this.initializeGraphicsDeviceVariables();
		return;
	}
	
	private void initializeGraphicsDeviceVariables()
	{
		this.mGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.mGD = this.mGE.getDefaultScreenDevice();
		this.mGC = this.mGD.getDefaultConfiguration();
		return;
	}
	
	public synchronized void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D)g;
            int width = this.calculateGridSize();
            if(this.mGUI.getGridSquares() == null){
            	// Show the splash image.
            	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            	g2d.setPaint(Globals.SPLASH_BACKGROUND_COLOR);
				g2d.fill(new Rectangle2D.Double(0, 0, width, width));
				g2d.setPaint(Color.WHITE);
				Font theFont = new Font("Serif", Font.BOLD + Font.ITALIC, 48);
				g2d.setFont(theFont);
				// Center the text.
				TextMetric tm = new TextMetric(Globals.APPLICATION_TITLE, g2d, theFont);
				g2d.drawString(Globals.APPLICATION_TITLE, this.getLeftForTextCenter(width, tm.getTextSize()), 200);
				theFont = new Font("Serif", Font.BOLD, 36);
				g2d.setFont(theFont);
				// Center the text.
				tm = new TextMetric("Click \"New Game\" to begin", g2d, theFont);
				g2d.drawString("Click \"New Game\" to begin", this.getLeftForTextCenter(width, tm.getTextSize()), 300);
			}else{
				// Show the board.
				g2d.setPaint(Globals.BACKGROUND_COLOR);
				g2d.fill(new Rectangle2D.Double(0, 0, width, width));
			}
			//int count = 0;
			ArrayList<GridSquare> squares = this.mGUI.getGridSquares();
			if(squares != null && squares.size() == (Globals.GRID_SIZE_INTEGER * Globals.GRID_SIZE_INTEGER)){
	    	for(GridSquare aSquare : squares)
		    	{
		    		if(aSquare.getCurrentImage() != null){
		    			drawImage(g2d, aSquare.getCurrentImage(), aSquare.getX(), aSquare.getY());
		    			//g2d.drawString("" + count, aPiece.getX() + 20, aPiece.getY() + 20); // just for testing.
		    		}
		    		//count++;
		    	}
			}
        }
		return;
	}
	
	private int calculateGridSize()
	{
		int width = 0;
		if(this.getWidth() < this.getHeight()){
			width = this.getWidth();
		}else{
			width = this.getHeight();
		}
		boolean done = false;
		while(!done)
		{
			if((width - ((Globals.GRID_SIZE_INTEGER + 1) * Globals.GRID_LINE_WIDTH)) % Globals.GRID_SIZE_INTEGER != 0){
				width -= 1;
			}else{
				done = true;
			}
		}
		return width;
	}
	
	// THIS CAN ONLY BE CALLED AFTER THE FRAME IS VISIBLE.
	public synchronized void initializeGridSquares()
	{
		boolean createNew = false;
		// Get the size of the board. Board is square, so only one dimension is necessary.
		int width = this.calculateGridSize();
		int pieceWidth = (int)Math.round(width / Globals.GRID_SIZE_DECIMAL) - 2;
		if(this.mGUI.getGridSquares() == null){
			this.mGUI.resetSquares();
			createNew = true;
		}
		BufferedImage square1 = this.getBlankSquare(pieceWidth);
		Image blank = Toolkit.getDefaultToolkit().createImage(square1.getSource());
		BufferedImage square2 = this.getBlankSquare(pieceWidth);
		Image white = this.getColoredCircle(square2, pieceWidth, Color.WHITE);
		BufferedImage square3 = this.getBlankSquare(pieceWidth);
		Image black = this.getColoredCircle(square3, pieceWidth, Color.BLACK);
		BufferedImage square4 = this.getBlankSquare(pieceWidth);
		Image blue = this.getColoredCircle(square4, pieceWidth, Color.BLUE);
		BufferedImage square5 = this.getBlankSquare(pieceWidth);
		Image yellow = this.getColoredCircle(square5, pieceWidth, Color.YELLOW);
		int count = 0;
		int y = Globals.GRID_LINE_WIDTH;
		for(int j = 0; j < Globals.GRID_SIZE_INTEGER; j++)
		{
			int x = Globals.GRID_LINE_WIDTH;
			for(int i = 0; i < Globals.GRID_SIZE_INTEGER; i++)
			{
				if(createNew == true){
					GridSquare newPiece = new GridSquare(blank, white, black, blue, yellow, x, y, pieceWidth);
					this.mGUI.getGridSquares().add(newPiece);
				}else{
					this.mGUI.getGridSquares().get(count).setImages(blank, white, black, blue, yellow);
					this.mGUI.getGridSquares().get(count).setX(x);
					this.mGUI.getGridSquares().get(count).setY(y);
					this.mGUI.getGridSquares().get(count).setSize(pieceWidth);
				}
				count++;
				x += pieceWidth + Globals.GRID_LINE_WIDTH;
			}
			y += pieceWidth + Globals.GRID_LINE_WIDTH;
		}
		this.mCurrentGridSize = y;
		return;
	}
	
	public synchronized void reinitializeGridSquares()
	{
		// Get the size of the board. Board is square, so only one dimension is necessary.
		int width = this.calculateGridSize();
		int pieceWidth = (int)Math.round(width / Globals.GRID_SIZE_DECIMAL) - 2;

		BufferedImage square1 = this.getBlankSquare(pieceWidth);
		Image blank = Toolkit.getDefaultToolkit().createImage(square1.getSource());
		BufferedImage square2 = this.getBlankSquare(pieceWidth);
		Image white = this.getColoredCircle(square2, pieceWidth, Color.WHITE);
		BufferedImage square3 = this.getBlankSquare(pieceWidth);
		Image black = this.getColoredCircle(square3, pieceWidth, Color.BLACK);
		BufferedImage square4 = this.getBlankSquare(pieceWidth);
		Image blue = this.getColoredCircle(square4, pieceWidth, Color.BLUE);
		BufferedImage square5 = this.getBlankSquare(pieceWidth);
		Image yellow = this.getColoredCircle(square5, pieceWidth, Color.YELLOW);
		int count = 0;
		int y = Globals.GRID_LINE_WIDTH;
		for(int j = 0; j < Globals.GRID_SIZE_INTEGER; j++)
		{
			int x = Globals.GRID_LINE_WIDTH;
			for(int i = 0; i < Globals.GRID_SIZE_INTEGER; i++)
			{
				this.mGUI.getGridSquares().get(count).setImages(blank, white, black, blue, yellow);
				this.mGUI.getGridSquares().get(count).setX(x);
				this.mGUI.getGridSquares().get(count).setY(y);
				this.mGUI.getGridSquares().get(count).setSize(pieceWidth);
				
				count++;
				x += pieceWidth + Globals.GRID_LINE_WIDTH;
			}
			y += pieceWidth + Globals.GRID_LINE_WIDTH;
		}
		this.mCurrentGridSize = y;
		return;
	}
	
	private synchronized int getLeftForTextCenter(final int totalWidth, final Dimension metric)
	{
		return (int)Math.round((totalWidth / 2.0) - (metric.width / 2.0));
	}
	
	public synchronized BufferedImage getBlankSquare(final int size)
	{
		BufferedImage square = this.mGC.createCompatibleImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D)square.getGraphics();
		g2d.setPaint(Globals.BOARD_COLOR);
		g2d.fill(new Rectangle2D.Double(0, 0, size, size));
		return square;
	}
	
	public synchronized Image getColoredCircle(final BufferedImage square, final int size, final Color aColor)
	{
		if(square != null){
			if(aColor != null){
				Graphics2D g2d = (Graphics2D)square.getGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setPaint(aColor);
				g2d.fill(new Ellipse2D.Double(4, 4, size - 8, size - 8));
			}else{
				System.out.println("GridPanel.getColoredCircle - first argument cannot be null.");
			}
		}else{
			System.out.println("GridPanel.getColoredCircle - third argument cannot be null.");
		}
		return Toolkit.getDefaultToolkit().createImage(square.getSource());
	}
	
	/**
	 * drawImage is a convenience method for drawing to a Graphics2D reference.
	 * @param g2d - Graphics2D reference to be drawn to.
	 * @param image - Image object to be drawn to the Graphics2D reference.
	 * @param x - integer horizontal coordinate to begin drawing.
	 * @param y - integer vertical coordinate to begin drawing.
	 */
	public synchronized void drawImage(final Graphics2D g2d, final Image image, final int x, final int y)
    {
        g2d.drawImage(image, x, y, null);
        return;
    }
	
	public int getCurrentGridSize()
	{
		return this.mCurrentGridSize;
	}
}
