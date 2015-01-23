package game;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public final class ImageLibrary
{
	BufferedImage [] aoe;
	BufferedImage [][] shot;
	BufferedImage [] archer;
	BufferedImage [] cleric;
	BufferedImage [] mage;
	BufferedImage [] swordsman;
	BufferedImage backBot;
	BufferedImage backTop;
	BufferedImage backTile;
	public ImageLibrary()
	{
		BufferedImage [] Aoe = {loadImage("shootplayeraoe"), loadImage("shootenemyaoe")};
		aoe = Aoe;
		BufferedImage [][] Shot = {loadSet("shootplayer", 5), loadSet("shootenemy", 5)};
		shot = Shot;
		archer = loadSet("goblin_archer", 49);
		cleric = loadSet("goblin_cleric", 32);
		mage = loadSet("goblin_mage", 31);
		swordsman = loadSet("goblin_swordsman", 55);
		backBot = loadImage("level1");
		backTop = loadImage("leveltop1");
		backTile = loadImage("leveltile1");
	}
	public BufferedImage [] loadSet(String s, int length)
	{
		BufferedImage [] set = new BufferedImage[length];
		for(int i = 0; i < length; i++)
		{
			set[i] = loadImage(s+correctDigits(i+1));
		}
		return set;
	}
	/**
	 * Adds 0's before string to make it four digits long
	 * Animations done in flash which when exporting .png sequence end file name with four character number
	 * @return Returns four character version of number
	 */
	protected String correctDigits(int start)
	{
		String end = ""+start;
		while(end.length() < 4)
		{
			end = "0" + end;
		}
		return end;
	}
	public BufferedImage loadImage(String s)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources/"+s+".png"));
		} catch (IOException e) {
		}
		return img;
	}
}