package game;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public final class ImageLibrary
{
	BufferedImage [] aoe;
	BufferedImage [] shot;
	public ImageLibrary()
	{
		aoe = loadSet("aoe", 4);
		shot = loadSet("shot", 4);
	}
	public BufferedImage [] loadSet(String s, int length)
	{
		BufferedImage [] set = new BufferedImage[length];
		for(int i = 0; i < length; i++)
		{
			set[i] = loadImage(s+correctDigits(i));
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
		String end = Integer.toString(start);
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
			img = ImageIO.read(new File(s+".png"));
		} catch (IOException e) {
		}
		return img;
	}
}