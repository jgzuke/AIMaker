/**
 * variables for wall objects
 */
package game;
abstract public class Wall
{
	protected View control;
	protected int playerRollWidth = 5;
	protected int humanWidth = 10;
	protected boolean tall;
	/**
	 * what happens every frame for walls interacting with players and enemies
	 */
	abstract protected void frameCall();
}