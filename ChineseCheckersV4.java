// The "ChineseCheckersV4" class.
import java.awt.*;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.event.*;
import java.io.*;

/** The "ChineseCheckersV4" class.
 * Purpose: To play the Chinese Checkers game
 * @author Maverick Lee and Tyler Kutluoglu
 * @version December 18, 2006
 */
public class ChineseCheckersV4 extends Frame
{
  public final int BOARD_WIDTH = 25;
  public final int BOARD_HEIGHT = 17;
  public int board[] [] = new int [BOARD_WIDTH] [BOARD_HEIGHT];
  public Ellipse2D.Double pictureBoard[] [] =
    new Ellipse2D.Double [BOARD_WIDTH] [BOARD_HEIGHT];
  public int widthPos = 0;
  public int heightPos = 0;
  public int startingWidth = 0;
  public int startingHeight = 0;
  public int selectedWidth = 0;
  public int selectedHeight = 0;
  public int lockHeight = 0;
  public int lockWidth = 0;
  public boolean selected = false;
  public boolean spaceJumpPossible = true;
  public boolean longJumpPossible = true;
  public final int SIZE_OF_MARBLE = 29;
  private MenuItem newOption, exitOption, twoPlayers,
          threePlayers, fourPlayers, sixPlayers, puzzleMode, howToPlay, aboutOption;
  public int noOfPlayers = 2;
  public int currentPlayer = 1;
  public static Image[] marblePics = new Image [7];
  public String[] playerNames = {"", "Player 1", "Player 2"};
  public int[] winZones = {0, 0, 0, 0, 0, 0, 0};
  public static int[] numbers;
  public int[] puzzleModewidthPos = new int [11];
  public int[] puzzleModeheightPos = new int [11];
  public int puzzleModeLevel = 1;
  public int puzzleModeTurns;
  public final int MAX_PUZZLE_MODE_LEVELS = 10;

  // For drawing images offScreen (prevents Flicker)
  // These variables keep track of an off screen image object and
  // its corresponding graphics object
  Image offScreenImage;
  Graphics offScreenBuffer;

  public ChineseCheckersV4 ()
  {
    super ("Chinese Checkers");
    int width;
    int height;
    int counter;

    // Set up the Game MenuItems
    newOption = new MenuItem ("New");
    exitOption = new MenuItem ("Exit");

    // Set up the Game Menu
    Menu gameMenu = new Menu ("Game");

    // Add each MenuItem to the Game Menu (with a separator)
    gameMenu.add (newOption);
    gameMenu.addSeparator ();
    gameMenu.add (exitOption);

    // Set up the Mode Menu
    Menu modeMenu = new Menu ("Mode");
    Menu multiPlayerMode = new Menu ("Multiplayer Mode");

    // Set up the Mode MenuItems
    twoPlayers = new MenuItem ("Two Players");
    threePlayers = new MenuItem ("Three Players");
    fourPlayers = new MenuItem ("Four Players");
    sixPlayers = new MenuItem ("Six Players");
    multiPlayerMode.add (twoPlayers);
    multiPlayerMode.add (threePlayers);
    multiPlayerMode.add (fourPlayers);
    multiPlayerMode.add (sixPlayers);
    puzzleMode = new MenuItem ("Puzzle Mode");

    // Add each MenuItem to the Mode Menu
    modeMenu.add (multiPlayerMode);
    modeMenu.add (puzzleMode);

    // Set up the Help Menu
    Menu helpMenu = new Menu ("Help");

    // Set up the Help MenuItems
    howToPlay = new MenuItem ("How To Play");
    aboutOption = new MenuItem ("About");

    // Add each MenuItem to the Help Menu (with a separator)
    helpMenu.add (howToPlay);
    helpMenu.addSeparator ();
    helpMenu.add (aboutOption);

    // Set up the Menu
    MenuBar mainMenu = new MenuBar ();
    mainMenu.add (gameMenu);
    mainMenu.add (modeMenu);
    mainMenu.add (helpMenu);

    // Set the menu bar for this frame to mainMenu
    setMenuBar (mainMenu);

    // Initiate the game
    setSize (580, 606 + this.getInsets ().top +
        this.getInsets ().bottom + 30);
    setBackground (Color.gray);

    // Starts a new game
    marblePics = new Image [3];
    marblePics [1] = new ImageIcon ("Pictures/1.gif").getImage ();
    marblePics [2] = new ImageIcon ("Pictures/2.gif").getImage ();

    // Set the icon
    Image image = new ImageIcon ("Pictures/17.gif").getImage ();
    setIconImage (image);

    // Makes it unresizable and starts a new game
    this.setResizable (false);
    newGame (noOfPlayers);
    show ();
  }


  /* Reads in the next level of puzzle mode
   *@param puzzleModeLevel      the level that the player is in on puzzle mode
   */
  public void readPuzzle (int puzzleModeLevel)
  {
    try {
      FileReader fr = new FileReader("PuzzleMode/" + Integer.toString(puzzleModeLevel) + ".txt");
      BufferedReader inFile = new BufferedReader(fr);

      puzzleModeTurns = Integer.parseInt(inFile.readLine());
      for (int index = 1 ; index <= 10 ; index++)
      {
        puzzleModewidthPos [index] = Integer.parseInt(inFile.readLine());
        puzzleModeheightPos [index] = Integer.parseInt(inFile.readLine());
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }


  /* Continues the game after the player inputs all the data from the other
   * window. Only to be used by the other window
   *@param finalColours     an array filled with the numbers of their pictures
   *                        which then correspond to the actual pictures
   *@param finalNames       an array filled the names of the players
   */
  public void resume (int[] finalColours, String[] finalNames)
  {
    marblePics = new Image [finalColours.length + 1];
    for (int index = 1 ; index <= finalColours.length ; index++)
      marblePics [index] = new ImageIcon ("Pictures/" +
          finalColours [index - 1] + ".gif").getImage ();
    playerNames = new String [finalNames.length + 1];
    for (int index = 1 ; index <= finalNames.length ; index++)
      playerNames [index] = finalNames [index - 1];
    noOfPlayers = finalColours.length;
    newGame (noOfPlayers);
  }


  /* Checks if a player has won
   *@param player       the player to check if he/she has won
   *@param zone         the winning zone to check
   */
  public boolean isWinnerInZone (int player, int zone)
  {
    int counter;
    // Checks if the whole zone is filled depending on the zone they chose
    if (zone == 1)
    {
      for (int height = 0 ; height < 4 ; height++)
      {
        switch (height)
        {
          case 0:
            if (board [12] [height] != player)
              return false;
            break;
          case 1:
            for (counter = 11 ; counter <= 13 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 2:
            for (counter = 10 ; counter <= 14 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 3:
            for (counter = 9 ; counter <= 15 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
        }
      }
    }
    else if (zone == 2)
    {
      for (int height = 4 ; height < 8 ; height++)
      {
        switch (height)
        {
          case 4:
            for (counter = 18 ; counter <= 24 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 5:
            for (counter = 19 ; counter <= 23 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 6:
            for (counter = 20 ; counter <= 22 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 7:
            if (board [21] [height] != player)
              return false;
            break;
        }
      }
    }
    else if (zone == 3)
    {
      for (int height = 9 ; height < 13 ; height++)
      {
        switch (height)
        {
          case 9:
            if (board [21] [height] != player)
              return false;
            break;
          case 10:
            for (counter = 20 ; counter <= 22 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 11:
            for (counter = 19 ; counter <= 23 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 12:
            for (counter = 18 ; counter <= 24 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
        }
      }
    }
    else if (zone == 4)
    {
      for (int height = 13 ; height < BOARD_HEIGHT ; height++)
      {
        switch (height)
        {
          case 13:
            for (counter = 9 ; counter <= 15 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 14:
            for (counter = 10 ; counter <= 14 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 15:
            for (counter = 11 ; counter <= 13 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 16:
            if (board [12] [height] != player)
              return false;
            break;
        }
      }
    }
    else if (zone == 5)
    {
      for (int height = 9 ; height < 13 ; height++)
      {
        switch (height)
        {
          case 9:
            if (board [3] [height] != player)
              return false;
            break;
          case 10:
            for (counter = 2 ; counter <= 4 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 11:
            for (counter = 1 ; counter <= 5 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;

          case 12:
            for (counter = 0 ; counter <= 6 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
        }
      }
    }
    else if (zone == 6)
    {
      for (int height = 3 ; height < 8 ; height++)
      {
        switch (height)
        {
          case 4:
            for (counter = 0 ; counter <= 6 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 5:
            for (counter = 1 ; counter <= 5 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 6:
            for (counter = 2 ; counter <= 4 ; counter += 2)
            {
              if (board [counter] [height] != player)
                return false;
            }
            break;
          case 7:
            if (board [3] [height] != player)
              return false;
            break;
        }
      }
    }
    return true;
  }



  /* Fills a zone (one of the 6 corners) with any player
   * This method is used for easier new game starting
   *@param zone     the zone that needs to be filled (zone 1 is the top
   *                middle, then goes clockwise)
   *@param player   the player that is occupying that zone
   */
  public void fillZone (int zone, int player)
  {
    int counter;

    // Sets the player's winning zone
    if (zone <= 3)
      winZones [player] = zone + 3;
    else
      winZones [player] = zone - 3;

    // Fills a zone with the corresponding player depending on the zone
    if (zone == 1)
    {
      for (int height = 0 ; height < 4 ; height++)
      {
        switch (height)
        {
          case 0:
            board [12] [height] = player;
            break;
          case 1:
            for (counter = 11 ; counter <= 13 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 2:
            for (counter = 10 ; counter <= 14 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 3:
            for (counter = 9 ; counter <= 15 ; counter += 2)
              board [counter] [height] = player;
            break;
        }
      }
    }
    else if (zone == 2)
    {
      for (int height = 4 ; height < 8 ; height++)
      {
        switch (height)
        {
          case 4:
            for (counter = 18 ; counter <= 24 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 5:
            for (counter = 19 ; counter <= 23 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 6:
            for (counter = 20 ; counter <= 22 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 7:
            board [21] [height] = player;
            break;
        }
      }
    }
    else if (zone == 3)
    {
      for (int height = 9 ; height < 13 ; height++)
      {
        switch (height)
        {
          case 9:
            board [21] [height] = player;
            break;
          case 10:
            for (counter = 20 ; counter <= 22 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 11:
            for (counter = 19 ; counter <= 23 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 12:
            for (counter = 18 ; counter <= 24 ; counter += 2)
              board [counter] [height] = player;
            break;
        }
      }
    }
    else if (zone == 4)
    {
      for (int height = 13 ; height < BOARD_HEIGHT ; height++)
      {
        switch (height)
        {
          case 13:
            for (counter = 9 ; counter <= 15 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 14:
            for (counter = 10 ; counter <= 14 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 15:
            for (counter = 11 ; counter <= 13 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 16:
            board [12] [height] = player;
            break;
        }
      }
    }
    else if (zone == 5)
    {
      for (int height = 9 ; height < 13 ; height++)
      {
        switch (height)
        {
          case 9:
            board [3] [height] = player;
            break;
          case 10:
            for (counter = 2 ; counter <= 4 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 11:
            for (counter = 1 ; counter <= 5 ; counter += 2)
              board [counter] [height] = player;
            break;

          case 12:
            for (counter = 0 ; counter <= 6 ; counter += 2)
              board [counter] [height] = player;
            break;
        }
      }
    }
    else if (zone == 6)
    {
      for (int height = 3 ; height < 8 ; height++)
      {
        switch (height)
        {
          case 4:
            for (counter = 0 ; counter <= 6 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 5:
            for (counter = 1 ; counter <= 5 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 6:
            for (counter = 2 ; counter <= 4 ; counter += 2)
              board [counter] [height] = player;
            break;
          case 7:
            board [3] [height] = player;
            break;
        }
      }
    }
  }


  /* Starts a new game right away
   *@param noOfPlayers    the number of players playing
   */
  public void newGame (int noOfPlayers)
  {
    currentPlayer = 1;
    int width;
    int height;
    int counter;

    // Unlocks their preview selection
    lockWidth = 0;
    lockHeight = 0;

    // Resets it so that players can make moves
    spaceJumpPossible = true;
    longJumpPossible = true;

    // Clear the board
    for (width = 0 ; width < BOARD_WIDTH ; width++)
    {
      for (height = 0 ; height < BOARD_HEIGHT ; height++)
      {
        pictureBoard [width] [height] = new Ellipse2D.Double (30 +
            width * (SIZE_OF_MARBLE / 2 + 5), 60 + height *
            (SIZE_OF_MARBLE + 3), SIZE_OF_MARBLE, SIZE_OF_MARBLE);
        board [width] [height] = -1;
      }
    }

    // Set up parts of the array that can actually be used as part of
    // the board by making all possible spots possible to be filled
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

    // The number of players is 1 (Puzzle Mode) so load the puzzle
    if (noOfPlayers == 1)
    {
      readPuzzle (puzzleModeLevel);
      for (int index = 1 ; index <= 10 ; index++)
        board [puzzleModewidthPos [index]]
          [puzzleModeheightPos [index]] = 1;
      winZones [1] = 1;
    }
    // The number of players is two or more so fill the appropiate zones
    // with the corresponding players
    else if (noOfPlayers == 2)
    {
      fillZone (1, 1);
      fillZone (4, 2);
    }
    else if (noOfPlayers == 3)
    {
      fillZone (1, 1);
      fillZone (3, 2);
      fillZone (5, 3);
    }
    else if (noOfPlayers == 4)
    {
      fillZone (2, 1);
      fillZone (3, 2);
      fillZone (5, 3);
      fillZone (6, 4);
    }
    else if (noOfPlayers == 6)
    {
      fillZone (1, 1);
      fillZone (2, 2);
      fillZone (3, 3);
      fillZone (4, 4);
      fillZone (5, 5);
      fillZone (6, 6);
    }
    repaint ();
  }


  public boolean keyDown (Event evt, int key)
  {
    Graphics g = getGraphics ();
    Graphics2D g2 = (Graphics2D) g;
    g2.setFont (new Font ("Times New Roman", Font.PLAIN, 30));
    if (key == ' ')
    {
      spaceJumpPossible = true;
      longJumpPossible = true;
      selected = false;
      startingWidth = 0;
      startingHeight = 0;
      lockWidth = 0;
      lockHeight = 0;
      widthPos = 0;
      heightPos = 0;
      g2.setColor (Color.gray);
      g2.fillRect (10, 10, 230, 100);

      // Starts a new game if a player wins in multiplayer mode
      if (isWinnerInZone (currentPlayer, winZones [currentPlayer]) && noOfPlayers >= 2)
      {
        JOptionPane.showMessageDialog (this,
            playerNames [currentPlayer] + " has won! Press OK for a new game.",
            "Congratulations", JOptionPane.WARNING_MESSAGE);
        newGame (noOfPlayers);
      }
      else
      {
        // Change the turn
        if (currentPlayer == noOfPlayers)
          currentPlayer = 1;
        else
          currentPlayer += 1;
        g2.setColor (Color.black);
        if (noOfPlayers >= 2)
        {
          g2.drawString (playerNames [currentPlayer] +
              "'s Turn", 25, 100);
          g2.drawImage (marblePics [currentPlayer], 108, 115, null);
        }
        else
        {
          // Display the number of turns left and level
          g2.setColor (Color.gray);
          g2.fillRect (10, 10, 190, 150);
          g2.setColor (Color.black);
          g2.drawString ("Level " + puzzleModeLevel, 25, 100);
          puzzleModeTurns--;
          g2.drawString ("Turns Left: " + puzzleModeTurns, 25, 125);
          if (isWinnerInZone (currentPlayer, winZones [currentPlayer]))
          {
            // Change to multiplayer mode since they beat puzzle mode
            if (puzzleModeLevel == MAX_PUZZLE_MODE_LEVELS)
            {
              JOptionPane.showMessageDialog (this,
                  "You beat Puzzle Mode!!", "Congratulations",
                  JOptionPane.WARNING_MESSAGE);
              noOfPlayers = 2;
              puzzleModeLevel = 1;
              newGame (noOfPlayers);
            }
            // Change to the next level of puzzle mode
            else
            {
              JOptionPane.showMessageDialog (this,
                  "You completed Level " + puzzleModeLevel,
                  "Congratulations", JOptionPane.WARNING_MESSAGE);
              puzzleModeLevel++;
              readPuzzle (puzzleModeLevel);
              newGame (1);
            }
          }
          // Loads the current level again since they failed to solve it
          else if (puzzleModeTurns == 0)
          {
            JOptionPane.showMessageDialog (this,
                "Game Over, Try Again", "Game Over",
                JOptionPane.WARNING_MESSAGE);
            readPuzzle (puzzleModeLevel);
            newGame (1);
          }
        }
      }
    }
    else
      return false;

    // Repaint the screen and return true since key event was handled
    repaint ();
    return true;

  } // end keyPressed()


  /* Handles mouse clicking
   *@param event   the event to be handled
   *@param x       the x position of where the player clicked
   *@param y       the x position of where the player clicked
   */
  public boolean mouseDown (Event event, int x, int y)
  {
    int index;
    int index2;
    int counter;
    int counter2;
    boolean valid = true;

    Graphics g = getGraphics ();
    Graphics2D g2 = (Graphics2D) g;
    g2.setFont (new Font ("Times New Roman", Font.PLAIN, 30));

    // Ends the turn
    if (x >= 400 && x <= 530 && y >= 70 && y <= 120 &&
        (longJumpPossible == false || spaceJumpPossible == false))
    {
      spaceJumpPossible = true;
      longJumpPossible = true;
      selected = false;
      startingWidth = 0;
      startingHeight = 0;
      lockWidth = 0;
      lockHeight = 0;
      widthPos = 0;
      heightPos = 0;
      g2.setColor (Color.gray);
      g2.fillRect (10, 10, 230, 100);

      // Starts a new game if a player wins in multiplayer mode
      if (isWinnerInZone (currentPlayer, winZones [currentPlayer]) && noOfPlayers >= 2)
      {
        JOptionPane.showMessageDialog (this,
            playerNames [currentPlayer] + " has won! Press OK for a new game.",
            "Congratulations", JOptionPane.WARNING_MESSAGE);
        newGame (noOfPlayers);
      }
      else
      {
        // Change the turn
        if (currentPlayer == noOfPlayers)
          currentPlayer = 1;
        else
          currentPlayer += 1;
        g2.setColor (Color.black);
        if (noOfPlayers >= 2)
        {
          g2.drawString (playerNames [currentPlayer] +
              "'s Turn", 25, 100);
          g2.drawImage (marblePics [currentPlayer], 108, 115, null);
        }
        else
        {
          // Display the number of turns left and level
          g2.setColor (Color.gray);
          g2.fillRect (10, 10, 190, 150);
          g2.setColor (Color.black);
          g2.drawString ("Level " + puzzleModeLevel, 25, 100);
          puzzleModeTurns--;
          g2.drawString ("Turns Left: " + puzzleModeTurns, 25, 125);
          if (isWinnerInZone (currentPlayer, winZones [currentPlayer]))
          {
            // Change to multiplayer mode since they beat puzzle mode
            if (puzzleModeLevel == MAX_PUZZLE_MODE_LEVELS)
            {
              JOptionPane.showMessageDialog (this,
                  "You beat Puzzle Mode!!", "Congratulations",
                  JOptionPane.WARNING_MESSAGE);
              noOfPlayers = 2;
              puzzleModeLevel = 1;
              newGame (noOfPlayers);
            }
            // Change to the next level of puzzle mode
            else
            {
              JOptionPane.showMessageDialog (this,
                  "You completed Level " + puzzleModeLevel,
                  "Congratulations", JOptionPane.WARNING_MESSAGE);
              puzzleModeLevel++;
              readPuzzle (puzzleModeLevel);
              newGame (1);
            }
          }
          // Loads the current level again since they failed to solve it
          else if (puzzleModeTurns == 0)
          {
            JOptionPane.showMessageDialog (this,
                "Game Over, Try Again", "Game Over",
                JOptionPane.WARNING_MESSAGE);
            readPuzzle (puzzleModeLevel);
            newGame (1);
          }
        }
      }
      repaint ();
    }

    // Changes the circle on the previous spot back to black
    g2.setColor (Color.black);
    if (board [widthPos] [heightPos] > 0)
    {
      g2.draw (pictureBoard [widthPos] [heightPos]);
    }

    // Determines which space on the board they chose
    for (int width = 0 ; width < BOARD_WIDTH ; width++)
    {
      for (int height = 0 ; height < BOARD_HEIGHT ; height++)
      {
        if (pictureBoard [width] [height].contains (x, y) &&
            board [width] [height] != -1)
        {
          widthPos = width;
          heightPos = height;
        }
      }
    }

    // Makes a move if they select a valid move
    if (selected == true)
    {
      if (board [widthPos] [heightPos] == -2)
      {
        board [selectedWidth] [selectedHeight] = 0;
        board [widthPos] [heightPos] = currentPlayer;
        g2.setColor (Color.gray);
        g2.fillArc (31 + (SIZE_OF_MARBLE / 2 + 5) * selectedWidth,
            61 + selectedHeight * (SIZE_OF_MARBLE + 3),
            SIZE_OF_MARBLE - 1, SIZE_OF_MARBLE - 1, 0, 360);
        repaint ();
        lockWidth = widthPos;
        lockHeight = heightPos;
        if ((selectedWidth + 1 == widthPos ||
              selectedWidth - 1 == widthPos) ||
            ((selectedWidth + 2 == widthPos ||
              selectedWidth - 2 == widthPos) &&
             selectedHeight == heightPos))
        {
          longJumpPossible = false;
          spaceJumpPossible = false;
        }
        else
        {
          spaceJumpPossible = false;
        }
      }
    }
    else
    {
      startingWidth = widthPos;
      startingHeight = heightPos;
    }

    // Reverts all the spaces which were possible moves back to blank spaces
    for (index = 0 ; index < BOARD_WIDTH ; index++)
    {
      for (index2 = 0 ; index2 < BOARD_HEIGHT ; index2++)
      {
        if (board [index] [index2] == -2)
        {
          board [index] [index2] = 0;
          g2.draw (pictureBoard [index] [index2]);
        }
      }
    }

    // Highlights all possible moves
    if (board [widthPos] [heightPos] == currentPlayer)
    {
      selected = true;
      selectedWidth = widthPos;
      selectedHeight = heightPos;

      // Highlights the current piece
      g2.setColor (Color.yellow);
      if (board [lockWidth] [lockHeight] == currentPlayer)
        g2.draw (pictureBoard [lockWidth] [lockHeight]);
      else if (board [widthPos] [heightPos] == currentPlayer)
        g2.draw (pictureBoard [widthPos] [heightPos]);

      if ((lockWidth == widthPos && lockHeight == heightPos) || (lockWidth == 0 && lockHeight == 0))
      {
        // Does the 1 space jumps
        g2.setColor (Color.green);

        // Note that X is the piece and O is the empty space from now on
        // Does  O
        //      X
        if (widthPos + 1 <= 24 && heightPos + 1 <= 16 &&
            board [widthPos + 1] [heightPos + 1] == 0 &&
            longJumpPossible == true && spaceJumpPossible == true)
        {
          g2.draw (pictureBoard [widthPos + 1] [heightPos + 1]);
          board [widthPos + 1] [heightPos + 1] = -2;
        }

        // Does  X
        //        O
        if (widthPos + 1 <= 24 && heightPos - 1 >= 0 &&
            board [widthPos + 1] [heightPos - 1] == 0 &&
            longJumpPossible == true && spaceJumpPossible == true)
        {
          g2.draw (pictureBoard [widthPos + 1] [heightPos - 1]);
          board [widthPos + 1] [heightPos - 1] = -2;
        }

        // Does  O
        //        X
        if (widthPos - 1 >= 0 && heightPos + 1 <= 16 &&
            board [widthPos - 1] [heightPos + 1] == 0 &&
            longJumpPossible == true && spaceJumpPossible == true)
        {
          g2.draw (pictureBoard [widthPos - 1] [heightPos + 1]);
          board [widthPos - 1] [heightPos + 1] = -2;
        }

        // Does  X
        //      O
        if (widthPos - 1 >= 0 && heightPos - 1 >= 0 &&
            board [widthPos - 1] [heightPos - 1] == 0 &&
            longJumpPossible == true && spaceJumpPossible == true)
        {
          g2.draw (pictureBoard [widthPos - 1] [heightPos - 1]);
          board [widthPos - 1] [heightPos - 1] = -2;
        }

        // Does  O X
        if (widthPos - 2 >= 0 && board [widthPos - 2] [heightPos] == 0
            && longJumpPossible == true
            && spaceJumpPossible == true)
        {
          g2.draw (pictureBoard [widthPos - 2] [heightPos]);
          board [widthPos - 2] [heightPos] = -2;
        }

        // Does  X O
        if (widthPos + 2 <= 24 &&
            board [widthPos + 2] [heightPos] == 0 &&
            longJumpPossible == true && spaceJumpPossible == true)
        {
          g2.draw (pictureBoard [widthPos + 2] [heightPos]);
          board [widthPos + 2] [heightPos] = -2;
        }

        // Does the long jumps
        for (counter = 0 ; counter <= 11 ; counter++)
        {
          // Does    O     and      O
          //        X              O
          //       X              X
          //                     O
          //                    X
          if (widthPos + counter <= 24 && widthPos +
              counter * 2 <= 24 && heightPos + counter <= 16 &&
              heightPos + counter * 2 <= 16)
          {
            if (board [widthPos + counter] [heightPos + counter] >
                0 && board [widthPos + (counter * 2)]
                [heightPos + (counter * 2)] == 0)
            {
              for (counter2 = counter + 1 ; counter2 <= counter *
                  2 ; counter2++)
              {
                if (board [widthPos + counter2]
                    [heightPos + counter2] > 0)
                  valid = false;
              }
              for (counter2 = 1 ; counter2 <= counter - 1 ;
                  counter2++)
              {
                if (board [widthPos + counter2] [heightPos +
                    counter2] > 0)
                  valid = false;
              }
              if (valid != false && longJumpPossible == true)
              {
                g2.draw (pictureBoard [widthPos + (counter * 2)]
                    [heightPos + (counter * 2)]);
                board [widthPos + (counter * 2)]
                  [heightPos + (counter * 2)] = -2;
              }
            }
          }
          valid = true;

          // Does  X       and  X
          //        X            O
          //         O            X
          //                       O
          //                        O
          if (widthPos + counter <= 24 && widthPos + counter * 2 <=
              24 && heightPos - counter >= 0 &&
              heightPos - counter * 2 >= 0)
          {
            if (board [widthPos + counter] [heightPos - counter] >
                0 && board [widthPos + (counter * 2)]
                [heightPos - (counter * 2)] == 0)
            {
              for (counter2 = counter + 1 ; counter2 <= counter *
                  2 ; counter2++)
              {
                if (board [widthPos + counter2]
                    [heightPos - counter2] > 0)
                  valid = false;
              }
              for (counter2 = 1 ; counter2 <= counter - 1 ;
                  counter2++)
              {
                if (board [widthPos + counter2]
                    [heightPos - counter2] > 0)
                  valid = false;
              }
              if (valid != false && longJumpPossible == true)
              {
                g2.draw (pictureBoard [widthPos + (counter * 2)]
                    [heightPos - (counter * 2)]);
                board [widthPos + (counter * 2)]
                  [heightPos - (counter * 2)] = -2;
              }
            }
          }
          valid = true;

          // Does  O     and     O
          //        X             O
          //         X             X
          //                        O
          //                         X
          if (widthPos - counter >= 0 && widthPos - counter * 2 >=
              0 && heightPos + counter <= 16 && heightPos +
              counter * 2 <= 16)
          {
            if (board [widthPos - counter] [heightPos + counter] >
                0 && board [widthPos - (counter * 2)]
                [heightPos + (counter * 2)] == 0)
            {
              for (counter2 = counter + 1 ; counter2 <=
                  counter * 2 ; counter2++)
              {
                if (board [widthPos - counter2]
                    [heightPos + counter2] > 0)
                  valid = false;
              }
              for (counter2 = 1 ; counter2 <= counter - 1 ;
                  counter2++)
              {
                if (board [widthPos - counter2]
                    [heightPos + counter2] > 0)
                  valid = false;
              }
              if (valid != false && longJumpPossible == true)
              {
                g2.draw (pictureBoard [widthPos - (counter * 2)]
                    [heightPos + (counter * 2)]);
                board [widthPos - (counter * 2)]
                  [heightPos + (counter * 2)] = -2;
              }
            }
          }
          valid = true;

          // Does    X     and      X
          //        X              O
          //       O              X
          //                     O
          //                    O
          if (widthPos - counter >= 0 && widthPos - counter * 2 >=
              0 && heightPos - counter >= 0 &&
              heightPos - counter * 2 >= 0)
          {
            if (board [widthPos - counter] [heightPos - counter] >
                0 && board [widthPos - (counter * 2)]
                [heightPos - (counter * 2)] == 0)
            {
              for (counter2 = counter + 1 ; counter2 <=
                  counter * 2 ; counter2++)
              {
                if (board [widthPos - counter2]
                    [heightPos - counter2] > 0)
                  valid = false;
              }
              for (counter2 = 1 ; counter2 <= counter - 1 ;
                  counter2++)
              {
                if (board [widthPos - counter2]
                    [heightPos - counter2] > 0)
                  valid = false;
              }
              if (valid != false && longJumpPossible == true)
              {
                g2.draw (pictureBoard [widthPos - (counter * 2)]
                    [heightPos - (counter * 2)]);
                board [widthPos - (counter * 2)]
                  [heightPos - (counter * 2)] = -2;
              }
            }
          }
          valid = true;

          // Does   X X O     and     X O X O O
          if (widthPos + counter <= 24 && widthPos + counter * 2 <=
              24)
          {
            if (board [widthPos + (counter * 2)] [heightPos] == 0 &&
                board [widthPos + counter] [heightPos] > 0)
            {
              for (counter2 = counter + 1 ; counter2 <= counter *
                  2 ; counter2++)
              {
                if (board [widthPos + counter2] [heightPos] > 0)
                  valid = false;
              }
              for (counter2 = 1 ; counter2 <= counter - 1 ;
                  counter2++)
              {
                if (board [widthPos + counter2] [heightPos] > 0)
                  valid = false;
              }
              if (valid != false && longJumpPossible == true)
              {
                g2.draw (pictureBoard [widthPos + (counter * 2)]
                    [heightPos]);
                board [widthPos + (counter * 2)] [heightPos] =
                  - 2;
              }
            }
          }
          valid = true;

          // Does   O X X     and     O O X O X
          if (widthPos - counter >= 0 && widthPos - counter * 2 >= 0)
          {
            if (board [widthPos - (counter * 2)] [heightPos] == 0 &&
                board [widthPos - counter] [heightPos] > 0)
            {
              for (counter2 = counter + 1 ; counter2 <= counter *
                  2 ; counter2++)
              {
                if (board [widthPos - counter2] [heightPos] > 0)
                  valid = false;
              }
              for (counter2 = 1 ; counter2 <= counter - 1 ;
                  counter2++)
              {
                if (board [widthPos - counter2] [heightPos] > 0)
                  valid = false;
              }
              if (valid != false && longJumpPossible == true)
              {
                g2.draw (pictureBoard [widthPos - (counter * 2)]
                    [heightPos]);
                board [widthPos - (counter * 2)] [heightPos] =
                  - 2;
              }
            }
          }
          valid = true;
        }
      }
    }
    return true;
  }


  // Method to deal with the menu options
  public boolean action (Event e, Object arg)
  {
    if (e.target == exitOption)
    {
      hide ();
      System.exit (0);
    }
    else if (e.target == howToPlay)
    {
      ImageIcon iconImage = new ImageIcon ("Pictures/HowToPlay.gif");
      Object[] object = {iconImage};
      JOptionPane.showMessageDialog (this, object,
          "How To Play", JOptionPane.INFORMATION_MESSAGE);
    }
    else if (e.target == puzzleMode)
    {
      noOfPlayers = 1;
      newGame (noOfPlayers);
    }
    else if (e.target == newOption)
      newGame (noOfPlayers);
    else if (e.target == twoPlayers)
    {
      MultiplayerOptionsWindow.noOfPlayers = 2;
      new MultiplayerOptionsWindow (this);
      setEnabled (false);
    }
    else if (e.target == threePlayers)
    {
      MultiplayerOptionsWindow.noOfPlayers = 3;
      new MultiplayerOptionsWindow (this);
      setEnabled (false);
    }
    else if (e.target == fourPlayers)
    {
      MultiplayerOptionsWindow.noOfPlayers = 4;
      new MultiplayerOptionsWindow (this);
      setEnabled (false);
    }
    else if (e.target == sixPlayers)
    {
      MultiplayerOptionsWindow.noOfPlayers = 6;
      MultiplayerOptionsWindow w = new MultiplayerOptionsWindow (this);
      setEnabled (false);
    }
    else if (e.target == aboutOption)
    {
      JOptionPane.showMessageDialog (this,
          "Chinese Checkers V4 \nBy: Maverick Lee\n       Tyler Kutluoglu\nJanuary 20, 2007", "About...",
          JOptionPane.INFORMATION_MESSAGE);

    }
    return true;
  }


  public void paint (Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    int width;
    int height;

    // Set up the offscreen buffer the first time paint() is called
    if (offScreenBuffer == null)
    {
      offScreenImage = createImage (size ().width, size ().height);
      offScreenBuffer = offScreenImage.getGraphics ();
    }

    // All of the drawing is done to an off screen buffer which is
    // then copied to the screen.  This will prevent flickering
    // Clear the offScreenBuffer first
    offScreenBuffer.clearRect (0, 0, size ().width, size ().height);

    // Draw the board and the pieces
    for (width = 0 ; width < BOARD_WIDTH ; width++)
    {
      for (height = 0 ; height < BOARD_HEIGHT ; height++)
      {
        if (board [width] [height] > 0)
        {
          offScreenBuffer.drawImage (marblePics [board [width] [height]], 31 +
              (SIZE_OF_MARBLE / 2 + 5) * width, 61 + height *
              (SIZE_OF_MARBLE + 3), null);
        }
      }
    }

    offScreenBuffer.setColor (Color.black);
    offScreenBuffer.drawRect (400, 70, 130, 50);
    offScreenBuffer.setFont (new Font ("Times New Roman", Font.PLAIN, 20));
    offScreenBuffer.drawString ("End Turn", 435, 100);
    offScreenBuffer.setFont (new Font ("Times New Roman", Font.PLAIN, 30));
    if (noOfPlayers >= 2)
    {
      offScreenBuffer.drawString (playerNames [currentPlayer] + "'s Turn", 25, 100);
      offScreenBuffer.drawImage (marblePics [currentPlayer], 108, 115, null);
    }
    else
    {
      offScreenBuffer.drawString ("Level " + puzzleModeLevel, 25, 100);
      offScreenBuffer.drawString ("Turns Left: " + puzzleModeTurns, 25, 125);
    }

    g.drawImage (offScreenImage, 0, 0, this);

    // The following needs Graphics2D so it drawn after the buffer
    g2.setColor (Color.yellow);
    if (board [lockWidth] [lockHeight] == currentPlayer)
      g2.draw (pictureBoard [lockWidth] [lockHeight]);
    else if (board [widthPos] [heightPos] == currentPlayer)
      g2.draw (pictureBoard [widthPos] [heightPos]);

    for (width = 0 ; width < BOARD_WIDTH ; width++)
    {
      for (height = 0 ; height < BOARD_HEIGHT ; height++)
      {
        if (board [width] [height] >= 0)
        {
          g2.setColor (Color.black);
          g2.draw (pictureBoard [width] [height]);
        }
        else if (board [width] [height] == -2)
        {
          g2.setColor (Color.green);
          g2.draw (pictureBoard [width] [height]);
        }
      }
    }
  } // paint method


  // Handles the close button on the window
  public boolean handleEvent (Event evt)
  {
    if (evt.id == Event.WINDOW_DESTROY)
    {
      setVisible (false);
      System.exit (0);
      return true;
    }

    // If not handled, pass the event along
    return super.handleEvent (evt);
  }


  public static void main (String[] args)
  {
    new ChineseCheckersV4 ();
  }
} // ChineseCheckersV4 class
