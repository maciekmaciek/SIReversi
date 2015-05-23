
/*
 *	The formula, ID = (8 * y) + x, creates a serial number identifying grid placement.
 *	The formula, x = mod(ID, 8), retrieves the original x-coordinate.
 *	The formula, y = floor(ID / 8), retrieves the original y-coordinate.
 */
package UI;

import Utils.Globals;

public class GridMath
{
	public GridMath()	{ return; }
	
	/**
	 * The formula, x = mod(ID, 8), retrieves the original x-coordinate from the ID.
	 * @param square - integer representing the ID number.
	 * @return - integer, x-coordinate.
	 */
	public static int getX(int square)
	{
		return (int)(square % Globals.GRID_SIZE_DECIMAL);
	}
	
	/**
	 * The formula, y = floor(ID / 8), retrieves the original y-coordinate from the ID.
	 * @param square - integer representing the ID number.
	 * @return - integer, y-coordinate.
	 */
	public static int getY(int square)
	{
		return (int)Math.floor(square / Globals.GRID_SIZE_DECIMAL);
	}
	
	/**
	 * The formula: ID = (8 * y) + x, creates a serial number identifying grid placement.
	 * @param x - integer.
	 * @param y - integer.
	 * @return - integer identifying grid placement.
	 */
	public static int getID(int x, int y)
	{
		return (Globals.GRID_SIZE_INTEGER * y) + x;
	}
}
