package game;

import java.awt.Color;
import java.util.ArrayList;

/*
 * hold descriptions of all objects in environment
 */
public final class Objects
{
	private Controller control;
	/*
	 * object that stores a panel, the five points and color
	 */
	public class Panel
	{
		int [][] points;
		int color;
		public Panel(int [][] pointsSet, int colorSet)
		{
			points = pointsSet;
			color = colorSet;
		}
	}
	// all panels must have a center as their last point
	protected ArrayList<Panel> panels = new ArrayList<Panel>();
	// each point is {x, y, z}
	// everything is in cm for sizing.
	public Objects(Controller controlSet)
	{
		control = controlSet;
		makeBackGround();
	}
	private void makeBackGround()
	{
		int a = 10000;
		makeJZ();
		makeArrow(500, 0, 0);
		makeArrow(-500, 0, 0);
		makeArrow(500, -1500, 0);
		makeArrow(-500, -1500, 0);
		makeArrow(500, -2500, 0);
		makeArrow(-500, -2500, 0);
		makeCube(425, 200, 50, 50, Color.);
		makeCube(825, 200, 50, 50, Color.rgb(200, 100, 100));
		makeCube(425, -200, 50, 50, Color.rgb(100, 200, 100));
		makeCube(825, -200, 50, 50, Color.rgb(100, 100, 200));
	}
	private int returnPatternIndex(String s)
	{
		if(s.equals("ground")) {
			return 0;
		} else if(s.equals("ground")) {
			return 1;
		} else if(s.equals("ground")) {
			return 2;
		} else if(s.equals("ground")) {
			return 3;
		} else if(s.equals("ground")) {
			return 4;
		} else if(s.equals("ground")) {
			return 5;
		} else if(s.equals("ground")) {
			return 6;
		} else
		{
			return 7;
		}
	}
	private void makeArrow(int y, int x, int z)
	{
		int [][] arrow = { 
				{x+37, y, z+100}, 
				{x+150, y, z+200}, 
				{x+37, y, z+300}, 
				{x+37, y, z+239}, 
				{x-150, y, z+239}, 
				{x-150, y, z+161}, 
				{x+37, y, z+161}
			};
		makePanel(addMid(arrow), Color.rgb(255, 255, 0));
	}
	private void makeJZ()
	{
		int [][] jFront = { 
			{500, 216, 169}, 
			{500, 216, 119}, 
			{500, 163, 68}, 
			{500, 99, 68}, 
			{500, 41, 121}, 
			{500, 41, 342}, 
			{500, 96, 342}, 
			{500, 96, 148}, 
			{500, 130, 114}, 
			{500, 164, 146}, 
			{500, 164, 169}
		};
		int [][] jBack = { 
			{550, 216, 169}, 
			{550, 216, 119}, 
			{550, 163, 68}, 
			{550, 99, 68}, 
			{550, 41, 121}, 
			{550, 41, 342}, 
			{550, 96, 342}, 
			{550, 96, 148}, 
			{550, 130, 114}, 
			{550, 164, 146}, 
			{550, 164, 169}
		};
		int [][][] jS1 = {
			{{500, 216, 169},	{500, 216, 119}, 	{550, 216, 119}, 	{550, 216, 169}},
			{{500, 216, 119},	{500, 163, 68}, 	{550, 163, 68}, 	{550, 216, 119}},
			{{500, 163, 68},	{500, 99, 68}, 		{550, 99, 68}, 		{550, 163, 68}},
			{{500, 99, 68}, 	{500, 41, 121}, 	{550, 41, 121}, 	{550, 99, 68}},
			{{500, 41, 121}, 	{500, 41, 342}, 	{550, 41, 342}, 	{550, 41, 121}},
			{{500, 41, 342}, 	{500, 96, 342}, 	{550, 96, 342}, 	{550, 41, 342}},
			{{500, 96, 342}, 	{500, 96, 148}, 	{550, 96, 148}, 	{550, 96, 342}},
			{{500, 96, 148}, 	{500, 130, 114}, 	{550, 130, 114}, 	{550, 96, 148}},
			{{500, 130, 114}, 	{500, 164, 146}, 	{550, 164, 146}, 	{550, 130, 114}},
			{{500, 164, 146}, 	{500, 164, 169}, 	{550, 164, 169}, 	{550, 164, 146}},
			{{500, 164, 169}, 	{500, 216, 169}, 	{550, 216, 169}, 	{550, 164, 169}}};
		
		int [][] zFront = { 
			{500, 6, 69}, 
			{500, -216, 69}, 
			{500, -216, 115}, 
			{500, -60, 115}, 
			{500, -210, 299}, 
			{500, -210, 342}, 
			{500, -10, 342}, 
			{500, -10, 296}, 
			{500, -137, 296}, 
			{500, 6, 119}
		};
		int [][] zBack = { 
			{550, 6, 69}, 
			{550, -216, 69}, 
			{550, -216, 115}, 
			{550, -60, 115}, 
			{550, -210, 299}, 
			{550, -210, 342}, 
			{550, -10, 342}, 
			{550, -10, 296}, 
			{550, -137, 296}, 
			{550, 6, 119}
		};
		int [][][] zS1 = {
			{{500, 6, 69}, 		{500, -216, 69}, 	{550, -216, 69}, 	{550, 6, 69}},
			{{500, -216, 69}, 	{500, -216, 115}, 	{550, -216, 115}, 	{550, -216, 69}},
			{{500, -216, 115}, 	{500, -60, 115}, 	{550, -60, 115}, 	{550, -216, 115}},
			{{500, -60, 115}, 	{500, -210, 299}, 	{550, -210, 299}, 	{550, -60, 115}},
			{{500, -210, 299}, 	{500, -210, 342}, 	{550, -210, 342}, 	{550, -210, 299}},
			{{500, -210, 342}, 	{500, -10, 342}, 	{550, -10, 342}, 	{550, -210, 342}},
			{{500, -10, 342}, 	{500, -10, 296}, 	{550, -10, 296}, 	{550, -10, 342}},
			{{500, -10, 296}, 	{500, -137, 296}, 	{550, -137, 296}, 	{550, -10, 296}},
			{{500, -137, 296}, 	{500, 6, 119}, 		{550, 6, 119}, 		{550, -137, 296}},
			{{500, 6, 119}, 	{500, 6, 69}, 		{550, 6, 69}, 		{550, 6, 119}}};
		makePanel(addMid(jFront), Color.rgb(0, 0, 255));
		makePanel(addMid(zFront), Color.rgb(0, 0, 255));
		makePanel(addMid(jBack), Color.rgb(0, 0, 255));
		makePanel(addMid(zBack), Color.rgb(0, 0, 255));
		for(int i = 0; i < 11; i++)
		{
			makePanel(addMid(jS1[i]), Color.rgb(255, 0, 0));
		}
		for(int i = 0; i < 10; i++)
		{
			makePanel(addMid(zS1[i]), Color.rgb(0, 255, 0));
		}
	}
	private int [][] addMid(int [][] panel)
	{
		int [][] newPanel = new int[panel.length+1][3];
		int xSum = 0;
		int ySum = 0;
		int zSum = 0;
		for(int i = 0; i < panel.length; i++)
		{
			newPanel[i][0] = panel[i][0];
			newPanel[i][1] = panel[i][1];
			newPanel[i][2] = panel[i][2];
			xSum += panel[i][0];
			ySum += panel[i][1];
			zSum += panel[i][2];
		}
		newPanel[panel.length][0] = xSum/panel.length;
		newPanel[panel.length][1] = ySum/panel.length;
		newPanel[panel.length][2] = zSum/panel.length;
		return newPanel;
	}
	private void makeCube(int x, int y, int z, int w, int color)
	{
		makePanel(x+w, y+w, z-w, x+w, y-w, z-w, x-w, y-w, z-w, x-w, y+w, z-w, color); // bottom
		makePanel(x+w, y+w, z+w, x+w, y-w, z+w, x-w, y-w, z+w, x-w, y+w, z+w, color); // top
		
		makePanel(x+w, y+w, z-w, x+w, y-w, z-w, x+w, y-w, z+w, x+w, y+w, z+w, color); // left
		makePanel(x-w, y+w, z-w, x-w, y-w, z-w, x-w, y-w, z+w, x-w, y+w, z+w, color); // right
		
		makePanel(x+w, y+w, z-w, x-w, y+w, z-w, x-w, y+w, z+w, x+w, y+w, z+w, color); // back
		makePanel(x+w, y-w, z-w, x-w, y-w, z-w, x-w, y-w, z+w, x+w, y-w, z+w, color); // front
	}
	private void makePanel(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, int x4, int y4, int z4, int color)
	{
		int[][] newPanel = {{x1, y1, z1}, {x2, y2, z2}, {x3, y3, z3}, {x4, y4, z4}, 
		{(x1+x2+x3+x4)/4, (y1+y2+y3+y4)/4, (z1+z2+z3+z4)/4}};
		makePanel(newPanel, color);
	}
	private void makePanel(int [] x, int [] y, int [] z, int color)
	{
		int[][] newPanel = new int[x.length+1][3];
		int xSum = 0;
		int ySum = 0;
		int zSum = 0;
		for(int i = 0; i < x.length; i++)
		{
			newPanel[i][0] = x[i];
			newPanel[i][1] = y[i];
			newPanel[i][2] = z[i];
			xSum += x[i];
			ySum += y[i];
			zSum += z[i];
		}
		newPanel[x.length][0] = xSum/x.length;
		newPanel[x.length][1] = ySum/x.length;
		newPanel[x.length][2] = zSum/x.length;
		makePanel(newPanel, color);
	}
	private void makePanel(int [][] points, int color)
	{
		Panel p = new Panel(points, color);
		panels.add(p);
	}
}