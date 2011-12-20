import hsa.Console;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Font;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

/** The "ChineseCheckersV2" class.
  * Purpose: To play the Chinese Checkers game
  * @author Maverick Lee
  * @version August 16, 2006
 */


public class ChineseCheckers extends Applet implements MouseListener
{
    public int board[] [] = new int [25] [17];
    public Ellipse2D.Double pictureboard[] [] = new Ellipse2D.Double [25] [17];
    public int widthpos = 0;
    public int heightpos = 0;
    public int startingwidth = 0;
    public int startingheight = 0;
    public int selectedwidth = 0;
    public int selectedheight = 0;
    public int turn = 1;
    public boolean selected = false;
    public boolean spacejump = false;
    public boolean longjump = false;

    public void init ()
    {
	setSize (660, 751);
	setBackground (Color.gray);
	addMouseListener (this);
    }


    public void paint (Graphics g)
    {
	Graphics2D g2 = (Graphics2D) g;

	int width;
	int height;
	int counter;

	for (width = 0 ; width < 25 ; width++)
	{
	    for (height = 0 ; height < 17 ; height++)
	    {
		pictureboard [width] [height] = new Ellipse2D.Double (10 + width * 25, 10 + height * 43, 40, 40);
		board [width] [height] = -1;
	    }
	}

	for (height = 0 ; height < 17 ; height++)
	{
	    switch (height)
	    {
		case 0:
		case 16:
		    g2.draw (pictureboard [12] [height]);
		    board [12] [height] = 0;
		    break;
		case 2:
		case 14:
		    for (counter = 10 ; counter <= 14 ; counter += 2)
		    {
			g2.draw (pictureboard [counter] [height]);
			board [counter] [height] = 0;
		    }
		    break;
		case 4:
		case 12:
		    for (counter = 0 ; counter <= 24 ; counter += 2)
		    {
			g2.draw (pictureboard [counter] [height]);
			board [counter] [height] = 0;
		    }
		    break;
		case 6:
		case 10:
		    for (counter = 2 ; counter <= 22 ; counter += 2)
		    {
			g2.draw (pictureboard [counter] [height]);
			board [counter] [height] = 0;
		    }
		    break;
		case 8:
		    for (counter = 4 ; counter <= 20 ; counter += 2)
		    {
			g2.draw (pictureboard [counter] [height]);
			board [counter] [height] = 0;
		    }
		    break;
		case 1:
		case 15:
		    for (counter = 11 ; counter <= 13 ; counter += 2)
		    {
			g2.draw (pictureboard [counter] [height]);
			board [counter] [height] = 0;
		    }
		    break;
		case 3:
		case 13:
		    for (counter = 9 ; counter <= 15 ; counter += 2)
		    {
			g2.draw (pictureboard [counter] [height]);
			board [counter] [height] = 0;
		    }
		    break;
		case 5:
		case 11:
		    for (counter = 1 ; counter <= 23 ; counter += 2)
		    {
			g2.draw (pictureboard [counter] [height]);
			board [counter] [height] = 0;
		    }
		    break;
		case 7:
		case 9:
		    for (counter = 3 ; counter <= 21 ; counter += 2)
		    {
			g2.draw (pictureboard [counter] [height]);
			board [counter] [height] = 0;
		    }
		    break;
	    }
	}

	g2.drawRect (500, 80, 150, 60);
	g2.setFont (new Font ("Times New Roman", Font.PLAIN, 20));
	g2.drawString ("Take Back Move", 510, 120);
	g2.drawRect (500, 10, 150, 60);
	g2.setFont (new Font ("Times New Roman", Font.PLAIN, 30));
	g2.drawString ("End Turn", 520, 50);
	g2.setColor (Color.red);
	g2.drawString ("Red Turn", 50, 50);

	g2.setColor (Color.red);
	for (height = 0 ; height < 4 ; height++)
	{
	    switch (height)
	    {
		case 0:
		    g2.fillArc (11 + 25 * 12, 11 + height * 43, 39, 39, 0, 360);
		    board [12] [height] = 1;
		    break;
		case 1:
		    for (counter = 11 ; counter <= 13 ; counter += 2)
		    {
			g2.fillArc (11 + 25 * counter, 11 + height * 43, 39, 39, 0, 360);
			board [counter] [height] = 1;
		    }
		    break;
		case 2:
		    for (counter = 10 ; counter <= 14 ; counter += 2)
		    {
			g2.fillArc (11 + 25 * counter, 11 + height * 43, 39, 39, 0, 360);
			board [counter] [height] = 1;
		    }
		    break;
		case 3:
		    for (counter = 9 ; counter <= 15 ; counter += 2)
		    {
			g2.fillArc (11 + 25 * counter, 11 + height * 43, 39, 39, 0, 360);
			board [counter] [height] = 1;
		    }
		    break;
	    }
	}
	g2.setColor (Color.blue);
	for (height = 13 ; height < 17 ; height++)
	{
	    switch (height)
	    {
		case 13:
		    for (counter = 9 ; counter <= 15 ; counter += 2)
		    {
			g2.fillArc (11 + 25 * counter, 11 + height * 43, 39, 39, 0, 360);
			board [counter] [height] = 2;
		    }
		    break;
		case 14:
		    for (counter = 10 ; counter <= 14 ; counter += 2)
		    {
			g2.fillArc (11 + 25 * counter, 11 + height * 43, 39, 39, 0, 360);
			board [counter] [height] = 2;
		    }
		    break;
		case 15:
		    for (counter = 11 ; counter <= 13 ; counter += 2)
		    {
			g2.fillArc (11 + 25 * counter, 11 + height * 43, 39, 39, 0, 360);
			board [counter] [height] = 2;
		    }
		    break;
		case 16:
		    g2.fillArc (11 + 25 * 12, 11 + height * 43, 39, 39, 0, 360);
		    board [12] [height] = 2;
		    break;
	    }
	}
    }


    public void mousePressed (MouseEvent evt)
    {
	int x = evt.getX ();
	int y = evt.getY ();
	int index;
	int index2;
	int counter;
	int counter2;
	boolean valid = true;
	boolean jump1valid = true;
	boolean jump2valid = true;
	boolean jump3valid = true;
	boolean jump4valid = true;
	boolean jump5valid = true;
	boolean jump6valid = true;
	boolean lock = false;
	int lockheight = 0;
	int lockwidth = 0;

	Graphics g = getGraphics ();
	Graphics2D g2 = (Graphics2D) g;
	g2.setFont (new Font ("Times New Roman", Font.PLAIN, 30));

	if (x >= 500 && x <= 650 && y >= 10 && y <= 70 && (longjump == true || spacejump == true))
	{
	    spacejump = false;
	    longjump = false;
	    turn++;
	    jump1valid = true;
	    jump2valid = true;
	    jump3valid = true;
	    jump4valid = true;
	    jump5valid = true;
	    jump6valid = true;
	    selected = false;
	    startingwidth = 0;
	    startingheight = 0;
	    g2.setColor (Color.gray);
	    g2.drawRect (10, 10, 200, 200);
	    if (turn % 2 == 1)
	    {
		g2.setColor (Color.red);
		g2.drawString ("Red Turn", 50, 50);
	    }
	    else
	    {
		g2.setColor (Color.blue);
		g2.drawString ("Blue Turn", 50, 50);
	    }
	}

	if (x >= 500 && x <= 650 && y >= 80 && y <= 140 && (longjump == true || spacejump == true))
	{
	    if (turn % 2 == 1)
	    {
		g2.setColor (Color.gray);
		board [selectedwidth] [selectedheight] = 0;
		g2.fillArc (11 + 25 * selectedwidth, 11 + selectedheight * 43, 39, 39, 0, 360);
		g2.setColor (Color.black);
		g2.draw (pictureboard [selectedwidth] [selectedheight]);

		g2.setColor (Color.red);
		board [startingwidth] [startingheight] = 1;
		g2.fillArc (11 + 25 * startingwidth, 11 + startingheight * 43, 39, 39, 0, 360);

		longjump = false;
		spacejump = false;
		selected = false;
	    }
	    if (turn % 2 == 0)
	    {
		g2.setColor (Color.gray);
		board [selectedwidth] [selectedheight] = 0;
		g2.fillArc (11 + 25 * selectedwidth, 11 + selectedheight * 43, 39, 39, 0, 360);
		g2.setColor (Color.black);
		g2.draw (pictureboard [selectedwidth] [selectedheight]);

		g2.setColor (Color.blue);
		board [startingwidth] [startingheight] = 2;
		g2.fillArc (11 + 25 * startingwidth, 11 + startingheight * 43, 39, 39, 0, 360);

		longjump = false;
		spacejump = false;
		selected = false;
	    }
	}

	g2.setColor (Color.black);
	if (board [widthpos] [heightpos] > 0)
	{
	    g2.draw (pictureboard [widthpos] [heightpos]);
	}

	widthpos = (x - 10) / 25;
	heightpos = (y - 10) / 43;

	for (index = 0 ; index <= 24 ; index++)
	{
	    for (index2 = 0 ; index2 <= 16 ; index2++)
	    {
		if (board [index] [index2] == -2)
		{
		    g2.draw (pictureboard [index] [index2]);
		}
	    }
	}


	if (selected == true)
	{
	    if (board [widthpos] [heightpos] == -2)
	    {
		board [selectedwidth] [selectedheight] = 0;
		if (turn % 2 == 1)
		{
		    board [widthpos] [heightpos] = 1;
		}
		else
		{
		    board [widthpos] [heightpos] = 2;
		}
		g2.setColor (Color.gray);
		g2.fillArc (11 + 25 * selectedwidth, 11 + selectedheight * 43, 39, 39, 0, 360);
		if (turn % 2 == 1)
		{
		    g2.setColor (Color.red);
		    g2.fillArc (11 + 25 * widthpos, 11 + heightpos * 43, 39, 39, 0, 360);
		    selectedwidth = widthpos;
		    selectedheight = heightpos;
		    lockwidth = widthpos;
		    lockheight = heightpos;
		}
		else
		{
		    g2.setColor (Color.blue);
		    g2.fillArc (11 + 25 * widthpos, 11 + heightpos * 43, 39, 39, 0, 360);
		    selectedwidth = widthpos;
		    selectedheight = heightpos;
		    lockwidth = widthpos;
		    lockheight = heightpos;
		}
		if (selectedwidth + 1 == widthpos || selectedwidth - 1 == widthpos)
		{
		    spacejump = true;
		}
		else
		{
		    longjump = true;

		}
	    }
	}
	else
	{
	    startingwidth = widthpos;
	    startingheight = heightpos;
	}

	for (index = 0 ; index <= 24 ; index++)
	{
	    for (index2 = 0 ; index2 <= 16 ; index2++)
	    {
		if (board [index] [index2] == -2)
		{
		    board [index] [index2] = 0;
		}
	    }
	}





	g2.setColor (Color.yellow);

	if (((board [widthpos] [heightpos] == 1 && turn % 2 == 1) || (board [widthpos] [heightpos] == 2 && turn % 2 == 0)))
	{
	    g2.draw (pictureboard [widthpos] [heightpos]);
	    selected = true;

	    if (selected == true && (spacejump == true /*|| longjump == true*/)  /*&& (widthpos != selectedwidth || heightpos != selectedheight)*/&& (lockwidth != widthpos || lockheight != heightpos))
	    {
		lock = true;
	    }
	    else
	    {
		lock = false;
	    }
	    selectedwidth = widthpos;
	    selectedheight = heightpos;


	    if (spacejump == false && lock == false)
	    {
		g2.setColor (Color.green);

		if (widthpos + 1 <= 24 && heightpos + 1 <= 16)
		{
		    if (board [widthpos + 1] [heightpos + 1] == 0 && longjump == false && spacejump == false)
		    {
			g2.draw (pictureboard [widthpos + 1] [heightpos + 1]);
			board [widthpos + 1] [heightpos + 1] = -2;
		    }
		}
		if (widthpos + 1 <= 24 && heightpos - 1 >= 0)
		{
		    if (board [widthpos + 1] [heightpos - 1] == 0 && longjump == false && spacejump == false)
		    {
			g2.draw (pictureboard [widthpos + 1] [heightpos - 1]);
			board [widthpos + 1] [heightpos - 1] = -2;
		    }
		}
		if (widthpos - 1 >= 0 && heightpos + 1 <= 16)
		{
		    if (board [widthpos - 1] [heightpos + 1] == 0 && longjump == false && spacejump == false)
		    {
			g2.draw (pictureboard [widthpos - 1] [heightpos + 1]);
			board [widthpos - 1] [heightpos + 1] = -2;
		    }
		}
		if (widthpos - 1 >= 0 && heightpos - 1 >= 0)
		{
		    if (board [widthpos - 1] [heightpos - 1] == 0 && longjump == false && spacejump == false)
		    {
			g2.draw (pictureboard [widthpos - 1] [heightpos - 1]);
			board [widthpos - 1] [heightpos - 1] = -2;
		    }
		}
		if (widthpos - 2 >= 0)
		{
		    if (board [widthpos - 2] [heightpos] == 0 && longjump == false && spacejump == false)
		    {
			g2.draw (pictureboard [widthpos - 2] [heightpos]);
			board [widthpos - 2] [heightpos] = -2;
		    }
		}
		if (widthpos + 2 <= 24)
		{
		    if (board [widthpos + 2] [heightpos] == 0 && longjump == false && spacejump == false)
		    {
			g2.draw (pictureboard [widthpos + 2] [heightpos]);
			board [widthpos + 2] [heightpos] = -2;
		    }
		}

		for (counter = 0 ; counter <= 11 ; counter++)
		{
		    if (widthpos + counter <= 24 && widthpos + counter * 2 <= 24 && heightpos + counter <= 16 && heightpos + counter * 2 <= 16)
		    {
			if (board [widthpos + counter] [heightpos + counter] > 0)
			{
			    if (board [widthpos + (counter * 2)] [heightpos + (counter * 2)] == 0)
			    {
				for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
				{
				    if (board [widthpos + counter2] [heightpos + counter2] > 0)
				    {
					valid = false;
					jump1valid = false;
				    }
				}
				for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
				{
				    if (board [widthpos + counter2] [heightpos + counter2] > 0)
				    {
					valid = false;
					jump1valid = false;
				    }
				}
				if (valid != false && jump1valid != false && spacejump == false)
				{
				    g2.draw (pictureboard [widthpos + (counter * 2)] [heightpos + (counter * 2)]);
				    board [widthpos + (counter * 2)] [heightpos + (counter * 2)] = -2;
				}
			    }
			}
		    }
		    valid = true;
		    if (widthpos + counter <= 24 && widthpos + counter * 2 <= 24 && heightpos - counter >= 0 && heightpos - counter * 2 >= 0)
		    {
			if (board [widthpos + counter] [heightpos - counter] > 0)
			{
			    if (board [widthpos + (counter * 2)] [heightpos - (counter * 2)] == 0)
			    {
				for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
				{
				    if (board [widthpos + counter2] [heightpos - counter2] > 0)
				    {
					valid = false;
					jump2valid = false;
				    }
				}
				for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
				{
				    if (board [widthpos + counter2] [heightpos - counter2] > 0)
				    {
					valid = false;
					jump2valid = false;
				    }
				}
				if (valid != false && jump2valid != false && spacejump == false)
				{
				    g2.draw (pictureboard [widthpos + (counter * 2)] [heightpos - (counter * 2)]);
				    board [widthpos + (counter * 2)] [heightpos - (counter * 2)] = -2;
				}
			    }
			}
		    }
		    valid = true;
		    if (widthpos - counter >= 0 && widthpos - counter * 2 >= 0 && heightpos + counter <= 16 && heightpos + counter * 2 <= 16)
		    {
			if (board [widthpos - counter] [heightpos + counter] > 0)
			{
			    if (board [widthpos - (counter * 2)] [heightpos + (counter * 2)] == 0)
			    {
				for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
				{
				    if (board [widthpos - counter2] [heightpos + counter2] > 0)
				    {
					valid = false;
					jump3valid = false;
				    }
				}
				for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
				{
				    if (board [widthpos - counter2] [heightpos + counter2] > 0)
				    {
					valid = false;
					jump3valid = false;
				    }
				}
				if (valid != false && jump3valid != false && spacejump == false)
				{
				    g2.draw (pictureboard [widthpos - (counter * 2)] [heightpos + (counter * 2)]);
				    board [widthpos - (counter * 2)] [heightpos + (counter * 2)] = -2;
				}
			    }
			}
		    }
		    valid = true;
		    if (widthpos - counter >= 0 && widthpos - counter * 2 >= 0 && heightpos - counter >= 0 && heightpos - counter * 2 >= 0)
		    {
			if (board [widthpos - counter] [heightpos - counter] > 0)
			{
			    if (board [widthpos - (counter * 2)] [heightpos - (counter * 2)] == 0)
			    {
				for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
				{
				    if (board [widthpos - counter2] [heightpos - counter2] > 0)
				    {
					valid = false;
					jump4valid = false;
				    }
				}
				for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
				{
				    if (board [widthpos - counter2] [heightpos - counter2] > 0)
				    {
					valid = false;
					jump4valid = false;
				    }
				}
				if (valid != false && jump4valid != false && spacejump == false)
				{
				    g2.draw (pictureboard [widthpos - (counter * 2)] [heightpos - (counter * 2)]);
				    board [widthpos - (counter * 2)] [heightpos - (counter * 2)] = -2;
				}
			    }
			}
		    }
		    valid = true;
		    if (widthpos + counter <= 24 && widthpos + counter * 2 <= 24)
		    {
			if (board [widthpos + counter] [heightpos] > 0)
			{
			    if (board [widthpos + (counter * 2)] [heightpos] == 0)
			    {
				for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
				{
				    if (board [widthpos + counter2] [heightpos] > 0)
				    {
					valid = false;
					jump5valid = false;
				    }
				}
				for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
				{
				    if (board [widthpos + counter2] [heightpos] > 0)
				    {
					valid = false;
					jump5valid = false;
				    }
				}
				if (valid != false && jump5valid != false && spacejump == false)
				{
				    g2.draw (pictureboard [widthpos + (counter * 2)] [heightpos]);
				    board [widthpos + (counter * 2)] [heightpos] = -2;
				}
			    }
			}
		    }
		    valid = true;
		    if (widthpos - counter >= 0 && widthpos - counter * 2 >= 0)
		    {
			if (board [widthpos - counter] [heightpos] > 0)
			{
			    if (board [widthpos - (counter * 2)] [heightpos] == 0)
			    {
				for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
				{
				    if (board [widthpos - counter2] [heightpos] > 0)
				    {
					valid = false;
					jump6valid = false;
				    }
				}
				for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
				{
				    if (board [widthpos - counter2] [heightpos] > 0)
				    {
					valid = false;
					jump6valid = false;
				    }
				}
				if (valid != false && jump6valid != false && spacejump == false)
				{
				    g2.draw (pictureboard [widthpos - (counter * 2)] [heightpos]);
				    board [widthpos - (counter * 2)] [heightpos] = -2;
				}
			    }
			}
		    }
		    valid = true;
		}
	    }
	}
    }




    public void mouseEntered (MouseEvent evt)
    {
    }


    public void mouseExited (MouseEvent evt)
    {
    }


    public void mouseClicked (MouseEvent evt)
    {
    }


    public void mouseReleased (MouseEvent evt)
    {
    }
} // ChineseCheckers class

