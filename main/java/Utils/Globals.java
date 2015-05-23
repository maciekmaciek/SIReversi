package Utils;

import java.awt.*;
public class Globals
{
	public static final int MINIMUM_WINDOW_WIDTH = 620;
	public static final int MINIMUM_WINDOW_HEIGHT = 706;
	public static final Color SPLASH_BACKGROUND_COLOR = new Color(0, 153, 0);
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color BOARD_COLOR = new Color(0, 153, 0);
	public static final int GRID_SIZE_INTEGER = 8;
	public static final double GRID_SIZE_DECIMAL = GRID_SIZE_INTEGER * 1.0;
	public static final int GRID_LINE_WIDTH = 2;
	
	public static final String APPLICATION_TITLE = "Reversi 0.1";
	public static final int COMPUTER_MOVE_BLINKS = 3;
	public static final int BLINKING_SPEED_MILLISECONDS = 300;
	
	public static final int[][] START_POS = new int[][]{{3, 3}, {4, 3}, {3, 4}, {4, 4}};
	
}
