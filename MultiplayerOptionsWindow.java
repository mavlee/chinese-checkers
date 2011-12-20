// The "MultiplayerOptionsWindow" class.
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/** The "MultiplayerOptionsWindow" class.
  * Purpose: To select the marble types, names and player types of the players
  * @author Tyler Kutluoglu & Maverick Lee
  * @version January 9, 2007
  */
public class MultiplayerOptionsWindow extends Frame
{
    Button OKButton;
    Button cancelButton;
    Choice colour1;
    Choice colour2;
    Choice colour3;
    Choice colour4;
    Choice colour5;
    Choice colour6;
    int noOfColours = 25;
    private ChineseCheckersV4 parent;
    public int[] colourChoices = new int [noOfColours - 1];
    public Image[] pics;
    public static int noOfPlayers = 6;
    private TextField[] names;
    private Choice[] choiceLists;
    private int width;
    private int height;
    private int[] finalColours = new int [noOfPlayers];
    public String[] finalNames = new String [noOfPlayers];
    private int[] midplayerTypeChoices = new int [noOfPlayers];
    private String[] strChoices = {"Choose a marble...", "Red", "Blue",
	"Green", "Yellow", "Purple", "Cyan", "Apple", "Gmail", "ICQ",
	"Messenger", "MSN", "AOL", "Canada", "China", "USA", "Turkey",
	"Ying Yang", "Frog", "Fire", "Desert", "Ice", "Water", "Tech", "Tree"};


    /** Constructs the window
      * @param ChineseCheckersV4    The parent class
      */
    public MultiplayerOptionsWindow (ChineseCheckersV4 parent)
    {
	//Set the name of the window
	super ("Options");
	this.parent = parent;

	//Make the size of the window based on the number of players
	if (noOfPlayers <= 3)
	{
	    width = 150 * noOfPlayers;
	    height = 150 + 50;
	}
	else
	{
	    width = 150 * 3;
	    height = 280 + 50;
	}
	setSize (width, height);
	setBackground (Color.GRAY);

	setLayout (null);


	//Make the OK and Cancel buttons
	OKButton = new Button ("OK");
	OKButton.setBounds (10, height - 40, 100, 30);
	add (OKButton);
	cancelButton = new Button ("Cancel");
	cancelButton.setBounds (120, height - 40, 100, 30);
	add (cancelButton);

	// Set the icon
	Image image = new ImageIcon ("Pictures/0.gif").getImage ();
	setIconImage (image);
	
	//Place the objects on the screen
	CreateNameBoxes ();
	CreateColourLists ();
	CreatePics ();
	this.setResizable (false);
	show ();
    }


    //Display the marble pictures on the screen
    public void CreatePics ()
    {
	pics = new Image [noOfColours];
	for (int picNo = 0 ; picNo < noOfColours ; picNo++)
	    //Get the pictures from the file
	    pics [picNo] = new ImageIcon
		("Pictures/" + picNo + ".gif").getImage ();
    }


    //Make the Name Boxes
    public void CreateNameBoxes ()
    {
	names = new TextField [noOfPlayers];
	for (int choiceNo = 0 ; choiceNo < names.length ; choiceNo++)
	{
	    names [choiceNo] = new TextField ();

	    //Set the default names to "Player" 1-6
	    for (int index = 0 ; index < noOfColours ; index++)
	    {
		names [choiceNo].setText ("Player " + (choiceNo + 1));
	    }
	    names [choiceNo].setFont (new Font ("Alor", Font.PLAIN, 12));

	    //Place the name boxes in positions based on the number of players
	    if (choiceNo <= 2)
		names [choiceNo].setBounds (20 + (150 * (choiceNo)),
			40, 120, 20);
	    else
		names [choiceNo].setBounds (20 + (150 * (choiceNo - 3)),
			160, 120, 20);
	    add (names [choiceNo]);
	}
    }


    //Make the colour lists
    public void CreateColourLists ()
    {
	choiceLists = new Choice [noOfPlayers];
	for (int choiceNo = 0 ; choiceNo < choiceLists.length ; choiceNo++)
	{
	    choiceLists [choiceNo] = new Choice ();

	    //Add all the possible choices to each list
	    for (int index = 0 ; index < noOfColours ; index++)
	    {
		choiceLists [choiceNo].add (strChoices [index]);
	    }
	    choiceLists [choiceNo].setFont (new Font ("Alor", Font.PLAIN, 12));

	    //Place the colour lists in positions based on the
	    //number of players
	    if (choiceNo <= 2)
		choiceLists [choiceNo].setBounds (20 +
			(150 * (choiceNo)), 70, 120, 30);
	    else
		choiceLists [choiceNo].setBounds
		    (20 + (150 * (choiceNo - 3)), 190, 120, 30);
	    add (choiceLists [choiceNo]);
	}
    }


    /** Handles the user pressing the OK or Cancel buttons
      * @param evt The event being executed
      * @param arg The object in question
      */
    public boolean action (Event evt, Object arg)
    {
	//If they press the OK button
	if (evt.target == OKButton)
	{
	    //Check that the same colour wasn't entered twice
	    for (int check = 0 ; check < noOfPlayers ; check++)
	    {
		//Check that the player's name isn't too long
		if ((names [check].getText ()).length () > 8)
		{
		    //Make illegal choice error
		    JOptionPane.showMessageDialog (this,
			    "Please select a shorter name " +
			    (names [check].getText ()),
			    "Input Error", JOptionPane.WARNING_MESSAGE);
		    return true;
		}

		//Illegal input error
		if (colourChoices [check] == 0)
		{
		    //Make illegal choice error
		    JOptionPane.showMessageDialog (this,
			    "Please select a colour for " +
			    (names [check].getText ()),
			    "Input Error", JOptionPane.WARNING_MESSAGE);
		    return true;
		}

		//Check that none of the player's chose the same colour
		for (int index = check + 1 ; index < noOfPlayers ; index++)
		{
		    if (colourChoices [check] == colourChoices [index])
		    {
			//Make same colour error
			JOptionPane.showMessageDialog (this,
				"Please select a different colour for " +
				(names [check].getText ()),
				"Input Error", JOptionPane.WARNING_MESSAGE);
			return true;
		    }
		}


	    }

	    //Records the information in the returning variable
	    //Only if there were no errors
	    for (int index = 0 ; index < noOfPlayers ; index++)
	    {
		finalColours [index] = colourChoices [index];
		finalNames [index] = names [index].getText ();
	    }

	    parent.setEnabled (true);

	    //Close the window
	    hide ();
	    parent.resume (finalColours, finalNames);
	}

	//If they press cancel
	if (evt.target == cancelButton)
	{
	    //Close the window
	    parent.setEnabled (true);
	    hide ();
	}

	//Record which colours they selected
	for (int index = 0 ; index < noOfPlayers ; index++)
	{
	    if (evt.target == choiceLists [index])
		colourChoices [index] =
		    choiceLists [index].getSelectedIndex ();
	}

	repaint ();
	return true;
    }


    // Handles the close button on the window
    public boolean handleEvent (Event evt)
    {
	if (evt.id == Event.WINDOW_DESTROY)
	{
	    parent.setEnabled (true);
	    setVisible (false);
	    return true;
	}
	// If not handled, pass the event along
	return super.handleEvent (evt);
    }


    //Paints the pictures of the marble types on the screen
    public void paint (Graphics g)
    {
	for (int picNo = 0 ; picNo < noOfPlayers ; picNo++)
	{
	    if (picNo <= 2)
		g.drawImage (pics [colourChoices [picNo]], 60 +
			(150 * (picNo)), 110, null);
	    else
		g.drawImage (pics [colourChoices [picNo]], 60 +
			(150 * (picNo - 3)), 230, null);
	}
    } // paint method


    public static void main (String[] args)
    {
	// Create a MultiplayerOptionsWindow frame
	new MultiplayerOptionsWindow (null);
    } // main method
} // MultiplayerOptionsWindow class
