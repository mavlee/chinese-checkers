// The "ChineseCheckersV3" class.
import java.applet.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.event.*;

/** The "ChineseCheckersV3" class.
  * Purpose: To play the Chinese Checkers game
  * @author Maverick Lee
  * @version August 27, 2006
 */


public class ChineseCheckersV3 extends JFrame implements MouseListener
{
    public final int BOARD_WIDTH = 25;
    public final int BOARD_HEIGHT = 17;
    public int board[] [] = new int [BOARD_WIDTH] [BOARD_HEIGHT];
    public Ellipse2D.Double pictureboard[] [] = new Ellipse2D.Double [BOARD_WIDTH] [BOARD_HEIGHT];
    public int widthpos = 0;
    public int heightpos = 0;
    public int startingwidth = 0;
    public int startingheight = 0;
    public int selectedwidth = 0;
    public int selectedheight = 0;
    public int turn = 1;
    public boolean selected = false;
    public boolean spacejumppossible = true;
    public boolean longjumppossible = true;
    public int lockheight = 0;
    public int lockwidth = 0;
    public final int SIZE_OF_MARBLE = 30;

    public ChineseCheckersV3 ()
    {
	super ("Chinese Checkers");
	int width;
	int height;
	
	int counter;

	// Initiate the game
	setSize (580, 606);
	setBackground (Color.gray);
	addMouseListener (this);
	setDefaultCloseOperation (EXIT_ON_CLOSE);
	
	
	// MenuBar menu = new MenuBar ();
	// Menu test = new Menu("TEST");
	// test.add(new MenuItem("WELCOME"));
	// menu.add(test);
	// this.setMenuBar (menu);
	
	
	// Declare the variables for the board which will be used to determine how the board is drawn
	for (width = 0 ; width < BOARD_WIDTH ; width++)
	{
	    for (height = 0 ; height < BOARD_HEIGHT ; height++)
	    {
		pictureboard [width] [height] = new Ellipse2D.Double (30 + width * (SIZE_OF_MARBLE / 2 + 5), 30 + height * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE, SIZE_OF_MARBLE);
		board [width] [height] = -1;
	    }
	}

	// Set up parts of the array that can actually be used as part of the board
	for (height = 0 ; height < BOARD_HEIGHT ; height++)
	{
	    switch (height)
	    {
		case 0:
		case 16:
		    board [12] [height] = 0;
		    break;
		case 2:
		case 14:
		    for (counter = 10 ; counter <= 14 ; counter += 2)
			board [counter] [height] = 0;
		    break;
		case 4:
		case 12:
		    for (counter = 0 ; counter <= 24 ; counter += 2)
			board [counter] [height] = 0;
		    break;
		case 6:
		case 10:
		    for (counter = 2 ; counter <= 22 ; counter += 2)
			board [counter] [height] = 0;
		    break;
		case 8:
		    for (counter = 4 ; counter <= 20 ; counter += 2)
			board [counter] [height] = 0;
		    break;
		case 1:
		case 15:
		    for (counter = 11 ; counter <= 13 ; counter += 2)
			board [counter] [height] = 0;
		    break;
		case 3:
		case 13:
		    for (counter = 9 ; counter <= 15 ; counter += 2)
			board [counter] [height] = 0;
		    break;
		case 5:
		case 11:
		    for (counter = 1 ; counter <= 23 ; counter += 2)
			board [counter] [height] = 0;
		    break;
		case 7:
		case 9:
		    for (counter = 3 ; counter <= 21 ; counter += 2)
			board [counter] [height] = 0;
		    break;
	    }
	}

	// Declare these positions as taken up by the red player
	for (height = 0 ; height < 4 ; height++)
	{
	    switch (height)
	    {
		case 0:
		    board [12] [height] = 1;
		    break;
		case 1:
		    for (counter = 11 ; counter <= 13 ; counter += 2)
			board [counter] [height] = 1;
		    break;
		case 2:
		    for (counter = 10 ; counter <= 14 ; counter += 2)
			board [counter] [height] = 1;
		    break;
		case 3:
		    for (counter = 9 ; counter <= 15 ; counter += 2)
			board [counter] [height] = 1;
		    break;
	    }
	}

	// Declare these positions as taken by the blue player
	for (height = 13 ; height < BOARD_HEIGHT ; height++)
	{
	    switch (height)
	    {
		case 13:
		    for (counter = 9 ; counter <= 15 ; counter += 2)
			board [counter] [height] = 2;
		    break;
		case 14:
		    for (counter = 10 ; counter <= 14 ; counter += 2)
			board [counter] [height] = 2;
		    break;
		case 15:
		    for (counter = 11 ; counter <= 13 ; counter += 2)
			board [counter] [height] = 2;
		    break;
		case 16:
		    board [12] [height] = 2;
		    break;
	    }
	}
	show ();
    } 


    public void paint (Graphics g)
    {
	Graphics2D g2 = (Graphics2D) g;
	int width;
	int height;

	// Draw the board and the pieces
	for (width = 0 ; width < BOARD_WIDTH ; width++)
	{
	    for (height = 0 ; height < BOARD_HEIGHT ; height++)
	    {
		g2.setColor (Color.black);
		if (board [width] [height] >= 0 || board [width] [height] == -2)
		{
		    g2.draw (pictureboard [width] [height]);
		}
		g2.setColor (Color.red);
		if (board [width] [height] == 1)
		{
		    g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * width, 31 + height * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
		}
		g2.setColor (Color.blue);
		if (board [width] [height] == 2)
		{
		    g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * width, 31 + height * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
		}
	    }
	}

	// Draw the "Take Back Move" and "End Turn" boxes and text
	g2.setColor (Color.black);
	// g2.drawRect (400, 100, 130, 50);
	// g2.setFont (new Font ("Times New Roman", Font.PLAIN, 16));
	// g2.drawString ("Take Back Move", 413, 130);

	g2.drawRect (400, 40, 130, 50);
	g2.setFont (new Font ("Times New Roman", Font.PLAIN, 20));
	g2.drawString ("End Turn", 435, 70);
	g2.setFont (new Font ("Times New Roman", Font.PLAIN, 30));
	if (turn % 2 == 1)
	{
	    g2.setColor (Color.red);
	    g2.drawString ("Red Turn", 50, 100);
	}
	else
	{
	    g2.setColor (Color.blue);
	    g2.drawString ("Blue Turn", 50, 100);
	}
    } // paint method


    public void mousePressed (MouseEvent evt)
    {
	int x = evt.getX ();
	int y = evt.getY ();
	int index;
	int index2;
	int counter;
	int counter2;
	boolean valid = true;

	Graphics g = getGraphics ();
	Graphics2D g2 = (Graphics2D) g;
	g2.setFont (new Font ("Times New Roman", Font.PLAIN, 30));

	// Ends the turn
	if (x >= 400 && x <= 530 && y >= 40 && y <= 90 && (longjumppossible == false || spacejumppossible == false))
	{
	    spacejumppossible = true;
	    longjumppossible = true;
	    turn++;
	    selected = false;
	    startingwidth = 0;
	    startingheight = 0;
	    lockwidth = 0;
	    lockheight = 0;
	    g2.setColor (Color.gray);
	    g2.fillRect (10, 10, 200, 150);
	    if (turn % 2 == 1)
	    {
		g2.setColor (Color.red);
		g2.drawString ("Red Turn", 50, 100);
	    }
	    else
	    {
		g2.setColor (Color.blue);
		g2.drawString ("Blue Turn", 50, 100);
	    }
	}
	
	// // Takes back their move
	// if (x >= 400 && x <= 530 && y >= 100 && y <= 150 && (longjumppossible == false || spacejumppossible == false))
	// {
	//     g2.setColor (Color.gray);
	//     board [lockwidth] [lockheight] = 0;
	//     g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * lockwidth, 31 + lockheight * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
	//     g2.setColor (Color.black);
	//     g2.draw (pictureboard [lockwidth] [lockheight]);
	//     g2.draw (pictureboard [startingwidth] [startingheight]);
	// 
	//     if (turn % 2 == 1)
	//     {
	//         g2.setColor (Color.red);
	//         board [startingwidth] [startingheight] = 1;
	//         g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * startingwidth, 31 + startingheight * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
	//     }
	//     if (turn % 2 == 0)
	//     {
	//         g2.setColor (Color.blue);
	//         board [startingwidth] [startingheight] = 2;
	//         g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * startingwidth, 31 + startingheight * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
	//     }
	//     longjumppossible = true;
	//     spacejumppossible = true;
	//     selected = false;
	//     lockwidth = 0;
	//     lockheight = 0;
	// }

	g2.setColor (Color.black);
	if (board [widthpos] [heightpos] > 0)
	{
	    g2.draw (pictureboard [widthpos] [heightpos]);
	}

	// Determines which space on the board they chose
	widthpos = (x - 30) / (SIZE_OF_MARBLE / 2 + 5);
	heightpos = (y - 30) / (SIZE_OF_MARBLE + 3);
	for (index = 0 ; index < BOARD_WIDTH ; index++)
	{
	    for (index2 = 0 ; index2 < BOARD_HEIGHT ; index2++)
	    {
		if (board [index] [index2] == -2)
		{
		    g2.draw (pictureboard [index] [index2]);
		}
	    }
	}

	// Makes a move if they select a valid move
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
		g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * selectedwidth, 31 + selectedheight * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
		if (turn % 2 == 1)
		{
		    g2.setColor (Color.red);
		    g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * widthpos, 31 + heightpos * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
		    lockwidth = widthpos;
		    lockheight = heightpos;
		}
		else
		{
		    g2.setColor (Color.blue);
		    g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * widthpos, 31 + heightpos * (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
		    lockwidth = widthpos;
		    lockheight = heightpos;
		}
		if ((selectedwidth + 1 == widthpos || selectedwidth - 1 == widthpos) || ((selectedwidth + 2 == widthpos || selectedwidth - 2 == widthpos) && selectedheight == heightpos))
		{
		    longjumppossible = false;
		    spacejumppossible = false;
		}
		else
		{
		    spacejumppossible = false;
		}
	    }
	}
	else
	{
	    startingwidth = widthpos;
	    startingheight = heightpos;
	}

	for (index = 0 ; index < BOARD_WIDTH ; index++)
	{
	    for (index2 = 0 ; index2 < BOARD_HEIGHT ; index2++)
	    {
		if (board [index] [index2] == -2)
		{
		    board [index] [index2] = 0;
		}
	    }
	}


	g2.setColor (Color.yellow);

	// Highlights all possible moves
	if ((board [widthpos] [heightpos] == 1 && turn % 2 == 1) || (board [widthpos] [heightpos] == 2 && turn % 2 == 0))
	{
	    g2.draw (pictureboard [widthpos] [heightpos]);
	    selected = true;

	    selectedwidth = widthpos;
	    selectedheight = heightpos;

	    // Does the 1 space jumps
	    if ((lockwidth == widthpos && lockheight == heightpos) || (lockwidth == 0 && lockheight == 0))
	    {
		g2.setColor (Color.green);

		// Note that X is the piece and O is the empty space from now on
		// Does  O
		//      X
		if (widthpos + 1 <= 24 && heightpos + 1 <= 16 && board [widthpos + 1] [heightpos + 1] == 0 && longjumppossible == true && spacejumppossible == true)
		{
		    g2.draw (pictureboard [widthpos + 1] [heightpos + 1]);
		    board [widthpos + 1] [heightpos + 1] = -2;
		}

		// Does  X
		//        O
		if (widthpos + 1 <= 24 && heightpos - 1 >= 0 && board [widthpos + 1] [heightpos - 1] == 0 && longjumppossible == true && spacejumppossible == true)
		{
		    g2.draw (pictureboard [widthpos + 1] [heightpos - 1]);
		    board [widthpos + 1] [heightpos - 1] = -2;
		}

		// Does  O
		//        X
		if (widthpos - 1 >= 0 && heightpos + 1 <= 16 && board [widthpos - 1] [heightpos + 1] == 0 && longjumppossible == true && spacejumppossible == true)
		{
		    g2.draw (pictureboard [widthpos - 1] [heightpos + 1]);
		    board [widthpos - 1] [heightpos + 1] = -2;
		}

		// Does  X
		//      O
		if (widthpos - 1 >= 0 && heightpos - 1 >= 0 && board [widthpos - 1] [heightpos - 1] == 0 && longjumppossible == true && spacejumppossible == true)
		{
		    g2.draw (pictureboard [widthpos - 1] [heightpos - 1]);
		    board [widthpos - 1] [heightpos - 1] = -2;
		}

		// Does  O X
		if (widthpos - 2 >= 0 && board [widthpos - 2] [heightpos] == 0 && longjumppossible == true && spacejumppossible == true)
		{
		    g2.draw (pictureboard [widthpos - 2] [heightpos]);
		    board [widthpos - 2] [heightpos] = -2;
		}

		// Does  X O
		if (widthpos + 2 <= 24 && board [widthpos + 2] [heightpos] == 0 && longjumppossible == true && spacejumppossible == true)
		{
		    g2.draw (pictureboard [widthpos + 2] [heightpos]);
		    board [widthpos + 2] [heightpos] = -2;
		}

		// Does the long jumps
		for (counter = 0 ; counter <= 11 ; counter++)
		{
		    // Does    O     and      O
		    //        X              O
		    //       X              X
		    //                     O
		    //                    X
		    if (widthpos + counter <= 24 && widthpos + counter * 2 <= 24 && heightpos + counter <= 16 && heightpos + counter * 2 <= 16)
		    {
			if (board [widthpos + counter] [heightpos + counter] > 0 && board [widthpos + (counter * 2)] [heightpos + (counter * 2)] == 0)
			{
			    for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
			    {
				if (board [widthpos + counter2] [heightpos + counter2] > 0)
				    valid = false;
			    }
			    for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
			    {
				if (board [widthpos + counter2] [heightpos + counter2] > 0)
				    valid = false;
			    }
			    if (valid != false && longjumppossible == true)
			    {
				g2.draw (pictureboard [widthpos + (counter * 2)] [heightpos + (counter * 2)]);
				board [widthpos + (counter * 2)] [heightpos + (counter * 2)] = -2;
			    }
			}
		    }
		    valid = true;

		    // Does  X       and  X
		    //        X            O
		    //         O            X
		    //                       O
		    //                        O
		    if (widthpos + counter <= 24 && widthpos + counter * 2 <= 24 && heightpos - counter >= 0 && heightpos - counter * 2 >= 0)
		    {
			if (board [widthpos + counter] [heightpos - counter] > 0 && board [widthpos + (counter * 2)] [heightpos - (counter * 2)] == 0)
			{
			    for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
			    {
				if (board [widthpos + counter2] [heightpos - counter2] > 0)
				    valid = false;
			    }
			    for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
			    {
				if (board [widthpos + counter2] [heightpos - counter2] > 0)
				    valid = false;
			    }
			    if (valid != false && longjumppossible == true)
			    {
				g2.draw (pictureboard [widthpos + (counter * 2)] [heightpos - (counter * 2)]);
				board [widthpos + (counter * 2)] [heightpos - (counter * 2)] = -2;
			    }
			}
		    }
		    valid = true;

		    // Does  O     and     O
		    //        X             O
		    //         X             X
		    //                        O
		    //                         X
		    if (widthpos - counter >= 0 && widthpos - counter * 2 >= 0 && heightpos + counter <= 16 && heightpos + counter * 2 <= 16)
		    {
			if (board [widthpos - counter] [heightpos + counter] > 0 && board [widthpos - (counter * 2)] [heightpos + (counter * 2)] == 0)
			{
			    for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
			    {
				if (board [widthpos - counter2] [heightpos + counter2] > 0)
				    valid = false;
			    }
			    for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
			    {
				if (board [widthpos - counter2] [heightpos + counter2] > 0)
				    valid = false;
			    }
			    if (valid != false && longjumppossible == true)
			    {
				g2.draw (pictureboard [widthpos - (counter * 2)] [heightpos + (counter * 2)]);
				board [widthpos - (counter * 2)] [heightpos + (counter * 2)] = -2;
			    }
			}
		    }
		    valid = true;

		    // Does    X     and      X
		    //        X              O
		    //       O              X
		    //                     O
		    //                    O
		    if (widthpos - counter >= 0 && widthpos - counter * 2 >= 0 && heightpos - counter >= 0 && heightpos - counter * 2 >= 0)
		    {
			if (board [widthpos - counter] [heightpos - counter] > 0 && board [widthpos - (counter * 2)] [heightpos - (counter * 2)] == 0)
			{
			    for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
			    {
				if (board [widthpos - counter2] [heightpos - counter2] > 0)
				    valid = false;
			    }
			    for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
			    {
				if (board [widthpos - counter2] [heightpos - counter2] > 0)
				    valid = false;
			    }
			    if (valid != false && longjumppossible == true)
			    {
				g2.draw (pictureboard [widthpos - (counter * 2)] [heightpos - (counter * 2)]);
				board [widthpos - (counter * 2)] [heightpos - (counter * 2)] = -2;
			    }
			}
		    }
		    valid = true;

		    // Does   X X O     and     X O X O O
		    if (widthpos + counter <= 24 && widthpos + counter * 2 <= 24)
		    {
			if (board [widthpos + (counter * 2)] [heightpos] == 0 && board [widthpos + counter] [heightpos] > 0)
			{
			    for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
			    {
				if (board [widthpos + counter2] [heightpos] > 0)
				    valid = false;
			    }
			    for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
			    {
				if (board [widthpos + counter2] [heightpos] > 0)
				    valid = false;
			    }
			    if (valid != false && longjumppossible == true)
			    {
				g2.draw (pictureboard [widthpos + (counter * 2)] [heightpos]);
				board [widthpos + (counter * 2)] [heightpos] = -2;
			    }
			}
		    }
		    valid = true;

		    // Does   O X X     and     O O X O X
		    if (widthpos - counter >= 0 && widthpos - counter * 2 >= 0)
		    {
			if (board [widthpos - (counter * 2)] [heightpos] == 0 && board [widthpos - counter] [heightpos] > 0)
			{
			    for (counter2 = counter + 1 ; counter2 <= counter * 2 ; counter2++)
			    {
				if (board [widthpos - counter2] [heightpos] > 0)
				    valid = false;
			    }
			    for (counter2 = 1 ; counter2 <= counter - 1 ; counter2++)
			    {
				if (board [widthpos - counter2] [heightpos] > 0)
				    valid = false;
			    }
			    if (valid != false && longjumppossible == true)
			    {
				g2.draw (pictureboard [widthpos - (counter * 2)] [heightpos]);
				board [widthpos - (counter * 2)] [heightpos] = -2;
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


    public static void main (String[] args)
    {
	new ChineseCheckersV3 ();
    }
} // ChineseCheckersV3 class
