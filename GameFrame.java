// The "ChineseCheckersV3" class.
//import java.applet.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.event.*;

class GameFrame extends JFrame
{ GameFrame ()
    { super ("Chinese Checkers V3");
      setLocation (20, 50);
      setSize (660, 751);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      show ();
      // Initiate the game
      setBackground(Color.gray);

      
      // Declare the variables for the board which will be used to determine how the board is drawn
      for ( width = 0; width < 25; width++ ) 
	{ for ( height = 0; height < 17; height++ ) 
	    {  pictureboard[width][height] = new Ellipse2D.Double(10 + width * 25, 10 + height * 43, 40, 40);
	       board[width][height] = -1;  
	    }  
	}        
      
      // Set up parts of the array that can actually be used as part of the board  
      for ( height = 0; height < 17; height++ )
	{  switch (height)
	    { case 0 :
	      case 16 :
		board[12][height] = 0;
		break;
	      case 2 :
	      case 14 :
		for ( counter = 10; counter <= 14; counter += 2 )
		  board[counter][height] = 0; 
		break;
	      case 4 :
	      case 12 :
		for ( counter = 0; counter <= 24; counter += 2 )
		  board[counter][height] = 0; 
		break;
	      case 6 :
	      case 10 :
		for ( counter = 2; counter <= 22; counter += 2 )
		  board[counter][height] = 0; 
		break;
	      case 8 :
		for ( counter = 4; counter <= 20; counter += 2 )
		  board[counter][height] = 0; 
		break;
	      case 1 :
	      case 15 :
		for ( counter = 11; counter <= 13; counter += 2 )
		  board[counter][height] = 0; 
		break;
	      case 3 :
	      case 13 :
		for ( counter = 9; counter <= 15; counter += 2 )
		  board[counter][height] = 0; 
		break;
	      case 5 :
	      case 11 :
		for ( counter = 1; counter <= 23; counter += 2 )
		  board[counter][height] = 0; 
		break;
	      case 7 :
	      case 9 :
		for ( counter = 3; counter <= 21; counter += 2 )
		  board[counter][height] = 0; 
		break;                 
	    } 
	}
 
      // Declare these positions as taken up by the red player  
      for ( height = 0; height < 4; height++)
	{ switch (height)
	    { case 0:
		board[12][height] = 1;
		break;
	      case 1:
		for ( counter = 11; counter <= 13; counter += 2 )
		  board[counter][height] = 1; 
		break;
	      case 2:
		for ( counter = 10; counter <= 14; counter += 2 )
		  board[counter][height] = 1; 
		break;
	      case 3:
		for ( counter = 9; counter <= 15; counter += 2 )
		  board[counter][height] = 1; 
		break;    
	    }
	}
	
      // Declare these positions as taken by the blue player    
      for ( height = 13; height < 17; height++)
	{ switch (height)
	    { case 13:
		for ( counter = 9; counter <= 15; counter += 2 )
		  board[counter][height] = 2; 
		break;
	      case 14:               
		for ( counter = 10; counter <= 14; counter += 2 )
		  board[counter][height] = 2; 
		break;
	      case 15:
		for ( counter = 11; counter <= 13; counter += 2 )
		  board[counter][height] = 2; 
		break;
	      case 16:
		board[12][height] = 2;
		break;    
	    }
	}    
       
     }
    
  public static void main (String[] args)
    { new GameFrame ();
      
    }

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
  public boolean spacejumppossible = true;
  public boolean longjumppossible = true;
  public int lockheight = 0;
  public int lockwidth = 0;
  public int width;
  public int height;
  public int counter;
  
  


      public void paint(Graphics g) 
	{ //super.paintComponent(g); 
	  Graphics2D g2 = (Graphics2D)g;
	  // Draw the board and the pieces
	  for ( width = 0; width < 25; width++ )
	    { for ( height = 0; height < 17; height++ )
		{ g2.setColor(Color.black);
		  if ( board[width][height] >= 0 || board[width][height] == -2 )
		    { g2.draw(pictureboard[width][height]);
		    }
		  g2.setColor(Color.red);
		  if ( board[width][height] == 1 )
		    { g2.fillArc(11 + 25*width, 11 + height * 43, 39, 39, 0, 360);
		    }
		  g2.setColor(Color.blue); 
		  if ( board[width][height] == 2 )
		    { g2.fillArc(11 + 25*width, 11 + height * 43, 39, 39, 0, 360);
		    }   
		}
	    }   
	
	  // Draw the "Take Back Move" and "End Turn" boxes and text
	  g2.setColor(Color.black);
	  g2.drawRect(500, 80, 150, 60);
	  g2.setFont(new Font( "Times New Roman", Font.PLAIN, 20));
	  g2.drawString("Take Back Move", 510, 120);  
	  g2.drawRect(500, 10, 150, 60);
	  g2.setFont(new Font( "Times New Roman", Font.PLAIN, 30));
	  g2.drawString("End Turn", 520, 50);
	  if ( turn%2 == 1 )
	    { g2.setColor(Color.red);
	      g2.drawString("Red Turn", 50, 50);
	    }
	  else
	    { g2.setColor(Color.blue);
	      g2.drawString("Blue Turn", 50, 50);
	    }           
	}   

    
    
	     
    
} // ChineseCheckersV3 class
