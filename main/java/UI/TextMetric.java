package UI;
import java.awt.*;
import java.awt.image.BufferedImage;
public class TextMetric
{
	private GraphicsEnvironment mGE = null;
	private GraphicsDevice mGD = null;
	private GraphicsConfiguration mGC = null;
	private String mText = null;
	private Font mFont = null;
	private Graphics2D mG2D = null;
	
	public TextMetric()
	{
		this.initGraphicsVariables();
		return;
	}
	
	public TextMetric(String text, Font theFont)
	{
		this.initGraphicsVariables();
		this.setText(text);
		this.setFont(theFont);
		return;
	}
	
	public TextMetric(String text, Graphics2D g2d, Font theFont)
	{
		this.initGraphicsVariables();
		this.setText(text);
		this.setGraphics2D(g2d);
		this.setFont(theFont);
		return;
	}
	
	private void initGraphicsVariables()
	{
		this.mGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.mGD = this.mGE.getDefaultScreenDevice();
		this.mGC = this.mGD.getDefaultConfiguration();
		return;
	}
	
	public Dimension getTextSize()
	{
		BufferedImage tempImage = this.mGC.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D)tempImage.getGraphics();
		Dimension result = new Dimension();
		FontMetrics metrics = g2d.getFontMetrics(this.mFont);
		result.height = metrics.getHeight();
		result.width = metrics.stringWidth(this.mText);
		return result;
	}
	
	public Dimension getTextSizeFromGraphics2D()
	{
		Dimension result = new Dimension();
		FontMetrics metrics = this.mG2D.getFontMetrics(this.mFont);
		result.height = metrics.getHeight();
		result.width = metrics.stringWidth(this.mText);
		return result;
	}
	
	public void setText(String text)
	{
		if(text != null){
			if(text.length() > 0){
				this.mText = text;
			}else{
				System.out.println("TextMetric.setText - attempt to pass empty string.");
			}
		}else{
			System.out.println("TextMetric.setText - attempt to pass null object.");
		}
		return;
	}
	
	public void setFont(Font theFont)
	{
		if(theFont != null){
			this.mFont = theFont;
		}else{
			System.out.println("TextMetric.setFont - attempt to pass null object.");
		}
		return;
	}
	
	public void setGraphics2D(Graphics2D g2d)
	{
		if(g2d != null){
			this.mG2D = g2d;
		}else{
			System.out.println("TextMetric.setGraphics2D - attempt to pass null object.");
		}
		return;
	}
}
