## Name
CS 351L Fall 2023 Project 2: H.E.R
Author: Nathan J. Rowe

## Description

<h2>How To Start</h2>
Run the program from the Main class, Main.java . Select a mode from the dropdown
and click Start!

<h2>How to Play</h2>

***Moving a Pawn***
To move you pawn in slowMode or fastMode, simply click your pawn! All possible moves will be highlighted.
Click enabled moves to move the pawn.

***Moving HER Pawn - Slow Mode***
After the user move in slow mode, lines with probabilities will appear on the screen.
Click a line to select that move, or click the <em>Select Random Pawn</em> button to let HER choose.

<h2>Known Bugs & Non-Funtional Features</h2>
<ul>
    <li>
        In some boardstates, the game will (rarely) think that a pawn can be
        moved despite no available moves.
    </li>
    <li>
        In slow mode, the lines will not be removed if the user resets the board before
        selecting one. To fix this, select a line and reset the board.
        Note: THE BOARD MUST BE RESET DURING YOUR TURN
    </li>
    <li>
        Auto Mode does not function past the first round. Likely due to the game
        finishing before the game generates.
    </li>
</ul>

**Other Notes from Author**
Fast mode and slow mode work amazingly. Auto mode proved to be an issue.
I ran short on time to find a solution, but some possible solutions come to mind...
<ol>
    <li>
        Forcing the game to only execute after pressing a button.
    </li>
    <li>
        Placing Auto Mode into a Timeline, slowing the game down
    </li>
    <li>
        Runnning a separate version of the game using only the attached
        string array (would require the most work)
    </li>
</ol>
Given some free time to revise this project, I might try some of these.